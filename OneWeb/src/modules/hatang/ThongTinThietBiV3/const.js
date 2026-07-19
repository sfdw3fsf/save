import moment from 'moment'

// ============================================================================
// Constants - Lists
// ============================================================================

export const CUSTOMER_TYPES = [
  {
    id: 1,
    name: 'Ngoài'
  },
  {
    id: 0,
    name: 'Nội bộ'
  }
]

export const USAGE_STATUS_LIST = [
  { ID: 1, TEN: 'Đang sử dụng' },
  { ID: 0, TEN: 'Chưa sử dụng' }
]

export const CONNECTED_LIST = [
  { ID: 1, TEN: 'Uplink' },
  { ID: 0, TEN: 'Downlink' }
]

export const SO_HUU_THIET_BI_LIST = [
  { ID: 1, TEN: 'IDC' },
  { ID: 2, TEN: 'Cloud' },
  { ID: 0, TEN: 'Khách hàng' }
]

// ============================================================================
// Constants - Device Types
// ============================================================================

export const LOAI_THIET_BI = {
  SERVER: 'SERVER',
  SERVER_BLADE: 'SERVER-BLADE',
  BLADE: 'BLADE',
  TU_DIA: 'TU-DIA',
  BO_DIEU_KHIEN: 'BO-DIEU-KHIEN',
  KHAY_DIA: 'KHAY-DIA',
  NETWORK: 'NETWORK',
  LINECARD: 'LINECARD',
  POWERCARD: 'POWERCARD',
  CONSOLECARD: 'CONSOLECARD',
  CARD: 'CARD'
}

// ============================================================================
// Constants - Device Type IDs
// ============================================================================

export const DEVICE_TYPE_ID = {
  SERVER: 9180,
  SERVER_BLADE: 9181,
  BLADE: 9182,
  TU_DIA: 9183,
  BO_DIEU_KHIEN: 9184,
  KHAY_DIA: 9185,
  NETWORK: 9177,
  CARD_NGUON: 9187,
  CARD_DIEU_KHIEN: 9188,
  CARD_DU_LIEU: 9189,
  CARD: 9201
}

// Enum cho device flags
export const IS_SERVER = 'isServer'
export const IS_NETWORK = 'isNetwork'
export const IS_SERVER_BLADE = 'isServerBlade'
export const IS_BLADE = 'isBlade'
export const IS_TU_DIA = 'isTuDia'
export const IS_BO_DIEU_KHIEN = 'isBoDieuKhien'
export const IS_KHAY_DIA = 'isKhayDia'
export const IS_LINECARD = 'isLinecard'
export const IS_POWERCARD = 'isPowercard'
export const IS_CONSOLECARD = 'isConsolecard'
export const IS_CARD = 'isCard'

// Object chứa tất cả flags để dễ iterate
const DEVICE_FLAGS = {
  SERVER: IS_SERVER,
  NETWORK: IS_NETWORK,
  SERVER_BLADE: IS_SERVER_BLADE,
  BLADE: IS_BLADE,
  TU_DIA: IS_TU_DIA,
  BO_DIEU_KHIEN: IS_BO_DIEU_KHIEN,
  KHAY_DIA: IS_KHAY_DIA,
  LINECARD: IS_LINECARD,
  POWERCARD: IS_POWERCARD,
  CONSOLECARD: IS_CONSOLECARD,
  CARD: IS_CARD
}

// Mapping từ loại thiết bị sang flag tương ứng
const DEVICE_TYPE_TO_FLAG = {
  [LOAI_THIET_BI.SERVER]: IS_SERVER,
  [LOAI_THIET_BI.SERVER_BLADE]: IS_SERVER_BLADE,
  [LOAI_THIET_BI.BLADE]: IS_BLADE,
  [LOAI_THIET_BI.TU_DIA]: IS_TU_DIA,
  [LOAI_THIET_BI.BO_DIEU_KHIEN]: IS_BO_DIEU_KHIEN,
  [LOAI_THIET_BI.KHAY_DIA]: IS_KHAY_DIA,
  [LOAI_THIET_BI.NETWORK]: IS_NETWORK,
  [LOAI_THIET_BI.LINECARD]: IS_LINECARD,
  [LOAI_THIET_BI.POWERCARD]: IS_POWERCARD,
  [LOAI_THIET_BI.CONSOLECARD]: IS_CONSOLECARD,
  [LOAI_THIET_BI.CARD]: IS_CARD
}

