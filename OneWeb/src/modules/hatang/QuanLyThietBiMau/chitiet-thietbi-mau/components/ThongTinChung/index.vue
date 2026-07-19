<template src="./index.template.html"></template>
<script>
import { FIELD_VISIBILITY_CONFIG, checkFieldVisibility, getEquipmentTypeFlags } from './fieldVisibility.js'
import { getFieldOrder, groupFieldsIntoRows } from './fieldOrderConfig.js'
import { isFieldRequired as checkFieldRequired } from './fieldValidation.js'
import { getMangThietBiId, getLoaiThietBiId } from './equipmentTypeMapping.js'
import api from '../../../../Physicalequpment/api.js'
import CongKetNoi from '../CongKetNoi/index.vue'

export default {
  name: 'ThongTinChung',
  components: {
    CongKetNoi
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
    },
    equipmentType: {
      type: String,
      default: null
    }
  },
  data() {
    return {
      mangThietBiList: [],
      loaiThietBiList: [],
      phanLoaiThietBiList: [],
      hangSanXuatList: [],
      uploadingAvatar: false,
      avatarPreviewUrl: '',
      avatarObjectUrl: '',
      avatarPresignedUrl: '',
      loaiNguonDienList: [
        { ID: 1, TEN: '1 pha' },
        { ID: 3, TEN: '3 pha' }
      ],
      loaiMayList: [
        { ID: 1, TEN: 'Hạ áp' },
        { ID: 2, TEN: 'Tăng áp' }
      ]
    }
  },
  computed: {
    thongTinChungControl() {
      return this.controls?.thongTinChungControl || {}
    },
    equipmentTypeInfo() {
      return getEquipmentTypeFlags(this.equipmentType)
    },
    thongTinChungFields() {
      const fields = getFieldOrder('thongTinChung', this.equipmentTypeInfo)
      return fields.filter((fieldKey) => this.isFieldVisible(fieldKey))
    },
    thongTinChungRows() {
      return groupFieldsIntoRows(this.thongTinChungFields, 3)
    },
    displayAvatarUrl() {
      if (this.avatarObjectUrl) {
        return this.avatarObjectUrl
      }
      if (this.avatarPresignedUrl) {
        return this.avatarPresignedUrl
      }
      return this.currentItem.anhDaiDien || ''
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
    'currentItem.anhDaiDien': async function(newVal, oldVal) {
      if (this.avatarObjectUrl) {
        return
      }
      if (newVal === oldVal) {
        return
      }
      if (newVal) {
        if (newVal.startsWith('http://') || newVal.startsWith('https://')) {
          this.avatarPresignedUrl = newVal
          this.avatarPreviewUrl = newVal
        } else {
          await this.loadPresignedUrl(newVal)
        }
      } else {
        this.avatarPresignedUrl = ''
        this.avatarPreviewUrl = ''
      }
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
    equipmentType: {
      handler(newType) {
        if (newType && (this.currentMode === 'ADD' || this.currentMode === 'EDIT')) {
          this.autoMapEquipmentTypeInfo()
        }
      },
      immediate: true
    }
  },
  methods: {
    async syncAvatarPreviewFromCurrentItem() {
      if (this.avatarObjectUrl) {
        return
      }
      const iconPath = this.currentItem?.anhDaiDien
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

      require(item.mangThietBiId && item.mangThietBiId !== 0, 'selectMangThietBi')
      require(item.loaiThietBiId && item.loaiThietBiId !== 0, 'selectLoaiThietBi')
      require(item.phanLoaiThietBiId && item.phanLoaiThietBiId !== 0, 'selectPhanLoaiThietBi')
      require(item.maChungLoaiTB && item.maChungLoaiTB.trim() !== '', 'txtMaChungLoaiTB')
      require(item.tenChungLoaiTB && item.tenChungLoaiTB.trim() !== '', 'txtTenChungLoaiTB')

      if (this.isFieldRequired('hangSanXuatId')) {
        require(item.hangSanXuatId && item.hangSanXuatId !== 0, 'selectHangSanXuat')
      }

      if (this.isFieldRequired('dienApDinhDanh')) {
        require(item.dienApDinhDanh && item.dienApDinhDanh > 0, 'txtDienApDinhDanh')
      }

      if (this.isFieldRequired('dongDienDinhDanh')) {
        require(item.dongDienDinhDanh && item.dongDienDinhDanh > 0, 'txtDongDienDinhDanh')
      }

      if (this.isFieldRequired('congSuatDinhDanh')) {
        require(item.congSuatDinhDanh && item.congSuatDinhDanh > 0, 'txtCongSuatDinhDanh')
      }

      if (this.isFieldRequired('loaiNguonDien')) {
        require(item.loaiNguonDien && item.loaiNguonDien !== 0, 'selectLoaiNguonDien')
      }

      if (!isValid) {
        this.$root.toastError('Vui lòng nhập đầy đủ thông tin bắt buộc!')
      }

      return isValid
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
        mangThietBiId: item.mangThietBiId,
        loaiThietBiId: item.loaiThietBiId,
        phanLoaiThietBiId: item.phanLoaiThietBiId,
        maChungLoaiTB: item.maChungLoaiTB,
        tenChungLoaiTB: item.tenChungLoaiTB,
        hieuLuc: item.hieuLuc,
        hangSanXuatId: item.hangSanXuatId,
        maVatTu: item.maVatTu,
        ghiChu: item.ghiChu,
        anhDaiDien: item.anhDaiDien,
        khongGianLapDat: item.khongGianLapDat,
        fullDepth: item.fullDepth,
        congSuatDinhDanh: item.congSuatDinhDanh,
        dienApDinhDanh: item.dienApDinhDanh,
        loaiNguonDien: item.loaiNguonDien,
        heSoCosPhi: item.heSoCosPhi,
        chieuDai: item.chieuDai,
        chieuRong: item.chieuRong,
        chieuCao: item.chieuCao,
        loaiMay: item.loaiMay,
        cauHinhHeThong: item.cauHinhHeThong,
        dongDienDinhDanh: item.dongDienDinhDanh
      }
      return this.sanitizePayload(payload)
    },
    async loadInitialData() {
      try {
        this.$root.showLoading(true)
        const loadPromises = [
          this.loadMangThietBiList(),
          this.loadHangSanXuatList()
        ]
        await Promise.all(loadPromises)
        
        // Auto-map equipment type info nếu có
        if (this.equipmentType && (this.currentMode === 'ADD' || this.currentMode === 'EDIT')) {
          await this.autoMapEquipmentTypeInfo()
        }
        
        // Load cổng kết nối
        await this.$nextTick()
        if (this.$refs.CongKetNoi && this.$refs.CongKetNoi.loadInitialData) {
          await this.$refs.CongKetNoi.loadInitialData()
        }
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải danh mục')
      } finally {
        this.$root.showLoading(false)
      }
    },
    async autoMapEquipmentTypeInfo() {
      try {
        const mangThietBiId = getMangThietBiId(this.equipmentType)
        const loaiThietBiId = getLoaiThietBiId(this.equipmentType)
        
        if (mangThietBiId) {
          this.$set(this.currentItem, 'mangThietBiId', mangThietBiId)
          await this.$nextTick()
          await this.loadLoaiThietBiFromMangThietBi()
        }
        
        if (loaiThietBiId) {
          this.$set(this.currentItem, 'loaiThietBiId', loaiThietBiId)
          await this.$nextTick()
          await this.loadPhanLoaiThietBiList()
        }
        
        // Disable các field này khi đã auto-map
        if (this.controls.thongTinChungControl) {
          if (this.controls.thongTinChungControl.selectMangThietBi) {
            this.$set(this.controls.thongTinChungControl.selectMangThietBi, 'enabled', false)
          }
          if (this.controls.thongTinChungControl.selectLoaiThietBi) {
            this.$set(this.controls.thongTinChungControl.selectLoaiThietBi, 'enabled', false)
          }
        }
      } catch (error) {
        console.error('Error auto mapping equipment type info:', error)
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
        if (this.currentItem.mangThietBiId) {
          this.loaiThietBiList = await api.getDanhMucLoaiThietBi(this.axios, this.currentItem.mangThietBiId)
        } else {
          this.loaiThietBiList = []
        }
      } catch (error) {
        this.loaiThietBiList = []
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
    async loadHangSanXuatList() {
      try {
        // TODO: Replace with actual API call
        this.hangSanXuatList = []
      } catch (error) {
        this.hangSanXuatList = []
      }
    },
    isFieldVisible(fieldKey) {
      const condition = FIELD_VISIBILITY_CONFIG[fieldKey]
      return checkFieldVisibility(this.equipmentTypeInfo, condition)
    },
    getFieldLabel(fieldKey) {
      const labels = {
        mangThietBiId: 'Mảng thiết bị',
        loaiThietBiId: 'Loại thiết bị',
        phanLoaiThietBiId: 'Phân loại TB',
        maChungLoaiTB: 'Mã chủng loại thiết bị',
        tenChungLoaiTB: 'Tên chủng loại TB',
        hieuLuc: 'Hiệu lực',
        hangSanXuatId: 'Hãng sản xuất',
        maVatTu: 'Mã vật tư',
        ghiChu: 'Ghi chú',
        anhDaiDien: 'Ảnh đại diện',
        khongGianLapDat: 'Không gian lắp đặt',
        fullDepth: 'Full depth',
        congSuatDinhDanh: this.getCongSuatLabel(),
        dienApDinhDanh: this.getDienApLabel(),
        loaiNguonDien: 'Loại nguồn điện',
        heSoCosPhi: 'Hệ số cos phi',
        chieuDai: 'Chiều dài (cm)',
        chieuRong: 'Chiều rộng (cm)',
        chieuCao: 'Chiều cao (cm)',
        loaiMay: 'Loại máy',
        cauHinhHeThong: 'Cấu hình hệ thống',
        dongDienDinhDanh: 'Dòng định danh (A)'
      }
      return labels[fieldKey] || fieldKey
    },
    getCongSuatLabel() {
      if (this.equipmentTypeInfo.isMayBienAp) {
        return 'Công suất định danh (W)'
      } else if (this.equipmentTypeInfo.isRPDU) {
        return 'Công suất định danh (VA)'
      }
      return 'Công suất định danh'
    },
    getDienApLabel() {
      if (this.equipmentTypeInfo.isTuTrungThe || this.equipmentTypeInfo.isMayBienAp) {
        return 'Điện áp định danh (kV)'
      } else if (this.equipmentTypeInfo.isMayPhatDien || this.equipmentTypeInfo.isTuHaThe || this.equipmentTypeInfo.isTuATS || this.equipmentTypeInfo.isUPS || this.equipmentTypeInfo.isRPDU) {
        return 'Điện áp định danh (V)'
      }
      return 'Điện áp định danh'
    },
    isFieldRequired(fieldKey) {
      return checkFieldRequired(fieldKey, this.equipmentTypeInfo)
    },
    getControlKeyFromFieldKey(fieldKey) {
      const fieldToControlMap = {
        mangThietBiId: 'selectMangThietBi',
        loaiThietBiId: 'selectLoaiThietBi',
        phanLoaiThietBiId: 'selectPhanLoaiThietBi',
        maChungLoaiTB: 'txtMaChungLoaiTB',
        tenChungLoaiTB: 'txtTenChungLoaiTB',
        hieuLuc: 'selectHieuLuc',
        hangSanXuatId: 'selectHangSanXuat',
        maVatTu: 'txtMaVatTu',
        ghiChu: 'txtGhiChu',
        anhDaiDien: 'txtAnhDaiDien',
        khongGianLapDat: 'txtKhongGianLapDat',
        fullDepth: 'txtFullDepth',
        congSuatDinhDanh: 'txtCongSuatDinhDanh',
        dienApDinhDanh: 'txtDienApDinhDanh',
        loaiNguonDien: 'selectLoaiNguonDien',
        heSoCosPhi: 'txtHeSoCosPhi',
        chieuDai: 'txtChieuDai',
        chieuRong: 'txtChieuRong',
        chieuCao: 'txtChieuCao',
        loaiMay: 'selectLoaiMay',
        cauHinhHeThong: 'txtCauHinhHeThong',
        dongDienDinhDanh: 'txtDongDienDinhDanh'
      }
      return fieldToControlMap[fieldKey] || null
    },
    isFieldInvalid(fieldKey) {
      const controlKey = this.getControlKeyFromFieldKey(fieldKey)
      if (!controlKey) return false
      const control = this.thongTinChungControl?.[controlKey]
      return control && control.invalid === true
    },
    getSectionColClass(section) {
      const sectionClassMap = {
        system: 'col-sm-4 col-12',
        thongTinChung: 'col-sm-4 col-12'
      }
      return sectionClassMap[section] || 'col-sm-4 col-12'
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

        this.setAvatarObjectUrl(URL.createObjectURL(file))

        const uploadResult = await this.uploadAvatarFile(file)
        if (!uploadResult) {
          this.clearAvatarObjectUrl()
          return
        }
        
        const { previewUrl, storedUrl } = this.resolveUploadedUrl(uploadResult)
        
        if (!storedUrl && !previewUrl) {
          this.$root.toastError('Không thể xác định đường dẫn ảnh sau khi upload')
          this.clearAvatarObjectUrl()
          return
        }

        if (storedUrl) {
          this.$set(this.currentItem, 'anhDaiDien', storedUrl)
        }

        if (previewUrl) {
          this.avatarPresignedUrl = previewUrl
          this.avatarPreviewUrl = previewUrl
        } else if (storedUrl) {
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
    async loadPresignedUrl(relativeUrl) {
      if (!relativeUrl) {
        return
      }
      
      if (relativeUrl.startsWith('http://') || relativeUrl.startsWith('https://')) {
        this.avatarPresignedUrl = relativeUrl
        this.avatarPreviewUrl = relativeUrl
        return
      }

      try {
        const response = await this.$root.context.post('/web-ecms/storage/getPresignedUrl', {
          fileSource: relativeUrl
        })

        let presignedUrl = null
        if (response && response.error == 200 && response.data && response.data.result) {
          presignedUrl = response.data.result
        }

        if (presignedUrl) {
          this.avatarPresignedUrl = presignedUrl
          this.avatarPreviewUrl = presignedUrl
        }
      } catch (error) {
        // Silent fail
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
.w200 {
  width: 200px;
}
.form-control::placeholder {
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
  box-shadow: none !important;
  outline: none !important;
}
</style>
