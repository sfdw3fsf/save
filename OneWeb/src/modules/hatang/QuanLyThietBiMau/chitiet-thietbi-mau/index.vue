<template src="./index.template.html"></template>
<script>
import LichSuThayDoi from './components/LichSuThayDoi/index.vue'
import ThongTinChung from './components/ThongTinChung/index.vue'
import { filterTabsByContext } from './tabConfig.js'
import api from '../api/ThietBi.api.js'

export default {
  name: 'ChiTietThietBiMauPage',
  components: {
    LichSuThayDoi,
    ThongTinChung
  },
  props: {
    equipmentType: {
      type: String,
      default: null
    }
  },
  watch: {
    '$route.params.id': {
      handler(newId) {
        if (newId && this.currentMode === 'VIEW') {
          this.loadItemDetail(parseInt(newId))
        }
      },
      immediate: true
    }
  },
  async created() {
    const routeId = this.$route.params.id
    if (routeId) {
      await this.loadItemDetail(parseInt(routeId))
    }
  },
  data: function() {
    return {
      visible: true,
      item: null,
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      tab_index: 1, // Default active tab
      currentItem: {
        id: null,
        mangThietBiId: null,
        loaiThietBiId: null,
        phanLoaiThietBiId: null,
        maChungLoaiTB: '',
        tenChungLoaiTB: '',
        hieuLuc: 1,
        hangSanXuatId: null,
        maVatTu: '',
        ghiChu: '',
        anhDaiDien: '',
        // Attributes for Server, Server Blade, Khay đĩa, Network
        khongGianLapDat: null,
        fullDepth: 0,
        congSuatDinhDanh: null,
        // Attributes for Máy phát điện
        dienApDinhDanh: null,
        loaiNguonDien: null,
        heSoCosPhi: null,
        chieuDai: null,
        chieuRong: null,
        chieuCao: null,
        // Attributes for Tủ trung thế
        // Attributes for Máy biến áp
        loaiMay: null,
        // Attributes for Tủ hạ thế, Tủ ATS, Tủ phân phối
        // Attributes for UPS
        cauHinhHeThong: '',
        // Attributes for rPDU
        dongDienDinhDanh: null
      },
      controls: {
        thongTinChungControl: {
          selectMangThietBi: { enabled: true, invalid: false },
          selectLoaiThietBi: { enabled: true, invalid: false },
          selectPhanLoaiThietBi: { enabled: true, invalid: false },
          txtMaChungLoaiTB: { enabled: false, invalid: false },
          txtTenChungLoaiTB: { enabled: false, invalid: false },
          selectHieuLuc: { enabled: false, invalid: false },
          selectHangSanXuat: { enabled: false, invalid: false },
          txtMaVatTu: { enabled: false, invalid: false },
          txtGhiChu: { enabled: false, invalid: false },
          txtAnhDaiDien: { enabled: false, invalid: false }
        }
      }
    }
  },
  computed: {
    tabContext() {
      return {
        equipmentType: this.equipmentType,
        currentMode: this.currentMode,
        currentItem: this.currentItem
      }
    },
    availableTabs() {
      const tabs = filterTabsByContext(this.tabContext)
      return Array.isArray(tabs) ? tabs : []
    },
    activeTab() {
      if (!this.availableTabs.length) {
        return null
      }
      const index = this.tab_index - 1
      if (index < 0 || index >= this.availableTabs.length) {
        return this.availableTabs[0]
      }
      return this.availableTabs[index]
    }
  },
  watch: {
    availableTabs: {
      handler(newTabs) {
        if (!Array.isArray(newTabs) || newTabs.length === 0) {
          this.tab_index = 0
          return
        }
        if (this.tab_index < 1 || this.tab_index > newTabs.length) {
          this.tab_index = 1
        }
      },
      immediate: true
    }
  },
  methods: {
    shouldRenderTab(tab) {
      if (!tab) {
        return false
      }
      const equipmentType = this.equipmentType
      if (Array.isArray(tab.equipmentTypes) && tab.equipmentTypes.length > 0) {
        if (!equipmentType) {
          return false
        }
        if (tab.equipmentTypes.indexOf(equipmentType) === -1) {
          return false
        }
      }
      if (typeof tab.isVisible === 'function') {
        return tab.isVisible(this.tabContext)
      }
      return true
    },
    resolveTabProps(tab) {
      return tab?.getProps?.(this) || {}
    },
    getRefInstance(refName) {
      const ref = this.$refs?.[refName]
      return Array.isArray(ref) ? ref[0] : ref
    },
    getTabComponentInstance(tab) {
      return tab?.ref ? this.getRefInstance(tab.ref) : null
    },
    async runHookGroup(hookKey) {
      await this.$nextTick()
      const executions = this.availableTabs.map((tab) => this.invokeTabHook(tab, hookKey))
      return Promise.all(executions)
    },
    async invokeTabHook(tab, hookKey) {
      const hookDef = tab?.[hookKey]
      if (!hookDef) {
        return true
      }
      const instance = this.getTabComponentInstance(tab)
      if (!instance) {
        return true
      }
      if (typeof hookDef === 'function') {
        return hookDef(instance, this)
      }
      if (typeof hookDef === 'string') {
        return instance?.[hookDef]?.()
      }
      if (Array.isArray(hookDef)) {
        for (let i = 0; i < hookDef.length; i += 1) {
          const methodName = hookDef[i]
          if (typeof methodName === 'string' && typeof instance[methodName] === 'function') {
            await instance[methodName]()
          }
        }
        return true
      }
      return true
    },
    async collectSaveResults() {
      await this.$nextTick()
      const results = []
      for (let i = 0; i < this.availableTabs.length; i += 1) {
        const tab = this.availableTabs[i]
        const typeSave = tab?.typeSave
        if (!tab?.saveFn) {
          continue
        }
        if (typeSave === 'method') {
          results.push({ tab, result: null })
          continue
        }
        const result = await this.invokeTabHook(tab, 'saveFn')
        results.push({ tab, result })
      }
      return results
    },
    mergePayloads(results) {
      return results.reduce(
        (acc, entry) => {
          const { tab, result } = entry
          const saveFn = tab?.saveFn
          if (!tab?.typeSave) {
            return acc
          }
          if (tab.typeSave === 'object' && result && typeof result === 'object') {
            return {
              aggregate: { ...acc.aggregate, ...result },
              methodTasks: acc.methodTasks
            }
          }
          if (tab.typeSave === 'method') {
            const methodNames = Array.isArray(saveFn) ? saveFn : [saveFn]
            const tasks = methodNames
              .filter((methodName) => typeof methodName === 'string' && methodName.trim())
              .map((methodName) => ({
                tab,
                methodName
              }))
            return {
              aggregate: acc.aggregate,
              methodTasks: acc.methodTasks.concat(tasks)
            }
          }
          if (Array.isArray(tab.typeSave)) {
            const methodTasks = acc.methodTasks.concat(
              tab.typeSave.map((methodName) => ({
                tab,
                methodName
              }))
            )
            return { aggregate: acc.aggregate, methodTasks }
          }
          return acc
        },
        { aggregate: {}, methodTasks: [] }
      )
    },
    async executeMethodTasks(tasks) {
      await this.$nextTick()
      for (let i = 0; i < tasks.length; i += 1) {
        const { tab, methodName } = tasks[i]
        const instance = this.getTabComponentInstance(tab)
        if (!instance || typeof instance[methodName] !== 'function') {
          continue
        }
        await instance[methodName]()
      }
    },
    async triggerTabFetchFlow() {
      await this.runHookGroup('fetchBeforeFn')
      await this.runHookGroup('fetchAfterFn')
    },
    onChangeTab(index) {
      this.tab_index = index
    },
    async onEditDetail() {
      this.currentMode = 'EDIT'
      this.$emit('mode-changed', 'EDIT')
    },
    async onAddNew() {
      this.resetCurrentItem()
      this.currentMode = 'ADD'
      this.resetThongTinChungValidation()
      this.tab_index = 1
      await this.triggerTabFetchFlow()
      this.$emit('mode-changed', 'ADD')
    },
    async onRefreshDetail() {
      if (this.currentItem && this.currentItem.id) {
        await this.loadItemDetail(this.currentItem.id)
      } else if (this.item && (this.item.id || this.item.ID)) {
        const itemId = this.item.id || this.item.ID
        await this.loadItemDetail(itemId)
      }
      await this.triggerTabFetchFlow()
    },
    async onCancel() {
      if (this.currentMode === 'ADD') {
        this.$emit('close')
      } else if (this.currentMode === 'EDIT') {
        if (this.currentItem && this.currentItem.id) {
          await this.loadItemDetail(this.currentItem.id)
          this.currentMode = 'VIEW'
          this.$emit('mode-changed', 'VIEW')
        } else {
          this.currentMode = 'VIEW'
          this.$emit('mode-changed', 'VIEW')
        }
      }
      this.resetThongTinChungValidation()
    },
    show: async function(item, mode) {
      try {
        console.log('Page show called with:', { item, mode })
        this.currentMode = mode
        this.item = item

        if (item && (item.id || item.ID) && (mode === 'VIEW' || mode === 'EDIT')) {
          const itemId = item.id || item.ID
          await this.loadItemDetail(itemId)
        } else if (item) {
          this.currentItem = { ...item }
        } else {
          this.resetCurrentItem()
        }

        await this.triggerTabFetchFlow()
      } catch (error) {
        this.$root.toastError(error.message || 'Đã xảy ra lỗi!')
      }
    },
    resetCurrentItem: function() {
      this.currentItem = {
        id: null,
        mangThietBiId: null,
        loaiThietBiId: null,
        phanLoaiThietBiId: null,
        maChungLoaiTB: '',
        tenChungLoaiTB: '',
        hieuLuc: 1,
        hangSanXuatId: null,
        maVatTu: '',
        ghiChu: '',
        anhDaiDien: '',
        khongGianLapDat: null,
        fullDepth: 0,
        congSuatDinhDanh: null,
        dienApDinhDanh: null,
        loaiNguonDien: null,
        heSoCosPhi: null,
        chieuDai: null,
        chieuRong: null,
        chieuCao: null,
        loaiMay: null,
        cauHinhHeThong: '',
        dongDienDinhDanh: null,
        // Cổng kết nối
        so_cong: null,
        bat_dau_cong: null,
        dinh_dang_ten: '',
        da_su_dung: 0,
        con_trong: 0
      }
    },
    resetThongTinChungValidation: function() {
      const thongTinChungRef = this.getRefInstance('ThongTinChung')
      if (thongTinChungRef && thongTinChungRef.resetValidation) {
        thongTinChungRef.resetValidation()
      }
    },
    onUploadingChanged: function(isUploading) {
      this.$emit('uploading-changed', isUploading)
    },
    onSave: async function() {
      const validationResults = await this.runHookGroup('validateFn')
      const isValid = validationResults.every((result) => result !== false)
      if (!isValid) {
        return
      }
      const saveResults = await this.collectSaveResults()
      const { aggregate, methodTasks } = this.mergePayloads(saveResults)
      if (!Object.keys(aggregate).length && !methodTasks.length) {
        const activeLabel = this.activeTab?.label || 'Tab'
        this.$root.toastError(`[${activeLabel}] Không có dữ liệu lưu hợp lệ!`)
        return
      }
      try {
        this.$root.showLoading(true)

        if (Object.keys(aggregate).length) {
          let savedId
          if (this.currentMode === 'ADD') {
            savedId = await api.createThietBiMau(aggregate)
          } else {
            savedId = await api.updateThietBiMau(this.currentItem.id, aggregate)
          }
          console.log('savedId', savedId)
          this.currentItem.id = savedId.id || savedId
        }
        if (methodTasks.length) {
          await this.executeMethodTasks(methodTasks)
        }

        this.$root.toastSuccess('Lưu thiết bị mẫu thành công!')

        if (this.currentMode === 'ADD' || this.currentMode === 'EDIT') {
          if (this.currentItem.id) {
            await this.loadItemDetail(this.currentItem.id)
          }
          this.currentMode = 'VIEW'
          this.$emit('mode-changed', 'VIEW')
          this.$emit('saved')
        }
      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    loadItemDetail: async function(itemId) {
      try {
        this.$root.showLoading(true)
        const response = await api.getThietBiMauById(itemId)

        if (response) {
          this.currentItem = this.convertApiDataToCurrentItem(response)
          if (this.currentMode !== 'ADD') {
            this.currentMode = 'VIEW'
          }
        } else {
          throw new Error('Thiết bị mẫu không tồn tại')
        }
      } catch (error) {
        this.$root.toastError(error.message || 'Thiết bị mẫu không tồn tại')
        this.$router.push({
          path: '/ha-tang/ds-thietbi-mau'
        })
      } finally {
        this.$root.showLoading(false)
      }
    },
    convertApiDataToCurrentItem: function(apiData = {}) {
      return {
        id: apiData.id ?? null,
        mangThietBiId: apiData.mangThietBiId ?? null,
        loaiThietBiId: apiData.loaiThietBiId ?? null,
        phanLoaiThietBiId: apiData.phanLoaiThietBiId ?? null,
        maChungLoaiTB: apiData.maChungLoaiTB ?? '',
        tenChungLoaiTB: apiData.tenChungLoaiTB ?? '',
        hieuLuc: apiData.hieuLuc ?? 1,
        hangSanXuatId: apiData.hangSanXuatId ?? null,
        maVatTu: apiData.maVatTu ?? '',
        ghiChu: apiData.ghiChu ?? '',
        anhDaiDien: apiData.anhDaiDien ?? '',
        khongGianLapDat: apiData.khongGianLapDat ?? null,
        fullDepth: apiData.fullDepth ?? 0,
        congSuatDinhDanh: apiData.congSuatDinhDanh ?? null,
        dienApDinhDanh: apiData.dienApDinhDanh ?? null,
        loaiNguonDien: apiData.loaiNguonDien ?? null,
        heSoCosPhi: apiData.heSoCosPhi ?? null,
        chieuDai: apiData.chieuDai ?? null,
        chieuRong: apiData.chieuRong ?? null,
        chieuCao: apiData.chieuCao ?? null,
        loaiMay: apiData.loaiMay ?? null,
        cauHinhHeThong: apiData.cauHinhHeThong ?? '',
        dongDienDinhDanh: apiData.dongDienDinhDanh ?? null,
        // Cổng kết nối
        so_cong: apiData.so_cong ?? null,
        bat_dau_cong: apiData.bat_dau_cong ?? null,
        dinh_dang_ten: apiData.dinh_dang_ten ?? '',
        da_su_dung: apiData.da_su_dung ?? 0,
        con_trong: apiData.con_trong ?? 0
      }
    },
    handleError: function(error) {
      if (error.message) {
        this.$root.toastError(error.message)
      } else {
        this.$root.toastError('Đã xảy ra lỗi!')
      }
    }
  }
}
</script>
<style>
.w200 {
  min-width: 180px !important;
}
</style>
