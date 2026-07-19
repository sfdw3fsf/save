// ============================================================================
// Constants - Device Template Types
// ============================================================================

export const THIET_BI_MAU_TYPE = {
  SERVER: 'SERVER',
  SERVER_BLADE: 'SERVER_BLADE',
  CONTROLLER: 'CONTROLLER',
  KHAY_DIA: 'KHAY_DIA',
  NETWORK: 'NETWORK',
  GENERATOR: 'GENERATOR',
  TU_TRUNG_THE: 'TU_TRUNG_THE',
  MAY_BIEN_AP: 'MAY_BIEN_AP',
  TU_HA_THE: 'TU_HA_THE',
  TU_ATS: 'TU_ATS',
  TU_PHAN_PHOI: 'TU_PHAN_PHOI',
  UPS: 'UPS',
  RPDU: 'RPDU'
}

// ============================================================================
// Constants - Device Template Type IDs (for routing)
// ============================================================================

export const THIET_BI_MAU_TYPE_ID = {
  SERVER: 1,
  SERVER_BLADE: 2,
  CONTROLLER: 3,
  KHAY_DIA: 4,
  NETWORK: 5,
  GENERATOR: 6,
  TU_TRUNG_THE: 7,
  MAY_BIEN_AP: 8,
  TU_HA_THE: 9,
  TU_ATS: 10,
  TU_PHAN_PHOI: 11,
  UPS: 12,
  RPDU: 13
}

// ============================================================================
// Field Visibility Configuration for Device Templates
// Based on the specification table
// ============================================================================

