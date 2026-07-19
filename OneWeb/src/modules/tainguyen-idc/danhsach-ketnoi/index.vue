<template src="./index.template.html"></template>
<script>
import api from './api'
import { LOAI_KETNOI, LOAI_DANH_MUC, trang_thai_kn_vl_options, trang_thai_kn_vh_options } from '../ketnoi-dien/constant'
import moment from 'moment'

export default {
  name: 'DanhSachKetNoi',
  // props: ['loaicv'],
  props: {
    loaicv: { type: Number, required: true },
    type: { type: String, required: true }
  },
  data() {
    return {
      current_item: {
        ma_cv: ''
      },
      tt_idc: 0,
      ketnoi_idc: 0,
      trangthai_vl: -1,
      trangthai_vh: -1,
      lop_ketnoi: 0,
      ds_ketnoi: [],
      ds_lichsu: [],
      modalAction: '',
      dsLoaiPhieu: [
        { ID: 1, TEN: 'Tạo kết nối điện hệ thống IDC' },
        { ID: 2, TEN: 'Thay đổi thông tin kết nối điện hệ thống IDC' },
        { ID: 3, TEN: 'Xóa kết nối điện hệ thống IDC' }
      ],
      tt_loai_phieu: 1,
      listLopKetNoi: [],
      listIDC: [],
      trang_thai_kn_vl_options: [{ id: -1, text: '-- Tất cả --' }, ...trang_thai_kn_vl_options],
      trang_thai_kn_vh_options: [{ id: -1, text: '-- Tất cả --' }, ...trang_thai_kn_vh_options],
      donvi_id: null,
      nhanVienList: [],
      nhanvienThId: 0,
      enabledNhanVien: true,
      enabledHeThong: true,
      listTbi: [],
      thietbi_nguon: 0,
      thietbi_dich: 0,
      ngayBatdau_kn: moment(new Date()).startOf('month').format('DD/MM/YYYY'),
      ngayKetthuc_kn:  moment(new Date()).endOf('month').format('DD/MM/YYYY'),
      ngayBatdau_ht: null,
      ngayKetthuc_ht: null,
      hinhthuc_id: 0,
      listHinhThuc: [],
      loaicv: null, // Loại công việc (30: Tạo mới kết nối điện IDC, 65: tháo dỡ kết nối điện IDC, 66: Thay đổi thông tin kết nối điện IDC),
      labelButtonThiCong: '',
      maTb: '',
      tenKhachHang: '',
      labelTable: '',
      isKhachHang: false,
      userLogin: 0,
    }
  },
  computed: {},
  watch: {
    ngayBatdau_kn() {
      this.validateRangeImmediate(this.ngayBatdau_kn, this.ngayKetthuc_kn, 1)
    },
    ngayKetthuc_kn() {
      this.validateRangeImmediate(this.ngayBatdau_kn, this.ngayKetthuc_kn, 2)
    },
    ngayBatdau_ht() {
      this.validateRangeImmediate(this.ngayBatdau_ht, this.ngayKetthuc_ht, 1)
    },
    ngayKetthuc_ht() {
      this.validateRangeImmediate(this.ngayBatdau_ht, this.ngayKetthuc_ht, 2)
    }
  },
  created: async function () {
    this.userLogin = this.$root.token.getNhanVienID()
    this.isKhachHang = this.type === 'khachhang' ? true : false
    this.getNameButton()
    await this.onLoadDanhMucForm()
    await this.onLoadDanhSachKetNoi()
  },
  methods: {
    getNameButton() {
      switch (this.loaicv) {
        case 30:
          this.labelButtonThiCong = 'Lắp đặt kết nối điện'
          break
        case 65:
          this.labelButtonThiCong = 'Tháo gỡ kết nối điện'
          break
        case 66:
          this.labelButtonThiCong = 'Thay đổi thông tin kết nối điện'
          break
        case 67:
          this.labelButtonThiCong = 'Thay đổi thông tin kết nối mạng'
          break
        case 68:
          this.labelButtonThiCong = 'Tháo gỡ kết nối mạng'
          break
        case 56:
          this.labelButtonThiCong = 'Lắp đặt kết nối mạng'
          break
        default:
          this.labelButtonThiCong = 'Lắp đặt kết nối điện'
          break
      }
      if (this.isKhachHang) {
        this.labelButtonThiCong = this.labelButtonThiCong + ' khách hàng'
      } else {
        this.labelButtonThiCong = this.labelButtonThiCong + ' IDC'
      }
    },
    // Validate ngay lập tức khi đã chọn đủ 2 ngày
    validateRangeImmediate(startDate, endDate, type) {
      if (type === 1) {
        if (startDate && endDate && new Date(startDate) > new Date(endDate)) {
          this.$toast.error(`Ngày bắt đầu không được lớn hơn ngày kết thúc`)
        }
      } else {
        if (startDate && endDate && new Date(startDate) > new Date(endDate)) {
          this.$toast.error(`Ngày kết thúc không được nhỏ hơn ngày bắt đầu`)
          return false
        }
      }

      return true
    },
    // Validate khi nhấn Search
    validateDateRange(startDate, endDate, typeLabel) {
      if (!startDate && endDate) {
        this.$toast.error(`Vui lòng chọn ngày bắt đầu ${typeLabel}`)
        return false
      }
      if (startDate && !endDate) {
        this.$toast.error(`Vui lòng chọn ngày kết thúc ${typeLabel}`)
        return false
      }
      if (startDate && endDate && new Date(startDate) > new Date(endDate)) {
        this.$toast.error(`Ngày bắt đầu không được lớn hơn ngày kết thúc`)
        return false
      }
      return true
    },
    onLoadDanhSachKetNoi: async function() {
      try {
        this.loading(true)
        // const params = {
        //   idLoaiCv: this.loaicv
        // }
        let params = {
          idLoaiCv: this.loaicv,
          isKhachHang: this.isKhachHang ? 1 : 0,
          // lấy trong tháng hiện tại
          ngayTaoFrom: this.ngayBatdau_kn,
          ngayTaoTo: this.ngayKetthuc_kn,
          // loại bỏ kết nối ở trạng thái 21
          phuongThuc: 'ketqua'
        }
        console.log('params', params)
        let response = await this.seachDSKetQua(params)
        if (response.error_code === 'BSS-00000000') {
          this.ds_ketnoi = response.data
          this.ds_ketnoi = this.ds_ketnoi.map((item, index) => ({
            ...item,
            // nếu item.KETNOI_ID = null thì gán F_KETNOI_ID = null để tránh lỗi
            F_KETNOI_ID: item.KETNOI_ID ? (
              this.loaicv == 30 || this.loaicv == 65 || this.loaicv == 66
                ? this.isKhachHang
                  ? `KND_KH_${item.KETNOI_ID}`
                  : `KND_IDC_${item.KETNOI_ID}`
                : this.isKhachHang
                ? `KNM_KH_${item.KETNOI_ID}`
                : `KNM_IDC_${item.KETNOI_ID}` // thêm tiền tố để phân biệt kết nối điện và mạng của IDC và khách hàng
            ) : null,
          }))
        } else {
          this.$toast.error(response?.data?.message_detail)
        }
      } catch (error) {
        this.$toast.error(error.data.message)
      } finally {
        this.loading(false)
      }
    },
    onView(data) {
      this.$router.push({
        path: '/tainguyen-idc/ketnoi',
        query: {
          isEditMode: 'false',
          loai_ketnoi: data.LOAIKN_ID,
          phieutc: data.PHIEUTC_ID,
          ketnoi_id: data.KETNOI_ID
        }
      })
    },
    onEdit(data) {
      this.$router.push({
        path: '/tainguyen-idc/ketnoi',
        query: {
          isEditMode: 'true',
          loai_ketnoi: data.LOAIKN_ID,
          phieutc: data.PHIEUTC_ID,
          ketnoi_id: data.KETNOI_ID
        }
      })
    },

    onDelete: async function(data) {
      const confirmed = await this.$bvModal.msgBoxConfirm(`Bạn có chắc chắn muốn xóa kết nối này không?`, {
        title: 'Thông báo',
        size: 'md',
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy',
        centered: true
      })
      if (!confirmed) return
      try {
        const response = await api.deleteKetNoi(this.axios, data.KETNOI_ID) // hoặc data.PHIEUTC_ID nếu đó là ID chính
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$toast.success('Xóa kết nối thành công')
          await this.onLoadDanhSachKetNoi() // Reload lại danh sách
        } else {
          this.$toast.error(response.data.message_detail)
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail)
      }
    },
    mapToTenLoaiKN: function(field, data, column) {
      if (field === 'LOAIKN_ID') {
        const item = Object.values(LOAI_KETNOI).find((x) => x.id === data.LOAIKN_ID)
        return item ? item.text : ''
      }
      return data[field]
    },
    mapToTrangThaiVL: function(field, data, column) {
      if (field === 'TRANGTHAI_VL_ID') {
        const item = trang_thai_kn_vl_options.find((x) => x.id === data.TRANGTHAI_VL_ID)
        return item ? item.text : ''
      }
      return data[field]
    },
    mapToTrangThaiVH: function(field, data, column) {
      if (field === 'TRANGTHAI_VH_ID') {
        const item = trang_thai_kn_vh_options.find((x) => x.id === data.TRANGTHAI_VH_ID)
        return item ? item.text : ''
      }
      return data[field]
    },
    getColumnTemplateChucNang(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                <button  class="btn btn-sm btn-outline-primary" title="Xem chi tiết" @click="parent.onView(data)">
                  <i class="fa fa-eye"></i>
                </button>
                <button v-if="data.TRANGTHAI_PHIEUYC_ID !== 24  && data.TRANGTHAI_PHIEUYC_ID !== 23 && data.NGUOITHUCHIEN_ID == parent.userLogin" class="btn btn-sm btn-outline-primary ml-1" title="Chỉnh sửa" @click="parent.onEdit(data)">
                  <i class="fa fa-edit"></i>
                </button>
                <!--
                <button v-if="data.TRANGTHAI_PHIEUYC_ID !== 24 && data.TRANGTHAI_VL_ID !== 0" class="btn btn-sm btn-outline-danger ml-1" title="Xóa" @click="parent.onDelete(data)">
                  <i class="fa fa-trash"></i>
                </button>
                -->
              </div>`,
            data() {
              return {
                parent: parent,
                data: this.$parent?.data || {}
              }
            }
          }
        }
      }
    },
    async onSearch() {
      const validKN = this.validateDateRange(this.ngayBatdau_kn, this.ngayKetthuc_kn, 'kết nối')
      const validHT = this.validateDateRange(this.ngayBatdau_ht, this.ngayKetthuc_ht, 'hoàn thành')
      if (!validKN || !validHT) return
      try {
        this.loading(true)
        let params = {
          donViId: Number(this.ketnoi_idc) > 0 ? Number(this.ketnoi_idc) : null,
          trangThaiVlId: Number(this.trangthai_vl) != -1 ? Number(this.trangthai_vl) : null,
          trangThaiVhId: Number(this.trangthai_vh) != -1 ? Number(this.trangthai_vh) : null,
          // lopKnId: Number(this.lop_ketnoi) > 0 ? Number(this.lop_ketnoi) : null,
          nhanVienThucHien: Number(this.nhanvienThId) > 0 ? Number(this.nhanvienThId) : null,
          heThongNguon: Number(this.thietbi_nguon) > 0 ? Number(this.thietbi_nguon) : null,
          heThongDich: Number(this.thietbi_dich) > 0 ? Number(this.thietbi_dich) : null,
          ngayTaoFrom: this.ngayBatdau_kn ? moment(this.ngayBatdau_kn).format('DD/MM/YYYY') : null,
          ngayTaoTo: this.ngayKetthuc_kn ? moment(this.ngayKetthuc_kn).format('DD/MM/YYYY') : null,
          ngayHoanThanhFrom: this.ngayBatdau_ht ? moment(this.ngayBatdau_ht).format('DD/MM/YYYY') : null,
          ngayHoanThanhTo: this.ngayKetthuc_ht ? moment(this.ngayKetthuc_ht).format('DD/MM/YYYY') : null,
          // hinhThucKn: Number(this.hinhthuc_id) > 0 ? Number(this.hinhthuc_id) : null,
          idLoaiCv: this.loaicv,
          maTb: this.maTb ? this.maTb.trim() : null,
          tenKhachHang: this.tenKhachHang ? this.tenKhachHang.trim() : null
        }
        console.log('params', params)
        let response = await this.seachDSKetQua(params)
        if (response.error_code === 'BSS-00000000') {
          this.ds_ketnoi = response.data
          this.ds_ketnoi = this.ds_ketnoi.map((item, index) => ({
            ...item,
                        // nếu item.KETNOI_ID = null thì gán F_KETNOI_ID = null để tránh lỗi
            F_KETNOI_ID: item.KETNOI_ID ? (
              this.loaicv == 30 || this.loaicv == 65 || this.loaicv == 66
                ? this.isKhachHang
                  ? `KND_KH_${item.KETNOI_ID}`
                  : `KND_IDC_${item.KETNOI_ID}`
                : this.isKhachHang
                ? `KNM_KH_${item.KETNOI_ID}`
                : `KNM_IDC_${item.KETNOI_ID}` // thêm tiền tố để phân biệt kết nối điện và mạng của IDC và khách hàng
            ) : null,
          }))
        } else {
          this.$toast.error(response?.data?.message_detail)
        }
      } catch (error) {
        console.log(' error', error)
        this.$toast.error(error?.data?.message_detail)
      } finally {
        this.loading(false)
      }
    },
    onLoadDanhMucForm: async function() {
      try {
        this.loading(true)
        this.listIDC = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.ALL_IDC)
        this.listIDC = [
          { id: 0, text: '-- Tất cả --' },
          ...this.listIDC.map((item) => ({
            id: item.DONVI_ID,
            text: item.TEN_DV
          }))
        ]
        this.listLopKetNoi = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.LOP_KET_NOI)
        this.listLopKetNoi = [{ id: 0, text: '-- Tất cả --' }, ...this.listLopKetNoi]
        this.listTbi = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.TBI_BY_IDC_ID)
        this.listTbi = [{ id: 0, text: '-- Tất cả --' }, ...this.listTbi]
        this.listHinhThuc = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.HINH_THUC_KN, 1)
        this.listHinhThuc = [{ id: 0, text: '-- Tất cả --' }, ...this.listHinhThuc]
      } catch (error) {
        this.$toast.error(error?.data?.message_detail)
      } finally {
        this.loading(false)
      }
    },
    onLoadDanhMucWithParams: async function(ten, thamSo1, thamSo2) {
      const response = await api.getDanhMuc(this.axios, { loaiDanhMuc: ten, thamSo1: thamSo1, thamSo2: thamSo2 })
      if (response && response.data && response.data.error == 200 && response.data.data) {
        return response.data.data.map((item) => ({
          id: item.ID,
          text: item.TEN,
          ...item
        }))
      }
      return []
    },
    seachDSKetQua: async function(data) {
      console.log('data', data)
      const rs = await api.seachDSKetQua(this.axios, data)
      return rs.data
    },
    onThiCong(loaicv) {
      this.$router.push({
        path: '/tainguyen-idc/danhsach-congviec',
        query: { loaicv: loaicv }
      })
    },
    onChangeDV: async function(donvi) {
      try {
        let donviID = donvi ? Number(donvi.id) : null
        this.ketnoi_idc = donviID
        this.loading(true)
        if (donviID) {
          this.nhanVienList = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.IDC_NHANVIEN_QLY, donviID)
          this.nhanVienList = [{ id: 0, text: '-- Tất cả --' }, ...this.nhanVienList]
          this.listTbi = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.TBI_BY_IDC_ID, donviID)
          if (this.ketnoi_idc > 0) {
            this.enabledNhanVien = false
            this.enabledHeThong = false
          } else {
            this.enabledNhanVien = true
            this.enabledHeThong = true
            this.nhanvienThId = 0
            this.thietbi_nguon = 0
            this.thietbi_dich = 0
          }
        } else {
          this.nhanVienList = []
          this.nhanvienThId = null
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    }
  }
}
// onRowSelected(data) {
//   // this.onView(data.data)
//   const popup = window.open('/#/tainguyen-idc/thongtin-ketnoi')

//   // Truyền data qua localStorage hoặc sessionStorage
//   sessionStorage.setItem('/#/tainguyen-idc/thongtin-ketnoi', JSON.stringify(data))
// },

// showThongTinKetNoi: function(action) {
//       this.modalAction = action
//       this.$bvModal.show('thongtin-ketnoi')
//     },
</script>
<style>
.info-row .key.w180 {
  width: 180px;
}
a {
  cursor: pointer;
}
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
