<template src="./index.template.html"></template>
<script>
import moment from 'moment'
import TabHistory from '@/modules/tainguyen-idc/components/Tabs/TabHistory'
import LichSuThayDoi from './components/LichSuThayDoi/index.vue'
import CongKetNoi from './components/CongKetNoi/index.vue'
import ViTriLapDat from './components/ViTriLapDat/index.vue'
import ThongTinChung from './components/ThongTinChung/index.vue'
import NghiepvuIDC from './components/TabNghiepvuIDC/index.vue'
import KhachHangIDC from './components/TabKhachHang/index.vue'
import TaiSan from './components/TabTaisan/index.vue'
import { filterTabsByContext } from './tabConfig.js'
import api from '../api'
import { DEVICE_TYPE_TO_MANG_THIET_BI } from '../const.js'

export default {
  name: 'ChiTietThietBiPageV2',
  components: {
    TabHistory,
    LichSuThayDoi,
    CongKetNoi,
    ViTriLapDat,
    ThongTinChung,
    NghiepvuIDC,
    KhachHangIDC,
    TaiSan
  },
  props: {
    deviceTypeId: {
      type: Number,
      default: null
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
        donViQuanLyId: 0,
        nguoiQuanLyId: null,
        mangThietBiId: null,
        loaiThietBiId: null,
        heThongId: null,
        tenThietBi: '',
        maThietBi: '',
        kyHieuThietBi: '',
        dongSanPham: '',
        serialNumber: null,
        thoiDiemLapDat: null,
        ngayDuaVaoSuDung: null,
        tagNumber: '',
        maTheTaiSan: '',
        trangThaiTaiSanId: null,
        congSuatThietKe: null,
        trangThaiVanHanhId: null,
        loaiKhachHangId: null,
        soHuuThietBiId: null,
        hieuLuc: 1,
        fullDepth: 0,
        icons: '',
        soCongNguonDauVao: null,
        hangSanXuatId: null,
        namSanXuat: new Date().getFullYear(),
        noiSanXuatId: null,
        soCongNguonDauRa: null,
        ghiChu: '',
        thietBiChaId: null,
        thanhPhanThietBiIds: [],
        nhaCungCapId: null,
        phamViIdcId: null,
        // Thông tin lắp đặt
        lapDatTaiIdcId: null,
        toaNhaId: null,
        matSanId: null,
        phongZoneId: null,
        rackId: null,
        uTrenId: null,
        uDuoiId: null,
        khongGianLapDat: null,
        kieuLapDatId: null,
        dienApDinhDanh: null,
        dongDienDinhDanh: null,
        // Đặc tính kỹ thuật mới
        chieuDai: null,
        chieuRong: null,
        chieuCao: null,
        congSuatThucTe: null,
        loaiNguonDien: null, // Cho tủ trung thế: 1 pha / 3 pha
        loaiMay: null, // Cho máy biến áp: Hạ áp / Tăng áp
        heSoCosPhi: null, // Cho máy biến áp
        tyLeSuDung: null, // Cho máy biến áp (ReadOnly, tự động tính)
        dongDienThucTe: null, // Cho máy phát điện, tủ trung thế, UPS, máy bơm
        cauHinhHeThong: '', // Cho UPS
        danhSachOCam: [],
        //tab tài sản
        maDuAn: '',
        theTaiSan: '',
        idChiTietTS: null,
        maVatTu: '',
        tenDuAn: '',
        ngayDKKhauHao: '',
        hanBaoHanh: '',
        ngayLapDat: '',
        ngaySuDung: '',
        donViQuanLy: null,
        caNhanQuanLy: null,
        phongToQuanLy: null,
        emailSDT: '',
      },
      controls: {
        thongTinChungControl: {
          selectMangThietBi: { enabled: false, invalid: false },
          selectLoaiThietBi: { enabled: false, invalid: false },
          selectHeThong: { enabled: false, invalid: false },
          selectChungLoaiThietBi: { enabled: false, invalid: false },
          selectPhanLoaiThietBi: { enabled: false, invalid: false },
          selectSoHuuThietBi: { enabled: false, invalid: false },
          txtMaThietBi: { enabled: false, invalid: false },
          txtTenThietBi: { enabled: false, invalid: false },
          txtSerialNumber: { enabled: false, invalid: false },
          txtDongSanPham: { enabled: false, invalid: false },
          selectHangSanXuat: { enabled: false, invalid: false },
          selectNhaCungCap: { enabled: false, invalid: false },
          txtKyHieuThietBi: { enabled: false, invalid: false },
          selectLoaiKhachHang: { enabled: false, invalid: false },
          txtGhiChu: { enabled: false, invalid: false },
          selectTrangThaiVanHanh: { enabled: false, invalid: false },
          selectKieuLapDat: { enabled: false, invalid: false },
          txtDienApDinhDanh: { enabled: false, invalid: false },
          txtDongDienDinhDanh: { enabled: false, invalid: false },
          txtCongSuatDinhDanh: { enabled: false, invalid: false },
          txtKhongGianLapDat: { enabled: false, invalid: false },
          txtChieuDai: { enabled: false, invalid: false },
          txtChieuRong: { enabled: false, invalid: false },
          txtChieuCao: { enabled: false, invalid: false },
          txtCongSuatThucTe: { enabled: false, invalid: false },
          selectLoaiNguonDien: { enabled: false, invalid: false },
          selectLoaiMay: { enabled: false, invalid: false },
          txtHeSoCosPhi: { enabled: false, invalid: false },
          txtDongDienThucTe: { enabled: false, invalid: false },
          txtCauHinhHeThong: { enabled: false, invalid: false }
        },
        tabTaiSanControl: {
          selectPhongToQuanLy: { enabled: false, invalid: false },
          selectDonViQuanLy: { enabled: false, invalid: false },
          selectCaNhanQuanLy: { enabled: false, invalid: false },
          txtTenDuAn: { enabled: false, invalid: false },
          txtMaVatTu: { enabled: false, invalid: false },
          txtIDChiTietTS: { enabled: false, invalid: false },
          txtMaDuAn: { enabled: false, invalid: false },
          txtTheTaiSan: { enabled: false, invalid: false },
          txtEmailSDT: { enabled: false, invalid: false }
        }
      }
    }
  },
  computed: {
    resolvedDeviceTypeId() {
      if (this.currentItem && this.currentItem.loaiThietBiId) {
        return this.currentItem.loaiThietBiId
      }
      if (this.deviceTypeId) {
        return this.deviceTypeId
      }
      return null
    },
    tabContext() {
      return {
        deviceTypeId: this.resolvedDeviceTypeId,
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
      const deviceTypeId = this.resolvedDeviceTypeId
      if (Array.isArray(tab.deviceTypeIds) && tab.deviceTypeIds.length > 0) {
        if (!deviceTypeId) {
          return false
        }
        if (tab.deviceTypeIds.indexOf(deviceTypeId) === -1) {
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
      // Chuyển sang chế độ EDIT
      this.currentMode = 'EDIT'

      // Disable mảng thiết bị và loại thiết bị khi có deviceTypeId
      if (this.deviceTypeId) {
        if (this.controls.thongTinChungControl) {
          if (this.controls.thongTinChungControl.selectMangThietBi) {
            this.controls.thongTinChungControl.selectMangThietBi.enabled = false
          }
          if (this.controls.thongTinChungControl.selectLoaiThietBi) {
            this.controls.thongTinChungControl.selectLoaiThietBi.enabled = false
          }
        }
        await this.autoMapDeviceTypeInfo()
      }

      this.$emit('mode-changed', 'EDIT')
    },
    async onAddNew() {
      // Chuyển sang chế độ ADD và reset dữ liệu
      this.resetCurrentItem()
      this.currentMode = 'ADD'
      this.resetThongTinChungValidation()
      this.tab_index = 1

      // Disable mảng thiết bị và loại thiết bị khi có deviceTypeId
      if (this.deviceTypeId) {
        if (this.controls.thongTinChungControl) {
          if (this.controls.thongTinChungControl.selectMangThietBi) {
            this.controls.thongTinChungControl.selectMangThietBi.enabled = false
          }
          if (this.controls.thongTinChungControl.selectLoaiThietBi) {
            this.controls.thongTinChungControl.selectLoaiThietBi.enabled = false
          }
        }
        await this.autoMapDeviceTypeInfo()
      }

      await this.triggerTabFetchFlow()
      this.$emit('mode-changed', 'ADD')
    },
    async onRefreshDetail() {
      // Làm mới dữ liệu chi tiết hiện tại
      if (this.currentItem && this.currentItem.id) {
        await this.loadItemDetail(this.currentItem.id)
      } else if (this.item && (this.item.id || this.item.ID)) {
        const itemId = this.item.id || this.item.ID
        await this.loadItemDetail(itemId)
      }
      await this.triggerTabFetchFlow()
    },
    async onCancel() {
      // Reset về chế độ VIEW
      if (this.currentMode === 'ADD') {
        // Nếu đang ở chế độ ADD, quay lại
        this.$emit('close')
      } else if (this.currentMode === 'EDIT') {
        // Nếu đang ở chế độ EDIT, reload lại dữ liệu và chuyển về VIEW
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

        // Tự động mapping mảng thiết bị và loại thiết bị từ deviceTypeId khi ở mode ADD/EDIT
        if ((mode === 'ADD' || mode === 'EDIT') && this.deviceTypeId) {
          await this.autoMapDeviceTypeInfo()
        }

        // Disable mảng thiết bị và loại thiết bị khi có deviceTypeId
        if (this.deviceTypeId && (mode === 'ADD' || mode === 'EDIT')) {
          this.resetCurrentItem()
          await this.autoMapDeviceTypeInfo()
          if (this.controls.thongTinChungControl) {
            if (this.controls.thongTinChungControl.selectMangThietBi) {
              this.controls.thongTinChungControl.selectMangThietBi.enabled = false
            }
            if (this.controls.thongTinChungControl.selectLoaiThietBi) {
              this.controls.thongTinChungControl.selectLoaiThietBi.enabled = false
            }
          }
        }

        await this.setDefaultDonViQuanLyId()
        await this.triggerTabFetchFlow()
      } catch (error) {
        this.$root.toastError(error.message || 'Đã xảy ra lỗi!')
      }
    },
    async autoMapDeviceTypeInfo() {
      try {
        // Set loại thiết bị từ deviceTypeId
        this.$set(this.currentItem, 'loaiThietBiId', this.deviceTypeId)

        // Lấy mangThietBiId từ const mapping (không cần gọi API)
        const mangThietBiId = DEVICE_TYPE_TO_MANG_THIET_BI[this.deviceTypeId]

        if (mangThietBiId) {
          // Set mảng thiết bị
          this.$set(this.currentItem, 'mangThietBiId', mangThietBiId)

          await this.$nextTick()
          await this.getRefInstance('ThongTinChung')?.loadLoaiThietBiFromMangThietBi?.()
        }
      } catch (error) {
        console.error('Error auto mapping device type info:', error)
      }
    },
    resetCurrentItem: function() {
      this.currentItem = {
        id: null,
        donViQuanLyId: 0,
        nguoiQuanLyId: null,
        mangThietBiId: null,
        loaiThietBiId: null,
        phanLoaiThietBiId: null,
        heThongId: null,
        tenThietBi: '',
        dongSanPham: '',
        serialNumber: null,
        thoiDiemLapDat: null,
        ngayDuaVaoSuDung: null,
        tagNumber: '',
        maTheTaiSan: '',
        trangThaiTaiSanId: null,
        congSuatThietKe: null,
        trangThaiVanHanhId: null,
        icons: '',
        soCongNguonDauVao: null,
        hangSanXuatId: null,
        namSanXuat: new Date().getFullYear(),
        noiSanXuatId: null,
        soCongNguonDauRa: null,
        ghiChu: '',
        thietBiChaId: null,
        thanhPhanThietBiIds: [],
        nhaCungCapId: null,
        phamViIdcId: null,
        lapDatTaiIdcId: null,
        toaNhaId: null,
        matSanId: null,
        phongZoneId: null,
        rackId: null,
        uTrenId: null,
        uDuoiId: null,
        // Đặc tính kỹ thuật mới
        chieuDai: null,
        chieuRong: null,
        chieuCao: null,
        congSuatThucTe: null,
        loaiNguonDien: null,
        loaiMay: null,
        heSoCosPhi: null,
        tyLeSuDung: null,
        dongDienThucTe: null,
        cauHinhHeThong: '',
        maDuAn: '',
        theTaiSan: '',
        idChiTietTS: null,
        maVatTu: '',
        tenDuAn: '',
        ngayDKKhauHao: '',
        hanBaoHanh: '',
        ngayLapDat: '',
        ngaySuDung: '',
        donViQuanLy: null,
        caNhanQuanLy: null,
        phongToQuanLy: null,
        emailSDT: '',
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

        // Lưu dữ liệu chính (tạo mới hoặc cập nhật)
        if (Object.keys(aggregate).length) {
          const savedId = await api.upsertThietBiIDC(this.axios, aggregate)
          console.log('savedId', savedId)
          this.currentItem.id = savedId
        }
        if (methodTasks.length) {
          await this.executeMethodTasks(methodTasks)
        }

        this.$root.toastSuccess('Lưu thiết bị thành công!')

        // Chuyển về chế độ VIEW sau khi lưu thành công
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
        const response = await api.getChiTietThietBiIDC(this.axios, itemId)

        if (response) {
          this.currentItem = this.convertApiDataToCurrentItem(response)
          // Đảm bảo mode là VIEW khi load chi tiết
          if (this.currentMode !== 'ADD') {
            this.currentMode = 'VIEW'
          }
        } else {
          throw new Error('Thiết bị không tồn tại')
        }
      } catch (error) {
        this.$root.toastError(error.message || 'Thiết bị không tồn tại')
        this.$router.push({
          path: this.$parent.deviceUrls.listUrl
        })
      } finally {
        this.$root.showLoading(false)
      }
    },
    convertApiDataToCurrentItem: function(apiData = {}) {
      const parseJsonArray = (value, fallback = []) => {
        if (!value) {
          return fallback
        }
        if (Array.isArray(value)) {
          return value
        }
        try {
          return JSON.parse(value)
        } catch (error) {
          return fallback
        }
      }

      return {
        id: apiData.ID ?? null,
        donViQuanLyId: apiData.DONVIQLY_ID ?? null,
        nguoiQuanLyId: apiData.NGUOIQLY_ID ?? null,
        mangThietBiId: apiData.MANGTBI_ID ?? null,
        loaiThietBiId: apiData.LOAITBI_ID ?? null,
        phanLoaiThietBiId: apiData.PHANLOAITBI_ID ?? null,
        chungLoaiThietBiId: apiData.CHUNGLOAITBI_ID ?? null,
        soHuuThietBiId: apiData.CHUQUAN_ID ?? null,
        heThongId: apiData.HETHONGTBI_ID ?? null,
        tenThietBi: apiData.TEN ?? null,
        maThietBi: apiData.MA_TBI ?? null,
        kyHieuThietBi: apiData.KYHIEU ?? null,
        thanhPhanThietBiIds: parseJsonArray(apiData.THANHPHAN_TBI_ID),
        dongSanPham: apiData.MODEL ?? null,
        serialNumber: apiData.SERIAL_NUMBER ?? null,
        thoiDiemLapDat: apiData.NGAY_LAPDAT ?? null,
        ngayDuaVaoSuDung: apiData.NGAY_SUDUNG ?? null,
        maTheTaiSan: apiData.MA_TS ?? null,
        tagNumber: apiData.KYHIEU ?? null,
        trangThaiTaiSanId: apiData.TRANGTHAI_TS_ID ?? null,
        trangThaiVanHanhId: apiData.TRANGTHAIVH_ID ?? null,
        congSuatThietKe: apiData.CS_DINHDANH ?? null,
        congSuatDinhDanh: apiData.CS_DINHDANH ?? null,
        congSuatThucTe: apiData.CS_THUCTE ?? null,
        dienApDinhDanh: apiData.DA_DINHDANH ?? null,
        dongDienDinhDanh: apiData.DD_DINHDANH ?? null,
        dongDienThucTe: apiData.DD_THUCTE ?? null,
        loaiNguonDien: apiData.LOAI_NDIEN ?? null,
        loaiMay: apiData.LOAIMAY ?? null,
        heSoCosPhi: apiData.HS_COSPHI ?? null,
        tyLeSuDung: apiData.TYLE_SD ?? null,
        icons: apiData.ICON_PATH ?? null,
        soCongNguonDauVao: apiData.SO_PORT_CONSOLE ?? null,
        soCongNguonDauRa: apiData.SO_PORT_INTERFACE ?? null,
        hangSanXuatId: apiData.HANGSX_ID ?? null,
        nhaCungCapId: apiData.NHACC_ID ?? null,
        noiSanXuatId: apiData.NHASX_ID ?? null,
        namSanXuat: apiData.NAMSX ?? null,
        ghiChu: apiData.GHI_CHU ?? null,
        thietBiChaId: apiData.THIETBICHA_ID ?? null,
        phamViIdcId: apiData.IDC_ID ?? null,
        lapDatTaiIdcId: apiData.LAPDAT_IDC_ID ?? null,
        toaNhaId: apiData.TOANHA_ID ?? null,
        matSanId: apiData.MATSAN_ID ?? null,
        phongZoneId: apiData.PHONG_ID ?? null,
        rackId: apiData.IDC_RACK_ID ?? null,
        uTrenId: apiData.UP_UNIT_ID ?? null,
        uDuoiId: apiData.DOWN_UNIT_ID ?? null,
        chieuDai: apiData.DAI ?? null,
        chieuRong: apiData.RONG ?? null,
        chieuCao: apiData.CAO ?? null,
        khongGianLapDat: apiData.SL_RACKUNIT ?? null,
        kieuLapDatId: apiData.KIEU_LD ?? null,
        loaiKhachHangId: apiData.LOAI_KHACH_HANG ?? apiData.LOAIKHACHHANG ?? null,
        soHuuThietBiId: apiData.CHUQUAN_ID ?? null,
        hieuLuc: apiData.HIEU_LUC ?? 1,
        fullDepth: apiData.FULL_DEPTH ?? 0,
        cauHinhHeThong: apiData.CAU_HINH ?? '',
        danhSachOCam: parseJsonArray(apiData.DANHSACH_OCAM, [])
      }
    },
    setDefaultDonViQuanLyId: async function() {
      const donViId = await this.$root.token.getDonViID()
      if (donViId && !this.currentItem.donViQuanLyId) {
        this.$set(this.currentItem, 'donViQuanLyId', donViId)
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
