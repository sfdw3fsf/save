<template src="./index.template.html"></template>
<script>
import moment from 'moment'
import TabHistory from '@/modules/tainguyen-idc/components/Tabs/TabHistory'
import ThongTinChung from './components/ThongTinChung/index.vue'
import api from '../api'

export default {
  name: 'ChiTietThietBiPageV2',
  components: {
    TabHistory,
    ThongTinChung
  },
  props: {},
  data: function() {
    return {
      item: null,
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      tab_index: 1, // Default active tab
      deviceType: null,
      currentItem: {
        id: null,
        donViQuanLyId: 0,
        nguoiQuanLyId: null,
        mangThietBiId: null,
        loaiThietBiId: null,
        heThongId: null,
        tenThietBi: '',
        dongSanPham: '',
        serialNumber: null,
        thoiDiemLapDat: null,
        ngayDuaVaoSuDung: null,
        tagNumber: '',
        maTheTaiSan: '',
        trangThaiTaiSanId: null,
        congSuatThietKe: null,
        trangThaiVanHanhId: null,
        icons: '',
        soCongNguonDauVao: null,
        hangSanXuatId: null,
        namSanXuat: new Date().getFullYear(),
        noiSanXuatId: null,
        soCongNguonDauRa: null,
        ghiChu: '',
        thietBiChaId: null,
        thanhPhanThietBiIds: [],
        nhaCungCapId: null,
        phamViIdcId: null,
        // Thông tin lắp đặt
        lapDatTaiIdcId: null,
        toaNhaId: null,
        matSanId: null,
        phongZoneId: null,
        rackId: null,
        uTrenId: null,
        uDuoiId: null
      },
      // Loading state
      isLoadingItem: false,
      // Form controls
      controls: {
        selectDonViQuanLy: { enabled: false, invalid: false },
        selectNhanVienQuanLy: { enabled: false, invalid: false },
        selectMangThietBi: { enabled: false, invalid: false },
        selectLoaiThietBi: { enabled: false, invalid: false },
        selectHeThong: { enabled: false, invalid: false },
        selectPhamViIdc: { enabled: false, invalid: false },
        txtMaTaiSan: { enabled: false, invalid: false },
        txtTenThietBi: { enabled: false, invalid: false },
        txtDongSanPham: { enabled: false, invalid: false },
        txtSerialNumber: { enabled: false, invalid: false },
        txtThoiDiemLapDat: { enabled: false, invalid: false },
        txtNgayDuaVaoSuDung: { enabled: false, invalid: false },
        txtTagNumber: { enabled: false, invalid: false },
        txtMaTheTaiSan: { enabled: false, invalid: false },
        selectTrangThaiTaiSan: { enabled: false, invalid: false },
        txtCongSuatThietKe: { enabled: false, invalid: false },
        selectTrangThaiVanHanh: { enabled: false, invalid: false },
        txtSoCongNguonDauVao: { enabled: false, invalid: false },
        selectHangSanXuat: { enabled: false, invalid: false },
        txtNamSanXuat: { enabled: false, invalid: false },
        selectNoiSanXuat: { enabled: false, invalid: false },
        txtSoCongNguonDauRa: { enabled: false, invalid: false },
        txtGhiChu: { enabled: false, invalid: false },
        selectThietBiCha: { enabled: false, invalid: false },
        selectThanhPhanThietBi: { enabled: false, invalid: false },
        // Controls mới từ component ThongTinChung
        selectChungLoaiThietBi: { enabled: false, invalid: false },
        selectPhanLoaiThietBi: { enabled: false, invalid: false },
        selectSoHuuThietBi: { enabled: false, invalid: false },
        txtMaThietBi: { enabled: false, invalid: false },
        selectNhaCungCap: { enabled: false, invalid: false },
        txtKyHieuThietBi: { enabled: false, invalid: false },
        selectLoaiKhachHang: { enabled: false, invalid: false },
        selectKieuLapDat: { enabled: false, invalid: false },
        txtDienApDinhDanh: { enabled: false, invalid: false },
        txtKhongGianLapDat: { enabled: false, invalid: false },
        txtDongDienDinhDanh: { enabled: false, invalid: false }
      }
    }
  },
  watch: {
    'currentItem.loaiThietBiId': {
      handler(newVal) {
        this.deviceType = newVal || null
      },
      immediate: true
    }
  },
  methods: {
    onChangeTab: function(index) {
      this.tab_index = index
    },
    onEditDetail: function() {
      // Chuyển sang chế độ EDIT
      this.currentMode = 'EDIT'
      this.$emit('mode-changed', 'EDIT')
    },
    onAddNew: function() {
      // Chuyển sang chế độ ADD và reset dữ liệu
      this.resetCurrentItem()
      this.currentMode = 'ADD'
      this.resetThongTinChungValidation()
      this.tab_index = 1
      this.$emit('mode-changed', 'ADD')
    },
    async onRefreshDetail() {
      // Làm mới dữ liệu chi tiết hiện tại
      if (this.currentItem && this.currentItem.id) {
        await this.loadItemDetail(this.currentItem.id)
      } else if (this.item && (this.item.id || this.item.ID)) {
        const itemId = this.item.id || this.item.ID
        await this.loadItemDetail(itemId)
      }
    },
    async onCancel() {
      // Reset về chế độ VIEW
      if (this.currentMode === 'ADD') {
        // Nếu đang ở chế độ ADD, quay lại
        this.$emit('close')
      } else if (this.currentMode === 'EDIT') {
        // Nếu đang ở chế độ EDIT, reload lại dữ liệu và chuyển về VIEW
        if (this.currentItem && this.currentItem.id) {
          await this.loadItemDetail(this.currentItem.id)
          this.currentMode = 'VIEW'
          this.$emit('mode-changed', 'VIEW')
        } else {
          this.currentMode = 'VIEW'
          this.$emit('mode-changed', 'VIEW')
        }
      }
      this.resetThongTinChungValidation()
    },
    goBack: function() {
      if (this.$router) {
        this.$router.go(-1)
      } else {
        this.$emit('close')
      }
    },
    show: async function(item, mode) {
      try {
        console.log('Page show called with:', { item, mode })
        this.currentMode = mode
        this.item = item

        if (item && (item.id || item.ID) && (mode === 'VIEW' || mode === 'EDIT')) {
          const itemId = item.id || item.ID
          await this.loadItemDetail(itemId)
        } else if (item) {
          this.currentItem = { ...item }
          this.deviceType = item.loaiThietBiId || null
        } else {
          this.resetCurrentItem()
        }

        await this.setDefaultDonViQuanLyId()
      } catch (error) {
        this.$root.toastError(error.message)
      }
    },
    resetCurrentItem: function() {
      this.currentItem = {
        id: null,
        donViQuanLyId: 0,
        nguoiQuanLyId: null,
        mangThietBiId: null,
        loaiThietBiId: null,
        heThongId: null,
        tenThietBi: '',
        dongSanPham: '',
        serialNumber: null,
        thoiDiemLapDat: null,
        ngayDuaVaoSuDung: null,
        tagNumber: '',
        maTheTaiSan: '',
        trangThaiTaiSanId: null,
        congSuatThietKe: null,
        trangThaiVanHanhId: null,
        icons: '',
        soCongNguonDauVao: null,
        hangSanXuatId: null,
        namSanXuat: new Date().getFullYear(),
        noiSanXuatId: null,
        soCongNguonDauRa: null,
        ghiChu: '',
        thietBiChaId: null,
        thanhPhanThietBiIds: [],
        nhaCungCapId: null,
        phamViIdcId: null,
        lapDatTaiIdcId: null,
        toaNhaId: null,
        matSanId: null,
        phongZoneId: null,
        rackId: null,
        uTrenId: null,
        uDuoiId: null
      }
      this.deviceType = null
    },
    resetThongTinChungValidation: function() {
      if (this.$refs.ThongTinChung && this.$refs.ThongTinChung.resetValidation) {
        this.$refs.ThongTinChung.resetValidation()
      }
    },
    onSave: async function() {
      const isThongTinChungValid = this.$refs.ThongTinChung.validateForm()
      if (!isThongTinChungValid) {
        return
      }
      try {
        this.$root.showLoading(true)

        const data = { ...this.currentItem }

        if (data.thoiDiemLapDat) {
          data.thoiDiemLapDat = moment(data.thoiDiemLapDat).format('YYYY-MM-DDTHH:mm:ss')
        }
        if (data.ngayDuaVaoSuDung) {
          data.ngayDuaVaoSuDung = moment(data.ngayDuaVaoSuDung).format('YYYY-MM-DDTHH:mm:ss')
        }

        if (!data.thanhPhanThietBiIds || !Array.isArray(data.thanhPhanThietBiIds) || data.thanhPhanThietBiIds.length === 0) {
          data.thanhPhanThietBiIds = null
        }

        const newId = await api.upsertThietBiIDC(this.axios, data)
        this.currentItem.id = newId

        this.$root.toastSuccess('Lưu thành công!')
        
        // Chuyển về chế độ VIEW sau khi lưu thành công
        if (this.currentMode === 'ADD' || this.currentMode === 'EDIT') {
          // Reload lại dữ liệu
          if (this.currentItem.id) {
            await this.loadItemDetail(this.currentItem.id)
          }
          this.currentMode = 'VIEW'
          this.$emit('mode-changed', 'VIEW')
          this.$emit('saved')
        }
        
        if (this.$parent && this.$parent.loadDanhSachThietbi) {
          this.$parent.loadDanhSachThietbi()
        }
      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    loadItemDetail: async function(itemId) {
      try {
        this.isLoadingItem = true
        this.$root.showLoading(true)
        const response = await api.getChiTietThietBiIDC(this.axios, itemId)

        if (response) {
          this.currentItem = this.convertApiDataToCurrentItem(response)
          this.$nextTick(() => {
            this.deviceType = this.currentItem.loaiThietBiId || null
          })
          // Đảm bảo mode là VIEW khi load chi tiết
          if (this.currentMode !== 'ADD') {
            this.currentMode = 'VIEW'
          }
        } else {
          this.$root.toastError('Không thể tải thông tin chi tiết thiết bị')
        }
      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
        this.isLoadingItem = false
      }
    },
    convertApiDataToCurrentItem: function(apiData) {
      return {
        id: apiData.ID || null,
        donViQuanLyId: apiData.DONVIQLY_ID || null,
        nguoiQuanLyId: apiData.NGUOIQLY_ID || null,
        mangThietBiId: apiData.MANGTBI_ID || null,
        loaiThietBiId: apiData.LOAITHIETBI_ID || null,
        heThongId: apiData.HETHONGTBI_ID || null,
        tenThietBi: apiData.TEN || null,
        thanhPhanThietBiIds: apiData.THANHPHAN_TBI_ID ? JSON.parse(apiData.THANHPHAN_TBI_ID) : [],
        dongSanPham: apiData.MODEL || null,
        serialNumber: apiData.SERIAL_NUMBER || null,
        thoiDiemLapDat: apiData.NGAY_LAPDAT || null,
        ngayDuaVaoSuDung: apiData.NGAY_SUDUNG || null,
        maTheTaiSan: apiData.MA_TS || null,
        trangThaiTaiSanId: apiData.TRANGTHAI_TS_ID,
        congSuatThietKe: apiData.CS_DINHDANH || null,
        trangThaiVanHanhId: apiData.TRANGTHAI_SD || null,
        icons: apiData.ICON_PATH || null,
        soCongNguonDauVao: apiData.SO_PORT_CONSOLE || null,
        hangSanXuatId: apiData.HANGSX_ID || null,
        namSanXuat: apiData.NAMSX,
        noiSanXuatId: apiData.NHACUNGCAP_ID || null,
        soCongNguonDauRa: apiData.SO_PORT_INTERFACE || null,
        ghiChu: apiData.GHI_CHU || null,
        thietBiChaId: apiData.THIETBICHA_ID || null,
        tagNumber: apiData.PART_NUMBER || null,
        nhaCungCapId: apiData.NHACUNGCAP_ID || null,
        phamViIdcId: apiData.IDC_ID || null,
        lapDatTaiIdcId: apiData.LAPDAT_IDC_ID || null,
        toaNhaId: apiData.TOANHA_ID || null,
        matSanId: apiData.MATSAN_ID || null,
        phongZoneId: apiData.PHONG_ID || null,
        rackId: apiData.IDC_RACK_ID || null,
        uTrenId: apiData.UP_UNIT_ID || null,
        uDuoiId: apiData.DOWN_UNIT_ID || null
      }
    },
    setDefaultDonViQuanLyId: async function() {
      const donViId = await this.$root.token.getDonViID()
      if (donViId && !this.currentItem.donViQuanLyId) {
        this.$set(this.currentItem, 'donViQuanLyId', donViId)
      }
    },
    handleError: function(error) {
      if (error.message) {
        this.$root.toastError(error.message)
      } else {
        this.$root.toastError('Đã xảy ra lỗi!')
      }
    }
  }
}
</script>
<style>
.w200 {
  min-width: 180px !important;
}
.device-detail-page {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.page-header {
  padding: 15px 20px;
  background-color: #fff;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.page-header .title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  display: flex;
  align-items: center;
  gap: 10px;
}

.page-content {
  flex: 1;
  overflow-y: auto;
  background-color: #f5f5f5;
}
</style>

