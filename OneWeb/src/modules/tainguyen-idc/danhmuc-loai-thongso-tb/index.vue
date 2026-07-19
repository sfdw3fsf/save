<template src="./index.template.html"> </template>
<script>
import Vue from 'vue'
import ModalImportExcel from '../components/Modals/ModalImportExcel'
import api from './api'

export default {
  name: 'DanhMucLoaiThongSoTB',
  components: {
    ModalImportExcel
  },
  data() {
    return {
      listLoaiThietBi: [],
      listTrangThaiVanHanh: [],
      dataItems: [],
      selectedRows: [],
      newBtn: true,
      saveBtn: false,
      editBtn: true,
      cancelBtn: false,
      deleteBtn: true,
      searchParams: {
        ten: '',
        idLoaiTB: '',
        suDung: null
      },
      importConfig: {
        title: 'Import dữ liệu loại thông số thiết bị',
        templateFileName: 'Template_Import_LoaiThongSoTB.xlsx',
        gridColumns: [
          { key: 'LOAITBI_ID', label: 'ID Loại thiết bị' },
          { key: 'TEN', label: 'Tên loại thông số thiết bị' },
          { key: 'GIATRI', label: 'Giá trị định danh' },
          { key: 'TRANGTHAI_VH_ID', label: 'Trạng thái vận hành' },
          { key: 'GHICHU', label: 'Ghi chú' },
          { key: 'SUDUNG', label: 'Hiệu lực' }
        ],
        tableColumns: [
          { key: 'LOAITBI_ID', label: 'ID Loại thiết bị (Xem trong danh mục loại thiết bị)' },
          { key: 'TEN', label: 'Tên loại thông số thiết bị' },
          { key: 'GIATRI', label: 'Giá trị định danh' },
          { key: 'TRANGTHAI_VH_ID', label: 'Trạng thái vận hành (1: Hoạt động, 0: Không hoạt động, 2: Cảnh báo lỗi )' },
          { key: 'GHICHU', label: 'Ghi chú' },
          { key: 'SUDUNG', label: 'Hiệu lực (0: Không hiệu lực, 1: Có hiệu lực)' }
        ],
        validationRules: [
          { field: 'LOAITBI_ID', validator: null },
          { field: 'LOAITBI_ID', required: true, message: 'ID Loại thiết bị không được để trống' },
          { field: 'LOAITBI_ID', type: 'number', required: true, message: 'ID Loại thiết bị chỉ nhận số' },
          { field: 'TEN', validator: null },
          { field: 'TEN', required: true, message: 'Tên loại thông số thiết bị không được để trống' },
          { field: 'TEN', maxLength: 200, message: 'Tên loại thông số thiết bị không được vượt quá 200 ký tự' },
          { field: 'GIATRI', required: true, type: 'number', message: 'Giá trị định danh phải là số' },
          { field: 'TRANGTHAI_VH_ID', validator: null },
          { field: 'TRANGTHAI_VH_ID', required: true, message: 'Trạng thái vận hành không được để trống' },
          { field: 'SUDUNG', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' },
          { field: 'GHICHU', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' }
        ],
        checkDataResult: true
      },
      // Lưu trữ dữ liệu import để validator có thể truy cập
      currentImportData: [],
      controls: {
        idLoaiThongSoTB: {
          enabled: false,
          invalid: false
        },
        hieuLuc: {
          enabled: false
        },
        idLoaiTB: {
          enabled: false,
          invalid: false
        },
        giaTriDinhDanh: {
          enabled: false,
          invalid: false
        },
        tenLoaiThongSoTB: {
          enabled: false,
          invalid: false
        },
        trangThaiVanHanhId: {
          enabled: false,
          invalid: false
        },
        ghiChu: {
          enabled: false,
          invalid: false
        }
      },
      currentItem: {
        idLoaiThongSoTB: null,
        idLoaiTB: '',
        tenLoaiThongSoTB: '',
        giaTriDinhDanh: null,
        trangThaiVanHanhId: '',
        ghiChu: '',
        hieuLuc: 1,
        moTa: null,
        tenTat: null
      }
    }
  },
  computed: {
    loaiThietBiIdSet() {
      return new Set(this.listLoaiThietBi.map((item) => item.id?.toString()))
    },
    trangThaiVanHanhIdSet() {
      return new Set(this.listTrangThaiVanHanh.map((item) => item.id?.toString()))
    }
  },
  watch: {},
  mounted: async function() {
    await this.loadLoaiThietBi()
    await this.loadTrangThaiVanHanh()
    await this.loadData()

    // Gán validator thực tế cho rule vừa khai báo trong importConfig
    const idxLoaiTB = this.importConfig.validationRules.findIndex(
      (r) => r.field === 'LOAITBI_ID' && r.validator === null
    )
    if (idxLoaiTB !== -1) {
      this.importConfig.validationRules[idxLoaiTB].validator = this.validateLoaiThietBiExists()
    }

    const idxTrangThai = this.importConfig.validationRules.findIndex(
      (r) => r.field === 'TRANGTHAI_VH_ID' && r.validator === null
    )
    if (idxTrangThai !== -1) {
      this.importConfig.validationRules[idxTrangThai].validator = this.validateTrangThaiVanHanhExists()
    }

    const idxTen = this.importConfig.validationRules.findIndex((r) => r.field === 'TEN' && r.validator === null)
    if (idxTen !== -1) {
      this.importConfig.validationRules[idxTen].validator = this.validateTenLoaiThongSoTBDuplicate()
    }
  },
  methods: {
    fillDataForm: function(data) {
      this.currentItem = {
        idLoaiThongSoTB: data.LOAITSTBI_ID,
        idLoaiTB: data.LOAITBI_ID,
        tenLoaiThongSoTB: data.TEN,
        giaTriDinhDanh: data.GIATRI,
        trangThaiVanHanhId: data.TRANGTHAI_VH_ID,
        ghiChu: data.GHICHU,
        hieuLuc: data.SUDUNG,
        moTa: data.MOTA || null,
        tenTat: data.TENTAT || null
      }
    },

    clearDataForm: function() {
      this.currentItem = {
        idLoaiThongSoTB: null,
        idLoaiTB: '',
        tenLoaiThongSoTB: '',
        giaTriDinhDanh: null,
        trangThaiVanHanhId: '',
        ghiChu: '',
        hieuLuc: 1,
        moTa: null,
        tenTat: null
      }
    },

    validateForm: function() {
      let isValid = true
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })

      if (!this.currentItem.tenLoaiThongSoTB || this.currentItem.tenLoaiThongSoTB.trim() === '') {
        this.controls.tenLoaiThongSoTB.invalid = true
        isValid = false
      }
      if (!this.currentItem.idLoaiTB) {
        this.controls.idLoaiTB.invalid = true
        isValid = false
      }
      if (!this.currentItem.giaTriDinhDanh) {
        this.controls.giaTriDinhDanh.invalid = true
        isValid = false
      }
      if (!this.currentItem.trangThaiVanHanhId) {
        this.controls.trangThaiVanHanhId.invalid = true
        isValid = false
      }

      if (!isValid) {
        this.$toast.error('Vui lòng điền đầy đủ thông tin bắt buộc')
      }
      return isValid
    },

    disableControls: function() {
      for (const key in this.controls) {
        this.controls[key].enabled = false
      }
    },

    enableControls: function() {
      for (const key in this.controls) {
        this.controls[key].enabled = true
      }
      this.controls.idLoaiThongSoTB.enabled = false
    },

    setToDefault: function() {
      this.newBtn = true
      this.saveBtn = false
      this.editBtn = true
      this.cancelBtn = false
      this.deleteBtn = true
      this.disableControls()
    },

    setToNewOrEditMode: function() {
      this.newBtn = false
      this.saveBtn = true
      this.editBtn = false
      this.cancelBtn = true
      this.deleteBtn = false
      this.enableControls()
    },

    loadLoaiThietBi: async function() {
      this.$root.loading(true)
      try {
        const res = await api.getLoaiThietBi(this.axios)
        if (res.data.error == 200) {
          this.listLoaiThietBi = []
          for (const item of res.data.data) {
            this.listLoaiThietBi.push({
              id: item.LOAITBI_ID,
              text: item.LOAI_TBI
            })
          }
        }
      } catch (error) {
        this.$toast.error(error.data?.message_detail || error.message || 'Lỗi khi tải dữ liệu')
      } finally {
        this.$root.loading(false)
      }
    },

    loadTrangThaiVanHanh: async function() {
      const res = await api.getTrangThaiVanHanh(this.axios)
      if (res.data.error == 200) {
        this.listTrangThaiVanHanh = []
        for (const item of res.data.data) {
          this.listTrangThaiVanHanh.push({
            id: item.ID,
            text: item.TEN
          })
        }
      } else {
        this.$toast.error(res.data?.message)
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

    mapToLoaiThietBi: function(field, data, column) {
      if (field === 'LOAITBI_ID') {
        return this.listLoaiThietBi.find((item) => item.id === data.LOAITBI_ID)?.text || ''
      }
      return data[field]
    },

    mapToTrangThaiVanHanh: function(field, data, column) {
      if (field === 'TRANGTHAI_VH_ID') {
        return this.listTrangThaiVanHanh.find((item) => item.id === data.TRANGTHAI_VH_ID)?.text || ''
      }
      return data[field]
    },

    mapToHieuLuc: function(field, data, column) {
      if (field === 'SUDUNG') {
        return data.SUDUNG === 1 ? 'Có hiệu lực' : 'Không hiệu lực'
      }
      return data[field]
    },

    onClickNhapMoi: function() {
      this.clearDataForm()
      this.setToNewOrEditMode()
    },

    onClickSua: function() {
      this.setToNewOrEditMode()
    },

    onClickGhiLai: async function() {
      if (this.validateForm()) {
        this.$root.loading(true)

        try {
          const res = await api.save(this.axios, this.currentItem)
          if (res.data.error == 200) {
            this.$toast.success('Lưu thành công')
            await this.loadData()
            this.setToDefault()
          }
        } catch (error) {
          this.$toast.error(error.data.message_detail || 'Lỗi khi lưu dữ liệu')
        } finally {
          this.$root.loading(false)
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
          const idsToDelete = this.selectedRows.map((row) => row.LOAITSTBI_ID)
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
          this.$toast.error('Lỗi khi xóa dữ liệu')
          console.error(error)
        }
      }
    },

    onClickHuy: function() {
      this.setToDefault()
      this.selectedRows = this.$refs.gridItems.$refs.grid.getSelectedRecords()
      if (this.selectedRows.length > 0) {
        this.fillDataForm(this.selectedRows[0])
      } else {
        this.clearDataForm()
      }
    },

    onClickExport: async function() {
      this.$refs.gridItems.excelExport()
    },

    onClickImport: function() {
      this.btnImportExcel_Click()
    },

    onClickTimKiem: function() {
      this.loadData()
    },

    validateLoaiThietBiExists() {
      return (value) => {
        const isValid = this.loaiThietBiIdSet.has(value.toString())
        return isValid ? '' : `Loại thiết bị "${value}" không tồn tại trong hệ thống`
      }
    },

    validateTrangThaiVanHanhExists() {
      return (value) => {
        const isValid = this.trangThaiVanHanhIdSet.has(value.toString())
        return isValid ? '' : `Trạng thái vận hành "${value}" không tồn tại trong hệ thống`
      }
    },

    validateTenLoaiThongSoTBDuplicate() {
      return (value, rowData) => {
        if (!value || value.trim() === '') return ''
        if (!this.currentImportData || !Array.isArray(this.currentImportData)) return ''

        const trimmedValue = value.trim().toLowerCase()
        const duplicateRows = []

        // Tìm các dòng có tên loại thông số thiết bị trùng lặp
        this.currentImportData.forEach((row, index) => {
          if (row && row.TEN && row.TEN.trim().toLowerCase() === trimmedValue) {
            const currentRowIndex = rowData.stt - 1
            if (index !== currentRowIndex) {
              duplicateRows.push(index + 1) // +1 vì dòng bắt đầu từ 1
            }
          }
        })

        if (duplicateRows.length > 0) {
          return `Tên loại thông số thiết bị "${value}" trùng với dòng ${duplicateRows.join(', ')}`
        }

        return ''
      }
    },

    processLoaiThongSoTBExcelData(dataRows) {
      const processedData = dataRows.map((row, index) => {
        return {
          LOAITBI_ID: row[0] || '',
          TEN: row[1] || '',
          GIATRI: row[2] || '',
          TRANGTHAI_VH_ID: row[3] || '',
          GHICHU: row[4] || '',
          SUDUNG: row[5]
        }
      })

      // Lưu dữ liệu để validator có thể truy cập
      this.currentImportData = processedData
      return processedData
    },

    formatLoaiThongSoTBCellValue(value, fieldName) {
      if (value === null || value === undefined || value === '') return ''
      if (fieldName === 'SUDUNG' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }
      if (fieldName === 'TRANGTHAI_VH_ID') {
        return this.listTrangThaiVanHanh.find((item) => item.id === value)?.text || ''
      }
      return value
    },

    async handleCheckData(checkDataInfo) {
      this.$root.loading(true)
      try {
        const payload = (checkDataInfo?.data || []).map((row) => ({
          ...row,
          LOAITSTBI_ID: null,
          MOTA: null,
          TENTAT: null
        }))
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
                errors: businessLogicErrors,
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

    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()
        if (result.success && result.data && result.data.length > 0) {
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} loại thông số thiết bị từ file Excel?`,
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
            LOAITSTBI_ID: null,
            LOAITBI_ID: item.LOAITBI_ID,
            TEN: item.TEN?.trim() || '',
            GIATRI: item.GIATRI,
            TRANGTHAI_VH_ID: item.TRANGTHAI_VH_ID,
            GHICHU: item.GHICHU || '',
            SUDUNG: item.SUDUNG || 0,
            MOTA: null,
            TENTAT: null
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
.invalidBox {
  border: 1px solid red;
  border-radius: 5px;
}

.e-error .e-input {
  border: 1px solid red !important;
}
</style>
