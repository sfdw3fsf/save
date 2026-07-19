<template src="./template.html"></template>

<script>
import * as XLSX from 'xlsx'
import DataGrid from '@/components/Controls/DataGrid'

export default {
  name: 'ModalImportExcel',
  components: {
    DataGrid
  },
  props: {
    // Tiêu đề modal
    title: {
      type: String,
      default: 'Import dữ liệu'
    },
    // Cấu hình cột bảng cho file template Excel
    tableColumns: {
      type: Array,
      required: true
      // Ví dụ: [{ key: 'ten', label: 'Tên loại rack' }, { key: 'soU', label: 'Số U' }]
    },
    // Cấu hình cột bảng cho data grid preview (tùy chọn)
    gridColumns: {
      type: Array,
      default: null
      // Nếu không truyền vào thì sẽ sử dụng tableColumns làm default
      // Ví dụ: [{ key: 'ten', label: 'Tên loại rack (Preview)' }, { key: 'soU', label: 'Số U (Preview)' }]
    },
    // Tên file template
    templateFileName: {
      type: String,
      default: 'Template_Import.xlsx'
    },
    // Hàm xử lý dữ liệu từ Excel
    processExcelData: {
      type: Function,
      required: true
      // Nhận vào raw data từ Excel, trả về array objects đã xử lý
    },
    // Hàm format giá trị cell
    formatCellValue: {
      type: Function,
      default: (value) => {
        if (value === null || value === undefined || value === '') return ''
        return value
      }
    },
    // Validation rules
    validationRules: {
      type: Array,
      default: () => []
      // Ví dụ: [
      //   { field: 'ten', required: true, message: 'Tên không được để trống' },
      //   { field: 'soU', type: 'number', min: 1, message: 'Số U phải lớn hơn 0' },
      //   { field: 'ten', maxLength: 100, message: 'Tên không được vượt quá 100 ký tự' }
      // ]
    },
    // Kết quả kiểm tra từ component cha (true = có lỗi, false = không có lỗi)
    checkDataResult: {
      type: Boolean,
      default: null
      // Cách sử dụng:
      // - null: Chưa kiểm tra (button "Ghi lại" sẽ bị disable nếu có lỗi dưới server)
      // - true: Có lỗi từ component cha (button "Ghi lại" sẽ bị disable)
      // - false: Không có lỗi từ component cha (button "Ghi lại" sẽ enable)
    },
    // Bật chế độ đọc dữ liệu theo tên header thay vì vị trí cột
    useHeaderMapping: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      filePath: '',
      importData: [],
      originalData: [], // mảng lưu dữ liệu gốc
      selectedFile: null,
      resolvePromise: null,
      rejectPromise: null,
      hasCheckedServer: false // biến track đã kiểm tra server chưa
    }
  },
  computed: {
    finalGridColumns() {
      // Tạo cột STT
      const sttColumn = {
        fieldName: 'stt',
        headerText: 'STT',
        width: 80,
        textAlign: 'Center',
        allowSorting: false,
        allowFiltering: false
      }

      // Sử dụng gridColumns nếu có, ngược lại sử dụng tableColumns
      const columnsToUse = this.gridColumns || this.tableColumns

      // Tạo các cột từ columnsToUse với valueAccessor để format hiển thị
      const dataColumns = columnsToUse.map((col, index) => ({
        fieldName: col.key,
        headerText: col.label,
        width: 150,
        textAlign: 'Left',
        allowSorting: true,
        allowFiltering: true,
        valueAccessor: (field, data, column) => {
          // Format hiển thị mà không thay đổi dữ liệu gốc
          return this.formatCellValue(data[field], field)
        }
      }))

      // Tạo column trạng thái kiểm tra với template
      const resultColumn = {
        fieldName: 'importResultText',
        headerText: 'Trạng thái kiểm tra',
        width: 200,
        textAlign: 'Center',
        allowSorting: false,
        allowFiltering: false,
        template: this.statusTemplate(this)
      }

      return [sttColumn, ...dataColumns, resultColumn]
    },

    dynamicGridHeight() {
      if (this.importData.length <= 10) {
        return 'auto'
      } else if (this.importData.length <= 50) {
        return '400px'
      } else {
        return '500px'
      }
    },

    // computed property để kiểm tra có lỗi validation không
    hasValidationErrors() {
      return this.importData.some((item) => !item.importResult?.isValid)
    },

    // computed property để kiểm tra có dữ liệu để import không
    hasDataToImport() {
      return this.importData.length > 0
    },

    // computed property để kiểm tra có nên disable button "Ghi lại" không
    shouldDisableSaveButton() {
      // Luôn disable khi mới vào modal (chưa có dữ liệu hoặc chưa kiểm tra)
      if (!this.hasDataToImport) return true

      // Nếu chưa kiểm tra server thì disable
      if (!this.hasCheckedServer) return true

      // Nếu component cha trả về true (có lỗi) thì disable
      if (this.checkDataResult === true) return true

      // Nếu có lỗi validation thì disable
      if (this.hasValidationErrors) return true

      return false
    }
  },
  methods: {
    handleShowModal() {
      this.filePath = ''
      this.importData = []
      this.originalData = [] // Reset dữ liệu gốc khi mở modal
      this.selectedFile = null
      this.hasCheckedServer = false // Reset trạng thái kiểm tra server
      // Reset file input khi mở modal
      this.$nextTick(() => {
        this.resetFileInput()
      })
    },

    hideModal() {
      this.$refs.popupImportExcel.hide()
      if (this.resolvePromise) {
        this.resolvePromise({ success: false, data: null })
      }
    },

    showModal() {
      return new Promise((resolve, reject) => {
        this.$refs.popupImportExcel.show()
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },

    openFileDialog() {
      // Reset input file để đảm bảo event change được trigger ngay cả khi chọn cùng tên file
      this.$refs.fileInput.value = ''
      this.$refs.fileInput.click()
    },

    onFileChange(event) {
      const file = event.target.files[0]
      if (file) {
        this.selectedFile = file
        this.filePath = file.name

        // Reset dữ liệu cũ trước khi đọc file mới
        this.importData = []
        this.originalData = []
        this.hasCheckedServer = false // Reset trạng thái kiểm tra server khi chọn file mới

        this.readExcelFile(file)
      }
    },
    isValidHexColor(value) {
      if (!value) return false

      const v = String(value).trim()
      // Chỉ chấp nhận #RRGGBB
      return /^#[A-Fa-f0-9]{6}$/.test(v)
    },
    // Thêm method để reset file input
    resetFileInput() {
      if (this.$refs.fileInput) {
        this.$refs.fileInput.value = ''
      }
    },

    // method validation
    validateRow(rowData) {
      const errors = []

      this.validationRules.forEach((rule) => {
        const value = rowData[rule.field]

        // Kiểm tra required trước
        if (rule.required && (value === null || value === undefined || value === '')) {
          errors.push(rule.message || `${rule.field} không được để trống`)
          // Nếu field required mà rỗng thì không cần kiểm tra các rule khác
          return
        }

        // Kiểm tra type number (chỉ khi field không rỗng)
        if (rule.type === 'number' && value !== null && value !== undefined && value !== '') {
          // Kiểm tra xem giá trị có phải là số nguyên hoặc số thập phân hợp lệ không
          const numValue = parseFloat(value)
          const stringValue = value.toString().trim()

          // Kiểm tra xem có phải là số hợp lệ không (không chứa ký tự không phải số)
          const isValidNumber = !isNaN(numValue) && /^[+-]?(\d+\.?\d*|\.\d+)$/.test(stringValue)

          if (!isValidNumber) {
            errors.push(rule.message || `${rule.field} phải là số hợp lệ`)
          } else {
            // Kiểm tra min value
            if (rule.min !== undefined && numValue < rule.min) {
              errors.push(rule.message || `${rule.field} phải lớn hơn hoặc bằng ${rule.min}`)
            }
            // Kiểm tra max value
            if (rule.max !== undefined && numValue > rule.max) {
              errors.push(rule.message || `${rule.field} phải nhỏ hơn hoặc bằng ${rule.max}`)
            }

            // Kiểm tra enum (chỉ khi field không rỗng và có enum được định nghĩa)
            if (rule.enum && Array.isArray(rule.enum) && rule.enum.length > 0) {
              if (!rule.enum.includes(numValue)) {
                const enumValues = rule.enum.join(' hoặc ')
                errors.push(rule.message || `${rule.field} chỉ nhận giá trị: ${enumValues}`)
              }
            }
          }
        }
        // Nếu type là colorcode thì kiểm tra mã màu hex
        if (rule.type === 'colorcode' && value !== '') {
          // Sử dụng hàm isValidHexColor để validate
          if (!this.isValidHexColor(value)) {
            errors.push(rule.message || `${rule.field} không phải mã màu hợp lệ (ví dụ: #FF0000 hoặc #F00)`)
            return
          }
        }
        // Kiểm tra maxLength (chỉ khi field không rỗng)
        if (rule.maxLength && value && value.toString().length > rule.maxLength) {
          errors.push(rule.message || `${rule.field} không được vượt quá ${rule.maxLength} ký tự`)
        }

        // Kiểm tra minLength (chỉ khi field không rỗng)
        if (rule.minLength && value && value.toString().length < rule.minLength) {
          errors.push(rule.message || `${rule.field} phải có ít nhất ${rule.minLength} ký tự`)
        }

        // Kiểm tra pattern (regex) (chỉ khi field không rỗng)
        if (rule.pattern && value && !rule.pattern.test(value.toString())) {
          errors.push(rule.message || `${rule.field} không đúng định dạng`)
        }

        // Kiểm tra custom validation function (chỉ khi field không rỗng)
        if (
          rule.validator &&
          typeof rule.validator === 'function' &&
          value !== null &&
          value !== undefined &&
          value !== ''
        ) {
          const customError = rule.validator(value, rowData)
          if (customError) {
            errors.push(customError)
          }
        }

        if (
          rule.enum &&
          Array.isArray(rule.enum) &&
          rule.enum.length > 0 &&
          value !== null &&
          value !== undefined &&
          value !== '' &&
          rule.type !== 'number'
        ) {
          const stringValue = value.toString().trim()
          if (!rule.enum.includes(stringValue)) {
            const enumValues = rule.enum.join(' hoặc ')
            errors.push(rule.message || `${rule.field} chỉ nhận giá trị: ${enumValues}`)
          }
        }
      })

      return {
        isValid: errors.length === 0,
        message: errors.length > 0 ? errors.join('; ') : 'OK'
      }
    },

    // Thêm method để tạo template cho cột trạng thái
    statusTemplate(parent) {
      return function() {
        return {
          template: {
            template: `
              <div class="center" :title="data.importResult?.message || ''">
                <span v-if="!data.importResult?.isValid" class="one-alert text-warning f20 inline vcenter" style="cursor: help;"></span>
                <span v-if="data.importResult?.isValid" class="one-check text-success f20 inline vcenter"></span>
              </div>
            `,
            data() {
              return {
                data: {},
                parent: parent
              }
            }
          }
        }
      }
    },

    readExcelFile(file) {
      const reader = new FileReader()
      reader.onload = (e) => {
        try {
          const data = new Uint8Array(e.target.result)
          const workbook = XLSX.read(data, { type: 'array' })
          const sheetName = workbook.SheetNames[0]
          const worksheet = workbook.Sheets[sheetName]

          // Đọc dữ liệu với header để lấy tên cột
          const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 })

          if (jsonData.length < 2) {
            throw new Error('File Excel phải có ít nhất 1 dòng header và 1 dòng dữ liệu')
          }

          // Lấy header row (dòng đầu tiên)
          const headerRow = jsonData[0]

          // Tạo mapping từ tên header sang index cột
          const headerMapping = this.createHeaderMapping(headerRow)

          // Bỏ qua header row và xử lý dữ liệu
          const dataRows = jsonData.slice(1)
          let processedData

          if (this.useHeaderMapping) {
            // Đọc theo tên header
            processedData = this.processExcelDataWithHeaders(dataRows, headerMapping)
          } else {
            // Đọc theo vị trí cột
            processedData = this.processExcelData(dataRows)
          }

          // Lưu dữ liệu gốc
          this.originalData = processedData.map((item, index) => ({
            stt: index + 1,
            ...item
          }))

          // Tạo dữ liệu hiển thị với STT và kết quả validation
          this.importData = this.originalData.map((item) => {
            const displayItem = {
              stt: item.stt,
              __rawData: { ...item }
            }

            // Gán giá trị hiển thị để hỗ trợ filter theo text đã format
            const columnsToUse = this.gridColumns || this.tableColumns
            columnsToUse.forEach((col) => {
              displayItem[col.key] = this.formatCellValue(item[col.key], col.key)
            })

            // Kết quả validation sử dụng dữ liệu gốc
            const validationResult = this.validateRow(item)
            displayItem.importResult = validationResult
            displayItem.importResultText = validationResult.isValid ? 'OK' : validationResult.message
            console.log('Validation result:', this.originalData)
            return displayItem
          })
        } catch (error) {
          console.error('Lỗi khi đọc file Excel:', error)
          this.$root.toastError('Lỗi khi đọc file Excel: ' + error.message)

          // Reset dữ liệu khi có lỗi
          this.filePath = ''
          this.importData = []
          this.originalData = []
          this.selectedFile = null
          this.resetFileInput()
        }
      }

      reader.onerror = () => {
        this.$root.toastError('Lỗi khi đọc file!')
        this.resetFileInput()
      }

      reader.readAsArrayBuffer(file)
    },

    // Thêm method để tạo mapping từ header
    createHeaderMapping(headerRow) {
      const mapping = {}

      // Tạo mapping từ tên header (label) sang index cột
      this.tableColumns.forEach((column) => {
        const headerIndex = headerRow.findIndex((header) => {
          if (!header) return false

          // So sánh không phân biệt hoa thường và loại bỏ khoảng trắng
          const normalizedHeader = header
            .toString()
            .trim()
            .toLowerCase()
          const normalizedLabel = column.label
            .toString()
            .trim()
            .toLowerCase()

          // Loại bỏ dấu (*) nếu có trong header
          const cleanHeader = normalizedHeader.replace(/\s*\(\*\)\s*$/, '')
          const cleanLabel = normalizedLabel.replace(/\s*\(\*\)\s*$/, '')

          return cleanHeader === cleanLabel
        })

        if (headerIndex !== -1) {
          mapping[column.key] = headerIndex
        } else {
          console.warn(`Không tìm thấy cột "${column.label}" trong file Excel`)
        }
      })

      return mapping
    },

    // Thêm method để xử lý dữ liệu với header mapping
    processExcelDataWithHeaders(dataRows, headerMapping) {
      return dataRows.map((row, index) => {
        const processedRow = {}

        // Sử dụng mapping để lấy dữ liệu theo tên cột thay vì vị trí
        this.tableColumns.forEach((column) => {
          const columnIndex = headerMapping[column.key]
          if (columnIndex !== undefined && columnIndex < row.length) {
            processedRow[column.key] = row[columnIndex]
          } else {
            // Nếu không tìm thấy cột, gán giá trị mặc định
            processedRow[column.key] = ''
          }
        })

        return processedRow
      })
    },

    downloadTemplate() {
      try {
        // Tạo header từ tableColumns với dấu (*) cho các field required
        const templateData = [
          this.tableColumns.map((col) => {
            // Kiểm tra xem field này có required trong validationRules không
            const isRequired = this.validationRules.some((rule) => rule.field === col.key && rule.required === true)

            // Thêm dấu (*) nếu field là required
            return isRequired ? `${col.label} (*)` : col.label
          })
        ]

        const worksheet = XLSX.utils.aoa_to_sheet(templateData)
        const workbook = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Template')

        // Đặt độ rộng cột
        worksheet['!cols'] = this.tableColumns.map((col) => ({ width: 20 }))

        XLSX.writeFile(workbook, this.templateFileName)
        this.$root.toastSuccess('Đã tải file mẫu thành công!')
      } catch (error) {
        console.error('Lỗi khi tạo file mẫu:', error)
        this.$root.toastError('Có lỗi khi tạo file mẫu: ' + error.message)
      }
    },

    importFromExcel() {
      this.openFileDialog()
    },

    saveData() {
      // Kiểm tra có dữ liệu để import không
      if (!this.hasDataToImport) {
        this.$root.toastError('File đính kèm chưa được tải lên hoặc file rỗng!')
        return
      }

      // Kiểm tra xem có dòng nào bị lỗi validation không
      if (this.hasValidationErrors) {
        this.$root.toastError('Có lỗi validation trong dữ liệu. Vui lòng kiểm tra và sửa lỗi trước khi lưu!')
        return
      }

      // Sử dụng dữ liệu gốc (không có format) để trả về
      const dataWithoutStt = this.originalData.map((item) => {
        const { stt, ...rest } = item
        return rest
      })
      // Emit event với dữ liệu đã xử lý
      this.$emit('save-data', dataWithoutStt)

      // Trả về dữ liệu cho component cha
      if (this.resolvePromise) {
        this.resolvePromise({
          success: true,
          data: dataWithoutStt
        })
      }
      this.hideModal()
    },

    checkData() {
      // Kiểm tra có dữ liệu để kiểm tra không
      if (!this.hasDataToImport) {
        this.$root.toastError('File đính kèm chưa được tải lên hoặc file rỗng!')
        return
      }

      // Đánh dấu đã kiểm tra server
      this.hasCheckedServer = true

      // Emit event với dữ liệu gốc
      this.$emit('check-data', {
        data: this.originalData, // Sử dụng dữ liệu gốc
        hasErrors: this.hasValidationErrors,
        totalRows: this.originalData.length,
        validRows: this.importData.filter((item) => item.importResult?.isValid).length,
        errorRows: this.importData.filter((item) => !item.importResult?.isValid).length
      })
    },
    handleImportGridBound() {
      this.$nextTick(() => {
        const gridComponent = this.$refs.importPreviewGrid
        const gridElement = gridComponent?.$el?.querySelector('.e-grid')
        if (gridElement && !gridElement.classList.contains('preserve-whitespace-grid')) {
          gridElement.classList.add('preserve-whitespace-grid')
        }
        if (gridElement) {
          const cells = gridElement.querySelectorAll('.e-rowcell')
          cells.forEach((cell) => {
            cell.style.whiteSpace = 'pre-wrap'
          })
        }
      })
    },

    handleCheckDataResult(result) {
      if (!result.isValid) {
        // tách chuỗi bằng dấu xuống dòng
        const errorList = result.errors.split('\n')

        // tách số thứ tự dòng lỗi và thông báo lỗi, cách nhau bởi dấu :
        const errorMap = errorList.map((err) => {
          const parts = err.split(':')
          const stt = parts[0].split(' ')[1] // lấy số thứ tự sau dấu cách
          return {
            stt: parseInt(stt),
            message: parts
              .slice(1)
              .join(',')
              .trim()
          }
        })

        // Cập nhật lại grid với lỗi từ server
        this.importData = this.importData.map((item) => {
          // Tìm lỗi tương ứng theo stt - Có nhiều lỗi cùng stt thì nối chuỗi lỗi
          const errorEntries = errorMap.filter((err) => err.stt === item.stt)
          let errorEntry = null
          if (errorEntries.length > 0) {
            const messages = errorEntries.map((e) => e.message)
            errorEntry = {
              stt: item.stt,
              message: messages.join('; ')
            }
            item.importResult = {
              isValid: false,
              message: errorEntry.message
            }
            item.importResultText = errorEntry.message
          }

          return item
        })
      }
    },

    exportValidationErrorsFile() {
      if (!this.hasDataToImport) {
        this.$root.toastError('Chưa có dữ liệu để xuất file lỗi!')
        return
      }

      // Lọc các dòng có lỗi validation
      const errorRows = this.importData.filter((item) => !item.importResult?.isValid)
      if (errorRows.length === 0) {
        this.$root.toastInfo('Không có lỗi nào để xuất file!')
        return
      }

      // Tạo dữ liệu để xuất file, bao gồm cả cột lỗi
      const exportData = [['STT', ...this.tableColumns.map((col) => col.label), 'Trạng thái kiểm tra']]

      errorRows.forEach((item) => {
        const row = this.tableColumns.map((col) => {
          if (item.__rawData && Object.prototype.hasOwnProperty.call(item.__rawData, col.key)) {
            return item.__rawData[col.key]
          }
          return item[col.key]
        })
        // Thêm STT và trạng thái kiểm tra vào đầu và cuối mảng
        exportData.push([item.stt, ...row, item.importResult?.message || ''])
      })

      try {
        const worksheet = XLSX.utils.aoa_to_sheet(exportData)
        const workbook = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Error')

        // Đặt độ rộng cột
        worksheet['!cols'] = this.tableColumns.map(() => ({ width: 20 })).concat([{ width: 30 }])

        const errorFileName = 'Error_' + this.templateFileName
        XLSX.writeFile(workbook, errorFileName)
        this.$root.toastSuccess('Đã xuất file lỗi thành công!')
      } catch (error) {
        this.$root.toastError('Có lỗi khi xuất file: ' + error.message)
      }
    }
  }
}
</script>

