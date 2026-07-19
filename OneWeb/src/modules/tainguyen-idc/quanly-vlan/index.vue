<template src="./index.html"></template>
<script>
import api from './api/vlan'
import PopupChiTiet from './components/PopupChiTiet.vue'
import { listOfFields, defaultItem, searchParams as defaultSearchParams } from './fields'
import { buildController, createControls, rules as R } from './controls'

export default {
  name: 'QuanLyVLAN',
  components: {
    PopupChiTiet
  },
  data() {
    return {
      mode: 'view',
      searchParams: { ...defaultSearchParams },
      currentItem: { ...defaultItem },
      loading: false,
      isModalOpen: false,
      tab_index: 1,
      grid: {
        // mock data
        dataItems: [
          {
            "id": 302,
            "ten": "vlan-0307-25",
            "idc_id": 21,
            "ten_idc": "idc an đồn",
            "thietbi_id": [{
              id: 293,
              ten: 'router_1206'
            }],
            "hatang_id": [{
              id: 302,
              ten: 'Kho hạ tầng mạng 2'
            }],
            "ten_thietbi": "router_1206",
            "vrf_id": [{
              id: 361,
              ten: 'vrf-0307-25'
            }],
            "ten_vrf": "vrf-0307-25",
            "zone_id": [{
              id: 261,
              ten: 'zone-0307-25'
            }],
            "ten_zone": "zone-0307-25",
            "ghi_chu": "vlan-0307",
            "hieu_luc": 1
          },
          {
            "id": 282,
            "ten": "beo_vlan_1606_2",
            "idc_id": 21,
            "ten_idc": "idc an đồn",
            "thietbi_id": [{
              id: 286,
              ten: 'server1-namlnq2'
            }],
            "ten_thietbi": "server1-namlnq2",
            "hatang_id": [{
              id: 302,
              ten: 'Kho hạ tầng mạng 2'
            }],
            "vrf_id": [{
              id: 323,
              ten: 'demo_vrf'
            }],
            "ten_vrf": "demo_vrf",
            "zone_id": [{
              id: 242,
              ten: 'demo_zone_1606'
            }],
            "ten_zone": "demo_zone_1606",
            "ghi_chu": "demo 1606",
            "hieu_luc": 1
          },
        ],
        columns: [
          {fieldName: 'id', headerText: 'ID VLAN', textAlign: 'Left', width: '100px'},
          {fieldName: 'ten', headerText: 'Tên VLAN', textAlign: 'Left'},
          {fieldName: 'ten_idc', headerText: 'IDC', textAlign: 'Left'},
          {fieldName: 'ten_thietbi', headerText: 'Thiết bị mạng', textAlign: 'Left' },
          {fieldName: 'hatang_id', headerText: 'Hạ tầng', textAlign: 'Left'}, // chưa có data
          {fieldName: 'ten_vrf', headerText: 'VRF', textAlign: 'Left'},
          {fieldName: 'ten_zone', headerText: 'Zone', textAlign: 'Left'},
          {fieldName: 'hieu_luc', headerText: 'Hiệu lực', textAlign: 'Left', width: '100px'},
          {fieldName: 'action', headerText: 'Thao tác', textAlign: 'Center', template: this.actionTemplate()},
        ] 
      }
    }
  },
  computed: {
    // get selected rows from grid
    selectedRows() {
      const gridRef = this.$refs.gridItems
      if (gridRef && gridRef.$refs && gridRef.$refs.grid) {
        return gridRef.$refs.grid.getSelectedRecords()
      }
      return []
    }
  },
  async created() {
    await this.fetchListData()
  },
  methods: {
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
    onChangeTab(index) {
      this.tab_index = index
    },
    handleModalClose () {
      this.isModalOpen = false
    },

    actionTemplate(parent) {
      const self = this
      return function() {
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
              async onView() {
                self.onViewAttribute(this.data)
              },
              async onEdit() {
                self.onEditAttribute(this.data)
              },
              async onDelete() {
                self.onDeleteAttribute(this.data)
              }
            }
          }
        }
      }
    },
    onViewAttribute (row) {
      this.mode = 'view'
      this.currentItem = { ...defaultItem, ...row }
      this.isModalOpen = true
    },
    onEditAttribute (row) {
      this.mode = 'edit'
      this.currentItem = { ...defaultItem, ...row }
      this.isModalOpen = true
    },
    onDeleteAttribute (row) { 
      this.$toast.info(`Xóa VLAN: ${row.ten} (Chức năng chưa được triển khai)`)
    },

    /**
     * API calls methods
     */

    // sanitize payload để loại bỏ các trường không có giá trị
    sanitizePayload(payload = {}) {
      const cleaned = {}
      Object.keys(payload || {}).forEach((key) => {
        const value = payload[key]
        const isEmptyString = typeof value === 'string' && value.trim() === ''
        if (value !== null && value !== undefined && value !== '' && !isEmptyString) {
          cleaned[key] = value
        }
      })
      return cleaned
    },
    async fetchListData() {
      try {
        this.loading = true
        const params = this.sanitizePayload(this.searchParams)
        const res = await api.getDanhSach(this.$root.context, params)
        this.dataItems = Array.isArray(res) ? res : []
      } catch (error) {
        this.$toast.error(error.message || 'Lỗi khi lấy danh sách VLAN')
      } finally {
        this.loading = false
      }
    },
  }
}
</script>
<style scoped>
.invalid-box .form-control,
.invalid-box .e-input-group,
.invalid-box .e-control-wrapper {
  border: 1px solid #dc3545 !important;
  border-radius: 4px;
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
