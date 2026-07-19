<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import TabInterface from '../../components/Tabs/TabInterface/index.vue'
import TabPower from '../../components/Tabs/TabPower/index.vue'
import TabConsole from '../../components/Tabs/TabConsole/index.vue'
import TabHistory from '../../components/Tabs/TabHistory/index.vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ModalPortThietBi from '../../components/Modals/ModalPortThietBi/index.vue'
import { PHANLOAI_NHOM } from '../../quanly-thietbi-idc/const.js'
import moment from 'moment'
import api from '../api'

export default {
  name: 'ModalChiTietThietBi',
  props: {
    modalID: null,
    heThongTbi: null,
    isKhachHang: { type: Boolean, default: true },
    phieutc: null,
    phieuThiCongDetail: { type: Object, default: null }
  },
  components: {
    TabInterface,
    TabPower,
    TabConsole,
    TabHistory,
    ModalPortThietBi,
    BssErrorMarker,
    BssRequiredMarker
  },
  created: async function() {
    await this.loadInitialData()
  },
  validations: {},
  watch: {
    'currentItem.lapDatTaiIdcId': function(newVal, oldVal) {
      this.onChangeLapDatTaiIdc()
    },
    'currentItem.toaNhaId': function(newVal, oldVal) {
      this.onChangeToaNha()
    },
    'currentItem.matSanId': function(newVal, oldVal) {
      this.onChangeMatSan()
    },
    'currentItem.phongZoneId': function(newVal, oldVal) {
      this.onChangePhongZone()
    },
    'currentItem.rack': function(newVal, oldVal) {
      this.onChangeRack()
    },
    'currentItem.mangThietBiId': function(newVal, oldVal) {
      this.onChangeMangThietBi()
    }
  },
  computed: {
    idcFromWork() {
      return this.phieuThiCongDetail?.IDC_ID || null
    },
    minDate() {
      // Cho phép chọn từ năm 2000
      return new Date(2000, 0, 1)
    },
    maxDate() {
      // Cho phép chọn đến 10 năm sau
      const maxDate = new Date()
      maxDate.setFullYear(maxDate.getFullYear() + 10)
      return maxDate
    }
  },
  methods: {
    // Xác định nhóm port dựa trên LOAICONG_ID -> THUOCNHOM; fallback sang PHANLOAI nếu cần
    resolveGroupId(port) {
      if (!port) return null
      const typeId = port.LOAICONG_ID
      if (typeId && Array.isArray(this.loaiCongList) && this.loaiCongList.length) {
        const type = this.loaiCongList.find((x) => x.ID == typeId)
        if (type && type.THUOCNHOM != null && type.THUOCNHOM !== '') {
          return Number(type.THUOCNHOM)
        }
      }
      // Fallback: dùng PHANLOAI nếu API đã trả về
      if (port.PHANLOAI != null) return Number(port.PHANLOAI)
      return null
    },
    closeModal: function() {
      this.$refs.ModalChiTietThietBi.hide(this.modalID)
      this.modeFormThiCong = null
    },
    show: async function(item, mode, thiCongMode) {
      // Reset về tab đầu tiên khi mở modal

      if (item) {
        this.currentMode = mode
        this.modeFormThiCong = thiCongMode || null
        let newItem = {
          id: item.ID ?? item.THIETBI_ID,
          thietBiChaId: item.THIETBICHA_ID,
          mangThietBiId: item.MANG_THIET_BI_ID,
          loaiThietBiId: item.LOAI_THIET_BI_ID,
          thanhPhanThietBiIds: item.THANHPHAN_IDS ? JSON.parse(item.THANHPHAN_IDS) : [],
          noiSanXuatId: item.NHACUNGCAP_ID,
          loaiKhachHangId: item.LOAI_KHACH_HANG_ID,
          heThongId: item.HETHONGTBI_ID,
          lapDatTaiIdcId: item.IDC_ID,
          tenThietBi: item.TEN ?? item.TEN_THIET_BI,
          toaNhaId: item.TOANHA_ID,
          matSanId: item.MATSAN_ID,
          phongZoneId: item.PHONG_ID,
          dongSanPham: item.MODEL,
          rack: item.IDC_RACK_ID,
          cauHinhChiTiet: null,
          uTrenId: item.UP_UNIT_ID,
          uDuoiId: item.DOWN_UNIT_ID,
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
          namSanXuat: item.NAMSX,
          noiSanXuatId: item.NHACUNGCAP_ID,
          soCongNguonDauRa: item.SO_PORT_INTERFACE,
          ghiChu: item.GHI_CHU,
          khachHang: null,
          soHopDong: null,
          maThueBao: item.THOI_DIEM_LAP_DAT,
          uDuoiPos: item.DOWN_UNIT_POS,
          uTrenPos: item.UP_UNIT_POS,
          nhanVienQlId: item.NHANVIEN_ID,
          phamViIDCId: item.PHAMVI_IDC_ID,
          donViQuanLy: item.DONVIQLY_ID
        }
        this.currentItem = { ...newItem, tenThietBi: newItem.tenThietBi || item.TEN_THIET_BI }

        // Load existing image if available
        if (this.currentItem.icons) {
          this.loadExistingImage(this.currentItem.icons)
        }
      } else {
        this.resetCurrentItem()
        this.currentMode = mode
      }
      if (this.heThongTbi) {
        this.currentItem.heThongId = this.heThongTbi.HETHONGTBI_ID
        this.currentItem.khachHang = this.heThongTbi.TEN_KH
        this.currentItem.soHopDong = this.heThongTbi.SO_HD
        this.currentItem.maThueBao = this.heThongTbi.MA_TB
        this.currentItem.hdtbId = this.heThongTbi.HDTB_ID
        this.currentItem.tinhId = this.heThongTbi.TINH_ID
      }
      this.currentItem.loaiKhachHangId = this.isKhachHang ? 1 : 0
      this.item = item
      this.tab_index =
        (this.currentMode != 'ADD' && this.modeFormThiCong == null && this.item != null) ||
        (this.currentMode == 'ADD' && this.modeFormThiCong == null && this.item == null)
          ? 1
          : 3
      this.port_tab_index = 1 // Reset về tab port đầu tiên
      if (this.currentMode !== 'ADD' && this.currentItem.id) {
        this.loadPortList(this.currentItem.id)
      } else {
        this.$set(this.currentItem, 'portList', { interfaces: [], powers: [], consoles: [] })
      }
      if(this.modeFormThiCong === 'LAPDAT' || this.modeFormThiCong === 'THAY_DOI_LAP_DAT') {
        this.currentItem.lapDatTaiIdcId = this.idcFromWork
      }
      this.loadInitialData()
      this.$refs.ModalChiTietThietBi.show()
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
              'Bạn có chắc chắn muốn lưu thông tin lắp đặt thiết bị này?',
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
          this.$root.toastSuccess('Lưu thành công!')
          this.closeModal()
        } catch (error) {
          this.handleError(error)
        } finally {
          this.$emit('reloadDataDevice')
          this.$root.showLoading(false)
        }
      }
    },

    async showModalPortThietBi() {
      await this.loadLoaiCongList()
      this.$refs.ModalPortThietBi.show()
    },

    async editPort(portData) {
      await this.loadLoaiCongList()

      // Đánh dấu port đang được edit
      const portToEdit = { ...portData, isEdit: true }

      // Nếu là port tạm thời, giữ nguyên ID tạm thời
      if (portData.isTemp) {
        portToEdit.isTemp = true
      }

      this.$refs.ModalPortThietBi.show(portToEdit)
    },

    async onPortSaved(portData) {
      try {
        // Kiểm tra trùng tên port
        const excludeId = portData.isEdit ? portData.ID : null
        if (this.checkDuplicatePortName(portData.TEN, excludeId)) {
          this.$toast.error(`Tên port "${portData.TEN}" đã tồn tại!`)
          return
        }

        // Mapping loaiCong để lấy THUOCNHOM và phân loại port
        let phanLoai = PHANLOAI_NHOM.INTERFACE // Default là interface
        if (portData.LOAICONG_ID && Array.isArray(this.loaiCongList) && this.loaiCongList.length) {
          const loaiCong = this.loaiCongList.find((x) => x.ID == portData.LOAICONG_ID)
          if (loaiCong && loaiCong.THUOCNHOM) {
            phanLoai = Number(loaiCong.THUOCNHOM)
          }
        }

        // Tạo ID tạm thời cho port mới nếu chưa có ID
        if (!portData.ID || portData.ID === 0) {
          portData.ID = this.generateTempPortId()
          portData.isTemp = true // Đánh dấu là port tạm thời
        }

        // Thêm PHANLOAI vào portData
        const portWithPhanLoai = {
          ID: portData.ID,
          LOAICONG_ID: portData.LOAICONG_ID || null,
          TEN: portData.TEN.trim(),
          GIATRI_CONG: portData.GIATRI_CONG,
          PHANLOAI: phanLoai,
          THONGSO_KYTHUAT: portData.THONGSO_KYTHUAT,
          TRANGTHAI_SD: portData.TRANGTHAI_SD,
          TRANGTHAI_DAUNOI: portData.TRANGTHAI_DAUNOI,
          GHI_CHU: portData.GHI_CHU,
          isTemp: portData.isTemp || false,
          isEdit: portData.isEdit || false
        }

        if (portData.isEdit) {
          // Tìm và xóa port cũ khỏi tất cả các nhóm
          Object.keys(this.currentItem.portList).forEach((group) => {
            const index = this.currentItem.portList[group].findIndex((port) => port.ID === portData.ID)
            if (index !== -1) {
              this.currentItem.portList[group].splice(index, 1)
            }
          })

          // Thêm port mới vào nhóm phù hợp
          this.addPortToCorrectGroup(portWithPhanLoai)
          this.$toast.success('Đã cập nhật port thành công')
        } else {
          // Thêm port mới vào nhóm phù hợp
          this.addPortToCorrectGroup(portWithPhanLoai)
          this.$toast.success('Đã thêm port vào danh sách')
        }
      } catch (e) {
        this.$toast.error('Không thể xử lý port: ' + e.message)
      }
    },

    // Helper method để tạo ID tạm thời cho port mới
    generateTempPortId() {
      return 'temp_' + Date.now()
    },

    // Helper method để kiểm tra trùng tên port
    checkDuplicatePortName(portName, excludeId = null) {
      if (!portName || !this.currentItem.portList) return false

      const allPorts = []
      Object.values(this.currentItem.portList).forEach((groupArr) => {
        if (Array.isArray(groupArr)) {
          allPorts.push(...groupArr)
        }
      })

      return allPorts.some(
        (port) => port.TEN && port.TEN.toLowerCase().trim() === portName.toLowerCase().trim() && port.ID !== excludeId
      )
    },

    // Helper method để thêm port vào đúng nhóm
    addPortToCorrectGroup(portData) {
      const phanLoai = portData.PHANLOAI

      if (phanLoai === PHANLOAI_NHOM.INTERFACE) {
        // Interface ports
        this.currentItem.portList.interfaces.push(portData)
      } else if (phanLoai === PHANLOAI_NHOM.POWER) {
        // Power ports
        this.currentItem.portList.powers.push(portData)
      } else if (phanLoai === PHANLOAI_NHOM.CONSOLE) {
        // Console ports
        this.currentItem.portList.consoles.push(portData)
      } else {
        // Default to interface if unknown
        this.currentItem.portList.interfaces.push(portData)
      }
    },

    async onPortSubmit(thietBiId) {
      let listPortSubmit = []
      if (this.currentItem.portList && typeof this.currentItem.portList === 'object') {
        Object.values(this.currentItem.portList).forEach((groupArr) => {
          if (Array.isArray(groupArr)) {
            listPortSubmit = listPortSubmit.concat(groupArr)
          }
        })
      }

      // Kiểm tra trùng tên port trước khi submit
      const duplicateNames = []
      const portNames = new Set()

      for (const port of listPortSubmit) {
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
      const convertedPorts = listPortSubmit.map((port) => {
        if (typeof port.ID === 'string' && port.ID.startsWith('temp_')) {
          return { ...port, ID: null, isTemp: false } // reset ID
        }
        return port
      })

      if (convertedPorts.length > 0) {
        await api.upsertPortThietBiKhachHang(this.axios, {
          portList: convertedPorts,
          thietBiId
        })
      }
      return true
    },

    async deletePort() {
      try {
        const ports = [].concat(this.portSelection || [])
        if (ports.length === 0) {
          this.$toast.warning('Vui lòng chọn port cần xóa')
          return
        }

        const portNames = ports.map((port) => port.TEN || port.ID).join(', ')
        const confirmFlag = await this.$confirm(
          `Bạn có chắc chắn muốn xoá ${ports.length} port: ${portNames}?`,
          'Xác nhận',
          {
            confirmButtonText: 'Xoá',
            cancelButtonText: 'Huỷ',
            type: 'warning',
            dangerouslyUseHTMLString: true
          }
        )
          .then(() => true)
          .catch(() => false)

        if (confirmFlag) {
          this.$root.showLoading(true)
          const portIdsToDelete = ports.map((port) => port.ID)

          // Xóa port từ tất cả các nhóm
          if (this.currentItem.portList && typeof this.currentItem.portList === 'object') {
            Object.keys(this.currentItem.portList).forEach((group) => {
              this.currentItem.portList[group] = this.currentItem.portList[group].filter(
                (port) => !portIdsToDelete.includes(port.ID)
              )
            })
          }

          // Phân loại port đã xóa để hiển thị thông báo phù hợp
          const tempPorts = ports.filter((port) => port.isTemp)
          const realPorts = ports.filter((port) => !port.isTemp)

          let message = `Đã xóa ${ports.length} port thành công!`
          if (tempPorts.length > 0 && realPorts.length > 0) {
            message = `Đã xóa ${realPorts.length} port từ server và ${tempPorts.length} port tạm thời!`
          } else if (tempPorts.length > 0) {
            message = `Đã xóa ${tempPorts.length} port tạm thời!`
          }

          this.$root.toastSuccess(message)
        }
      } catch (error) {
        this.$root.toastError('Có lỗi khi xóa port')
      } finally {
        this.$root.showLoading(false)
      }
    },

    // Change handlers for SelectExt components
    onChangePhamViIdc: function() {
      // Handle change logic
    },

    onChangeMangThietBi: async function() {
      if (this.currentItem.mangThietBiId) {
        this.loaiThietBiList = await api.getDanhMuc(this.axios, 'IDC-LOAI-TBI', this.currentItem.mangThietBiId)
      } else {
        this.loaiThietBiList = []
      }
    },

    onChangeLoaiThietBi: function() {
      // Handle change logic
    },

    onChangeLoaiKhachHang: function() {
      // Handle change logic
    },

    onChangeHeThong: function() {
      // Handle change logic
    },

    onChangeToaNha: function() {
      // Handle change logic
    },

    onChangePhongZone: function() {
      // Handle change logic
    },

    onChangeUTren: function() {
      // Handle change logic
    },

    onChangeUDuoi: function() {
      // Handle change logic
    },

    onChangeCanCuLapDat: function() {
      // Handle change logic
    },

    onChangeTrangThaiTaiSan: function() {
      // Handle change logic
    },

    onChangeTrangThaiVanHanh: function() {
      // Handle change logic
    },

    onChangeHangSanXuat: function() {
      // Handle change logic
    },

    onChangeNoiSanXuat: function() {
      // Handle change logic for nơi sản xuất
    },

    // Normalizer cho TreeSelect
    normalizer(node) {
      return {
        id: node.ID,
        label: node.TEN,
        children: node.children
      }
    },

    onPortSelectionChanged: function(ports) {
      const newPorts = Array.isArray(ports) ? ports : [ports]

      newPorts.forEach((port) => {
        if (!this.portSelection.some((p) => p.ID === port.ID)) {
          this.portSelection.push(port)
        }
      })
    },

    // Image upload methods
    onClickUploadIcon: function() {
      if (this.$refs.fileInputIcon) {
        this.$refs.fileInputIcon.value = null
        this.$refs.fileInputIcon.click()
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
    resetCurrentItem: function() {
      this.currentItem = {
        id: null,
        mangThietBiId: null,
        loaiThietBiId: null,
        thanhPhanThietBiIds: [],
        noiSanXuatId: null,
        loaiKhachHangId: null,
        heThongId: null,
        donViQuanLy: this.$root.token.getDonViID(),
        nguoiQuanLy: '',
        lapDatTaiIdcId: null,
        tenThietBi: '',
        toaNhaId: null,
        matSanId: null,
        dongSanPham: '',
        phongZoneId: null,
        rack: '',
        cauHinhChiTiet: '',
        uTrenId: null,
        uDuoiId: null,
        serialNumber: '',
        thoiDiemLapDat: '',
        tagNumber: '',
        maTheTaiSan: '',
        trangThaiTaiSanId: null,
        congSuatThietKe: null,
        trangThaiVanHanhId: null,
        icons: '',
        soCongNguonDauVao: null,
        hangSanXuatId: null,
        namSanXuat: new Date().getFullYear(),
        soCongNguonDauRa: null,
        ghiChu: '',
        khachHang: null,
        soHopDong: null,
        maThueBao: null,
        hdtbId: null,
        tinhId: null
      }
      // Reset image upload variables
      this.pendingIconFile = null
      this.pendingIconPreviewUrl = ''
      this.displayIconUrl = ''
      this.pendingRemoveIcon = false
    },

    resetValidation: function() {
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })
    },

    validateForm: function() {
      if(this.modeFormThiCong === 'THAO_DO') {
        return true
      }
      let isValid = true

      // Reset tất cả validation trước khi validate
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })
      console.log(this.currentItem);
      
      // 1. Tên thiết bị (bắt buộc)
      if (!this.currentItem.tenThietBi || this.currentItem.tenThietBi.trim() === '') {
        this.controls.txtTenThietBi.invalid = true
        isValid = false
      }

      // 2. Mảng thiết bị (bắt buộc)
      if (!this.currentItem.mangThietBiId) {
        this.controls.selectMangThietBi.invalid = true
        isValid = false
      }

      // 3. Loại thiết bị (bắt buộc)
      if (!this.currentItem.loaiThietBiId) {
        this.controls.selectLoaiThietBi.invalid = true
        isValid = false
      }

      // 4. Serial Number (bắt buộc)
      if (!this.currentItem.serialNumber || this.currentItem.serialNumber.trim() === '') {
        this.controls.txtSerialNumber.invalid = true
        isValid = false
      }

      // 5. Hãng sản xuất (bắt buộc)
      if (!this.currentItem.hangSanXuatId) {
        this.controls.selectHangSanXuat.invalid = true
        isValid = false
      }

      // 6. Model (bắt buộc)
      if (!this.currentItem.dongSanPham || this.currentItem.dongSanPham.trim() === '') {
        this.controls.txtDongSanPham.invalid = true
        isValid = false
      }

      // 7. Năm sản xuất (không bắt buộc, chỉ validate khoảng năm hợp lệ nếu có giá trị)
      if (this.currentItem.namSanXuat && (this.currentItem.namSanXuat < 1900 || this.currentItem.namSanXuat > 2030)) {
        this.controls.txtNamSanXuat.invalid = true
        this.$root.toastError('Năm sản xuất phải trong khoảng từ 1900 đến 2030!')
        isValid = false
      }

      // 8. Công suất thiết kế (bắt buộc)
      if (!this.currentItem.congSuatThietKe || this.currentItem.congSuatThietKe <= 0) {
        this.controls.txtCongSuatThietKe.invalid = true
        isValid = false
      }

      // 9. Số cổng nguồn vào (bắt buộc)
      if (
        this.currentItem.soCongNguonDauVao === null ||
        this.currentItem.soCongNguonDauVao === undefined ||
        this.currentItem.soCongNguonDauVao < 0
      ) {
        this.controls.txtSoCongNguonDauVao.invalid = true
        isValid = false
      }

      // 10. Số cổng nguồn ra (bắt buộc)
      if (
        this.currentItem.soCongNguonDauRa === null ||
        this.currentItem.soCongNguonDauRa === undefined ||
        this.currentItem.soCongNguonDauRa < 0
      ) {
        this.controls.txtSoCongNguonDauRa.invalid = true
        isValid = false
      }

      // 11. Trạng thái Tài sản (bắt buộc)
      if (this.currentItem.trangThaiTaiSanId === null || this.currentItem.trangThaiTaiSanId === undefined) {
        this.controls.selectTrangThaiTaiSan.invalid = true
        isValid = false
      }

      // 12. Trạng thái Vận hành (bắt buộc)
      if (!this.currentItem.trangThaiVanHanhId) {
        this.controls.selectTrangThaiVanHanh.invalid = true
        isValid = false
      }

      // 13. Đơn vị quản lý (bắt buộc)
      if (!this.currentItem.donViQuanLy) {
        this.controls.selectDonViQuanLy.invalid = true
        isValid = false
      }

      // 14. Phạm vi IDC (bắt buộc)
      if (!this.currentItem.phamViIDCId) {
        this.controls.selectPhamViIdc.invalid = true
        isValid = false
      }

      // 15. Hệ thống (bắt buộc)
      if (!this.currentItem.heThongId) {
        this.controls.selectHeThong.invalid = true
        isValid = false
      }

      // 16. Loại khách hàng (bắt buộc)
      if (this.currentItem.loaiKhachHangId === null || this.currentItem.loaiKhachHangId === undefined) {
        this.controls.selectLoaiKhachHang.invalid = true
        isValid = false
      }

      // Validation cho khách hàng (nếu là khách hàng)
      if (this.isKhachHang) {
        // 17. Khách hàng (bắt buộc)
        if (!this.currentItem.khachHang || this.currentItem.khachHang.trim() === '') {
          this.controls.txtKhachHang.invalid = true
          isValid = false
        }

        // 18. Số hợp đồng (bắt buộc)
        if (!this.currentItem.soHopDong || this.currentItem.soHopDong.trim() === '') {
          this.controls.txtSoHopDong.invalid = true
          isValid = false
        }

        // 19. Mã thuê bao (bắt buộc)
        if (!this.currentItem.maThueBao || this.currentItem.maThueBao.trim() === '') {
          this.controls.txtMaThueBao.invalid = true
          isValid = false
        }
      } else {
        // Validation cho không phải khách hàng
        // 20. Nhân viên quản lý (bắt buộc)
        if (!this.currentItem.nhanVienQlId) {
          this.controls.selectNhanVienQuanLy.invalid = true
          isValid = false
        }

        // 21. Mã tài sản (bắt buộc)
        if (!this.currentItem.maTheTaiSan || this.currentItem.maTheTaiSan.trim() === '') {
          this.controls.txtMaTaiSan.invalid = true
          isValid = false
        }
      }

      // Validation cho tab Lắp đặt (nếu có dữ liệu lắp đặt)
      if (this.currentMode !== 'ADD') {
        // 22. Lắp đặt tại IDC (bắt buộc nếu có thông tin lắp đặt)
        if (this.currentItem.lapDatTaiIdcId && !this.currentItem.lapDatTaiIdcId) {
          this.controls.selectLapDatTaiIdc.invalid = true
          isValid = false
        }

        // 23. Toà nhà (bắt buộc nếu có thông tin lắp đặt)
        if (this.currentItem.lapDatTaiIdcId && !this.currentItem.toaNhaId) {
          this.controls.selectToaNha.invalid = true
          isValid = false
        }

        // 24. Thời điểm lắp đặt (bắt buộc nếu có thông tin lắp đặt)
        if (this.currentItem.lapDatTaiIdcId && !this.currentItem.thoiDiemLapDat) {
          this.controls.txtThoiDiemLapDat.invalid = true
          isValid = false
        }

        // Validate ngày lắp đặt nằm trong khoảng hợp lệ (2000 - 10 năm sau)
        if (this.currentItem.thoiDiemLapDat) {
          const selectedDate = moment(this.currentItem.thoiDiemLapDat).toDate()
          const minDate = new Date(2000, 0, 1)
          const maxDate = new Date()
          maxDate.setFullYear(maxDate.getFullYear() + 10)

          if (isNaN(selectedDate.getTime()) || selectedDate < minDate || selectedDate > maxDate) {
            this.controls.txtThoiDiemLapDat.invalid = true
            this.$root.toastError('Thời điểm lắp đặt phải trong khoảng từ 01/01/2000 đến ' + moment(maxDate).format('DD/MM/YYYY'))
            isValid = false
          }
        }

        // 25. U Trên và U Dưới (bắt buộc nếu đã chọn rack)
        if (this.currentItem.rack) {
          if (!this.currentItem.uTrenPos) {
            this.controls.selectUTren.invalid = true
            isValid = false
          }
          if (!this.currentItem.uDuoiPos) {
            this.controls.selectUDuoi.invalid = true
            isValid = false
          }
        }
      }

      if (!isValid) {
        this.$root.toastError('Vui lòng điền đầy đủ thông tin bắt buộc !')
      }

      return isValid
    },

    // API methods
    loadInitialData: async function() {
      try {
        this.$root.showLoading(true)
        await Promise.all([this.loadDanhMuc(), this.loadLoaiCongList()])
      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    loadLoaiCongList: async function() {
      try {
        this.loaiCongList = await api.getDanhMucLoaiCong(this.axios)
      } catch (e) {
        this.loaiCongList = []
      }
    },
    loadHeThongList: async function() {
      try {
        if (this.isKhachHang) {
          this.heThongList = await api.getDanhMucHeThong(this.axios)
        } else {
          this.heThongList = (await api.getDanhMucHeThongIDC(this.axios, this.$root.token.getDonViID())).map(
            (item) => ({
              ...item,
              HETHONGTBI_ID: item.ID
            })
          )
        }
      } catch (e) {
        this.heThongList = []
      }
    },
    loadDanhMuc: async function() {
      try {
        this.phamViIdcList = await api.getDanhMucDonViQuanLy(this.axios, this.$root.token)
        this.mangThietBiList = await api.getDanhMuc(this.axios, 'IDC-MANG-TBI')
        this.loaiKhachHangList = await api.getDanhMuc(this.axios, 'LOAI-KH')
        // this.heThongList = await api.getDanhMucHeThong(this.axios)
        await this.loadHeThongList()
        // luôn luôn lấy hết danh sách
        this.lapDatTaiIdcList = await api.getDanhMucIDC(this.axios, this.$root.token, 'VIEW')
        this.trangThaiVanHanhList = await api.getDanhMucTrangThaiVanHanh(this.axios)
        this.hangSanXuatList = await api.getDanhMuc(this.axios, 'IDC-HANG-SX')
        this.thietBiChaList = await api.getDanhSachThietBiCha(this.axios)
        this.nhanVienList = await api.getDanhMucNhanVienQuanLy(this.axios, this.$root.token)
        this.donViList = await api.getDanhMucDonViQuanLy(this.axios)
        // this.trangThaiTaiSanList = await api.getDanhMucTrangThaiTaiSan(this.axios)
        this.trangThaiTaiSanList = [
          { ID: '0', TEN: 'Đang sử dụng' },
          { ID: '1', TEN: 'Dừng hoạt động' },
          { ID: '2', TEN: 'Hỏng' },
          { ID: '3', TEN: 'Đã thanh lý' }
        ]
        this.thanhPhanThietBiList = await api.getDanhMucThanhPhanThietBi(this.axios)
        this.nhaCungCapList = await api.getDanhMucNhaCungCap(this.axios)
      } catch (error) {
        console.log(error)
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    onChangeLapDatTaiIdc: async function() {
      try {
        if (this.currentMode !== 'VIEW' && this.modeFormThiCong !== 'THAO_DO') {
          // Clear các thông tin con khi thay đổi IDC
          this.$set(this.currentItem, 'toaNhaId', null)
          this.$set(this.currentItem, 'matSanId', null)
          this.$set(this.currentItem, 'phongZoneId', null)
          this.$set(this.currentItem, 'rack', null)
          this.$set(this.currentItem, 'uTrenPos', null)
          this.$set(this.currentItem, 'uDuoiPos', null)
          this.matSanList = []
          this.phongZoneList = []
          this.rackList = []
          this.uTrenList = []
          this.uDuoiList = []
        }

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
    onChangeToaNha: async function() {
      try {
        if (this.currentMode !== 'VIEW' && this.modeFormThiCong !== 'THAO_DO') {
          // Clear các thông tin con khi thay đổi toà nhà
          this.$set(this.currentItem, 'matSanId', null)
          this.$set(this.currentItem, 'phongZoneId', null)
          this.$set(this.currentItem, 'rack', null)
          this.$set(this.currentItem, 'uTrenPos', null)
          this.$set(this.currentItem, 'uDuoiPos', null)
          this.phongZoneList = []
          this.rackList = []
          this.uTrenList = []
          this.uDuoiList = []
        }

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
    onChangeMatSan: async function() {
      try {
        if (this.currentMode !== 'VIEW' && this.modeFormThiCong !== 'THAO_DO') {
          // Clear các thông tin con khi thay đổi mặt sàn
          this.$set(this.currentItem, 'phongZoneId', null)
          this.$set(this.currentItem, 'rack', null)
          this.$set(this.currentItem, 'uTrenPos', null)
          this.$set(this.currentItem, 'uDuoiPos', null)
          this.rackList = []
          this.uTrenList = []
          this.uDuoiList = []
        }

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
    onChangePhongZone: async function() {
      try {
        if (this.currentMode !== 'VIEW' && this.modeFormThiCong !== 'THAO_DO') {
          // Clear các thông tin con khi thay đổi phòng/zone
          this.$set(this.currentItem, 'rack', null)
          this.$set(this.currentItem, 'uTrenPos', null)
          this.$set(this.currentItem, 'uDuoiPos', null)
          this.uTrenList = []
          this.uDuoiList = []
        }

        if (this.currentItem.phongZoneId && this.currentItem.lapDatTaiIdcId) {
          // Khi modeFormThiCong là THAO_DO thì fetch all giống VIEW (truyền null cho phieutcId)
          let phieutcId = (this.modeFormThiCong === 'THAO_DO' || this.currentMode === 'VIEW') 
            ? null 
            : (this.currentItem.hdtbId ? this.phieutc : null)
          let rs = await api.getDanhMucRackByPhongZone(this.axios, this.currentItem.phongZoneId, phieutcId)
          this.rackList = rs || []
        } else {
          this.rackList = []
        }
      } catch (error) {
        console.log(error)
        this.handleError(error)
      }
    },
    onChangeRack: async function() {
      if (this.currentMode !== 'VIEW' && this.modeFormThiCong !== 'THAO_DO') {
        // Clear các thông tin con khi thay đổi rack
        this.$set(this.currentItem, 'uTrenPos', null)
        this.$set(this.currentItem, 'uDuoiPos', null)
        this.uTrenList = []
        this.uDuoiList = []
      }

      if (this.currentItem.rack) {
        this.uTrenList = await api.getDanhMucUByRack(this.axios, this.currentItem.rack, this.currentMode === 'VIEW' || this.modeFormThiCong === 'THAO_DO')
        this.uDuoiList = await api.getDanhMucUByRack(this.axios, this.currentItem.rack, this.currentMode === 'VIEW' || this.modeFormThiCong === 'THAO_DO')
      }
    },
    // CRUD operations
    saveNewItem: async function() {
      let dto = {
        heThongTbiId: this.currentItem.heThongId,
        mangTbiId: this.currentItem.mangThietBiId,
        loaiThietBiId: this.currentItem.loaiThietBiId,
        thanhPhanThietBiIds: this.currentItem.thanhPhanThietBiIds,
        noiSanXuatId: this.currentItem.noiSanXuatId,
        ten: this.currentItem.tenThietBi,
        model: this.currentItem.dongSanPham,
        cauHinh: this.currentItem.cauHinhChiTiet,
        serialNumber: this.currentItem.serialNumber,
        partNumber: this.currentItem.tagNumber,
        csDinhDanh: this.currentItem.congSuatThietKe ? this.currentItem.congSuatThietKe.toString() : '',
        soPortConsole: this.currentItem.soCongNguonDauVao,
        soPortInterface: this.currentItem.soCongNguonDauRa,
        hangSxId: this.currentItem.hangSanXuatId,
        namSx: this.currentItem.namSanXuat ? this.currentItem.namSanXuat.toString() : '',
        iconPath: this.currentItem.icons,
        loaiKhachHang: this.currentItem.loaiKhachHangId ? this.currentItem.loaiKhachHangId.toString() : '',
        phongId: this.currentItem.phongZoneId,
        // idcId: this.currentItem.lapDatTaiIdcId,
        tdcRackId: this.currentItem.rack ? parseInt(this.currentItem.rack) : null,
        upUnitId: this.currentItem.uTrenId ? this.currentItem.uTrenId : null,
        downUnitId: this.currentItem.uDuoiId ? this.currentItem.uDuoiId : null,
        ngayLapDat: this.currentItem.thoiDiemLapDat,
        canCuLD: '',
        trangThaiSD: this.currentItem.trangThaiVanHanhId ? this.currentItem.trangThaiVanHanhId.toString() : '',
        trangThaiTsId: this.currentItem.trangThaiTaiSanId ? this.currentItem.trangThaiTaiSanId.toString() : '',
        maTs: this.currentItem.maTheTaiSan,
        hieuLuc: 1,
        tsNoiBo: '',
        moTa: this.currentItem.ghiChu,
        hdtbId: this.currentItem.hdtbId,
        tinhId: this.currentItem.tinhId,
        donViQLId: this.currentItem.donViQuanLy,
        phamViIDCId: this.currentItem.phamViIDCId,
        nguoiQLId: this.currentItem.nhanVienQlId
      }
      let rs = await api.themMoiThietBiKhachHang(this.axios, dto)
      try {
        if (rs.error_code == 'BSS-00000000') {
          // upsert port - kiểm tra validation trước
          const portSubmitSuccess = await this.onPortSubmit(rs.data[0].item)

          if (portSubmitSuccess) {
            if (this.heThongTbi) {
              this.$emit('themMoiThietBiSuccess', {
                ID: rs.data[0].item
              }) // Emit event to refresh parent grid
              this.closeModal()
            }
          } else {
            // Nếu port validation fail, không đóng modal
            return
          }
        } else {
          this.$root.toastError(rs.message_detail || 'Đã xảy ra lỗi!')
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },

    loadPortList: async function(thietBiId) {
      try {
        if (!thietBiId) {
          this.$set(this.currentItem, 'portList', [])
          return
        }
        this.$root.showLoading(true)
        const portList = await api.getDanhSachPortTheoThietBi(this.axios, thietBiId)

        if (portList && Array.isArray(portList)) {
          const interfaces = []
          const powers = []
          const consoles = []

          for (const p of portList) {
            const groupId = this.resolveGroupId(p)
            if (groupId === PHANLOAI_NHOM.INTERFACE) interfaces.push(p)
            else if (groupId === PHANLOAI_NHOM.POWER) powers.push(p)
            else if (groupId === PHANLOAI_NHOM.CONSOLE) consoles.push(p)
            else interfaces.push(p) // default
          }

          this.$set(this.currentItem, 'portList', { interfaces, powers, consoles })
        } else {
          this.$root.toastError('Không thể tải danh sách port')
          this.$set(this.currentItem, 'portList', {})
        }
      } catch (error) {
        this.$set(this.currentItem, 'portList', {})
        this.$root.toastError('Không thể tải danh sách port')
      } finally {
        this.$root.showLoading(false)
        console.log(this.currentItem.portList)
      }
    },

    updateItem: async function() {
      if (this.modeFormThiCong === 'LAPDAT') {
        let dto = {
          thietBiId: this.currentItem.id,
          rackId: this.currentItem.rack ? parseInt(this.currentItem.rack) : null,
          upUnitPos: this.currentItem.uTrenPos ? this.currentItem.uTrenPos : null,
          downUnitPos: this.currentItem.uDuoiPos ? this.currentItem.uDuoiPos : null,
          thoiDiemLapDat: this.currentItem.thoiDiemLapDat
            ? moment(this.currentItem.thoiDiemLapDat).format('YYYY-MM-DD')
            : null,
          maTheTaiSan: this.currentItem.maTheTaiSan,
          phieuThiCongId: this.phieutc
        }
        if (dto.upUnitPos && dto.downUnitPos && Number(dto.upUnitPos) > Number(dto.downUnitPos)) {
          throw new Error('Vị trí U trên phải nhỏ hơn vị trí U dưới!')
        }
        let rs = await api.lapDatThietBiKhachHang(this.axios, dto)
      } else if (this.modeFormThiCong === 'THAO_DO') {
        let dto = {
          thietBiId: this.currentItem.id,
          phieuThiCongId: this.phieutc
        }
        if (dto.upUnitPos && dto.downUnitPos && Number(dto.upUnitPos) > Number(dto.downUnitPos)) {
          throw new Error('Vị trí U trên phải nhỏ hơn vị trí U dưới!')
        }
        let rs = await api.thaoDoThietBiKhachHang(this.axios, dto)
        if (rs && rs.error_code === 'BSS-00000000') {
          this.$emit('reloadDataDevice') // Emit event to refresh parent grid
          this.closeModal()
        } else {
          this.$root.toastError(rs.message_detail || 'Đã xảy ra lỗi!')
        }
      } else if (this.modeFormThiCong === 'THAY_DOI_LAP_DAT') {
        let dto = {
          thietBiId: this.currentItem.id,
          rackId: this.currentItem.rack ? parseInt(this.currentItem.rack) : null,
          upUnitPos: this.currentItem.uTrenPos ? this.currentItem.uTrenPos : null,
          downUnitPos: this.currentItem.uDuoiPos ? this.currentItem.uDuoiPos : null,
          phieuThiCongId: this.phieutc
        }
        if (dto.upUnitPos && dto.downUnitPos && dto.upUnitPos > dto.downUnitPos) {
          throw new Error('Vị trí U trên phải nhỏ hơn vị trí U dưới!')
        }
        let rs = await api.thayDoiVitriLapDat(this.axios, dto)
        if (rs && rs.error_code === 'BSS-00000000') {
          this.$emit('reloadDataDevice') // Emit event to refresh parent grid
          this.closeModal()
        } else {
          this.$root.toastError(rs.message_detail || 'Đã xảy ra lỗi!')
        }
      }
    },

    deleteItem: async function() {
      // API call to delete item
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
    },
    onChangeTab: function(index) {
      this.tab_index = index
    }
  },
  data: function() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      item: null,
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      modeFormThiCong: null,
      tab_index: 1, // Tab chính: 1=Cấu hình thiết bị, 2=Lắp đặt, 3=Lịch sử thay đổi
      port_tab_index: 1, // Tab port: 1=Cổng dữ liệu, 2=Cổng nguồn, 3=Cổng điều khiển
      currentItem: {
        id: 0,
        mangThietBiId: 0,
        loaiThietBiId: 0,
        thanhPhanThietBiIds: [],
        noiSanXuatId: 0,
        loaiKhachHangId: 0,
        heThongId: 0,
        phamViIDCId: 0,
        donViQuanLy: '',
        nguoiQuanLy: '',
        lapDatTaiIdcId: 0,
        tenThietBi: '',
        toaNhaId: 0,
        matSanId: 0,
        dongSanPham: '',
        phongZoneId: 0,
        rack: '',
        cauHinhChiTiet: '',
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
        namSanXuat: new Date().getFullYear(),
        soCongNguonDauRa: 0,
        ghiChu: '',
        khachHang: null,
        soHopDong: null,
        maThueBao: null,
        hdtbId: null,
        tinhId: null
      },

      // Dropdown lists
      phamViIdcList: [],
      mangThietBiList: [],
      loaiThietBiList: [],
      thanhPhanThietBiList: [],
      nhaCungCapList: [],
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
      thietBiChaList: [],
      rackList: [],
      nhanVienList: [],
      donViList: [],
      loaiCongList: [],
      portList: { interfaces: [], powers: [], consoles: [] },
      portSelection: [],

      // Image upload variables
      pendingIconFile: null, // File ảnh đang chờ upload
      pendingIconPreviewUrl: '', // URL preview tạm thời
      displayIconUrl: '', // URL hiển thị ảnh
      pendingRemoveIcon: false, // Flag đánh dấu cần xóa ảnh
      loadingImageUrl: false, // Flag đang tải ảnh
      uploadingIcon: false, // Flag đang upload ảnh

      // Form controls
      controls: {
        selectPhamViIdc: { enabled: false, invalid: false },
        selectMangThietBi: { enabled: false, invalid: false },
        selectLoaiThietBi: { enabled: false, invalid: false },
        selectThanhPhanThietBi: { enabled: false, invalid: false },
        selectNoiSanXuat: { enabled: false, invalid: false },
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
        txtCauHinhChiTiet: { enabled: false, invalid: false },
        selectUTren: { enabled: false, invalid: false },
        selectUDuoi: { enabled: false, invalid: false },
        txtSerialNumber: { enabled: false, invalid: false },
        txtThoiDiemLapDat: { enabled: false, invalid: false },
        selectCanCuLapDat: { enabled: false, invalid: false },
        txtTagNumber: { enabled: false, invalid: false },
        txtMaTaiSan: { enabled: false, invalid: false },
        selectTrangThaiTaiSan: { enabled: false, invalid: false },
        txtCongSuatThietKe: { enabled: false, invalid: false },
        selectTrangThaiVanHanh: { enabled: false, invalid: false },
        txtIcons: { enabled: false, invalid: false },
        txtSoCongNguonDauVao: { enabled: false, invalid: false },
        selectHangSanXuat: { enabled: false, invalid: false },
        txtNamSanXuat: { enabled: false, invalid: false },
        txtSoCongNguonDauRa: { enabled: false, invalid: false },
        txtGhiChu: { enabled: false, invalid: false },
        txtKhachHang: { enabled: false, invalid: false },
        txtSoHopDong: { enabled: false, invalid: false },
        txtMaThueBao: { enabled: false, invalid: false },
        selectRack: { enabled: false, invalid: false },
        selectDonViQuanLy: { enabled: false, invalid: false },
        selectNhanVienQuanLy: { enabled: false, invalid: false }
      }
    }
  },
  mounted: function() {}
}
</script>
<style scoped>
.popup-box {
  max-height: 90vh !important;
  overflow-y: auto;
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
.form-control::placeholder {
  color: #999 !important;
}
/deep/ .vue-treeselect__placeholder {
  color: #999 !important;
}
</style>
