export default{
    ts_tracuu_ls_tb: async(axios, data) => axios.post('/ccbs/executor/ts_tracuu_ls_tb',data),
    ts_tracuu_laytt_tb_ts: async(axios, data) => axios.post('/ccbs/executor/ts_tracuu_laytt_tb_ts',data),
    ts_tracuu_ls_tb_tt: async(axios) => axios.post('/ccbs/danhMuc/ts_tracuu_ls_tb_tt')
}