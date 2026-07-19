// Re-export từ const.js để giữ backward compatibility
export { 
  DEVICE_TYPE_MAP, 
  getDeviceTypeFlags,
  IS_TU_PHAN_PHOI,
  IS_RPDU,
  IS_MAY_PHAT_DIEN,
  IS_TU_TRUNG_THE,
  IS_MAY_BIEN_AP,
  IS_TU_DIEN_HA_THE,
  IS_TU_DIEN_ATS,
  IS_UPS,
  IS_DIEU_HOA,
  IS_THAP_GIAN_NHIET,
  IS_MAY_BOM
} from '../../../const.js'

/**
 * Kiểm tra xem field có được hiển thị dựa trên deviceTypeInfo và condition
 * @param {Object} deviceTypeInfo - Thông tin loại thiết bị (chứa các flags như isServer, isNetwork, etc.)
 * @param {string|Array|Object|Function|null|undefined} condition - Điều kiện hiển thị
 * @returns {boolean} true nếu field được hiển thị, false nếu không
 */
export const checkFieldVisibility = (deviceTypeInfo, condition) => {
  if (!condition) return true
  if (typeof condition === 'function') {
    return condition(deviceTypeInfo)
  }
  if (typeof condition === 'string') {
    return deviceTypeInfo[condition] === true
  }
  if (Array.isArray(condition)) {
    return condition.some((key) => deviceTypeInfo[key] === true)
  }
  if (typeof condition === 'object') {
    return Object.keys(condition).every((key) => {
      return deviceTypeInfo[key] === condition[key]
    })
  }

  return true
}

export const FIELD_VISIBILITY_CONFIG = {
  // Thông tin tiêu chuẩn hệ thống
  mangThietBiId: true, // Luôn hiển thị
  heThongId: true, // Luôn hiển thị
  loaiThietBiId: true, // Luôn hiển thị
  chungLoaiThietBiId: true, // Luôn hiển thị
  phanLoaiThietBiId: true, // Luôn hiển thị
  soHuuThietBiId: true, // Luôn hiển thị

  // Thông tin cơ bản
  maThietBi: true, // Luôn hiển thị
  tenThietBi: true, // Luôn hiển thị
  serialNumber: true, // Luôn hiển thị
  dongSanPham: true, // Luôn hiển thị
  thoiDiemLapDat: true, // Luôn hiển thị (Ngày lắp đặt)
  ngayDuaVaoSuDung: true, // Luôn hiển thị (Ngày sử dụng)
  hangSanXuatId: true, // Luôn hiển thị
  nhaCungCapId: true, // Luôn hiển thị
  kyHieuThietBi: true, // Luôn hiển thị
  loaiKhachHangId: false, // Không hiển thị cho các loại thiết bị này
  ghiChu: true, // Luôn hiển thị
  hieuLuc: true, // Luôn hiển thị
  fullDepth: [
    'isTuPhanPhoi',
    'isRPDU',
    'isTuTrungThe',
    'isTuDienHaThe',
    'isTuDienATS',
    'isUPS'
  ],
  trangThaiVanHanhId: true, // Luôn hiển thị
  anhDaiDien: true, // Luôn hiển thị

  // Đặc tính kỹ thuật
  kieuLapDatId: [
    'isRPDU'
  ], // Chỉ hiển thị cho rPDU
  dienApDinhDanh: [
    'isRPDU',
    'isMayPhatDien',
    'isMayBienAp',
    'isTuTrungThe',
    'isTuDienHaThe',
    'isTuDienATS',
    'isUPS',
    'isThapGianNhiet',
    'isMayBom'
  ],
  dongDienDinhDanh: [
    'isRPDU',
    'isMayPhatDien',
    'isMayBienAp',
    'isTuTrungThe',
    'isTuDienHaThe',
    'isTuDienATS',
    'isUPS',
    'isThapGianNhiet'
  ],
  congSuatDinhDanh: [
    'isRPDU',
    'isMayPhatDien',
    'isMayBienAp',
    'isTuDienHaThe',
    'isTuDienATS',
    'isUPS',
    'isThapGianNhiet',
    'isMayBom',
    'isDieuHoa',
    'isTuPhanPhoi'
  ],
  congSuatThucTe: [
    'isMayBienAp',
    'isTuDienHaThe',
    'isTuDienATS',
    'isTuPhanPhoi',
    'isMayPhatDien',
    'isTuTrungThe',
    'isUPS',
    'isMayBom'
  ], // Công suất thực tế (kVA)
  khongGianLapDat: [
    'isTuPhanPhoi',
    'isRPDU',
    'isTuTrungThe',
    'isTuDienHaThe',
    'isTuDienATS',
    'isUPS'
  ],
  // Kích thước thiết bị
  chieuDai: [
    'isTuTrungThe',
    'isMayBienAp',
    'isTuDienHaThe',
    'isTuDienATS',
    'isTuPhanPhoi',
    'isMayPhatDien',
    'isUPS',
    'isDieuHoa',
    'isThapGianNhiet',
    'isMayBom'
  ],
  chieuRong: [
    'isTuTrungThe',
    'isMayBienAp',
    'isTuDienHaThe',
    'isTuDienATS',
    'isTuPhanPhoi',
    'isMayPhatDien',
    'isUPS',
    'isDieuHoa',
    'isThapGianNhiet',
    'isMayBom'
  ],
  chieuCao: [
    'isTuTrungThe',
    'isMayBienAp',
    'isTuDienHaThe',
    'isTuDienATS',
    'isTuPhanPhoi',
    'isMayPhatDien',
    'isUPS',
    'isDieuHoa',
    'isThapGianNhiet',
    'isMayBom'
  ],
  // Loại nguồn điện
  loaiNguonDien: [
    'isTuTrungThe',
    'isTuDienHaThe',
    'isTuDienATS',
    'isMayBienAp',
    'isUPS'
  ], // Loại nguồn điện: 1 pha / 3 pha
  // Đặc tính kỹ thuật cho máy biến áp
  loaiMay: [
    'isMayBienAp'
  ], // Loại máy: Hạ áp / Tăng áp
  heSoCosPhi: [
    'isMayBienAp'
  ], // Hệ số cos phi
  tyLeSuDung: [
    'isMayBienAp'
  ], // Tỷ lệ % sử dụng (ReadOnly, tự động tính: công suất tiêu thụ/công suất danh định x 100%)
  dongDienThucTe: [
    'isMayPhatDien',
    'isTuTrungThe',
    'isTuDienHaThe',
    'isUPS',
    'isMayBom'
  ],
  cauHinhHeThong: [
    'isUPS'
  ]
}
