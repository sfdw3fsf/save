// ============= MAIN API ============= //

export default {
  getDanhSachIdc: async (axios, id) => {
    try {
      const rs = await axios.get('/web-ecms/danhmuc/idc/statistic')
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  }
}

const handleApiError = (e) => {
  console.log('API ERROR: ', e)
  if (e?.data?.message_detail) {
    throw new Error(e.data.message_detail)
  } else {
    console.error(e)
  }
}
