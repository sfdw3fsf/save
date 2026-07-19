export const TAB_INDICES = {
  THONG_TIN_CHUNG: 1,
  SUBNET: 2,
  IP_STORE: 3,
  HA_TANG_AO_HOA: 4,
  LICH_SU_THAY_DOI: 5
}
export const LOAI_DOI_TUONG_SD = [
  { ID: 1, TEN: 'Nội bộ' },
  { ID: 0, TEN: 'Khách hàng' }
]
export const DONVI_QUANLY = [
  { ID: 1, TEN: 'IDC' },
  { ID: 2, TEN: 'Cloud' }
]
export const LOAI_HATANG = [
  { ID: 1, TEN: 'Hạ tầng ảo hóa' },
  { ID: 2, TEN: 'Hạ tầng smart cloud' },
  { ID: 3, TEN: 'Hạ tầng IDG' },
  { ID: 4, TEN: 'Hạ tầng backup' }
]

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
