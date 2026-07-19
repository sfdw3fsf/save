/**
 * Kiểm tra field có required không dựa trên validation config
 */
export const isFieldRequired = (fieldKey, equipmentTypeInfo) => {
  const requiredConfig = {
    mangThietBiId: () => true,
    loaiThietBiId: () => true,
    phanLoaiThietBiId: () => true,
    maChungLoaiTB: () => true,
    tenChungLoaiTB: (info) => {
      return (
        info.isTuHaThe ||
        info.isTuATS ||
        info.isTuPhanPhoi
      )
    },
    hangSanXuatId: (info) => {
      return (
        info.isTuHaThe ||
        info.isTuATS ||
        info.isTuPhanPhoi
      )
    },
    dienApDinhDanh: (info) => {
      return info.isRPDU
    },
    dongDienDinhDanh: (info) => {
      return info.isRPDU
    },
    congSuatDinhDanh: (info) => {
      return info.isRPDU
    },
    loaiNguonDien: (info) => {
      return info.isTuTrungThe
    }
  }

  const validator = requiredConfig[fieldKey]
  if (!validator) {
    return false
  }

  if (typeof validator === 'function') {
    return validator(equipmentTypeInfo)
  }

  return validator === true
}
