/**
 * Cấu hình thứ tự hiển thị field theo từng phần và từng loại thiết bị
 * Mỗi phần (section) có thể có nhiều loại thiết bị khác nhau
 * Mỗi loại thiết bị có một array các field được sắp xếp theo thứ tự hiển thị
 * Cứ 3 field sẽ nằm trên 1 row
 */

export const FIELD_ORDER_CONFIG = {
  // Đặc tính kỹ thuật
  dacTinhKyThuat: {
    // rPDU
    isRPDU: [
      'kieuLapDatId',
      'dienApDinhDanh',
      'dongDienDinhDanh',
      'congSuatDinhDanh',
      'khongGianLapDat'
    ],
    // Tủ trung thế
    isTuTrungThe: [
      'chieuDai',
      'chieuRong',
      'chieuCao',
      'dienApDinhDanh',
      'dongDienDinhDanh',
      'dongDienThucTe',
      'congSuatDinhDanh',
      'congSuatThucTe',
      'loaiNguonDien'
    ],
    // Máy biến áp
    isMayBienAp: [
      'loaiMay',
      'congSuatDinhDanh',
      'heSoCosPhi',
      'tyLeSuDung',
      'congSuatThucTe',
      'dienApDinhDanh',
      'loaiNguonDien',
      'chieuDai',
      'chieuRong',
      'chieuCao'
    ],
    // Máy phát điện
    isMayPhatDien: [
      'chieuDai',
      'chieuRong',
      'chieuCao',
      'dongDienThucTe',
      'congSuatDinhDanh',
      'congSuatThucTe'
    ],
    // Tủ điện hạ thế
    isTuDienHaThe: [
      'chieuDai',
      'chieuRong',
      'chieuCao',
      'congSuatDinhDanh',
      'dienApDinhDanh',
      'loaiNguonDien',
      'dongDienThucTe',
      'congSuatThucTe'
    ],
    // Tủ điện ATS
    isTuDienATS: [
      'chieuDai',
      'chieuRong',
      'chieuCao',
      'congSuatDinhDanh',
      'dienApDinhDanh',
      'loaiNguonDien',
      'congSuatThucTe'
    ],
    // UPS
    isUPS: [
      'chieuDai',
      'chieuRong',
      'chieuCao',
      'dongDienThucTe',
      'congSuatDinhDanh',
      'congSuatThucTe',
      'cauHinhHeThong'
    ],
    // Tủ phân phối
    isTuPhanPhoi: [
      'chieuDai',
      'chieuRong',
      'chieuCao',
      'congSuatDinhDanh',
      'congSuatThucTe',
      'dienApDinhDanh'
    ],
    // Điều hòa
    isDieuHoa: [
      'chieuDai',
      'chieuRong',
      'chieuCao',
      'congSuatDinhDanh'
    ],
    // Tháp giải nhiệt
    isThapGianNhiet: [
      'chieuDai',
      'chieuRong',
      'chieuCao',
      'congSuatDinhDanh',
      'dienApDinhDanh',
      'dongDienDinhDanh'
    ],
    // Máy bơm
    isMayBom: [
      'chieuDai',
      'chieuRong',
      'chieuCao',
      'congSuatDinhDanh',
      'dienApDinhDanh',
      'dongDienThucTe',
      'congSuatThucTe'
    ]
  }
}

/**
 * Lấy danh sách field theo thứ tự cho một phần và loại thiết bị
 * @param {string} section - Tên phần (ví dụ: 'dacTinhKyThuat')
 * @param {Object} deviceTypeInfo - Object chứa device type flags
 * @returns {Array} Mảng các field key theo thứ tự
 */
export const getFieldOrder = (section, deviceTypeInfo) => {
  if (!FIELD_ORDER_CONFIG[section] || !deviceTypeInfo) {
    return []
  }

  // Tìm loại thiết bị phù hợp
  const deviceTypeKeys = Object.keys(FIELD_ORDER_CONFIG[section])
  for (const deviceTypeKey of deviceTypeKeys) {
    if (deviceTypeInfo[deviceTypeKey] === true) {
      return FIELD_ORDER_CONFIG[section][deviceTypeKey] || []
    }
  }

  return []
}

/**
 * Chia mảng field thành các nhóm 3 field để hiển thị trên các row
 * @param {Array} fields - Mảng các field key
 * @returns {Array} Mảng các nhóm, mỗi nhóm có tối đa 3 field
 */
export const groupFieldsIntoRows = (fields, itemsPerRow = 3) => {
  const rows = []
  for (let i = 0; i < fields.length; i += itemsPerRow) {
    rows.push(fields.slice(i, i + itemsPerRow))
  }
  return rows
}

