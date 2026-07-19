<template src="./subnet-con.html"> </template>

<script>
import AddSubnetConModal from './add-subnetcon-modal/AddSubnetConModal.vue'
import ViewEditSubnetConModal from './view-edit-subnetcon-modal/ViewEditSubnetConModal.vue'

export default {
  name: 'SubnetConTab',

  components: {
    AddSubnetConModal,
    ViewEditSubnetConModal
  },

  data() {
    return {
      columns: [
        { fieldName: 'stt', headerText: 'STT', width: 80 },
        { fieldName: 'tenSubnet', headerText: 'Tên Subnet', width: 150 },
        { fieldName: 'subnet', headerText: 'Subnet', width: 140 },
        { fieldName: 'gateWay', headerText: 'gateWay', width: 140 },
        { fieldName: 'haTangMang', headerText: 'Hạ tầng mạng', width: 150 },
        { fieldName: 'trangThaiCcdv', headerText: 'Trạng thái CCDV', width: 180 },
        {
          field: 'actions',
          headerText: 'Thao tác',
          textAlign: 'Center',
          width: 120,
          template: this.actionColumn(this)
        }
      ],
      dataSource: [
        {
          stt: 1,
          tenSubnet: 'Net_Srv_01',
          subnet: '10.10.1.0',
          gateway: '10.10.1.1',
          vlan: 'VLAN10',
          haTangMang: 'HTM_beo01',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 2,
          tenSubnet: 'Net_Srv_02',
          subnet: '10.10.2.0',
          gateway: '10.10.2.1',
          vlan: 'VLAN20',
          haTangMang: 'HTM_beo02',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 3,
          tenSubnet: 'Net_Srv_03',
          subnet: '10.10.3.0',
          gateway: '10.10.3.1',
          vlan: 'VLAN30',
          haTangMang: 'HTM_beo03',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 4,
          tenSubnet: 'Net_Srv_04',
          subnet: '10.20.1.0',
          gateway: '10.20.1.1',
          vlan: 'VLAN15',
          haTangMang: 'HTM_beo05',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 5,
          tenSubnet: 'Net_Srv_05',
          subnet: '172.16.1.0',
          gateway: '172.16.1.1',
          vlan: 'VLAN40',
          haTangMang: 'HTM_beo07',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 6,
          tenSubnet: 'Net_Srv_06',
          subnet: '192.168.10.0',
          gateway: '192.168.10.1',
          vlan: 'VLAN50',
          haTangMang: 'HTM_beo09',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        }
      ],
      showAddSubnetConModal: false,
      showViewEditSubnetConModal: false,
      modalMode: 'VIEW',
      selectedItem: {}
    }
  },

  props: {
    mode: {
      type: String,
      default: 'VIEW'
    },

    subnetConData: {
      type: Array
    }
  },

  computed: {
    isViewMode() {
      return this.mode === 'VIEW'
    },
    isEditMode() {
      return this.mode === 'EDIT'
    }
  },

  methods: {
    addSubnet(mode, row) {
      this.modalMode = mode
      this.selectedItem = { ...row }
      this.showAddSubnetConModal = true
    },
    onSaveSubnet(edited) {
      this.showAddSubnetConModal = false
    },

    // View and Edit
    viewItem(mode, row) {
      this.modalMode = mode
      this.selectedItem = { ...row }
      this.showViewEditSubnetConModal = true
    },

    editItem(row) {
      this.modalMode = 'EDIT'
      this.selectedItem = { ...row }
      this.showViewEditSubnetConModal = true
    },

    async deleteRow(row) {
      const confirmed = await this.$confirm(`Bạn có chắc chắn muốn xóa  bản ghi đã chọn?`, 'Xác nhận xóa', {
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
        type: 'warning'
      })
    },

    onActionClick(e) {
      if (e.action === 'view') this.addSubnet('VIEW', e.row)
      if (e.action === 'edit') this.editItem('EDIT', e.row)
      if (e.action === 'delete') this.deleteRow(e.row)
    },
    actionColumn(parent) {
      return function() {
        return {
          template: {
            template: `
              <div class="action-buttons">
               <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết" @click="viewItem()">
                <i class="fa fa-eye"></i>
               </button>

                <button class="btn btn-sm btn-outline-primary ml-1" title="Chỉnh sửa" @click="editItem()">
                <i class="fa fa-edit"></i>
                </button>

                <button class="btn btn-sm btn-outline-danger ml-1" title="Xóa" @click="deleteItem()">
                <i class="fa fa-trash"></i>
                </button>
              </div>`,

            methods: {
              editItem() {
                parent.editItem(this.data)
              },

              viewItem() {
                parent.viewItem('VIEW', this.rowData)
              },
              deleteItem() {
                parent.deleteRow(this.rowData)
              }
            }
          }
        }
      }
    }
  }
}
</script>
