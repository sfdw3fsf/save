<template src="./index.html"></template>
<script>
import { listOfFields, columnsOfGrid, defaultItem, searchParams as defaultSearchParams } from '../../fields'
import { buildController, createControls, rules as R } from '../../controls'
import ItemSelectorModal from './components/ItemSelectorModal.vue'
import danhmuc from '../../api/danhmuc'
import vlan from '../../api/vlan'

export default {
  name: 'ThongTinChung',
  components: {
    ItemSelectorModal
  },
  props: {
    currentItem: {
      type: Object,
      default: () => { }
    },
    mode: {
      type: String,
      default: 'view'
    }
  },
  data () {
    return {
      controls: createControls(Object.keys(listOfFields)),
      dropdownFields: { value: 'id', text: 'ten' },
      dropdowns: {
        idcs: [],
        vrfs: [],
        zones: [],
      },
      listOfFields,
      dataGrid: {
        devices: {
          columns: [
            { fieldName: 'mathietbi', headerText: 'Mã thiết bị', width: '100px' },
            { fieldName: 'ten', headerText: 'Tên thiết bị', width: '180px' },
            { fieldName: 'ten_htm', headerText: 'Thuộc hạ tầng mạng', width: '180px' },
            { fieldName: 'idc_rack', headerText: 'Rack', width: '150px' },
            { fieldName: 'ten_tttvh', headerText: 'Trạng thái VH' }],
          dataItems: []
        },
      }
    }
  },
  created () {
    this.controlsController = buildController(this.controls, {
      ten: [R.required(), R.maxLength(255)],
      idc_id: [R.required()],
      vrf_id: [R.required()],
      zone_id: [R.required()],
      ghi_chu: [R.maxLength(500)]
    })
    this.applyMode(this.mode)
  },
  async mounted () {
    await this.loadDanhMucMaster()
  },
  methods: {
    async loadDanhMucMaster () {
      this.$root.showLoading(true)
      try {
        const promise = []

        promise.push(this.fetchIdcOptions())
        promise.push(this.fetchDevice())
        if (this.currentItem?.idc_id) {
          promise.push(this.fetchVrfOptions())
          promise.push(this.fetchZoneOptions())
        }
        await Promise.all(promise)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải dữ liệu ban đầu')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async upsertData () {
      if (!this.validateControls()) {
        return
      }
      console.log('currentItem before save', this.currentItem)
      this.$root.showLoading(true)
      try {
        const payload = this.preparePayloadForSave(this.currentItem)
        return await vlan.upsert(this.$root.context, payload)

      } catch (error) {
        this.$toast.error(error.message || 'Lưu thông tin VLAN thất bại.')
      } finally {
        this.$root.showLoading(false)
      }
    },

    /**
     * Các hàm fetch data từ API
     */

    // idc
    async fetchIdcOptions () {
      try {
        // Lấy phân vùng của user hiện tại
        const raw = await danhmuc.getIdcOptions(this.$root.context)
        this.dropdowns.idcs = this.normalizeOptions(raw)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách IDC')
      }
    },
    // vrf -> phụ thuộc idc
    async fetchVrfOptions () {
      try {
        const raw = await danhmuc.getVrfByInfrastructure(this.$root.context, this.currentItem.idc_id)
        this.dropdowns.vrfs = this.normalizeOptions(raw)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách VRF')
      }
    },
    // zone -> phụ thuộc idc
    async fetchZoneOptions () {
      try {
        const raw = await danhmuc.getZoneByVrf(this.$root.context, this.currentItem.idc_id)
        this.dropdowns.zones = this.normalizeOptions(raw)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách Zone')
      }
    },
    // thiết bị -> lấy theo vlan
    async fetchDevice () {
      try {
        const raw = await danhmuc.getNetworkDevicesByInfrastructure(this.$root.context, this.currentItem.id)
        this.dataGrid.devices.dataItems = this.normalizeOptions(raw)
        console.log('thiết bị mạng', this.dataGrid.devices.dataItems)
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách thiết bị mạng')
      }
    },

    normalizeOptions (list) {
      if (!Array.isArray(list)) return []

      const res = list.map((item) =>
        Object.fromEntries(Object.entries(item).map(([key, value]) => [key.toLowerCase(), value]))
      )
      return res
    },
    async onIdcSelected (args) {
      this.currentItem.vrf_id = null
      this.currentItem.zone_id = null

      await this.fetchVrfOptions()
      await this.fetchZoneOptions()

    },

    /**
     * helper functions
     */
    // prepare payload for save
    preparePayloadForSave (item) {
      return {
        id: item.id? Number(item.id) : null,
        ten: item.ten ? item.ten.trim() : '',
        idc_id: item.idc_id ? Number(item.idc_id) : null,
        vrf_id: item.vrf_id ? Number(item.vrf_id) : null,
        zone_id: item.zone_id ? Number(item.zone_id) : null,
        ghi_chu: item.ghi_chu ? item.ghi_chu.trim() : '',
        hieu_luc: item.hieu_luc ? Number(item.hieu_luc) : null
      }
    },
    validateControls () {
      const { isValid, errors } = this.controlsController.validate(this.currentItem)
      if (!isValid) {
        Object.keys(errors || {}).forEach((field) => {
          const messages = errors[field]
          if (messages && messages.length > 0) {
            const fieldLabel = this.listOfFields[field] || field
            this.$toast.error(`Trường ${fieldLabel}: ${messages.join(', ')}`)
          }
        })
      }
      return isValid
    },
    applyMode (mode) {
      if (!['view', 'add', 'edit'].includes(mode)) return

      if (mode === 'add' || mode === 'edit') {
        this.controlsController.setEnabledAll(true)
      } else if (mode === 'view') {
        this.controlsController.setEnabledAll(false)
      }
    },
  }
}
</script>
<style scoped>
.invalid-box {
  border: 1px solid #dc3545 !important;
  border-radius: 5px;
}

.invalid-box .e-input-group .e-input,
.invalid-box .e-multi-select-wrapper {
  border: none;
}

.checkbox-inline {
  display: flex;
  align-items: center;
  gap: 8px;
  height: 38px;
}
</style>
