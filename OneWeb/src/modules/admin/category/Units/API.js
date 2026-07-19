export default{
    QLVT_DVI_TINH: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/QLVT_DVI_TINH', data,header),
    fn_xoa_dvi_tinh: async (axios, data,header) => axios.post('/web-quantri/donvitinh/fn_xoa_dvi_tinh', data,header),
    get_keys: async (axios, data,header) => axios.post('/web-quantri/donvitinh/get_keys', data,header),
    fn_ghilai_dvi_tinh: async (axios, data,header) => axios.post('/web-quantri/donvitinh/fn_ghilai_dvi_tinh', data,header),
}
    