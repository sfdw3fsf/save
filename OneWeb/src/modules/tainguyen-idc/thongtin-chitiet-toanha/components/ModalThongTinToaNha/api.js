export default {
    getThongTinToaNha: async (axios, id) => {
        try {
            const rs = await axios.post(`/web-ecms/danhmuc/toanha/get-by-id?id=${id}`);
            return rs && rs.data ? rs.data.data : null;
        } catch (error) {
            const e = error && error.data ? error.data.message_detail : 'Lỗi hệ thống'
            throw new Error(e)
        }
    },
    async getPresignedUrlFromMinio(axios, src) {
        try {
            const rs = await axios.post('/web-ecms/storage/getPresignedUrl', { fileSource: src })
                
            if (rs.data && rs.data.error_code === 'BSS-00000000' && rs.data.data != undefined) {
                return rs.data.data.result
            } else return null
        } catch (error) {
            console.log(error)
            return null
        }
    },
    getDanhSachMatSan: async (axios, id) => {
        try {
            const rs = await axios.post(`/web-ecms/danhmuc/toanha/get-mat-san?id=${id}`);
            return rs && rs.data ? rs.data.data : null;
        } catch (error) {
            const e = error && error.data ? error.data.message_detail : 'Lỗi hệ thống'
            throw new Error(e)
        }
    }
}