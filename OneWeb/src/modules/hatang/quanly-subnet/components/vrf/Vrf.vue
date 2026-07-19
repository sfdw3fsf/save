<template src="./vrf.html"> </template>

<script>
export default {
  name: 'Vrf',

  components: {},

  data() {
    return {
      columns: [
        { fieldName: 'VRF_ID', headerText: 'ID VRF', width: 60 },
        { fieldName: 'VRF_TEN', headerText: 'TÊN VRF', width: 150 },
        { fieldName: 'ASN', headerText: 'ASN', width: 140 },
        { fieldName: 'RD', headerText: 'RD', width: 150 },
        { fieldName: 'RT_EXPORT', headerText: 'RT_EXPORT', width: 180 },
        { fieldName: 'RT_IMPORT', headerText: 'RT_IMPORT', width: 180 },
        {
          field: 'actions',
          headerText: 'Thao tác',
          textAlign: 'Center',
          width: 120,
          template: this.actionColumn(this)
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

    danhSachVrfData: {
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
