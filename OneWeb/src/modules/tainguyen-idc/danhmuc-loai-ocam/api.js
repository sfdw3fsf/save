// ============= API CALLS ============= //

export default {
  getDanhSachLoaiOcam: async function(axios, searchDto = {}) {
    try {
      const rs = await axios.post('/web-ecms/idc/loai-ocam', searchDto)
      return rs.data
    } catch (error) {
      throw error
    }
  },

  upSertLoaiOcam: async function(axios, payload) {
    try {
      const rs = await axios.post('/web-ecms/idc/loai-ocam/upsert', payload)
      return rs.data
    } catch (e) {
      throw e
    }
  },

  deleteLoaiOcam: async function(axios, ids) {
    try {
      const rs = await axios.post('/web-ecms/idc/loai-ocam/delete-multi', {
        ids: ids
      })
      return rs.data
    } catch (e) {
      throw e
    }
  },

  validateLoaiOcam: async function(axios, payload) {
    try {
      const response = await axios.post('/web-ecms/idc/loai-ocam/validate', payload)
      return response?.data || false
    } catch (error) {
      throw error
    }
  },

  validateLoaiOcamData: async function(axios, payload) {
    try {
      const response = await axios.post('/web-ecms/idc/loai-ocam/validate-multi', payload)
      return response?.data || false
    } catch (error) {
      throw error
    }
  }
}

