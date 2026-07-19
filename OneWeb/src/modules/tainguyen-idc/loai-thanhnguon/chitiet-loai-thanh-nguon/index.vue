<template src="./index.template.html"></template>
<script>
export default {
  name: 'ModalChiTietLoaiThanhNguon',
  data() {
    const defaultItem = {
      id: 0,
      ten: '',
      hangSxId: '',
      loaiDongDien: '',
      thongSoDongDien: null,
      dienApDongDien: null,
      csThietKe: null,
      lapDat: null,
      rong: null,
      dai: null,
      cao: null,
      slIecC13: null,
      slIecC19: null,
      slKhac: null,
      ghiChu: '',
      suDung: 1
    }

    return {
      currentMode: 'NEW', // NEW, EDIT, VIEW
      currentItem: { ...defaultItem },
      defaultItem,
      hangSanXuatSelectionList: [],
      controls: {
        txtTenLoaiThanhNguon: { invalid: false },
        selectLoaiDongDien: { invalid: false },
        selectHangSanXuat: { invalid: false },
        txtThongSoDongDien: { invalid: false },
        txtDienApDongDien: { invalid: false },
        selectLapDat: { invalid: false },
        txtCongSuatThietKe: { invalid: false },
        txtKichThuocChieuRong: { invalid: false },
        txtKichThuocChieuDai: { invalid: false },
        txtKichThuocChieuCao: { invalid: false },
        txtSoLuongIECC13: { invalid: false },
        txtSoLuongIECC19: { invalid: false },
        txtSoLuongKhac: { invalid: false }
      },
      loaiDongDienOptions: [
        { TEN: '1 pha', ID: 1 },
        { TEN: '3 pha', ID: 3 }
      ],
      lapDatOptions: [
        { TEN: 'Ngang', ID: 0 },
        { TEN: 'Dọc', ID: 1 }
      ],
      animationSettings: {
        effect: 'Zoom'
      },
      target: '.main-wrapper',
      enableWatchValidation: false
    }
  },

  watch: {
    'currentItem.ten'(val) {
      this.validateField('ten', val)
    },
    'currentItem.loaiDongDien'(val) {
      this.validateField('loaiDongDien', val)
    },
    'currentItem.hangSxId'(val) {
      this.validateField('hangSxId', val)
    },
    'currentItem.thongSoDongDien'(val) {
      this.validateField('thongSoDongDien', val)
    },
    'currentItem.dienApDongDien'(val) {
      this.validateField('dienApDongDien', val)
    },
    'currentItem.lapDat'(val) {
      this.validateField('lapDat', val)
    },
    'currentItem.csThietKe'(val) {
      this.validateField('csThietKe', val)
    },
    'currentItem.rong'(val) {
      this.validateField('rong', val)
    },
    'currentItem.dai'(val) {
      this.validateField('dai', val)
    },
    'currentItem.cao'(val) {
      this.validateField('cao', val)
    },
    'currentItem.slIecC13'(val) {
      this.validateField('slIecC13', val)
    },
    'currentItem.slIecC19'(val) {
      this.validateField('slIecC19', val)
    },
    'currentItem.slKhac'(val) {
      this.validateField('slKhac', val)
    }
  },

  methods: {
    validateField(field, value) {
      if (!this.enableWatchValidation) return
      const fieldValidationMap = {
        ten: { control: 'txtTenLoaiThanhNguon', type: 'string' },
        loaiDongDien: { control: 'selectLoaiDongDien', type: 'string' },
        hangSxId: { control: 'selectHangSanXuat', type: 'string' },
        thongSoDongDien: { control: 'txtThongSoDongDien', type: 'number' },
        dienApDongDien: { control: 'txtDienApDongDien', type: 'number' },
        lapDat: { control: 'selectLapDat', type: 'select' },
        csThietKe: { control: 'txtCongSuatThietKe', type: 'number' },
        rong: { control: 'txtKichThuocChieuRong', type: 'number' },
        dai: { control: 'txtKichThuocChieuDai', type: 'number' },
        cao: { control: 'txtKichThuocChieuCao', type: 'number' },
        slIecC13: { control: 'txtSoLuongIECC13', type: 'number' },
        slIecC19: { control: 'txtSoLuongIECC19', type: 'number' },
        slKhac: { control: 'txtSoLuongKhac', type: 'number' }
      }

      const config = fieldValidationMap[field]
      if (!config) return

      let isValid = false
      if (config.type === 'string') {
        isValid = value && value.toString().trim() !== ''
      } else if (config.type === 'number') {
        isValid = value && value > 0
      } else if (config.type === 'select') {
        isValid = value !== null && value !== undefined && value !== '' && value !== 0
      }

      if (this.controls[config.control]) {
        this.controls[config.control].invalid = !isValid
      }
    },

    showModal: function(mode, item = null) {
      this.currentMode = mode

      if (mode === 'NEW' || mode === 'EDIT') {
        this.hangSanXuatSelectionList = this.$parent.hangSanXuatSelectionList.filter((h) => h.SU_DUNG === 1)
      } else {
        // VIEW thì hiển thị tất cả
        this.hangSanXuatSelectionList = [...this.$parent.hangSanXuatSelectionList]
      }

      if (mode === 'NEW') {
        this.resetForm()
      } else if (mode === 'EDIT' || mode === 'VIEW') {
        // Mapping API format to camelCase
        const apiMapping = {
          id: 'ID',
          ten: 'TEN',
          hangSxId: 'HANG_SX_ID',
          loaiDongDien: 'LOAI_DONGDIEN',
          thongSoDongDien: 'THONGSO_DONGDIEN',
          dienApDongDien: 'DIENAP_DONGDIEN',
          csThietKe: 'CS_THIETKE',
          lapDat: 'LAP_DAT',
          rong: 'RONG',
          dai: 'DAI',
          cao: 'CAO',
          slIecC13: 'SL_IEC_C13',
          slIecC19: 'SL_IEC_C19',
          slKhac: 'SL_KHAC',
          ghiChu: 'GHI_CHU',
          suDung: 'SUDUNG'
        }

        // Convert API data to component format
        this.currentItem = Object.keys(apiMapping).reduce((acc, key) => {
          const apiKey = apiMapping[key]
          acc[key] = item[apiKey] ?? this.defaultItem[key]
          return acc
        }, {})
      }

      this.$refs.ModalChiTietLoaiThanhNguon.show()
    },

    closeModal: function() {
      this.$refs.ModalChiTietLoaiThanhNguon.hide()
      this.resetForm()
    },

    resetForm: function() {
      this.currentItem = { ...this.defaultItem }
      // Reset validation states
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })
    },

    onSave: async function() {
      if (this.validateForm()) {
        try {
          const confirmFlag = await this.$confirm('Bạn có muốn lưu thông tin này không?', 'Xác nhận', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            type: 'info',
            dangerouslyUseHTMLString: true
          })
            .then((s) => true)
            .catch((e) => false)

          if (confirmFlag) {
            this.$root.showLoading(true)

            const isEdit = this.currentItem.id > 0
            const message = isEdit ? 'Cập nhật loại thanh nguồn thành công.' : 'Thêm mới loại thanh nguồn thành công.'

            const payload = {
              ...this.currentItem,
              id: isEdit ? this.currentItem.id.toString() : ''
            }

            const result = await this.upSertLoaiThanhNguon(payload)
            if (result) {
              this.$root.toastSuccess(message)
              this.closeModal()
              // Emit event to refresh parent data
              this.$emit('onSaved')
            }
          }
        } catch (error) {
          console.error('Error in save:', error)
          this.$root.toastError('Có lỗi khi lưu dữ liệu')
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    validateForm: function() {
      this.enableWatchValidation = true
      let isValid = true

      // Reset all validation states
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })

      // Validation rules configuration
      const validationRules = [
        {
          field: 'ten',
          control: 'txtTenLoaiThanhNguon',
          type: 'string',
          message: 'Tên loại thanh nguồn không được để trống.'
        },
        {
          field: 'loaiDongDien',
          control: 'selectLoaiDongDien',
          type: 'string',
          message: 'Loại dòng điện không được để trống.'
        },
        {
          field: 'hangSxId',
          control: 'selectHangSanXuat',
          type: 'string',
          message: 'Hãng sản xuất không được để trống.'
        },
        {
          field: 'thongSoDongDien',
          control: 'txtThongSoDongDien',
          type: 'number',
          message: 'Thông số dòng điện phải lớn hơn 0.'
        },
        {
          field: 'dienApDongDien',
          control: 'txtDienApDongDien',
          type: 'number',
          message: 'Thông số điện áp phải lớn hơn 0.'
        },
        { field: 'lapDat', control: 'selectLapDat', type: 'select', message: 'Hình thức lắp đặt không được để trống.' },
        {
          field: 'csThietKe',
          control: 'txtCongSuatThietKe',
          type: 'number',
          message: 'Công suất thiết kế phải lớn hơn 0.'
        },
        {
          field: 'rong',
          control: 'txtKichThuocChieuRong',
          type: 'number',
          message: 'Kích thước chiều rộng phải lớn hơn 0.'
        },
        {
          field: 'dai',
          control: 'txtKichThuocChieuDai',
          type: 'number',
          message: 'Kích thước chiều dài phải lớn hơn 0.'
        },
        {
          field: 'cao',
          control: 'txtKichThuocChieuCao',
          type: 'number',
          message: 'Kích thước chiều cao phải lớn hơn 0.'
        },
        {
          field: 'slIecC13',
          control: 'txtSoLuongIECC13',
          type: 'number',
          message: 'Số lượng ổ IEC C13 phải lớn hơn 0.'
        },
        {
          field: 'slIecC19',
          control: 'txtSoLuongIECC19',
          type: 'number',
          message: 'Số lượng ổ IEC C19 phải lớn hơn 0.'
        },
        { field: 'slKhac', control: 'txtSoLuongKhac', type: 'number', message: 'Số lượng ổ khác phải lớn hơn 0.' }
      ]

      // Validate each rule
      validationRules.forEach((rule) => {
        const value = this.currentItem[rule.field]
        let isFieldValid = false

        if (rule.type === 'string') {
          isFieldValid = value && value.toString().trim() !== ''
        } else if (rule.type === 'number') {
          isFieldValid = value && value > 0
        } else if (rule.type === 'select') {
          isFieldValid = value !== null && value !== undefined && value !== ''
        }

        if (!isFieldValid) {
          this.controls[rule.control].invalid = true
          this.$root.toastError(rule.message)
          isValid = false
        }
      })

      return isValid
    },

    upSertLoaiThanhNguon: async function(payload) {
      let result = false
      try {
        let rs = await this.axios.post('/web-ecms/idc/loai-thanh-nguon', payload)
        result = rs.data
      } catch (e) {
        this.handleApiError(e)
      }
      return result
    },

    handleApiError(e) {
      if (e?.data?.message_detail) {
        this.$root.toastError(e.data.message_detail)
      } else {
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>
.popup-box {
  max-height: 90vh !important;
  overflow-y: auto;
}
.is-invalid {
  border: 1px solid red;
  border-radius: 5px;
}
</style>
