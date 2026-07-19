<template src="./index.html"></template>

<script>
import { addDonVi, addMultipleDonVi, checkValidateExcel, deleteDonVi, DonViApi, getAllDonVi, updateDonVi } from './api'
import ModalImportExcel from '../../tainguyen-idc/components/Modals/ModalImportExcel'
import * as XLSX from 'xlsx'

const HieuLucEnum = {
  HIEU_LUC: 1,
  KHONG_HIEU_LUC: 0
}

export default {
  name: 'DonViSH',

  components: {
    ModalImportExcel
  },

  mounted: async function() {
    await this.loadDonViData()
  },

  data() {
    return {
      // Button, FormField status states
      canAdd: true,
      canEdit: true,
      canCancel: false,
      canDelete: true,
      canImportExcel: true,
      canExportExcel: true,
      canSave: false,
      canEditField: false,

      touchedFields: {
        ten: false
      },

      // Current selected item
      currentItem: {
        id: null,
        ten: '',
        tenTat: '',
        mota: '',
        ghiChu: '',
        suDung: 1
      },

      DonViList: [],
      selectedRows: [],

      importConfig: {
        title: 'Import dữ liệu Đơn Vị',
        templateFileName: 'Template_Import_DonVi.xlsx',
        tableColumns: [
          { key: 'ten', label: 'Tên Đơn Vị' },
          { key: 'tenTat', label: 'Tên Tắt' },
          { key: 'moTa', label: 'Mô Tả' },
          { key: 'ghiChu', label: 'Ghi Chú' },
          { key: 'suDung', label: 'Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)' }
        ],
        gridColumns: [
          { key: 'ten', label: 'Tên Đơn Vị' },
          { key: 'tenTat', label: 'Tên Tắt' },
          { key: 'moTa', label: 'Mô Tả' },
          { key: 'ghiChu', label: 'Ghi Chú' },
          { key: 'suDung', label: 'Hiệu lực' }
        ],
        validationRules: [
          { field: 'ten', required: true, message: 'Tên Đơn Vị không được để trống' },
          { field: 'ten', maxLength: 100, message: 'Tên Đơn Vị không được vượt quá 100 ký tự' },
          { field: 'tenTat', maxLength: 50, message: 'Tên tắt không được vượt quá 50 ký tự' },
          { field: 'moTa', maxLength: 255, message: 'Mô tả không được vượt quá 255 ký tự' },
          { field: 'ghiChu', maxLength: 255, message: 'Ghi chú không được vượt quá 255 ký tự' },
          {
            field: 'suDung',
            type: 'number',
            enum: [HieuLucEnum.HIEU_LUC, HieuLucEnum.KHONG_HIEU_LUC],
            message: 'Hiệu lực chỉ nhận 0 hoặc 1'
          }
        ],
        checkDataResult: true
      }
    }
  },

  methods: {
    loadDonViData: async function() {
      try {
        this.$root.loading(true)
        const response = await getAllDonVi()

        if (response.data.error == 200) {
          this.DonViList = response.data.data || []
        }
      } catch (error) {
        this.$toast.error('Lỗi khi tải dữ liệu')
      } finally {
        this.$root.loading(false)
      }
    },

    // BUTTON HANDLERS
    onAdd() {
      this.canAdd = false
      this.canEdit = false
      this.canSave = true
      this.canCancel = true
      this.canEditField = true
      this.touchedFields.ten = false
      this.clearDataForm()
    },

    async onAddSubmit() {
      try {
        const payload = {
          donViSH_id: 0,
          ten: this.currentItem.ten,
          tenTat: this.currentItem.tenTat,
          moTa: this.currentItem.moTa,
          ghiChu: this.currentItem.ghiChu,
          suDung: this.currentItem.suDung
        }

        const response = await addDonVi(payload)
        if (response.data.error == '200' || response.data.error == 200) {
          this.$toast.success('Thêm đơn vị thành công!')
          await this.loadDonViData()
          this.resetButtonsStatus()
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail)
      }
    },

    async onUpdate() {
      try {
        this.$root.loading(true)

        const payload = {
          donViSH_id: this.currentItem.id,
          ten: this.currentItem.ten,
          tenTat: this.currentItem.tenTat,
          moTa: this.currentItem.moTa,
          ghiChu: this.currentItem.ghiChu,
          suDung: this.currentItem.suDung
        }

        const response = await updateDonVi(payload)

        if (response.data.error === '200') {
          this.$toast.success('Cập nhật đơn vị thành công!')
          await this.loadDonViData()
          this.resetButtonsStatus()
        } else {
          this.$toast.error(response.data.message || 'Cập nhật thất bại')
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail)
      } finally {
        this.$root.loading(false)
      }
    },

    clearDataForm() {
      this.currentItem = {
        id: null,
        ten: '',
        tenTat: '',
        moTa: '',
        ghiChu: '',
        suDung: 1
      }
    },

    fillDataForm(data) {
      this.currentItem = {
        id: data.DONVISH_ID,
        ten: data.TEN,
        tenTat: data.TENTAT,
        moTa: data.MOTA,
        ghiChu: data.GHICHU,
        suDung: data.SUDUNG
      }
    },

    onSave() {
      if (this.currentItem.id === null) {
        this.onAddSubmit()
      } else {
        this.onUpdate()
      }
    },

    onCancel() {
      this.resetButtonsStatus()
      this.canEditField = false

      if (this.selectedRows && this.selectedRows.length > 0) {
        this.fillDataForm(this.selectedRows[0])
      } else {
        this.clearDataForm()
        this.canEdit = false
      }
    },

    onEdit() {
      this.canEdit = false
      this.canCancel = true
      this.canEditField = true
      this.canSave = true
      this.canAdd = false
      this.canDelete = false
    },

    async onDelete() {
      const confirmed = await this.$confirm(
        `Bạn có chắc chắn muốn xóa ${this.selectedRows.length} bản ghi đã chọn?`,
        'Xác nhận xóa',
        { confirmButtonText: 'Xóa', cancelButtonText: 'Hủy', type: 'warning' }
      )

      try {
        this.$root.loading(true)
        const idsToDelete =
          this.selectedRows.length > 0 ? this.selectedRows.map((item) => item.DONVISH_ID) : [this.currentItem.id]

        let successCount = 0
        let failCount = 0

        for (const id of idsToDelete) {
          try {
            const response = await deleteDonVi(id)

            if (response.data && (response.data.error === '200' || response.data.error === 200)) {
              successCount++
            } else {
              failCount++
            }
          } catch (err) {
            failCount++
          }
        }

        // Toast
        if (successCount > 0 && failCount === 0) {
          this.$toast.success(`Đã xóa thành công ${successCount} đơn vị!`)
        } else if (successCount > 0 && failCount > 0) {
          this.$toast.warning(`Đã xóa ${successCount}/${idsToDelete.length} đơn vị (một số thất bại)`)
        } else {
          this.$toast.error('Không thể xóa đơn vị nào!')
        }

        await this.loadDonViData()
        this.resetButtonsStatus()
      } catch (error) {
        this.$toast.error('Đã xảy ra lỗi khi xóa đơn vị!')
      } finally {
        this.$root.loading(false)
      }
    },

    resetButtonsStatus() {
      this.canAdd = true
      this.canEdit = true
      this.canSave = false
      this.canCancel = false
      this.canDelete = true
      this.canImportExcel = true
      this.canExportExcel = true
      this.canEditField = false
    },

    // DATA GRIDS
    mapToHieuLuc: function(field, data) {
      if (field === 'SUDUNG') return data.SUDUNG === HieuLucEnum.HIEU_LUC ? 'Có hiệu lực' : 'Không hiệu lực'
      return data[field]
    },

    isInvalid(value) {
      return !value || value.toString().trim() === ''
    },

    grdItems_RowSelected(event) {
      const row = event.data

      this.currentItem = {
        id: row.DONVISH_ID,
        ten: row.TEN,
        tenTat: row.TENTAT,
        moTa: row.MOTA,
        ghiChu: row.GHICHU,
        suDung: row.SUDUNG
      }
    },

    grdItems_RowDeselected(event) {
      if (this.selectedRows && this.selectedRows.length > 0) {
        const last = this.selectedRows[this.selectedRows.length - 1]
        this.currentItem = {
          id: last.DONVISH_ID,
          ten: last.TEN,
          tenTat: last.TENTAT,
          moTa: last.MOTA,
          ghiChu: last.GHICHU,
          suDung: last.SUDUNG
        }
      } else {
        this.currentItem = { id: null, ten: '', ghiChu: '', suDung: 0 }
      }
    },

    onSelectRowCheckbox(selectedItems) {
      this.resetButtonsStatus()
      this.selectedRows = selectedItems
    },

    processDonViExcelData(rows) {
      return rows.map((row) => ({
        ten: row[0] || '',
        tenTat: row[1] || '',
        moTa: row[2] || '',
        ghiChu: row[3] || '',
        suDung: row[4] === 1 ? 1 : 0
      }))
    },

    async processImportData(importData) {
      try {
        this.$root.loading(true)

        const validItems = importData.map((item) => ({
          ten: String(item.ten || '').trim(),
          tenTat: String(item.tenTat || '').trim(),
          moTa: String(item.moTa || '').trim(),
          ghiChu: String(item.ghiChu || '').trim(),
          suDung: Number(item.suDung) === 1 ? 1 : 0
        }))

        const response = await addMultipleDonVi(validItems)

        if (response.data?.successCount > 0) {
          this.$toast.success(`Import thành công ${response.data.successCount} đơn vị!`)
        }
        if (response.data?.failedCount > 0) {
          this.$toast.warning(`Bỏ qua ${response.data.failedCount} đơn vị lỗi.`)
        }

        await this.loadDonViData()
      } catch (error) {
        this.$toast.error('Lỗi khi import dữ liệu: ' + error.message)
      } finally {
        this.$root.loading(false)
      }
    },

    async onImportExcel() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()

        if (result.success && result.data && result.data.length > 0) {
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} đơn vị từ file Excel?`,
            'Xác nhận import',
            {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
              type: 'warning',
              dangerouslyUseHTMLString: true
            }
          )
            .then(() => true)
            .catch(() => false)

          if (confirmFlag) {
            await this.processImportData(result.data)
          }
        }
      } catch (error) {
        this.$toast.error('Có lỗi khi import Excel: ' + error.message)
      }
    },

    async handleCheckData(checkDataInfo) {
      this.$root.loading(true)
      try {
        const validateData = checkDataInfo.data.map((item) => ({
          ten: String(item.ten || '').trim(),
          tenTat: String(item.tenTat || '').trim(),
          moTa: item.moTa || '',
          ghiChu: item.ghiChu || '',
          suDung: item.suDung ?? 1
        }))

        const response = await checkValidateExcel(validateData)

        if (response.data.error == 200) {
          const result = response.data

          if (result.data.isValid) {
            this.importConfig.checkDataResult = false
            this.$root.toastSuccess('Dữ liệu hợp lệ!')
          } else {
            this.importConfig.checkDataResult = true
            const errorMessage = `${result.data.errorMessage}`
            this.$root.toastError(errorMessage)

            this.$refs.modalImportExcel.handleCheckDataResult({
              isValid: false,
              errors: result.data.errorMessage
            })
          }
        } else {
          this.importConfig.checkDataResult = true
          this.$root.toastError('Không nhận được phản hồi từ server.')
        }
      } catch (error) {
        this.importConfig.checkDataResult = true

        if (error?.data?.message_detail) {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.data?.message_detail)
        } else {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error?.message)
        }
      } finally {
        this.$root.loading(false)
      }
    },

    formatDonViCellValue(value, fieldName) {
      if (fieldName === 'suDung') {
        switch (value) {
          case HieuLucEnum.HIEU_LUC:
            return 'Hiệu lực'
          case HieuLucEnum.KHONG_HIEU_LUC:
            return 'Không hiệu lực'
          default:
            return value
        }
      }
      return value
    },

    async onExportExcel() {
      try {
        if (this.DonViList.length === 0) {
          this.$toast.warning('Dữ liệu trống!')
          return
        }

        const data = this.DonViList.map((item) => ({
          'ID Đơn Vị': item.DONVISH_ID,
          'Tên Đơn Vị': item.TEN,
          'Tên Tắt': item.TENTAT,
          'Mô Tả': item.MOTA,
          'Ghi Chú': item.GHICHU,
          'Hiệu Lực': item.SUDUNG === HieuLucEnum.HIEU_LUC ? 'Hiệu lực' : 'Không hiệu lực'
        }))

        const worksheet = XLSX.utils.json_to_sheet(data)
        worksheet['!cols'] = [{ wch: 10 }, { wch: 25 }, { wch: 15 }, { wch: 25 }, { wch: 25 }, { wch: 15 }]

        const workbook = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Danh sách Đơn Vị')

        XLSX.writeFile(workbook, `Bao_cao_danh_muc_don_vi_${new Date().toISOString().slice(0, 10)}.xlsx`)

        this.$toast.success('Xuất file Excel thành công!')
      } catch (ex) {
        this.$toast.error('Có lỗi khi xuất Excel: ' + ex.message)
      }
    }
  },

  watch: {
    selectedRows(newVal) {
      const hasSelection = newVal.length > 0 || !!this.currentItem.id
      this.canDelete = hasSelection
      this.canEdit = hasSelection
    },

    'currentItem.id'(newVal) {
      const hasSelection = !!newVal || this.selectedRows.length > 0
      this.canDelete = hasSelection
    }
  }
}
</script>
