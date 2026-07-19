// ============= CONSTANTS ============= //

// Default form item
export const DEFAULT_FORM_ITEM = {
  id: 0,
  ten: '',
  mucDichSuDung: null,
  thoiDiemVanHanh: '',
  dienTich: null,
  trangThaiVanHanh: null,
  anhDaiDien: '',
  ghiChu: '',
  suDung: 1,
  donViIdc: null,
  idcId: null,
  toaNhaId: null,
  matsanId: null,
  viTri: '',
  toaDoX: '',
  toaDoY: '',
  slRackThietKe: null,
  slRackDaLapDat: null,
  slRackKhaDung: null,
  csDinhDanhThietBi: null
}

// Import config
export const IMPORT_CONFIG = {
  title: 'Import dữ liệu phòng/zone',
  templateFileName: 'Template_Import_PhongZone.xlsx',
  tableColumns: [
    { key: 'matsanId', label: 'Tên Mặt sàn' },
    { key: 'ten', label: 'Tên phòng/zone' },
    { key: 'dienTich', label: 'Diện tích (m2)' },
    { key: 'trangThaiVH', label: 'Trạng thái vận hành' },
    { key: 'ghiChu', label: 'Ghi chú' },
    { key: 'suDung', label: 'Hiệu lực' }
  ],
  validationRules: [
    { field: 'matsanId', required: true, message: 'Mặt sàn không được để trống' },
    { field: 'matsanId', validator: null },
    { field: 'ten', required: true, message: 'Tên phòng/zone không được để trống' },
    { field: 'ten', maxLength: 100, message: 'Tên phòng/zone không được vượt quá 100 ký tự' },
    { field: 'dienTich', type: 'number', message: 'Diện tích phải là số hợp lệ' },
    { field: 'trangThaiVH', validator: null },
    { field: 'trangThaiVH', required: true, message: 'Trạng thái vận hành không được để trống' },
    { field: 'suDung', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' },
    { field: 'ghiChu', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' }
  ],
  checkDataResult: true
}

