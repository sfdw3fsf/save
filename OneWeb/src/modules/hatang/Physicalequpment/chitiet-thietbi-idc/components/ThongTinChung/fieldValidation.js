/**
 * Cấu hình validation cho các field
 * - Field luôn required: true
 * - Field conditional required: array các device type flags
 * - Field không required: không có trong config hoặc false
 */

export const FIELD_VALIDATION_CONFIG = {
  // Thông tin tiêu chuẩn hệ thống
  mangThietBiId: true, // Luôn required
  loaiThietBiId: true, // Luôn required
  phanLoaiThietBiId: true, // Luôn required
  soHuuThietBiId: true, // Luôn required

  // Thông tin cơ bản
  tenThietBi: true, // Luôn required
  serialNumber: true, // Luôn required
  dongSanPham: true, // Luôn required (Model)
  hangSanXuatId: true, // Luôn required
  trangThaiVanHanhId: true, // Luôn required

  // Đặc tính kỹ thuật - Conditional required
  khongGianLapDat: [
    'isRPDU',
    'isUPS',
    'isTuTrungThe',
    'isTuDienHaThe',
    'isTuDienATS',
    'isTuPhanPhoi'
  ],
  dienApDinhDanh: [
    'isTuTrungThe',
    'isTuDienHaThe',
    'isTuDienATS',
    'isMayBienAp',
    'isThapGianNhiet',
    'isMayBom'
  ],
  dongDienDinhDanh: [
    'isTuTrungThe',
    'isMayBienAp',
    'isThapGianNhiet'
  ],
  loaiNguonDien: [
    'isTuTrungThe',
    'isTuDienHaThe',
    'isTuDienATS',
    'isMayBienAp',
    'isUPS'
  ],
  dongDienThucTe: [
    'isMayPhatDien',
    'isTuTrungThe',
    'isTuDienHaThe',
    'isUPS',
    'isMayBom'
  ],
  cauHinhHeThong: ['isUPS'],
  loaiMay: ['isMayBienAp'],
  congSuatDinhDanh: ['isMayBienAp'],
  heSoCosPhi: ['isMayBienAp']
}

/**
 * Kiểm tra field có required không dựa trên deviceTypeInfo
 * @param {string} fieldKey - Tên field
 * @param {Object} deviceTypeInfo - Object chứa device type flags
 * @returns {boolean} true nếu required
 */
export const isFieldRequired = (fieldKey, deviceTypeInfo = {}) => {
  const validationRule = FIELD_VALIDATION_CONFIG[fieldKey]

  // Nếu không có rule hoặc là false, không required
  if (!validationRule) {
    return false
  }

  // Nếu là true, luôn required
  if (validationRule === true) {
    return true
  }

  // Nếu là array, kiểm tra device type flags
  if (Array.isArray(validationRule)) {
    return validationRule.some((flag) => deviceTypeInfo[flag] === true)
  }

  return false
}

