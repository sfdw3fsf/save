export default {
    getDsToaNhaTrongIdc: async (axios, data) => {
        try {
            const rs = await axios.post('/web-ecms/danhmuc/toanha/search', data);
            return rs && rs.data ? rs.data.data : null;
        } catch (error) {
            const e = error && error.data ? error.data.message_detail : 'Lỗi hệ thống'
            throw new Error(e)
        }
    },
    getDanhMuc: async (axios, loaiDanhMuc, thamSo1, thamSo2) => {
        try {
            const rs = await axios.post('/web-ecms/idc/danh-muc/common', 
                { loaiDanhMuc: loaiDanhMuc, thamSo1: thamSo1, thamSo2: thamSo2 }
            );

            if (rs && rs.data && rs.data.data) {
                
                return rs.data.data.map((item) => ({
                    id: item.ID,
                    text: item.TEN,
                    ...item
                }))
            }
            return [];
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
}