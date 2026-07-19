<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import TabHistory from '../../components/Tabs/TabHistory/index.vue' // import component TabHistory
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import api from '../api'
import moment from 'moment'

export default {
  name: 'ModalChiTietHeThongKhachHang',
  props: {
    modalID: null,
    isKhachHang: { type: Boolean, default: true },
    phieuThiCongDetail: { type: Object, default: null }
  },
  components: {
    TabHistory,
    BssErrorMarker,
    BssRequiredMarker
  },
  validations: {},
  watch: {},
  computed: {
    idcFromWork() {
      return this.$parent.phieuThiCongDetail?.IDC_ID || null
    }
  },
  methods: {
    isChiTietHeThongDaLapDat: function() {
      return !!(this.currentItem.IDC_ID && this.currentItem.NGAY_LD && this.currentItem.CANCU_YC)
    },
    onChangeTab: function(index) {
      this.tab_index = index
    },
    closeModal: function() {
      this.$refs.ModalChiTietHeThongKhachHang.hide(this.modalID)
    },
    show: function(item, mode, modeFormThiCong) {
      if (item) {
        this.currentItem = { ...item }
        this.currentMode = mode
      } else {
        this.resetCurrentItem()
        this.currentMode = mode
      }

      // Set the operation mode for construction work
      this.modeFormThiCong = modeFormThiCong || null

      // If called from construction work (phieu thi cong), set appropriate data
      if (typeof modeFormThiCong === 'object' && modeFormThiCong !== null) {
        this.isFromThiCong = true
        this.modeFormThiCong = null // Reset since it was used for data
        if(!item) {
          this.currentItem.TINH_ID = modeFormThiCong.TINH_ID
          this.currentItem.HDTB_ID = modeFormThiCong.HDTB_ID
          this.currentItem.TEN_KH = modeFormThiCong.TEN_KH
          this.currentItem.SO_HD = modeFormThiCong.SO_HD
          this.currentItem.MA_TB = modeFormThiCong.MA_TB
        }
      } else {
        this.isFromThiCong = false
      }

      this.currentItem.LOAIKH_ID = this.isKhachHang ? 1 : 0
      this.item = item

      this.tab_index = 1
      if (
        this.modeFormThiCong === 'LAPDAT' ||
        this.modeFormThiCong === 'THAY_DOI_LAP_DAT' ||
        this.modeFormThiCong === 'THAO_DO'
      ) {
        this.tab_index = 2
      }
      // binding dữ liệu IDC từ chi tiết phiếu
      if (
        this.modeFormThiCong === 'LAPDAT' ||
        this.modeFormThiCong === 'THAY_DOI_LAP_DAT' ||
        this.currentMode != 'ADD'
      ) {
        this.currentItem.IDC_ID = this.idcFromWork || this.$parent.idcSelectedEmit
      } else {
        this.currentItem.IDC_ID = null
      }
      this.isHeThongDaLapDat = this.isChiTietHeThongDaLapDat()
      this.loadInitialData().then(() => {
        this.$refs.ModalChiTietHeThongKhachHang.show()
      })
    },

    onAddNew: function() {
      this.resetCurrentItem()
      this.currentMode = 'ADD'
      this.resetValidation()
    },

    onSave: async function() {
      if (this.validateForm()) {
        // Xác nhận trước khi lưu thông tin lắp đặt
        if (this.modeFormThiCong === 'LAPDAT') {
          try {
            await this.$confirm(
              'Bạn có chắc chắn muốn lưu thông tin lắp đặt hệ thống này?',
              'Xác nhận lắp đặt',
              {
                confirmButtonText: 'Xác nhận',
                cancelButtonText: 'Huỷ bỏ',
                type: 'warning'
              }
            )
          } catch {
            return // Người dùng chọn huỷ bỏ
          }
        }

        try {
          this.$root.showLoading(true)
          if (this.currentMode === 'ADD') {
            await this.saveNewItem()
          } else {
            await this.updateItem()
          }
          this.closeModal()
        } catch (error) {
          console.log(error)
          this.handleError(error)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    onThaoDoHeThong: async function() {
      if (this.validateForm()) {
        try {
          const confirm = await this.$confirm('Bạn có chắc chắn muốn tháo dỡ hệ thống này?', 'Xác nhận tháo dỡ', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'warning',
            dangerouslyUseHTMLString: true
          })

          if (!confirm) {
            return
          }

          this.$root.showLoading(true)

          this.currentItem.NGAY_THAOGO = this.ngayThaoDo
          this.currentItem.NGAY_LD = ''
          this.currentItem.CANCU_YC = ''

          // Gọi API update item
          await this.updateItem()
        } catch (error) {
          console.log(error)
          if (error != 'cancel') {
            this.handleError(error)
          }
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    // Helper methods
    isEmptyOrWhitespace: function(str) {
      return !str || !str.toString().trim()
    },
    resetCurrentItem: function() {
      this.currentItem = {
        MANGTBI_ID: null,
        LOAIKH_ID: null,
        HETHONGTBI_ID: '',
        TEN: '',
        IDC_ID: '',
        NGAY_LD: '',
        CANCU_YC: '',
        TRANGTHAI_VH_ID: 1,
        TRANGTHAI_TS_ID: '',
        MOTA: '',
        NGAY_CN: '',
        NGAY_CAP_NHAT: '',
        GHICHU: '',
        TEN_KH: '',
        SO_HD: '',
        MA_TB: '',
        HDTB_ID: null,
        SUDUNG: 1,
        DONVI_QL_ID: this.$root.token.getDonViID(),
        NGAY_THAOGO: null
      }
      this.ngayThaoDo = ''
    },

    resetValidation: function() {
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })
    },

    validateForm: function() {
      let isValid = true
      this.resetValidation()

      if (this.modeFormThiCong === 'LAPDAT' || this.modeFormThiCong === 'THAY_DOI_LAP_DAT') {
        // Validation cho lắp đặt và thay đổi lắp đặt - focus vào 3 trường chính
        if (!this.currentItem.IDC_ID) {
          this.controls.selectIDC.invalid = true
          isValid = false
        }

        if (!this.currentItem.NGAY_LD) {
          this.controls.txtNgayLapDat.invalid = true
          isValid = false
        } else {
          // Validate ngày lắp đặt không được trong tương lai
          const ngayLapDat = moment(this.currentItem.NGAY_LD)
          const today = moment().startOf('day')
          if (ngayLapDat.isAfter(today)) {
            this.controls.txtNgayLapDat.invalid = true
            isValid = false
            this.$root.toastError('Ngày lắp đặt không được vượt quá ngày hiện tại!')
            return false
          }
        }

        if (!this.currentItem.CANCU_YC || this.isEmptyOrWhitespace(this.currentItem.CANCU_YC)) {
          this.controls.txtCanCuLapDat.invalid = true
          isValid = false
        }

        if (!isValid) {
          this.$root.toastError('Vui lòng điền đầy đủ thông tin lắp đặt')
        }
      } else if (this.modeFormThiCong === 'THAO_DO') {
        // Validation cho tháo dỡ - chỉ cần ngày tháo dỡ
        if (!this.ngayThaoDo) {
          this.controls.txtNgayThaoDo.invalid = true
          isValid = false
        } else {
          // Validate ngày tháo dỡ phải sau ngày lắp đặt
          const ngayThaoDo = moment(this.ngayThaoDo)
          const ngayLapDat = this.currentItem.NGAY_LD ? moment(this.currentItem.NGAY_LD) : null
          
          if (ngayLapDat && ngayThaoDo.isBefore(ngayLapDat)) {
            this.controls.txtNgayThaoDo.invalid = true
            isValid = false
            this.$root.toastError('Ngày tháo dỡ phải sau ngày lắp đặt hệ thống!')
            return false
          }

          // Validate ngày tháo dỡ không được trong tương lai
          const today = moment().startOf('day')
          if (ngayThaoDo.isAfter(today)) {
            this.controls.txtNgayThaoDo.invalid = true
            isValid = false
            this.$root.toastError('Ngày tháo dỡ không được vượt quá ngày hiện tại!')
            return false
          }
        }

        if (!isValid) {
          this.$root.toastError('Vui lòng chọn ngày tháo dỡ!')
        }
      } else {
        // Validation thông thường cho ADD/EDIT mode
        if (!this.currentItem.MANGTBI_ID) {
          this.controls.selectMangThietBi.invalid = true
          isValid = false
        }
        if (!this.currentItem.TEN || this.isEmptyOrWhitespace(this.currentItem.TEN)) {
          this.controls.txtTenHeThong.invalid = true
          isValid = false
        }

        if (!this.currentItem.TRANGTHAI_VH_ID) {
          this.controls.selectTrangThaiVanHanh.invalid = true
          isValid = false
        }

        if (!this.currentItem.TRANGTHAI_TS_ID) {
          this.controls.selectTrangThaiTaiSan.invalid = true
          isValid = false
        }

        if (!this.currentItem.TEN_KH && this.isKhachHang) {
          this.controls.txtKhachHang.invalid = true
          isValid = false
        }

        if (!this.currentItem.SO_HD && this.isKhachHang) {
          this.controls.txtSoHopDong.invalid = true
          isValid = false
        }

        if (!this.currentItem.MA_TB && this.isKhachHang) {
          this.controls.txtMaThueBao.invalid = true
          isValid = false
        }

        // Validate ngày lắp đặt và ngày tháo dỡ trong chế độ EDIT
        if (this.currentItem.NGAY_LD) {
          const ngayLapDat = moment(this.currentItem.NGAY_LD)
          const today = moment().startOf('day')
          if (ngayLapDat.isAfter(today)) {
            this.controls.txtNgayLapDat.invalid = true
            isValid = false
            this.$root.toastError('Ngày lắp đặt không được vượt quá ngày hiện tại!')
            return false
          }
        }

        if (this.currentItem.NGAY_THAOGO && this.currentItem.NGAY_LD) {
          const ngayThaoDo = moment(this.currentItem.NGAY_THAOGO)
          const ngayLapDat = moment(this.currentItem.NGAY_LD)
          
          if (ngayThaoDo.isBefore(ngayLapDat)) {
            this.controls.txtNgayThaoDo.invalid = true
            isValid = false
            this.$root.toastError('Ngày tháo dỡ phải sau ngày lắp đặt hệ thống!')
            return false
          }

          const today = moment().startOf('day')
          if (ngayThaoDo.isAfter(today)) {
            this.controls.txtNgayThaoDo.invalid = true
            isValid = false
            this.$root.toastError('Ngày tháo dỡ không được vượt quá ngày hiện tại!')
            return false
          }
        }

        if (!isValid) {
          this.$root.toastError('Vui lòng điền đầy đủ thông tin bắt buộc!')
        }
      }

      return isValid
    },

    // API methods
    loadInitialData: async function() {
      try {
        this.$root.showLoading(true)
        await Promise.all([this.loadDanhMuc()])
      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },

    // Load dropdown data methods
    loadDanhMuc: async function() {
      this.idcList = await api.getDanhMucIDC(this.axios, this.$root.token, 'VIEW')
      this.mangThietBiList = await api.getDanhMuc(this.axios, 'IDC-MANG-TBI')
      this.loaiKhachHangList = await api.getDanhMuc(this.axios, 'LOAI-KH')
      this.trangThaiTaiSanList = await api.getDanhMucTrangThaiTaiSan(this.axios)
      this.trangThaiVanHanhList = await api.getDanhMucTrangThaiVanHanh(this.axios)
      this.nhanVienList = await api.getDanhMucNhanVienQuanLy(this.axios, this.$root.token)
      this.donViList = await api.getDanhMucDonViQuanLy(this.axios)
      this.trangThaiTaiSanList = [
        { ID: '0', TEN: 'Đang sử dụng' },
        { ID: '1', TEN: 'Dừng hoạt động' },
        { ID: '2', TEN: 'Hỏng' },
        { ID: '3', TEN: 'Đã thanh lý' }
      ]
    },

    // CRUD operations
    saveNewItem: async function() {
      // Convert currentItem to match DTO
      let dto = {
        mangTbiId: +this.currentItem.MANGTBI_ID,
        loaiKHId: this.currentItem.LOAIKH_ID ? +this.currentItem.LOAIKH_ID : null,
        tenHeThong: this.currentItem.TEN,
        ghiChu: this.currentItem.GHICHU,
        idcId: +this.currentItem.IDC_ID,
        ngayLD: this.currentItem.NGAY_LD ? moment(this.currentItem.NGAY_LD).format('YYYY-MM-DD') : null,
        canCuYC: this.currentItem.CANCU_YC,
        trangThaiTSId: +this.currentItem.TRANGTHAI_TS_ID,
        trangThaiVHId: +this.currentItem.TRANGTHAI_VH_ID,
        suDung: this.currentItem.SUDUNG ?? 1,
        moTa: this.currentItem.MOTA,
        hdtbId: this.isKhachHang ? +this.currentItem.HDTB_ID : null,
        tinhId: this.isKhachHang ? +this.currentItem.TINH_ID : 68,
        nguoiQLId: this.currentItem.NHANVIEN_QL_ID ? +this.currentItem.NHANVIEN_QL_ID : null,
        donViQLId: this.currentItem.DONVI_QL_ID ? +this.currentItem.DONVI_QL_ID : null
      }
      let rs = await api.createHeThongThietBiKhachHang(this.axios, dto)
      if (rs.error_code == 'BSS-00000000') {
        if (this.isFromThiCong) {
          this.$emit('themMoiHeThongSuccess', {
            HETHONGTBI_ID: rs.data[0].item
          })
          this.$emit('refreshData')
          this.closeModal()
        }
      } else {
        this.$root.toastError(rs.message)
      }
    },

    updateItem: async function() {
      // Tạo DTO chung cho tất cả các chế độ
      let dto = {
        heThongTbiId: +this.currentItem.HETHONGTBI_ID,
        mangTbiId: +this.currentItem.MANGTBI_ID,
        loaiKHId: +this.currentItem.LOAIKH_ID,
        tenHeThong: this.currentItem.TEN,
        ghiChu: this.currentItem.GHICHU,
        idcId: +this.currentItem.IDC_ID,
        ngayLD: this.currentItem.NGAY_LD ? moment(this.currentItem.NGAY_LD).format('YYYY-MM-DD') : null,
        canCuYC: this.currentItem.CANCU_YC,
        trangThaiTSId: +this.currentItem.TRANGTHAI_TS_ID,
        trangThaiVHId: +this.currentItem.TRANGTHAI_VH_ID,
        trangThaiVanHanh: this.currentItem.TRANGTHAI_VANHANH,
        suDung: this.currentItem.SUDUNG || 1,
        moTa: this.currentItem.MOTA,
        hdtbId: this.isKhachHang ? +this.currentItem.HDTB_ID : null,
        tinhId: this.isKhachHang ? +this.currentItem.TINH_ID : 68,
        nguoiQLId: this.currentItem.NHANVIEN_QL_ID ? +this.currentItem.NHANVIEN_QL_ID : null,
        donViQLId: this.currentItem.DONVI_QL_ID ? +this.currentItem.DONVI_QL_ID : null,
        ngayThaoGo: this.modeFormThiCong === 'LAPDAT' || this.modeFormThiCong === 'THAY_DOI_LAP_DAT' ? null : this.currentItem.NGAY_THAOGO
      }
      let rs = await api.updateHeThongThietBiKhachHang(this.axios, dto)

      if (rs && rs.error_code === 'BSS-00000000') {
        // Hiển thị message tương ứng với chế độ
        let successMessage = 'Cập nhật thành công!'
        if (this.modeFormThiCong === 'LAPDAT') {
          successMessage = 'Lắp đặt hệ thống thành công!'
        } else if (this.modeFormThiCong === 'THAO_DO') {
          successMessage = 'Tháo dỡ hệ thống thành công!'
        } else if (this.modeFormThiCong === 'THAY_DOI_LAP_DAT') {
          successMessage = 'Thay đổi thông tin hệ thống thành công!'
        }

        this.$root.toastSuccess(successMessage)
        this.$emit('refreshData')
        this.closeModal()
      } else {
        // Hiển thị message lỗi tương ứng với chế độ
        let errorMessage = rs?.message_detail || rs?.message || 'Đã xảy ra lỗi!'
        if (this.modeFormThiCong === 'LAPDAT') {
          errorMessage = rs?.message_detail || 'Đã xảy ra lỗi khi lắp đặt hệ thống!'
        } else if (this.modeFormThiCong === 'THAO_DO') {
          errorMessage = rs?.message_detail || 'Đã xảy ra lỗi khi tháo dỡ hệ thống!'
        } else if (this.modeFormThiCong === 'THAY_DOI_LAP_DAT') {
          errorMessage = rs?.message_detail || 'Đã xảy ra lỗi khi thay đổi thông tin hệ thống!'
        }

        this.$root.toastError(errorMessage)
      }
    },

    handleError: function(error) {
      console.log('Error: ', error)

      // Check multiple possible error message locations
      let errorMessage = 'Đã xảy ra lỗi!'

      if (error.response && error.response.data) {
        // Try different message properties
        errorMessage =
          error.response.data.message_detail ||
          error.response.data.message ||
          error.response.data.error_message ||
          errorMessage
      } else if (error.message) {
        // For non-HTTP errors
        errorMessage = error.message
      }

      this.$root.toastError(errorMessage)
    }
  },
  data: function() {
    return {
      target: '.main-wrapper',
      dialogPosition: { X: 'center', Y: 'center' },
      animationSettings: { effect: 'Zoom' },
      item: null,
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      modeFormThiCong: null, // LAPDAT, THAO_DO, THAY_DOI_LAP_DAT
      isFromThiCong: false,
      isHeThongDaLapDat: false,
      currentItem: {
        HETHONGTBI_ID: '',
        TEN: '',
        IDC_ID: '',
        NGAY_LD: '',
        NGUOI_QUAN_LY: '',
        TRANGTHAI_VH_ID: 1,
        MOTA: '',
        NGAY_TAO: '',
        NGAY_CAP_NHAT: '',
        GHICHU: '',
        TEN_KH: '',
        SO_HD: '',
        MA_TB: '',
        HDTB_ID: null,
        MANGTBI_ID: null,
        LOAIKH_ID: null,
        CANCU_YC: '',
        TRANGTHAI_TS_ID: '',
        DONVI_QL_ID: null,
        NGUOI_QL_ID: null,
        TRANGTHAI_VANHANH: null,
        SUDUNG: 1,
        NGAY_THAOGO: null
      },

      // Trường riêng cho tháo dỡ
      ngayThaoDo: '',

      // Dropdown lists
      idcList: [],
      donViQuanLyList: [],
      trangThaiVanHanhList: [],
      trangThaiTaiSanList: [],
      mangThietBiList: [],
      loaiKhachHangList: [],
      nhanVienList: [],
      donViList: [],
      // Form controls
      controls: {
        selectMangThietBi: { enabled: false, invalid: false },
        selectLoaiKhachHang: { enabled: false, invalid: false },
        txtIdHeThong: { enabled: false, invalid: false },
        txtTenHeThong: { enabled: false, invalid: false },
        selectIDC: { enabled: false, invalid: false },
        txtNgayLapDat: { enabled: false, invalid: false },
        txtCanCuLapDat: { enabled: false, invalid: false },
        txtNgayThaoDo: { enabled: false, invalid: false },
        selectTrangThaiVanHanh: { enabled: false, invalid: false },
        selectTrangThaiTaiSan: { enabled: false, invalid: false },
        txtMoTa: { enabled: false, invalid: false },
        txtNgayTao: { enabled: false, invalid: false },
        txtNgayCapNhat: { enabled: false, invalid: false },
        txtGhiChu: { enabled: false, invalid: false },
        txtKhachHang: { enabled: false, invalid: false },
        txtSoHopDong: { enabled: false, invalid: false },
        txtMaThueBao: { enabled: false, invalid: false },
        selectNhanVienQuanLy: { enabled: false, invalid: false },
        selectDonViQuanLy: { enabled: false, invalid: false }
      },
      tab_index: 1
    }
  }
}
</script>
<style scoped>
.popup-box {
  max-height: 90vh !important;
  overflow-y: auto;
}
.e-dialog {
  position: fixed !important;
  top: 50% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
}

.custom-dialog .e-dlg-content {
  overflow: visible !important;
  overflow-x: visible !important;
}
.form-control::placeholder {
  color: #999 !important;
}
</style>