<style scoped>
.hidden {
  display: none;
}

.table-content {
  margin-top: 15px;
}

/* Giữ nguyên khoảng trắng gốc cho từng ô trong DataGrid */
:deep(.preserve-whitespace-grid .e-rowcell) {
  white-space: pre-wrap !important;
}

.cursor-pointer {
  cursor: pointer;
}

.text-chossen-file {
  font-size: 14px;
  color: #007bff;
  font-weight: 600;
  font-family: 'Roboto', sans-serif;
  padding-left: 5px;
}

/* Style cho trạng thái disabled */
.disabled {
  opacity: 0.6;
  pointer-events: none;
}

.disabled-link {
  color: #6c757d !important;
  cursor: not-allowed !important;
}

.disabled-link:hover {
  text-decoration: none !important;
}

/* Tùy chỉnh style cho modal lớn hơn */
:deep(.modal-xl) {
  max-width: 1200px;
}

:deep(.popup-body) {
  max-height: 70vh;
  overflow-y: auto;
}

/* Giới hạn chiều cao tối đa cho DataGrid khi panelDataHeight = auto */
:deep(.e-grid) {
  max-height: 500px;
  overflow-y: auto;
}

/* Thêm style cho icon trạng thái */
.center {
  text-align: center;
}

.f20 {
  font-size: 20px;
}

.inline {
  display: inline-block;
}

.vcenter {
  vertical-align: middle;
}

/* Style cho tooltip */
[title] {
  position: relative;
}

/* tooltip */
:deep(.e-tooltip) {
  background-color: #333;
  color: white;
  padding: 5px 10px;
  border-radius: 4px;
  font-size: 12px;
  max-width: 300px;
  word-wrap: break-word;
}
</style>
