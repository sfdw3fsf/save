// import axios from 'axios';

export default {
    getLoaiThietBi: async (axios) => axios.get('/web-ecms/danhmuc/loai-thongso-thietbi/get-loai-thiet-bi'),
    getTrangThaiVanHanh: async (axios) => axios.get('/web-ecms/danhmuc/loai-thongso-thietbi/get-trangthai-vanhanh'),
    getList: async (axios, params) => axios.get('/web-ecms/danhmuc/loai-thongso-thietbi/get'),
    save: async (axios, data) => axios.post('/web-ecms/danhmuc/loai-thongso-thietbi/create-or-update', data),
    delete: async (axios, id) => axios.post(`/web-ecms/danhmuc/loai-thongso-thietbi/delete?id=${id}`),
    import: async (axios, data) => axios.post('/web-ecms/danhmuc/loai-thongso-thietbi/upload-by-file', data),
    validate: async (axios, data) => axios.post('/web-ecms/danhmuc/loai-thongso-thietbi/validate-by-file', data),
}
