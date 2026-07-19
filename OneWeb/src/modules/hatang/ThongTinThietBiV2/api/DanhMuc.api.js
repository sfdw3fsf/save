import { USAGE_STATUS_LIST } from '../const.js'

export default {
  async getDeviceType(axios) {
    try {
      const rs = await axios.get('/web-ecms/danh-muc-loai-thiet-bi/get-all?validity=1')
      return rs && rs.data ? rs.data : []
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
}
const getDanhMucCommon = async (axios, loaiDanhMuc, thamSo1 = null) => {
  try {
    const payload = { loaiDanhMuc }
    if (thamSo1 !== null && thamSo1 !== undefined) {
      payload.thamSo1 = thamSo1
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