// Danh sách tất cả các flags
const ALL_DEVICE_FLAGS = Object.values(DEVICE_FLAGS)

// ============================================================================
// Helper Functions - Device Type Flags
// ============================================================================

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
 * @param {string} deviceType - Loại thiết bị
 * @returns {Object} Object chứa flags với flag tương ứng = true
 */
const createDeviceTypeFlags = (deviceType) => {
  const flags = createEmptyDeviceFlags()
  const flagKey = DEVICE_TYPE_TO_FLAG[deviceType]
  if (flagKey) {
    flags[flagKey] = true
  }
  return flags
}

/**
 * Tạo DEVICE_TYPE_FLAGS mapping từ LOAI_THIET_BI
 * @returns {Object} Mapping từ loại thiết bị sang flags object
 */
const buildDeviceTypeFlags = () => {
  return Object.values(LOAI_THIET_BI).reduce((acc, deviceType) => {
    acc[deviceType] = createDeviceTypeFlags(deviceType)
    return acc
  }, {})
}

export const DEVICE_TYPE_FLAGS = buildDeviceTypeFlags()

/**
 * Lấy device type flags từ PHANLOAI
 * @param {string|null|undefined} phanLoai - Phân loại thiết bị
 * @returns {Object} Object chứa device type flags
 */
export const getDeviceTypeFlags = (phanLoai) => {
  if (!phanLoai) {
    return createEmptyDeviceFlags()
  }
  const upperPhanLoai = phanLoai.toUpperCase()
  return DEVICE_TYPE_FLAGS[upperPhanLoai] || createEmptyDeviceFlags()
}

// ============================================================================
// Constants - Other
// ============================================================================

export const THIETBI_PHUTRO_ID = 225

export const PORT_TYPE = {
  INTERFACE: {
    ID: 1,
    NAME: 'Interface'
  },
  POWER: {
    ID: 2,
    NAME: 'Power'
  },
  CONSOLE: {
    ID: 3,
    NAME: 'Console'
  }
}

export const DEFAULT_PORT_TYPE = {
  thietbi_id: 0,
  phanloai: 0,
  loaicong_id: 0,
  so_cong: 0,
  bat_dau: 0,
  port_fname: '',
  mucdichsudung_cong_id: 0,
  bangthongcong_id: 0,
  thongtin_module: '',
  ketnoi: 0
}

// ============================================================================
// Helper Functions - Default Objects
// ============================================================================

/**
 * Base default object cho thiết bị
 * @returns {Object} Base default object
 */
const createBaseDefaultDevice = () => ({
  id: null,
  // Thông tin chung
  mathietbi: '',
  ten: '',
  kyhieu: '',
  mathietbi_infra: '',
  thietbicha_id: null,
  loaithietbi_id: null,
  loaikhachhang: 0,
  serial_number: '',
  model: '',
  part_number: '',
  hsm: 0,
  hieu_luc: 1,
  ghi_chu: '',
  loaitudia_id: null,
  loai_card: null,
  chungloaithietbi_id: null,
  ip_id: null,
  // Đặc tính thiết bị
  vaitrothietbi_ids: [],
  psu_qty_in: 0,
  psu_qty_out: 0,
  cauhinh: '',
  hangsx_id: null,
  namsx: null,
  nhacungcap_id: null,
  cs_dinhdanh: 0,
  cs_thucte: 0,
  hedieuhanh_id: null,
  trangthai_sd: 1,
  ngay_lapdat: moment().format('YYYY-MM-DD'),
  ngay_baohanh: moment().format('YYYY-MM-DD'),
  ngay_sudung: moment().format('YYYY-MM-DD'),
  cpu_sokhe: 0,
  cpu_dacam: 0,
  cpu_total: 0,
  hdd_sokhe: 0,
  hdd_dacam: 0,
  hdd_total: 0,
  ram_sokhe: 0,
  ram_dacam: 0,
  ram_total: 0,
  bodieukhien_qty: 0,
  bodieukhien_dau: 0,
  bodieukhien_fname: '',
  khaydia_qty: 0,
  khaydia_dau: 0,
  khaydia_fname: '',
  // Vị trí lắp đặt TB
  idc_id: null,
  building_id: null,
  floor_id: null,
  zone_id: null,
  rack_id: null,
  unit_qty: 0,
  up_unit_id: null,
  down_unit_id: null,
  // Thong tin kiem ke tai san
  project_id: null,
  project_code: '',
  project_name: '',
  so_the_ts: '',
  donviqly_id: null,
  phongbanqly_id: null,
  nguoiqly_id: null,
  // Thong tin cong thiet bi
  so_linecard: 0,
  so_port_interface: 0,
  so_interface: 0,
  interface_start: 0,
  interface_fname: '',
  so_power: 0,
  so_port_power: 0,
  so_powerslot: 0,
  power_start: 0,
  power_fname: '',
  so_controlcard: 0,
  so_port_console: 0,
  so_console: 0,
  console_start: 0,
  console_fname: '',
  // Nang luc luu tru
  hdd_tong: 0,
  hdd_nangluc: 0,
  hdd_capphat: 0,
  ssd_tong: 0,
  ssd_nangluc: 0,
  ssd_capphat: 0,
  iops_tong: 0,
  iops_nangluc: 0,
  iops_capphat: 0,
  mem_cache: null,
  flash_cache: null,
  // Loai thiet bi
  is_interface: null,
  is_power: null,
  is_console: null,
  is_controller: null,
  is_khaydia: null
})

