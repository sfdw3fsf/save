// ============= MAIN API ============= //

export default {
  chuyenPhieuThiCong: async (axios, params) => {
    try {
      const rs = await axios.post('/web-ecms/idc/thietbi-hethong-khachhang/chuyen-phieu-thi-cong', params)
      return rs && rs.data ? rs.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },

  getDanhMucTaiNguyenIDC: (axios, params) => axios.post('/web-ecms/idc/danh-muc/common', params)
}

// ============= HELPER FUNCTIONS ============= //

const handleApiError = (e) => {
  console.log('API ERROR: ', e)
  if (e?.data?.message_detail) {
    throw new Error(e.data.message_detail)
  } else {
    console.error(e)
  }
}
