export const PHANLOAI_NHOM = {
  INTERFACE: 1,
  POWER: 2,
  CONSOLE: 3
}

// ============================================================================
// Constants - Device Type IDs
// ============================================================================

export const DEVICE_TYPE_ID = {
  TU_PHAN_PHOI: 9195,
  RPDU: 9197,
  MAY_PHAT_DIEN: 9190,
  TU_TRUNG_THE: 9191,
  MAY_BIEN_AP: 9192,
  TU_DIEN_HA_THE: 9193,
  TU_DIEN_ATS: 9194,
  UPS: 9196,
  DIEU_HOA: 9198,
  THAP_GIAN_NHIET: 9199,
  MAY_BOM: 9200
}

// ============================================================================
// Constants - Device Type to Mảng Thiết Bị Mapping
// ============================================================================

export const DEVICE_TYPE_TO_MANG_THIET_BI = {
  // NHÓM HỆ THỐNG ĐIỆN - CƠ ĐIỆN (id = 10)
  [DEVICE_TYPE_ID.MAY_PHAT_DIEN]: 10,      // URD7.5 MÁY PHÁT ĐIỆN
  [DEVICE_TYPE_ID.TU_TRUNG_THE]: 10,       // URD7.6 TỦ TRUNG THẾ
  [DEVICE_TYPE_ID.MAY_BIEN_AP]: 10,       // URD7.7 MÁY BIẾN ÁP
  [DEVICE_TYPE_ID.TU_DIEN_HA_THE]: 10,    // URD7.8 TỦ ĐIỆN HẠ THẾ
  [DEVICE_TYPE_ID.TU_DIEN_ATS]: 10,       // URD7.9 TỦ ĐIỆN ATS
  [DEVICE_TYPE_ID.TU_PHAN_PHOI]: 10,       // URD7.10 TỦ PHÂN PHỐI
  [DEVICE_TYPE_ID.UPS]: 10,                // URD7.11 UPS
  [DEVICE_TYPE_ID.RPDU]: 10,               // URD7.12 rPDU
  
  // PHỤ TRỢ (id = 15)
  [DEVICE_TYPE_ID.DIEU_HOA]: 15,          // URD7.13 ĐIỀU HÒA
  [DEVICE_TYPE_ID.THAP_GIAN_NHIET]: 15,    // URD7.14 THÁP GIẢI NHIỆT
  [DEVICE_TYPE_ID.MAY_BOM]: 15             // URD7.15 MÁY BƠM
}

// ============================================================================
// Constants - Device Type Mapping
// ============================================================================

/**
 * Mapping từ ID loại thiết bị sang tên loại thiết bị
 * Key: ID loại thiết bị (number)
 * Value: Tên loại thiết bị (string)
 */
export const DEVICE_TYPE_MAP = {
  9195: 'TU-PHAN-PHOI',
  9197: 'RPDU',
  9190: 'MAY-PHAT-DIEN',
  9191: 'TU-TRUNG-THE',
  9192: 'MAY-BIEN-AP',
  9193: 'TU-DIEN-HA-THE',
  9194: 'TU-DIEN-ATS',
  9196: 'UPS',
  9198: 'DIEU-HOA',
  9199: 'THAP-GIAN-NHIET',
  9200: 'MAY-BOM'
}

// ============================================================================
// Constants - Device Type Flags
// ============================================================================

export const IS_TU_PHAN_PHOI = 'isTuPhanPhoi'
export const IS_RPDU = 'isRPDU'
export const IS_MAY_PHAT_DIEN = 'isMayPhatDien'
export const IS_TU_TRUNG_THE = 'isTuTrungThe'
export const IS_MAY_BIEN_AP = 'isMayBienAp'
export const IS_TU_DIEN_HA_THE = 'isTuDienHaThe'
export const IS_TU_DIEN_ATS = 'isTuDienATS'
export const IS_UPS = 'isUPS'
export const IS_DIEU_HOA = 'isDieuHoa'
export const IS_THAP_GIAN_NHIET = 'isThapGianNhiet'
export const IS_MAY_BOM = 'isMayBom'

// Mapping từ tên loại thiết bị sang flag tương ứng
const DEVICE_TYPE_TO_FLAG = {
  'TU-PHAN-PHOI': IS_TU_PHAN_PHOI,
  'RPDU': IS_RPDU,
  'MAY-PHAT-DIEN': IS_MAY_PHAT_DIEN,
  'TU-TRUNG-THE': IS_TU_TRUNG_THE,
  'MAY-BIEN-AP': IS_MAY_BIEN_AP,
  'TU-DIEN-HA-THE': IS_TU_DIEN_HA_THE,
  'TU-DIEN-ATS': IS_TU_DIEN_ATS,
  'UPS': IS_UPS,
  'DIEU-HOA': IS_DIEU_HOA,
  'THAP-GIAN-NHIET': IS_THAP_GIAN_NHIET,
  'MAY-BOM': IS_MAY_BOM
}

// Danh sách tất cả các flags
const ALL_DEVICE_FLAGS = [
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
]

