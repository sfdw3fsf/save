<template src="./index.template.html"></template>
<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import api from '../../../api'

export default {
  name: 'ThongTinChung',
  components: {
    Treeselect
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
      kieuLapDatList: [], // Kiểu lắp đặt: 0-rPDU ngang, 1-rPDU dọc
      uploadingAvatar: false,
      avatarPreviewUrl: this.currentItem?.anhDaiDien || '',
      avatarObjectUrl: ''
    }
  },
  computed: {
    congSuatDinhDanh() {
      const dienAp = parseFloat(this.currentItem.dienApDinhDanh) || 0
      const dongDien = parseFloat(this.currentItem.dongDienDinhDanh) || 0
      if (dienAp > 0 && dongDien > 0) {
        return (dienAp * dongDien).toFixed(2)
      }
      return ''
    },
    displayAvatarUrl() {
      return this.avatarPreviewUrl || this.currentItem.anhDaiDien || ''
    }
  },
  watch: {
    'currentItem.mangThietBiId': async function(newVal) {
      if (newVal && newVal !== 0) {
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
    'currentItem.anhDaiDien': function(newVal) {
      if (this.avatarObjectUrl) {
        return
      }
      this.avatarPreviewUrl = newVal || ''
    }
  },
  methods: {
    resetValidation() {
      if (!this.controls) {
        return
      }
      Object.keys(this.controls).forEach((key) => {
        const control = this.controls[key]
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
        const control = this.controls?.[controlKey]
        if (control) {
          this.$set(control, 'invalid', true)
        }
        isValid = false
      }

      require(item.tenThietBi && item.tenThietBi.trim() !== '', 'txtTenThietBi')
      require(item.mangThietBiId && item.mangThietBiId !== 0, 'selectMangThietBi')
      require(item.loaiThietBiId && item.loaiThietBiId !== 0, 'selectLoaiThietBi')
      require(item.serialNumber && item.serialNumber.trim() !== '', 'txtSerialNumber')
      require(item.hangSanXuatId && item.hangSanXuatId !== 0, 'selectHangSanXuat')
      require(item.dongSanPham && item.dongSanPham.trim() !== '', 'txtDongSanPham')
      require(item.congSuatThietKe && item.congSuatThietKe !== 0, 'txtCongSuatThietKe')
      require(item.soCongNguonDauVao && item.soCongNguonDauVao !== 0, 'txtSoCongNguonDauVao')
      require(item.soCongNguonDauRa && item.soCongNguonDauRa !== 0, 'txtSoCongNguonDauRa')
      require(item.trangThaiTaiSanId !== null && item.trangThaiTaiSanId !== undefined, 'selectTrangThaiTaiSan')
      require(item.trangThaiVanHanhId && item.trangThaiVanHanhId !== 0, 'selectTrangThaiVanHanh')
      require(item.phamViIdcId && item.phamViIdcId !== 0, 'selectPhamViIdc')
      require(item.donViQuanLyId && item.donViQuanLyId !== 0, 'selectDonViQuanLy')
      require(item.nguoiQuanLyId && item.nguoiQuanLyId !== 0, 'selectNhanVienQuanLy')
      require(item.maTheTaiSan && item.maTheTaiSan.trim() !== '', 'txtMaTaiSan')

      if (!isValid) {
        this.$root.toastError('Vui lòng nhập đầy đủ thông tin bắt buộc!')
      }

      return isValid
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
    // Load danh mục
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
          this.loadKieuLapDatList()
        ]
        await Promise.all(loadPromises)
        // Load loại thiết bị nếu đã có mảng thiết bị
        if (this.currentItem.mangThietBiId) {
          await this.loadLoaiThietBiFromMangThietBi()
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
        this.loaiThietBiList = await api.getDanhMucLoaiThietBiFromMangThietBi(
          this.axios,
          this.currentItem.mangThietBiId
        )
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
          this.phanLoaiThietBiList = await api.getDanhMucPhanLoaiThietBi(
            this.axios,
            this.currentItem.loaiThietBiId
          )
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
          return
        }
        const { previewUrl, storedUrl } = this.resolveUploadedUrl(uploadResult)
        if (!previewUrl && !storedUrl) {
          this.$root.toastError('Không thể xác định đường dẫn ảnh sau khi upload')
          return
        }

        this.clearAvatarObjectUrl()
        this.$nextTick(() => {
          this.avatarPreviewUrl = previewUrl
        })
      } catch (error) {
        this.$root.toastError(error?.message || 'Upload ảnh không thành công')
      }
    },
    async uploadAvatarFile(file) {
      try {
        this.uploadingAvatar = true
        const formData = new FormData()
        formData.append('file', file)
        const response = await this.$root.context.post('/web-ecms/storage/uploadFile', formData)
        const uploadData = this.extractUploadPayload(response)
        if (uploadData) {
          return uploadData
        }
        this.$root.toastError(response?.data?.message || response?.message || 'Upload ảnh thất bại')
        return null
      } catch (error) {
        this.$root.toastError(error?.message || 'Không thể upload ảnh')
        return null
      } finally {
        this.uploadingAvatar = false
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
      if ((response.error_code === 'BSS-00000000' || response.error === '200') && response?.data?.status) {
        return response.data.message
      }
      if (response.status === 200 && response.data?.status) {
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
    }
  },
  created: async function() {
    await this.loadInitialData()
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
  outline: none !important;    /* Xóa viền outline mặc định */
}
</style>

