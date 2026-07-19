/**
 * Cấu hình các loại thiết bị mẫu
 */
export const EQUIPMENT_TYPES = {
  SERVER: 'SERVER',
  SERVER_BLADE: 'SERVER_BLADE',
  KHAY_DIA: 'KHAY_DIA',
  NETWORK: 'NETWORK',
  BLADE: 'BLADE',
  CONTROLLER: 'CONTROLLER',
  MAY_PHAT_DIEN: 'MAY_PHAT_DIEN',
  TU_TRUNG_THE: 'TU_TRUNG_THE',
  MAY_BIEN_AP: 'MAY_BIEN_AP',
  TU_HA_THE: 'TU_HA_THE',
  TU_ATS: 'TU_ATS',
  TU_PHAN_PHOI: 'TU_PHAN_PHOI',
  UPS: 'UPS',
  RPDU: 'RPDU'
}

/**
 * Tạo device type flags cho một loại thiết bị mẫu
 * @param {string} equipmentType - Loại thiết bị
 * @returns {Object} Object chứa flags với flag tương ứng = true
 */
export const getEquipmentTypeFlags = (equipmentType) => {
  const flags = {
    isServer: false,
    isServerBlade: false,
    isKhayDia: false,
    isNetwork: false,
    isBlade: false,
    isController: false,
    isMayPhatDien: false,
    isTuTrungThe: false,
    isMayBienAp: false,
    isTuHaThe: false,
    isTuATS: false,
    isTuPhanPhoi: false,
    isUPS: false,
    isRPDU: false
  }

  if (!equipmentType) {
    return flags
  }

  switch (equipmentType) {
    case EQUIPMENT_TYPES.SERVER:
      flags.isServer = true
      break
    case EQUIPMENT_TYPES.SERVER_BLADE:
      flags.isServerBlade = true
      break
    case EQUIPMENT_TYPES.KHAY_DIA:
      flags.isKhayDia = true
      break
    case EQUIPMENT_TYPES.NETWORK:
      flags.isNetwork = true
      break
    case EQUIPMENT_TYPES.BLADE:
      flags.isBlade = true
      break
    case EQUIPMENT_TYPES.CONTROLLER:
      flags.isController = true
      break
    case EQUIPMENT_TYPES.MAY_PHAT_DIEN:
      flags.isMayPhatDien = true
      break
    case EQUIPMENT_TYPES.TU_TRUNG_THE:
      flags.isTuTrungThe = true
      break
    case EQUIPMENT_TYPES.MAY_BIEN_AP:
      flags.isMayBienAp = true
      break
    case EQUIPMENT_TYPES.TU_HA_THE:
      flags.isTuHaThe = true
      break
    case EQUIPMENT_TYPES.TU_ATS:
      flags.isTuATS = true
      break
    case EQUIPMENT_TYPES.TU_PHAN_PHOI:
      flags.isTuPhanPhoi = true
      break
    case EQUIPMENT_TYPES.UPS:
      flags.isUPS = true
      break
    case EQUIPMENT_TYPES.RPDU:
      flags.isRPDU = true
      break
  }

  return flags
}
