import { getEquipmentTypeFlags, EQUIPMENT_TYPES } from './equipmentTypeConfig.js'

/**
 * Kiểm tra xem field có được hiển thị dựa trên equipmentTypeInfo và condition
 */
export const checkFieldVisibility = (equipmentTypeInfo, condition) => {
  if (!condition) return true
  if (typeof condition === 'function') {
    return condition(equipmentTypeInfo)
  }
  if (typeof condition === 'string') {
    return equipmentTypeInfo[condition] === true
  }
  if (Array.isArray(condition)) {
    return condition.some((key) => equipmentTypeInfo[key] === true)
  }
  if (typeof condition === 'object') {
    return Object.keys(condition).every((key) => {
      return equipmentTypeInfo[key] === condition[key]
    })
  }
  return true
}

export const FIELD_VISIBILITY_CONFIG = {
  // Thông tin tiêu chuẩn hệ thống - luôn hiển thị
  mangThietBiId: true,
  loaiThietBiId: true,
  phanLoaiThietBiId: true,
  
  // Thông tin chung - luôn hiển thị
  maChungLoaiTB: true,
  tenChungLoaiTB: true,
  hieuLuc: true,
  hangSanXuatId: true,
  maVatTu: true,
  ghiChu: true,
  anhDaiDien: true,

  // Attributes for Server, Server Blade, Khay đĩa, Network
  khongGianLapDat: ['isServer', 'isServerBlade', 'isKhayDia', 'isNetwork', 'isController'],
  fullDepth: ['isServer', 'isServerBlade', 'isKhayDia', 'isNetwork', 'isController'],
  congSuatDinhDanh: [
    'isServer',
    'isServerBlade',
    'isKhayDia',
    'isNetwork',
    'isController',
    'isMayPhatDien',
    'isTuTrungThe',
    'isMayBienAp',
    'isTuHaThe',
    'isTuATS',
    'isTuPhanPhoi',
    'isUPS',
    'isRPDU'
  ],

  // Attributes for Máy phát điện
  dienApDinhDanh: ['isMayPhatDien', 'isTuTrungThe', 'isMayBienAp', 'isTuHaThe', 'isTuATS', 'isUPS', 'isRPDU'],
  loaiNguonDien: ['isMayPhatDien', 'isTuTrungThe', 'isMayBienAp', 'isTuHaThe', 'isTuATS', 'isUPS'],
  heSoCosPhi: ['isMayPhatDien'],
  chieuDai: [
    'isMayPhatDien',
    'isTuTrungThe',
    'isMayBienAp',
    'isTuHaThe',
    'isTuATS',
    'isTuPhanPhoi',
    'isUPS',
    'isRPDU'
  ],
  chieuRong: [
    'isMayPhatDien',
    'isTuTrungThe',
    'isMayBienAp',
    'isTuHaThe',
    'isTuATS',
    'isTuPhanPhoi',
    'isUPS',
    'isRPDU'
  ],
  chieuCao: [
    'isMayPhatDien',
    'isTuTrungThe',
    'isMayBienAp',
    'isTuHaThe',
    'isTuATS',
    'isTuPhanPhoi',
    'isUPS',
    'isRPDU'
  ],

  // Attributes for Máy biến áp
  loaiMay: ['isMayBienAp'],

  // Attributes for UPS
  cauHinhHeThong: ['isUPS'],

  // Attributes for rPDU
  dongDienDinhDanh: ['isRPDU']
}

/**
 * Re-export để giữ backward compatibility
 */
export { getEquipmentTypeFlags, EQUIPMENT_TYPES }
