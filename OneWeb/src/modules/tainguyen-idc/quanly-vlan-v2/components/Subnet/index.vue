<template src="./index.html"></template>
<script>
import api from '../../api/vlan'
import ChiTietSubnet from './components/ChiTietSubnet/index.vue'
import DanhSachSubnet from './components/DanhSachSubnet/index.vue'
import { listOfFields, defaultItem } from './fields'

export default {
  name: 'Subnet',
  props: {
    vlanId: {
      type: [Number, String],
      required: false
    }
  },
  components: {
    ChiTietSubnet,
    DanhSachSubnet
  },
  data() {
    return {
      mode: 'view',
      currentItem: { ...defaultItem },
      loading: false,
      isModalOpen: false,
      tab_index: 1,
      grid: {
          dataItems: [],
          columns: [            
          {fieldName: 'subnet_id', headerText: 'ID Subnet', textAlign: 'Left', width: '100px'},
          {fieldName: 'ten_subnet', headerText: 'Tên Subnet', textAlign: 'Left', width: '150px'},
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
      },
      tempSelectedSubnet: [],
      savedSubnet: []
    }
  },
  async created() {
    await this.fetchListSubnet()
    this.savedSubnet = [...this.grid.dataItems]
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
    async onSubnetSelected (selectedItems) {
      this.isModalOpen = false
      if (this.vlanId) {
        console.log('lưu vào vlan', selectedItems)
        await this.insertBatchSubnet(this.vlanId, selectedItems)
      } else {
        // tạo id subnet_vlan tạm thời
        const maxId = this.grid.dataItems.reduce((max, item) => (item.id > max ? item.id : max), 0)
        selectedItems = selectedItems.map((item, index) => ({
          ...item,
          id: maxId + index + 1
        }))
        // lưu vào danh sách tạm thời
        this.tempSelectedSubnet = [...selectedItems]
        this.grid.dataItems = [
          ...this.savedSubnet,
          ...this.tempSelectedSubnet
        ]
        this.$emit('on-temp-subnet-selected', this.tempSelectedSubnet)
      }
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
                          <!--
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
                        -->
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
    // onViewAttribute (row) {
    //   this.mode = 'view'
    //   this.currentItem = { ...defaultItem, ...row }
    //   this.isModalOpen = true
    // },
    // onEditAttribute (row) {
    //   this.mode = 'edit'
    //   this.currentItem = { ...defaultItem, ...row }
    //   this.isModalOpen = true
    // },
    async onDeleteAttribute (row) { 
      if (!row) return
      if (this.vlanId) {
        // xóa khỏi vlan
        const confirmed = await this.$confirm(
          `Bạn có chắc chắn muốn gỡ Subnet khỏi VLAN này?`,
          'Xác nhận gỡ',
          { confirmButtonText: 'Gỡ', cancelButtonText: 'Hủy', type: 'warning' }
        )
        if (confirmed) {
          const id = row?.id || null
          await this.deleteSubnet(id)
        }
      } else {        
        // xóa trong danh sách tạm thời
        this.tempSelectedSubnet = this.tempSelectedSubnet.filter(item => item.id !== row.id)
        this.grid.dataItems = [
          ...this.savedSubnet,
          ...this.tempSelectedSubnet
        ]
        this.$emit('on-temp-subnet-selected', this.tempSelectedSubnet)
      }

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
    preparePayload (vlanId, selectedItems) {
      const ids = selectedItems.map(item => item.subnet_id)

      return {
        vlanId: vlanId,
        subnetList: ids
      }
    },
    async fetchListSubnet() {
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
    async insertBatchSubnet (vlanId, selectedItems) {
      this.$root.showLoading(true)
      try {
        const payload = this.preparePayload(vlanId, selectedItems)
        console.log('payload', payload)
        await api.insertBatchSubnetVlan(this.$root.context, payload)

        await this.fetchListSubnet()
        this.$toast.success('Thêm Subnet vào VLAN thành công.')
      } catch (error) {
        this.$toast.error(error.message || 'Lỗi khi thêm Subnet vào VLAN')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async deleteSubnet (id) {
      if (!id) return
      this.$root.showLoading(true)
      try {
        await api.deleteSubnetVlan(this.$root.context, id)
        await this.fetchListSubnet()

        this.$toast.success(`Gỡ thành công subnet khỏi VLAN`)
      } catch (error) {
        this.$toast.error(error.message || 'Gỡ Subnet thất bại.')
      } finally {
        this.$root.showLoading(false)
      }
    }
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
