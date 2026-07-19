<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import moment from 'moment'
import TabHistory from '../../components/Tabs/TabHistory'

import ModalPortThietBi from '../../components/Modals/ModalPortThietBi'

// Import các component con
import ThongTinThietBi from './components/ThongTinThietBi/index.vue'
import ThongTinQuanLy from './components/ThongTinQuanLy/index.vue'
import ThongTinPort from './components/ThongTinPort/index.vue'
import ThongTinLapDat from './components/ThongTinLapDat/index.vue'

import api from '../api'

export default {
  name: 'ModalChiTietThietBi',
  props: {
    modalID: null,
    danhMucData: {
      type: Object,
      default: () => ({
        toaNhaList: [],
        phongList: [],
        matSanList: [],
        loaiThietBiList: [],
        rackList: [],
        danhMucIdcList: [],
        uTrenList: [],
        uDuoiList: []
      })
    }
  },
  components: {
    TabHistory,
    ModalPortThietBi,
    ThongTinThietBi,
    ThongTinQuanLy,
    ThongTinPort,
    ThongTinLapDat
  },
  created: async function() {
    await this.loadInitialData()
  },
  validations: {},
  watch: {},
  computed: {},
  methods: {
    onChangeTab: function(index) {
      this.tab_index = index
    },

    closeModal: function() {
      // Kiểm tra xem có port tạm thời chưa được lưu không
      if (this.hasTempPorts()) {
        this.showUnsavedPortsWarning()
        return
      }

      this.doCloseModal()
    },

    doCloseModal: function() {
      this.resetCurrentItem()
      this.resetValidation()
      this.tab_index = 1
      this.$refs.ModalChiTietThietBi.hide(this.modalID)
    },

    hasTempPorts: function() {
      if (!Array.isArray(this.currentItem.portList) || this.currentItem.portList.length === 0) {
        return false
      }

      return this.currentItem.portList.some((port) => typeof port.ID === 'string' && port.ID.startsWith('temp_'))
    },

    showUnsavedPortsWarning: function() {
      const tempPortsCount = this.currentItem.portList.filter(
        (port) => typeof port.ID === 'string' && port.ID.startsWith('temp_')
      ).length

      this.$confirm(
        `Có ${tempPortsCount} port chưa được lưu. Bạn có muốn lưu trước khi đóng modal không?`,
        'Cảnh báo',
        {
          confirmButtonText: 'Lưu và đóng',
          cancelButtonText: 'Bỏ qua và đóng',
          showCancelButton: true,
          showClose: false,
          type: 'warning'
        }
      )
        .then(() => {
          // Người dùng chọn lưu
          this.onSave(true)
        })
        .catch(() => {
          // Người dùng chọn bỏ qua
          this.doCloseModal()
        })
    },
    show: async function(item, mode, danhMucData = null) {
      try {
        console.log('Modal show called with:', { item, mode, danhMucData })
        this.currentMode = mode
        this.item = item

        // Cập nhật danh mục từ component cha nếu có
        if (danhMucData) {
          this.updateDanhMucFromParent(danhMucData)
        }
        // reset danh muc khi open modal
        this.loaiThietBiList = []

        if (item && (item.id || item.ID) && (mode === 'VIEW' || mode === 'EDIT')) {
          // Gọi API lấy chi tiết thông tin khi view hoặc edit
          const itemId = item.id || item.ID
          await this.loadItemDetail(itemId)
        } else if (item) {
          // Nếu có item nhưng không có id hoặc mode khác, copy dữ liệu trực tiếp
          this.currentItem = { ...item }
          // Đảm bảo portList luôn là mảng
          if (!Array.isArray(this.currentItem.portList)) {
            this.$set(this.currentItem, 'portList', [])
          }
          // Load existing image if available
          if (item.icons) {
            await this.loadExistingImage(item.icons)
          }
        } else {
          // Reset cho trường hợp thêm mới
          this.resetCurrentItem()
        }

        await this.setDefaultDonViQuanLyId()
        this.$refs.ModalChiTietThietBi.show()
      } catch (error) {
        this.$root.toastError(error.message)
      }
    },

    onAddNew: function() {
      this.resetCurrentItem()
      this.currentMode = 'ADD'
      this.resetValidation()
      // Đảm bảo portList luôn là mảng cho mode ADD
      if (!Array.isArray(this.currentItem.portList)) {
        this.$set(this.currentItem, 'portList', [])
      }
    },

    updateDanhMucFromParent: function(danhMucData) {
      this.loaiThietBiList = danhMucData.loaiThietBiList || []
    },

    onSave: async function(fromWarningDialog = false) {
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

          // Submit port list sau khi lưu thiết bị thành công
          const portSubmitSuccess = await this.submitPortsToServer()
          if (!portSubmitSuccess) {
            return // Không đóng modal nếu port validation fail
          }

          this.$root.toastSuccess('Lưu thành công!')
          this.$parent.loadDanhSachThietbi()

          // Nếu được gọi từ warning dialog, đóng modal luôn
          if (fromWarningDialog) {
            this.doCloseModal()
          } else {
            this.closeModal()
          }
        } catch (error) {
          this.handleError(error)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    async showModalPortThietBi() {
      this.$refs.ModalPortThietBi.show()
    },

    async editPort(portData) {
      await this.loadLoaiCongList()
      this.$refs.ModalPortThietBi.show(portData)
    },

    onPortSaved(portData) {
      // Delegate to ThongTinPort component
      this.$refs.ThongTinPort.onPortSaved(portData)
    },

    onPortChanged() {
      // Handle port changes - could trigger validation or other updates
      console.log('Port data changed')
    },

    deletePort: async function(selectedPorts) {
      // Delegate to ThongTinPort component
      await this.$refs.ThongTinPort.deletePort()
    },

    // Change handlers for SelectExt components

    onChangeMangThietBi: async function() {
      await this.loadLoaiThietBiFromMangThietBiList()
    },

    onChangeLoaiThietBi: function() {
      // Handle change logic
    },

    onChangeHeThong: function() {
      // Handle change logic
    },

    onChangePhamViIdc: function() {
      // Handle change logic for phạm vi IDC
    },

    onChangeDonViQuanLy: function() {
      // Handle change logic
    },

    onChangeNhanVienQuanLy: function() {
      // Handle change logic
    },

    onChangeTrangThaiVanHanh: function() {
      // Handle change logic
    },

    onChangeTrangThaiTaiSan: function() {
      // Handle change logic
    },

    onChangeHangSanXuat: function() {
      // Handle change logic
    },

    onChangeThietBiCha: function() {
      // Handle change logic for thiết bị cha
    },

    onChangeThanhPhanThietBi: function(selectedValues) {
      this.$set(this.currentItem, 'thanhPhanThietBiIds', selectedValues || [])
    },

    onChangeNoiSanXuat: function() {
      // Handle change logic for nơi sản xuất
    },

    // Image upload methods
    onClickUploadIcon: function() {
      if (this.$refs.ThongTinThietBi && this.$refs.ThongTinThietBi.$refs.fileInputIcon) {
        this.$refs.ThongTinThietBi.$refs.fileInputIcon.value = null
        this.$refs.ThongTinThietBi.$refs.fileInputIcon.click()
      }
    },

    onRemoveIcon: function() {
      this.pendingRemoveIcon = true
      this.pendingIconFile = null
      this.pendingIconPreviewUrl = ''
      this.displayIconUrl = ''
      this.$set(this.currentItem, 'icons', '')
    },

    onChangeIconFile: async function(event) {
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
          } catch (e) {}
        }
        this.pendingIconPreviewUrl = URL.createObjectURL(file)
        this.displayIconUrl = this.pendingIconPreviewUrl
      } catch (e) {
        this.$root.toastError('Có lỗi khi tải ảnh')
      } finally {
        this.uploadingIcon = false
      }
    },

    // Helper methods
    resetCurrentItem: function() {
      this.currentItem = {
        id: 0,
        donViQuanLyId: 0,
        nguoiQuanLyId: 0,
        mangThietBiId: 0,
        loaiThietBiId: 0,
        heThongId: 0,
        tenThietBi: '',
        dongSanPham: '',
        serialNumber: '',
        thoiDiemLapDat: '',
        ngayDuaVaoSuDung: '',
        tagNumber: '',
        maTheTaiSan: '',
        trangThaiTaiSanId: 0,
        congSuatThietKe: 0,
        trangThaiVanHanhId: 0,
        icons: '',
        soCongNguonDauVao: 0,
        hangSanXuatId: 0,
        namSanXuat: new Date().getFullYear(),
        noiSanXuatId: 0,
        soCongNguonDauRa: 0,
        ghiChu: '',
        thietBiChaId: 0,
        thanhPhanThietBiIds: [],
        nhaCungCapId: 0,
        phamViIdcId: 0,
        portList: [],
        // Thông tin lắp đặt
        lapDatTaiIdcId: 0,
        toaNhaId: 0,
        matSanId: 0,
        phongZoneId: 0,
        rackId: 0,
        uTrenId: 0,
        uDuoiId: 0
      }

      // Reset image upload properties
      this.resetImageUploadProperties()
    },

    resetImageUploadProperties: function() {
      this.displayIconUrl = ''
      this.pendingIconFile = null
      this.pendingIconPreviewUrl = ''
      this.pendingRemoveIcon = false
      this.uploadingIcon = false
      this.loadingImageUrl = false
    },

    loadExistingImage: async function(iconPath) {
      if (iconPath) {
        try {
          this.loadingImageUrl = true
          const viewUrl = await this.getPresignedUrlFromMinio(iconPath)
          this.displayIconUrl = viewUrl || iconPath
        } catch (error) {
          console.error('Error loading existing image:', error)
          this.displayIconUrl = iconPath
        } finally {
          this.loadingImageUrl = false
        }
      } else {
        this.displayIconUrl = ''
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

      // Validate thông tin thiết bị bắt buộc
      if (!this.currentItem.tenThietBi || this.currentItem.tenThietBi.trim() === '') {
        this.controls.txtTenThietBi.invalid = true
        isValid = false
      }

      if (!this.currentItem.mangThietBiId || this.currentItem.mangThietBiId === 0) {
        this.controls.selectMangThietBi.invalid = true
        isValid = false
      }

      if (!this.currentItem.loaiThietBiId || this.currentItem.loaiThietBiId === 0) {
        this.controls.selectLoaiThietBi.invalid = true
        isValid = false
      }

      if (!this.currentItem.serialNumber || this.currentItem.serialNumber.trim() === '') {
        this.controls.txtSerialNumber.invalid = true
        isValid = false
      }

      if (!this.currentItem.hangSanXuatId || this.currentItem.hangSanXuatId === 0) {
        this.controls.selectHangSanXuat.invalid = true
        isValid = false
      }

      if (!this.currentItem.dongSanPham || this.currentItem.dongSanPham.trim() === '') {
        this.controls.txtDongSanPham.invalid = true
        isValid = false
      }

      if (!this.currentItem.congSuatThietKe || this.currentItem.congSuatThietKe === 0) {
        this.controls.txtCongSuatThietKe.invalid = true
        isValid = false
      }

      if (!this.currentItem.soCongNguonDauVao || this.currentItem.soCongNguonDauVao === 0) {
        this.controls.txtSoCongNguonDauVao.invalid = true
        isValid = false
      }

      if (!this.currentItem.soCongNguonDauRa || this.currentItem.soCongNguonDauRa === 0) {
        this.controls.txtSoCongNguonDauRa.invalid = true
        isValid = false
      }

      if (this.currentItem.trangThaiTaiSanId === null || this.currentItem.trangThaiTaiSanId === undefined) {
        this.controls.selectTrangThaiTaiSan.invalid = true
        isValid = false
      }

      if (!this.currentItem.trangThaiVanHanhId || this.currentItem.trangThaiVanHanhId === 0) {
        this.controls.selectTrangThaiVanHanh.invalid = true
        isValid = false
      }

      if (!this.currentItem.phamViIdcId || this.currentItem.phamViIdcId === 0) {
        this.controls.selectPhamViIdc.invalid = true
        isValid = false
      }

      // Validate thông tin quản lý bắt buộc
      if (!this.currentItem.donViQuanLyId || this.currentItem.donViQuanLyId === 0) {
        this.controls.selectDonViQuanLy.invalid = true
        isValid = false
      }

      if (!this.currentItem.nguoiQuanLyId || this.currentItem.nguoiQuanLyId === 0) {
        this.controls.selectNhanVienQuanLy.invalid = true
        isValid = false
      }

      if (!this.currentItem.maTheTaiSan || this.currentItem.maTheTaiSan.trim() === '') {
        this.controls.txtMaTaiSan.invalid = true
        isValid = false
      }

      // Bổ sung validation cho năm sản xuất và số đo
      let hasSpecificError = false

      // Validate năm sản xuất hợp lệ (1900 - năm hiện tại) - chỉ validate nếu có giá trị
      if (this.currentItem.namSanXuat && this.currentItem.namSanXuat !== 0 && this.currentItem.namSanXuat !== null) {
        const currentYear = new Date().getFullYear()
        if (this.currentItem.namSanXuat < 1900 || this.currentItem.namSanXuat > currentYear) {
          this.controls.txtNamSanXuat.invalid = true
          this.$root.toastError(`Năm sản xuất phải trong khoảng từ 1900 đến ${currentYear}!`)
          isValid = false
          hasSpecificError = true
        }
      }

      // Validate các trường số đo phải > 0 (khi có giá trị)
      if (
        this.currentItem.congSuatThietKe &&
        this.currentItem.congSuatThietKe !== 0 &&
        this.currentItem.congSuatThietKe <= 0
      ) {
        this.controls.txtCongSuatThietKe.invalid = true
        this.$root.toastError('Công suất thiết kế phải lớn hơn 0!')
        isValid = false
        hasSpecificError = true
      }

      if (
        this.currentItem.soCongNguonDauVao &&
        this.currentItem.soCongNguonDauVao !== 0 &&
        this.currentItem.soCongNguonDauVao <= 0
      ) {
        this.controls.txtSoCongNguonDauVao.invalid = true
        this.$root.toastError('Số cổng nguồn đầu vào phải lớn hơn 0!')
        isValid = false
        hasSpecificError = true
      }

      if (
        this.currentItem.soCongNguonDauRa &&
        this.currentItem.soCongNguonDauRa !== 0 &&
        this.currentItem.soCongNguonDauRa <= 0
      ) {
        this.controls.txtSoCongNguonDauRa.invalid = true
        this.$root.toastError('Số cổng nguồn đầu ra phải lớn hơn 0!')
        isValid = false
        hasSpecificError = true
      }

      if (!isValid && !hasSpecificError) {
        this.$root.toastError(`Vui lòng nhập đầy đủ thông tin bắt buộc !`)
      }

      return isValid
    },

    // API methods
    loadInitialData: async function() {
      try {
        this.$root.showLoading(true)

        const loadPromises = []
        loadPromises.push(
          this.loadDonViQuanLyList(),
          this.loadNhanVienList(),
          this.loadMangThietBiList(),
          this.loadHeThongList(),
          this.loadTrangThaiTaiSanList(),
          this.loadTrangThaiVanHanhList(),
          this.loadHangSanXuatList(),
          this.loadThietBiChaList(),
          this.loadThanhPhanThietBiList(),
          this.loadNhaCungCapList(),
          this.loadLoaiCongList(),
          this.loadDanhMucIdcList()
        )

        await Promise.all(loadPromises)
      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },

    loadDonViQuanLyList: async function() {
      this.donViQuanLyList = await api.getDanhMucDonViQuanLy(this.axios, this.$root.token)
    },

    loadNhanVienList: async function() {
      this.nhanVienList = await api.getDanhMucNhanVienQuanLy(this.axios, this.$root.token)
    },

    loadMangThietBiList: async function() {
      try {
        this.mangThietBiList = await api.getDanhMucMangThietBi(this.axios)
      } catch (error) {
        this.mangThietBiList = []
      }
    },

    loadHeThongList: async function() {
      try {
        this.heThongList = await api.getDanhMucHeThongThietBi(this.axios, this.$root.token)
      } catch (error) {
        this.heThongList = []
      }
    },

    loadTrangThaiTaiSanList: async function() {
      // Gắn cứng trạng thái tài sản
      this.trangThaiTaiSanList = [
        { ID: '0', TEN: 'Đang sử dụng' },
        { ID: '1', TEN: 'Dừng hoạt động' },
        { ID: '2', TEN: 'Hỏng' },
        { ID: '3', TEN: 'Đã thanh lý' }
      ]
    },

    loadTrangThaiVanHanhList: async function() {
      try {
        this.trangThaiVanHanhList = await api.getDanhMucTrangThaiVanHanh(this.axios)
      } catch (error) {
        this.trangThaiVanHanhList = []
      }
    },

    loadHangSanXuatList: async function() {
      try {
        this.hangSanXuatList = await api.getDanhMucHangSanXuat(this.axios)
      } catch (error) {
        console.error('Error loading hang san xuat list:', error)
        this.hangSanXuatList = []
      }
    },

    loadThietBiChaList: async function() {
      try {
        this.thietBiChaList = await api.getThietBiCha(this.axios, this.$root.token.getDonViID())
      } catch (error) {
        console.error('Error loading thiet bi cha list:', error)
        this.thietBiChaList = []
      }
    },

    loadThanhPhanThietBiList: async function() {
      try {
        const data = await api.getDanhMucThanhPhanThietBi(this.axios)
        // Convert data to format expected by MultiSelect component
        this.thanhPhanThietBiList = data.map((item) => ({
          id: item.ID,
          label: item.TEN
        }))
      } catch {
        this.thanhPhanThietBiList = []
      }
    },

    loadNhaCungCapList: async function() {
      try {
        this.nhaCungCapList = await api.getDanhMucNhaCungCap(this.axios)
      } catch (error) {
        console.error('Error loading nha cung cap list:', error)
        this.nhaCungCapList = []
      }
    },

    loadDanhMucIdcList: async function() {
      try {
        this.danhMucData.danhMucIdcList = await api.getDanhMucIDC(this.axios, this.$root.token)
      } catch (error) {
        console.error('Error loading danh muc IDC list:', error)
        this.danhMucData.danhMucIdcList = []
      }
    },

    loadLoaiThietBiFromMangThietBiList: async function() {
      try {
        this.loaiThietBiList = await api.getDanhMucLoaiThietBiFromMangThietBi(
          this.axios,
          this.currentItem.mangThietBiId
        )
      } catch {
        this.loaiThietBiList = []
      }
    },

    loadLoaiCongList: async function() {
      try {
        this.loaiCongList = await api.getDanhMucLoaiCong(this.axios)
      } catch {
        this.loaiCongList = []
      }
    },

    // Load danh mục lắp đặt dựa trên dữ liệu hiện có khi show modal
    loadDanhMucLapDatFromCurrentData: async function() {
      try {
        const { lapDatTaiIdcId, toaNhaId, matSanId, phongZoneId, rackId } = this.currentItem
        const promises = []

        if (lapDatTaiIdcId) {
          promises.push(
            api.getDanhMuc(this.axios, 'IDC-TOANHA-BY-IDC', lapDatTaiIdcId).then((res) => {
              this.danhMucData.toaNhaList = res || []
            })
          )
        }

        if (toaNhaId) {
          promises.push(
            api.getDanhMuc(this.axios, 'IDC-MATSAN-BY-TOANHA', toaNhaId).then((res) => {
              this.danhMucData.matSanList = res || []
            })
          )
        }

        if (matSanId) {
          promises.push(
            api.getDanhMuc(this.axios, 'IDC-PHONGZONE-BY-MATSAN', matSanId).then((res) => {
              this.danhMucData.phongList = res || []
            })
          )
        }

        if (phongZoneId) {
          promises.push(
            api.getDanhMucRackByPhongZone(this.axios, phongZoneId).then(async (res) => {
              this.danhMucData.rackList = res || []
            })
          )
        }
        if (rackId) {
          promises.push(
            api.getDanhMucUByRack(this.axios, rackId).then(async (res) => {
              this.danhMucData.uTrenList = res || []
              this.danhMucData.uDuoiList = res || []
            })
          )
        }

        await Promise.all(promises)
      } catch (error) {
        console.error('Error loading danh muc lap dat:', error)
        this.danhMucData.toaNhaList = []
        this.danhMucData.matSanList = []
        this.danhMucData.phongList = []
        this.danhMucData.rackList = []
        this.danhMucData.uTrenList = []
        this.danhMucData.uDuoiList = []
      }
    },

    // Load danh sách port theo thiết bị ID
    loadPortList: async function(thietBiId) {
      try {
        if (!thietBiId || thietBiId <= 0) {
          this.$set(this.currentItem, 'portList', [])
          return
        }

        this.$root.showLoading(true)
        const portList = await api.getDanhSachPortTheoThietBi(this.axios, thietBiId)

        if (portList && Array.isArray(portList)) {
          this.$set(this.currentItem, 'portList', portList)
        } else {
          this.$set(this.currentItem, 'portList', [])
        }
      } catch (error) {
        console.error('Error loading port list:', error)
        this.$set(this.currentItem, 'portList', [])
        this.$root.toastError('Không thể tải danh sách port')
      } finally {
        this.$root.showLoading(false)
      }
    },

    // CRUD operations
    loadItemDetail: async function(itemId) {
      try {
        console.log('loadItemDetail called with itemId:', itemId)
        this.isLoadingItem = true

        this.$root.showLoading(true)
        const response = await api.getChiTietThietBiIDC(this.axios, itemId)

        if (response) {
          // Convert dữ liệu từ uppercase sang format phù hợp với currentItem
          this.currentItem = this.convertApiDataToCurrentItem(response)

          // Load danh mục lắp đặt theo dữ liệu hiện có
          await this.loadDanhMucLapDatFromCurrentData()
          // Load existing image nếu có
          if (this.currentItem.icons) {
            await this.loadExistingImage(this.currentItem.icons)
          }

          if (this.currentItem.mangThietBiId) {
            await this.loadLoaiThietBiFromMangThietBiList()
          }

          // Load danh sách port của thiết bị
          await this.loadPortList(this.currentItem.id)
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
      // Convert dữ liệu từ API (uppercase) sang format currentItem (camelCase)
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
        portList: [], // Sẽ được load riêng từ API getDanhSachPortTheoThietBi
        tagNumber: apiData.PART_NUMBER || null,
        nhaCungCapId: apiData.NHACUNGCAP_ID || null,
        phamViIdcId: apiData.IDC_ID || null,
        // Thông tin lắp đặt
        lapDatTaiIdcId: apiData.LAPDAT_IDC_ID || null,
        toaNhaId: apiData.TOANHA_ID || null,
        matSanId: apiData.MATSAN_ID || null,
        phongZoneId: apiData.PHONG_ID || null,
        rackId: apiData.IDC_RACK_ID || null,
        uTrenId: apiData.UP_UNIT_ID || null,
        uDuoiId: apiData.DOWN_UNIT_ID || null,
        trangThaiDauNoi: apiData.TRANGTHAI_DAUNOI || null
      }
    },

    saveNewItem: async function() {
      const data = { ...this.currentItem }

      // Loại bỏ portList khỏi data submit vì sẽ xử lý riêng
      delete data.portList

      if (data.thoiDiemLapDat) {
        data.thoiDiemLapDat = moment(data.thoiDiemLapDat).format('YYYY-MM-DDTHH:mm:ss')
      }
      if (data.ngayDuaVaoSuDung) {
        data.ngayDuaVaoSuDung = moment(data.ngayDuaVaoSuDung).format('YYYY-MM-DDTHH:mm:ss')
      }

      // Xử lý thành phần thiết bị multiple selection
      if (!data.thanhPhanThietBiIds || !Array.isArray(data.thanhPhanThietBiIds) || data.thanhPhanThietBiIds.length === 0) {
        data.thanhPhanThietBiIds = null
      }
      // Giữ nguyên lapDatTaiIdcId vì đã có sẵn
      // data.lapDatTaiIdcId = data.phamViIdcId // Bỏ dòng này vì giờ đã phân biệt rõ
      const newId = await api.upsertThietBiIDC(this.axios, data)
      this.currentItem.id = newId
    },

    updateItem: async function() {
      const data = { ...this.currentItem }

      delete data.portList
      if (data.thoiDiemLapDat) {
        data.thoiDiemLapDat = moment(data.thoiDiemLapDat).format('YYYY-MM-DDTHH:mm:ss')
      }
      if (data.ngayDuaVaoSuDung) {
        data.ngayDuaVaoSuDung = moment(data.ngayDuaVaoSuDung).format('YYYY-MM-DDTHH:mm:ss')
      }

      // Xử lý thành phần thiết bị multiple selection
      if (!data.thanhPhanThietBiIds || !Array.isArray(data.thanhPhanThietBiIds) || data.thanhPhanThietBiIds.length === 0) {
        data.thanhPhanThietBiIds = null
      }

      const newId = await api.upsertThietBiIDC(this.axios, data)
      this.currentItem.id = newId
    },

    deleteItem: async function() {
      // API call to delete item
    },

    handleError: function(error) {
      if (error.message) {
        this.$root.toastError(error.message)
      } else {
        this.$root.toastError('Đã xảy ra lỗi!')
      }
    },

    // Set default donViQuanLyId và lapDatTaiIdcId
    setDefaultDonViQuanLyId: async function() {
      const donViId = await this.$root.token.getDonViID()
      const foundDonVi = this.donViQuanLyList.find((item) => String(item.ID) === String(donViId))

      if (foundDonVi) {
        this.$set(this.currentItem, 'donViQuanLyId', donViId)
      } else {
        throw new Error('Không thuộc phạm vi đơn vị quản lý của IDC')
      }
    },

    // ====== PORT OPERATIONS ======
    // Submit tất cả port lên server
    async submitPortsToServer() {
      try {
        if (!Array.isArray(this.currentItem.portList) || this.currentItem.portList?.length === 0) {
          return true
        }

        // Kiểm tra trùng tên port trước khi submit
        const duplicateNames = []
        const portNames = new Set()

        for (const port of this.currentItem.portList) {
          if (port.TEN && port.TEN.trim()) {
            const normalizedName = port.TEN.toLowerCase().trim()
            if (portNames.has(normalizedName)) {
              duplicateNames.push(port.TEN)
            } else {
              portNames.add(normalizedName)
            }
          }
        }

        if (duplicateNames.length > 0) {
          this.$root.toastError(`Tên port không được trùng nhau: ${duplicateNames.join(', ')}`)
          return false
        }

        // Lọc bỏ port tạm thời khi submit (chỉ submit port đã có ID thật)
        const convertedPorts = this.currentItem.portList.map((port) => {
          if (typeof port.ID === 'string' && port.ID.startsWith('temp_')) {
            return { ...port, ID: null, isTemp: false } // reset ID
          }
          return port
        })
        if (convertedPorts.length > 0) {
          const result = await api.upsertPortThietBiIDC(this.axios, {
            portList: convertedPorts,
            thietBiId: this.currentItem.id
          })

          if (result && result.error_code === 'BSS-00000000') {
            return true
          } else {
            this.$root.toastError(result?.message_detail || 'Không thể lưu danh sách port')
            return false
          }
        }
        return true
      } catch (error) {
        this.$root.toastError('Lỗi khi lưu danh sách port: ' + error.message)
        return false
      }
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
  data: function() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      item: null,
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      tab_index: 1, // Default active tab
      currentItem: {
        id: null,
        donViQuanLyId: 0, // Will be set after data loads
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
        portList: [],
        // Thông tin lắp đặt
        lapDatTaiIdcId: null,
        toaNhaId: null,
        matSanId: null,
        phongZoneId: null,
        rackId: null,
        uTrenId: null,
        uDuoiId: null
      },

      // Dropdown lists
      donViQuanLyList: [],
      nhanVienList: [],
      mangThietBiList: [],
      loaiThietBiList: [],
      heThongList: [],
      trangThaiTaiSanList: [],
      trangThaiVanHanhList: [],
      hangSanXuatList: [],
      thietBiChaList: [],
      thanhPhanThietBiList: [],
      nhaCungCapList: [],
      loaiCongList: [],

      // Image upload properties
      displayIconUrl: '',
      pendingIconFile: null,
      pendingIconPreviewUrl: '',
      pendingRemoveIcon: false,
      uploadingIcon: false,
      loadingImageUrl: false,

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
        txtIcons: { enabled: false, invalid: false },
        txtSoCongNguonDauVao: { enabled: false, invalid: false },
        selectHangSanXuat: { enabled: false, invalid: false },
        txtNamSanXuat: { enabled: false, invalid: false },
        selectNoiSanXuat: { enabled: false, invalid: false },
        txtSoCongNguonDauRa: { enabled: false, invalid: false },
        txtGhiChu: { enabled: false, invalid: false },
        selectThietBiCha: { enabled: false, invalid: false },
        selectThanhPhanThietBi: { enabled: false, invalid: false },
        // Controls cho thông tin lắp đặt
        selectLapDatTaiIdc: { enabled: false, invalid: false },
        selectToaNha: { enabled: false, invalid: false },
        selectMatSan: { enabled: false, invalid: false },
        selectPhongZone: { enabled: false, invalid: false },
        selectRack: { enabled: false, invalid: false },
        selectUTren: { enabled: false, invalid: false },
        selectUDuoi: { enabled: false, invalid: false }
      }
    }
  }
}
</script>
<style>
.popup-box {
  max-height: 90vh !important;
  overflow-y: auto;
}

.fixed-icon-box {
  width: 32px;
  height: 32px !important;
  object-fit: cover;
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 2px;
}

.image-loading {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #f8f9fa;
}

.preview {
  margin-top: 8px;
}
</style>
