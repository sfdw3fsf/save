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
  props: { modalID: null },
  components: {
    TabHistory,
    BssErrorMarker,
    BssRequiredMarker
  },
  created: async function() {
    await this.loadInitialData()
  },
  validations: {},
  watch: {
    currentMode: function(newVal) {
      // Enable/disable form controls based on currentMode
      this.showModal = false
      setTimeout(() => {
        this.showModal = true
        const isViewMode = newVal === 'VIEW'
        Object.keys(this.controls).forEach((key) => {
          this.controls[key].enabled = !isViewMode
          if (isViewMode) {
            this.controls[key].invalid = false // Reset validation in view mode
          }
        })
      }, 0)
    }
  },
  computed: {},
  methods: {
    closeModal: function() {
      this.resetCurrentItem()
      this.tab_index = 1
      this.currentMode = 'VIEW'
      this.resetValidation()
      this.item = null
      this.$refs.ModalChiTietHeThongKhachHang.hide(this.modalID)
    },
    show: function(item, mode) {
      if (item) {
        this.currentItem = { ...item }
        this.currentMode = mode
      } else {
        this.resetCurrentItem()
        this.currentMode = mode
      }
      this.item = item
      this.$refs.ModalChiTietHeThongKhachHang.show()
    },

    onAddNew: function() {
      this.resetCurrentItem()
      this.currentMode = 'ADD'
      this.resetValidation()
    },

    onSave: async function() {
      // if (this.validateForm()) { //Tạm thời bỏ validate
      if (true) {
        try {
          this.$root.showLoading(true)
          if (this.currentMode === 'ADD') {
            await this.saveNewItem()
          } else {
            await this.updateItem()
          }
          this.currentMode = 'VIEW'
          this.$refs.tabHistory.refreshData() // Refresh history tab
          this.$emit('refreshData') // Emit event to refresh parent grid
        } catch (error) {
          this.handleError(error)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    // Helper methods
    resetCurrentItem: function() {
      this.currentItem = {
        MANGTBI_ID: null,
        LOAIKH_ID: null,
        HETHONGTBI_ID: '',
        TEN: '',
        IDC_ID: '',
        NGAY_LD: '',
        CANCU_YC: '',
        TRANGTHAI_VH_ID: '',
        TRANGTHAI_TS_ID: '',
        MOTA: '',
        NGAY_CN: '',
        NGAY_CAP_NHAT: '',
        GHICHU: ''
      }
    },

    resetValidation: function() {
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })
    },

    validateForm: function() {
      let isValid = true

      // Validate required fields
      if (!this.currentItem.MANGTBI_ID) {
        this.controls.selectMangThietBi.invalid = true
        isValid = false
      }

      if (!this.currentItem.LOAIKH_ID) {
        this.controls.selectLoaiKhachHang.invalid = true
        isValid = false
      }
      if (!this.currentItem.TEN) {
        this.controls.txtTenHeThong.invalid = true
        isValid = false
      }

      if (!this.currentItem.IDC_ID) {
        this.controls.selectIDC.invalid = true
        isValid = false
      }

      if (!this.currentItem.NGAY_LD) {
        this.controls.selectDonViQuanLy.invalid = true
        isValid = false
      }

      if (!this.currentItem.CANCU_YC) {
        this.controls.txtCanCuLapDat.invalid = true
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

      if (!this.currentItem.KHACH_HANG_TEN) {
        this.controls.txtMoTa.invalid = true
        isValid = false
      }

      if (!this.currentItem.SO_HOP_DONG) {
        this.controls.txtSoHopDong.invalid = true
        isValid = false
      }

      if (!this.currentItem.MA_THUE_BAO) {
        this.controls.txtMaThueBao.invalid = true
        isValid = false
      }

      if (!isValid) {
        this.$root.toastError('Vui lòng điền đầy đủ thông tin bắt buộc!')
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
      this.idcList = await api.getDanhMucIDC(this.axios, this.$root.token)
      this.mangThietBiList = await api.getDanhMuc(this.axios, 'IDC-MANG-TBI')
      this.loaiKhachHangList = await api.getDanhMuc(this.axios, 'LOAI-KH')
      this.trangThaiTaiSanList = await api.getDanhMucTrangThaiTaiSan(this.axios)
      this.trangThaiVanHanhList = await api.getDanhMucTrangThaiVanHanh(this.axios)
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
        mangTbiId: this.currentItem.MANGTBI_ID,
        loaiKHId: this.currentItem.LOAIKH_ID,
        tenHeThong: this.currentItem.TEN,
        ghiChu: this.currentItem.GHICHU,
        donViQLId: this.currentItem.DONVIQL_ID,
        nguoiQLId: this.currentItem.NGUOI_QL_ID,
        idcId: this.currentItem.IDC_ID,
        ngayLD: this.currentItem.NGAY_LD ? moment(this.currentItem.NGAY_LD).format('YYYY-MM-DD') : null,
        canCuYC: this.currentItem.CANCU_YC,
        trangThaiTSId: this.currentItem.TRANGTHAI_TS_ID,
        trangThaiVHId: this.currentItem.TRANGTHAI_VH_ID,
        trangThaiVanHanh: this.currentItem.TRANGTHAI_VANHANH,
        suDung: this.currentItem.SUDUNG,
        moTa: this.currentItem.MOTA,
        hdtbId: this.currentItem.HDTB_ID,
        tinhId: this.currentItem.TINH_ID
      }
      let rs = await api.createHeThongThietBiKhachHang(this.axios, dto)
      if (rs.error_code == 'BSS-00000000') {
        this.$root.toastSuccess('Tạo mới thành công!')
      } else {
        this.$root.toastError(rs.message)
      }
    },

    updateItem: async function() {
      let dto = {
        heThongTbiId: this.currentItem.HETHONGTBI_ID,
        mangTbiId: this.currentItem.MANGTBI_ID,
        loaiKHId: this.currentItem.LOAIKH_ID,
        tenHeThong: this.currentItem.TEN,
        ghiChu: this.currentItem.GHICHU,
        donViQLId: this.currentItem.DONVIQL_ID,
        nguoiQLId: this.currentItem.NGUOI_QL_ID,
        idcId: this.currentItem.IDC_ID,
        ngayLD: this.currentItem.NGAY_LD ? moment(this.currentItem.NGAY_LD).format('YYYY-MM-DD') : null,
        canCuYC: this.currentItem.CANCU_YC,
        trangThaiTSId: this.currentItem.TRANGTHAI_TS_ID,
        trangThaiVHId: this.currentItem.TRANGTHAI_VH_ID,
        trangThaiVanHanh: this.currentItem.TRANGTHAI_VANHANH,
        suDung: this.currentItem.SUDUNG,
        moTa: this.currentItem.MOTA,
        hdtbId: this.currentItem.HDTB_ID,
        tinhId: this.currentItem.TINH_ID
      }
      let rs = await api.updateHeThongThietBiKhachHang(this.axios, dto)
      if (rs.error_code == 'BSS-00000000') {
        this.$root.toastSuccess('Cập nhật thành công!')
      } else {
        this.$root.toastError(rs.message)
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
    },
    onChangeTab: function(index) {
      this.tab_index = index
    }
  },
  data: function() {
    return {
      loaiCongList: [],
      showModal: true,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      item: null,
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      tab_index: 1,
      currentItem: {
        HETHONGTBI_ID: '',
        TEN: '',
        IDC_ID: '',
        NGAY_LD: '',
        NGUOI_QUAN_LY: '',
        TRANGTHAI_VH_ID: '',
        MOTA: '',
        NGAY_TAO: '',
        NGAY_CAP_NHAT: '',
        GHICHU: '',
        KHACH_HANG_TEN: '',
        SO_HOP_DONG: '',
        MA_THUE_BAO: ''
      },

      // Dropdown lists
      idcList: [],
      donViQuanLyList: [],
      trangThaiVanHanhList: [],
      trangThaiTaiSanList: [],
      mangThietBiList: [],
      loaiKhachHangList: [],

      // Form controls
      controls: {
        selectMangThietBi: { enabled: false, invalid: false },
        selectLoaiKhachHang: { enabled: false, invalid: false },
        txtIdHeThong: { enabled: false, invalid: false },
        txtTenHeThong: { enabled: false, invalid: false },
        selectIDC: { enabled: false, invalid: false },
        txtNgayLapDat: { enabled: false, invalid: false },
        txtCanCuLapDat: { enabled: false, invalid: false },
        selectTrangThaiVanHanh: { enabled: false, invalid: false },
        selectTrangThaiTaiSan: { enabled: false, invalid: false },
        txtMoTa: { enabled: false, invalid: false },
        txtNgayTao: { enabled: false, invalid: false },
        txtNgayCapNhat: { enabled: false, invalid: false },
        txtGhiChu: { enabled: false, invalid: false },
        txtKhachHang: { enabled: false, invalid: false },
        txtSoHopDong: { enabled: false, invalid: false },
        txtMaThueBao: { enabled: false, invalid: false }
      }
    }
  },
  mounted: function() {}
}
</script>
<style>
.popup-box {
  max-height: 90vh !important;
  overflow-y: auto;
}
</style>
