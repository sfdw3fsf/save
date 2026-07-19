<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import api from './api'
import * as XLSX from 'xlsx'
import ModalImportExcel from '../components/Modals/ModalImportExcel'

export default {
  components: {
    ModalImportExcel
  },
  data: function() {
    return {
      dataItems: [],
      dsSelected: [],
      currentItem: {
        TRANGTHAI_ID: null,
        TEN: '',
        SUDUNG: 1,
        TENTAT: null
      },
      controls: {
        txtTen: {
          enabled: false,
          invalid: false
        },
        txtTenTat: {
          enabled: false
        },
        checkBoxSuDung: {
          enabled: false
        }
      },
      buttons: {
        btnNhapMoi: {
          enabled: true
        },
        btnGhiLai: {
          enabled: false
        },
        btnHuyBo: {
          enabled: false
        },
        btnSua: {
          enabled: false
        },
        btnXoa: {
          enabled: false
        }
      },

      // Cấu hình cho modal import
      importConfig: {
        title: 'Import dữ liệu',
        templateFileName: 'Template_Import_TrangThaiQuyHoach.xlsx',
        tableColumns: [
          { key: 'ten', label: 'Tên trạng thái quy hoạch' },
          { key: 'tenTat', label: 'Tên viết tắt' },
          { key: 'suDung', label: 'Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)' }
        ],
        // Header cho data grid preview (nếu không truyền thì sẽ dùng tableColumns)
        gridColumns: [
          { key: 'ten', label: 'Tên trạng thái quy hoạch' },
          { key: 'tenTat', label: 'Tên viết tắt' },
          { key: 'suDung', label: 'Hiệu lực' }
        ],
        validationRules: [
          { field: 'ten', required: true, message: 'Tên trạng thái quy hoạch không được để trống' },
          { field: 'ten', maxLength: 100, message: 'Tên trạng thái quy hoạch không được vượt quá 100 ký tự' },
          // { field: 'tenTat', required: true, message: 'Tên viết tắt không được để trống' },
          // { field: 'tenTat', maxLength: 50, message: 'Tên viết tắt không được vượt quá 50 ký tự' },
          { field: 'suDung', type: 'number', message: 'Hiệu lực phải nhập số' },
          { field: 'suDung', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' }
        ],
        checkDataResult: true
      },
      actionMode: 'create' // 'view', 'edit', 'create'
    }
  },
  computed: {
    gridItems: function() {
      return this.$refs.gridItems
    }
  },
  watch: {
    'currentItem.TRANGTHAI_ID': function(value) {
      this.buttons.btnSua.enabled = value > 0
      this.buttons.btnXoa.enabled = value > 0
    }
    // 'currentItem.TEN': function(value) {
    //   this.controls.txtTen.invalid = value
    // }
    // 'currentItem.TENTAT': function(value) {
    //   this.controls.txtTenTat.invalid = value == null || value <= 0
    // },
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.dataItems = await this.getItems()
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    getItems: async function() {
      let result = []
      try {
        let rs = await api.getDanhSachTrangThai(this.$root.context)
        result = rs.data
        result.forEach((item) => {
          item.SUDUNG_LABEL = item.SUDUNG == 1 ? 'Hiệu lực' : 'Không hiệu lực'
        })
        if (result) {
          this.setCurrentItemDefault()
        }
      } catch (error) {
        console.error('Có lỗi xảy ra:', error)
      }
      return result
    },
    updateItem: async function(args) {
      let result = false
      try {
        let rs = await api.capNhatTrangThai(this.$root.context, args)
        result = rs
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
    },
    deleteItem: async function(id) {
      let result = false
      try {
        let rs = await api.xoaTrangThai(this.$root.context, id)
        result = rs.data[0].item
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    enableControls: function(args) {
      this.controls.txtTen.enabled = args
      this.controls.txtTenTat.enabled = args
      this.controls.checkBoxSuDung.enabled = args

      this.buttons.btnGhiLai.enabled = args
      this.buttons.btnNhapMoi.enabled = !args
      this.buttons.btnHuyBo.enabled = this.buttons.btnGhiLai.enabled
      if (this.currentItem.TRANGTHAI_ID > 0) this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled
    },
    setCurrentItem: function(item) {
      this.currentItem.TRANGTHAI_ID = item.TRANGTHAI_ID
      this.currentItem.TEN = item.TEN
      this.currentItem.TENTAT = item.TENTAT
      this.currentItem.SUDUNG =
        item.SUDUNG !== undefined && item.SUDUNG !== null
          ? item.SUDUNG
          : item.SU_DUNG !== undefined && item.SU_DUNG !== null
          ? item.SU_DUNG
          : 1
    },
    setCurrentItemDefault: function() {
      this.setCurrentItem({
        ID: null,
        TEN: '',
        TENTAT: null,
        SUDUNG: 1
      })
    },
    validateForm: function() {
      let isValid = true
      let errorMessage = ''
      Object.keys(this.controls).forEach((k) => (this.controls[k].invalid = false))
      if (!this.currentItem.TEN || this.currentItem.TEN.trim() === '') {
        this.controls.txtTen.invalid = true
        isValid = false
        errorMessage = 'Vui lòng điền đầy đủ thông tin bắt buộc!'
      }

      if (isValid) {
        const name = this.currentItem.TEN.trim().toLowerCase()
        const isDuplicate = this.dataItems.some((item) => item.TEN && item.TEN.trim().toLowerCase() === name)
        if (isDuplicate) {
          this.controls.txtTen.invalid = true
          isValid = false
          errorMessage = 'Tên trạng thái quy hoạch đã tồn tại.'
        }
      }

      if (!isValid) this.$toast.error(errorMessage)
      return isValid
    },
    gridItems_RowSelected: async function(args) {
      this.setCurrentItem(args.data)
    },
    gridItems_RowDeselected: async function(args) {
      let items = this.gridItems.getSelectedRecords()
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0])
      } else {
        this.setCurrentItemDefault()
      }
    },
    btnGhiLai_Click: async function(args) {
      if (this.validateForm()) {
        let flag = await this.$confirm('Bạn có chắc chắn muốn lưu thông tin này không?', 'Xác nhận thực hiện', {
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
            this.$root.showLoading(true)
            let rs = await this.updateItem(this.currentItem)
            console.log('rs', rs)
            if (rs.error == 200) {
              if (this.actionMode === 'create') {
                // this.currentItem.TRANGTHAI_ID = rs.data[0].item
                this.$root.toastSuccess('Thêm trạng thái quy hoạch thiết bị thành công')
              } else {
                this.$root.toastSuccess('Cập nhật trạng thái quy hoạch thiết bị thành công')
              }

              // Reload data từ API
              this.dataItems = await this.getItems()

              // Cập nhật currentItem với dữ liệu mới nhất
              if (this.currentItem.TRANGTHAI_ID === 0) {
                // Nếu là thêm mới, tìm item vừa thêm (thường là item đầu tiên hoặc có ID lớn nhất)
                if (this.dataItems && this.dataItems.length > 0) {
                  // Tìm item mới nhất (có thể dựa vào ID hoặc thời gian tạo)
                  const latestItem = this.dataItems.reduce((latest, current) => {
                    return current.TRANGTHAI_ID > latest.TRANGTHAI_ID ? current : latest
                  })
                  this.setCurrentItem(latestItem)
                }
              } else {
                // Nếu là cập nhật, tìm item có ID tương ứng
                const updatedItem = this.dataItems.find((item) => item.TRANGTHAI_ID === this.currentItem.TRANGTHAI_ID)
                if (updatedItem) {
                  this.setCurrentItem(updatedItem)
                }
              }
            } else {
              if (this.currentItem.TRANGTHAI_ID === 0) {
                this.$root.toastError('Thêm trạng thái quy hoạch thiết bị thất bại!')
              } else {
                this.$root.toastError('Cập nhật trạng thái quy hoạch thiết bị thất bại!')
              }
            }
            this.enableControls(!rs)
          } finally {
            this.$root.showLoading(false)
          }
        }
      }
    },
    btnNhapMoi_Click: async function(args) {
      this.actionMode = 'create'
      this.enableControls(true)
      this.setCurrentItemDefault()
    },
    btnHuyBo_Click: async function(args) {
      let item = null
      item = this.dataItems.find((x) => x.TRANGTHAI_ID == this.currentItem.TRANGTHAI_ID)
      if (!item) {
        let items = this.gridItems.getSelectedRecords()
        if (items && items.length > 0) {
          item = items[0]
        }
      }
      if (this.currentItem.TRANGTHAI_ID && this.currentItem.TRANGTHAI_ID > 0) {
        item = this.dataItems.find((x) => x.TRANGTHAI_ID == this.currentItem.TRANGTHAI_ID)
      } else {
        let items = this.gridItems.getSelectedRecords()
        if (items && items.length > 0) {
          item = items[0]
        }
      }
      if (item != null) {
        this.setCurrentItem(item)
      } else {
        this.setCurrentItemDefault()
      }
      this.enableControls(false)
    },
    btnSua_Click: async function(args) {
      this.actionMode = 'edit'
      this.enableControls(true)
    },
    btnXoa_Click: async function(args) {
      if (this.dsSelected.length <= 0) {
        this.$toast.error('Bạn chưa chọn danh mục cần xoá.')
        return
      }
      let flag = await this.$confirm(
        'Bạn có chắc chắn muốn xóa trạng thái quy hoạch thiết bị đang chọn?',
        'Xác nhận thực hiện',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning',
          dangerouslyUseHTMLString: true
        }
      )
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.$root.showLoading(true)
          let promises = []
          for (let item of this.dsSelected) {
            promises.push(await this.deleteItem(item.TRANGTHAI_ID))
          }
          let res = await Promise.all(promises)
          let ok = res.filter((x) => x === 1)

          if (ok.length > 0) {
            this.$toast.success(`Đã xoá thành công ${ok.length} / ${res.length} mục`)
          } else {
            this.$toast.error(`Có lỗi xảy ra khi xoá danh mục.`)
          }
          this.dataItems = await this.getItems()
          this.enableControls(false)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    btnXuatExcel_Click() {
      this.$refs.gridItems.excelExport({
        fileName: 'DanhMucTrangThaiQuyHoach.xlsx'
      })
    },
    // Hàm xử lý dữ liệu Excel
    processCellData(dataRows) {
      return dataRows.map((row, index) => {
        return {
          ten: row[0] || '',
          tenTat: row[1],
          suDung: row[2]
        }
      })
    },

    // Hàm format giá trị cell
    formatCellValue(value, fieldName = null) {
      if (value === null || value === undefined || value === '') return ''

      // Chỉ format cho trường hiệu lực (suDung)
      if (fieldName === 'suDung' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value === 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }

      return value
    },

    parseNumber(value) {
      if (value === null || value === undefined || value === '') return null
      const num = parseFloat(value)
      return isNaN(num) ? null : num
    },

    btnImportExcel_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal()
        console.log('Import result:', result)

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} trạng thái quy hoạch thiết bị từ file Excel?`,
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
    // method để gọi bulk API
    bulkCreateItems: async function(items) {
      console.log('items to bulk create:', items)
      let result = false
      try {
        let rs = await api.bulkCreateTrangThai(this.$root.context, items)
        result = rs.data
        if (result.successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${result.successCount} trạng thái quy hoạch thiết bị.`)
        }
        if (result.failedCount > 0) {
          this.$root.toastError(`Bỏ qua ${result.failedCount} trạng thái quy hoạch thiết bị.`)
        }
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    processImportData: async function(importData) {
      try {
        this.$root.showLoading(true)
        const errors = []
        const validItems = []
        let successCount = 0
        let errorCount = 0
        for (let i = 0; i < importData.length; i++) {
          const item = importData[i]

          try {
            let pSuDung = 1 // Default value
            if (item.suDung !== undefined && item.suDung !== null) {
              // Nếu là string, convert sang number
              if (typeof item.suDung === 'string') {
                pSuDung = item.suDung.toLowerCase() === 'hiệu lực' ? 1 : 0
              } else {
                // Nếu là number, sử dụng trực tiếp
                pSuDung = item.suDung ? 1 : 0
              }
            }

            // Chuẩn bị dữ liệu theo format API bulk-create
            const importItem = {
              ten: item.ten.trim(),
              tenTat: item.tenTat ? item.tenTat.trim() : null,
              suDung: pSuDung
            }

            validItems.push(importItem)
          } catch (error) {
            errorCount++
            errors.push(`Dòng ${i + 1}: ${error.message}`)
          }
        }

        // Nếu có dữ liệu hợp lệ, gọi bulk API
        console.log('validItems for bulk create:', validItems)
        if (validItems.length > 0) {
          try {
            const result = await this.bulkCreateItems(validItems)
            if (result) {
              successCount = validItems.length
            } else {
              errorCount += validItems.length
              errors.push(`Lỗi khi lưu dữ liệu hàng loạt`)
            }
          } catch (error) {
            errorCount += validItems.length
            errors.push(`Lỗi khi gọi API bulk-create: ${error.message}`)
          }
        }

        // Reload data sau khi import
        this.dataItems = await this.getItems()
      } finally {
        this.$root.showLoading(false)
      }
    },
    async handleCheckData(checkDataInfo) {
      try {
        // Chuẩn bị dữ liệu theo format API validate-batch
        const validateData = checkDataInfo.data.map((item) => ({
          ten: item.ten || '',
          tenTat: item.tenTat || '',
          suDung: item.suDung || 1
        }))

        // Gọi API validate-batch
        const response = await api.validateBatch(this.$root.context, validateData)
        if (response.data) {
          const result = response.data

          if (result.valid) {
            this.importConfig.checkDataResult = false // Không có lỗi -> enable button "Ghi lại"
            this.$root.toastSuccess('Dữ liệu hợp lệ!')
          } else {
            this.importConfig.checkDataResult = true // Có lỗi -> disable button "Ghi lại"
            const errorMessage = 'Có tổng ' + result.errorCount + ' dòng dữ liệu lỗi. \n' + result.errorMessage
            this.$root.toastError(errorMessage)
            // emit cho modalImportExcel để hiển thị lỗi lên grid
            this.$refs.modalImportExcel.handleCheckDataResult({
              isValid: false,
              errors: result.errorMessage
            })
          }
        } else {
          this.importConfig.checkDataResult = true // Có lỗi -> disable button "Ghi lại"
          this.$root.toastError('Lỗi khi nhận phản hồi từ server')
        }
      } catch (error) {
        this.importConfig.checkDataResult = true // Có lỗi -> disable button "Ghi lại"
        console.error('Lỗi khi validate batch:', error)

        if (error.response && error.response.data && error.response.data.message_detail) {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.response.data.message_detail)
        } else {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.message)
        }
      }
    }
  }
}
</script>
<style>
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

/* CSS cho validation error */
.error-input {
  border: 1px solid #f57f6c !important;
  animation-name: bounce;
  animation-duration: 0.5s;
  animation-delay: 0.25s;
}
</style>
