export default {
    sp_lay_ds_nguoidung_tinh: async (axios, nhanvien_id, quyen) => axios.get(`/web-baohong/TiepNhanBaoHong/sp_lay_ds_nguoidung_tinh?nhanVienID=${nhanvien_id}&quyen=${quyen}`),
    fn_lay_danh_muc: async (axios, ds_danhmuc_id) => axios.post('/web-ccdv/doisoat_it/fn_lay_danh_muc_doisoat_it',{
        p_ds_danhmuc_id:ds_danhmuc_id
    }),

    sp_layphieuoutbounddtv: async (axios, data) => axios.post('/web-quantri/khaosat-khachhang/sp_layphieuoutbounddtv', data),
    sp_multisrv_rs9_lapmoi_ob: async (axios, ma_nd) => axios.post('/web-quantri/khaosat-khachhang/sp_multisrv_rs9_lapmoi_ob', {
        vma_nd:ma_nd
    }),
    sp_multisrv_rs8_baohong_ob: async (axios, ma_nd) => axios.post('/web-quantri/khaosat-khachhang/sp_multisrv_rs8_baohong_ob', {
        vma_nd:ma_nd
    }),

    sp_laythongtin_oblm: async (axios, oblm_id) => axios.post('/web-quantri/khaosat-khachhang/sp_laythongtin_oblm', {
        voblm_id:oblm_id
    }),

    sp_capnhat_trangthai_phieu_lapmoiob: async (axios, data) => axios.post('/web-quantri/khaosat-khachhang/sp_capnhat_trangthai_phieu_lapmoiob', data),

    sp_lay_ds_hdtb_hen: async (axios, hdkh_id) => axios.post('/web-quantri/khaosat-khachhang/sp_lay_ds_hdtb_hen', {
        hdkd_id:hdkh_id
    }),
    
    sp_lay_ds_hdtb_hen: async (axios, hdkh_id) => axios.post('/web-quantri/khaosat-khachhang/sp_lay_ds_hdtb_hen', {
        hdkd_id:hdkh_id
    }),

    lay_tt_hdkh_theo_hdkh_id: async (axios, hdkh_id) => axios.post('/web-quantri/khaosat-khachhang/lay_tt_hdkh_theo_hdkh_id', {
        vhdkh_id:hdkh_id
    }),

    langoi_chuatraloi: async (axios, hdkh_id, baohong_id) => axios.post('/web-quantri/khaosat-khachhang/langoi_chuatraloi', {
        hdkh_id:hdkh_id,
        baohong_id:baohong_id
    }),
    giuphieu_ob_lm_taptrung: async (axios, dshdtb, ma_nd) => axios.post('/web-quantri/khaosat-khachhang/giuphieu_ob_lm_taptrung', {
        vdshdtb_id: dshdtb, 
        vma_nd:ma_nd
    }),

    sp_tb_traloi_theo_hdkh_id: async (axios, hdkh_id) => axios.post('/web-quantri/khaosat-khachhang/sp_tb_traloi_theo_hdkh_id', {
        vhdkh_id:hdkh_id
    }),

    lay_ds_cauhoi_theoloainv: async (axios, loainv_id, hinhthucks_id) => axios.post('web-quantri/khaosat-khachhang/lay_ds_cauhoi_theoloainv', {
        vloainv_id:loainv_id, 
        vhinhthucks_id: hinhthucks_id 
    }),
    
    sp_laychitietoutbound: async (axios, tbtl_id) => axios.post('/web-quantri/khaosat-khachhang/sp_laychitietoutbound', {
        vtbtl_id: tbtl_id
    }),
    
    sp_updateoutbound: async (axios, js_tb_traloi, js_ct_tbtraloi) => axios.post('/web-quantri/khaosat-khachhang/sp_updateoutbound', {
        js_tb_traloi: js_tb_traloi,
        js_ct_tbtraloi:js_ct_tbtraloi
    }),
    
    sp_capnhat_trangthai_phieu_lapmoiob: async (axios, data) => axios.post('/web-quantri/khaosat-khachhang/sp_capnhat_trangthai_phieu_lapmoiob', data),
    
    sp_delete_tb_traloi: async (axios, tbtl_id) => axios.post('/web-quantri/khaosat-khachhang/sp_delete_tb_traloi', {
        tbtl_id: tbtl_id
    }),
    
    sp_delete_ct_tbtraloi: async (axios, tbtl_id) => axios.post('/web-quantri/khaosat-khachhang/sp_delete_ct_tbtraloi', {
        tbtl_id: tbtl_id
    }),

    dieulai_ob_taptrung: async (axios, tbtl_id) => axios.post('/web-quantri/khaosat-khachhang/dieulai_ob_taptrung', {
        vtbtl_id: tbtl_id
    }),
    
    sp_hen_ks_lapmoi_taptrung: async (axios, data) => axios.post('/web-quantri/khaosat-khachhang/sp_hen_ks_lapmoi_taptrung', data),
     
    lay_tt_baohong_theo_baohong_id: async (axios, baohong_id) => axios.get(`/web-ccdv/tien_trinh_bao_hong/lay_tt_baohong_theo_baohong_id/${baohong_id}`),

    fn_lay_tocdo_loaihinh: async (axios, dichvuvt_id, ma_tb) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_lay_tocdo_loaihinh', {
        p_dichvuvt_id:dichvuvt_id, 
        p_ma_tb:ma_tb
    }),

    fn_lanhong_trong30ngay: async (axios, thuebao_id) => axios.post('/web-hopdong/khaosatkhachhangtaptrung/fn_lanhong_trong30ngay', {
        thuebao_id: thuebao_id
    }),
    
    sp_tb_traloi_theo_tbtl_id: async (axios, tbtl_id) => axios.get(`/web-ccdv/tien_trinh_bao_hong/sp_tb_traloi_theo_tbtl_id/${tbtl_id}`),
    
    sp_tb_traloi_theo_baohong_id: async (axios, baohong_id) => axios.get(`web-ccdv/tien_trinh_bao_hong/sp_tb_traloi_theo_baohong_id/${baohong_id}`),

    lay_lichsu_hen_baohong: async (axios, baohong_id) => axios.post('/web-tracuu/tracuubaohong/lay_lichsu_hen_baohong', {
        baohong_id:baohong_id
    }),
    
    sp_capnhat_trangthai_phieu_baohongob: async (axios, data) => axios.post('/web-hopdong/khaosatkhachhangtaptrung/sp_capnhat_trangthai_phieu_baohongob', data),
    
    sp_ct_tbtraloi: async (axios, tbtl_id) => axios.get(`/web-ccdv/tien_trinh_bao_hong/sp_ct_tbtraloi/${tbtl_id}`),

    sp_hen_ks_baohong_taptrung: async (axios, data) => axios.post('/web-hopdong/hopdong/sp_hen_ks_baohong_taptrung', data),
    
    get_keys: async (axios, keyname) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',{
        keyname: keyname
    }),
    sp_insert_ct_tbtraloi: async (axios, js_ct_tbtraloi) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_insert_ct_tbtraloi', {
        p_js_ct_tbtraloi:js_ct_tbtraloi
    }),
    sp_insert_tb_traloi: async (axios, js_tbtraloi) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_insert_tb_traloi', {
        p_js_tbtraloi:js_tbtraloi
    }),
    lay_ds_thamso_md_mats: async (axios, ma_ts) => axios.post('/web-ccdv/khaibaotsl/lay_ds_thamso_md_mats', {
        ma_ts:ma_ts
    }),

    capnhat_so_dt_khi_outbound: async (axios, thuebao_id, somay) => axios.post('/web-ccdv/tien_trinh_bao_hong/capnhat_so_dt_khi_outbound', {
        vthuebao_id: thuebao_id,
        vsomay: somay
    }),

    fn_kiemtradieuhonglai: async (axios, baohong_id, hdtb_id) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_kiemtradieuhonglai', {
        vbaohong_id: baohong_id,
        vhdtb_id: hdtb_id
    }),

    dexuat_bsc_khl_nvkt: async (axios, tbtl_id) => axios.post('/web-ccdv/tien_trinh_bao_hong/dexuat_bsc_khl_nvkt', {
        vtbtl_id:tbtl_id
    }),

    capnhat_phieu_dieulai_xn: async (axios, tbtl_id, nhanvien_id, donvi_id) => axios.post('/web-ccdv/tien_trinh_bao_hong/capnhat_phieu_dieulai_xn', {
        vtbtl_id:tbtl_id, 
        vnhanvien_id:nhanvien_id, 
        vdonvi_id:donvi_id 
    }),

    kiemtra_nhanvien_sua_khaosat: async (axios, tbtl_id, giaophieu_ks_id, kieu) => axios.post('/web-ccdv/tien_trinh_bao_hong/kiemtra_nhanvien_sua_khaosat', {
        vtbtl_id:tbtl_id,
        vgiaophieu_ks_id: giaophieu_ks_id,
        vkieu: kieu
    }),

    fn_quyen_sua_khaosat: async (axios, tbtl_id, giaophieu_ks_id, loaiphieu_id) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_quyen_sua_khaosat', {
        vtbtl_id:tbtl_id,
        vgiaophieu_ks_id: giaophieu_ks_id,
        vloai_phieu: loaiphieu_id
    }),

    sp_lay_tb_traloi_nd: async (axios, tbtl_id, ma_nd) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_lay_tb_traloi_nd', {
        vtbtl_id: tbtl_id,
        vma_nd: ma_nd
    }),
    
    sp_hen_ks_lapmoi_taptrung: async (axios, data) => axios.post('/web-quantri/khaosat-khachhang/sp_hen_ks_lapmoi_taptrung', data),
     
    lay_tt_baohong_theo_baohong_id: async (axios, baohong_id) => axios.get(`/web-ccdv/tien_trinh_bao_hong/lay_tt_baohong_theo_baohong_id/${baohong_id}`),

    fn_lay_tocdo_loaihinh: async (axios, dichvuvt_id, ma_tb) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_lay_tocdo_loaihinh', {
        p_dichvuvt_id:dichvuvt_id, 
        p_ma_tb:ma_tb
    }),

    fn_lanhong_trong30ngay: async (axios, thuebao_id) => axios.post('/web-hopdong/khaosatkhachhangtaptrung/fn_lanhong_trong30ngay', {
        thuebao_id: thuebao_id
    }),

    sp_lay_user_ipcc_theo_nguoidung_id: async (axios, nguoidung_id) => axios.get(`/web-tracuu/tracuu/sp_lay_user_ipcc_theo_nguoidung_id?nguoidung_id=${nguoidung_id}`),
    sp_capnhat_cuocgoi_ipcc: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/sp_capnhat_cuocgoi_ipcc`, input),
    sp_batdau_cuocgoi_ipcc: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/sp_batdau_cuocgoi_ipcc`, input),
    sp_ketthuc_cuocgoi_ipcc: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/sp_ketthuc_cuocgoi_ipcc`, input),
    sp_lay_ds_cuocgoi: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/lay_ds_cuocgoi`, input),
    changeAgentState: (axios, data) => axios.post('/tichhop/ipcc/changeAgentState', data),

    callOb: (axios, data) => axios.post('/tichhop/ipcc/callOb', data),
    
    
}
