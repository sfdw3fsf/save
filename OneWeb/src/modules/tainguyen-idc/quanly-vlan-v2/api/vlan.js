const BASE_URL = '/web-ecms/quanly-vlan'
// const BASE_URL = 'http://localhost:10990/quanly-vlan'

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
      const rs = await axios.post(`${BASE_URL}/search`, params)
      return extractData(rs, [])
    } catch (error) {
      handleApiError(error)
    }
  },

  async upsert(axios, payload) {
    try {
      const rs = await axios.post(`${BASE_URL}/upsert`, payload)
      return extractData(rs, null)
    } catch (error) {
      handleApiError(error)
    }
  },

  async delete(axios, id) {
    try {
      const rs = await axios.post(`${BASE_URL}/delete`, { vlanId: id })
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
  },
  async searchSubnet(axios, params = {}) {
    try {
      const rs = await axios.post(`${BASE_URL}/search-subnet`, params)
      return extractData(rs, [])
    } catch (error) {
      handleApiError(error)
    }
  },
  async insertSubnetVlan (axios, payload) {
    try {
      const rs = await axios.post(`${BASE_URL}/insert-subnet-vlan`, payload)
      return extractData(rs, null)
    } catch (error) {
      handleApiError(error)
    }
  },
  async insertBatchSubnetVlan (axios, payload) {
    try {
      const rs = await axios.post(`${BASE_URL}/insert-batch-subnet-vlan`, payload)
      return extractData(rs, null)
    } catch (error) {
      handleApiError(error)
    }
  },
  async deleteSubnetVlan (axios, id) {
    try {
      const rs = await axios.post(`${BASE_URL}/delete-subnet-vlan`, { id: id })
      return extractData(rs, null)
    } catch (error) {
      handleApiError(error)
    }
  },
  async getLichSuThayDoi(axios, khoaDuLieu) {
    try {
      const rs = await axios.post(`/web-ecms/idc/lichsu-tacdong/danhsach`, {
        tenBang: "VLAN",
        tenCot: "",
        khoaDuLieu: khoaDuLieu
      });

      return rs?.data ?? [];
    } catch (error) {
      handleApiError(error);
      return [];
    }
  },

}