export const FIELD_VISIBILITY_CONFIG = {
  // Common fields for most device types
  maChungLoaiTB: {
    SERVER: true,
    SERVER_BLADE: true,
    CONTROLLER: true,
    KHAY_DIA: true,
    NETWORK: true,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  tenChungLoaiTB: {
    SERVER: true,
    SERVER_BLADE: true,
    CONTROLLER: true,
    KHAY_DIA: true,
    NETWORK: true,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  khongGianLapDat: {
    SERVER: true,
    SERVER_BLADE: true,
    CONTROLLER: true,
    KHAY_DIA: true,
    NETWORK: true,
    GENERATOR: false,
    TU_TRUNG_THE: false,
    MAY_BIEN_AP: false,
    TU_HA_THE: false,
    TU_ATS: false,
    TU_PHAN_PHOI: false,
    UPS: false,
    RPDU: false
  },
  hieuLuc: {
    SERVER: true,
    SERVER_BLADE: true,
    CONTROLLER: true,
    KHAY_DIA: true,
    NETWORK: true,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  fullDepth: {
    SERVER: true,
    SERVER_BLADE: true,
    CONTROLLER: true,
    KHAY_DIA: true,
    NETWORK: true,
    GENERATOR: false,
    TU_TRUNG_THE: false,
    MAY_BIEN_AP: false,
    TU_HA_THE: false,
    TU_ATS: false,
    TU_PHAN_PHOI: false,
    UPS: false,
    RPDU: false
  },
  hangSanXuat: {
    SERVER: true,
    SERVER_BLADE: true,
    CONTROLLER: true,
    KHAY_DIA: true,
    NETWORK: true,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  congSuatDinhDanh: {
    SERVER: true,
    SERVER_BLADE: true,
    CONTROLLER: true,
    KHAY_DIA: true,
    NETWORK: true,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  maVatTu: {
    SERVER: true,
    SERVER_BLADE: true,
    CONTROLLER: true,
    KHAY_DIA: true,
    NETWORK: true,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  anhDaiDien: {
    SERVER: true,
    SERVER_BLADE: true,
    CONTROLLER: false,
    KHAY_DIA: true,
    NETWORK: true,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  ghiChu: {
    SERVER: true,
    SERVER_BLADE: true,
    CONTROLLER: true,
    KHAY_DIA: true,
    NETWORK: true,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  // Electrical fields
  dienApDinhDanh: {
    SERVER: false,
    SERVER_BLADE: false,
    CONTROLLER: false,
    KHAY_DIA: false,
    NETWORK: false,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  loaiNguonDien: {
    SERVER: false,
    SERVER_BLADE: false,
    CONTROLLER: false,
    KHAY_DIA: false,
    NETWORK: false,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: false
  },
  heSoCosPhi: {
    SERVER: false,
    SERVER_BLADE: false,
    CONTROLLER: false,
    KHAY_DIA: false,
    NETWORK: false,
    GENERATOR: true,
    TU_TRUNG_THE: false,
    MAY_BIEN_AP: true,
    TU_HA_THE: false,
    TU_ATS: false,
    TU_PHAN_PHOI: false,
    UPS: false,
    RPDU: false
  },
  // Dimension fields
  chieuDai: {
    SERVER: false,
    SERVER_BLADE: false,
    CONTROLLER: false,
    KHAY_DIA: false,
    NETWORK: false,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  chieuRong: {
    SERVER: false,
    SERVER_BLADE: false,
    CONTROLLER: false,
    KHAY_DIA: false,
    NETWORK: false,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  chieuCao: {
    SERVER: false,
    SERVER_BLADE: false,
    CONTROLLER: false,
    KHAY_DIA: false,
    NETWORK: false,
    GENERATOR: true,
    TU_TRUNG_THE: true,
    MAY_BIEN_AP: true,
    TU_HA_THE: true,
    TU_ATS: true,
    TU_PHAN_PHOI: true,
    UPS: true,
    RPDU: true
  },
  // Special fields
  loaiMay: {
    SERVER: false,
    SERVER_BLADE: false,
    CONTROLLER: false,
    KHAY_DIA: false,
    NETWORK: false,
    GENERATOR: false,
    TU_TRUNG_THE: false,
    MAY_BIEN_AP: true,
    TU_HA_THE: false,
    TU_ATS: false,
    TU_PHAN_PHOI: false,
    UPS: false,
    RPDU: false
  },
  cauHinhHeThong: {
    SERVER: false,
    SERVER_BLADE: false,
    CONTROLLER: false,
    KHAY_DIA: false,
    NETWORK: false,
    GENERATOR: false,
    TU_TRUNG_THE: false,
    MAY_BIEN_AP: false,
    TU_HA_THE: false,
    TU_ATS: false,
    TU_PHAN_PHOI: false,
    UPS: true,
    RPDU: false
  },
  dongDinhDanh: {
    SERVER: false,
    SERVER_BLADE: false,
    CONTROLLER: false,
    KHAY_DIA: false,
    NETWORK: false,
    GENERATOR: false,
    TU_TRUNG_THE: false,
    MAY_BIEN_AP: false,
    TU_HA_THE: false,
    TU_ATS: false,
    TU_PHAN_PHOI: false,
    UPS: false,
    RPDU: true
  }
}

// ============================================================================
// Helper Functions
// ============================================================================

/**
 * Check if a field is visible for a device template type
 * @param {string} fieldName - Field name
 * @param {string} deviceType - Device template type
 * @returns {boolean} True if field is visible
 */
export const isFieldVisible = (fieldName, deviceType) => {
  const fieldConfig = FIELD_VISIBILITY_CONFIG[fieldName]
  if (!fieldConfig) return false
  return fieldConfig[deviceType] === true
}

/**
 * Get device template type from route path
 * @param {string} path - Route path (can be full path or just route name)
 * @returns {string|null} Device template type or null
 */
export const getDeviceTemplateTypeFromPath = (path) => {
  // Extract route name from full path if needed
  let routeName = path
  if (path.includes('/')) {
    const parts = path.split('/')
    routeName = parts[parts.length - 1]
  }
  // Remove query params and id if present
  routeName = routeName.split('?')[0].split(':')[0]
  
  const pathMap = {
    'thietbi-mau-server': THIET_BI_MAU_TYPE.SERVER,
    'thietbi-mau-server-blade': THIET_BI_MAU_TYPE.SERVER_BLADE,
    'thietbi-mau-controller': THIET_BI_MAU_TYPE.CONTROLLER,
    'thietbi-mau-khay-dia': THIET_BI_MAU_TYPE.KHAY_DIA,
    'thietbi-mau-network': THIET_BI_MAU_TYPE.NETWORK,
    'thietbi-mau-generator': THIET_BI_MAU_TYPE.GENERATOR,
    'thietbi-mau-tu-trung-the': THIET_BI_MAU_TYPE.TU_TRUNG_THE,
    'thietbi-mau-may-bien-ap': THIET_BI_MAU_TYPE.MAY_BIEN_AP,
    'thietbi-mau-tu-ha-the': THIET_BI_MAU_TYPE.TU_HA_THE,
    'thietbi-mau-tu-ats': THIET_BI_MAU_TYPE.TU_ATS,
    'thietbi-mau-tu-phan-phoi': THIET_BI_MAU_TYPE.TU_PHAN_PHOI,
    'thietbi-mau-ups': THIET_BI_MAU_TYPE.UPS,
    'thietbi-mau-rpdu': THIET_BI_MAU_TYPE.RPDU
  }
  return pathMap[routeName] || null
}

/**
 * Get default device template object
 * @returns {Object} Default device template
 */
export const getDefaultDeviceTemplate = () => ({
  id: null,
  maChungLoaiTB: '',
  tenChungLoaiTB: '',
  khongGianLapDat: null,
  hieuLuc: 1,
  fullDepth: 0,
  hangSanXuat: null,
  congSuatDinhDanh: '',
  maVatTu: '',
  anhDaiDien: '',
  ghiChu: '',
  dienApDinhDanh: '',
  loaiNguonDien: null,
  heSoCosPhi: null,
  chieuDai: null,
  chieuRong: null,
  chieuCao: null,
  loaiMay: null,
  cauHinhHeThong: '',
  dongDinhDanh: null
})
