/**
 * Mapping từ equipment type sang mảng thiết bị và loại thiết bị
 */
export const EQUIPMENT_TYPE_TO_MANG_THIET_BI = {
  SERVER: 11,
  SERVER_BLADE: 12,
  KHAY_DIA: 14,
  NETWORK: 13,
  BLADE: 12,
  CONTROLLER: 11,
  MAY_PHAT_DIEN: 10,
  TU_TRUNG_THE: 10,
  MAY_BIEN_AP: 10,
  TU_HA_THE: 10,
  TU_ATS: 10,
  TU_PHAN_PHOI: 10,
  UPS: 10,
  RPDU: 10
}

export const EQUIPMENT_TYPE_TO_LOAI_THIET_BI = {
  SERVER: 9180,
  SERVER_BLADE: 9181,
  KHAY_DIA: 9185,
  NETWORK: 9177,
  BLADE: 9182,
  CONTROLLER: 9184,
  MAY_PHAT_DIEN: 9190,
  TU_TRUNG_THE: 9191,
  MAY_BIEN_AP: 9192,
  TU_HA_THE: 9193,
  TU_ATS: 9194,
  TU_PHAN_PHOI: 9195,
  UPS: 9196,
  RPDU: 9197
}

export const getMangThietBiId = (equipmentType) => {
  if (!equipmentType) return null
  return EQUIPMENT_TYPE_TO_MANG_THIET_BI[equipmentType] || null
}

export const getLoaiThietBiId = (equipmentType) => {
  if (!equipmentType) return null
  return EQUIPMENT_TYPE_TO_LOAI_THIET_BI[equipmentType] || null
}
