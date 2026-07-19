<template src="./ip-store.html"> </template>

<script>
import AddIpStoreModal from './add-ipstore-modal/AddIpStoreModal.vue'
import ViewEditIpStoreModal from './view-edit-ipstore-modal/ViewEditIpStoreModal.vue'

export default {
  name: 'IpStoreIpStoreTab',

  components: {
    ViewEditIpStoreModal,
    AddIpStoreModal
  },

  data() {
    return {
      columns: [
        { fieldName: 'IP_ID', headerText: 'STT', width: 60 },
        { fieldName: 'DIACHI_IP', headerText: 'IP', width: 150 },
        { fieldName: 'SUBNET', headerText: 'Subnet', width: 140 },
        { fieldName: 'MUC_DICH_SD', headerText: 'Mục đích sử dụng', width: 150 },
        { fieldName: 'TT_CCDV', headerText: 'Trạng thái CCDV', width: 180 },
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
          ip: '10.10.1.2',
          loaiIp: 'IPv4',
          phanLoaiIp: 'Private',
          IpStore: '10.10.1.0/24',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 2,
          ip: '10.10.1.3',
          loaiIp: 'IPv4',
          phanLoaiIp: 'Private',
          IpStore: '10.10.1.0/24',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 3,
          ip: '10.10.2.5',
          loaiIp: 'IPv4',
          phanLoaiIp: 'Private',
          IpStore: '10.10.2.0/24',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 4,
          ip: '10.20.1.8',
          loaiIp: 'IPv4',
          phanLoaiIp: 'Public',
          IpStore: '10.20.1.0/24',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 5,
          ip: '172.16.1.15',
          loaiIp: 'IPv4',
          phanLoaiIp: 'Private',
          IpStore: '172.16.1.0/24',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        },
        {
          stt: 6,
          ip: '192.168.10.22',
          loaiIp: 'IPv4',
          phanLoaiIp: 'Private',
          IpStore: '192.168.10.0/24',
          trangThaiCcdv: 'đã cung cấp / chưa cung cấp',
          actions: ''
        }
      ],
      showAddIpStoreModal: false,
      showViewEditIpStoreModal: false,
      modalMode: 'VIEW',
      selectedItem: {}
    }
  },

  props: {
    mode: {
      type: String,
      default: 'VIEW'
    },

    danhSachIpData: {
      type: Array
    }
  },

  methods: {
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
    },

    onActionClick(e) {
      if (e.action === 'view') this.addSubnet('VIEW', e.row)
      if (e.action === 'edit') this.editItem('EDIT', e.row)
      if (e.action === 'delete') this.deleteRow(e.row)
    },

    addIpStore(mode, row) {
      this.modalMode = mode
      this.selectedItem = { ...row }
      this.showAddIpStoreModal = true
    },
    onSaveIpStore(edited) {
      this.showAddIpStoreModal = false
    },

    async deleteRow(row) {
      const confirmed = await this.$confirm(`Bạn có chắc chắn muốn xóa  bản ghi đã chọn?`, 'Xác nhận xóa', {
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
        type: 'warning'
      })
    },

    // View and Edit
    viewItem(mode, row) {
      this.modalMode = mode
      this.selectedItem = { ...row }
      this.showViewEditIpStoreModal = true
    },

    editItem(row) {
      this.modalMode = 'EDIT'
      this.selectedItem = { ...row }
      this.showViewEditIpStoreModal = true
    }
  }
}
</script>
