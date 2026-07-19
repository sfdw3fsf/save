// ============= CONSTANTS ============= //

// Danh sách chuẩn (gán cứng)
export const CHUAN_OPTIONS = [
  { ID: 1, TEN: 'Chuẩn IEC 60320' },
  { ID: 2, TEN: 'Chuẩn IEC 60309' },
  { ID: 3, TEN: 'Chuẩn NEMA' }
]

// Default form item
export const DEFAULT_FORM_ITEM = {
  id: 0,
  chuanDauNoi: '',
  chuan: null,
  hieuLuc: 1
}

// Import config
export const IMPORT_CONFIG = {
  title: 'Import dữ liệu chuẩn đầu đấu nối',
  templateFileName: 'Template_Import_ChuanDauDauNoi.xlsx',
  tableColumns: [
    { key: 'chuanDauNoi', label: 'Chuẩn đấu nối' },
    { key: 'chuanId', label: 'Chuẩn (ID: 1=IEC 60320, 2=IEC 60309, 3=NEMA)' },
    { key: 'hieuLuc', label: 'Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)' }
  ],
  validationRules: [
    { field: 'chuanDauNoi', required: true, message: 'Chuẩn đấu nối không được để trống' },
    { field: 'chuanDauNoi', maxLength: 100, message: 'Chuẩn đấu nối không được vượt quá 100 ký tự' },
    { field: 'chuanId', required: true, message: 'Chuẩn không được để trống' },
    { field: 'hieuLuc', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' }
  ],
  checkDataResult: true
}

