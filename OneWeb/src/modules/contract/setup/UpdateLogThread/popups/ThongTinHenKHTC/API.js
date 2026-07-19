export default {
    sp_lay_ds_hdtb_theo_hdkh: async (axios, hdkh_id) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_lay_ds_hdtb_theo_hdkh',{
        hdkh_id: hdkh_id
    }),

    sp_lay_ds_hdtb_hen: async (axios, hdkh_id) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_lay_ds_hdtb_hen',{
        hdkh_id: hdkh_id
    }),

    xoa_hdtb_hen: async (axios, rid) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/xoa_hdtb_hen',{
        rid: rid
    }),

    sp_tt_thamso_md: async (axios) => axios.post('/web-hopdong/laydulieu/sp_tt_thamso_md',{"p_param": "", "p_type":1}),

    sp_lay_nhanvien_theo_manv_loainv: async (axios, ma_nv, loainv_id) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_lay_nhanvien_theo_manv_loainv',{
        ma_nv:ma_nv,
        loainv_id:loainv_id
    }),

    lay_ts_macdinh_dv: async (axios, donvi_id, ma_ts) => axios.get('/web-quantri/danhmuc/lay_ts_macdinh_dv/'+donvi_id+'/'+ma_ts),

    lay_nhanvien_id_theo_hdkh_id: async (axios, hdkh_id) => axios.post('/web-hopdong/laydulieu/fn_tt_hd_khachhang', {"p_param": hdkh_id, "p_type":1}),

    layso_ngay_hen: async (axios, loaiphieu) => axios.post('/web-thicong/ktvt-gphone/layso_ngay_hen',{
        loaiphieu: loaiphieu
    }),

    sp_ins_hdtb_hen: async (axios, js_hdtb_hen, kieu) => axios.post('/web-thicong/ktvt-gphone/sp_ins_hdtb_hen',{
        js_hdtb_hen: js_hdtb_hen,
        kieu: kieu
    }),
    get_keys: async (axios, keyname) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',{
        keyname: keyname
    }),
    sendSMS: async (axios, sdt, noidung) => axios.post('/tichhop/sms/send', {
        msisdn: sdt,
        content : noidung
    }),


}