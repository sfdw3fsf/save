const BASE_URL = '/web-ecms'


const handleApiError = (error) => {
  const message = error?.response?.data?.message_detail || error?.message || 'Đã xảy ra lỗi API'
  throw new Error(message)
}

export default {
    async getDanhMucVlan (axios, loaiDanhMuc, thamSo1 = null, thamSo2 = null) { 
        try {
            const rs = await axios.post(`${BASE_URL}/danhmuc-thietbi`, {
                loaiDanhMuc: loaiDanhMuc,
                thamSo1: thamSo1,
                thamSo2: thamSo2
            })
            if (rs && rs.data) {
                return rs.data
            }
            return []
        } catch (error) { 
            handleApiError(error)
        }
    },
    async getIdcOptions(axios) {
        try {
            return await this.getDanhMucVlan(axios, 'IDC')
        } catch (error) {
            handleApiError(error)
        }
    },

    async getVrfByInfrastructure(axios, idcId) {
        try {
        if (!idcId) return []
            return await this.getDanhMucVlan(axios, 'VRF-IDC', idcId)
        } catch (error) {
            handleApiError(error)
        }
    },

    async getZoneByVrf(axios, idcId) {
        try {
        if (!idcId) return []
            return await this.getDanhMucVlan(axios, 'ZONE-IDC', idcId)
        } catch (error) {
            handleApiError(error)
        }
    },

    async getNetworkDevicesByInfrastructure(axios, vlanId) {
        try {
            if (!vlanId) return []
            return await this.getDanhMucVlan(axios, 'THIETBI-VLAN', vlanId)
        } catch (error) {
            handleApiError(error)
        }
    },

    async getInfrastructureByIdc(axios, idcId) {
        try {
            if (!idcId) return []
            return await this.getDanhMucVlan(axios, 'HATANG-IDC', idcId)
        } catch (error) {
            handleApiError(error)
        }
    },

    async getNetworkDevicesByHatang(axios, hatangId) {
        try {
            if (!hatangId) return []
            return await this.getDanhMucVlan(axios, 'THIETBI-HATANG', hatangId)
        } catch (error) {
            handleApiError(error)
        }
    },

    async getLoaiIp (axios) {
        try {
            return await this.getDanhMucVlan(axios, 'LOAI-IP')
        } catch (error) {
            handleApiError(error)
        }
    },

    async getPhanLoaiIp (axios) { 
        try {
            return await this.getDanhMucVlan(axios, 'PHANLOAI-IP')
        } catch (error) {
            handleApiError(error)
        }
    }
}