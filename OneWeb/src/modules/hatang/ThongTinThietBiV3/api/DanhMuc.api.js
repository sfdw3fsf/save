import { USAGE_STATUS_LIST } from '../const.js'

export default {
  async getDeviceType(axios) {
    try {
      return await getDanhMucCommon(axios, 'IDC-LOAI-TBI')
    } catch (error) {
      handleApiError(error)
    }
  },
  async getIDC(axios) {
    try {
      const rs = await axios.get('/web-ecms/danhmuc/ha-tang-idc')
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getIDCByDonVi(axios, donViId) {
    try {
      return await getDanhMucCommon(axios, 'IDC-DS-IDC',donViId)
    } catch (error) {
      handleApiError(error)
    }
  },
  async getChungLoaiThietBi(axios) {
    try {
      const rs = await axios.post(`/web-ecms/chungloai-thietbi/lay-ds-chungloai-thietbi`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getIDCBuilding(axios, idcID) {
    try {
      return await getDanhMucCommon(axios, 'IDC-TOANHA-BY-IDC-ID', idcID)
    } catch (error) {
      handleApiError(error)
    }
  },
  async getIDCFloor(axios, idcBuildingID) {
    try {
      const rs = await axios.get(`/web-ecms/danhmuc/ha-tang-idc-mat-san?building_id=${idcBuildingID ?? ''}`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getIDCZone(axios, idcFloorID) {
    try {
      const rs = await axios.get(`/web-ecms/danhmuc/ha-tang-idc-zone?floor_id=${idcFloorID ?? ''}`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getIDCRack(axios, idcZoneID) {
    try {
      const rs = await axios.get(`/web-ecms/danhmuc/ha-tang-idc-rack?zone_id=${idcZoneID ?? ''}`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getDanhMucLoaiTuDia(axios) {
    try {
      const rs = await axios.post(`/web-ecms/danhmuc/loai-tu-dia/danhsach`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getManufacturerList(axios) {
    try {
      const rs = await axios.get('/web-ecms/danhmuc/hangsanxuat/danhsach')
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getSupplierList(axios) {
    try {
      const rs = await axios.get('/web-ecms/danhmuc/nhacungcap/danhsach')
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getDeviceRoleList(axios) {
    try {
      const rs = await axios.get('/web-ecms/hatang/loai-vaitro/v2/get-items?validity=1')
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getOSList(axios) {
    try {
      const rs = await axios.get('/web-ecms/danhmuc/he-dieu-hanh')
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getUsageStatusList(axios) {
    return USAGE_STATUS_LIST
  },
  async getMucDichSuDung(axios) {
    try {
      const rs = await axios.post(`/web-ecms/hatang/mucdich-sudung-cong/get-items`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getBangThongCong(axios) {
    try {
      const rs = await axios.post(`/web-ecms/hatang/bangthong-cong/get-items`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getLoaiCong(axios) {
    try {
      const rs = await axios.post(`/web-ecms/hatang/loai-cong/get-items`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getThongtinModule(axios) {
    try {
      const rs = await axios.post(`/web-ecms/danhmuc/thong-tin-module/danhsach`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getTrangThaiVH(axios) {
    try {
      return await getDanhMucCommon(axios, 'IDC-TRANGTHAI-VH')
    } catch (error) {
      handleApiError(error)
    }
  },
  async getLoaiOCung(axios) {
    try {
      return await getDanhMucCommon(axios, 'IDC-LOAI-OCUNG')
    } catch (error) {
      handleApiError(error)
    }
  },
  async getMangThietBi(axios) {
    try {
      return await getDanhMucCommon(axios, 'IDC-MANG-TBI')
    } catch (error) {
      handleApiError(error)
    }
  },
  async getHeThongByMangThietBi(axios, mangThietBiId) {
    try {
      return await getDanhMucCommon(axios, 'IDC-HETHONG-TBI-BY-MANG-TBI', mangThietBiId)
    } catch (error) {
      handleApiError(error)
    }
  },
  async getLoaiThietBiByMangThietBi(axios, mangThietBiId) {
    try {
      return await getDanhMucCommon(axios, 'IDC-LOAI-TBI', mangThietBiId)
    } catch (error) {
      handleApiError(error)
    }
  },
  async getPhanLoaiTB(axios, loaiThietBiId) {
    try {
      return await getDanhMucCommon(axios, 'IDC-PHANLOAI-TBI', loaiThietBiId)
    } catch (error) {
      handleApiError(error)
    }
  },
  async getRackUnitByRackId(axios, rackId) {
    try {
      return await getDanhMucCommon(axios, 'IDC-RACK-UNIT-BY-RACK', rackId)
    } catch (error) {
      handleApiError(error)
    }
  },
  async getDonViQuanLyList(axios) {
    return await getDanhMucCommon(axios, 'IDC-CLOUD-DONVI-QLY')
  },
  async getDonViIDC(axios) {
    return await getDanhMucCommon(axios, 'IDC-DONVI-QLY')
  },
  async getPhongBanQuanLyList(axios, donViId) {
    return await getDanhMucCommon(axios, 'IDC-CLOUD-DONVI-QLY-CON', donViId)
  },
  async getNguoiQuanLyList(axios, phongBanId) {
    return await getDanhMucCommon(axios, 'NHANVIEN-DONVI-IDC', phongBanId)
  },
  async getNhiemVuList(axios) {
    return await getDanhMucCommon(axios, 'IDC-THIETBI-NHIEMVU')
  },
  /**
   * Lấy danh sách thiết bị cha theo nhóm (NETwork, TUDIA, ...)
   * thamSo1: loaiDanhMuc = 'TBI-CHA'
   * thamSo2: nhóm thiết bị cha (NETwork, TUDIA, ...)
   */
  async getParentDeviceList(axios, parentGroup) {
    try {
      const payload = {
        loaiDanhMuc: 'TBI-CHA',
        thamSo1: 'TBI-CHA',
        thamSo2: parentGroup
      }
      const rs = await axios.post('/web-ecms/idc/danh-muc/common', payload)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  },
  async getLoaiTuDiaList(axios) {
    try {
      const rs = await axios.post(`/web-ecms/danhmuc/loai-tu-dia/danhsach`)
      return rs && rs.data ? rs.data : []
    } catch (error) {
      handleApiError(error)
    }
  }
}
const getDanhMucCommon = async (axios, loaiDanhMuc, thamSo1 = null, thamSo2 = null) => {
  try {
    const payload = { loaiDanhMuc }
    if (thamSo1 !== null && thamSo1 !== undefined) {
      payload.thamSo1 = thamSo1
    }
    if (thamSo2 !== null && thamSo2 !== undefined) {
      payload.thamSo2 = thamSo2
    }
    const rs = await axios.post('/web-ecms/idc/danh-muc/common', payload)
    return rs && rs.data ? rs.data : []
  } catch (error) {
    handleApiError(error)
    return []
  }
}

const handleApiError = (e) => {
  if (e?.data?.message_detail) {
    throw new Error(e.data.message_detail)
  } else {
    console.error(e)
  }
}
