export const COLUMNS_VISIBLE_CONFIG = {
  ma_hatang_aohoa: [
    'isServer',
    'isServerBlade',
    'isTuDia',
    'isNetwork',
  ],
  ten_hatang_aohoa: [
    'isServer',
    'isServerBlade',
    'isTuDia',
    'isNetwork',
  ],
 gan_voi_vm: [
   'isTuDia',
   'isNetwork',
   'isServer',
   'isServerBlade',
  ],
  dungluong: [
    'isTuDia', 
  ],
  trang_thai: [
    'isServerBlade',
    'isTuDia',
    'isServer',
    'isNetwork'
  ]
}

export const getVisibleColumnsHelper=(allColumns, deviceTypeInfo) =>{
  return allColumns.filter(col => {
    const rules = COLUMNS_VISIBLE_CONFIG[col.key]

    if (!rules) return true

    return rules.some(flag => deviceTypeInfo[flag] === true)
  })
}