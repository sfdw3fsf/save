<template src="./index.template.html"></template>
<script>
import ModalImportExcel from '../components/Modals/ModalImportExcel'
import api from './api'
import { DEFAULT_FORM_ITEM, IMPORT_CONFIG } from './constants'

export default {
  name: 'DanhMucMucDichSuDung',
  components: {
    ModalImportExcel
  },
  data() {
    return {
      dsSelected: [],
      dsMucDichSuDung: [],
      dsLoaiMucDich: [],
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
        txtGhiChu: {
          enabled: false,
          invalid: false
        },
        selectLoaiMucDich: {
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
        return rule
      })
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.dsLoaiMucDich = await this.getDanhSachLoaiMucDich()
      this.dsMucDichSuDung = await this.getDanhSachMucDichSuDung()
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
    'form.ten'(val) {
      this.validateField('ten', val)
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
        ten: { control: 'txtTen', type: 'string' }
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

    // Hàm xử lý dữ liệu Excel cho mục đích sử dụng
    processMucDichSuDungExcelData(dataRows) {
      const nonEmptyRows = dataRows.filter((row) => {
        return row && row.some((cell) => cell !== null && cell !== undefined && cell !== '' && String(cell).trim() !== '')
      })
      
      return nonEmptyRows.map((row) => {
        // Convert và lưu giá trị gốc
        const nhomMucDichId = this.convertNhomMucDichToId(row[1])
        let hieuLuc = row[2] != null ? (typeof row[2] === 'string' ? (row[2].trim().toLowerCase().includes('hiệu lực') || row[2] === '1' ? 1 : 0) : row[2]) : 1
        
        // Thêm field display để filter theo text
        const nhomMucDichText = nhomMucDichId ? (this.dsLoaiMucDich.find((item) => item.ID === nhomMucDichId)?.TEN || '') : ''
        const hieuLucText = hieuLuc == 1 ? 'Hiệu lực' : 'Không hiệu lực'
        
        return {
          ten: row[0] ? String(row[0]).trim() : '',
          nhomMucDichId: nhomMucDichText, // Text để filter
          hieuLuc: hieuLucText, // Text để filter
          ghiChu: row[3] || '',
          // Lưu giá trị gốc để save
          _nhomMucDichId: nhomMucDichId,
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
            `Bạn có chắc muốn import ${result.data.length} mục đích sử dụng từ file Excel?`,
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
          ID: item.ID || '',
          TEN: item.TEN || '',
          LOAI_MUCDICH_TEN: item.LOAI_MUCDICH_TEN || '',
          SUDUNG_TEXT: item.SUDUNG_TEXT,
          GHICHU: item.GHICHU || ''
        }
      })

      const exportConfig = {
        fileName: 'DanhSachMucDichSuDung.xlsx',
        dataSource: exportData
      }

      this.grdItems.excelExport(exportConfig)
      this.$root.toastSuccess('Xuất dữ liệu thành công')
    },

    formatMucDichSuDungCellValue(value, fieldName) {
      return value || ''
    },

    // Helper function để convert nhomMucDichId (có thể là ID hoặc TEN) về ID
    convertNhomMucDichToId(value) {
      if (value === null || value === undefined || value === '') return null
      
      // Nếu là số, thử tìm theo ID trước
      if (typeof value === 'number') {
        const foundById = this.dsLoaiMucDich.find((item) => item.ID === value)
        if (foundById) return foundById.ID
      } else {
        // Kiểm tra xem có phải là số dạng string không
        const trimmedValue = value.toString().trim()
        if (!isNaN(trimmedValue) && trimmedValue !== '') {
          const id = parseInt(trimmedValue)
          const foundById = this.dsLoaiMucDich.find((item) => item.ID === id)
          if (foundById) return foundById.ID
        }
      }
      
      // Tìm theo TEN (so sánh không phân biệt hoa thường)
      const valueStr = value.toString().trim().toLowerCase()
      const foundByName = this.dsLoaiMucDich.find((item) => {
        const itemName = (item.TEN || '').toString().trim().toLowerCase()
        return itemName === valueStr
      })
      
      return foundByName ? foundByName.ID : null
    },

    processImportData: async function(importData) {
      try {
        this.$root.showLoading(true)
        const validItems = (importData || []).map((item) => ({
          id: null,
          ten: String(item.ten).trim() || '',
          nhomMucDichId: item._nhomMucDichId !== undefined ? item._nhomMucDichId : this.convertNhomMucDichToId(item.nhomMucDichId),
          hieuLuc: item._hieuLuc !== undefined ? item._hieuLuc : (item.hieuLuc === 'Hiệu lực' ? 1 : 0),
          ghiChu: item.ghiChu || ''
        }))
        await this.importMucDichSuDungData(validItems)
      } finally {
        this.$root.showLoading(false)
      }
    },

    async handleCheckData(checkDataInfo) {
      try {
        const payload = (checkDataInfo?.data || []).map((row) => ({
          id: row.id || null,
          ten: String(row.ten)?.trim() || '',
          nhomMucDichId: row._nhomMucDichId !== undefined ? row._nhomMucDichId : this.convertNhomMucDichToId(row.nhomMucDichId),
          hieuLuc: row._hieuLuc !== undefined ? row._hieuLuc : (row.hieuLuc === 'Hiệu lực' ? 1 : 0),
          ghiChu: row.ghiChu || ''
        }))

        const isValid = await api.validateMucDichSuDungData(this.$root.context, payload)
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
            let mess = isEdit == 0 ? 'Thêm mới mục đích sử dụng thành công.' : 'Cập nhật mục đích sử dụng thành công.'
            this.$root.showLoading(true)

            // Map form fields to database field names
            let payload = {
              id: isEdit ? this.form.id : null,
              ten: String(this.form.ten)?.trim() || '',
              nhomMucDichId: this.form.nhomMucDichId || null,
              ghiChu: String(this.form.ghiChu)?.trim() || '',
              hieuLuc: this.form.hieuLuc != null ? this.form.hieuLuc : 1
            }
            let result = await this.upSertMucDichSuDung(payload)
            if (result) {
              this.$root.toastSuccess(mess)
              this.enableControls(false)
            } else {
              this.enableControls(false)
            }
          } catch (error) {
            this.$root.toastError(error.message || 'Có lỗi khi lưu thông tin')
            console.error('Error in onSave:', error)
          } finally {
            this.dsMucDichSuDung = await this.getDanhSachMucDichSuDung()
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
          let isSuccess = await this.deleteMucDichSuDung(ids)
          if (isSuccess) {
            this.setCurrentItemDefault()
            this.enableControls(false)
            this.dsSelected = []
            this.buttons.btnDelete.enabled = false
            this.$root.toastSuccess('Xóa thông tin thành công')
          }
        } finally {
          this.dsMucDichSuDung = await this.getDanhSachMucDichSuDung()
          this.$root.showLoading(false)
        }
      }
    },

    setCurrentItem: async function(item) {
      this.form.id = item.ID || 0
      this.form.ten = item.TEN
      this.form.nhomMucDichId = item.LOAI_MUCDICH_ID || null
      this.form.hieuLuc = item.SUDUNG !== undefined && item.SUDUNG !== null ? item.SUDUNG : 1
      this.form.ghiChu = item.GHICHU || ''
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
          field: 'ten',
          control: 'txtTen',
          type: 'string',
          message: 'Mục đích sử dụng không được để trống.'
        },
        {
          field: 'nhomMucDichId',
          control: 'selectLoaiMucDich',
          type: 'required',
          message: 'Đối tượng sử dụng không được để trống.'
        }
      ]

      // Validate each rule
      validationRules.forEach((rule) => {
        const value = this.form[rule.field]
        let isFieldValid = false

        if (rule.type === 'string') {
          isFieldValid = value && value.toString().trim() !== ''

          // Check for duplicate names (excluding current item)
          if (isFieldValid && rule.field === 'ten') {
            const trimmedValue = value
              .toString()
              .trim()
              .replace(/\s+/g, ' ') 
              .toLowerCase()
            const duplicate = this.dsMucDichSuDung.find(
              (item) =>
                item.ID !== this.form.id &&
                item.TEN &&
                item.TEN.toString()
                  .trim()
                  .toLowerCase() === trimmedValue
            )
            if (duplicate) {
              isFieldValid = false
              this.controls[rule.control].invalid = true
              this.$root.toastError('Tên mục đích sử dụng đã tồn tại. Vui lòng nhập tên khác.')
              isValid = false
              return
            }
          }
        }

        if (rule.type === 'required') {
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

    getDanhSachMucDichSuDung: async function() {
      try {
        return await api.getDanhSachMucDichSuDung(this.$root.context)
      } catch (error) {
        this.handleApiError(error)
        return []
      }
    },

    getDanhSachLoaiMucDich: async function() {
      try {
        return await api.getDanhSachLoaiMucDich(this.$root.context)
      } catch (error) {
        this.handleApiError(error)
        return []
      }
    },

    upSertMucDichSuDung: async function(payload) {
      try {
        return await api.upSertMucDichSuDung(this.$root.context, payload)
      } catch (e) {
        this.handleApiError(e)
        return false
      }
    },

    deleteMucDichSuDung: async function(payload) {
      try {
        return await api.deleteMucDichSuDung(this.$root.context, payload)
      } catch (e) {
        this.handleApiError(e)
        return false
      }
    },

    async importMucDichSuDungData(data) {
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
            await api.upSertMucDichSuDung(this.$root.context, item)
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
        this.dsMucDichSuDung = await this.getDanhSachMucDichSuDung()
        if (successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${successCount} mục đích sử dụng`)
        }
        if (errorCount > 0) {
          this.$root.toastError(`Import thất bại ${errorCount} mục đích sử dụng. Vui lòng kiểm tra lại dữ liệu.`)
        }
      }
      return true
    },

    handleApiError(e) {
      console.log('API ERROR: ', e.response.data.message_detail)
      if (e.response.data.message_detail) {
        this.$root.toastError(e.response.data.message_detail)
      } else {
        console.error(e)
      }
    },

    // ====== TEMPLATE ====== //
    getHieuLucText: function(value) {
      return value.toString() === '1' ? 'Hiệu lực' : 'Không hiệu lực'
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
