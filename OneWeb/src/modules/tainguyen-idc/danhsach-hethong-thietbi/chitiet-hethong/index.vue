<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import moment from 'moment'
import TabHistory from '../../components/Tabs/TabHistory/index.vue' // import component TabHistory
import api from '../api'

export default {
  name: 'ModalChiTietHeThong',
  props: { modalID: null },
  components: {
    TabHistory
  },
  created: async function() {
    await this.loadInitialData()
    // Set default value for Don vi quan ly
    this.currentItem.DONVI_QL_ID = this.$root.token.getDonViID() || ''
  },
  validations: {},
  watch: {
    'currentItem.TEN': function() {
      this.controls.txtTenHeThong.invalid = false
    },
    'currentItem.LOAIKH_ID': function() {
      this.controls.selectLoaiKhachHang.invalid = false
    },
    'currentItem.MANGTBI_ID': function() {
      this.controls.selectMangThietBi.invalid = false
    },
    'currentItem.IDC_ID': function() {
      this.controls.selectIDC.invalid = false
    },
    'currentItem.DONVI_QL_ID': function() {
      this.controls.selectDonViQuanLy.invalid = false
    },
    'currentItem.NHANVIEN_QL_ID': function() {
      this.controls.selectNhanVienQuanLy.invalid = false
    },
    'currentItem.TRANGTHAI_VH_ID': function() {
      this.controls.selectTrangThaiVanHanh.invalid = false
    },
    'currentItem.TRANGTHAI_TS_ID': function() {
      this.controls.selectTrangThaiTaiSan.invalid = false
    },
    'currentItem.NGAY_LD': function() {
      this.controls.txtNgayLapDat.invalid = false
    },
    'currentItem.CANCU_YC': function() {
      this.controls.txtCanCuLapDat.invalid = false
    },
  },
  computed: {},
  methods: {
    closeModal: function() {
      this.resetValidation()
      this.$refs.ModalChiTietHeThong.hide(this.modalID)
    },
    show: function(item, mode) {
      if (item) {
        this.currentItem = { ...item, LOAIKH_ID: item.LOAIKH_ID ? item.LOAIKH_ID : 0}
        this.currentMode = mode
      } else {
        this.resetCurrentItem()
        this.currentMode = mode
      }
      this.item = item
      this.tab_index = 1 // Reset to first tab
      this.$refs.ModalChiTietHeThong.show()
    },

    onAddNew: function() {
      this.resetCurrentItem()
      this.currentMode = 'ADD'
      this.resetValidation()
    },

    onSave: async function() {
      if (this.validateForm()) {
        try {
          this.$root.showLoading(true)
          if (this.currentMode === 'ADD') {
            await this.saveNewItem()
          } else {
            await this.updateItem()
          }
          this.$root.toastSuccess('Lưu thành công!')
          this.closeModal()
          this.$emit('refreshData')
        } catch (error) {
          this.$root.toastError(error.message || 'Đã xảy ra lỗi!')
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    // Change handlers for SelectExt components (kept for future use if needed)
    onChangeLoaiKhachHang: function() {
      // Handle change logic if needed
    },

    onChangeMangThietBi: function() {
      // Handle change logic if needed
    },

    onChangeIDC: function() {
      // Handle change logic if needed
    },

    onChangeDonViQuanLy: function() {
      // Handle change logic if needed
    },

    onChangeTrangThaiVanHanh: function() {
      // Handle change logic if needed
    },

    onChangeTrangThaiTaiSan: function() {
      // Handle change logic if needed
    },

    onChangeNhanVienQuanLy: function() {
      // Handle change logic if needed
    },

    // Helper methods
    isEmptyOrWhitespace: function(str) {
      return !str || !str.toString().trim()
    },
    resetCurrentItem: function() {
      this.currentItem = {
        HETHONGTBI_ID: '',
        LOAIKH_ID: 0,
        TEN: '',
        MANGTBI_ID: '',
        IDC_ID: '',
        DONVI_QL_ID: this.$root.token.getDonViID() || '',
        NHANVIEN_QL_ID: '',
        TRANGTHAI_VH_ID: '',
        TRANGTHAI_TS_ID: '',
        MOTA: '',
        NGAY_CAP_NHAT: '',
        NGAY_LD: '',
        CANCU_YC: '',
        GHICHU: '',
        SU_DUNG: 1
      }
    },

    resetValidation: function() {
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })
    },

    validateForm: function() {
      let isValid = true
      this.resetValidation()

      // Loại khách hàng (required)
      if (this.currentItem.LOAIKH_ID === null || this.currentItem.LOAIKH_ID === undefined) {
        this.controls.selectLoaiKhachHang.invalid = true
        isValid = false
      }

      if (!this.currentItem.TEN || this.isEmptyOrWhitespace(this.currentItem.TEN)) {
        this.controls.txtTenHeThong.invalid = true
        isValid = false
      }

      if (this.currentItem.MANGTBI_ID === null || this.currentItem.MANGTBI_ID === undefined || this.currentItem.MANGTBI_ID === '') {
        this.controls.selectMangThietBi.invalid = true
        isValid = false
      }

      if (this.currentItem.IDC_ID === null || this.currentItem.IDC_ID === undefined || this.currentItem.IDC_ID === '') {
        this.controls.selectIDC.invalid = true
        isValid = false
      }

      if (this.currentItem.TRANGTHAI_TS_ID === null || this.currentItem.TRANGTHAI_TS_ID === undefined) {
        this.controls.selectTrangThaiTaiSan.invalid = true
        isValid = false
      }

      if (this.currentItem.TRANGTHAI_VH_ID === null || this.currentItem.TRANGTHAI_VH_ID === undefined || this.currentItem.TRANGTHAI_VH_ID === '') {
        this.controls.selectTrangThaiVanHanh.invalid = true
        isValid = false
      }

      if (this.currentItem.DONVI_QL_ID === null || this.currentItem.DONVI_QL_ID === undefined || this.currentItem.DONVI_QL_ID === '') {
        this.controls.selectDonViQuanLy.invalid = true
        isValid = false
      }

      if (this.currentItem.NHANVIEN_QL_ID === null || this.currentItem.NHANVIEN_QL_ID === undefined || this.currentItem.NHANVIEN_QL_ID === '') {
        this.controls.selectNhanVienQuanLy.invalid = true
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
        await Promise.all([
          this.loadLoaiKhachHangList(),
          this.loadMangThietBiList(),
          this.loadIDCList(),
          this.loadDonViQuanLyList(),
          this.loadTrangThaiVanHanhList(),
          this.loadTrangThaiTaiSanList(),
          this.loadNhanVienList()
        ])
        this.trangThaiTaiSanList = [
          { ID: '0', TEN: 'Đang sử dụng' },
          { ID: '1', TEN: 'Dừng hoạt động' },
          { ID: '2', TEN: 'Hỏng' },
          { ID: '3', TEN: 'Đã thanh lý' }
        ]
      } catch (error) {
        this.$root.toastError(error.message || 'Đã xảy ra lỗi!')
      } finally {
        this.$root.showLoading(false)
      }
    },

    // Load dropdown data methods
    loadLoaiKhachHangList: async function() {
      try {
        this.loaiKhachHangList = await api.getDanhMucLoaiKhachHang(this.axios)
      } catch (error) {
        console.error('Error loading Loai khach hang list:', error)
        this.loaiKhachHangList = []
      }
    },

    loadMangThietBiList: async function() {
      try {
        this.mangThietBiList = await api.getDanhMucMangThietBi(this.axios)
      } catch (error) {
        console.error('Error loading Mang thiet bi list:', error)
        this.mangThietBiList = []
      }
    },

    loadIDCList: async function() {
      try {
        this.idcList = await api.getDanhMucIDC(this.axios, this.$root.token)
      } catch (error) {
        console.error('Error loading IDC list:', error)
        this.idcList = []
      }
    },

    loadDonViQuanLyList: async function() {
      try {
        this.donViQuanLyList = await api.getDanhMucDonViQuanLy(this.axios, this.$root.token)
      } catch (error) {
        console.error('Error loading Don vi quan ly list:', error)
        this.donViQuanLyList = []
      }
    },

    loadTrangThaiVanHanhList: async function() {
      try {
        this.trangThaiVanHanhList = await api.getDanhMucTrangThaiVanHanh(this.axios)
      } catch (error) {
        console.error('Error loading Trang thai van hanh list:', error)
        this.trangThaiVanHanhList = []
      }
    },

    loadTrangThaiTaiSanList: async function() {
      try {
        this.trangThaiTaiSanList = await api.getDanhMucTrangThaiTaiSan(this.axios)
      } catch (error) {
        console.error('Error loading Trang thai tai san list:', error)
        this.trangThaiTaiSanList = []
      }
    },

    loadNhanVienList: async function() {
      try {
        this.nhanVienList = await api.getDanhMucNhanVienQuanLy(this.axios, this.$root.token)
      } catch (error) {
        console.error('Error loading Nhan vien list:', error)
        this.nhanVienList = []
      }
    },

    // CRUD operations
    saveNewItem: async function() {
      try {
        const apiData = this.transformToApiFormat(this.currentItem)
        await api.upsertHeThongThietBi(this.axios, apiData)
      } catch (error) {
        throw error
      }
    },

    updateItem: async function() {
      try {
        const apiData = this.transformToApiFormat(this.currentItem)
        await api.upsertHeThongThietBi(this.axios, apiData)
      } catch (error) {
        throw error
      }
    },

    deleteItem: async function() {
      try {
        await api.deleteMultiHeThongThietBi(this.axios, [this.currentItem.HETHONGTBI_ID])
      } catch (error) {
        throw error
      }
    },

    // Data transformation helper
    transformToApiFormat: function(item) {
      return {
        id: this.currentMode === 'ADD' ? null : item.HETHONGTBI_ID || null,
        ten: item.TEN,
        mangThietBiId: parseInt(item.MANGTBI_ID) || null,
        idcId: parseInt(item.IDC_ID) || null,
        donviQlId: parseInt(item.DONVI_QL_ID) || null,
        nhanvienQlId: parseInt(item.NHANVIEN_QL_ID) || null,
        trangthaiVhId: parseInt(item.TRANGTHAI_VH_ID ?? null),
        trangThaiTaiSanId: parseInt(item.TRANGTHAI_TS_ID ?? null),
        loaiKhId: parseInt(item.LOAIKH_ID ?? null),
        mota: item.MOTA || '',
        ghichu: item.GHICHU || '',
        ngayLd: item.NGAY_LD ? moment(item.NGAY_LD).format('YYYY-MM-DD') : null,
        cancuYc: item.CANCU_YC || '',
        sudung: item.SU_DUNG || 1
      }
    },

    normalizeDate(dateStr) {
      if (!dateStr) return null
      const d = new Date(dateStr)
      return isNaN(d.getTime()) ? null : d.toISOString().split('T')[0]
    },

    // Tab navigation methods
    onChangeTab: function(tabIndex) {
      this.tab_index = tabIndex
      // Refresh history data when switching to history tab
      if (tabIndex === 2 && this.$refs.tabHistory) {
        this.$refs.tabHistory.refreshData()
      }
    }
  },
  data: function() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      item: null,
      currentMode: 'VIEW',
      tab_index: 1, // Tab index for navigation
      currentItem: {
        HETHONGTBI_ID: '',
        LOAIKH_ID: 0,
        TEN: '',
        MANGTBI_ID: '',
        IDC_ID: '',
        DONVI_QL_ID: '',
        NHANVIEN_QL_ID: '',
        TRANGTHAI_VH_ID: '',
        TRANGTHAI_TS_ID: '',
        MOTA: '',
        NGAY_CAP_NHAT: '',
        NGAY_LD: '',
        CANCU_YC: '',
        GHICHU: '',
        SU_DUNG: 1
      },

      // Dropdown lists
      loaiKhachHangList: [],
      mangThietBiList: [],
      idcList: [],
      donViQuanLyList: [],
      trangThaiVanHanhList: [],
      trangThaiTaiSanList: [],
      nhanVienList: [],

      // Form controls
      controls: {
        txtIdHeThong: { enabled: false, invalid: false },
        selectLoaiKhachHang: { enabled: false, invalid: false },
        txtTenHeThong: { enabled: false, invalid: false },
        selectMangThietBi: { enabled: false, invalid: false },
        selectIDC: { enabled: false, invalid: false },
        selectDonViQuanLy: { enabled: false, invalid: false },
        selectNhanVienQuanLy: { enabled: false, invalid: false },
        selectTrangThaiVanHanh: { enabled: false, invalid: false },
        selectTrangThaiTaiSan: { enabled: false, invalid: false },
        txtNgayLapDat: { enabled: false, invalid: false },
        txtCanCuLapDat: { enabled: false, invalid: false },
        txtNgayTao: { enabled: false, invalid: false },
        txtMoTa: { enabled: false, invalid: false },
        txtNgayCapNhat: { enabled: false, invalid: false },
        txtGhiChu: { enabled: false, invalid: false }
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

.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}

/* Date picker validation styling */
.date-picker.e-error .e-input-group {
  border-color: #dc3545 !important;
  box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.25) !important;
}
</style>
