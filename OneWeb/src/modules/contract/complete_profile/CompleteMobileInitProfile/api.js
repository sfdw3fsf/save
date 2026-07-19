export default{
    CSS_KIEU_HD: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_HD', data, header),
    CSS_KENHTHU: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_KENHTHU', data, header),
    CSS_LOAI_HD: (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD', data, header),
    CSS_LOAIHINH_TB: (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', data, header),
    lay_ds_quytrinh_v2: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_quytrinh_v2', data, header),
    sp_lay_huonggiao_theo_quytrinh: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_lay_huonggiao_theo_quytrinh', data, header),
    lay_luong_thaotac: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_luong_thaotac', data, header),
    lay_ct_thaotac_control: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ct_thaotac_control', data, header),
    lay_ds_hdtb_didong: async (axios, data,header) => axios.post('/web-tratruoc/tra-truoc/lay_ds_hdtb_didong', data, header),
    fn_frmdongbovina_hoanthien: async (axios, data,header) => axios.post('/web-tratruoc/tra-truoc/fn_frmdongbovina_hoanthien', data, header),
    sp_frmdongbovina_update_uploadanh: async (axios, data,header) => axios.post('/web-tratruoc/tra-truoc/sp_frmdongbovina_update_uploadanh', data, header),
    fn_frmdongbovina_capnhat: async (axios, data,header) => axios.post('/web-tratruoc/tra-truoc/fn_frmdongbovina_capnhat', data, header),
    fn_frmdongbovina_ghepma: async (axios, data,header) => axios.post('/web-tratruoc/tra-truoc/fn_frmdongbovina_ghepma', data, header),
    sp_frmdongbovina_lay_goidadv: async (axios, data,header) => axios.post('/web-tratruoc/tra-truoc/sp_frmdongbovina_lay_goidadv', data, header),
    lay_ds_goi_cv_vnp: async (axios, data,header) => axios.post('/web-hopdong/goi-da-dichvu/lay_ds_goi_cv_vnp', data, header),
    sp_frmdongbovina_tao_goidadv: async (axios, data,header) => axios.post('/web-tratruoc/tra-truoc/sp_frmdongbovina_tao_goidadv', data, header),
    capnhat_status_hopdong_thuebao: async (axios, data,header) => axios.post('/web-hopdong/hopdong/capnhat_status_hopdong_thuebao', data, header),
    update_hd_thuebao_status: async (axios, data,header) => axios.post('/web-hopdong/hopdong/update_hd_thuebao_status', data, header),
    capnhat_ngaykh_ngay_ht_v2: async (axios, data,header) => axios.post('/web-hopdong/CapNhatTraTruoc/capnhat_ngaykh_ngay_ht_v2', data, header),

    //#region CCBS
    ts_laphd_thanhly_gui_catmo: async (axios, data,header) => axios.post('/ccbs/executor/ts_laphd_thanhly_gui_catmo', data, header),
    ts_hoanthien_hd_thanhly_danhsach_hoanthanh: async (axios, data,header) => axios.post('/ccbs/executor/ts_hoanthien_hd_thanhly_danhsach_hoanthanh', data, header),
    ts_laphd_chuyenquyen_sudung_ds_thuebao: async (axios, data,header) => axios.post('/ccbs/executor/ts_laphd_chuyenquyen_sudung_ds_thuebao', data, header),
    ts_laphd_chuyenquyen_sudung_hoanthienhd: async (axios, data,header) => axios.post('/ccbs/pttb/ts_laphd_chuyenquyen_sudung_hoanthienhd', data, header),
    app_khoamo_ic_oc: async (axios, data,header) => axios.post('/web-hopdong/hopdong/fn_didong', { vtype: 11, vhdtb_id: data.hdtb_id , vinput: data}, header),
    dong_mo_dv: async (axios, data,header) => axios.post('/web-hopdong/hopdong/fn_didong', { vtype: 10, vhdtb_id: data.hdtb_id , vinput: data}, header),
    khoamo_ic_oc: async (axios, data,header) => axios.post('/web-hopdong/hopdong/fn_didong', { vtype: 9, vhdtb_id: data.hdtb_id , vinput: data}, header),
    ts_laphd_chuyentinh_laytt_tb: async (axios, data,header) => axios.post('/ccbs/executor/ts_laphd_chuyentinh_laytt_tb', data, header),
    ts_laphd_chuyentinh_hoanthanh: async (axios, data,header) => axios.post('/ccbs/executor/ts_laphd_chuyentinh_hoanthanh', data, header),
    ts_hoanthien_hd_hoamang_ds_tb: async (axios, data,header) => axios.post('/ccbs/executor/ts_hoanthien_hd_hoamang_ds_tb', data, header),
    ts_chuyentinh_thuebao_cts_query: async (axios, data,header) => axios.post('/ccbs/executor/ts_chuyentinh_thuebao_cts_query', data, header),
    ts_thongtin_dangky_goicuoc_tb: async (axios, data,header) => axios.post('/ccbs/executor/ts_thongtin_dangky_goicuoc_tb', data, header),
    ts_thongtin_danhmuc_goicuoc_dk_tb: async (axios, data,header) => axios.post('/ccbs/executor/ts_thongtin_danhmuc_goicuoc_dk_tb', data, header),
    ts_laphd_hoamangmoi_ds_loaianh: async (axios, data,header) => axios.post('/ccbs/executor/ts_laphd_hoamangmoi_ds_loaianh', data, header),
    ts_hoanthien_hd_ds_loaianh: async (axios, data,header) => axios.post('/ccbs/executor/ts_hoanthien_hd_ds_loaianh', data, header),
    //ts_hoanthien_hd_hoamang_hoanthanh: async (axios, data,header) => axios.post('/ccbs/executor/ts_hoanthien_hd_hoamang_hoanthanh', data, header),
    ts_hoanthien_hd_hoamang_hoanthanh: async (axios, data,header) => axios.post('/ccbs/pttb/ts_hoanthien_hd_hoamang_hoanthanh', data, header),
    ts_hthd_hoamang_hoanthanh: async (axios, data,header) => axios.post('/ccbs/pttb/ts_hthd_hoamang_hoanthanh', data, header),
    tt_3_dktt: async (axios, data,header) => axios.post('/prepaid/ts_dktt_pps', data, header),

    ts_thuphi_hoamangmoi_layds_tb_ld: async (axios, data,header) => axios.post('/ccbs/executor/ts_thuphi_hoamangmoi_layds_tb_ld', data, header),
    ts_thuphi_hoamangmoi_gui_cmdv_khoitao_ac: async (axios, data,header) => axios.post('/web-hopdong/hopdong/fn_didong', { vinput : data, vtype: 3, vhdtb_id: data.hdtb_id  }, header),
    ts_thuphi_hoamangmoi_gui_cmdv_khoitao_lai: async (axios, data,header) => axios.post('/web-hopdong/hopdong/fn_didong',{ vinput : data, vtype: 1, vhdtb_id: data.hdtb_id  }, header),
    ts_thuphi_hoamangmoi_gui_cmdv_khoitao_cmdv: async (axios, data,header) => axios.post('/web-hopdong/hopdong/fn_didong', { vinput : data, vtype: 2, vhdtb_id: data.hdtb_id  }, header),
    upload_img_dktt: async (axios, data,header) => axios.post('/ccbs/executor/upload_img_dktt', data, header),
    ts_upload_img_tt_khcn: async (axios, data,header) => axios.post('/ccbs/prepaid/ts_upload_img_tt_khcn', data, header),

    fn_tt_hd_thuebao: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao', data, header),
    fn_tt_hd_khachhang: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_khachhang', data, header),
    lay_tt_chuyenmang_hd_thuebao: async (axios, data,header) => axios.post('/web-hopdong/hopdong/lay_tt_chuyenmang_hd_thuebao', data, header),

    fn_tt_hd_thanhtoan: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thanhtoan', data, header), //{ type = 2, param = hdtt_id }
    fn_tt_ghep_matt: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_ghep_matt', data, header), //{ type = 1, param = ma_tt }
    fn_tt_quoctich: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_quoctich', data, header),

    fn_tt_loai_kh: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_kh', data, header), //{ type = 2, param = loaikh_id }
    fn_tt_loai_kh_neo: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_kh_neo', data, header), //{ type = 1, param = loaikh_id }
    fn_tt_hdtb_dd: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_dd', data, header),  //{ type = 1, param = hdtb_id }

    //#endregion



}