/**
 * Tạo default object cho thiết bị với các options tùy chỉnh
 * @param {Object} options - Các options để override hoặc extend base object
 * @returns {Object} Default device object
 */
export const createDefaultDevice = (options = {}) => {
  return {
    ...createBaseDefaultDevice(),
    ...options
  }
}

// ============================================================================
// Exported Default Objects
// ============================================================================

export const NEW_DEFAULT_OBJ = createDefaultDevice()

export const NEW_DEFAULT_DEVICE = createDefaultDevice()

// ============================================================================
// Constants - Device URL Configuration
// ============================================================================

/**
 * Cấu hình URL cho từng loại thiết bị
 * Key: deviceTypeId (ID loại thiết bị)
 * Value: Object chứa listUrl và detailUrl
 */
export const DEVICE_URL_CONFIG = {
  [DEVICE_TYPE_ID.SERVER]: {
    listUrl: '/ha-tang/ds-thietbi-server',
    detailUrl: '/ha-tang/thietbi-server'
  },
  [DEVICE_TYPE_ID.NETWORK]: {
    listUrl: '/ha-tang/ds-thietbi-network',
    detailUrl: '/ha-tang/thietbi-network'
  },
  [DEVICE_TYPE_ID.BLADE]: {
    listUrl: '/ha-tang/ds-thietbi-blade',
    detailUrl: '/ha-tang/thietbi-blade'
  },
  [DEVICE_TYPE_ID.SERVER_BLADE]: {
    listUrl: '/ha-tang/ds-thietbi-server-blade',
    detailUrl: '/ha-tang/thietbi-server-blade'
  },
  [DEVICE_TYPE_ID.TU_DIA]: {
    listUrl: '/ha-tang/ds-thietbi-tudia',
    detailUrl: '/ha-tang/thietbi-tudia'
  },
  [DEVICE_TYPE_ID.BO_DIEU_KHIEN]: {
    listUrl: '/ha-tang/ds-thietbi-bo-dk',
    detailUrl: '/ha-tang/thietbi-bo-dk'
  },
  [DEVICE_TYPE_ID.KHAY_DIA]: {
    listUrl: '/ha-tang/ds-thietbi-khay-dia',
    detailUrl: '/ha-tang/thietbi-khay-dia'
  },
  [DEVICE_TYPE_ID.CARD_DU_LIEU]: {
    listUrl: '/ha-tang/ds-thietbi-linecard',
    detailUrl: '/ha-tang/thietbi-linecard'
  },
  [DEVICE_TYPE_ID.CARD_NGUON]: {
    listUrl: '/ha-tang/ds-thietbi-powercard',
    detailUrl: '/ha-tang/thietbi-powercard'
  },
  [DEVICE_TYPE_ID.CARD_DIEU_KHIEN]: {
    listUrl: '/ha-tang/ds-thietbi-consolecard',
    detailUrl: '/ha-tang/thietbi-consolecard'
  },
  [DEVICE_TYPE_ID.CARD]: {
    listUrl: '/ha-tang/ds-thietbi-card',
    detailUrl: '/ha-tang/thietbi-card'
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

