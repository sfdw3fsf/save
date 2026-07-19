// ============= CONSTANTS ============= //

// Full depth options
export const FULL_DEPTH_OPTIONS = [
  { ID: 0, TEN: 'Full' },
  { ID: 1, TEN: 'Not Full' }
]

// Default form item
export const DEFAULT_FORM_ITEM = {
  id: 0,
  // Tab 1: Thông tin chung
  maVatTu: null, // Readonly - Chờ đồng bộ với QLTS
  loaiTuDia: null, // SAN, NAS, CEPH, MinIO
  modelThietBi: '',
  hangSanXuatId: null,
  congSuatDinhDanh: null,
  vaiTroThietBiId: null,
  fullDepth: null, // 0: full / 1: not full
  soBoDieuKhien: null,
  cpu: null,
  ram: null,
  // Tab 2: Thông tin Port
  soCong: null,
  tenCong: '',
  loaiCong: null,
  bangThong: null,
  moduleId: null,
  mucDichSuDungId: null,
  // Tab 3: Thông tin khe cắm
  soKhayDia: null,
  maTuDiaCha: '', // Readonly - Hệ thống tự lấy
  soThuTu: null,
  soKheCamSSD: null,
  soKheCamHDD: null,
  soKheCamNvMe: null,
  // Tab 4: Thông tin nguồn
  soCongNguon: null,
  tenCongNguon: '',
  dongDinhDanh: '',
  dienApDinhDanh: null,
  chuanDauNoiId: null,
  soLuongUCanDat: null
}

// Import config
export const IMPORT_CONFIG = {
  title: 'Import dữ liệu chủng loại tủ đĩa',
  templateFileName: 'Template_Import_ChungLoaiTuDia.xlsx',
  tableColumns: [
    { key: 'loaiTuDia', label: 'Loại tủ đĩa' },
    { key: 'modelThietBi', label: 'Model thiết bị' },
    { key: 'hangSanXuat', label: 'Hãng sản xuất' },
    { key: 'congSuatDinhDanh', label: 'Công suất định danh' },
    { key: 'vaiTroThietBi', label: 'Vai trò thiết bị' },
    { key: 'fullDepth', label: 'Full depth' },
    { key: 'soBoDieuKhien', label: 'Số bộ điều khiển' },
    { key: 'cpu', label: 'CPU' },
    { key: 'ram', label: 'RAM' }
  ],
  validationRules: [
    { field: 'loaiTuDia', required: true, message: 'Loại tủ đĩa không được để trống' },
    { field: 'modelThietBi', required: true, message: 'Model thiết bị không được để trống' },
    { field: 'hangSanXuat', required: true, message: 'Hãng sản xuất không được để trống' },
    { field: 'congSuatDinhDanh', type: 'number', message: 'Công suất định danh phải là số hợp lệ' },
    { field: 'soBoDieuKhien', type: 'number', message: 'Số bộ điều khiển phải là số nguyên dương' },
    { field: 'cpu', type: 'number', message: 'CPU phải là số nguyên dương' },
    { field: 'ram', type: 'number', message: 'RAM phải là số nguyên dương' }
  ],
  checkDataResult: true
}

// Form-level validation configs
export const FORM_VALIDATION_RULES = [
  {
    field: 'loaiTuDia',
    control: 'selectLoaiTuDia',
    type: 'select',
    message: 'Loại tủ đĩa không được để trống.'
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
  { field: 'congSuatDinhDanh', control: 'txtCongSuatDinhDanh', message: 'Công suất định danh phải là số dương.' },
  { field: 'soBoDieuKhien', control: 'txtSoBoDieuKhien', message: 'Số bộ điều khiển phải là số dương.' },
  { field: 'cpu', control: 'txtCpu', message: 'CPU phải là số dương.' },
  { field: 'ram', control: 'txtRam', message: 'RAM phải là số dương.' },
  { field: 'soCong', control: 'txtSoCong', message: 'Số cổng phải là số dương.' },
  { field: 'bangThong', control: 'txtBangThong', message: 'Băng thông phải là số dương.' },
  { field: 'soKhayDia', control: 'txtSoKhayDia', message: 'Số khay đĩa phải là số dương.' },
  { field: 'soThuTu', control: 'txtSoThuTu', message: 'Số thứ tự phải là số dương.' },
  { field: 'soKheCamSSD', control: 'txtSoKheCamSSD', message: 'Số khe cắm SSD phải là số dương.' },
  { field: 'soKheCamHDD', control: 'txtSoKheCamHDD', message: 'Số khe cắm HDD phải là số dương.' },
  { field: 'soKheCamNvMe', control: 'txtSoKheCamNvMe', message: 'Số khe cắm NvMe phải là số dương.' },
  { field: 'soCongNguon', control: 'txtSoCongNguon', message: 'Số cổng nguồn phải là số dương.' },
  { field: 'dienApDinhDanh', control: 'txtDienApDinhDanh', message: 'Điện áp định danh phải là số dương.' },
  { field: 'soLuongUCanDat', control: 'txtSoLuongUCanDat', message: 'Số lượng U cần đặt phải là số dương.' }
]
