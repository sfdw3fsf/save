<template src="./index.template.html"> </template>
<script>
import ModalImportExcel from '../components/Modals/ModalImportExcel'
import { buildController, rules as R, createControls } from './fieldsControl'
import { importConfig, listOfFields, columnsOfGrid, defaultItem } from './const'
import api from './api'

export default {
  name: 'DanhMucTrangThaiVanHanh',
  components: {
    ModalImportExcel
  },
  data() {
    return {
      mode: 'view', // view | edit | add
      columnsOfGrid: columnsOfGrid,
      importConfig: importConfig,
      dataItems: [],
      searchParams: {
        ten: '',
        sudung: null
      },
      currentItem: defaultItem,
      controls: createControls(listOfFields)
    }
  },
  computed: {
    selectedRows() {
      return this.$refs.gridItems ? this.$refs.gridItems.$refs.grid.getSelectedRecords() : []
    }
  },
  async created() {
    ;(this.controlsController = buildController(this.controls, {
      trangthai_vh: [R.required()],
      sudung: [R.required(), R.number(), R.in([0, 1])]
    })),
      await this.fetchListData()
  },
  methods: {
    async fetchListData() {
      try {
        const res = await api.getDanhSach(this.$root.context)
        this.dataItems = res
      } catch (error) {
        this.$toast.error('Lỗi khi lấy danh sách trạng thái vận hành')
      }
    },
    async upsertData() {
      try {
        const payload = this.preparePayLoadForSave(this.currentItem)
        const res = await api.upsert(this.$root.context, payload)

        await this.fetchListData()
        this.setMode('view')

        this.$toast.success('Lưu trạng thái vận hành thành công')
      } catch (error) {
        this.$toast.error(error.message)
      }
    },
    async deleteData(ttvh_id) {
      try {
        const res = await api.delete(this.$root.context, ttvh_id)
      } catch (error) {
        throw new Error(`Lỗi khi xóa trạng thái vận hành id: ${ttvh_id}`)
      }
    },
    async deleteMultipleData(ids) {
      try {
        const promise = []

        for (const id of ids) {
          promise.push(this.deleteData(id))
        }
        await Promise.all(promise)
        await this.fetchListData()

        this.setMode('view')
        this.$toast.success('Xóa trạng thái vận hành thành công')
      } catch (error) {
        this.$toast.error(error.message)
      }
    },
    preparePayLoadForSave(item) {
      return {
        ttvhId: item.ttvh_id,
        trangThaiVh: item.trangthai_vh,
        suDung: item.sudung,
        ghiChu: item.ghichu
      }
    },

    /**
     * Event handlers
     */

    async onClickSave() {
      if (this.validateControls()) {
        await this.upsertData()
      }
    },
    async onClickDelete() {
      if (this.selectedRows.length === 0) {
        this.$toast.warning('Vui lòng chọn ít nhất một bản ghi để xóa')
        return
      }

      const confirmed = await this.$confirm(
        `Bạn có chắc chắn muốn xóa ${this.selectedRows.length} bản ghi đã chọn?`,
        'Xác nhận xóa',
        { confirmButtonText: 'Xóa', cancelButtonText: 'Hủy', type: 'warning' }
      )
      if (confirmed) {
        const ids = this.selectedRows.map((row) => row.ttvh_id)
        await this.deleteMultipleData(ids)
      }
    },
    onClickImport() {
      this.$refs.modalImportExcel.showModal()
    },
    onClickExport () {
      const excelExportProperties = {
        fileName: 'Export_Danh_muc_trang_thai_van_hanh.xlsx',
        dataSource: this.dataItems,
        enableFilter: true,
      }
      this.$refs.gridItems.excelExport(excelExportProperties)
    },

    /**
     * Helper methods
     */
    validateControls() {
      const { isValid, errors } = this.controlsController.validate(this.currentItem)
      if (!isValid) {
        listOfFields.forEach((field) => {
          if (errors[field]) {
            this.$toast.error(`Trường ${errors[field]}`)
          }
        })
      }
      return isValid
    },
    setMode(mode) {
      if (['view', 'edit', 'add'].includes(mode)) {
        this.mode = mode

        if (mode === 'add') {
          this.controlsController.setEnabledAll(true)
          this.currentItem = { ...defaultItem }
        } else if (mode === 'edit') {
          this.controlsController.setEnabledAll(true)
        } else if (mode === 'view') {
          this.controlsController.setEnabledAll(false)
          // If there is selected row, show its data; otherwise, reset to default item
          if (this.selectedRows.length > 0) {
            this.currentItem = { ...this.selectedRows[0] }
          } else {
            this.currentItem = { ...defaultItem }
          }
        }
      } else {
        console.warn(`Invalid mode: ${mode}`)
      }
    },
    onRowSelected(currentRow) {
      const rowData = currentRow.data || {}
      this.currentItem = { ...rowData }
    },

    /**
     * Import Excel modal handlers
     */

    formatTrangThaiCellValue(value, fieldName) {
      if (value === null || value === undefined || value === '') return ''
      if (fieldName === 'sudung' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value == 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }
      return value
    },

    // Process Excel data rows into structured objects
    processExcelData(dataRows) {
      const processedData = dataRows
        ? dataRows.map((row) => {
            return {
              trangthai_vh: row[0] ? row[0].trim() : '',
              ghichu: row[1] ? row[1].trim() : '',
              sudung: row[2]
            }
          })
        : []
      this.currentImportData = processedData
      return processedData
    },

    // When user click "Kiểm tra" button in import modal
    async handleCheckImportData(checkDataInfo) {
      this.$root.loading(true)
      try {
        const payload = checkDataInfo?.data.map((item) => this.preparePayLoadForSave(item))

        const { numberOfInvalidRows, errors } = await api.validate(this.$root.context, payload)

        // All data valid
        if (numberOfInvalidRows === 0 && (errors == null || errors.length === 0)) {
          this.importConfig.checkDataResult = false
          this.$toast.success('File hợp lệ, bạn có thể ghi lại')
        }
        // Invalid data found
        else if (errors != null && errors.length != undefined) {
          this.$refs.modalImportExcel.handleCheckDataResult({ isValid: false, errors: errors })
          this.$toast.error(`File có ${numberOfInvalidRows} dòng không hợp lệ. Vui lòng kiểm tra lại`)
        }
      } catch (error) {
        this.$toast.error('Lỗi khi kiểm tra dữ liệu từ file')
      } finally {
        this.$root.loading(false)
      }
    },

    // When user click "Ghi lại" button in import modal
    async handleSaveImportData(importData) {
      this.$root.loading(true)

      try {
        const validItems = importData.map((item) => this.preparePayLoadForSave(item))
        const res = await api.import(this.axios, validItems)

        await this.fetchListData()
        this.$toast.success(`Nhập dữ liệu thành công ${res.data} bản ghi`)
      } catch (error) {
        this.$toast.error(error.message)
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
