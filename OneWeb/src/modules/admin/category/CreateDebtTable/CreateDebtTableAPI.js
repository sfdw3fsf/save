export default {
    sp_lay_ds_taobangno_khoitao: async (axios) => axios.post('/web-quantri/thuno/sp_lay_ds_taobangno_khoitao',{}),
    sp_ghilai_taobang_no: async (axios, data) => axios.post('/web-quantri/thuno/sp_ghilai_taobang_no', data),
    
}