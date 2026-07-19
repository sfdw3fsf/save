const BASE_URL = '/web-ecms/quanly-vlan'

const extractData = (response, fallback = []) => {
  if (!response) return fallback
  if (response.data && response.data.data !== undefined) {
    return response.data.data
  }
  return response.data ?? fallback
}

const handleApiError = (error) => {
  const message = error?.response?.data?.message_detail || error?.message || 'Đã xảy ra lỗi API'
  throw new Error(message)
}

export default {
  async getDanhSach(axios, params = {}) {
    try {
      const rs = await axios.post(`${BASE_URL}/lay-danhsach`, params)
      return extractData(rs, [])
    } catch (error) {
      handleApiError(error)
    }
  },

  async upsert(axios, payload) {
    try {
      const hasId = !!payload?.id
      const url = hasId ? `${BASE_URL}/cap-nhat` : `${BASE_URL}/them-moi`
      const rs = await axios.post(url, payload)
      return extractData(rs, null)
    } catch (error) {
      handleApiError(error)
    }
  },

  async delete(axios, id) {
    try {
      const rs = await axios.post(`${BASE_URL}/xoa`, { id })
      return extractData(rs, null)
    } catch (error) {
      handleApiError(error)
    }
  },

  async getHaTang (axios, type) {
    try {
      const rs = await axios.post(`${BASE_URL}/lay-hatang`, { type })
      return extractData(rs, [])
    } catch (error) {
      handleApiError(error)
    }
  },
  async getSubnetByVLan (axios, vlan) {
    try {
      const rs = await axios.post(`${BASE_URL}/subnet-by-vlan`, { id: vlan })
      return extractData(rs, [])
    } catch (error) {
      handleApiError(error)
    }
  }

}

