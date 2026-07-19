<template src="./index.template.html"></template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import ModalImportExcel from '../components/Modals/ModalImportExcel'

export default {
  name: 'DanhMucThanhPhanThietBi',
  components: {
    ModalImportExcel
  },
  data() {
    return {
      currentId: 0,
      isDisableSave: true,
      isDisableNew: false,
      isDisableUpdate: false,
      isDisableDelete: false,
      dsSelected: [],
      dsThanhPhanThietBi: [],
      isModalVisible: false,
      form: {
        id: 0,
        ten: '',
        ghiChu: '',
        suDung: 1
      },
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
        chkSuDung: {
          enabled: false,
          invalid: false
        }
      },
      importConfig: {
        title: 'Import dữ liệu thành phần thiết bị',
        templateFileName: 'Template_Import_ThanhPhanThietBi.xlsx',
        tableColumns: [
          { key: 'ten', label: 'Tên thành phần' },
          { key: 'ghiChu', label: 'Ghi chú' },
          { key: 'suDung', label: 'Hiệu lực' }
        ],
        validationRules: [
          { field: 'ten', required: true, message: 'Tên thành phần không được để trống' },
          { field: 'ten', maxLength: 100, message: 'Tên thành phần không được vượt quá 100 ký tự' },
          { field: 'ghiChu', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' },
          { field: 'suDung', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' }
        ],
        checkDataResult: true
      },
      enableWatchValidation: false
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.dsThanhPhanThietBi = await this.getDanhSachThanhPhanThietBi()
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

    // Hàm xử lý dữ liệu Excel cho thành phần thiết bị
    processThanhPhanThietBiExcelData(dataRows) {
      return dataRows.map((row, index) => {
        return {
          ten: row[0] || '',
          ghiChu: row[1] || '',
          suDung: row[2]
        }
      })
    },

    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} thành phần thiết bị từ file Excel?`,
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
        ...item,
        SUDUNG: this.getSuDungText(item.SUDUNG)
      }))

      const exportConfig = {
        fileName: 'DanhSachThanhPhanThietBi.xlsx',
        dataSource: exportData
      }

      this.grdItems.excelExport(exportConfig)
      this.$root.toastSuccess('Xuất dữ liệu thành công')
    },

    formatThanhPhanThietBiCellValue(value, fieldName) {
      if (value === null || value === undefined || value === '') return ''

      // Format cho trường hiệu lực
      if (fieldName === 'suDung' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }

      return value
    },

    parseNumber(value) {
      if (value === null || value === undefined || value === '') return null
      const num = parseFloat(value)
      return isNaN(num) ? null : num
    },

    processImportData: async function(importData) {
      try {
        this.$root.showLoading(true)

        const validItems = (importData || []).map((item) => {
          return {
            id: null,
            ten: item.ten ? item.ten.toString().trim() : '',
            ghiChu: item.ghiChu ? item.ghiChu.toString().trim() : '',
            suDung: item.suDung !== undefined && item.suDung !== null ? Number(item.suDung) : 1
          }
        })

        await this.importThanhPhanThietBiData(validItems)
      } finally {
        this.$root.showLoading(false)
      }
    },

    async handleCheckData(checkDataInfo) {
      try {
        const payload = (checkDataInfo?.data || []).map((row) => ({
          ...row,
          id: ''
        }))

        const response = await this.axios.post('/web-ecms/idc/thanhphan-thietbi/validate-multi', payload)
        const isValid = response?.data.data
        if (isValid) {
          this.importConfig.checkDataResult = false
          this.$root.toastSuccess('Dữ liệu hợp lệ!')
        }
      } catch (error) {
        const msgDetail = error?.data?.message_detail || ''
        this.importConfig.checkDataResult = true
        const errorList = msgDetail.split('\n').filter((line) => line.trim() !== '')
        if (errorList.length > 0) {
          this.$root.toastError(`Có ${errorList.length} lỗi trong file import\n${msgDetail}`)
        }
        // emit cho modalImportExcel để hiển thị lỗi lên grid
        this.$refs.modalImportExcel.handleCheckDataResult({
          isValid: false,
          errors: msgDetail,
        })  
      }
    },

    // --- code cũ

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
            let mess =
              isEdit == 0 ? 'Thêm mới thành phần thiết bị thành công.' : 'Cập nhật thành phần thiết bị thành công.'
            this.$root.showLoading(true)

            let payload = {
              ...this.form,
              id: isEdit ? this.form.id.toString() : ''
            }
            let result = await this.upSertThanhPhanThietBi(payload)
            if (result) {
              this.$root.toastSuccess(mess)
              this.enableControls(false)
            }
          } catch (error) {
            console.error('Error in onSave:', error)
          } finally {
            this.dsThanhPhanThietBi = await this.getDanhSachThanhPhanThietBi()
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
          let isSuccess = await this.deleteThanhPhanThietBi(ids)
          if (isSuccess) {
            this.setCurrentItemDefault()
            this.enableControls(false)
            this.dsSelected = []
            this.buttons.btnDelete.enabled = false
            this.$root.toastSuccess('Xóa thông tin thành công')
          }
        } finally {
          this.dsThanhPhanThietBi = await this.getDanhSachThanhPhanThietBi()
          this.$root.showLoading(false)
        }
      }
    },

    setCurrentItem: async function(item) {
      this.form.id = item.ID
      this.form.ten = item.TEN
      this.form.ghiChu = item.GHICHU
      this.form.suDung = item.SUDUNG
    },
    setCurrentItemDefault: function() {
      this.setCurrentItem({
        ID: 0,
        TEN: '',
        GHICHU: '',
        SUDUNG: 1
      })
      this.enableControls(false)
      this.dsSelected = []
      this.buttons.btnDelete.enabled = false
    },
    validateControls: function() {
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
          control: 'txtTen',
          type: 'string',
          message: 'Tên thành phần không được để trống.'
        }
      ]

      // Validate each rule
      validationRules.forEach((rule) => {
        const value = this.form[rule.field]
        let isFieldValid = false

        if (rule.type === 'string') {
          isFieldValid = value && value.toString().trim() !== ''
        }

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

    getDanhSachThanhPhanThietBi: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/idc/thanhphan-thietbi')
        result = rs.data
      } catch (error) {
        if (!(error.response == null || error.response == undefined)) {
          if (!(error.response.data == null || error.response.data == undefined)) {
            this.$root.toastError(error.response.data.message_detail)
          }
        }
      }
      return result
    },

    upSertThanhPhanThietBi: async function(payload) {
      let result = false
      try {
        let rs = await this.axios.post('/web-ecms/idc/thanhphan-thietbi', payload)
        result = rs.data
      } catch (e) {
        this.handleApiError(e)
      }
      return result
    },

    deleteThanhPhanThietBi: async function(payload) {
      let result = 0
      try {
        let rs = await this.axios.delete('/web-ecms/idc/thanhphan-thietbi/delete-multi', {
          data: { ids: payload }
        })
        result = rs.data
      } catch (e) {
        this.handleApiError(e)
      }
      return result
    },

    async importThanhPhanThietBiData(data) {
      if (!data || data.length === 0) {
        this.$root.toastError('Không có dữ liệu hợp lệ để import!')
        return false
      }
      let successCount = 0
      let errorCount = 0
      try {
        const response = await this.axios.post('/web-ecms/idc/thanhphan-thietbi/upsert-multi', data)

        if (response?.data?.data) {
          successCount = response.data.data.successCount || 0
          errorCount = response.data.data.failCount || 0
          if (Array.isArray(response.data.data.errors)) {
            errors = response.data.data.errors
          }
        }
      } catch (err) {
        this.$root.toastError(`Import lỗi`)
        console.error(err)
      } finally {
        this.dsThanhPhanThietBi = await this.getDanhSachThanhPhanThietBi()
        if (successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${successCount} thành phần thiết bị`)
        }
        if (errorCount > 0) {
          this.$root.toastError(`Import thất bại ${errorCount} thành phần thiết bị. Vui lòng kiểm tra lại dữ liệu.`)
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

    handleArrApiErrors(error) {
      let errors = []
      if (error) {
        const msg = error?.data?.message_detail || error?.data?.message || 'Lỗi không xác định'
        errors = [msg]
      }

      return errors
    },

    // ====== TEMPLATE ====== //
    getSuDungText: function(value) {
      return value.toString() === '1' ? 'Hiệu lực' : 'Không hiệu lực'
    },
    getSuDungTemplate: function(parent) {
      return function() {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function() {
                return parent.getSuDungText(this.data.SUDUNG)
              }
            }
          }
        }
      }
    }
  }
}
</script>

<style>
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
