// ============= CONSTANTS (Thiết bị mạng) ============= //

// Full depth options (reuse semantics)
export const FULL_DEPTH_OPTIONS = [
  { ID: 0, TEN: 'Full' },
  { ID: 1, TEN: 'Not Full' }
]

// Default form item for Thiết bị mạng
export const DEFAULT_FORM_ITEM = {
  id: 0,
  // Tab 1: Thông tin chung
  maVatTu: null,
  loaiThietBiMang: null, // Router/Switch/Firewall/LoadBalancer...
  modelThietBi: '',
  hangSanXuatId: null,
  vaiTroThietBiId: null,
  fullDepth: null, // 0: full / 1: not full
  // Hiệu năng cơ bản
  cpu: null,
  ram: null,
  // Tab 2: Thông tin Port
  soCong: null,
  tenCong: '',
  loaiCong: null,
  bangThong: null,
  moduleId: null,
  mucDichSuDungId: null,
  // Tab 3: Thông tin nguồn
  soCongNguon: null,
  tenCongNguon: '',
  dongDinhDanh: '',
  dienApDinhDanh: null,
  chuanDauNoiId: null,
  soLuongUCanDat: null
}

// Import config
export const IMPORT_CONFIG = {
  title: 'Import dữ liệu chủng loại thiết bị mạng',
  templateFileName: 'Template_Import_ChungLoaiThietBiMang.xlsx',
  tableColumns: [
    { key: 'loaiThietBiMang', label: 'Loại thiết bị mạng' },
    { key: 'modelThietBi', label: 'Model thiết bị' },
    { key: 'hangSanXuat', label: 'Hãng sản xuất' },
    { key: 'vaiTroThietBi', label: 'Vai trò thiết bị' },
    { key: 'fullDepth', label: 'Full depth' },
    { key: 'cpu', label: 'CPU' },
    { key: 'ram', label: 'RAM' }
  ],
  validationRules: [
    { field: 'loaiThietBiMang', required: true, message: 'Loại thiết bị mạng không được để trống' },
    { field: 'modelThietBi', required: true, message: 'Model thiết bị không được để trống' },
    { field: 'hangSanXuat', required: true, message: 'Hãng sản xuất không được để trống' },
    { field: 'cpu', type: 'number', message: 'CPU phải là số nguyên dương' },
    { field: 'ram', type: 'number', message: 'RAM phải là số nguyên dương' }
  ],
  checkDataResult: true
}

// Form-level validation configs
export const FORM_VALIDATION_RULES = [
  {
    field: 'loaiThietBiMang',
    control: 'selectLoaiThietBiMang',
    type: 'select',
    message: 'Loại thiết bị mạng không được để trống.'
  },
  {
    field: 'modelThietBi',
    control: 'txtModelThietBi',
    type: 'string',
    message: 'Model thiết bị không được để trống.'
  },
  {
    field: 'hangSanXuatId',
    control: 'selectHangSanXuat',
    type: 'select',
    message: 'Hãng sản xuất không được để trống.'
  }
]

// Number fields must be positive when provided
export const POSITIVE_NUMBER_FIELDS = [
  { field: 'cpu', control: 'txtCpu', message: 'CPU phải là số dương.' },
  { field: 'ram', control: 'txtRam', message: 'RAM phải là số dương.' },
  { field: 'soCong', control: 'txtSoCong', message: 'Số cổng phải là số dương.' },
  { field: 'bangThong', control: 'txtBangThong', message: 'Băng thông phải là số dương.' },
  { field: 'soCongNguon', control: 'txtSoCongNguon', message: 'Số cổng nguồn phải là số dương.' },
  { field: 'dienApDinhDanh', control: 'txtDienApDinhDanh', message: 'Điện áp định danh phải là số dương.' },
  { field: 'soLuongUCanDat', control: 'txtSoLuongUCanDat', message: 'Số lượng U cần đặt phải là số dương.' }
]



