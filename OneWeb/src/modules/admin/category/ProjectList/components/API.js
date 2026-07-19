export default{
    lay_loai_tt_duan: async (axios, data,header) => axios.get('/web-quantri/toanha/lay_loai_tt_duan', data,header),
    lay_tt_tientrinh_duan: async (axios, data,header) => axios.post('/web-quantri/toanha/lay_tt_tientrinh_duan', data,header),
    tientrinh_duan: async (axios, data,header) => axios.post('/web-quantri/toanha/tientrinh_duan', data,header),

}
    