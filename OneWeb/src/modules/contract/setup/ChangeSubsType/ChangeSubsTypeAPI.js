//Link API URD UR2.1.035:https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=620043524&range=C2170
export default {
    
    sp_lay_tienkhoanmuc_theo_loaihd: async (axios, loaihd_id) => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_tienkhoanmuc_theo_loaihd',{
        loaihd_id:loaihd_id
    }),

    ds_thamso_md: async (axios, kieu) => axios.get('/web-quantri/danhmuc/ds_thamso_md/'+kieu),

    lay_danhmuc_lapmoi_tt_chung_v8: async (axios, chuoi) => axios.post('/web-danhba/lapdatmoi/lay_danhmuc_lapmoi_tt_chung_v8',{
        vchuoi:chuoi
    }),

    lay_ds_loaihinh_tb: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),

    lay_danhmuc_cdlh_tt_chung: async (axios, chuoi) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/lay_danhmuc_cdlh_tt_chung',{
        vchuoi:chuoi
    }),

    ///

    lay_ds_kieu_ld_lhtb: async (axios, loaihd_id, loaitb_id, tinhkhac) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_kieu_ld_lhtb',{
        vloaihd_id: loaihd_id,
        vloaitb_id: loaitb_id,
        vtinhkhac: tinhkhac
    }),
    
    lay_khuyenmai_hdtb: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_khuyenmai_hdtb', data),

    //UR2.1.033_017: CSS.LAY_DS_HOPDONG_THEO_MA_GD https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=620043524&range=E1085
    lay_ds_hopdong_theo_ma_gd: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_gd', data),

    //bỏ
   // fn_map_id: async (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),

    // Map table db_thuebao, type thứ tự cột map tương ứng
    fn_map_db_thuebao: async (axios, thuebao_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao', {
        param: thuebao_id, 
        type: type
    }),

    fn_map_goicuoc_dd: async (axios, goicuoc_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_goicuoc_dd', {
        param: goicuoc_id, 
        type: type
    }),

    fn_map_tocdo_adsl: async (axios, tocdo_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl', {
        param: tocdo_id, 
        type: type
    }),

    fn_map_db_adsl: async (axios, thuebao_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_adsl', {
        param: thuebao_id, 
        type: type
    }),

    fn_map_hdtb_dd: async (axios, hdtb_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_dd', {
        param: hdtb_id, 
        type: type
    }),

    fn_map_muccuoc_tb: async (axios, muccuoctb_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_muccuoc_tb', {
        param: muccuoctb_id, 
        type: type
    }),

    fn_map_khuyenmai: async (axios, khuyenmai_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_khuyenmai', {
        param: khuyenmai_id, 
        type: type
    }),

    

    


    
    
    
    

    fn_lay_tt_ht_hdkh_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_lay_tt_ht_hdkh_cdlhtb', {
        p_ds_para:ds_para
    }),

    xoa_hopdong_thuebao: async (axios, hdtbId) => axios.post('/web-hopdong/hopdong/xoa_hopdong_thuebao?hdtbId='+hdtbId,{}),

    fn_lay_tt_ht_hdtb_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_lay_tt_ht_hdtb_cdlhtb', {
        p_ds_para:ds_para
    }),

    fn_lay_tt_hienthittmorong_db_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_lay_tt_hienthittmorong_db_cdlhtb', {
        p_ds_para:ds_para
    }),

    lay_ds_hopdong_theo_ma_tb: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb', data),

    sp_lay_danhba_theo_matb: async (axios, data) => axios.post('/web-hopdong/tracuulichsutb/sp_lay_danhba_theo_matb', data),

    sp_thaydoi_tocdo_internet_hienthitt_danhba: async (axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_thaydoi_tocdo_internet_hienthitt_danhba', {
        parameters:data
    }),
    
    kiemtra_thuebao_lap_hopdong_khac: async (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac', data),

    lay_ds_chitiet_km_theo_tbid: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_chitiet_km_theo_tbid', {
        vkieu: 2,
        vthuebao_id: thuebao_id
    }),

    sp_lay_ds_khuvuc_theo_thuebaoid: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_ds_khuvuc_theo_thuebaoid', {
        vthuebao_id: thuebao_id
    }),

    lay_ds_diachi_theo_dbtbid: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_diachi_theo_dbtbid', {
        thuebao_id: thuebao_id
    }),

    lay_ds_isdn_theo_thuebao_id: async (axios, thuebao_id) => axios.post('/web-hopdong/thanhly/lay_ds_isdn_theo_thuebao_id', {
        thuebao_id: thuebao_id
    }),

    lay_ds_tien_dc_conlai: async (axios, thuebao_id, loaitb_id, thang_thoai) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_tien_dc_conlai', {
        vthuebao_id: thuebao_id,
        vloaitb_id: loaitb_id,
        vthang_thoai: thang_thoai
    }),

    lay_ds_mucuoc_tb_v2: async (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2', data),

    lay_tien_khoanmuc_ld: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_tien_khoanmuc_ld', data),

    sp_ht_lhtb_moi_combobox: async (axios, kieuld_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_ht_lhtb_moi_combobox', {
        vkieuld_id: kieuld_id
    }),
    
    fn_lay_tt_hienthittmorong_hd_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_lay_tt_hienthittmorong_hd_cdlhtb', {
        p_ds_para:ds_para
    }),

    sp_lay_ctkm_loai_tbi: async (axios, ds_ctkm_id, loaitbi_id) => axios.post('/web-ccdv/ban-thiet-bi/sp_lay_ctkm_loai_tbi', {
        ds_ctkm_id: ds_ctkm_id,
        loaitbi_id: loaitbi_id
    }),

    sp_lay_ds_tragop_thietbi: async (axios, ds_ctkm_id, loaitbi_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_ds_tragop_thietbi', {
        p_ds_ctkm_id: ds_ctkm_id,
        p_loaitbi_id: loaitbi_id
    }),

    kiemtra_thoihan_khuyenmai: async (axios, khuyenmai_id, ngay_yc) => axios.post('/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_thoihan_khuyenmai', {
        khuyenmai_id: khuyenmai_id,
        ngay_yc:ngay_yc
    }),

    // Cập nhật
    fn_kt_va_lay_tt_capnhat_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_kt_va_lay_tt_capnhat_cdlhtb', {
        p_ds_para:ds_para
    }),
    

    // Thêm thuê bao
    fn_ktdl_themtb_cdlh: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_ktdl_themtb_cdlh', {
        p_ds_para:ds_para
    }),
    

    // 
    fn_tt_taodulieu_hdkh_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_tt_taodulieu_hdkh_cdlhtb', {
        p_ds_para:ds_para
    }),
    
    fn_tt_taodulieu_hdtb_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_tt_taodulieu_hdtb_cdlhtb', {
        p_ds_para:ds_para
    }),

    get_keys: async (axios, keyname) => axios.post('/web-hopdong/goi-da-dichvu/get_keys', {
        keyname: keyname,
        numblocksize: 1,
        numretry:10
    }),

    sp_ht_danhmuc_dv_tdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_ht_danhmuc_dv_tdlhtb', {
        p_ds_para:ds_para
    }),

    lay_ds_db_cd_theo_tbid: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid', {
        thuebao_id:thuebao_id
    }),

    ur2_1_035_038_temp: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/ur2_1_035_038_temp', {
        thuebao_id:thuebao_id
    }),

    sp_lay_donvi_theo_loaidv_diachi_ld_v1: async (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_donvi_theo_loaidv_diachi_ld_v1', data),

    lay_ds_danhba_tsl: async (axios, thuebao_id) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_tsl?thueBaoId='+thuebao_id),

    fn_ins_update_hd_cdlh: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_ins_update_hd_cdlh', {
        p_ds_para:ds_para
    }),

    lay_tt_fs_kem_ftth_v2: async (axios, loaihd_id) => axios.post('/web-hopdong/lapdatmoi/lay_tt_fs_kem_ftth_v2', {
        vloaihd_id:loaihd_id
    }),

    kiemtra_thanhtoan_datmoi_lh: async (axios, hdkh_id) => axios.get('/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_thanhtoan_datmoi_lh?hdkh_id='+hdkh_id),

    xoa_hdkh_v2: async (axios, hdkh_id, loaihd_id) => axios.post('/web-hopdong/khoiphucthanhly/xoa_hdkh_v2', {
        hdkh_id:hdkh_id,
        loaihd_id:loaihd_id
    }),

    fn_themtb_cdlh: async (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_themtb_cdlh', data),

    lay_ds_db_ims_theo_tbid: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_ims_theo_tbid', {
        thuebao_id:thuebao_id
    }),

    sp_lay_tocdo_adsl: async (axios, loaitb_id) => axios.post('/web-hopdong/dichvu-ca/sp_lay_tocdo_adsl', {
        loaitb_id: loaitb_id
    }),

    sp_ht_tocdo_adsl_combobox_byloaitb: async (axios, loaitb_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_ht_tocdo_adsl_combobox_byloaitb', {
        p_loaitb_id: loaitb_id
    }),

    lay_tocdo_thuc_adsl_theo_tocdo_id: async (axios, tocdo_id) => axios.post('/web-hopdong/TocDoMucCuoc/lay_tocdo_thuc_adsl_theo_tocdo_id', {
        tocdo_id: tocdo_id
    }),

    fn_tt_taodulieu_hdtb_adsl_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_tt_taodulieu_hdtb_adsl_cdlhtb', {
        p_ds_para: ds_para
    }),


    kiemtra_dky_family_dichuyen: async (axios, hdtb_id, matb_tn) => axios.post('/web-hopdong/thaydoiloaihinhtb/kiemtra_dky_family_dichuyen', {
        vhdtb_id: hdtb_id,
        vmatb_tn: matb_tn
    }),

    lay_goivasc_goi_dadv: async (axios, goi_id, tocdo_id, muccuoc_id) => axios.post('/web-hopdong/chuyendich/lay_goivasc_goi_dadv', {
        goi_id: goi_id,
        tocdo_id: tocdo_id,
        muccuoc_id: muccuoc_id
    }),

    sp_themmoi_hopdongtb_goida_dv_mytv: async (axios, huonggiao_id, js_hdtbgoi) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_themmoi_hopdongtb_goida_dv_mytv', {
        p_huonggiao_id: huonggiao_id,
        p_js_hdtbgoi: js_hdtbgoi
    }),

    lay_ds_dbtb_theo_tbid: async (axios, thuebao_id) => axios.post('/web-hopdong/phattrienthuebao/lay_ds_dbtb_theo_tbid', {
        thuebao_id: thuebao_id
    }),

    sp_themmoi_hopdongtb_goida_dv_ftth: async (axios, huonggiao_id, js_hdtb, js_hdtbgoi) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_themmoi_hopdongtb_goida_dv_ftth', {
        p_huonggiao_id: huonggiao_id,
        p_js_hdtb: js_hdtb,
        p_js_hdtbgoi:js_hdtbgoi
    }),

    fn_lay_tt_taodl_hdtb_dkygoi_mytv: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_lay_tt_taodl_hdtb_dkygoi_mytv', {
        p_ds_para: ds_para
    }),

    sp_lay_cuoctk_cuoctc: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/sp_lay_cuoctk_cuoctc', data),

    fn_lay_text_huongketnoi_v2: async (axios, huong_kn) => axios.post('/web-hopdong/khoiphucthanhly/fn_lay_text_huongketnoi_v2', {
        huong_kn:huong_kn
    }),

    fn_tt_taodulieu_hdtb_mgwan_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_tt_taodulieu_hdtb_mgwan_cdlhtb', {
        p_ds_para: ds_para
    }),

    fn_lay_tt_hienthittdiachi_tsl_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_lay_tt_hienthittdiachi_tsl_cdlhtb', {
        p_ds_para: ds_para
    }),

    kiemtra_quyen_nd: async (axios, quyen_id) => axios.post('/web-hopdong/lapdatmoi/kiemtra_quyen_nd', {
        quyen_id:quyen_id
    }),

    tudong_gan_hodan_thuebao: async (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/tudong_gan_hodan_thuebao',data),
    tudong_gan_cap_thuebao: async (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/tudong_gan_cap_thuebao',data),

    lay_ds_goicuoc_dd: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_GOICUOC_DD'),

    lay_tt_tb_dd: async (axios, vds_tb_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_tt_tb_dd',{
        vds_tb_id: vds_tb_id
    }),
    //CCBS
    ts_laphd_thanhly_loai_tb_tt: async (axios, so_tb) => axios.post('/ccbs/executor/ts_laphd_thanhly_loai_tb_tt',{
        so_tb: so_tb
    }),
    
    ts_laphd_thanhly: async (axios, data) => axios.post('/ccbs/executor/ts_laphd_thanhly', data),

    thongtin_nguoidung: async (axios) => axios.post('/quantri/user/thongtin_nguoidung2', {}),

    sp_capnhat_status_hdtb: async (axios, hdtb_id, status) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_capnhat_status_hdtb',{
        p_hdtb_id: hdtb_id,
        p_status:status
    }),

    update_hdtb_dd: async (axios, hdtb_id, ma_hd_neo, ghichu) => axios.post('/web-hopdong/thaydoiloaihinhtb/update_hdtb_dd',{
        ghichu: ghichu,
        hdtb_id: hdtb_id,
        ma_hd_neo: ma_hd_neo
    }),

    sp_lay_thongtin_hdkh_tuvan: async (axios, hdkh_cha_id, kieu) => axios.post('/web-hopdong/lapdatmoi/sp_lay_thongtin_hdkh_tuvan',{
        hdkh_cha_id: hdkh_cha_id,
        kieu:kieu
    }),

    lay_thongtin_kh_theo_ma_kh: async (axios, ma_kh) => axios.post('/web-hopdong/thong-tin-kenh-trang/lay_thongtin_kh_theo_ma_kh',{
        vma_kh: ma_kh
    }),

    sp_kt_thoigian_datcoc: async (axios, data) => axios.post('web-hopdong/hopdong/sp_kt_thoigian_datcoc',data),

    get_app_config: async (axios, keyname) => axios.post('web-hopdong/hopdong/get_app_config',{
        keyname: keyname
    }),

    //CCBS
    ts_laphd_thanhly_danhsach_hopdong: async (axios) => axios.post('ccbs/executor/ts_laphd_thanhly_danhsach_hopdong',{}),
    ts_xoa_hd_thanhly: async (axios, ma_hd) => axios.post('ccbs/executor/ts_xoa_hd_thanhly ',{
        ma_hd:ma_hd
    }),
    get_fn_ktra_tocdo_dk_greennet: async (axios, data) => axios.get('/web-tracuu/hopdong/fn_ktra_tocdo_dk_greennet',data)
    
}

    