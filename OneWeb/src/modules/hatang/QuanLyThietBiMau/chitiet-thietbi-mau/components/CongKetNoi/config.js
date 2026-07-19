export const PHANLOAI_CONG = {
  CONG_DU_LIEU: 1,
  CONG_NGUON: 2,
  CONG_DIEU_KHIEN: 3,
  KHE_CAM: 4
}

export const LOAICONG_ID = {
  CONG_DU_LIEU: 504,
  CONG_NGUON: 505,
  CONG_DIEU_KHIEN: 506,
  KHE_CAM_DL: 501,
  KHE_CAM_NGUON: 502,
  KHE_CAM_DIEU_KHIEN: 503
}

export const TRANGTHAI_KN = {
  DA_KET_NOI: 142,
  CHUA_KET_NOI: 141
}

export const TRANGTHAI_PORT = {
  CHUA_SU_DUNG: 0,
  DANG_SU_DUNG: 1,
  HONG: 2
}

export const colsCongDuLieu = (parent) => [
  { fieldName: 'TEN', headerText: 'Tên cổng', allowFiltering: true },
  { fieldName: 'LOAICONG', headerText: 'Loại cổng', allowFiltering: true },
  { fieldName: 'TRANGTHAI_PORT', headerText: 'Trạng thái Port', allowFiltering: true, width: 150 },
  { fieldName: 'TRANGTHAI_KN', headerText: 'Trạng thái kết nối', allowFiltering: true, width: 150 }
]

export const colsCongNguon = (parent) => [
  { fieldName: 'TEN', headerText: 'Tên cổng', allowFiltering: true },
  { fieldName: 'DD_DINHDANH', headerText: 'Dòng định danh (A)', allowFiltering: true, width: 150 },
  { fieldName: 'DA_DINHDANH', headerText: 'Điện áp định danh', allowFiltering: true, width: 150 },
  { fieldName: 'LOAI_NDIEN', headerText: 'Loại nguồn điện', allowFiltering: true },
  { fieldName: 'TRANGTHAI_PORT', headerText: 'Trạng thái port', allowFiltering: true },
  { fieldName: 'TRANGTHAI_KN', headerText: 'Trạng thái kết nối', allowFiltering: true }
]

export const colsCongDieuKhien = (parent) => [
  { fieldName: 'TEN', headerText: 'Tên cổng', allowFiltering: true },
  { fieldName: 'LOAICONG', headerText: 'Loại cổng', allowFiltering: true },
  { fieldName: 'TRANGTHAI_PORT', headerText: 'Trạng thái Port', allowFiltering: true },
  { fieldName: 'TRANGTHAI_KN', headerText: 'Trạng thái kết nối', allowFiltering: true }
]
