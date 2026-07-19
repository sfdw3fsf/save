export default{
    sp_tt_thietbi_thuebao_dd_theo_matb: (axios, data) => axios.post('/web-quantri/chuyendoi4g/sp_tt_thietbi_thuebao_dd_theo_matb', data),
    sp_ds_thietbi_thuebao_dd: (axios,data) => axios.post('/web-quantri/chuyendoi4g/sp_ds_thietbi_thuebao_dd',data),
    sp_hoanthanh_thietbi_thuebao_dd: (axios, data) => axios.post('/web-quantri/chuyendoi4g/sp_hoanthanh_thietbi_thuebao_dd', data),
    sp_capnhat_thietbi_thuebao_dd: (axios, data) => axios.post('/web-quantri/chuyendoi4g/sp_capnhat_thietbi_thuebao_dd', data),
    sp_lay_kieu_tbi_thietbi_thuebao_dd: (axios) => axios.post('web-quantri/chuyendoi4g/sp_lay_kieu_tbi_thietbi_thuebao_dd')

}
