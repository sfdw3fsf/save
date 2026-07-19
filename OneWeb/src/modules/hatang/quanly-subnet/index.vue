<template src="./index.html"></template>
<script>
import AddSubnetModal from './components/add-subnet-modal/AddSubnetModal.vue'
import ModalSubnet from './components/subnet-modal/ModalSubnet.vue'
import axios from 'axios'
export default {
  name: 'QuanLySubnet',

  components: {
    ModalSubnet,
    AddSubnetModal
  },

  data() {
    return {
      // activeTab: 1,
      subnetColumns: [
        { fieldName: 'TEN', headerText: 'Tên Subnet', textAlign: 'Left', width: 150 },
        { fieldName: 'LOAIIP_ID', headerText: 'Loại IP', textAlign: 'Center', width: 100 },
        { fieldName: 'PHANLOAIIP_ID', headerText: 'Phân loại', textAlign: 'Center', width: 130 },
        { fieldName: 'SUBNET', headerText: 'Subnet', textAlign: 'Left', width: 150 },
        { fieldName: 'SBMASK', headerText: 'Subnet Mask', textAlign: 'Left', width: 140 },
        { fieldName: 'IDC_ID', headerText: 'IDC', width: 140 },
        {
          field: 'actions',
          headerText: 'Thao tác',
          textAlign: 'Center',
          width: 120,
          template: this.actionColumn(this)
        }
      ],

      allSubnetList: [],

      showModal: false,
      showAddModal: false,
      modalMode: 'VIEW',
      selectedSubnetId: null,

      buttons: {
        btnAddNew: { enabled: true },
        btnSave: { enabled: false },
        btnCancel: { enabled: false },
        btnEdit: { enabled: false },
        btnDelete: { enabled: false }
      },

      form: {
        name: '',
        mask: '',
        prefix: '',
        ipType: '',
        classify: '',
        idc: '',
        vrf: '',
        zone: '',
        vlan: '',
        phanLoaiIp: ''
      }
    }
  },

  mounted() {
    this.loadAllSubnet()
    console.log('danh sach subnet', this.allSubnetList)
  },

  methods: {
    async loadAllSubnet() {
      try {
        const rs = await this.$root.context.get('http://localhost:10990/subnet-tai-nguyen/danh-sach-subnet')
        console.log('danh sach subnet 2', rs)
        if (rs?.data) {
          this.allSubnetList = rs.data
        }
      } catch (e) {
        console.error('Error loading Subnet list', e)
      }
    },

    reloadList() {
      this.loadAllSubnet() // reload data table
    },

    onNew() {},
    onSave() {},
    onCancel() {},
    onEdit() {},
    onDelete() {},

    onRowSelected(row) {
      this.selectedSubnetId = row.data.ID
      console.log('Row selected:', row)
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
                parent.viewItem('VIEW', this.data)
              },
              deleteItem() {
                parent.deleteRow(this.data)
              }
            }
          }
        }
      }
    },

    viewItem(mode, row) {
      this.modalMode = mode
      this.showModal = true
    },

    onAddSubnet() {
      this.showAddModal = true
    },

    editItem(row) {
      this.modalMode = 'EDIT'
      this.showModal = true
    },

    deleteRow(row) {
      console.log('Delete row:', row)
    },

    onActionClick(e) {
      if (e.action === 'view') this.viewItem('VIEW', e.row)
      if (e.action === 'edit') this.editItem('EDIT', e.row)
      if (e.action === 'delete') this.deleteRow(e.row)
    },

    onCommandClick(e) {
      const row = e.rowData
      if (e.commandColumn.type === 'View') this.openModal('VIEW', row)
      if (e.commandColumn.type === 'Edit') this.openModal('EDIT', row)
    },

    onSaveSubnet(edited) {
      this.showModal = false
    },

    mapFormToPayload(form) {
      return {
        TEN: form.tenSubnet,
        HTM_ID: form.htm,
        LOAIIP_ID: form.loaiIp,
        PHANLOAIIP_ID: form.phanLoaiIp,
        GATEWAY: form.gateWay,
        SUBNET: form.subnet,
        SBMASK: form.subnetMask,
        IDC_ID: form.idc,
        SNCHA_ID: form.subnetCha,
        SINH_IP: form.sinhIp ?? 1,
        QUANGBA_IP: form.quangBa ?? 0,
        TT_CCDV_ID: form.ccdv,
        MUCDICHSD_ID: form.mucDich,
        THAMCHIEU: form.thamChieu,
        HIEU_LUC: form.hieuLuc ? 1 : 0,
        GHI_CHU: form.ghiChu,
        NOIBO: form.noiBo,
        DONVIQL_ID: form.donVi,
        NGUOI_CN: 'CSS' // or logged in user
      }
    },

    async createSubnet(form) {
      try {
        const payload = this.mapFormToPayload(form)

        const rs = await this.$root.context.post('http://localhost:10990/subnet-tai-nguyen/create-subnet', payload)

        if (rs?.data) {
          this.$toast.success('Tạo subnet thành công!')
          this.showAddModal = false
          // this.loadData() // reload bảng
        }
      } catch (error) {
        console.error(error)
        this.$toast.error('Lỗi tạo subnet')
      }
    }
  }
}
</script>
