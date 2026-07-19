export default {
    async getDanhSach (axios) {
        try {
            const rs = await axios.post('/web-ecms/danhmuc/trangthai-vanhanh/get-v2')
            return rs && rs.data ? rs.data : []
        } catch (error) { 
            handleApiError(error)
        }
    },

    async upsert (axios, data) {
        try {
            const rs = await axios.post('/web-ecms/danhmuc/trangthai-vanhanh/create-or-update', data)   
            return rs && rs.data ? rs.data : null
        } catch (error) {
            handleApiError(error)
        }
    },

    async delete (axios, id){ 
        try {
            const rs = await axios.post(`/web-ecms/danhmuc/trangthai-vanhanh/delete?id=${id}`)
            return rs && rs.data ? rs.data : null
        } catch (error) {
            handleApiError(error)
        }
    },

    async import (axios, data) { 
        try {
            const rs = axios.post('/web-ecms/danhmuc/trangthai-vanhanh/upload-file-v2', data)
            return rs && rs.data ? rs.data : null
        }catch (error) {    
            handleApiError(error)
        }
    },

    async validate (axios, data) { 
        try {
            const rs = await axios.post('/web-ecms/danhmuc/trangthai-vanhanh/validate-file-v2', data)
            return rs && rs.data ? rs.data : null
        } catch (error) {
            handleApiError(error)
        }
    }

}

const handleApiError = (e) => {
    if (e?.response?.data?.message_detail) {
      throw new Error(e.response.data.message_detail)
    } else {
      throw e
    }
}


