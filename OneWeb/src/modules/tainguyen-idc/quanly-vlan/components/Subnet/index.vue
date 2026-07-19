<template src="./index.html"></template>
<script>
import api from '../../api/vlan'
import ChiTietSubnet from './components/ChiTietSubnet.vue'
import { listOfFields, defaultItem } from './fields'

export default {
  name: 'Subnet',
  props: {
    vlanId: {
      type: [Number, String],
      required: true
    }
  },
  components: {
    ChiTietSubnet
  },
  data() {
    return {
      mode: 'view',
      currentItem: { ...defaultItem },
      loading: false,
      isModalOpen: false,
      tab_index: 1,
      grid: {
        // mock data
          dataItems: [
            // {
            //     "id": 101,
            //     "ten": "subnet-01",
            //     "ip_loai_id": 1,
            //     "ip_phanloai_id": 2,
            //     "subnet": "10.10.1.0",
            //     "subnet_mask_id": 24,
            //     "gateway": "10.10.1.1",
            //     "trangthai_id": 1,
            //     "ghi_chu": "subnet cho phong IT",
            //     "hieu_luc": 1
            // },
        ],
          columns: [            
          {fieldName: 'ten', headerText: 'Tên Subnet', textAlign: 'Left', width: '150px'},
          {fieldName: 'ten_loai_ip', headerText: 'Loại IP', textAlign: 'Left', width: '100px'},
          {fieldName: 'ten_phan_loai_ip', headerText: 'Phân loại IP', textAlign: 'Left', width: '150px'},
          {fieldName: 'subnet', headerText: 'Subnet', textAlign: 'Left', width: '100px'},
          {fieldName: 'subnetmask', headerText: 'Subnet mask', textAlign: 'Left', width: '150px'},
          {fieldName: 'gateway', headerText: 'Gateway', textAlign: 'Left', width: '100px'},
          {fieldName: 'trangthai_id', headerText: 'Trạng thái CCDV', textAlign: 'Left', width: '150px'},
          // {fieldName: 'ghi_chu', headerText: 'Ghi chú', textAlign: 'Left', width: '100px'},
          {fieldName: 'hieu_luc', headerText: 'Hiệu lực', textAlign: 'Left', width: '100px'},
          {fieldName: 'action', headerText: 'Thao tác', textAlign: 'Center', template: this.actionTemplate()},
        ] 
      }
    }
  },
  async created() {
    await this.fetchListData()
  },
  methods: {
    /**
     * Event handlers
     */

    onAddSubnet () {
      this.mode = 'add'
      this.currentItem = { ...defaultItem }
      this.isModalOpen = true
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
      this.loading = true
      try {
        const res = await api.getSubnetByVLan(this.$root.context, this.vlanId)
        this.grid.dataItems = Array.isArray(res) ? res : []
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
.select-wrapper {
    display: flex;
    justify-content: flex-end;
}

</style>
