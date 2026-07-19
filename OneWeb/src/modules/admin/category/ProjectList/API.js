export default{
    ds_chudtu: async (axios, data,header) => axios.get('/web-quantri/toanha/ds_chudtu', data,header),
    lay_tt_donvi: async (axios, data,header) => axios.post('/web-quantri/toanha/lay_tt_donvi', data,header),
    ds_ht_hoptac: async (axios, data,header) => axios.get('/web-quantri/toanha/ds_ht_hoptac', data,header),
    ds_trangthai_da: async (axios, data,header) => axios.get('/web-quantri/toanha/ds_trangthai_da', data,header),
    lay_tt_duan: async (axios, data,header) => axios.post('/web-quantri/toanha/lay_tt_duan', data,header),
    capnhat_tt_duan: async (axios, data,header) => axios.post('/web-quantri/toanha/capnhat_tt_duan', data,header),

}
    