<template src="./index.html"></template>
<script>
import { listOfFields, columnsOfGrid, defaultItem, searchParams as defaultSearchParams } from '../../fields'
import { buildController, createControls, rules as R } from '../../controls'
import ItemSelectorModal from './components/ItemSelectorModal.vue'
import api from '../../api/danhmuc'

export default {
  name: 'ThongTinChung',
  components: {
    ItemSelectorModal
  },
  props: {
    currentItem: {
      type: Object,
      default: () => {}
    },
    mode: {
      type: String,
      default: 'view'
    }
  },
  data() {
    return {
      controls: createControls(Object.keys(listOfFields)),
      dropdowns: {
        idcs: [],
        vrfs: [],
        zones: [],
        devices: [],
        infrastructures: []
      },
      dropdownFields: { value: 'id', text: 'ten' }
    }
  },
  created() {
    this.controlsController = buildController(this.controls, {
      ten: [R.required(), R.maxLength(255)],
      idc_id: [R.required()],
      thietbi_id: [R.required()],
      hatang_id: [R.required()],
      vrf_id: [R.required()],
      zone_id: [R.required()],
      ghi_chu: [R.maxLength(500)]
    })
    this.applyMode(this.mode)
  },
  async mounted() {
    await this.loadDanhMucMaster()
  },
  methods: {
    async loadDanhMucMaster() {
      try {
        this.setGlobalLoading(true)
        const promise = []

        promise.push(this.fetchIdcOptions())
        if (this.currentItem?.idc_id) {
          promise.push(this.fetchInfrastructureOptions())
        }
        if (this.hasSelectedId(this.currentItem?.hatang_id)) {
          promise.push(this.fetchDeviceOptions())
          promise.push(this.fetchVrfOptions())
        }
        if (this.hasSelectedId(this.currentItem?.vrf_id)) {
          promise.push(this.fetchZoneOptions())
        }
        await Promise.all(promise)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải dữ liệu ban đầu')
      } finally {
        this.setGlobalLoading(false)
      }
    },

    /**
     * Các hàm fetch data từ API
     */

    // idc
    async fetchIdcOptions() {
      try {
        const raw = await api.getIdcOptions(this.$root.context)
        this.dropdowns.idcs = this.normalizeOptions(raw)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách IDC')
      }
    },
    // hạ tầng mạng -> phụ thuộc IDC
    async fetchInfrastructureOptions() {
      try {
        const raw = await api.getInfrastructureByIdc(this.$root.context, this.currentItem.idc_id)
        this.dropdowns.infrastructures = this.normalizeOptions(raw)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách hạ tầng mạng')
      }
    },
    // thiết bị -> phụ thuộc hạ tầng mạng
    async fetchDeviceOptions() {
      const hatangId = this.getSelectedId(this.currentItem.hatang_id)
      if (!hatangId) {
        this.dropdowns.devices = []
        return
      }
      try {
        const raw = await api.getNetworkDevicesByInfrastructure(this.$root.context, hatangId)
        this.dropdowns.devices = this.normalizeOptions(raw)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách thiết bị mạng')
      }
    },
    // vrf -> phụ thuộc hạ tầng mạng
    async fetchVrfOptions() {
      const hatangId = this.getSelectedId(this.currentItem.hatang_id)
      if (!hatangId) {
        this.dropdowns.vrfs = []
        return
      }
      try {
        const raw = await api.getVrfByInfrastructure(this.$root.context, hatangId)
        this.dropdowns.vrfs = this.normalizeOptions(raw)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách VRF')
      }
    },
    // zone -> phụ thuộc vrf
    async fetchZoneOptions() {
      const vrfId = this.getSelectedId(this.currentItem.vrf_id)
      if (!vrfId) {
        this.dropdowns.zones = []
        return
      }
      try {
        const raw = await api.getZoneByVrf(this.$root.context, vrfId)
        this.dropdowns.zones = this.normalizeOptions(raw)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách Zone')
      }
    },
    normalizeOptions(list) {
      if (!Array.isArray(list)) return []

      const res = list.map((item) =>
        Object.fromEntries(Object.entries(item).map(([key, value]) => [key.toLowerCase(), value]))
      )
      return res
    },
    async onIdcSelected(args) {
      this.currentItem.hatang_id = []
      this.currentItem.thietbi_id = []
      this.currentItem.vrf_id = []
      this.currentItem.zone_id = []
      this.dropdowns.infrastructures = []
      this.dropdowns.devices = []
      this.dropdowns.vrfs = []
      this.dropdowns.zones = []

      if (this.currentItem.idc_id) {
        await this.fetchInfrastructureOptions()
      }
    },
    async onInfrastructureSelected(selectedItems = []) {
      this.currentItem.hatang_id = Array.isArray(selectedItems) ? [...selectedItems] : []
      this.currentItem.thietbi_id = []
      this.currentItem.vrf_id = []
      this.currentItem.zone_id = []
      this.dropdowns.devices = []
      this.dropdowns.vrfs = []
      this.dropdowns.zones = []

      if (this.hasSelectedId(this.currentItem.hatang_id)) {
        await Promise.all([this.fetchDeviceOptions(), this.fetchVrfOptions()])
      }
    },
    onThietBiSelected(selectedItems = []) {
      this.currentItem.thietbi_id = Array.isArray(selectedItems) ? [...selectedItems] : []
    },
    async onVrfSelected(selectedItems = []) {
      this.currentItem.vrf_id = Array.isArray(selectedItems) ? [...selectedItems] : []
      this.currentItem.zone_id = []
      this.dropdowns.zones = []

      if (this.hasSelectedId(this.currentItem.vrf_id)) {
        await this.fetchZoneOptions()
      }
    },
    onZoneSelected(selectedItems = []) {
      this.currentItem.zone_id = Array.isArray(selectedItems) ? [...selectedItems] : []
    },

    /**
     * event handlers
     */
    async onClickSearch() {},

    async onClickSave() {
      if (this.validateControls()) {
        await this.upsertData()
      }
    },
    async onClickDelete() {
      if (this.selectedRows.length === 0) {
        this.$toast.error('warning', 'Vui lòng chọn ít nhất một VLAN để xóa.')
        return
      }
      const confirmed = await this.$confirm(
        `Bạn có chắc chắn muốn xóa ${this.selectedRows.length} VLAN đã chọn?`,
        'Xác nhận xóa',
        { confirmButtonText: 'Xóa', cancelButtonText: 'Hủy', type: 'warning' }
      )
      if (confirmed) {
        const ids = this.selectedRows.map((row) => row.id).filter((id) => id !== null && id !== undefined)
        await this.deleteMultipleData(ids)
      }
    },

    async upsertData() {
      try {
        this.setGlobalLoading(true)
        const payload = this.preparePayloadForSave(this.currentItem)
        await api.upsert(this.$root.context, payload)
        await this.fetchListData()

        this.$toast.error('success', 'Ghi lại thông tin VLAN thành công.')
      } catch (error) {
        this.$toast.error(error.message || 'Lưu thông tin VLAN thất bại.')
      } finally {
        this.setGlobalLoading(false)
      }
    },
    async deleteMultipleData(ids = []) {
      if (!ids.length) return
      try {
        this.setGlobalLoading(true)
        const promise = ids.map((id) => api.delete(this.$root.context, id))
        await Promise.all(promise)
        await this.fetchListData()

        this.$toast.error('success', 'Xóa VLAN thành công.')
      } catch (error) {
        this.$toast.error(error.message || 'Xóa VLAN thất bại.')
      } finally {
        this.setGlobalLoading(false)
      }
    },

    /**
     * helper functions
     */
    // prepare payload for save
    preparePayloadForSave(item) {
      return {
        id: item.id,
        ten: item.ten ? item.ten.trim() : '',
        idc_id: item.idc_id,
        thietbi_id: this.getSelectedId(item.thietbi_id),
        hatang_id: this.getSelectedId(item.hatang_id),
        vrf_id: this.getSelectedId(item.vrf_id),
        zone_id: this.getSelectedId(item.zone_id),
        ghi_chu: item.ghi_chu ? item.ghi_chu.trim() : '',
        hieu_luc: item.hieu_luc
      }
    },
    validateControls() {
      const normalized = {
        ...this.currentItem,
        thietbi_id: this.getSelectedId(this.currentItem.thietbi_id),
        hatang_id: this.getSelectedId(this.currentItem.hatang_id),
        vrf_id: this.getSelectedId(this.currentItem.vrf_id),
        zone_id: this.getSelectedId(this.currentItem.zone_id)
      }
      const { isValid, errors } = this.controlsController.validate(normalized)
      if (!isValid) {
        Object.keys(errors || {}).forEach((field) => {
          const messages = errors[field]
          if (messages && messages.length > 0) {
            const fieldLabel = this.listOfFields[field] || field
            this.$toast.error('Lỗi trường ', `${fieldLabel}: ${messages[0]}`)
          }
        })
      }
      return isValid
    },
    applyMode(mode) {
      if (!['view', 'add', 'edit'].includes(mode)) return

      if (mode === 'add' || mode === 'edit') {
        this.controlsController.setEnabledAll(true)
      } else if (mode === 'view') {
        this.controlsController.setEnabledAll(false)
      }
    },
    setGlobalLoading(state) {
      if (this.$root && typeof this.$root.loading === 'function') {
        this.$root.loading(state)
      }
    },
    getSelectedId(selection) {
      if (Array.isArray(selection) && selection.length > 0) {
        const first = selection[0]
        if (first && typeof first === 'object') {
          return first.id ?? first.ID ?? first.value ?? null
        }
        return first ?? null
      }
      if (selection && typeof selection === 'object') {
        return selection.id ?? selection.ID ?? null
      }
      return selection ?? null
    },
    hasSelectedId(selection) {
      const id = this.getSelectedId(selection)
      return id !== undefined && id !== null && id !== ''
    },
  }
}
</script>
