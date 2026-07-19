export default {
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

    getDetail: async (axios, id) => axios.post(`/web-ecms/danhmuc/idc/get-by-id?id=${id}`),

    save: async (axios, data) => axios.post('/web-ecms/danhmuc/idc/create-or-update', data),
    
    delete: async (axios, id) => axios.post(`/web-ecms/danhmuc/idc/delete?id=${id}`),

}