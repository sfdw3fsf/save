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
  thietbicha_id: [
    'isBlade',
    'isBoDieuKhien',
    'isKhayDia',
    'isNetwork',
    'isLinecard',
    'isPowercard',
    'isConsolecard',
    'isCard'
  ],
  ip_id: [
    'isServer',
    'isServerBlade',
    'isBlade',
    'isTuDia',
    'isNetwork',
    'isLinecard',
    'isPowercard',
    'isConsolecard',
    'isCard'
  ],
  loaikhachhang: [
    'isServer',
    'isServerBlade',
    'isBlade'
  ],
  full_depth: [
    'isServer',
    'isServerBlade',
    'isTuDia',
    'isBoDieuKhien',
    'isKhayDia',
    'isNetwork'
  ],
  loai_card: [
    // không dùng trường này nữa
    // 'isLinecard',
    // 'isPowercard',
    // 'isConsolecard',
    // 'isCard'
  ],
  loaitudia_id: [
    'isTuDia'
  ],
  cs_dinhdanh: [
    'isServer',
    'isServerBlade',
    'isBlade',
    'isBoDieuKhien',
    'isKhayDia',
    'isNetwork'
  ],
  vaitrothietbi_id: [
    'isServer',
    'isServerBlade',
    'isBlade',
    'isBoDieuKhien',
    'isKhayDia',
    'isNetwork'
  ],
  cs_thucte: [
    'isServer',
    'isServerBlade',
    'isBlade',
    'isNetwork'
  ],
  hedieuhanh_id: [
    'isServer',
    'isServerBlade',
    'isBlade',
    'isBoDieuKhien',
    'isKhayDia',
    'isNetwork'
  ],
  khong_gian_lap_dat: [
    'isServer',
    'isServerBlade',
    'isBoDieuKhien'
  ]
}
