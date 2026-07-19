// ============= CONSTANTS ============= //

// Default form item
export const DEFAULT_FORM_ITEM = {
  id: 0,
  ten: '',
  nhomMucDichId: null,
  hieuLuc: 1,
  ghiChu: ''
}

// Import config
export const IMPORT_CONFIG = {
  title: 'Import dữ liệu mục đích sử dụng',
  templateFileName: 'Template_Import_MucDichSuDung.xlsx',
  tableColumns: [
    { key: 'ten', label: 'Mục đích sử dụng' },
    { key: 'nhomMucDichId', label: 'Đối tượng sử dụng (ID hoặc tên)' },
    { key: 'hieuLuc', label: 'Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)' },
    { key: 'ghiChu', label: 'Ghi chú' }
  ],
  validationRules: [
    { field: 'ten', required: true, message: 'Mục đích sử dụng không được để trống' },
    { field: 'ten', maxLength: 100, message: 'Mục đích sử dụng không được vượt quá 100 ký tự' },
    { field: 'nhomMucDichId', validator: null },
    { field: 'nhomMucDichId', required: true, message: 'Đối tượng sử dụng không được để trống' },
    { field: 'hieuLuc', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' },
    { field: 'ghiChu', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' }
  ],
  checkDataResult: true
}

