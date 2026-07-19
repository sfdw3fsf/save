<template src="./index.template.html"></template>
<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import api from '../../../api'
import { FIELD_VISIBILITY_CONFIG, checkFieldVisibility, getDeviceTypeFlags } from './fieldVisibility.js'
import { getFieldOrder, groupFieldsIntoRows } from './fieldOrderConfig.js'
import { isFieldRequired as checkFieldRequired } from './fieldValidation.js'
import { DEVICE_TYPE_ID } from '../../../const.js'
import DanhSachSoLuongOCam from './components/DanhSachSoLuongOCam/index.vue'
import DanhSachOCam from './components/DanhSachOCam/index.vue'

export default {
  name: 'ThongTinChung',
  components: {
    Treeselect,
    DanhSachSoLuongOCam,
    DanhSachOCam
  },
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    currentMode: {
      type: String,
      required: true
    },
    controls: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      DEVICE_TYPE_ID,
      // Danh mục
      mangThietBiList: [],
      loaiThietBiList: [],
      hangSanXuatList: [],
      trangThaiVanHanhList: [],
      nhaCungCapList: [],
      heThongList: [],
      // Danh mục mới
      chungLoaiThietBiList: [],
      phanLoaiThietBiList: [],
      soHuuThietBiList: [], // Sở hữu thiết bị - gọi từ API
      loaiKhachHangList: [],
      kieuLapDatList: [], // Kiểu lắp đặt: 1-rPDU ngang, 2-rPDU dọc
      loaiNguonDienList: [], // Loại nguồn điện: 1 - 1 pha / 3 - 3 pha
      loaiMayList: [], // Loại máy: Hạ áp / Tăng áp
      // Danh mục cho ổ cắm
      loaiOCamList: [], // Loại ổ cắm
      trangThaiOCamList: [], // Trạng thái ổ cắm
      trangThaiKetNoiList: [], // Trạng thái kết nối
      thietBiList: [], // Danh sách thiết bị để kết nối
      uploadingAvatar: false,
      avatarPreviewUrl: '', // Sẽ được set từ presigned URL hoặc object URL
      avatarObjectUrl: '',
      avatarPresignedUrl: '' // Lưu presigned URL để hiển thị
    }
  },
  computed: {
    // Lấy cụm control thông tin chung
    thongTinChungControl() {
      return this.controls?.thongTinChungControl || {}
    },
    congSuatDinhDanh() {
      const dienAp = parseFloat(this.currentItem.dienApDinhDanh) || 0
      const dongDien = parseFloat(this.currentItem.dongDienDinhDanh) || 0
      if (dienAp > 0 && dongDien > 0) {
        return (dienAp * dongDien).toFixed(2)
      }
      return ''
    },
    // Tỷ lệ % sử dụng cho máy biến áp (tự động tính)
    tyLeSuDungComputed() {
      if (!this.deviceTypeInfo.isMayBienAp) {
        return ''
      }
      const congSuatThucTe = parseFloat(this.currentItem.congSuatThucTe) || 0
      const congSuatDinhDanh = parseFloat(this.currentItem.congSuatDinhDanh) || parseFloat(this.congSuatDinhDanh) || 0
      if (congSuatDinhDanh > 0) {
        const tyLe = (congSuatThucTe / congSuatDinhDanh) * 100
        return tyLe.toFixed(2)
      }
      return ''
    },
    displayAvatarUrl() {
      // Ưu tiên: object URL (khi đang upload) > presigned URL > relative URL
      if (this.avatarObjectUrl) {
        return this.avatarObjectUrl
      }
      if (this.avatarPresignedUrl) {
        return this.avatarPresignedUrl
      }
      // Nếu là relative URL thì cần gọi getPresignedUrl (sẽ được xử lý trong watch/mounted)
      return this.currentItem.anhDaiDien || this.currentItem.icons || ''
    },
    /**
     * Tạo deviceTypeInfo object từ loaiThietBiId
     * @returns {Object} Object chứa device type flags
     */
    deviceTypeInfo() {
      const deviceTypeId = this.currentItem?.loaiThietBiId
      if (!deviceTypeId) {
        return getDeviceTypeFlags(null)
      }
      return getDeviceTypeFlags(deviceTypeId)
    },
    dacTinhKyThuatFields() {
      const fields = getFieldOrder('dacTinhKyThuat', this.deviceTypeInfo)
      return fields.filter((fieldKey) => this.isFieldVisible(fieldKey))
    },
    dacTinhKyThuatRows() {
      return groupFieldsIntoRows(this.dacTinhKyThuatFields, this.getFieldsPerRow('dacTinhKyThuat'))
    },
    isUploading() {
      return this.uploadingAvatar
    }
  },
  watch: {
    currentItem: {
      handler() {
        this.syncAvatarPreviewFromCurrentItem()
      },
      deep: false
    },
    'currentItem.mangThietBiId': async function(newVal) {
      if (newVal && newVal !== 0 && this.currentMode === 'VIEW') {
        await this.loadLoaiThietBiFromMangThietBi()
      } else {
        this.loaiThietBiList = []
        this.phanLoaiThietBiList = []
      }
    },
    'currentItem.loaiThietBiId': async function(newVal) {
      if (newVal && newVal !== 0) {
        await this.loadPhanLoaiThietBiList()
      } else {
        this.phanLoaiThietBiList = []
      }
    },
    'currentItem.dienApDinhDanh': function() {
      this.calculatePower()
    },
    'currentItem.dongDienDinhDanh': function() {
      this.calculatePower()
    },
    'currentItem.congSuatThucTe': function() {
      // Cập nhật tỷ lệ sử dụng khi công suất thực tế thay đổi
      if (this.deviceTypeInfo.isMayBienAp) {
        this.updateTyLeSuDung()
      }
    },
    'currentItem.congSuatDinhDanh': function() {
      // Cập nhật tỷ lệ sử dụng khi công suất định danh thay đổi
      if (this.deviceTypeInfo.isMayBienAp) {
        this.updateTyLeSuDung()
      }
    },
    'currentItem.anhDaiDien': async function(newVal, oldVal) {
      if (this.avatarObjectUrl) {
        // Đang upload, không cập nhật
        return
      }
      // Chỉ xử lý khi giá trị thực sự thay đổi
      if (newVal === oldVal) {
        return
      }
      if (newVal) {
        // Kiểm tra xem có phải là presigned URL (chứa http/https) hay relative URL
        if (newVal.startsWith('http://') || newVal.startsWith('https://')) {
          // Đã là presigned URL, dùng luôn
          this.avatarPresignedUrl = newVal
          this.avatarPreviewUrl = newVal
        } else {
          // Là relative URL - luôn gọi getPresignedUrl để lấy presigned URL cho việc hiển thị
          await this.loadPresignedUrl(newVal)
        }
      } else {
        this.avatarPresignedUrl = ''
        this.avatarPreviewUrl = ''
      }
    },
    'currentItem.icons': async function(newVal, oldVal) {
      // Nếu không có anhDaiDien thì dùng icons
      if (!this.currentItem.anhDaiDien && newVal) {
        if (this.avatarObjectUrl) {
          return
        }
        // Chỉ xử lý khi giá trị thực sự thay đổi
        if (newVal === oldVal) {
          return
        }
        if (newVal.startsWith('http://') || newVal.startsWith('https://')) {
          this.avatarPresignedUrl = newVal
          this.avatarPreviewUrl = newVal
        } else {
          // Là relative URL - luôn gọi getPresignedUrl để lấy presigned URL cho việc hiển thị
          await this.loadPresignedUrl(newVal)
        }
      }
    }
  },
  methods: {
    async syncAvatarPreviewFromCurrentItem() {
      if (this.avatarObjectUrl) {
        return
      }
      const iconPath = this.currentItem?.anhDaiDien || this.currentItem?.icons
      if (!iconPath) {
        this.avatarPresignedUrl = ''
        this.avatarPreviewUrl = ''
        return
      }
      if (iconPath.startsWith('http://') || iconPath.startsWith('https://')) {
        this.avatarPresignedUrl = iconPath
        this.avatarPreviewUrl = iconPath
        return
      }
      await this.loadPresignedUrl(iconPath)
    },
    resetValidation() {
      if (!this.thongTinChungControl) {
        return
      }
      Object.keys(this.thongTinChungControl).forEach((key) => {
        const control = this.thongTinChungControl[key]
        if (control && control.invalid) {
          this.$set(control, 'invalid', false)
        }
      })
    },
    validateForm() {
      const item = this.currentItem || {}
      let isValid = true
      this.resetValidation()

      const require = (condition, controlKey) => {
        if (condition) {
          return
        }
        const control = this.thongTinChungControl?.[controlKey]
        if (control) {
          this.$set(control, 'invalid', true)
        }
        isValid = false
      }

      require(item.tenThietBi && item.tenThietBi.trim() !== '', 'txtTenThietBi')
      require(item.mangThietBiId && item.mangThietBiId !== 0, 'selectMangThietBi')
      require(item.loaiThietBiId && item.loaiThietBiId !== 0, 'selectLoaiThietBi')
      require(item.phanLoaiThietBiId && item.phanLoaiThietBiId !== 0, 'selectPhanLoaiThietBi')
      require(item.soHuuThietBiId && item.soHuuThietBiId !== 0, 'selectSoHuuThietBi')
      require(item.serialNumber && item.serialNumber.trim() !== '', 'txtSerialNumber')
      require(item.hangSanXuatId && item.hangSanXuatId !== 0, 'selectHangSanXuat')
      require(item.dongSanPham && item.dongSanPham.trim() !== '', 'txtDongSanPham')
      require(item.trangThaiVanHanhId && item.trangThaiVanHanhId !== 0, 'selectTrangThaiVanHanh')
      const needsKhongGian =
        this.deviceTypeInfo.isRPDU ||
        this.deviceTypeInfo.isUPS ||
        this.deviceTypeInfo.isTuTrungThe ||
        this.deviceTypeInfo.isTuDienHaThe ||
        this.deviceTypeInfo.isTuDienATS ||
        this.deviceTypeInfo.isTuPhanPhoi

      if (needsKhongGian) {
        require(item.khongGianLapDat && item.khongGianLapDat > 0, 'txtKhongGianLapDat')
      }

      const needsDienAp =
        this.deviceTypeInfo.isTuTrungThe ||
        this.deviceTypeInfo.isTuDienHaThe ||
        this.deviceTypeInfo.isTuDienATS ||
        this.deviceTypeInfo.isMayBienAp ||
        this.deviceTypeInfo.isThapGianNhiet ||
        this.deviceTypeInfo.isMayBom

      if (needsDienAp) {
        require(item.dienApDinhDanh && item.dienApDinhDanh > 0, 'txtDienApDinhDanh')
      }

      const needsDongDinhDanh =
        this.deviceTypeInfo.isTuTrungThe || this.deviceTypeInfo.isMayBienAp || this.deviceTypeInfo.isThapGianNhiet

      if (needsDongDinhDanh) {
        require(item.dongDienDinhDanh && item.dongDienDinhDanh > 0, 'txtDongDienDinhDanh')
      }

      const needsLoaiNguon =
        this.deviceTypeInfo.isTuTrungThe ||
        this.deviceTypeInfo.isTuDienHaThe ||
        this.deviceTypeInfo.isTuDienATS ||
        this.deviceTypeInfo.isMayBienAp ||
        this.deviceTypeInfo.isUPS

      if (needsLoaiNguon) {
        require(item.loaiNguonDien && item.loaiNguonDien !== 0, 'selectLoaiNguonDien')
      }

      const needsDongThucTe =
        this.deviceTypeInfo.isMayPhatDien ||
        this.deviceTypeInfo.isTuTrungThe ||
        this.deviceTypeInfo.isTuDienHaThe ||
        this.deviceTypeInfo.isUPS ||
        this.deviceTypeInfo.isMayBom

      if (needsDongThucTe) {
        require(item.dongDienThucTe && item.dongDienThucTe > 0, 'txtDongDienThucTe')
      }

      if (this.deviceTypeInfo.isUPS) {
        require(item.cauHinhHeThong && item.cauHinhHeThong.trim() !== '', 'txtCauHinhHeThong')
      }

      if (this.deviceTypeInfo.isMayBienAp) {
        require(item.loaiMay && item.loaiMay !== 0, 'selectLoaiMay')
        require(item.congSuatDinhDanh && item.congSuatDinhDanh > 0, 'txtCongSuatDinhDanh')
        require(item.heSoCosPhi && item.heSoCosPhi > 0, 'txtHeSoCosPhi')
      }

      // Validate danh sách ổ cắm cho RPDU: thiết bị kết nối bắt buộc
      if (
        this.deviceTypeInfo.isRPDU &&
        item.danhSachOCam &&
        Array.isArray(item.danhSachOCam) &&
        item.danhSachOCam.length > 0
      ) {
        const oCamWithoutThietBi = item.danhSachOCam.find((ocam) => {
          const thietBiKnId = ocam.THIETBIKN_ID || ocam.thietBiKetNoiId
          return !thietBiKnId || thietBiKnId === 0
        })
        if (oCamWithoutThietBi) {
          this.$root.toastError('Vui lòng chọn thiết bị kết nối cho tất cả các ổ cắm!')
          isValid = false
        }
      }

      if (!isValid) {
        this.$root.toastError('Vui lòng nhập đầy đủ thông tin bắt buộc!')
      }

      return isValid
    },
    formatDateValue(value) {
      if (!value) {
        return null
      }
      return this.$moment ? this.$moment(value).format('YYYY-MM-DDTHH:mm:ss') : value
    },
    sanitizePayload(payload = {}) {
      const sanitized = {}
      Object.keys(payload).forEach((key) => {
        const value = payload[key]
        if (value !== undefined) {
          sanitized[key] = value
        }
      })
      return sanitized
    },
    buildThongTinChungPayload() {
      const item = this.currentItem || {}
      const payload = {
        id: item.id,
        ten: item.tenThietBi,
        maTbi: item.maThietBi,
        maTs: item.maTheTaiSan || null,
        kyHieu: item.kyHieuThietBi || item.tagNumber || null,
        serialNumber: item.serialNumber,
        model: item.dongSanPham,
        ghiChu: item.ghiChu,
        hieuLuc: item.hieuLuc,
        fullDepth: item.fullDepth,
        loaiKhachHang: item.loaiKhachHangId,
        chuQuanId: item.soHuuThietBiId,
        donViQLyId: item.donViQuanLyId,
        nguoiQLyId: item.nguoiQuanLyId,
        trangThaiVhId: item.trangThaiVanHanhId,
        trangThaiTsId: item.trangThaiTaiSanId,
        hangSxId: item.hangSanXuatId,
        nhaccId: item.nhaCungCapId,
        mangTbiId: item.mangThietBiId,
        heThongTbiId: item.heThongId,
        loaiThietBiId: item.loaiThietBiId,
        loaiTbiId: item.loaiThietBiId,
        phanLoaiTbiId: item.phanLoaiThietBiId,
        chungLoaiThietBiId: item.chungLoaiThietBiId,
        chungLoaiTbiId: item.chungLoaiThietBiId,
        csDinhDanh: item.congSuatDinhDanh,
        csThucTe: item.congSuatThucTe,
        daDinhDanh: item.dienApDinhDanh,
        ddDinhDanh: item.dongDienDinhDanh,
        ddThucTe: item.dongDienThucTe,
        loaiNdien: item.loaiNguonDien,
        loaiMay: item.loaiMay,
        heSoCosPhi: item.heSoCosPhi,
        tyLeSuDung: item.tyLeSuDung,
        idcId: item.phamViIdcId || item.lapDatTaiIdcId,
        lapDatTaiIdcId: item.lapDatTaiIdcId,
        toanhaId: item.toaNhaId,
        matSanId: item.matSanId,
        phongId: item.phongZoneId,
        idcRackId: item.rackId,
        upUnitId: item.uTrenId,
        downUnitId: item.uDuoiId,
        slRackUnit: item.khongGianLapDat,
        kieuLapDat: item.kieuLapDatId,
        cauHinh: item.cauHinhHeThong,
        dai: item.chieuDai,
        rong: item.chieuRong,
        cao: item.chieuCao,
        iconPath: item.icons || item.anhDaiDien,
        danhSachOCam: this.mapDanhSachOCamToPayload(item.danhSachOCam || []),
        ngayLapDat: this.formatDateValue(item.thoiDiemLapDat),
        ngaySudung: this.formatDateValue(item.ngayDuaVaoSuDung)
      }
      return this.sanitizePayload(payload)
    },
    mapDanhSachOCamToPayload(danhSachOCam) {
      if (!Array.isArray(danhSachOCam) || danhSachOCam.length === 0) {
        return []
      }
      return danhSachOCam.map((ocam) => {
        const payload = {
          loaiOCamId: ocam.LOAIOCAM_ID || ocam.loaiOCamId || null,
          ten: ocam.TEN || ocam.ten || '',
          trangThaiOCam: ocam.TRANGTHAI_PORT !== undefined ? parseInt(ocam.TRANGTHAI_PORT, 10) : null,
          trangThaiKetNoi: ocam.TRANGTHAIKN_ID !== undefined ? parseInt(ocam.TRANGTHAIKN_ID, 10) : null,
          thietBiKetNoiId: ocam.THIETBIKN_ID || ocam.thietBiKetNoiId || null
        }
        return payload
      })
    },
    calculatePower() {
      const dienAp = parseFloat(this.currentItem.dienApDinhDanh) || 0
      const dongDien = parseFloat(this.currentItem.dongDienDinhDanh) || 0
      if (dienAp > 0 && dongDien > 0) {
        this.$set(this.currentItem, 'congSuatThietKe', dienAp * dongDien)
      }
    },
    normalizer(node) {
      return {
        id: node.ID || node.id,
        label: node.TEN || node.name || node.label,
        children: node.children || node.items
      }
    },
    async loadInitialData() {
      try {
        this.$root.showLoading(true)
        const loadPromises = [
          this.loadMangThietBiList(),
          this.loadHangSanXuatList(),
          this.loadTrangThaiVanHanhList(),
          this.loadNhaCungCapList(),
          this.loadHeThongList(),
          this.loadChungLoaiThietBiList(),
          this.loadLoaiKhachHangList(),
          this.loadSoHuuThietBiList(),
          this.loadKieuLapDatList(),
          this.loadLoaiNguonDienList(),
          this.loadLoaiMayList()
        ]
        await Promise.all(loadPromises)

        // Load danh mục cho ổ cắm nếu là RPDU
        if (this.currentItem.loaiThietBiId === DEVICE_TYPE_ID.RPDU) {
          await this.loadDanhMucOCam()
        }
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải danh mục')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async loadMangThietBiList() {
      try {
        this.mangThietBiList = await api.getDanhMucMangThietBi(this.axios)
      } catch (error) {
        this.mangThietBiList = []
      }
    },
    async loadLoaiThietBiFromMangThietBi() {
      try {
        this.loaiThietBiList = await api.getDanhMucLoaiThietBi(this.axios, this.currentItem.mangThietBiId)
      } catch (error) {
        this.loaiThietBiList = []
      }
    },
    async loadHangSanXuatList() {
      try {
        this.hangSanXuatList = await api.getDanhMucHangSanXuat(this.axios)
      } catch (error) {
        this.hangSanXuatList = []
      }
    },
    async loadTrangThaiVanHanhList() {
      try {
        this.trangThaiVanHanhList = await api.getDanhMucTrangThaiVanHanh(this.axios)
      } catch (error) {
        this.trangThaiVanHanhList = []
      }
    },
    async loadNhaCungCapList() {
      try {
        this.nhaCungCapList = await api.getDanhMucNhaCungCap(this.axios)
      } catch (error) {
        this.nhaCungCapList = []
      }
    },
    async loadHeThongList() {
      try {
        this.heThongList = await api.getDanhMucHeThongThietBi(this.axios, this.$root.token)
      } catch (error) {
        this.heThongList = []
      }
    },
    // Load danh mục mới
    async loadChungLoaiThietBiList() {
      try {
        this.chungLoaiThietBiList = await api.getDanhMucChungLoaiThietBi(this.axios)
      } catch (error) {
        this.chungLoaiThietBiList = []
      }
    },
    async loadPhanLoaiThietBiList() {
      try {
        if (this.currentItem.loaiThietBiId) {
          this.phanLoaiThietBiList = await api.getDanhMucPhanLoaiThietBi(this.axios, this.currentItem.loaiThietBiId)
        } else {
          this.phanLoaiThietBiList = []
        }
      } catch (error) {
        this.phanLoaiThietBiList = []
      }
    },
    async loadLoaiKhachHangList() {
      try {
        this.loaiKhachHangList = await api.getDanhMucLoaiKhachHang(this.axios)
      } catch (error) {
        this.loaiKhachHangList = []
      }
    },
    async loadSoHuuThietBiList() {
      try {
        this.soHuuThietBiList = await api.getDanhMucChuQuanSoHuuThietBi(this.axios)
      } catch (error) {
        this.soHuuThietBiList = []
      }
    },
    loadKieuLapDatList() {
      // Mảng sẵn cho Kiểu lắp đặt
      this.kieuLapDatList = [
        { ID: 1, TEN: 'rPDU ngang' },
        { ID: 2, TEN: 'rPDU dọc' }
      ]
    },
    loadLoaiNguonDienList() {
      // Mảng sẵn cho Loại nguồn điện
      this.loaiNguonDienList = [
        { ID: 1, TEN: '1 pha' },
        { ID: 3, TEN: '3 pha' }
      ]
    },
    loadLoaiMayList() {
      // Mảng sẵn cho Loại máy biến áp
      this.loaiMayList = [
        { ID: 1, TEN: 'Hạ áp' },
        { ID: 2, TEN: 'Tăng áp' }
      ]
    },
    async loadDanhMucOCam() {
      try {
        await Promise.all([
          this.loadLoaiOCamList(),
          this.loadTrangThaiOCamList(),
          this.loadTrangThaiKetNoiList(),
          this.loadThietBiList()
        ])
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải danh mục ổ cắm')
      }
    },
    async loadLoaiOCamList() {
      try {
        this.loaiOCamList = await api.getDanhMucLoaiOCam(this.axios)
      } catch (error) {
        this.loaiOCamList = []
      }
    },
    async loadTrangThaiOCamList() {
      this.trangThaiOCamList = [
        { ID: 0, TEN: 'Hỏng' },
        { ID: 1, TEN: 'Hoạt động' }
      ]
    },
    async loadTrangThaiKetNoiList() {
      this.trangThaiKetNoiList = [
        { ID: 0, TEN: 'Chưa kết nối' },
        { ID: 1, TEN: 'Đã kết nối' }
      ]
    },
    async loadThietBiList() {
      try {
        // Lấy danh sách thiết bị từ API để kết nối
        this.thietBiList = await api.getDanhMucThietBiKetNoi(this.axios)
      } catch (error) {
        this.thietBiList = []
      }
    },
    // Hàm helper để lấy tên thiết bị theo ID
    getThietBiNameById(thietBiId) {
      if (!thietBiId) return ''
      const thietBi = this.thietBiList.find((t) => t.id === thietBiId || t.ID === thietBiId)
      if (thietBi) {
        return thietBi.ten || thietBi.TEN || thietBi.tenThietBi || thietBi.THIETBI_TEN || ''
      }
      return `ID: ${thietBiId}`
    },
    updateTyLeSuDung() {
      const tyLe = this.tyLeSuDungComputed
      if (tyLe !== '') {
        this.$set(this.currentItem, 'tyLeSuDung', parseFloat(tyLe))
      } else {
        this.$set(this.currentItem, 'tyLeSuDung', null)
      }
    },
    triggerAvatarUpload() {
      if (this.currentMode === 'VIEW') {
        return
      }
      if (this.$refs.avatarInput) {
        this.$refs.avatarInput.value = null
        this.$refs.avatarInput.click()
      }
    },
    async onAvatarFileChange(event) {
      try {
        const file = event?.target?.files?.[0]
        if (!file) {
          return
        }
        
        if (!file.type?.startsWith('image/')) {
          this.$root.toastError('Vui lòng chọn đúng định dạng ảnh')
          return
        }

        // Hiển thị preview tạm thời
        this.setAvatarObjectUrl(URL.createObjectURL(file))

        const uploadResult = await this.uploadAvatarFile(file)
        if (!uploadResult) {
          this.clearAvatarObjectUrl()
          return
        }
        
        const { previewUrl, storedUrl } = this.resolveUploadedUrl(uploadResult)
        
        // storedUrl dùng để lưu vào database, previewUrl dùng để xem
        if (!storedUrl && !previewUrl) {
          this.$root.toastError('Không thể xác định đường dẫn ảnh sau khi upload')
          this.clearAvatarObjectUrl()
          return
        }

        // Lưu storedUrl (relative URL) vào currentItem để gửi lên server
        if (storedUrl) {
          this.$set(this.currentItem, 'anhDaiDien', storedUrl)
          this.$set(this.currentItem, 'icons', storedUrl)
        }

        // Dùng previewUrl (presigned URL) để hiển thị ngay nếu có
        if (previewUrl) {
          this.avatarPresignedUrl = previewUrl
          this.avatarPreviewUrl = previewUrl
        } else if (storedUrl) {
          // Nếu không có previewUrl, gọi getPresignedUrl để lấy presigned URL từ storedUrl để hiển thị ngay
          await this.loadPresignedUrl(storedUrl)
        }

        this.clearAvatarObjectUrl()
      } catch (error) {
        this.$root.toastError(error?.message || 'Upload ảnh không thành công')
        this.clearAvatarObjectUrl()
      }
    },
    async uploadAvatarFile(file) {
      try {
        this.uploadingAvatar = true
        this.$emit('uploading-changed', true)
        const formData = new FormData()
        formData.append('file', file)
        
        const response = await this.$root.context.post('/web-ecms/storage/uploadFile', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        
        const uploadData = this.extractUploadPayload(response)
        if (uploadData) {
          return uploadData
        }
        
        const errorMessage = response?.data?.message || response?.message || response?.data?.error || 'Upload ảnh thất bại'
        this.$root.toastError(errorMessage)
        return null
      } catch (error) {
        const errorMessage = error?.response?.data?.message || 
                           error?.response?.data?.error || 
                           error?.message || 
                           'Không thể upload ảnh'
        this.$root.toastError(errorMessage)
        return null
      } finally {
        this.uploadingAvatar = false
        this.$emit('uploading-changed', false)
      }
    },
    resolveUploadedUrl(uploadResult) {
      if (!uploadResult) {
        return { previewUrl: '', storedUrl: '' }
      }
      const previewUrl = uploadResult.presignedUrl || ''
      const storedUrl = uploadResult.relativeUrl || ''
      return { previewUrl, storedUrl }
    },
    extractUploadPayload(response) {
      if (!response) {
        return null
      }
      if ((response.error_code === 'BSS-00000000' || response.error == 200) && response?.data?.status) {
        return response.data.message
      }
      return null
    },
    setAvatarObjectUrl(url) {
      this.clearAvatarObjectUrl()
      this.avatarObjectUrl = url
      this.avatarPreviewUrl = url
    },
    clearAvatarObjectUrl() {
      if (this.avatarObjectUrl) {
        try {
          URL.revokeObjectURL(this.avatarObjectUrl)
        } catch (e) {}
      }
      this.avatarObjectUrl = ''
    },
    /**
     * Lấy presigned URL từ relative URL để hiển thị ảnh
     * @param {string} relativeUrl - Relative URL từ database
     */
    async loadPresignedUrl(relativeUrl) {
      if (!relativeUrl) {
        return
      }
      
      // Nếu đã là presigned URL (chứa http/https) thì không cần gọi API
      if (relativeUrl.startsWith('http://') || relativeUrl.startsWith('https://')) {
        this.avatarPresignedUrl = relativeUrl
        this.avatarPreviewUrl = relativeUrl
        return
      }

      try {
        const response = await this.$root.context.post('/web-ecms/storage/getPresignedUrl', {
          fileSource: relativeUrl
        })

        // Xử lý nhiều format response
        let presignedUrl = null
        if (response && response.error == 200 && response.data && response.data.result) {
          presignedUrl = response.data.result
        }

        if (presignedUrl) {
          this.avatarPresignedUrl = presignedUrl
          this.avatarPreviewUrl = presignedUrl
        }
      } catch (error) {
      }
    },
    /**
     * Kiểm tra xem field có được hiển thị dựa trên deviceTypeInfo
     * @param {string} fieldKey - Tên field cần kiểm tra
     * @returns {boolean} true nếu field được hiển thị, false nếu không
     */
    isFieldVisible(fieldKey) {
      const condition = FIELD_VISIBILITY_CONFIG[fieldKey]
      return checkFieldVisibility(this.deviceTypeInfo, condition)
    },
    /**
     * Lấy label cho field
     * @param {string} fieldKey - Tên field
     * @returns {string} Label của field
     */
    getFieldLabel(fieldKey) {
      const labels = {
        kieuLapDatId: 'Kiểu lắp đặt',
        dienApDinhDanh: 'Điện áp định danh (V)',
        dongDienDinhDanh: 'Dòng điện định danh (A)',
        congSuatDinhDanh: `Công suất định danh ${this.deviceTypeInfo.isMayBienAp ? '(kVA)' : '(W)'}`,
        congSuatThucTe: 'Công suất thực tế (kVA)',
        khongGianLapDat: 'Không gian lắp đặt (U)',
        chieuDai: `Chiều dài ${this.deviceTypeInfo.isRPDU ? '(mm)' : '(cm)'}`,
        chieuRong: `Chiều rộng ${this.deviceTypeInfo.isRPDU ? '(mm)' : '(cm)'}`,
        chieuCao: `Chiều cao ${this.deviceTypeInfo.isRPDU ? '(mm)' : '(cm)'}`,
        loaiNguonDien: 'Loại nguồn điện',
        loaiMay: 'Loại máy',
        heSoCosPhi: 'Hệ số cos phi',
        tyLeSuDung: 'Tỷ lệ % sử dụng',
        dongDienThucTe: 'Dòng điện thực tế (A)',
        cauHinhHeThong: 'Cấu hình hệ thống'
      }
      return labels[fieldKey] || fieldKey
    },
    /**
     * Kiểm tra field có required không dựa trên validation config
     * @param {string} fieldKey - Tên field
     * @returns {boolean} true nếu required
     */
    isFieldRequired(fieldKey) {
      return checkFieldRequired(fieldKey, this.deviceTypeInfo)
    },
    /**
     * Lấy control key từ field key để kiểm tra invalid state
     * @param {string} fieldKey - Tên field
     * @returns {string|null} Control key tương ứng
     */
    getControlKeyFromFieldKey(fieldKey) {
      const fieldToControlMap = {
        mangThietBiId: 'selectMangThietBi',
        heThongId: 'selectHeThong',
        loaiThietBiId: 'selectLoaiThietBi',
        chungLoaiThietBiId: 'selectChungLoaiThietBi',
        phanLoaiThietBiId: 'selectPhanLoaiThietBi',
        soHuuThietBiId: 'selectSoHuuThietBi',
        maThietBi: 'txtMaThietBi',
        serialNumber: 'txtSerialNumber',
        dongSanPham: 'txtDongSanPham',
        hangSanXuatId: 'selectHangSanXuat',
        nhaCungCapId: 'selectNhaCungCap',
        tenThietBi: 'txtTenThietBi',
        kyHieuThietBi: 'txtKyHieuThietBi',
        loaiKhachHangId: 'selectLoaiKhachHang',
        ghiChu: 'txtGhiChu',
        trangThaiVanHanhId: 'selectTrangThaiVanHanh',
        kieuLapDatId: 'selectKieuLapDat',
        cauHinhHeThong: 'txtCauHinhHeThong',
        dienApDinhDanh: 'txtDienApDinhDanh',
        dongDienDinhDanh: 'txtDongDienDinhDanh',
        dongDienThucTe: 'txtDongDienThucTe',
        congSuatDinhDanh: 'txtCongSuatDinhDanh',
        congSuatThucTe: 'txtCongSuatThucTe',
        khongGianLapDat: 'txtKhongGianLapDat',
        chieuDai: 'txtChieuDai',
        chieuRong: 'txtChieuRong',
        chieuCao: 'txtChieuCao',
        loaiNguonDien: 'selectLoaiNguonDien',
        loaiMay: 'selectLoaiMay',
        heSoCosPhi: 'txtHeSoCosPhi'
      }
      return fieldToControlMap[fieldKey] || null
    },
    /**
     * Kiểm tra field có invalid không
     * @param {string} fieldKey - Tên field
     * @returns {boolean} true nếu invalid
     */
    isFieldInvalid(fieldKey) {
      const controlKey = this.getControlKeyFromFieldKey(fieldKey)
      if (!controlKey) return false
      const control = this.thongTinChungControl?.[controlKey]
      return control && control.invalid === true
    },
    /**
     * Lấy class cột theo section
     * @param {string} section - Tên section (system/basic/dacTinhKyThuat)
     * @returns {string} class cột Bootstrap
     */
    getSectionColClass(section) {
      const sectionClassMap = {
        system: 'col-sm-4 col-12',
        basic: 'col-sm-4 col-12',
        dacTinhKyThuat: 'col-sm-6 col-12'
      }
      return sectionClassMap[section] || 'col-sm-4 col-12'
    },
    /**
     * Lấy số field trên mỗi row theo section
     * @param {string} section
     * @returns {number}
     */
    getFieldsPerRow(section) {
      const sectionRowMap = {
        system: 3,
        basic: 3,
        dacTinhKyThuat: 2
      }
      return sectionRowMap[section] || 3
    },
    generateTempId() {
      // Tạo UUID tạm thời cho ổ cắm mới
      return (
        'temp_' +
        Date.now() +
        '_' +
        Math.random()
          .toString(36)
          .substr(2, 9)
      )
    },
    onAddSoLuongOCam(data) {
      if (!this.currentItem.danhSachOCam) {
        this.$set(this.currentItem, 'danhSachOCam', [])
      }

      const { loaiOCamId, tenOCam, soLuong } = data

      // Tạo số lượng ổ cắm tương ứng với cùng tên và cùng loại
      // Tất cả đều có cùng tên (không thêm số thứ tự)
      for (let i = 1; i <= soLuong; i++) {
        const newItem = {
          tempId: this.generateTempId(), // UUID tạm thời
          RPDUOCAM_ID: null,
          TEN: tenOCam, // Tất cả đều có cùng tên
          LOAIOCAM_ID: loaiOCamId, // Tất cả đều có cùng loại
          TRANGTHAI_PORT: 1, // Mặc định: Hoạt động
          TRANGTHAIKN_ID: 0, // Mặc định: Chưa kết nối
          THIETBIKN_ID: null
        }
        this.currentItem.danhSachOCam.push(newItem)
      }

      this.$set(this.currentItem, 'danhSachOCam', [...this.currentItem.danhSachOCam])
    },
    onEditSoLuongOCam(item) {
      if (!this.currentItem.danhSachOCam) {
        this.$set(this.currentItem, 'danhSachOCam', [])
        return
      }

      const { loaiOCamId, tenOCam, soLuong, originalSoLuong } = item
      const soLuongMoi = parseInt(soLuong, 10)
      const soLuongCu = parseInt(originalSoLuong, 10)

      if (soLuongMoi === soLuongCu) {
        // Số lượng không thay đổi, không cần làm gì
        return
      }

      // Tìm tất cả ổ cắm có cùng loại và tên
      const matchedOCam = this.currentItem.danhSachOCam.filter((ocam) => {
        const ocamLoaiId = ocam.LOAIOCAM_ID || ocam.loaiOCamId
        const ocamTen = ocam.TEN || ocam.ten || ''
        return ocamLoaiId === loaiOCamId && ocamTen === tenOCam
      })

      if (soLuongMoi > soLuongCu) {
        // Thêm ổ cắm mới
        const soLuongThem = soLuongMoi - soLuongCu
        for (let i = 1; i <= soLuongThem; i++) {
          const newItem = {
            tempId: this.generateTempId(),
            RPDUOCAM_ID: null,
            TEN: tenOCam,
            LOAIOCAM_ID: loaiOCamId,
            TRANGTHAI_PORT: 1, // Mặc định: Hoạt động
            TRANGTHAIKN_ID: 0, // Mặc định: Chưa kết nối
            THIETBIKN_ID: null
          }
          this.currentItem.danhSachOCam.push(newItem)
        }
      } else {
        // Xóa bớt ổ cắm (xóa từ cuối danh sách)
        const soLuongXoa = soLuongCu - soLuongMoi
        let countXoa = 0
        // Xóa từ cuối danh sách ngược lại
        for (let i = this.currentItem.danhSachOCam.length - 1; i >= 0 && countXoa < soLuongXoa; i--) {
          const ocam = this.currentItem.danhSachOCam[i]
          const ocamLoaiId = ocam.LOAIOCAM_ID || ocam.loaiOCamId
          const ocamTen = ocam.TEN || ocam.ten || ''
          if (ocamLoaiId === loaiOCamId && ocamTen === tenOCam) {
            this.currentItem.danhSachOCam.splice(i, 1)
            countXoa++
          }
        }
      }

      this.$set(this.currentItem, 'danhSachOCam', [...this.currentItem.danhSachOCam])
    },
    onDeleteSoLuongOCam(item) {
      if (!this.currentItem.danhSachOCam) return
      const loaiOCamId = item.loaiOCamId
      const tenOCam = item.tenOCam
      const filteredList = this.currentItem.danhSachOCam.filter((ocam) => {
        const ocamLoaiId = ocam.LOAIOCAM_ID || ocam.loaiOCamId
        const ocamTen = ocam.TEN || ocam.ten || ''
        return !(ocamLoaiId === loaiOCamId && ocamTen === tenOCam)
      })

      this.$set(this.currentItem, 'danhSachOCam', filteredList)
    },
    onViewChiTietKetNoi(item) {
      // TODO: Implement view chi tiet ket noi
    },
    onAddOCam(item) {
      if (!this.currentItem.danhSachOCam) {
        this.$set(this.currentItem, 'danhSachOCam', [])
      }
      // Tạo item mới với format đúng
      const newItem = {
        tempId: this.generateTempId(), // UUID tạm thời
        RPDUOCAM_ID: null,
        TEN: item.TEN || '',
        LOAIOCAM_ID: item.LOAIOCAM_ID || null,
        TRANGTHAI_PORT: item.TRANGTHAI_PORT !== undefined ? item.TRANGTHAI_PORT : 1, // Mặc định: Hoạt động
        TRANGTHAIKN_ID: item.TRANGTHAIKN_ID !== undefined ? item.TRANGTHAIKN_ID : 0, // Mặc định: Chưa kết nối
        THIETBIKN_ID: item.THIETBIKN_ID || null
      }

      this.currentItem.danhSachOCam.push(newItem)
      this.$set(this.currentItem, 'danhSachOCam', [...this.currentItem.danhSachOCam])
    },
    onUpdateOCam(item) {
      if (this.loaiOCamList.length === 0) {
        this.loadDanhMucOCam()
      }
      if (!this.currentItem.danhSachOCam) return

      const index = this.currentItem.danhSachOCam.findIndex(
        (o) =>
          (item.tempId && o.tempId === item.tempId) ||
          (item.RPDUOCAM_ID && o.RPDUOCAM_ID && o.RPDUOCAM_ID === item.RPDUOCAM_ID) ||
          (item.originalData && item.originalData.tempId && o.tempId === item.originalData.tempId) ||
          (item.originalData &&
            item.originalData.RPDUOCAM_ID &&
            o.RPDUOCAM_ID &&
            o.RPDUOCAM_ID === item.originalData.RPDUOCAM_ID)
      )

      if (index !== -1) {
        const updatedItem = {
          ...this.currentItem.danhSachOCam[index],
          TEN: item.TEN || this.currentItem.danhSachOCam[index].TEN,
          LOAIOCAM_ID:
            item.LOAIOCAM_ID !== undefined ? item.LOAIOCAM_ID : this.currentItem.danhSachOCam[index].LOAIOCAM_ID,
          TRANGTHAI_PORT:
            item.TRANGTHAI_PORT !== undefined
              ? item.TRANGTHAI_PORT
              : this.currentItem.danhSachOCam[index].TRANGTHAI_PORT,
          TRANGTHAIKN_ID:
            item.TRANGTHAIKN_ID !== undefined
              ? item.TRANGTHAIKN_ID
              : this.currentItem.danhSachOCam[index].TRANGTHAIKN_ID,
          THIETBIKN_ID:
            item.THIETBIKN_ID !== undefined ? item.THIETBIKN_ID : this.currentItem.danhSachOCam[index].THIETBIKN_ID
        }

        this.$set(this.currentItem.danhSachOCam, index, updatedItem)
        this.$set(this.currentItem, 'danhSachOCam', [...this.currentItem.danhSachOCam])
      }
    },
    onDeleteOCam(item) {
      // Xóa ổ cắm khỏi danh sách
      if (!this.currentItem.danhSachOCam) return

      const originalData = item.originalData || item
      const index = this.currentItem.danhSachOCam.findIndex(
        (o) =>
          (originalData.tempId && o.tempId === originalData.tempId) ||
          (originalData.RPDUOCAM_ID && o.RPDUOCAM_ID && o.RPDUOCAM_ID === originalData.RPDUOCAM_ID) ||
          (o.RPDUOCAM_ID && o.RPDUOCAM_ID === item.RPDUOCAM_ID) ||
          (o.id && o.id === item.id) ||
          (o.ID && o.ID === item.ID)
      )

      if (index !== -1) {
        this.currentItem.danhSachOCam.splice(index, 1)
        this.$set(this.currentItem, 'danhSachOCam', [...this.currentItem.danhSachOCam])
      }
    }
  },
  async mounted() {
    await this.syncAvatarPreviewFromCurrentItem()
  },
  beforeDestroy() {
    this.clearAvatarObjectUrl()
  }
}
</script>
<style scoped>
.form-control::placeholder {
  color: #999 !important;
}
/deep/ .vue-treeselect__placeholder {
  color: #999 !important;
}

.info-row {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}

.key {
  min-width: 140px;
  padding-right: 10px;
}

.value {
  flex: 1;
}

.padt4 {
  padding-top: 4px;
}

.check-action {
  display: flex;
  align-items: center;
}

.check-action .check {
  margin-right: 5px;
}

.red {
  color: red;
}

.avatar-upload .avatar-thumb {
  max-height: 120px;
  width: auto;
  border-radius: 6px;
  object-fit: cover;
}

.avatar-upload .avatar-preview {
  max-width: 180px;
}
.is-invalid {
  border: 1px solid #dc3545 !important;
  border-radius: 4px !important;
  box-shadow: none !important; /* Xóa bóng mờ */
  outline: none !important; /* Xóa viền outline mặc định */
}
</style>
