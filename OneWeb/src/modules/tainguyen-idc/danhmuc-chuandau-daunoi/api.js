// ============= API CALLS ============= //

export default {
  getDanhSachChuanDauDauNoi: async function(axios, searchDto = {}) {
    try {
      const rs = await axios.post('/web-ecms/idc/chuan-daunoi', searchDto)
      return rs.data
    } catch (error) {
      throw error
    }
  },

  upSertChuanDauDauNoi: async function(axios, payload) {
    try {
      const rs = await axios.post('/web-ecms/idc/chuan-daunoi/upsert', payload)
      return rs.data
    } catch (e) {
      throw e
    }
  },

  deleteChuanDauDauNoi: async function(axios, ids) {
    try {
      const rs = await axios.post('/web-ecms/idc/chuan-daunoi/delete-multi', {
        ids: ids
      })
      return rs.data
    } catch (e) {
      throw e
    }
  },

  validateChuanDauDauNoi: async function(axios, payload) {
    try {
      const response = await axios.post('/web-ecms/idc/chuan-daunoi/validate', payload)
      return response.data
    } catch (error) {
      throw error
    }
  },

  validateChuanDauDauNoiData: async function(axios, payload) {
    try {
      const response = await axios.post('/web-ecms/idc/chuan-daunoi/validate-multi', payload)
      return response.data
    } catch (error) {
      throw error
    }
  }
}

