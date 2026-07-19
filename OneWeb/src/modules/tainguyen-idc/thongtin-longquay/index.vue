<template src="./index.template.html"></template>

<script>
import axios from 'axios'
import ModalThongtinLongQuay from './components/ModalThongtinLongQuay.vue'

export default {
  name: 'ThongTinLongQuay',
  components: {
    ModalThongtinLongQuay
  },
  data: function() {
    return {
      currentItem: {
        LONGQUAY_ID: null,
        TEN: '',
        TENTAT: '',
        MOTA: '',
        GHICHU: '',
        CHIEUDAI: null,
        CHIEURONG: null,
        CHIEUCAO: null,
        CAMERA: null,
        KHOA_ID: null,
        LOAILQ_ID: null,
        PHONG_ID: null,
        TEN_PHONG: '',
        TOADO_X: null,
        TOADO_Y: null,
        TRANGTHAI_VH: null,
        TRANGTHAI_VH_TEXT: '',
        SUDUNG_TEXT: '',
        SUDUNG: 1,
        NGUOI_CN: '',
        NGAY_CN: '',
        TEN_SOHUU: '',
        DONVISH_ID: null,
        SO_RACK: 0, // Tổng số rack trong lồng quây
        RACK_ID: [] // chọn nhiều rack -> mảng id
      },
      currentTab: 1,
      searchItem: {
        keyWord: '',
        trangThaiId: null,
        soHuuId: null,
        khoaId: null
      },
      listTrangThai: [],
      listKhoa: [],
      dsLongQuay: [],
      listSoHuu: []
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    }
  },
  watch: {},
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.listKhoa = await this.getDSKhoa()
      this.listTrangThai = await this.getTrangThaiVanHanh()
      this.listSoHuu = await this.getListSoHuuRack()
      this.reloadDSLongQuay()
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    getListSoHuuRack: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post(`/web-ecms/long-quay/danhsach-sohuu-rack`)
        result = rs.data || []
        result = [{ ID: 0, TEN: '-- Tất cả --' }, ...result]
        this.searchItem.soHuuId = 0
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getTrangThaiVanHanh: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'IDC-TRANGTHAI-VH',
          thamSo1: null
        })
        result = rs.data || []
        result = [{ ID: 0, TEN: '-- Tất cả --' }, ...result]
        this.searchItem.trangThaiId = 0
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getDSKhoa: async function() {
      let result = []
      try {
        // let rs = await this.$root.context.post('/web-ecms/thongtin-longquay/get-danhmuc-trangthai', {
        //   loaiDanhMuc: 'TRANGTHAI-VANHANH',
        //   thamSo1: null
        // })
        // result = rs.data || []
        result = [
          { id: 0, text: '-- Tất cả --' },
          { id: 1, text: 'Khóa từ' },
          { id: 2, text: 'Khóa cơ' },
          { id: 3, text: 'Không có' }
        ]
        this.searchItem.khoaId = 0
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getDSPhong: async function() {
      let result = []
      try {
        // let rs = await this.$root.context.post('/web-ecms/thongtin-longquay/get-danhmuc-trangthai', {
        //   loaiDanhMuc: 'TRANGTHAI-VANHANH',
        //   thamSo1: null
        // })
        // result = rs.data || []
        result = [
          { id: 0, text: '-- Tất cả --' },
          { id: 1, text: 'Zone 1 - Hà Nội' },
          { id: 2, text: 'Zone 2 - Đà Nẵng' },
          { id: 3, text: 'Zone 3 - TP.HCM' }
        ]
        this.searchItem.phongId = 0
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getDSLongQuay: async function() {
      let result = []
      try {
        this.loading(true)
        const params = {
          ten: this.searchItem.keyWord || null,
          trangthai_vh: Number(this.searchItem.trangThaiId) || null,
          donvi_sh: Number(this.searchItem.soHuuId) || null,
          khoa_id: Number(this.searchItem.khoaId) || null
        }
        let rs = await this.$root.context.post('/web-ecms/long-quay/danh-sach', params)
        // let rs = await this.$root.context.post('http://localhost:10990/long-quay/danh-sach', params)

        result = rs.data || []
      } catch (e) {
        console.log('Error fetching data:', e)
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      } finally {
        this.loading(false)
      }
      return result
    },
    reloadDSLongQuay: async function() {
      this.dsLongQuay = await this.getDSLongQuay()
      this.dsLongQuay = this.dsLongQuay.map((item) => ({
        ...item,
        SUDUNG_TEXT: item.SUDUNG === 1 ? 'Hiệu lực' : 'Không hiệu lực',
        KICH_THUOC: `${item.CHIEUDAI}cm x ${item.CHIEURONG}cm x ${item.CHIEUCAO}cm`,
        CAMERA_LABEL: item.CAMERA ? 'Có' : 'Không',
        KHOA_LABEL: item.KHOA_ID === 1 ? 'Khóa từ' : item.KHOA_ID === 2 ? 'Khóa cơ' : 'Không có'
      }))
    },

    grdItems_RowSelected: async function(args) {
      try {
        //this.$root.showLoading(true);
        this.setCurrentItem(args.data)
      } finally {
        this.$root.showLoading(false)
      }
    },
    grdItems_RowDeselected: async function() {
      let items = this.grdItems.getSelectedRecords()
      if (!(items == null || items.length == 0)) {
        try {
          //this.$root.showLoading(true);
          this.setCurrentItem(items[0])
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.setCurrentItemDefault()
      }
    },
    setCurrentItem: async function(item) {
      this.currentItem = { ...item }
      this.currentItem.RACK_ID = item.DS_RACK ? item.DS_RACK.split(',').map(Number) : []
    },
    setCurrentItemDefault: function() {
      this.currentItem = {
        LONGQUAY_ID: null,
        TEN: '',
        SUDUNG: 1,
        SUDUNG_TEXT: '',
        LOAILQ_ID: null,
        PHONG_ID: null,
        CHIEUDAI: null,
        CHIEURONG: null,
        CHIEUCAO: null,
        CAMERA: null,
        KHOA_ID: null,
        SOHUU_ID: null,
        TRANGTHAI_VH: null,
        TRANGTHAI_VH_TEXT: '',
        TENTAT: '',
        MOTA: '',
        GHICHU: '',
        TOADO_X: null,
        TOADO_Y: null,
        RACK_ID: []
      }
    },
    onCreate: async function() {
      this.$refs.ModalThongTinLongQuay.showModal(3) // 3 - Create
    },
    onSearch: function() {
      this.reloadDSLongQuay()
    },
    actionColumn(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-success" title="Xem" @click="onViewItem()">
                  <i class="fa fa-eye"></i>
                </button>
                <button class="btn btn-sm btn-outline-primary" title="Sửa" @click="onEditItem()">
                  <i class="icon nc-icon-outline one-edit"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger" title="Xóa" @click="onDeleteItem()">
                      <i class="fa fa-trash"></i>
                </button>
              </div>`,
            computed: {},
            methods: {
              onDeleteItem() {
                parent.onDeleteItem(this.data)
              },
              onEditItem() {
                parent.onEditItem(this.data)
              },
              onViewItem() {
                parent.onViewItem(this.data)
              }
            }
          }
        }
      }
    },
    async onDeleteItem(data) {
      try {
        const flag = await this.$bvModal.msgBoxConfirm(`Bạn có chắc chắn muốn xóa lồng quây này không?`, {
          title: 'Thông báo',
          size: 'md',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
          centered: true
        })
        if (flag) {
          try {
            this.$root.showLoading(true)
            if(data && data.SO_RACK > 0){
              this.$toast.error('Tồn tại Rack trong lồng quây, không thể xóa! Vui lòng tháo gỡ các Rack trong lồng quây trước khi thực hiện xóa.')
              return;
            }
            let response = await this.$root.context.post(`/web-ecms/long-quay/delete/${data.LONGQUAY_ID}`)
            if (response && response.error_code === 'BSS-00000000') {
              this.$toast.success('Xóa lồng quây thành công')
              await this.reloadDSLongQuay() // Reload lại danh sách
            } else {
              this.$toast.error(response.message_detail)
            }
          } finally {
            this.$root.showLoading(false)
          }
        }
      } catch (error) {
        console.error('delete error:', error)

        this.$toast.error('Có lỗi xảy ra trong quá trình thực hiện')
      }
    },

    onEditItem(data) {
      try {
        this.$refs.ModalThongTinLongQuay.showModal(2) // 2 - Edit
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra khi xử lý công việc')
      }
    },
    onViewItem(data) {
      try {
        this.$refs.ModalThongTinLongQuay.showModal(1) // 1 - View
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra khi xử lý công việc')
      }
    }
  }
}
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
