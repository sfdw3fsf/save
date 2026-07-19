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
  },
  getChiTietIDC: async (axios, id) => {
    try {
      const rs = await axios.post('/web-ecms/danhmuc/idc/statistic-detail', {
        idcId: id
      })
      return rs && rs.data ? rs.data.data : null
    } catch (error) {
      handleApiError(error)
      return null
    }
  },
  getRackStatistic: async (axios, idcId) => {
    try {
      const rs = await axios.post('/web-ecms/danhmuc/idc/rack-statistic', {
        idcId: idcId
      })
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
  },
  getMatSanStatistic: async (axios, idcId) => {
    try {
      const rs = await axios.post('/web-ecms/danhmuc/idc/matsan-statistic?id='+idcId)
      // const rs = await axios.post('http://localhost:10990/danhmuc/idc/matsan-statistic?id='+idcId)
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
  },
  getKhachHangStatistic: async (axios, idcId) => {
    try {
      const rs = await axios.post('/web-ecms/danhmuc/idc/khachhang-statistic?id='+idcId)
      // const rs = await axios.post('http://localhost:10990/danhmuc/idc/khachhang-statistic?id='+idcId)
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
    }
  },
  getToaNhaStatistic: async (axios, idcId) => {
    try {
      const rs = await axios.post('/web-ecms/danhmuc/idc/toanha-statistic?id=' + idcId)
      // const rs = await axios.post('http://localhost:10990/danhmuc/idc/toanha-statistic?id=' + idcId)
      return rs && rs.data ? rs.data.data : []
    } catch (error) {
      handleApiError(error)
      return []
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
