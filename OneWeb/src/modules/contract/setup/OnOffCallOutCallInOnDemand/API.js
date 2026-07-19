export default{
    gphone_reset_huy_lydo: async (axios, data) => axios.post('/ccbs/executor/gphone_reset_huy_lydo', data),
    ts_tracuu_laytt_tb: async (axios, data) => axios.post('/ccbs/executor/ts_tracuu_laytt_tb', data),
    ts_tracuu_laytt_tb_dv: async (axios, data) => axios.post('/ccbs/executor/ts_tracuu_laytt_tb_dv', data),
    gphone_reset_huy_ls_tb: async (axios, data) => axios.post('/ccbs/gphone/gphone_reset_huy_ls_tb', data),
    ts_tracuu_laytt_ls_3g: async (axios, data) => axios.post('/ccbs/executor/ts_tracuu_laytt_ls_3g', data),
    khoamo_ic_oc: async (axios, data) => axios.post('/web-hopdong/hopdong/fn_didong', {vinput:data, vtype:9, vhdtb_id: data.hdtb_id})
}
