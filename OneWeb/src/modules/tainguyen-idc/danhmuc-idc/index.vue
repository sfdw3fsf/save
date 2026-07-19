<template src="./index.template.html"> </template>
<script>
import Vue from 'vue'
import ModalImportExcel from '../components/Modals/ModalImportExcel'
import api from './api'
import DataForm from './components/DataForm/DataForm.vue'

export default {
  name: 'DanhMucIDC',
  components: {
    ModalImportExcel,
    DataForm
  },
  data() {
    return {
      listDonViIDC: [],
      listTrangThaiVanHanh: [],
      dataItems: [],
      selectedRows: [],
      newBtn: true,
      saveBtn: false,
      editBtn: true,
      cancelBtn: false,
      deleteBtn: true,
      formMode: 'VIEW', // 'VIEW', 'ADD', 'EDIT'
      currentItemId: null,
      currentItemData: null,
      searchParams: {
        ten: '',
        idDonVi: '',
        suDung: null
      },
      importConfig: {
        title: 'Import dữ liệu IDC',
        templateFileName: 'Template_Import_IDC.xlsx',
        gridColumns: [
          { key: 'ID_DONVI', label: 'ID đơn vị' },
          { key: 'TEN', label: 'Tên IDC' },
          { key: 'TENTAT', label: 'Tên tắt IDC' },
          { key: 'THOIDIEM_VANHANH', label: 'Thời điểm vận hành' },
          { key: 'ID_TRANGTHAI_VH', label: 'Trạng thái vận hành' },
          { key: 'TONG_DIENTICH', label: 'Diện tích tổng thể (m²)' },
          { key: 'DIA_CHI', label: 'Địa chỉ' },
          { key: 'TOADO_X', label: 'Tọa độ X' },
          { key: 'TOADO_Y', label: 'Tọa độ Y' },
          { key: 'GHI_CHU', label: 'Ghi chú' },
          { key: 'SUDUNG', label: 'Hiệu lực' }
        ],
        tableColumns: [
          {
            key: 'ID_DONVI',
            label: 'ID đơn vị (891522: IDC Miền Bắc - 891523: IDC Miền Trung - 891524: IDC Miền Nam)'
          },
          { key: 'TEN', label: 'Tên IDC' },
          { key: 'TENTAT', label: 'Tên tắt IDC' },
          { key: 'THOIDIEM_VANHANH', label: 'Thời điểm vận hành' },
          {
            key: 'ID_TRANGTHAI_VH',
            label:
              'Trạng thái vận hành (10: Hoạt động bình thường, 11: Bảo trì, 12: Cảnh báo, 13: Sự cố, 14: Khôi phục)'
          },
          { key: 'TONG_DIENTICH', label: 'Diện tích tổng thể (m²)' },
          { key: 'DIA_CHI', label: 'Địa chỉ' },
          { key: 'TOADO_X', label: 'Tọa độ X' },
          { key: 'TOADO_Y', label: 'Tọa độ Y' },
          { key: 'GHI_CHU', label: 'Ghi chú' },
          { key: 'SUDUNG', label: 'Hiệu lực (0:Không hiệu lực, 1: Có hiệu lực)' }
        ],
        validationRules: [
          { field: 'ID_DONVI', validator: null },
          { field: 'ID_DONVI', required: true, message: 'ID đơn vị không được để trống' },
          { field: 'ID_DONVI', type: 'number', required: true, message: 'ID đơn vị chỉ nhận số' },
          { field: 'TEN', validator: null },
          { field: 'TEN', required: true, message: 'Tên IDC không được để trống' },
          { field: 'TEN', maxLength: 200, message: 'Tên IDC không được vượt quá 200 ký tự' },
          { field: 'TENTAT', required: true, message: 'Tên tắt IDC không được để trống' },
          { field: 'TENTAT', maxLength: 200, message: 'Tên tắt IDC không được vượt quá 200 ký tự' },
          { field: 'THOIDIEM_VANHANH', validator: null },
          { field: 'THOIDIEM_VANHANH', type: 'date', message: 'Thời điểm vận hành sai định dạng ngày' },
          {
            field: 'ID_TRANGTHAI_VH',
            type: 'number',
            enum: [10, 11, 12, 13, 14],
            message: 'Trạng thái vận hành chỉ nhận số trong khoảng quy định'
          },
          { field: 'TONG_DIENTICH', type: 'number', message: 'Diện tích tổng thể chỉ nhận số' },
          { field: 'TOADO_X', type: 'number', message: 'Tọa độ X chỉ nhận số' },
          { field: 'TOADO_Y', type: 'number', message: 'Tọa độ Y chỉ nhận số' },
          { field: 'DIA_CHI', required: true, message: 'Địa chỉ không được để trống' },
          { field: 'GHI_CHU', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' },
          { field: 'SUDUNG', type: 'number', required: true, enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1' }
        ],
        checkDataResult: true
      },
      // Lưu trữ dữ liệu import để validator có thể truy cập
      currentImportData: []
    }
  },
  computed: {},
  watch: {},
  created() {
    // Không cần controlsController nữa vì đã chuyển sang DataForm component
  },
  mounted: async function() {
    await this.loadDanhMuc()
    await this.loadData()

    const idxDonVi = this.importConfig.validationRules.findIndex((r) => r.field === 'ID_DONVI' && r.validator === null)
    if (idxDonVi !== -1) {
      this.importConfig.validationRules[idxDonVi].validator = this.validateDonViExists()
    }

    const idxThoiDiem = this.importConfig.validationRules.findIndex(
      (r) => r.field === 'THOIDIEM_VANHANH' && r.validator === null
    )
    if (idxThoiDiem !== -1) {
      this.importConfig.validationRules[idxThoiDiem].validator = this.validateDateFormat()
    }

    const idxTen = this.importConfig.validationRules.findIndex((r) => r.field === 'TEN' && r.validator === null)
    if (idxTen !== -1) {
      this.importConfig.validationRules[idxTen].validator = this.validateTenIDCDuplicate()
    }
  },
  methods: {
    formatDate: function(date) {
      if (date instanceof Date) {
        const day = date
          .getDate()
          .toString()
          .padStart(2, '0')
        const month = (date.getMonth() + 1).toString().padStart(2, '0')
        const year = date.getFullYear()
        return `${day}/${month}/${year}`
      }

      return date
    },

    fillDataForm: function(data) {
      this.currentItemData = data
      this.currentItemId = data.ID_IDC || null
    },

    clearDataForm: function() {
      this.currentItemData = null
      this.currentItemId = null
    },

    setToDefault: function() {
      this.newBtn = true
      this.saveBtn = false
      this.editBtn = true
      this.cancelBtn = false
      this.deleteBtn = true
      this.formMode = 'VIEW'
    },

    setToNewOrEditMode: function(mode) {
      this.newBtn = false
      this.saveBtn = true
      this.editBtn = false
      this.cancelBtn = true
      this.deleteBtn = false
      this.formMode = mode || 'EDIT'
    },

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

    loadData: async function() {
      try {
        this.$root.loading(true)
        const res = await api.getList(this.axios)

        if (res.data.error == 200) {
          this.dataItems = res.data.data || []
        } else {
          this.$toast.error('Lỗi khi tải dữ liệu')
        }
      } catch (error) {
        console.log('Lỗi khi tải dữ liệu', error)
      } finally {
        this.$root.loading(false)
      }
    },

    onRowSelected: function(selectedRow) {
      this.fillDataForm(selectedRow.data)
    },

    mapToName: function(field, data, column) {
      if (field === 'DONVI_ID') {
        return this.listDonViIDC.find((item) => item.id === data.DONVI_ID)?.text || ''
      }
      // SP trả về TEN_DV thay vì DONVI_ID, nên trả về trực tiếp
      if (field === 'TEN_DV') {
        return data.TEN_DV || ''
      }
      return data[field]
    },

    mapToHieuLuc: function(field, data, column) {
      if (field === 'SUDUNG') {
        return data.SUDUNG === 1 ? 'Có hiệu lực' : 'Không hiệu lực'
      }
      return data[field]
    },

    mapToTrangThaiVanHanh: function(field, data, column) {
      if (field === 'ID_TRANGTHAI_VH') {
        return this.listTrangThaiVanHanh.find((item) => item.id === data.ID_TRANGTHAI_VH)?.text || ''
      }
      return data[field]
    },

    // ----------------------------------------------------

    onClickNhapMoi: function() {
      this.clearDataForm()
      this.setToNewOrEditMode('ADD')
    },

    onClickSua: function() {
      if (!this.currentItemId && !this.currentItemData) {
        this.$toast.warning('Vui lòng chọn một bản ghi để sửa')
        return
      }
      this.setToNewOrEditMode('EDIT')
    },

    onClickGhiLai: async function() {
      // Gọi method save từ DataForm component
      if (this.$refs.dataForm) {
        await this.$refs.dataForm.saveIDC()
      }
    },

    // Handler khi DataForm emit event saved
    onDataFormSaved: async function(result) {
      if (result.success) {
        const isNewIdc = result.isNew
        let newIdcId = result.data

        // Cập nhật currentItemId nếu là tạo mới
        if (isNewIdc && newIdcId) {
          this.currentItemId = newIdcId
        }

        // Load lại data để có thông tin mới nhất
        await this.loadData()

        // Reset form về chế độ VIEW
        this.setToDefault()

        // Load lại data vào form (cho cả edit và create mới)
        const itemIdToLoad = isNewIdc ? newIdcId : this.currentItemId
        if (itemIdToLoad) {
          const selectedData = this.dataItems.find((item) => item.ID_IDC === itemIdToLoad)
          if (selectedData) {
            this.fillDataForm(selectedData)
          }
        }
      }
    },

    onClickXoa: async function() {
      this.selectedRows = this.$refs.gridItems.$refs.grid.getSelectedRecords()

      if (this.selectedRows.length === 0) {
        this.$toast.warning('Vui lòng chọn ít nhất một bản ghi để xóa')
        return
      }

      const confirmed = await this.$confirm(
        `Bạn có chắc chắn muốn xóa ${this.selectedRows.length} bản ghi đã chọn?`,
        'Xác nhận xóa',
        {
          confirmButtonText: 'Xóa',
          cancelButtonText: 'Hủy',
          type: 'warning'
        }
      )

      if (confirmed) {
        try {
          const idsToDelete = this.selectedRows.map((row) => row.ID_IDC)

          const results = await Promise.all(idsToDelete.map((id) => api.delete(this.axios, id)))

          results.forEach((res, index) => {
            const id = idsToDelete[index]
            if (res.data && res.data.error == 200) {
              this.$toast.success('Xóa thành công id: ' + id)
            } else {
              this.$toast.error(res.data?.message_detail || 'Lỗi khi xóa dữ liệu')
            }
          })

          await this.loadData()
          this.selectedRows = []
        } catch (error) {
          this.$toast.error(error.data?.message_detail || 'Lỗi khi xóa dữ liệu')
          console.error(error)
        }
      }
    },

    onClickHuy: function() {
      this.setToDefault()
      this.selectedRows = this.$refs.gridItems.$refs.grid.getSelectedRecords()
      if (this.selectedRows && this.selectedRows.length > 0) {
        this.fillDataForm(this.selectedRows[0])
      } else {
        // Nếu không có row nào được chọn, clear form
        this.clearDataForm()
      }
    },

    onClickExport: async function() {
      this.$refs.gridItems.excelExport()
    },

    onClickImport: function() {
      this.btnImportExcel_Click()
    },

    validateDonViExists() {
      return (value) => {
        const donViIdSet = new Set(this.listDonViIDC.map((dv) => dv.id?.toString()))
        const isValid = donViIdSet.has(value.toString())
        return isValid ? '' : `Đơn vị "${value}" không tồn tại trong hệ thống`
      }
    },

    validateDateFormat() {
      const isValidDate = (value) => {
        if (typeof value !== 'string') return false
        const regex = /^\d{2}\/\d{2}\/\d{4}$/
        if (!regex.test(value)) return false
        const [ddStr, mmStr, yyyyStr] = value.split('/')
        const dd = parseInt(ddStr, 10)
        const mm = parseInt(mmStr, 10)
        const yyyy = parseInt(yyyyStr, 10)
        if (mm < 1 || mm > 12) return false
        if (dd < 1 || dd > 31) return false
        const daysInMonth = new Date(yyyy, mm, 0).getDate()
        return dd <= daysInMonth
      }
      return (value) => {
        console.log('value thoidiem', value)
        if (value == null || value === '') return ''
        return isValidDate(value) ? '' : 'Thời điểm vận hành phải có định dạng dd/MM/yyyy hợp lệ'
      }
    },

    validateTenIDCDuplicate() {
      return (value, rowData) => {
        if (!value || value.trim() === '') return ''
        if (!this.currentImportData || !Array.isArray(this.currentImportData)) return ''

        const trimmedValue = value.trim().toLowerCase()
        const duplicateRows = []

        // Tìm các dòng có tên IDC trùng lặp
        this.currentImportData.forEach((row, index) => {
          if (row && row.TEN && row.TEN.trim().toLowerCase() === trimmedValue) {
            const currentRowIndex = rowData.stt - 1

            if (index !== currentRowIndex) {
              duplicateRows.push(index + 1) // +1 vì dòng bắt đầu từ 1
            }
          }
        })

        if (duplicateRows.length > 0) {
          return `Tên IDC "${value}" trùng với dòng ${duplicateRows.join(', ')}`
        }

        return ''
      }
    },

    processIDCExcelData(dataRows) {
      const processedData = dataRows.map((row) => {
        // Chuyển đổi số serial Excel thành định dạng dd/MM/yyyy
        let thoiDiem = row[3] || ''
        if (typeof thoiDiem === 'number' && thoiDiem > 0) {
          const excelEpoch = new Date(1900, 0, 1)
          const date = new Date(excelEpoch.getTime() + (thoiDiem - 1) * 24 * 60 * 60 * 1000)
          const day = date
            .getDate()
            .toString()
            .padStart(2, '0')
          const month = (date.getMonth() + 1).toString().padStart(2, '0')
          const year = date.getFullYear()
          thoiDiem = `${day}/${month}/${year}`
        }

        return {
          ID_DONVI: row[0] || '',
          TEN: row[1] || '',
          TENTAT: row[2] || '',
          THOIDIEM_VANHANH: thoiDiem,
          ID_TRANGTHAI_VH: row[4] || null,
          TONG_DIENTICH: row[5] || null,
          DIA_CHI: row[6] || '',
          TOADO_X: row[7] || null,
          TOADO_Y: row[8] || null,
          GHI_CHU: row[9] || '',
          SUDUNG: row[10] || 1
        }
      })

      this.currentImportData = processedData
      return processedData
    },

    formatIDCCellValue(value, fieldName) {
      if (value === null || value === undefined || value === '') return ''
      if (fieldName === 'SUDUNG' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }
      return value
    },

    // Khi click "Kiểm tra" trong modal import
    async handleCheckData(checkDataInfo) {
      this.$root.loading(true)
      try {
        const payload = (checkDataInfo?.data || []).map((row) => ({
          ID: null,
          TEN: row.TEN?.trim() || '',
          TENTAT: row.TENTAT?.trim() || '',
          ID_DONVI: row.ID_DONVI,
          THOIDIEM_VANHANH: row.THOIDIEM_VANHANH,
          ID_TRANGTHAI_VH: row.ID_TRANGTHAI_VH || null,
          TONG_DIENTICH: row.TONG_DIENTICH || null,
          DIA_CHI: row.DIA_CHI || '',
          TOADO_X: row.TOADO_X || null,
          TOADO_Y: row.TOADO_Y || null,
          GHI_CHU: row.GHI_CHU || '',
          SUDUNG: row.SUDUNG || 1,
          MOTA: null
        }))
        console.log('payload', payload)
        const res = await api.validate(this.axios, payload)

        if (res.data.error == 200) {
          this.dbInValidRows = res.data.data.invalidRows
          const dataTypeErrors = res.data.data.dataTypeError || []
          const businessLogicErrors = res.data.data.businessLogicError

          if (
            this.dbInValidRows === 0 &&
            dataTypeErrors.length === 0 &&
            (businessLogicErrors == null || businessLogicErrors.length === 0)
          ) {
            this.$toast.success('File hợp lệ, bạn có thể ghi lại')
            this.importConfig.checkDataResult = false
          } else {
            if (dataTypeErrors.length > 0) {
              dataTypeErrors.forEach((err) => {
                this.$toast.error(err)
              })
            }
            if (businessLogicErrors != null && businessLogicErrors.length != undefined) {
              const errorList = businessLogicErrors.split('\n')
              errorList.forEach((err) => {
                if (err && err.trim() !== '') this.$toast.error(err)
              })

              // emit cho modalImportExcel để hiển thị lỗi lên grid
              this.$refs.modalImportExcel.handleCheckDataResult({
                isValid: false,
                errors: businessLogicErrors
              })
            }
          }
        }
      } catch (error) {
        this.$toast.error(error.data?.message_detail || error.message || 'Lỗi khi kiểm tra file')
      } finally {
        this.$root.loading(false)
      }
    },

    // Khi xác nhận import trong modal
    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()
        if (result.success && result.data && result.data.length > 0) {
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} IDC từ file Excel?`,
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
        this.$toast.error('Có lỗi khi import Excel: ' + error.message)
      }
    },

    processImportData: async function(importData) {
      try {
        this.$root.loading(true)
        const validItems = (importData || []).map((item) => {
          return {
            ID: null,
            TEN: item.TEN?.trim() || '',
            TENTAT: item.TENTAT?.trim() || '',
            ID_DONVI: item.ID_DONVI,
            THOIDIEM_VANHANH: item.THOIDIEM_VANHANH,
            ID_TRANGTHAI_VH: item.ID_TRANGTHAI_VH || null,
            TONG_DIENTICH: item.TONG_DIENTICH || null,
            DIA_CHI: item.DIA_CHI || '',
            TOADO_X: item.TOADO_X || null,
            TOADO_Y: item.TOADO_Y || null,
            GHI_CHU: item.GHI_CHU || '',
            SUDUNG: item.SUDUNG || 1,
            MOTA: null
          }
        })

        const res = await api.import(this.axios, validItems)
        if (res.data.error == 200) {
          const invalidRows = res.data.data.invalidRows
          const validRows = res.data.data.validRows
          this.$toast.success('Đã thêm: ' + validRows + ' bản ghi')
          if (invalidRows > 0) {
            this.$toast.error('Đã bỏ qua: ' + invalidRows + ' bản ghi không thể thêm do lỗi dữ liệu')
          }
          await this.loadData()
        }
      } catch (error) {
        this.$toast.error(error.data?.message_detail || error.message)
      } finally {
        this.$root.loading(false)
      }
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
