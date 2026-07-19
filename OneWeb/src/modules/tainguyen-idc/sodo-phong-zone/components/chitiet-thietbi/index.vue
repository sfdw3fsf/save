<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import TabInterface from '../../../components/Tabs/TabInterface/index.vue'
import TabPower from '../../../components/Tabs/TabPower/index.vue'
import TabConsole from '../../../components/Tabs/TabConsole/index.vue'
import TabHistory from '../../../components/Tabs/TabHistory/index.vue'
import ThongTinPort from '../../../quanly-thietbi-khachhang/chitiet-thietbi-khachhang/components/ThongTinPort/index.vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ModalPortThietBi from '../../../components/Modals/ModalPortThietBi/index.vue'
import RackPosition from '../../../sodo-daunoi-mang/components/RackPosition.vue'
import KetNoi from '../../../sodo-daunoi-mang/components/KetNoi.vue'
import api from './api'
import moment from 'moment'

// Constants cho phân loại port
const PHANLOAI_NHOM = {
  INTERFACE: 1,
  POWER: 2,
  CONSOLE: 3
}
export default {
  name: 'ModalChiTietThietBi',
  props: { 
    thietBiId: null,
    isNetworkDevice: {
      type: Boolean,
      default: false
    },
  },
  components: {
    TabInterface,
    TabPower,
    TabConsole,
    TabHistory,
    ThongTinPort,
    ModalPortThietBi,
    BssErrorMarker,
    BssRequiredMarker,
    RackPosition,
    KetNoi,
  },
  created: async function () {
    // await this.loadInitialData()
  },
  validations: {},
  watch: {
    'currentItem.lapDatTaiIdcId': function (newVal, oldVal) {
      this.onChangeLapDatTaiIdc()
    },
    'currentItem.toaNhaId': function (newVal, oldVal) {
      this.onChangeToaNha()
    },
    'currentItem.matSanId': function (newVal, oldVal) {
      this.onChangeMatSan()
    },
    'currentItem.phongZoneId': function (newVal, oldVal) {
      this.onChangePhongZone()
    },
    'currentItem.rack': function (newVal, oldVal) {
      this.onChangeRack()
    }
  },
  computed: {
    modalTitle() {
      if (this.currentMode === 'VIEW') {
        return ' Chi tiết thiết bị: ' + (this.item && this.item.TEN ? this.item.TEN : 'Chưa có tên')
      } else if (this.currentMode === 'EDIT') {
        return ' Chỉnh sửa thiết bị: ' + (this.item && this.item.TEN ? this.item.TEN : 'Chưa có tên')
      } else {
        return ' Thêm mới thiết bị'
      }
    }
  },
  methods: {
    closeModal: function () {
      this.resetCurrentItem()
      this.resetValidation()
      this.isModalVisible = false
      this.tab_index = 1
    },
    onChangeTab: function (index) {
      this.tab_index = index
    },
    show: async function (item, mode) {
      // Set modal visible ngay lập tức để tránh phải click 2 lần
      this.isModalVisible = true
      this.loadInitialData();
      if (!item && this.thietBiId) {
        try {
          let rs = await this.$root.context.get('/web-ecms/idc/quanly-thietbi/detail/' + this.thietBiId)
          item = rs.data
        } catch (error) {
          console.error('Error loading device detail:', error)
          this.handleError(error)
          this.closeModal()
          return
        }
      }
      if (item) {
        this.currentMode = mode
        let newItem = {
          id: item.ID,
          phamViIDCId: item.PHAMVI_IDC_ID,
          donViQLId: item.DONVIQLY_ID,
          mangThietBiId: item.MANGTBI_ID,
          loaiThietBiId: item.LOAITHIETBI_ID,
          loaiKhachHangId: item.LOAIKHACHHANG,
          heThongId: item.HETHONGTBI_ID,
          lapDatTaiIdcId: item.IDC_ID,
          tenThietBi: item.TEN,
          toaNhaId: item.TOANHA_ID,
          matSanId: item.MATSAN_ID,
          phongZoneId: item.PHONG_ID,
          dongSanPham: item.MODEL,
          rack: item.IDC_RACK_ID,
          uTrenId: item.UP_UNIT_ID,
          uDuoiId: item.DOWN_UNIT_ID,
          uTrenPos: item.UP_UNIT_POS,
          uDuoiPos: item.DOWN_UNIT_POST,
          serialNumber: item.SERIAL_NUMBER,
          thoiDiemLapDat: item.NGAY_LAPDAT,
          tagNumber: item.PART_NUMBER,
          maTheTaiSan: item.MA_TS,
          trangThaiTaiSanId: item.TRANGTHAI_TS_ID,
          congSuatThietKe: item.CS_DINHDANH,
          trangThaiVanHanhId: item.TRANGTHAI_SD,
          icons: item.ICON_PATH,
          soCongNguonDauVao: item.SO_PORT_CONSOLE,
          hangSanXuatId: item.HANGSX_ID,
          noiSanXuatId: item.NHACUNGCAP_ID,
          namSanXuat: item.NAMSX,
          soCongNguonDauRa: item.SO_PORT_INTERFACE,
          ghiChu: item.GHI_CHU,
          khachHang: item.TEN_TB,
          soHopDong: item.SO_HOPDONG,
          maThueBao: item.MA_TB,
          thanhPhanThietBiIds: JSON.parse(item.THANHPHAN_IDS) || [],
          hdtbId: item.HDTB_ID,
          tinhId: item.TINH_ID,
          portList: item.portList || [] // Load portList nếu có
        }
        this.currentItem = { ...newItem }
        this.item = item

        // Load existing image if available
        if (this.currentItem.icons) {
          await this.loadExistingImage(this.currentItem.icons)
        }

        // Load danh sách port nếu đang xem/sửa thiết bị
        if (mode === 'VIEW' || mode === 'EDIT') {
          await this.loadPortList(this.currentItem.id)
        }
      } else {
        this.resetCurrentItem()
        this.currentMode = mode
        this.item = item
      }
    },

    onAddNew: function () {
      this.resetCurrentItem()
      this.currentMode = 'ADD'
      this.resetValidation()
      this.tab_index = 1
      // Reset portList cho thiết bị mới
      this.currentItem.portList = []
      // Reset image upload variables
      this.pendingIconFile = null
      this.pendingIconPreviewUrl = ''
      this.displayIconUrl = ''
      this.pendingRemoveIcon = false
    },

    onSave: async function () {
      if (this.validateForm()) {
        try {
          this.$root.showLoading(true)

          // Handle image upload/removal before saving
          if (this.pendingRemoveIcon && this.currentItem.icons) {
            await this.deleteFileFromMinio(this.currentItem.icons)
            this.currentItem.icons = ''
          }

          // If there's a new image to upload
          if (this.pendingIconFile) {
            if (this.currentItem.icons) {
              await this.deleteFileFromMinio(this.currentItem.icons)
            }
            const uploadRs = await this.uploadFileToMinio(this.pendingIconFile)
            if (uploadRs && uploadRs.relativeUrl) {
              this.currentItem.icons = uploadRs.relativeUrl
              const viewUrl = await this.getPresignedUrlFromMinio(this.currentItem.icons)
              this.displayIconUrl = viewUrl || ''
            } else {
              this.$root.toastError('Tải ảnh thất bại')
              return
            }
          }

          if (this.currentMode === 'ADD') {
            await this.saveNewItem()
          } else {
            await this.updateItem()
          }
        } catch (error) {
          this.handleError(error)
        } finally {
          this.closeModal()
          this.$root.showLoading(false)
        }
      }
    },

    showModalChonRack() {
      this.$refs.ModalChonRack.show()
    },

    showModalPortThietBi(portData = null) {
      this.$refs.ModalPortThietBi.show(portData)
    },

    // Xử lý khi port được lưu từ modal - được ủy quyền cho ThongTinPort component
    onPortSaved(portData) {
      // Gọi method của ThongTinPort component
      if (this.$refs.thongTinPort) {
        this.$refs.thongTinPort.onPortSaved(portData)
      }
    },

    // Xử lý khi có thay đổi port từ ThongTinPort component
    onPortChanged() {
      // Port đã được cập nhật trong currentItem.portList bởi ThongTinPort component
      console.log('Port list updated:', this.currentItem.portList)
    },

    // Load danh sách port của thiết bị
    async loadPortList(thietBiId) {
      try {
        if (!thietBiId || thietBiId === 0) {
          this.currentItem.portList = []
          return
        }

        this.$root.showLoading(true)
        const portList = await api.getDanhSachPortThietBiKhachHang(this.axios, thietBiId)
        this.currentItem.portList = portList || []
        console.log('Loaded port list:', this.currentItem.portList)
      } catch (error) {
        console.error('Error loading port list:', error)
        this.currentItem.portList = []
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },

    // Change handlers for SelectExt components
    onChangePhamViIDC: function () {
      // Handle change logic
    },

    onChangeMangThietBi: function () {
      // Handle change logic
    },

    onChangeLoaiThietBi: function () {
      // Handle change logic
    },

    onChangeLoaiKhachHang: function () {
      // Handle change logic
    },

    onChangeHeThong: function () {
      // Handle change logic
    },

    onChangeLapDatTaiIdc: async function () {
      try {
        if (this.currentItem.lapDatTaiIdcId) {
          this.toaNhaList = await api.getDanhMuc(this.axios, 'IDC-TOANHA-BY-IDC', this.currentItem.lapDatTaiIdcId)
        } else {
          this.toaNhaList = []
        }
      } catch (error) {
        console.log(error)
        this.handleError(error)
      }
    },
    onChangeToaNha: async function () {
      try {
        if (this.currentItem.toaNhaId) {
          this.matSanList = await api.getDanhMuc(this.axios, 'IDC-MATSAN-BY-TOANHA', this.currentItem.toaNhaId)
        } else {
          this.matSanList = []
        }
      } catch (error) {
        console.log(error)
        this.handleError(error)
      }
    },
    onChangeMatSan: async function () {
      try {
        if (this.currentItem.matSanId) {
          this.phongZoneList = await api.getDanhMuc(this.axios, 'IDC-PHONGZONE-BY-MATSAN', this.currentItem.matSanId)
        } else {
          this.phongZoneList = []
        }
      } catch (error) {
        console.log(error)
        this.handleError(error)
      }
    },
    onChangePhongZone: async function () {
      try {
        if (this.currentItem.phongZoneId && this.currentItem.lapDatTaiIdcId) {
          let rs = await api.getDanhMucRackByPhongZone(this.axios, this.currentItem.phongZoneId)
          this.rackList = rs || []
        } else {
          this.rackList = []
        }
      } catch (error) {
        console.log(error)
        this.handleError(error)
      }
    },
    onChangeRack: async function () {
      this.uTrenList = await api.getDanhMucUByRack(this.axios, this.currentItem.rack)
      this.uDuoiList = await api.getDanhMucUByRack(this.axios, this.currentItem.rack)
    },

    onChangeUTren: function () {
      // Handle change logic
    },

    onChangeUDuoi: function () {
      // Handle change logic
    },

    onChangeCanCuLapDat: function () {
      // Handle change logic
    },

    onChangeTrangThaiTaiSan: function () {
      // Handle change logic
    },

    onChangeTrangThaiVanHanh: function () {
      // Handle change logic
    },

    onChangeHangSanXuat: function () {
      // Handle change logic
    },

    onChangeNoiSanXuat: function () {
      // Handle change logic for nơi sản xuất
    },

    onChangeThanhPhanThietBi: function () {
      // Handle change logic for thành phần thiết bị
    },

    // Normalizer cho TreeSelect
    normalizer(node) {
      return {
        id: node.ID,
        label: node.TEN,
        children: node.children
      }
    },

    // Image upload methods
    onClickUploadIcon: function () {
      if (this.$refs.fileInputIcon) {
        this.$refs.fileInputIcon.value = null
        this.$refs.fileInputIcon.click()
      }
    },

    onRemoveIcon: function () {
      this.pendingRemoveIcon = true
      this.pendingIconFile = null
      this.pendingIconPreviewUrl = ''
      this.displayIconUrl = ''
      this.$set(this.currentItem, 'icons', '')
    },

    onChangeIconFile: async function (event) {
      try {
        const files = event.target.files
        if (!files || files.length === 0) {
          return
        }
        const file = files[0]

        // Validate file type
        if (!file.type.startsWith('image/')) {
          this.$root.toastError('Vui lòng chọn file ảnh hợp lệ!')
          return
        }

        // Validate file size (max 5MB)
        if (file.size > 5 * 1024 * 1024) {
          this.$root.toastError('Kích thước file không được vượt quá 5MB!')
          return
        }

        // Chỉ lưu tạm thời file và hiển thị preview local, không gọi API ngay
        this.pendingRemoveIcon = false
        this.pendingIconFile = file

        // Tạo URL preview tạm thời
        if (this.pendingIconPreviewUrl) {
          try {
            URL.revokeObjectURL(this.pendingIconPreviewUrl)
          } catch (e) { }
        }
        this.pendingIconPreviewUrl = URL.createObjectURL(file)
        this.displayIconUrl = this.pendingIconPreviewUrl
      } catch (e) {
        this.$root.toastError('Có lỗi khi tải ảnh')
      } finally {
        this.uploadingIcon = false
      }
    },

    // Load existing image when viewing/editing
    async loadExistingImage(imagePath) {
      if (!imagePath) return

      try {
        this.loadingImageUrl = true
        const viewUrl = await this.getPresignedUrlFromMinio(imagePath)
        this.displayIconUrl = viewUrl || imagePath
      } catch (error) {
        console.error('Error loading existing image:', error)
        this.displayIconUrl = imagePath
      } finally {
        this.loadingImageUrl = false
      }
    },

    // Helper methods
    resetCurrentItem: function () {
      this.currentItem = {
        id: 0,
        phamViIDCId: 0,
        donViQLId: this.$root.token.getDonViID(),
        mangThietBiId: 0,
        loaiThietBiId: 0,
        loaiKhachHangId: 0,
        heThongId: 0,
        donViQuanLy: '',
        nguoiQuanLy: '',
        lapDatTaiIdcId: 0,
        tenThietBi: '',
        toaNhaId: 0,
        matSanId: 0,
        dongSanPham: '',
        phongZoneId: 0,
        rack: '',
        uTrenId: 0,
        uDuoiId: 0,
        serialNumber: '',
        thoiDiemLapDat: '',
        tagNumber: '',
        maTheTaiSan: '',
        trangThaiTaiSanId: 0,
        congSuatThietKe: 0,
        trangThaiVanHanhId: 0,
        icons: '',
        soCongNguonDauVao: 0,
        hangSanXuatId: 0,
        noiSanXuatId: 0,
        namSanXuat: new Date().getFullYear(),
        soCongNguonDauRa: 0,
        ghiChu: '',
        khachHang: null,
        soHopDong: null,
        maThueBao: null,
        thanhPhanThietBiIds: [], // Reset thành phần thiết bị khi reset form
        portList: [] // Reset portList khi reset form
      }
      // Reset image upload variables
      this.pendingIconFile = null
      this.pendingIconPreviewUrl = ''
      this.displayIconUrl = ''
      this.pendingRemoveIcon = false
    },

    resetValidation: function () {
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })
    },

    validateForm: function () {
      let isValid = true

      // Validate required fields
      if (!this.currentItem.tenThietBi) {
        this.controls.txtTenThietBi.invalid = true
        isValid = false
      }

      if (!this.currentItem.serialNumber) {
        this.controls.txtSerialNumber.invalid = true
        isValid = false
      }

      // Add more validation rules as needed

      // Bổ sung validation cho năm sản xuất và số đo
      let hasSpecificError = false

      // Validate năm sản xuất hợp lệ (1900 - năm hiện tại) khi có giá trị
      if (this.currentItem.namSanXuat) {
        const currentYear = new Date().getFullYear()
        if (this.currentItem.namSanXuat < 1900 || this.currentItem.namSanXuat > currentYear) {
          this.controls.txtNamSanXuat.invalid = true
          this.$root.toastError(`Năm sản xuất phải trong khoảng từ 1900 đến ${currentYear}!`)
          isValid = false
          hasSpecificError = true
        }
      }

      // Validate các trường số đo phải > 0 (khi có giá trị)
      if (this.currentItem.congSuatThietKe && this.currentItem.congSuatThietKe <= 0) {
        this.controls.txtCongSuatThietKe.invalid = true
        this.$root.toastError('Công suất thiết kế phải lớn hơn 0!')
        isValid = false
        hasSpecificError = true
      }

      if (this.currentItem.soCongNguonDauVao && this.currentItem.soCongNguonDauVao <= 0) {
        this.controls.txtSoCongNguonDauVao.invalid = true
        this.$root.toastError('Số cổng nguồn đầu vào phải lớn hơn 0!')
        isValid = false
        hasSpecificError = true
      }

      if (this.currentItem.soCongNguonDauRa && this.currentItem.soCongNguonDauRa <= 0) {
        this.controls.txtSoCongNguonDauRa.invalid = true
        this.$root.toastError('Số cổng nguồn đầu ra phải lớn hơn 0!')
        isValid = false
        hasSpecificError = true
      }

      if (!isValid && !hasSpecificError) {
        this.$root.toastError('Vui lòng điền đầy đủ thông tin bắt buộc!')
      }

      return isValid
    },

    // API methods
    loadInitialData: async function () {
      try {
        this.$root.showLoading(true)
        await Promise.all([this.loadDanhMuc()])
      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    loadDanhMuc: async function () {
      try {
        this.phamViIdcList = await api.getDanhMucDonViQuanLy(this.axios, this.$root.token)
        this.mangThietBiList = await api.getDanhMuc(this.axios, 'IDC-MANG-TBI')
        this.loaiThietBiList = await api.getDanhMuc(this.axios, 'IDC-LOAI-TBI')
        this.loaiKhachHangList = await api.getDanhMuc(this.axios, 'LOAI-KH')
        this.heThongList = await api.getDanhMucHeThong(this.axios)
        this.lapDatTaiIdcList = await api.getDanhMucIDC(this.axios, this.$root.token)
        this.trangThaiTaiSanList = await api.getDanhMucTrangThaiTaiSan(this.axios)
        this.trangThaiVanHanhList = await api.getDanhMucTrangThaiVanHanh(this.axios)
        this.hangSanXuatList = await api.getDanhMuc(this.axios, 'IDC-HANG-SX')
        this.nhaCungCapList = await api.getDanhMucNhaCungCap(this.axios)
        this.thietBiChaList = await api.getDanhSachThietBiCha(this.axios)
        this.thanhPhanThietBiList = await api.getDanhSachThanhPhanThietBi(this.axios)
        this.loaiCongList = await api.getDanhMucLoaiCong(this.axios)
        this.trangThaiTaiSanList = [
          { ID: '0', TEN: 'Đang sử dụng' },
          { ID: '1', TEN: 'Dừng hoạt động' },
          { ID: '2', TEN: 'Hỏng' },
          { ID: '3', TEN: 'Đã thanh lý' }
        ]
      } catch (error) {
        console.log(error)
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    // CRUD operations
    saveNewItem: async function () {
      try {
        const data = { ...this.currentItem }

        // Loại bỏ portList khỏi data submit vì sẽ xử lý riêng
        delete data.portList
        // TODO: Implement API call to save new device
        // const result = await api.createThietBiKhachHang(this.axios, data)

        // Sau khi tạo thiết bị thành công, lưu portList thông qua ThongTinPort component
        if (this.$refs.thongTinPort && this.currentItem.portList && this.currentItem.portList.length > 0) {
          const portSaveResult = await this.$refs.thongTinPort.submitPortsToServer()
          if (!portSaveResult) {
            this.$root.toastError('Không thể lưu danh sách port!')
            return
          }
        }

        this.$emit('onRefreshData')
        this.$root.toastSuccess('Tạo mới thiết bị thành công!')
        this.currentMode = 'VIEW'
      } catch (error) {
        this.handleError(error)
      }
    },

    updateItem: async function () {
      try {
        let dto = {
          thietBiKHId: this.currentItem.id,
          mangTbiId: this.currentItem.mangThietBiId,
          loaiThietBiId: this.currentItem.loaiThietBiId,
          ten: this.currentItem.tenThietBi,
          model: this.currentItem.dongSanPham,
          serialNumber: this.currentItem.serialNumber,
          partNumber: this.currentItem.tagNumber,
          csDinhDanh: this.currentItem.congSuatThietKe,
          soPortConsole: this.currentItem.soCongNguonDauVao,
          soPortInterface: this.currentItem.soCongNguonDauRa,
          hangSxId: this.currentItem.hangSanXuatId,
          noiSanXuatId: this.currentItem.noiSanXuatId,
          namSx: this.currentItem.namSanXuat,
          iconPath: this.currentItem.icons,
          loaiKhachHang: this.currentItem.loaiKhachHangId,
          ngayLapDat: this.currentItem.thoiDiemLapDat,
          trangThaiSD: this.currentItem.trangThaiVanHanhId,
          trangThaiTsId: this.currentItem.trangThaiTaiSanId,
          maTs: this.currentItem.maTheTaiSan,
          moTa: this.currentItem.ghiChu,
          heThongTbiId: this.currentItem.heThongId,
          hdtbId: this.currentItem.hdtbId,
          tinhId: this.currentItem.tinhId,
          phamViIDCId: this.currentItem.phamViIDCId,
          donViQLId: this.currentItem.donViQLId,
          thanhPhanThietBiIds: this.currentItem.thanhPhanThietBiIds
        }
        let rs = await api.updateThietBiKhachHang(this.axios, dto)
        if (rs.error_code == 'BSS-00000000') {
          // Sau khi cập nhật thiết bị thành công, lưu portList thông qua ThongTinPort component
          if (this.$refs.thongTinPort && this.currentItem.portList && this.currentItem.portList.length > 0) {
            const portSaveResult = await this.$refs.thongTinPort.submitPortsToServer()
            if (!portSaveResult) {
              this.$root.toastError('Không thể lưu danh sách port!')
              return
            }
          }

          this.$emit('onRefreshData')
          this.closeModal()
          this.$root.toastSuccess('Cập nhật thành công!')
          this.currentMode = 'VIEW'
        } else {
          this.$root.toastError(rs.message)
        }
      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },

    deleteItem: async function () {
      // API call to delete item
    },

    handleError: function (error) {
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

    // ====== Minio helpers ======
    async uploadFileToMinio(item) {
      try {
        this.$root.showLoading(true)
        let formData = new FormData()
        formData.append('file', item)
        let url = `/web-ecms/storage/uploadFile`
        let response = await this.$root.context.post(url, formData)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          if (response.data.status) {
            return response.data.message
          } else return null
        } else return null
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra trong quá trình upload file!')
        return null
      } finally {
        this.$root.showLoading(false)
      }
    },

    async getPresignedUrlFromMinio(src) {
      try {
        let url = `/web-ecms/storage/getPresignedUrl`
        let response = await this.$root.context.post(url, { fileSource: src })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          return response.data.result
        } else return null
      } catch (error) {
        console.log(error)
        return null
      }
    },

    async deleteFileFromMinio(src) {
      try {
        this.$root.showLoading(true)
        let url = `/web-ecms/storage/deleteFile`
        let response = await this.$root.context.post(url, { fileSource: src })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          if (!response.data.status) this.$toast.error(response.data.message)
          return response.data.status
        } else return false
      } catch (error) {
        console.log(error)
        return false
      } finally {
        this.$root.showLoading(false)
      }
    }
  },
  data: function () {
    return {
      isModalVisible: false,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      item: null,
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      currentItem: {
        id: 0,
        phamViIDCId: 0,
        donViQLId: this.$root.token.getDonViID(),
        mangThietBiId: 0,
        loaiThietBiId: 0,
        loaiKhachHangId: 0,
        heThongId: 0,
        donViQuanLy: '',
        nguoiQuanLy: '',
        lapDatTaiIdcId: 0,
        tenThietBi: '',
        toaNhaId: 0,
        matSanId: 0,
        dongSanPham: '',
        phongZoneId: 0,
        rack: '',
        uTrenId: 0,
        uDuoiId: 0,
        serialNumber: '',
        thoiDiemLapDat: '',
        tagNumber: '',
        maTheTaiSan: '',
        trangThaiTaiSanId: 0,
        congSuatThietKe: 0,
        trangThaiVanHanhId: 0,
        icons: '',
        soCongNguonDauVao: 0,
        hangSanXuatId: 0,
        noiSanXuatId: 0,
        namSanXuat: new Date().getFullYear(),
        soCongNguonDauRa: 0,
        ghiChu: '',
        khachHang: null,
        soHopDong: null,
        maThueBao: null,
        thanhPhanThietBiIds: [], // Danh sách thành phần thiết bị
        portList: [] // Danh sách port của thiết bị
      },
      tab_index: 1, // Tab chính: 1=Cấu hình thiết bị, 2=Port, 3=Lắp đặt, 4=Lịch sử thay đổi

      // Image upload variables
      pendingIconFile: null, // File ảnh đang chờ upload
      pendingIconPreviewUrl: '', // URL preview tạm thời
      displayIconUrl: '', // URL hiển thị ảnh
      pendingRemoveIcon: false, // Flag đánh dấu cần xóa ảnh
      loadingImageUrl: false, // Flag đang tải ảnh
      uploadingIcon: false, // Flag đang upload ảnh

      // Dropdown lists
      phamViIdcList: [],
      donViQLList: [],
      mangThietBiList: [],
      loaiThietBiList: [],
      loaiKhachHangList: [],
      heThongList: [],
      lapDatTaiIdcList: [],
      toaNhaList: [],
      matSanList: [],
      phongZoneList: [],
      uTrenList: [],
      uDuoiList: [],
      canCuLapDatList: [],
      trangThaiTaiSanList: [],
      trangThaiVanHanhList: [],
      hangSanXuatList: [],
      nhaCungCapList: [],
      thietBiChaList: [],
      thanhPhanThietBiList: [],
      rackList: [],
      loaiCongList: [],

      // Form controls
      controls: {
        selectPhamViIDC: { enabled: false, invalid: false },
        selectDonViQL: { enabled: false, invalid: false },
        selectMangThietBi: { enabled: false, invalid: false },
        selectLoaiThietBi: { enabled: false, invalid: false },
        selectLoaiKhachHang: { enabled: false, invalid: false },
        selectHeThong: { enabled: false, invalid: false },
        txtDonViQuanLy: { enabled: false, invalid: false },
        txtNguoiQuanLy: { enabled: false, invalid: false },
        selectLapDatTaiIdc: { enabled: false, invalid: false },
        txtTenThietBi: { enabled: false, invalid: false },
        selectToaNha: { enabled: false, invalid: false },
        selectMatSan: { enabled: false, invalid: false },
        txtDongSanPham: { enabled: false, invalid: false },
        selectPhongZone: { enabled: false, invalid: false },
        txtRack: { enabled: false, invalid: false },
        selectUTren: { enabled: false, invalid: false },
        selectUDuoi: { enabled: false, invalid: false },
        txtSerialNumber: { enabled: false, invalid: false },
        txtThoiDiemLapDat: { enabled: false, invalid: false },
        selectCanCuLapDat: { enabled: false, invalid: false },
        txtTagNumber: { enabled: false, invalid: false },
        txtMaTheTaiSan: { enabled: false, invalid: false },
        selectTrangThaiTaiSan: { enabled: false, invalid: false },
        txtCongSuatThietKe: { enabled: false, invalid: false },
        selectTrangThaiVanHanh: { enabled: false, invalid: false },
        txtIcons: { enabled: false, invalid: false },
        txtSoCongNguonDauVao: { enabled: false, invalid: false },
        selectHangSanXuat: { enabled: false, invalid: false },
        selectNoiSanXuat: { enabled: false, invalid: false },
        txtNamSanXuat: { enabled: false, invalid: false },
        txtSoCongNguonDauRa: { enabled: false, invalid: false },
        txtGhiChu: { enabled: false, invalid: false },
        selectThanhPhanThietBi: { enabled: false, invalid: false },
        txtKhachHang: { enabled: false, invalid: false },
        txtSoHopDong: { enabled: false, invalid: false },
        txtMaThueBao: { enabled: false, invalid: false }
      }
    }
  },
  mounted: function () { }
}
</script>
<style scoped>
.modal-body-content {
  overflow-y: auto;
}
</style>

<style>
.device-detail-modal .modal-dialog {
  max-width: 95vw !important;
  width: 95vw !important;
  margin: 1rem auto;
}

.device-detail-modal .modal-body {
  overflow: scroll;
  padding: 0 !important;
}

.fixed-icon-box {
  width: 50px;
  height: 50px;
  object-fit: cover;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.image-loading {
  display: flex;
  align-items: center;
  justify-content: center;
}

.device-detail-modal {
  padding: 0px;
}
</style>