/**
 * Tạo object flags với tất cả flags = false
 * @returns {Object} Object chứa tất cả device flags với giá trị false
 */
const createEmptyDeviceFlags = () => {
  return ALL_DEVICE_FLAGS.reduce((acc, flag) => {
    acc[flag] = false
    return acc
  }, {})
}

/**
 * Tạo device type flags cho một loại thiết bị cụ thể
 * @param {string} deviceTypeName - Tên loại thiết bị (ví dụ: 'SERVER', 'NETWORK')
 * @returns {Object} Object chứa flags với flag tương ứng = true
 */
const createDeviceTypeFlags = (deviceTypeName) => {
  const flags = createEmptyDeviceFlags()
  if (!deviceTypeName) {
    return flags
  }
  const upperDeviceType = deviceTypeName.toUpperCase()
  const flagKey = DEVICE_TYPE_TO_FLAG[upperDeviceType]
  if (flagKey) {
    flags[flagKey] = true
  }
  return flags
}

/**
 * Lấy device type flags từ device type ID
 * @param {number|null|undefined} deviceTypeId - ID loại thiết bị
 * @returns {Object} Object chứa device type flags
 */
export const getDeviceTypeFlags = (deviceTypeId) => {
  if (!deviceTypeId || !DEVICE_TYPE_MAP[deviceTypeId]) {
    return createEmptyDeviceFlags()
  }
  const deviceTypeName = DEVICE_TYPE_MAP[deviceTypeId]
  return createDeviceTypeFlags(deviceTypeName)
}

// ============================================================================
// Constants - Device URL Configuration
// ============================================================================

/**
 * Cấu hình URL cho từng loại thiết bị
 * Key: deviceTypeId (ID loại thiết bị)
 * Value: Object chứa listUrl và detailUrl
 */
export const DEVICE_URL_CONFIG = {
  [DEVICE_TYPE_ID.TU_PHAN_PHOI]: {
    listUrl: '/ha-tang/ds-thietbi-tu-phan-phoi',
    detailUrl: '/ha-tang/thietbi-tu-phan-phoi'
  },
  [DEVICE_TYPE_ID.RPDU]: {
    listUrl: '/ha-tang/ds-thietbi-rpdu',
    detailUrl: '/ha-tang/thietbi-rpdu'
  },
  [DEVICE_TYPE_ID.MAY_PHAT_DIEN]: {
    listUrl: '/ha-tang/ds-thietbi-may-phat-dien',
    detailUrl: '/ha-tang/thietbi-may-phat-dien'
  },
  [DEVICE_TYPE_ID.TU_TRUNG_THE]: {
    listUrl: '/ha-tang/ds-thietbi-tu-trung-the',
    detailUrl: '/ha-tang/thietbi-tu-trung-the'
  },
  [DEVICE_TYPE_ID.MAY_BIEN_AP]: {
    listUrl: '/ha-tang/ds-thietbi-may-bien-ap',
    detailUrl: '/ha-tang/thietbi-may-bien-ap'
  },
  [DEVICE_TYPE_ID.TU_DIEN_HA_THE]: {
    listUrl: '/ha-tang/ds-thietbi-tu-dien-ha-the',
    detailUrl: '/ha-tang/thietbi-tu-dien-ha-the'
  },
  [DEVICE_TYPE_ID.TU_DIEN_ATS]: {
    listUrl: '/ha-tang/ds-thietbi-tu-dien-ats',
    detailUrl: '/ha-tang/thietbi-tu-dien-ats'
  },
  [DEVICE_TYPE_ID.UPS]: {
    listUrl: '/ha-tang/ds-thietbi-ups',
    detailUrl: '/ha-tang/thietbi-ups'
  },
  [DEVICE_TYPE_ID.DIEU_HOA]: {
    listUrl: '/ha-tang/ds-thietbi-dieu-hoa',
    detailUrl: '/ha-tang/thietbi-dieu-hoa'
  },
  [DEVICE_TYPE_ID.THAP_GIAN_NHIET]: {
    listUrl: '/ha-tang/ds-thietbi-thap-gian-nhiet',
    detailUrl: '/ha-tang/thietbi-thap-gian-nhiet'
  },
  [DEVICE_TYPE_ID.MAY_BOM]: {
    listUrl: '/ha-tang/ds-thietbi-may-bom',
    detailUrl: '/ha-tang/thietbi-may-bom'
  }
}

/**
 * Lấy URL configuration cho loại thiết bị
 * @param {number|null|undefined} deviceTypeId - ID loại thiết bị
 * @returns {Object} Object chứa listUrl và detailUrl, hoặc null nếu không tìm thấy
 */
export const getDeviceUrls = (deviceTypeId) => {
  if (!deviceTypeId) {
    return null
  }
  return DEVICE_URL_CONFIG[deviceTypeId] || null
}

export const getDeviceTypeIdByListUrl = (url) => {
  if (!url) {
    return null
  }
  return Object.keys(DEVICE_URL_CONFIG).find(key => {
    return DEVICE_URL_CONFIG[key].listUrl === url;
  });
}