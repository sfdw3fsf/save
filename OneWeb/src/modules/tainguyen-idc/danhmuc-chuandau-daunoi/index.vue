<template src="./index.template.html"></template>
<script>
import ModalImportExcel from '../components/Modals/ModalImportExcel'
import api from './api'
import { DEFAULT_FORM_ITEM, IMPORT_CONFIG, CHUAN_OPTIONS } from './constants'

export default {
  name: 'DanhMucChuanDauDauNoi',
  components: {
    ModalImportExcel
  },
  data() {
    return {
      dsSelected: [],
      dsChuanDauDauNoi: [],
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
        if (rule.field === 'hieuLuc') {
          return { ...rule, field: '_hieuLuc' }
        }
        if (rule.field === 'chuanId') {
          return { ...rule, field: '_chuanId' }
        }
        return rule
      })
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.dsChuanDauDauNoi = await this.getDanhSachChuanDauDauNoi()
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
    'form.chuanDauNoi'(val) {
      this.validateField('chuanDauNoi', val)
    },
    'form.chuan'(val) {
      this.validateField('chuan', val)
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
        chuanDauNoi: { control: 'txtTen', type: 'string' },
        chuan: { control: 'selectChuan', type: 'number' }
      }

      const config = fieldValidationMap[field]
      if (!config) return

      let isValid = false
      if (config.type === 'string') {
        isValid = value && value.toString().trim() !== ''
      } else if (config.type === 'number') {
        isValid = value !== null && value !== undefined && value !== ''
      }

      if (this.controls[config.control]) {
        this.controls[config.control].invalid = !isValid
      }
    },

    // Hàm xử lý dữ liệu Excel cho chuẩn đầu đấu nối
    processChuanDauDauNoiExcelData(dataRows) {
      const nonEmptyRows = dataRows.filter((row) => {
        return row && row.some((cell) => cell !== null && cell !== undefined && cell !== '' && String(cell).trim() !== '')
      })
      
      return nonEmptyRows.map((row) => {
        // Lưu giá trị gốc từ Excel
        const originalChuan = row[1] !== undefined && row[1] !== null && row[1] !== '' ? String(row[1]).trim() : ''
        const originalHieuLuc = row[2] !== undefined && row[2] !== null && row[2] !== '' ? String(row[2]).trim() : ''
        
        // Convert và lưu giá trị đã xử lý
        const chuanId = this.convertChuanToId(row[1])
        let hieuLuc =
          row[2] !== undefined && row[2] !== null && row[2] !== ''
            ? typeof row[2] === 'string'
              ? row[2]
                  .trim()
                  .toLowerCase()
                  .includes('hiệu lực') || row[2] === '1'
                ? 1
                : 0
              : parseInt(row[2])
            : 1
        
        // Hiển thị: nếu convert thành công thì hiển thị text, nếu không thì hiển thị giá trị gốc
        const chuanText = chuanId ? this.chuanOptions.find((item) => item.ID === chuanId)?.TEN || '' : originalChuan
        const hieuLucText =
          hieuLuc != null ? (hieuLuc == 1 ? 'Hiệu lực' : hieuLuc == 0 ? 'Không hiệu lực' : originalHieuLuc) : originalHieuLuc
        
        return {
          chuanDauNoi: String(row[0]).trim() || '',
          chuanId: chuanText,
          hieuLuc: hieuLucText,
          _chuanId: chuanId,
          _hieuLuc: hieuLuc
        }
      })
    },

    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} chuẩn đầu đấu nối từ file Excel?`,
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
      const exportData = filtered.map((item) => ({
        ID: item.ID || '',
        TEN: item.TEN || '',
        CHUAN_TEN: item.CHUAN_TEN || '',
        SUDUNG_TEXT: item.SUDUNG_TEXT == 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }))

      const exportConfig = {
        fileName: 'DanhSachChuanDauDauNoi.xlsx',
        dataSource: exportData
      }

      this.grdItems.excelExport(exportConfig)
      this.$root.toastSuccess('Xuất dữ liệu thành công')
    },

    formatChuanDauDauNoiCellValue(value, fieldName) {
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
        const validItems = (importData || [])
          .map((item) => {
            const chuanId = item._chuanId !== undefined ? item._chuanId : this.convertChuanToId(item.chuanId)
            return {
              id: null,
              chuanDauNoi: String(item.chuanDauNoi).trim() || '',
              chuanId: chuanId,
              hieuLuc: item._hieuLuc !== undefined ? item._hieuLuc : item.hieuLuc === 'Hiệu lực' ? 1 : 0
            }
          })
          .filter((item) => {
            // Lọc bỏ các item có chuanId null hoặc undefined
            if (item.chuanId === null || item.chuanId === undefined) {
              return false
            }
            return true
          })
        
        if (validItems.length === 0) {
          this.$root.toastError('Không có dữ liệu hợp lệ để import! Tất cả các dòng đều thiếu thông tin chuẩn.')
          return
        }
        
        if (validItems.length < importData.length) {
          const skippedCount = importData.length - validItems.length
          this.$root.toastWarning(`Đã bỏ qua ${skippedCount} dòng không có thông tin chuẩn.`)
        }
        
        await this.importChuanDauDauNoiData(validItems)
      } finally {
        this.$root.showLoading(false)
      }
    },

    async handleCheckData(checkDataInfo) {
      try {
        const payload = (checkDataInfo?.data || [])
          .map((row) => {
            const chuanId = row._chuanId !== undefined ? row._chuanId : this.convertChuanToId(row.chuanId || row.chuan)
            return {
              id: null,
              chuanDauNoi: String(row.chuanDauNoi).trim() || '',
              chuanId: chuanId,
              hieuLuc: row._hieuLuc !== undefined ? row._hieuLuc : row.hieuLuc === 'Hiệu lực' ? 1 : 0
            }
          })
          .filter((row) => {
            // Validate: chuanId không được null
            if (row.chuanId === null || row.chuanId === undefined) {
              return false
            }
            return true
          })

        if (payload.length === 0) {
          this.importConfig.checkDataResult = true
          this.$root.toastError('Tất cả các dòng đều thiếu thông tin chuẩn. Vui lòng kiểm tra lại dữ liệu.')
          this.$refs.modalImportExcel.handleCheckDataResult({
            isValid: false,
            errors: 'Tất cả các dòng đều thiếu thông tin chuẩn. Chuẩn là bắt buộc.'
          })
          return
        }

        const isValid = await api.validateChuanDauDauNoiData(this.$root.context, payload)
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
            let mess = isEdit == 0 ? 'Thêm mới chuẩn đầu đấu nối thành công.' : 'Cập nhật chuẩn đầu đấu nối thành công.'
            this.$root.showLoading(true)

            // Map form fields to backend DTO
            let payload = {
              id: isEdit ? this.form.id : null,
              chuanDauNoi: this.form.chuanDauNoi.trim(),
              chuanId: Number(this.form.chuan) || null,
              hieuLuc: this.form.hieuLuc !== undefined && this.form.hieuLuc !== null ? this.form.hieuLuc : 1
            }
            let result = await this.upSertChuanDauDauNoi(payload)
            if (result) {
              this.$root.toastSuccess(mess)
              this.enableControls(false)
            }
          } catch (error) {
            console.error('Error in onSave:', error)
          } finally {
            this.dsChuanDauDauNoi = await this.getDanhSachChuanDauDauNoi()
            this.$root.showLoading(false)
          }
        }
      }
    },

    onAddNew: async function() {
      this.setCurrentItemDefault()
      this.enableControls(true)
    },
    onEdit: async function(args) {
      this.resetValidation()
      this.enableWatchValidation = false
      this.enableControls(true)
    },
    onCancel: async function(args) {
      let items = this.grdItems.getSelectedRecords()
      let item = null
      if (!(items == null || items.length == 0)) {
        if (this.form.id == 0) {
          item = items[0]
        } else {
          item = items.find((x) => x.ID == this.form.id)
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
      let ids = items.map((x) => x.ID)

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
          let isSuccess = await this.deleteChuanDauDauNoi(ids)
          if (isSuccess) {
            this.setCurrentItemDefault()
            this.enableControls(false)
            this.dsSelected = []
            this.buttons.btnDelete.enabled = false
            this.$root.toastSuccess('Xóa thông tin thành công')
          }
        } finally {
          this.dsChuanDauDauNoi = await this.getDanhSachChuanDauDauNoi()
          this.$root.showLoading(false)
        }
      }
    },

    setCurrentItem: async function(item) {
      // Map backend response to form fields
      this.form.id = item.ID || 0
      this.form.chuanDauNoi = item.TEN || ''
      this.form.chuan = item.CHUAN_ID || item.chuanId || item.CHUAN || null
      this.form.hieuLuc = item.SUDUNG !== undefined && item.SUDUNG !== null ? item.SUDUNG : 1
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
          field: 'chuanDauNoi',
          control: 'txtTen',
          type: 'string',
          message: 'Chuẩn đấu nối không được để trống.'
        },
        {
          field: 'chuan',
          control: 'selectChuan',
          type: 'number',
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
          if (isFieldValid && rule.field === 'chuanDauNoi') {
            const trimmedValue = value.toString().trim().toLowerCase()
            if (this.dsChuanDauDauNoi && Array.isArray(this.dsChuanDauDauNoi)) {
              const duplicate = this.dsChuanDauDauNoi.find(
                (item) =>
                  item.ID !== this.form.id &&
                  item.TEN &&
                  item.TEN.toString().trim().toLowerCase() === trimmedValue
              )
              if (duplicate) {
                isFieldValid = false
                this.controls[rule.control].invalid = true
                this.$root.toastError('Tên chuẩn đấu nối đã tồn tại. Vui lòng nhập tên khác.')
                isValid = false
                return
              }
            }
          }
        } else if (rule.type === 'number') {
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

    // ====== API ====== //

    getDanhSachChuanDauDauNoi: async function() {
      try {
        const result = await api.getDanhSachChuanDauDauNoi(this.$root.context)
        return result || []
      } catch (error) {
        this.handleApiError(error)
        return []
      }
    },

    upSertChuanDauDauNoi: async function(payload) {
      try {
        return await api.upSertChuanDauDauNoi(this.$root.context, payload)
      } catch (e) {
        this.handleApiError(e)
        return false
      }
    },

    deleteChuanDauDauNoi: async function(payload) {
      try {
        return await api.deleteChuanDauDauNoi(this.$root.context, payload)
      } catch (e) {
        this.handleApiError(e)
        return false
      }
    },

    async importChuanDauDauNoiData(data) {
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
            await api.upSertChuanDauDauNoi(this.$root.context, item)
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
        this.dsChuanDauDauNoi = await this.getDanhSachChuanDauDauNoi()
        if (successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${successCount} chuẩn đầu đấu nối`)
        }
        if (errorCount > 0) {
          this.$root.toastError(`Import thất bại ${errorCount} chuẩn đầu đấu nối. Vui lòng kiểm tra lại dữ liệu.`)
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

