<template src="./index.html"></template>
<script>
import api from './api/vlan'
import danhmuc from './api/danhmuc'
import PopupChiTiet from './components/PopupChiTiet.vue'
import { listOfFields, defaultItem, searchParams as defaultSearchParams } from './fields'
import { buildController, createControls, rules as R } from './controls'

export default {
  name: 'QuanLyVLANV2',
  components: {
    PopupChiTiet
  },
  data () {
    return {
      mode: 'view',
      searchParams: { ...defaultSearchParams },
      dropdowns: {
        idcs: [],
        vrfs: [],
        zones: [],
        devices: []
      },
      currentItem: { ...defaultItem },
      isModalOpen: false,
      grid: {
        dataItems: [],
        columns: [
          { fieldName: 'id', headerText: 'ID VLAN', textAlign: 'Left', width: '100px' },
          { fieldName: 'ten', headerText: 'Tên VLAN', textAlign: 'Left' },
          { fieldName: 'ten_idc', headerText: 'IDC', textAlign: 'Left' },
          // { fieldName: 'ten_thietbi', headerText: 'Thiết bị mạng', textAlign: 'Left' },
          // { fieldName: 'hatang_id', headerText: 'Hạ tầng', textAlign: 'Left' }, // chưa có data
          { fieldName: 'ten_vrf', headerText: 'VRF', textAlign: 'Left' },
          { fieldName: 'ten_zone', headerText: 'Zone', textAlign: 'Left' },
          { fieldName: 'ten_hieu_luc', headerText: 'Hiệu lực', textAlign: 'Left', width: '100px' },
          { fieldName: 'action', headerText: 'Thao tác', textAlign: 'Center', template: this.actionTemplate() },
        ]
      }
    }
  },
  computed: {
    // get selected rows from grid
    selectedRows () {
      const gridRef = this.$refs.gridItems
      if (gridRef && gridRef.$refs && gridRef.$refs.grid) {
        return gridRef.$refs.grid.getSelectedRecords()
      }
      return []
    }
  },
  async created () {
    Promise.all([
      this.loadDanhMucMaster(),
      this.fetchListData()
    ])
  },
  mounted () { 
    this.dropdowns.hieuluc = [
      { id: -1, ten: 'Tất cả' },
      { id: 1, ten: 'Có hiệu lực' },
      { id: 0, ten: 'Không hiệu lực' },
    ]
  },
  methods: {
    /**
     * Các hàm fetch data từ API
     */

    async loadDanhMucMaster () {
      this.$root.showLoading(true)
      try {
        const promise = []

        promise.push(this.fetchIdcOptions())
        promise.push(this.fetchDevice())
        if (this.searchParams?.idc_id) {
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

    // idc
    async fetchIdcOptions () {
      try {
        // Lấy phân vùng của user hiện tại
        const raw = await danhmuc.getIdcOptions(this.$root.context)
        this.dropdowns.idcs = this.normalizeOptions(raw)
        this.dropdowns.idcs.unshift({ id: 0, ten: 'Tất cả' })
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách IDC')
      }
    },
    // vrf -> phụ thuộc idc
    async fetchVrfOptions () {
      try {
        const raw = await danhmuc.getVrfByInfrastructure(this.$root.context, this.searchParams.idc_id)
        this.dropdowns.vrfs = this.normalizeOptions(raw)
        this.dropdowns.vrfs.unshift({ id: 0, ten: 'Tất cả' })
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách VRF')
      }
    },
    // zone -> phụ thuộc idc
    async fetchZoneOptions () {
      try {
        const raw = await danhmuc.getZoneByVrf(this.$root.context, this.searchParams.idc_id)
        this.dropdowns.zones = this.normalizeOptions(raw)
        this.dropdowns.zones.unshift({ id: 0, ten: 'Tất cả' })
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải danh sách Zone')
      }
    },
    // thiết bị -> lấy theo vlan
    async fetchDevice () {
      try {
        const raw = await danhmuc.getNetworkDevicesByInfrastructure(this.$root.context, this.searchParams.idc_id)
        this.dropdowns.devices = this.normalizeOptions(raw)
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
    onIdcChange () {
      // reset vrf, zone, device
      this.searchParams.vrf_id = null
      this.searchParams.zone_id = null
      this.searchParams.device_id = null

      // fetch new vrf, zone, device
      this.fetchVrfOptions()
      this.fetchZoneOptions()
      this.fetchDevice()
    },

    /**
     * Event handlers
     */

    async onClickSearch () {
      await this.fetchListData()
    },
    onClickNhapMoi () {
      this.mode = 'add'
      this.currentItem = { ...defaultItem }
      this.isModalOpen = true
    },
    onChangeTab (index) {
      this.tab_index = index
    },
    handleModalClose () {
      this.isModalOpen = false
    },

    actionTemplate (parent) {
      const self = this
      return function () {
        return {
          template: {
            template: `
                        <div class="action-buttons gap-1">
                        <button
                            class="btn btn-sm btn-outline-primary ml-1"
                            @click="onView()">
                            <i class="fa fa-eye"></i>
                        </button>
                        <button
                            class="btn btn-sm btn-outline-primary ml-1"
                            @click="onEdit()">
                            <i class="fa fa-pencil"></i>
                        </button>
                        <button
                            class="btn btn-sm btn-outline-danger ml-1"
                            @click="onDelete()">
                            <i class="fa fa-trash"></i>
                        </button>
                        </div>
                    `,
            methods: {
              async onView () {
                self.onViewAttribute(this.data)
              },
              async onEdit () {
                self.onEditAttribute(this.data)
              },
              async onDelete () {
                self.onDeleteAttribute(this.data)
              }
            }
          }
        }
      }
    },
    onViewAttribute (row) {
      console.log('view row', row)
      this.mode = 'view'
      this.currentItem = { ...defaultItem, ...row }
      this.isModalOpen = true
    },
    onEditAttribute (row) {
      this.mode = 'edit'
      this.currentItem = { ...defaultItem, ...row }
      this.isModalOpen = true
    },
    async onDeleteAttribute (row) {
      const confirmed = await this.$confirm(
        `Bạn có chắc chắn muốn xóa ${this.selectedRows.length} VLAN đã chọn?`,
        'Xác nhận xóa',
        { confirmButtonText: 'Xóa', cancelButtonText: 'Hủy', type: 'warning' }
      )
      if (confirmed) {
        const id = row?.id || null
        await this.deleteData(id)
      }
    },
    onVlanSaved (savedItem) {
      this.isModalOpen = false
      this.$toast.success('Lưu thông tin VLAN thành công.')
      this.fetchListData()
    },
    /**
     * API calls methods
     */

    preparePayload (item) {
      return {
        ten: item.ten ? item.ten.trim() : '',
        idc_id: item.idc_id ? Number(item.idc_id) : null,
        vrf_id: item.vrf_id ? Number(item.vrf_id) : null,
        zone_id: item.zone_id ? Number(item.zone_id) : null,
        hieu_luc: item.hieu_luc ? Number(item.hieu_luc) : null,
      }
    },
    async fetchListData () {
      this.$root.showLoading(true)
      try {
        const params = this.preparePayload(this.searchParams)
        const res = await api.getDanhSach(this.$root.context, params)

        this.grid.dataItems = Array.isArray(res) ? res : []
      } catch (error) {
        this.$toast.error(error.message || 'Lỗi khi lấy danh sách VLAN')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async deleteData (id) {
      if (!id) return
      this.$root.showLoading(true)
      try {
        await api.delete(this.$root.context, id)
        await this.fetchListData()

        this.$toast.success('Xóa VLAN thành công.')
      } catch (error) {
        this.$toast.error(error.message || 'Xóa VLAN thất bại.')
      } finally {
        this.$root.showLoading(false)
      }
    },
  }
}
</script>
