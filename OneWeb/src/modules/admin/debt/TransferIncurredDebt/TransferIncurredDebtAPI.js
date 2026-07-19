export default{
    post_sp_lay_chukyno_theo_kyhd: async (axios, data) => axios.post('/web-quantri/chuyennops/sp_lay_chukyno_theo_kyhd', data),
    post_sp_lay_ds_tieuchi: async (axios, data) => axios.post('/web-quantri/chuyennops/sp_lay_ds_tieuchi', data),
    post_fn_lay_ds_ketqua_donno_ps: async (axios, data) => axios.post('/web-quantri/chuyennops/fn_lay_ds_ketqua_donno_ps', data),
    post_fn_chuyeno_phatsinh: async (axios, data) => axios.post('/web-quantri/chuyennops/fn_chuyeno_phatsinh', data),
    sp_lay_ds_dot_no: async (axios, data) => axios.post('/web-quantri/chuyennops/sp_lay_ds_dot_no', data),
    fn_lay_tt_chuyenno_ps: async (axios, data) => axios.post('/web-quantri/chuyennops/fn_lay_tt_chuyenno_ps', data),
  }