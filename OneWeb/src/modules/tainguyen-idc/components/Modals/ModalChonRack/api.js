// ============= MAIN API ============= //

export default {
  // ============= DANH MỤC ============= //
  // Lấy danh mục chung (có thể dùng cho các loại danh mục khác)
  getDanhMuc: async (axios, loaiDanhMuc, thamSo1 = null) => {
    return await getDanhMucCommon(axios, loaiDanhMuc, thamSo1)
  },

  // Lấy danh mục IDC
  getDanhMucIDC: async (axios, token) => {
    return await getDanhMucCommon(axios, 'IDC-DS-IDC', token.getDonViID())
  },

  searchRacks: async (axios, criteria) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/timkiem-rack-idc', criteria)
      return rs && rs.data ? rs.data : { total: 0, items: [] }
    } catch (error) {
      handleApiError(error)
      return { total: 0, items: [] }
    }
  }
}

// ============= HELPER FUNCTIONS ============= //

// Lấy danh mục chung
const getDanhMucCommon = async (axios, loaiDanhMuc, thamSo1 = null) => {
  try {
    const payload = { loaiDanhMuc }
    if (thamSo1 !== null && thamSo1 !== undefined) {
      payload.thamSo1 = thamSo1
    }
    const rs = await axios.post('/web-ecms/idc/danh-muc/common', payload)
    return rs && rs.data ? rs.data.data : []
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
