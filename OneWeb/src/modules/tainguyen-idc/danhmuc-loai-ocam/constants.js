// ============= CONSTANTS ============= //

// Danh sách chuẩn (gán cứng)
export const CHUAN_OPTIONS = [
  { ID: 1, TEN: 'IEC 60320' },
  { ID: 2, TEN: 'IEC 60309' },
  { ID: 3, TEN: 'NEMA' }
]

// Default form item
export const DEFAULT_FORM_ITEM = {
  id: 0,
  loaiOcam: '',
  chuan: null,
  hieuLuc: 1
}

// Import config
export const IMPORT_CONFIG = {
  title: 'Import dữ liệu loại ổ cắm',
  templateFileName: 'Template_Import_LoaiOcam.xlsx',
  tableColumns: [
    { key: 'ten', label: 'Loại ổ cắm' },
    { key: 'chuanId', label: 'Chuẩn (ID: 1=IEC 60320, 2=IEC 60309, 3=NEMA)' },
    { key: 'sudung', label: 'Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)' }
  ],
  validationRules: [
    { field: 'ten', required: true, message: 'Loại ổ cắm không được để trống' },
    { field: 'ten', maxLength: 100, message: 'Loại ổ cắm không được vượt quá 100 ký tự' },
    { field: 'chuanId', validator: null },
    { field: 'chuanId', required: true, message: 'Chuẩn không được để trống' },
    { field: 'sudung', validator: (value) => {
        if (value !== null && value !== undefined && value !== '') {
          const stringValue = String(value).trim().toLowerCase()
          
          const numValue = parseFloat(value)
          if (!isNaN(numValue)) {
            if (numValue !== 0 && numValue !== 1) {
              return 'Hiệu lực chỉ nhận giá trị 0 (Không hiệu lực) hoặc 1 (Hiệu lực)'
            }
          } else {
            const isValidText = stringValue.includes('hiệu lực') || stringValue.includes('không hiệu lực')
            if (!isValidText) {
              return 'Hiệu lực chỉ nhận giá trị 0 (Không hiệu lực) hoặc 1 (Hiệu lực)'
            }
          }
        }
        return null // Không có lỗi
      }
    }
  ],
  checkDataResult: true
}

