/**
 * Cấu hình thứ tự hiển thị field theo từng phần và từng loại thiết bị
 */
export const FIELD_ORDER_CONFIG = {
  thongTinChung: {
    // Common fields for all types
    default: [
      'maChungLoaiTB',
      'tenChungLoaiTB',
      'hieuLuc',
      'hangSanXuatId',
      'maVatTu',
      'anhDaiDien',
      'ghiChu'
    ],
    // Note: mangThietBiId, loaiThietBiId, phanLoaiThietBiId được hiển thị riêng trong section "Thông tin tiêu chuẩn hệ thống"
    // Server, Server Blade, Khay đĩa, Network
    isServer: ['khongGianLapDat', 'fullDepth', 'congSuatDinhDanh'],
    isServerBlade: ['khongGianLapDat', 'fullDepth', 'congSuatDinhDanh'],
    isKhayDia: ['khongGianLapDat', 'fullDepth', 'congSuatDinhDanh'],
    isNetwork: ['khongGianLapDat', 'fullDepth', 'congSuatDinhDanh'],
    isController: ['khongGianLapDat', 'fullDepth', 'congSuatDinhDanh'],
    // Blade
    isBlade: [],
    // Máy phát điện
    isMayPhatDien: [
      'congSuatDinhDanh',
      'dienApDinhDanh',
      'loaiNguonDien',
      'heSoCosPhi',
      'chieuDai',
      'chieuRong',
      'chieuCao'
    ],
    // Tủ trung thế
    isTuTrungThe: [
      'dienApDinhDanh',
      'loaiNguonDien',
      'chieuDai',
      'chieuRong',
      'chieuCao'
    ],
    // Máy biến áp
    isMayBienAp: [
      'loaiMay',
      'congSuatDinhDanh',
      'dienApDinhDanh',
      'chieuDai',
      'chieuRong',
      'chieuCao'
    ],
    // Tủ hạ thế, Tủ ATS, Tủ phân phối
    isTuHaThe: [
      'dienApDinhDanh',
      'congSuatDinhDanh',
      'loaiNguonDien',
      'chieuDai',
      'chieuRong',
      'chieuCao'
    ],
    isTuATS: [
      'dienApDinhDanh',
      'congSuatDinhDanh',
      'loaiNguonDien',
      'chieuDai',
      'chieuRong',
      'chieuCao'
    ],
    isTuPhanPhoi: [
      'congSuatDinhDanh',
      'dienApDinhDanh',
      'chieuDai',
      'chieuRong',
      'chieuCao'
    ],
    // UPS
    isUPS: [
      'cauHinhHeThong',
      'dienApDinhDanh',
      'congSuatDinhDanh',
      'loaiNguonDien',
      'chieuDai',
      'chieuRong',
      'chieuCao'
    ],
    // rPDU
    isRPDU: [
      'dienApDinhDanh',
      'dongDienDinhDanh',
      'congSuatDinhDanh',
      'chieuDai',
      'chieuRong',
      'chieuCao'
    ]
  }
}

/**
 * Lấy danh sách field theo thứ tự cho một phần và loại thiết bị
 */
export const getFieldOrder = (section, equipmentTypeInfo) => {
  if (!FIELD_ORDER_CONFIG[section] || !equipmentTypeInfo) {
    return []
  }

  // Lấy common fields trước
  const commonFields = FIELD_ORDER_CONFIG[section].default || []

  // Tìm loại thiết bị phù hợp
  const equipmentTypeKeys = Object.keys(FIELD_ORDER_CONFIG[section])
  let specificFields = []
  for (const equipmentTypeKey of equipmentTypeKeys) {
    if (equipmentTypeKey !== 'default' && equipmentTypeInfo[equipmentTypeKey] === true) {
      specificFields = FIELD_ORDER_CONFIG[section][equipmentTypeKey] || []
      break
    }
  }

  // Kết hợp common fields và specific fields
  return [...commonFields, ...specificFields]
}

/**
 * Chia mảng field thành các nhóm để hiển thị trên các row
 */
export const groupFieldsIntoRows = (fields, itemsPerRow = 3) => {
  const rows = []
  for (let i = 0; i < fields.length; i += itemsPerRow) {
    rows.push(fields.slice(i, i + itemsPerRow))
  }
  return rows
}
