export default {
    lay_ds_dichvu_vt: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),

    lay_ds_kieu_ld: async (axios, loaitb_id, loaihd_id) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_kieu_ld',{
        loaihd_id:loaihd_id,
        loaitb_id:loaitb_id
    }),
    lay_ds_hopdong_theo_ma_gd: async (axios, ma_gd, loaihd_id, tthd_id, donvi_dl_id) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd',{
        in_loaihd_id: loaihd_id,
        in_ma_gd: ma_gd,
        in_tthd_id: tthd_id,
        in_donvi_dl_id: donvi_dl_id
    }),
    ht_goi_dadv_datatable: async (axios) => axios.post('/web-hopdong/lapdatmoi/ht_goi_dadv_datatable',{}),
    fn_lay_tt_ds_thuebao_dk_goi_dadvlm: async (axios, ds_para) => axios.post('/web-hopdong/lapdatmoi/fn_lay_tt_ds_thuebao_dk_goi_dadvlm',{
        ds_para:ds_para
    }),
    sp_lay_danhba_theo_matb: async (axios, ma_tb, dichvuvt_id, donvi_dl_id) => axios.post('/web-hopdong/tracuulichsutb/sp_lay_danhba_theo_matb', {
        dichvuvt_id: dichvuvt_id,
        ma_tb: ma_tb,
        donvi_dl_id: donvi_dl_id
    }),
    fn_themhdthuebao_dk_goidadvlm: async (axios, ds_para) => axios.post('/web-hopdong/lapdatmoi/fn_themhdthuebao_dk_goidadvlm',{
        ds_para:ds_para
    }),
    get_keys: async (axios, keyname) => axios.post('/web-hopdong/goi-da-dichvu/get_keys', {
        keyname: keyname,
        numblocksize: 1,
        numretry:10
    }),
    
    fn_xoahdtb_dk_goidadvlm: async (axios, ds_para) => axios.post('/web-hopdong/lapdatmoi/fn_xoahdtb_dk_goidadvlm',{
        ds_para:ds_para
    }),
    
    fn_themtbngoaikh_dk_goidadvlm: async (axios, ds_para) => axios.post('/web-hopdong/lapdatmoi/fn_themtbngoaikh_dk_goidadvlm',{
        ds_para:ds_para
    }),

    
}