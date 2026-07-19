/**
 * Config xác định field nào required theo từng loại thiết bị
 * - true: luôn required
 * - array: required nếu deviceTypeInfo có flag tương ứng = true
 */
export const FIELD_VALIDATION_CONFIG = {
  // 1. Thông tin tiêu chuẩn hệ thống
  select_mang_thiet_bi_id: true,
  select_loaithietbi_id: true,
  select_phan_loai_tb_id: true,
  select_so_huu_thiet_bi_id: true,
  select_thietbicha_id: ['isBlade'],
  select_loaitudia_id: ['isTuDia'],
  // 2. Thông tin cơ bản
  txt_ten: true,
  txt_kyhieu: true,
  txt_serial_number: true,
  select_trang_thai_vh_id: true,

  // 3. Đặc tính thiết bị
  select_hangsx_id: true,
  txt_khong_gian_lap_dat: ['isServer', 'isServerBlade', 'isBoDieuKhien']
}

/**
 * Kiểm tra control có required không theo deviceTypeInfo
 * @param {string} controlKey
 * @param {Object} deviceTypeInfo
 * @returns {boolean}
 */
export const isFieldRequired = (controlKey, deviceTypeInfo = {}) => {
  const config = FIELD_VALIDATION_CONFIG[controlKey]
  if (!config) return false
  if (config === true) return true
  if (Array.isArray(config)) {
    return config.some((flag) => deviceTypeInfo?.[flag])
  }
  return false
}

