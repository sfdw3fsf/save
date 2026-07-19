<template src="./index.html"></template>
<script>
import { buildController, rules as R, createControls } from './controls'
import ImageSelector from '../../../components/ImageSelector/ImageSelector.vue'
import AttributeGrid from '../AttributeGrid/AttributeGrid.vue'
import api from './api'
import moment from 'moment'

export default {
  name: 'DataForm',
  components: {
    ImageSelector,
    AttributeGrid
  },
  props: {
    parentId: {
      type: Number
    },
    parentType: {
      type: String
    },
    itemId: {
      type: Number
    },
    itemType: {
      type: String
    },
    mode: {
      // 'ADD', 'EDIT', 'VIEW'
      type: String,
      default: 'VIEW'
    },
    itemData: {
      type: Object,
      default: null
    }
  },
  watch: {
    mode(newMode) {
      this.updateControlsByMode(newMode)
    },
    itemData(newData) {
      if (newData) {
        this.fillDataForm(newData)
      } else {
        this.clearDataForm()
      }
    },
    'currentItem.thoiDiemVanHanh'(newValue) {
      // Khi datepicker value thay đổi, kiểm tra nếu bị clear về null sau khi user đã nhập
      if (
        this.thoiDiemVanHanhHasInteraction &&
        newValue === null &&
        this.textThoiDiemVanHanh &&
        this.textThoiDiemVanHanh.trim() !== ''
      ) {
        // Datepicker đã clear về null nhưng text vẫn còn - có nghĩa là input không hợp lệ
        // Chỉ đánh dấu invalid, không hiển thị toast (sẽ hiển thị khi submit)
        this.controls.thoiDiemVanHanh.invalid = true
      }
    }
  },
  data() {
    return {
      listDonViIDC: [],
      listTrangThaiVanHanh: [],
      tempImageFile: null,
      textThoiDiemVanHanh: '',
      thoiDiemVanHanhHasInteraction: false,
      controls: createControls([
        'id',
        'suDung',
        'ten',
        'tenTat',
        'idDonVi',
        'thoiDiemVanHanh',
        'trangThaiVanHanh',
        'diaChi',
        'dienTichTongThe',
        'toaDoX',
        'toaDoY',
        'anhDaiDien',
        'ghiChu'
      ]),
      currentItem: {}
    }
  },
  created() {
    this.controlsController = buildController(this.controls, {
      ten: [R.required()],
      tenTat: [R.required()],
      idDonVi: [R.required()],
      thoiDiemVanHanh: [R.date()],
      diaChi: [R.required()],
      dienTichTongThe: [R.number(), R.min(0)],
      toaDoX: [R.number()],
      toaDoY: [R.number()]
    })

    // Set initial controls state based on mode
    this.updateControlsByMode(this.mode)
    this.clearDataForm()

    if (this.itemData) {
      this.fillDataForm(this.itemData)
    }
  },
  mounted: async function() {
    await this.loadDanhMuc()

    // Load item data nếu có itemId nhưng chưa có itemData
    if (this.itemId && !this.itemData && this.mode !== 'ADD') {
      await this.loadItemData(this.itemId)
    }

    // Setup datepicker event listener
    await this.initFormData()
  },
  methods: {
    saveIDC: async function() {
      if (this.validateForm()) {
        this.$root.loading(true)

        try {
          // upload image
          if (this.tempImageFile) {
            await this.handleImageUploaded()
          }
          const payload = this.formatCurrentItem(this.currentItem)
          // create or update IDC
          const res = await api.save(this.axios, payload)

          // check if is new IDC, save additional attributes if any
          const isNewIdc = !this.currentItem.id
          let newIdcId = null

          if (isNewIdc && res.data && res.data.data) {
            newIdcId = res.data.data

            if (newIdcId) {
              this.currentItem.id = newIdcId

              // Lưu thuộc tính bổ sung nếu có
              if (this.$refs.attributeGrid && this.$refs.attributeGrid.tempDsThuocTinh.length > 0) {
                try {
                  await this.$refs.attributeGrid.saveTempAttributes(newIdcId)
                } catch (attrError) {
                  this.$toast.error('Lưu IDC thành công nhưng có lỗi khi lưu thuộc tính')
                }
              }
            }
          }

          this.$toast.success('Lưu thành công')

          // Emit event để parent biết đã save thành công
          this.$emit('saveSuccessed', {
            success: true,
            data: res.data.data,
            isNew: isNewIdc,
            itemData: { ...this.currentItem }
          })
        } catch (error) {
          this.$toast.error(error.data?.message_detail || error.message || 'Lỗi khi lưu dữ liệu')
          this.$emit('saveSuccessed', {
            success: false,
            error: error
          })
        } finally {
          this.$root.loading(false)
        }
      }
    },

    deleteIDC: async function() {
      const confirmed = await this.$confirm(`Bạn có chắc chắn muốn xóa IDC đã chọn?`, 'Xác nhận xóa', {
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
        type: 'warning'
      })

      if (confirmed) {
        try {
          const res = await api.delete(this.axios, this.itemId)

          if (res && res.data && res.data.error === 200) {
            this.$toast.success('Xóa IDC thành công')
          }
        } catch (error) {
          this.$toast.error(error.data?.message_detail || 'Lỗi khi xóa dữ liệu')
        }
      }
    },

    // ========== load data ==========
    loadDanhMuc: async function() {
      try {
        const [donViRes, trangThaiRes] = await Promise.all([
          api.getDanhMuc(this.axios, 'IDC-DONVI-QLY'),
          api.getDanhMuc(this.axios, 'IDC-TTVH-KHONGGIAN')
        ])

        this.listDonViIDC = donViRes || []
        this.listTrangThaiVanHanh = trangThaiRes || []
      } catch (error) {
        this.$toast.error(error.message || 'Lỗi khi tải danh mục')
      }
    },
    loadItemData: async function(itemId) {
      try {
        const res = await api.getDetail(this.axios, itemId)
        if (res && res.data) {
          this.fillDataForm(res.data.data[0])
        }
      } catch (error) {
        this.$toast.error(error.message || 'Lỗi khi tải dữ liệu')
      }
    },

    // ======== utilities ========
    formatCurrentItem: function(currentItem) {
      console.log('Current item:', currentItem.thoiDiemVanHanh)
      return {
        id: currentItem.id,
        ten: currentItem.ten,
        tenTat: currentItem.tenTat,
        idDonVi: currentItem.idDonVi ? Number(currentItem.idDonVi) : null,
        thoiDiemVanHanh:
          currentItem.thoiDiemVanHanh && moment(currentItem.thoiDiemVanHanh).isValid()
            ? moment(currentItem.thoiDiemVanHanh).format('DD/MM/YYYY')
            : null,
        trangThaiVanHanh: currentItem.trangThaiVanHanh ? Number(currentItem.trangThaiVanHanh) : null,
        diaChi: currentItem.diaChi,
        dienTichTongThe:
          currentItem.dienTichTongThe != null && currentItem.dienTichTongThe !== ''
            ? Number(currentItem.dienTichTongThe)
            : null,
        toaDoX: currentItem.toaDoX != null && currentItem.toaDoX !== '' ? Number(currentItem.toaDoX) : null,
        toaDoY: currentItem.toaDoY != null && currentItem.toaDoY !== '' ? Number(currentItem.toaDoY) : null,
        anhDaiDien: currentItem.anhDaiDien,
        ghiChu: currentItem.ghiChu,
        suDung: currentItem.suDung ? Number(currentItem.suDung) : null
      }
    },
    updateControlsByMode(mode) {
      if (mode === 'ADD' || mode === 'EDIT') {
        this.enableControls()
      } else if (mode === 'VIEW') {
        this.disableControls()
      }
    },
    fillDataForm: function(data) {
      this.currentItem = {
        id: data.ID_IDC || null,
        ten: data.TEN_IDC || '',
        tenTat: data.TENTAT || '',
        idDonVi: data.ID_DV || null,
        thoiDiemVanHanh: data.THOIDIEM_VANHANH ? moment(data.THOIDIEM_VANHANH, 'DD/MM/YYYY').toDate() : null,
        trangThaiVanHanh: data.ID_TRANGTHAI_VH || null,
        diaChi: data.DIA_CHI || '',
        dienTichTongThe: data.TONG_DIENTICH || null,
        toaDoX: data.TOADO_X || null,
        toaDoY: data.TOADO_Y || null,
        anhDaiDien: data.HINHANH || '',
        ghiChu: data.GHI_CHU || '',
        suDung: data.SUDUNG != null ? data.SUDUNG : 1,
        // Read-only fields từ SP
        tongDienTichPhongMay: data.TONG_DIENTICH_PHONGMAY || null,
        tongSoLuongRackThietKe: data.SL_RACK_THIETKE || null,
        tongSoLuongRackLapDat: data.SL_RACK_DALAP || null,
        tongSoLuongRackConLai: data.SL_RACK_CHUALAP || null,
        congSuatDinhDanh: data.TONGCS_TB_DALAPDAT || null
      }

      // Set itemId vào currentItem.id nếu có itemId prop
      if (this.itemId) {
        this.currentItem.id = this.itemId
      }

      // Setup datepicker event listener after data is filled
      this.$nextTick(() => {
        this.initFormData()
      })
    },
    clearDataForm: function() {
      this.currentItem = {
        id: null,
        suDung: 1,
        ten: '',
        tenTat: '',
        idDonVi: this.parentId || null,
        thoiDiemVanHanh: null,
        trangThaiVanHanh: null,
        diaChi: '',
        dienTichTongThe: null,
        toaDoX: null,
        toaDoY: null,
        anhDaiDien: '',
        ghiChu: '',
        // Read-only fields
        tongDienTichPhongMay: null,
        tongSoLuongRackThietKe: null,
        tongSoLuongRackLapDat: null,
        tongSoLuongRackConLai: null,
        congSuatDinhDanh: null
      }
      this.tempImageFile = null
      this.textThoiDiemVanHanh = ''
      this.thoiDiemVanHanhHasInteraction = false

      // Reset thuộc tính trong AttributeGrid
      if (this.$refs.attributeGrid) {
        this.$refs.attributeGrid.dsThuocTinh = []
        this.$refs.attributeGrid.tempDsThuocTinh = []
      }
    },
    validateForm: function() {
      // Validate datepicker trước
      const dateValidation = this.validateThoiDiemVanHanhForSubmit()
      if (!dateValidation.valid) {
        this.$toast.error(dateValidation.error)
        return false
      }

      // Validate các trường khác
      const { isValid } = this.controlsController.validate(this.currentItem)
      if (!isValid) {
        this.$toast.error('Vui lòng kiểm tra lại các trường dữ liệu')
      }
      return isValid
    },

    // ======== handle image selection ========
    handleImageSelected: function(file) {
      this.tempImageFile = file
    },
    handleImageCleared: function() {
      this.currentItem.anhDaiDien = ''
      this.tempImageFile = null
    },
    handleImageUploaded: async function() {
      try {
        const rs = await this.$refs.ImageSelector.uploadImage(this.tempImageFile)
        // this.currentItem.anhDaiDien = rs.message.relativeUrl
        if (rs.status == true) {
          this.currentItem.anhDaiDien = rs.message.relativeUrl
        } else {
          throw new Error(`${rs.message} Vui lòng chọn ảnh có định dạng .jpg, .png, .jpeg.`)
        }
      } catch (error) {
        throw error
      }
    },

    // ======== controls helpers for template ========
    isEnabled: function(field) {
      return !!(this.controls && this.controls[field] && this.controls[field].enabled)
    },
    isInvalid: function(field) {
      return !!(this.controls && this.controls[field] && this.controls[field].invalid)
    },

    disableControls: function() {
      this.controlsController?.setEnabledAll(false)
    },

    enableControls: function() {
      this.controlsController?.setEnabledAll(true)
      this.controls.id.enabled = false
    },
    onClickGhiLai: function() {
      this.saveIDC()
    },
    onClickHuy: function() {
      this.clearDataForm()
    },

    // ======== datepicker validation ========
    async initFormData() {
      await this.$nextTick()
      const inputEl = this.$refs.thoiDiemVanHanhPicker?.ej2Instances?.element
      if (inputEl) {
        inputEl.addEventListener('input', (e) => {
          this.textThoiDiemVanHanh = e.target.value
          this.thoiDiemVanHanhHasInteraction = true
          this.controls.thoiDiemVanHanh.invalid = !this.isValidDate(e.target.value)
        })
      }
    },
    isValidDate: function(textValue) {
      if (!textValue || !textValue.trim()) {
        return this.currentItem.thoiDiemVanHanh === null
      }
      const date = moment(textValue.trim(), 'dd/MM/yyyy', true)
      return date.isValid() && date.year() > 1900 && date.year() < 2099
    },
    validateThoiDiemVanHanhForSubmit: function() {
      const textValue = (this.textThoiDiemVanHanh || '').trim()
      if (!this.thoiDiemVanHanhHasInteraction || !this.controls.thoiDiemVanHanh.invalid || !textValue) {
        return { valid: true }
      }
      const date = moment(textValue, 'dd/MM/yyyy')
      if (
        !textValue ||
        !date.isValid() ||
        date.year() <= 1900 ||
        date.year() >= 2099 
      ) {
        return { valid: false, error: 'Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng DD/MM/YYYY.' }
      }
      return { valid: true }
    }
  }
}
</script>
<style scoped>
.date-picker .e-input-group {
  border-color: 1px solid red !important;
}
.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}
.e-error .e-input {
  border: 1px solid red !important;
}
</style>
