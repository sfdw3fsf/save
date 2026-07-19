export default {
    lay_ds_kieu_ld: async (axios, data) => axios.post('/web-ccdv/ds_yeucau_khaosat/lay_ds_kieu_ld',data),
    sp_lay_dichvu_vt_theo_nd: async (axios, data) => axios.post('/web-ccdv/ds_yeucau_khaosat/sp_lay_dichvu_vt_theo_nd',data),
    lay_ds_lhtb_theo_dv: async (axios, data) => axios.post('/web-ccdv/ds_yeucau_khaosat/lay_ds_lhtb_theo_dv',data),
    CSS_LOAI_HD: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD'),
    fn_lay_ds_thamso_md_mats: async (axios, data) => axios.post('/web-ccdv/ds_yeucau_khaosat/fn_lay_ds_thamso_md_mats',data),
    sp_lay_ds_hopdong_yc_khaosat: async (axios, data) => axios.post('/web-ccdv/ds_yeucau_khaosat/sp_lay_ds_hopdong_yc_khaosat',data),
    sp_capnhat_huyphieu_tn: async (axios, data) => axios.post('/web-ccdv/ds_yeucau_khaosat/sp_capnhat_huyphieu_tn',data),
}