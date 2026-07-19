export default {
    getList: async (axios, thietBiId) => axios.post(`/web-ecms/idc/thietbi-hethong-khachhang/nghiep-vu-idc/${thietBiId}`),

    save: async (axios, data) => axios.post('/web-ecms/danhmuc/toanha/create-or-update', data),

    delete: async (axios, id) => axios.post(`/web-ecms/danhmuc/toanha/delete?id=${id}`),

    import: async (axios, data) => axios.post('/web-ecms/danhmuc/toanha/upload-by-file', data),

    validate: async (axios, data) => axios.post('/web-ecms/danhmuc/toanha/validate-by-file', data),

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
    }
}