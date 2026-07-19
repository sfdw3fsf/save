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

export default {
  async getManufacturerList(axios) {
    try {
      const rs = await axios.get('/web-ecms/danhmuc/hangsanxuat/danhsach')
      return rs && rs.data ? rs.data : []
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
  }
}
