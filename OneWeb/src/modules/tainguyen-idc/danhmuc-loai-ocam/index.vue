<template src="./index.template.html"></template>
<script>
import ModalImportExcel from '../components/Modals/ModalImportExcel'
import api from './api'
import { DEFAULT_FORM_ITEM, IMPORT_CONFIG, CHUAN_OPTIONS } from './constants'

export default {
  name: 'DanhMucLoaiOcam',
  components: {
    ModalImportExcel
  },
  data() {
    return {
      dsSelected: [],
      dsLoaiOcam: [],
      chuanOptions: CHUAN_OPTIONS,
      form: { ...DEFAULT_FORM_ITEM },
      buttons: {
        btnSearch: {
          enabled: true
        },
        btnAddNew: {
          enabled: true
        },
        btnSave: {
          enabled: false
        },
        btnCancel: {
          enabled: false
        },
        btnEdit: {
          enabled: false
        },
        btnDelete: {
          enabled: false
        }
      },
      controls: {
        txtID: {
          enabled: false,
          invalid: false
        },
        txtTen: {
          enabled: false,
          invalid: false
        },
        selectChuan: {
          enabled: false,
          invalid: false
        },
        chkHieuLuc: {
          enabled: false,
          invalid: false
        }
      },
      importConfig: IMPORT_CONFIG,
      enableWatchValidation: false
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    },
    adjustedValidationRules: function() {
      return this.importConfig.validationRules.map((rule) => {
        if (rule.field === 'sudung') {
          return { ...rule, field: '_sudung' }
        }
        return rule
      })
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.dsLoaiOcam = await this.getDanhSachLoaiOcam()
    } catch (error) {
      console.error('Error in created:', error)
      this.$toast.error('Có lỗi khi khởi tạo dữ liệu')
    } finally {
      this.$root.showLoading(false)
    }
  },
  watch: {
    'form.id': function(value) {
      this.buttons.btnEdit.enabled = value > 0
      // Chỉ enable nút xóa khi có item được chọn và không ở chế độ edit
      this.buttons.btnDelete.enabled = value > 0 && !this.buttons.btnSave.enabled
    },
    'form.loaiOcam'(val) {
      this.validateField('loaiOcam', val)
    },
    dsSelected: function(value) {
      if (!this.buttons.btnSave.enabled) {
        this.buttons.btnDelete.enabled = value && value.length > 0
      }
    }
  },

  methods: {
    validateField(field, value) {
      if (!this.enableWatchValidation) return
      const fieldValidationMap = {
        loaiOcam: { control: 'txtTen', type: 'string' }
      }

      const config = fieldValidationMap[field]
      if (!config) return

      let isValid = false
      if (config.type === 'string') {
        isValid = value && value.toString().trim() !== ''
      }

      if (this.controls[config.control]) {
        this.controls[config.control].invalid = !isValid
      }
    },

    // Hàm xử lý dữ liệu Excel cho loại ổ cắm
    processLoaiOcamExcelData(dataRows) {
      const nonEmptyRows = dataRows.filter((row) => {
        return (
          row && row.some((cell) => cell !== null && cell !== undefined && cell !== '' && String(cell).trim() !== '')
        )
      })

      return nonEmptyRows.map((row) => {
        // Lưu giá trị gốc từ Excel
        const originalChuan = row[1] !== undefined && row[1] !== null && row[1] !== '' ? String(row[1]).trim() : ''
        const originalSudung = row[2] !== undefined && row[2] !== null && row[2] !== '' ? String(row[2]).trim() : ''

        // Convert và lưu giá trị đã xử lý
        const chuanId = this.convertChuanToId(row[1])
        
        // Xử lý sudung: nếu giá trị không hợp lệ thì trả về giá trị gốc
        let sudung = null
        let sudungText = originalSudung
        
        if (row[2] !== undefined && row[2] !== null && row[2] !== '') {
          const sudungValue = row[2]
          
          // Kiểm tra nếu là số
          if (typeof sudungValue === 'number') {
            if (sudungValue === 0 || sudungValue === 1) {
              sudung = sudungValue
              sudungText = sudung === 1 ? 'Hiệu lực' : 'Không hiệu lực'
            } else {
              // Giá trị không hợp lệ, giữ nguyên giá trị gốc
              sudung = originalSudung
            }
          } else {
            // Kiểm tra nếu là string có thể convert thành số
            const numValue = parseInt(sudungValue)
            if (!isNaN(numValue) && (numValue === 0 || numValue === 1)) {
              sudung = numValue
              sudungText = sudung === 1 ? 'Hiệu lực' : 'Không hiệu lực'
            } else {
              // Kiểm tra nếu là text "hiệu lực" hoặc "không hiệu lực"
              const lowerValue = String(sudungValue).trim().toLowerCase()
              if (lowerValue.includes('hiệu lực') || lowerValue === '0') {
                sudung = 0
                sudungText = 'Không hiệu lực'
              } else if (lowerValue.includes('không hiệu lực') || lowerValue === '1') {
                sudung = 1
                sudungText = 'Hiệu lực'
              } else {
                // Giá trị không hợp lệ, giữ nguyên giá trị gốc
                sudung = originalSudung
              }
            }
          }
        } else {
          // Giá trị rỗng, mặc định là 1
          sudung = 1
          sudungText = 'Hiệu lực'
        }

        // Hiển thị: nếu convert thành công thì hiển thị text, nếu không thì hiển thị giá trị gốc
        const chuanText = chuanId ? this.chuanOptions.find((item) => item.ID === chuanId)?.TEN || '' : originalChuan

        return {
          ten: row[0] || '',
          chuanId: chuanText,
          sudung: sudungText,
          _chuanId: chuanId,
          _sudung: sudung
        }
      })
    },

    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} loại ổ cắm từ file Excel?`,
            'Xác nhận import',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
              type: 'warning',
              dangerouslyUseHTMLString: true
            }
          )
            .then((s) => true)
            .catch((e) => false)

          if (confirmFlag) {
            await this.processImportData(result.data)
          }
        }
      } catch (error) {
        console.error('Lỗi khi import Excel:', error)
        this.$root.toastError('Có lỗi khi import Excel: ' + error.message)
      }
    },

    btnExportExcel_Click() {
      const filtered = this.$refs.grdItems.grid.ej2Instances.currentViewData
      const exportData = filtered.map((item) => {
        return {
          LOAIOCAM_ID: item.LOAIOCAM_ID || '',
          TEN: item.TEN || '',
          CHUAN_TEN: item.CHUAN_TEN || '',
          SUDUNG_TEXT: item.SUDUNG_TEXT
        }
      })

      const exportConfig = {
        fileName: 'DanhSachLoaiOcam.xlsx',
        dataSource: exportData
      }

      this.grdItems.excelExport(exportConfig)
      this.$root.toastSuccess('Xuất dữ liệu thành công')
    },

    formatLoaiOcamCellValue(value, fieldName) {
      return value || ''
    },

    // Helper function để convert chuanId (có thể là ID hoặc TEN) về ID
    convertChuanToId(value) {
      if (value === null || value === undefined || value === '') return null

      // Nếu là số, thử tìm theo ID trước
      if (typeof value === 'number' || (!isNaN(value) && value.toString().trim() !== '')) {
        const id = typeof value === 'number' ? value : parseInt(value)
        const foundById = this.chuanOptions.find((item) => item.ID === id)
        if (foundById) return foundById.ID
      }

      // Tìm theo TEN (so sánh không phân biệt hoa thường)
      const valueStr = value
        .toString()
        .trim()
        .toLowerCase()
      const foundByName = this.chuanOptions.find((item) => {
        const itemName = (item.TEN || '')
          .toString()
          .trim()
          .toLowerCase()
        return itemName === valueStr
      })

      return foundByName ? foundByName.ID : null
    },

    processImportData: async function(importData) {
      try {
        this.$root.showLoading(true)
        const validItems = (importData || []).map((item) => ({
          loaiocamId: null,
          ten: String(item.ten).trim() || '',
          chuanId: item._chuanId !== undefined ? item._chuanId : this.convertChuanToId(item.chuanId),
          sudung: item._sudung !== undefined ? item._sudung : item.sudung === 'Hiệu lực' ? 1 : 0
        }))
        await this.importLoaiOcamData(validItems)
      } finally {
        this.$root.showLoading(false)
      }
    },

    async handleCheckData(checkDataInfo) {
      try {
        const payload = (checkDataInfo?.data || []).map((row) => ({
          loaiocamId: null,
          ten: row.ten || row.loaiOcam || '',
          chuanId: row._chuanId !== undefined ? row._chuanId : this.convertChuanToId(row.chuanId || row.chuan),
          sudung: row._sudung !== undefined ? row._sudung : row.sudung === 'Hiệu lực' ? 1 : 0
        }))

        const isValid = await api.validateLoaiOcamData(this.$root.context, payload)
        if (isValid) {
          this.importConfig.checkDataResult = false
          this.$root.toastSuccess('Dữ liệu hợp lệ!')
        }
      } catch (error) {
        const msgDetail = error?.response?.data?.message_detail || error?.data?.message_detail || ''
        this.importConfig.checkDataResult = true
        const errorList = msgDetail.split('\n').filter((line) => line.trim() !== '')
        if (errorList.length > 0) {
          this.$root.toastError(`Có ${errorList.length} lỗi trong file import\n${msgDetail}`)
        }
        this.$refs.modalImportExcel.handleCheckDataResult({
          isValid: false,
          errors: msgDetail
        })
      }
    },

    enableControls: function(args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args
        }
      }

      this.buttons.btnSave.enabled = args
      this.buttons.btnAddNew.enabled = !args
      this.buttons.btnSearch.enabled = !args
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled
      this.buttons.btnEdit.enabled = !args
      if (!args) {
        this.buttons.btnDelete.enabled = this.dsSelected && this.dsSelected.length > 0
      } else {
        this.buttons.btnDelete.enabled = false
      }

      if (this.form.id > 0) this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
    },

    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
      if (!this.buttons.btnSave.enabled) {
        this.buttons.btnDelete.enabled = itemsCheck && itemsCheck.length > 0
      }
    },

    onSave: async function() {
      if (this.validateControls()) {
        let flag = await this.$confirm('Bạn có muốn lưu thông tin này không ?', 'Xác nhận', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info',
          dangerouslyUseHTMLString: true
        })
          .then((s) => true)
          .catch((e) => {
            return false
          })
        if (flag) {
          try {
            const isEdit = this.form.id
            let mess = isEdit == 0 ? 'Thêm mới loại ổ cắm thành công.' : 'Cập nhật loại ổ cắm thành công.'
            this.$root.showLoading(true)

            // Map form fields to backend DTO
            let payload = {
              loaiocamId: isEdit ? this.form.id : null,
              ten: this.form.loaiOcam.trim(),
              chuanId: this.form.chuan || null,
              sudung: this.form.hieuLuc !== undefined && this.form.hieuLuc !== null ? this.form.hieuLuc : 1
            }
            let result = await this.upSertLoaiOcam(payload)
            if (result) {
              this.$root.toastSuccess(mess)
              this.enableControls(false)
            }
          } catch (error) {
            console.error('Error in onSave:', error)
          } finally {
            this.dsLoaiOcam = await this.getDanhSachLoaiOcam()
            this.$root.showLoading(false)
          }
        }
      }
    },

    onAddNew: async function() {
      this.resetValidation()
      this.enableWatchValidation = false
      this.setCurrentItemDefault()
      this.enableControls(true)
    },
    onEdit: async function(args) {
      this.resetValidation()
      this.enableWatchValidation = false
      this.enableControls(true)
    },
    onCancel: async function(args) {
      this.resetValidation()
      this.enableWatchValidation = false
      let items = this.grdItems.getSelectedRecords()
      let item = null
      if (!(items == null || items.length == 0)) {
        if (this.form.id == 0) {
          item = items[0]
        } else {
          item = items.find((x) => {
            const itemId = x.LOAIOCAM_ID || x.ID || x.loaiocamId || 0
            return itemId == this.form.id
          })
        }
      }
      if (item != null) {
        this.setCurrentItem(item)
      } else {
        this.setCurrentItemDefault()
      }
      this.enableControls(false)
      if (this.dsSelected && this.dsSelected.length > 0) {
        this.buttons.btnDelete.enabled = true
      }
    },
    onDelete: async function(args) {
      let items = this.grdItems.getSelectedRecords()
      let ids = items.map((x) => x.LOAIOCAM_ID || x.ID || x.loaiocamId || 0).filter((id) => id > 0)

      let msg = `Bạn có muốn xóa ${items.length} mẫu tin đã chọn không?`
      let flag = await this.$confirm(msg, 'Xác nhận', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.$root.showLoading(true)
          let isSuccess = await this.deleteLoaiOcam(ids)
          if (isSuccess) {
            this.setCurrentItemDefault()
            this.enableControls(false)
            this.dsSelected = []
            this.buttons.btnDelete.enabled = false
            this.$root.toastSuccess('Xóa thông tin thành công')
          }
        } finally {
          this.dsLoaiOcam = await this.getDanhSachLoaiOcam()
          this.$root.showLoading(false)
        }
      }
    },

    setCurrentItem: async function(item) {
      // Map backend response to form fields
      this.form.id = item.LOAIOCAM_ID || item.ID || item.loaiocamId || 0
      this.form.loaiOcam = item.TEN || item.ten || ''
      this.form.chuan = item.CHUAN_ID || item.chuanId || item.CHUAN || null
      this.form.hieuLuc =
        item.SUDUNG !== undefined && item.SUDUNG !== null
          ? item.SUDUNG
          : item.sudung !== undefined && item.sudung !== null
          ? item.sudung
          : item.HIEU_LUC !== undefined && item.HIEU_LUC !== null
          ? item.HIEU_LUC
          : 1
    },
    setCurrentItemDefault: function() {
      this.form = { ...DEFAULT_FORM_ITEM }
      this.resetValidation()
      this.enableWatchValidation = false
      this.enableControls(false)
      this.dsSelected = []
      this.buttons.btnDelete.enabled = false
    },
    resetValidation: function() {
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })
    },
    validateControls: function() {
      this.enableWatchValidation = true
      let isValid = true

      // Reset all validation states
      this.resetValidation()

      // Validation rules configuration
      const validationRules = [
        {
          field: 'loaiOcam',
          control: 'txtTen',
          type: 'string',
          message: 'Loại ổ cắm không được để trống.'
        },
        {
          field: 'chuan',
          control: 'selectChuan',
          type: 'required',
          message: 'Chuẩn không được để trống.'
        }
      ]

      // Validate each rule
      validationRules.forEach((rule) => {
        const value = this.form[rule.field]
        let isFieldValid = false

        if (rule.type === 'string') {
          isFieldValid = value && value.toString().trim() !== ''

          // Check for duplicate names (excluding current item)
          if (isFieldValid && rule.field === 'loaiOcam') {
            const trimmedValue = value
              .toString()
              .trim()
              .toLowerCase()
            if (this.dsLoaiOcam && Array.isArray(this.dsLoaiOcam)) {
              const duplicate = this.dsLoaiOcam.find((item) => {
                const itemId = item.LOAIOCAM_ID || item.ID || item.loaiocamId || 0
                const itemTen = item.TEN || item.ten || item.LOAI_OCAM || ''
                return (
                  itemId !== this.form.id &&
                  itemTen &&
                  itemTen
                    .toString()
                    .trim()
                    .toLowerCase() === trimmedValue
                )
              })
              if (duplicate) {
                isFieldValid = false
                this.controls[rule.control].invalid = true
                this.$root.toastError('Tên loại ổ cắm đã tồn tại. Vui lòng nhập tên khác.')
                isValid = false
                return
              }
            }
          }
        } else if (rule.type === 'required') {
          isFieldValid = value !== null && value !== undefined && value !== ''
        }

        // Handle validation error
        if (!isFieldValid) {
          this.controls[rule.control].invalid = true
          this.$root.toastError(rule.message)
          isValid = false
        }
      })

      return isValid
    },
    grdItems_RowSelected: async function(args) {
      // Chỉ cho phép thay đổi current item khi không ở chế độ edit
      if (this.buttons.btnSave.enabled) {
        return // Không cho phép thay đổi khi đang edit
      }

      try {
        this.$root.showLoading(true)
        this.setCurrentItem(args.data)
      } finally {
        this.$root.showLoading(false)
      }
    },

    grdItems_RowDeselected: async function(args) {
      if (args) {
        this.form = { ...DEFAULT_FORM_ITEM }
      }
    },

    // ====== API ====== //

    getDanhSachLoaiOcam: async function() {
      try {
        const result = await api.getDanhSachLoaiOcam(this.$root.context)
        return result || []
      } catch (error) {
        this.handleApiError(error)
        return []
      }
    },

    upSertLoaiOcam: async function(payload) {
      try {
        return await api.upSertLoaiOcam(this.$root.context, payload)
      } catch (e) {
        this.handleApiError(e)
        return false
      }
    },

    deleteLoaiOcam: async function(payload) {
      try {
        return await api.deleteLoaiOcam(this.$root.context, payload)
      } catch (e) {
        this.handleApiError(e)
        return false
      }
    },

    async importLoaiOcamData(data) {
      if (!data || data.length === 0) {
        this.$root.toastError('Không có dữ liệu hợp lệ để import!')
        return false
      }
      let successCount = 0
      let errorCount = 0
      try {
        // Gọi upsert từng item vì backend không có endpoint upsert-multi
        for (const item of data) {
          try {
            await api.upSertLoaiOcam(this.$root.context, item)
            successCount++
          } catch (err) {
            errorCount++
            console.error('Error importing item:', item, err)
          }
        }
      } catch (err) {
        this.$root.toastError(`Import lỗi`)
        console.error(err)
      } finally {
        this.dsLoaiOcam = await this.getDanhSachLoaiOcam()
        if (successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${successCount} loại ổ cắm`)
        }
        if (errorCount > 0) {
          this.$root.toastError(`Import thất bại ${errorCount} loại ổ cắm. Vui lòng kiểm tra lại dữ liệu.`)
        }
      }
      return true
    },

    handleApiError(e) {
      if (e?.data?.message_detail) {
        this.$root.toastError(e.data.message_detail)
      } else {
        console.error(e)
      }
    },

    // ====== TEMPLATE ====== //
    getSuDungText: function(value) {
      return value.toString() === '0' ? 'Hiệu lực' : 'Không hiệu lực'
    }
  }
}
</script>

<style scoped>
.w200 {
  width: 200px !important;
}
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}
.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}
</style>
