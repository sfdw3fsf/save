export default{
    ADMIN_DONVI: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DONVI', data,header),
    QLVT_NHOM_VT: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/QLVT_NHOM_VT', data,header),
    sp_lay_ds_ccdv: async (axios, data,header) => axios.post('/web-quantri/ganvattu-chucdanh-donvi/sp_lay_ds_ccdv', data,header),
    sp_lay_ds_nhom_lvt: async (axios, data,header) => axios.post('/web-quantri/ganvattu-chucdanh-donvi/sp_lay_ds_nhom_lvt', data,header),
    sp_lay_ds_loai_vt: async (axios, data,header) => axios.post('/web-quantri/ganvattu-chucdanh-donvi/sp_lay_ds_loai_vt', data,header),
    ganall_ccdc: async (axios, data,header) => axios.post('/web-quantri/ganvattu-chucdanh-donvi/ganall_ccdc', data,header),

}
    