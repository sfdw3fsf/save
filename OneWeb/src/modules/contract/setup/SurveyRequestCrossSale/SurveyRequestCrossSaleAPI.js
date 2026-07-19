export default {
    //"chuoi":"DOITUONG|DICHVU_VT|LOAIHINH_TB|LOAI_GT|NGANHNGHE|QUOCTICH"
    lay_danhmuc_lapmoi_tt_chung_v7: async (axios, chuoi) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/lay_danhmuc_lapmoi_tt_chung_v7',{
        chuoi:chuoi
    }),
    sp_lay_ds_doituong_theo_dieukien: async (axios) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_lay_ds_doituong_theo_dieukien',{}),
    

    lay_ds_tinh_thicong_combobox: async (axios) => axios.post('/web-hopdong/bss_group1/lay_ds_tinh_thicong_combobox',{}),
    
    sp_lay_ds_donvi_ldv: async (axios, loaidv_id) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_lay_ds_donvi_ldv',{
        loaidv_id:loaidv_id
    }),
    // lay_ds_thamso_md: async (axios, kieu_id) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md',{
    //     list_of_cols: "*",
    //     order: "",
    //     schema: "admin",
    //     table_name: "thamso_md",
    //     where: "kieu_id="+kieu_id
    // }),
    lay_ds_thamso_md: async (axios, kieu_id) => axios.post('/web-hopdong/laydulieu/sp_tt_thamso_md',{
        p_param: kieu_id,
        p_type:4
    }),

    
    lay_ds_kieu_ld: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD'),

    lay_ds_ks_bc: async (axios, data) => axios.post('/web-quantri/tylethulaoquahan/lay_ds_ks_bc', data),

    lay_ds_hopdong_tb_theo_hdtb_id: async (axios, hdtb_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_hopdong_tb_theo_hdtb_id', {
        hdtb_id:hdtb_id
    }),

    //fn_map_id: async (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),
    fn_tt_giaophieu: async (axios, hdtb_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieu', {
        param: hdtb_id,
        type: 6,
        param1: null
    }),
    

    lay_ds_hopdong_theo_ma_gd: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_gd', data),

    lay_ds_diachi_theo_hdkh: async (axios, hdKhId) => axios.get('/web-hopdong/hopdong/lay_ds_diachi_theo_hdkh?hdKhId='+hdKhId),

    lay_thongtin_khachhang_nhanquyen: async (axios, ma_kh) => axios.get('/web-hopdong/hopdong/lay_thongtin_khachhang_nhanquyen?maKh='+ma_kh),

    sp_lay_ds_donvi_thicong: async (axios, id, kieu, loaidv_id, kieudv_id) => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_ds_donvi_thicong',{
        kieu:kieu,
        id:id,
        loaidv_id:loaidv_id,
        kieudv_id:kieudv_id
    }),
    //https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=40328572&range=E457
    lay_ds_diachi_theo_khachhang: async (axios, khachHangId) => axios.get('/web-hopdong/hopdong/lay_ds_diachi_theo_khachhang?khachHangId='+khachHangId),
    
    lay_donvi_theo_loaidv_diachi_ld: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_donvi_theo_loaidv_diachi_ld',data),
    
    kiemtra_makh_mtt: async (axios, ma_kh, kieu, loaihd_id) => axios.post('/web-thicong/thicong/kiemtra_makh_mtt',{
        ma_kh: ma_kh,
        kieu:kieu,
        loaihd_id:loaihd_id
    }),

    lay_ds_diachi_bc_theo_hdtb_id: async (axios, hdtb_id) => axios.post('/web-tracuu/thuebaoims/lay_ds_diachi_bc_theo_hdtb_id', {
        in_hdtb_id:hdtb_id
    }),
    get_keys: async (axios, keyname) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',{
        keyname: keyname
    }),
    lay_magd: async (axios, loaihd_id) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd',{
        loaihd_id: loaihd_id
    }),
    //https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=620043524&range=E3927
    sp_layhuonggiao_tiepnhan: async (axios, loaihd_id, kieuld_id) => axios.post('/web-hopdong/goi-da-dichvu/sp_layhuonggiao_tiepnhan', {
        loaihd_id: loaihd_id,
        kieuld_id: kieuld_id
    }),
    //https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=40328572&range=E173
    fn_lay_khachhangid_thicong: async (axios, tinhthicong_id, khachhang_id) => axios.post('/web-hopdong/chuyendich-codinh/fn_lay_khachhangid_thicong', {
        p_khachhang_id: khachhang_id,
        p_tinhthicong_id: tinhthicong_id
    }),
    //UR2.1.001
    sp_capnhat_hopdong_ks: async (axios, data) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_capnhat_hopdong_ks', data),
    sp_themmoi_hopdong_ks: async (axios, data) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_themmoi_hopdong_ks', data),

    fn_ins_update_hd_datmoi_bancheo_v2: async (axios, data) => axios.post('/web-baohong/TiepNhanBaoHong/fn_ins_update_hd_datmoi_bancheo_v2', data),

    xoa_hdtb: async (axios, hdtb_id) => axios.post('/web-hopdong/khoiphucthanhly/xoa_hdtb', {
        hdtb_id:hdtb_id
    }),
    //Tham chiếu  UR2.1.007_057
    xoa_hdkh_v2: async (axios, hdkh_id, loaihd_id) => axios.post('/web-hopdong/khoiphucthanhly/xoa_hdkh_v2',{
        hdkh_id:hdkh_id,
        loaihd_id:loaihd_id
    }),

    sp_themmoi_hopdong_ks_v2: async (axios, data) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_themmoi_hopdong_ks_v2', data),

    layhuonggiao_tiepnhan_bancheo_v2: async (axios, tinhthicong_id, loaihd_id, kieuld_id) => axios.post('/web-bancheo/tracuu/layhuonggiao_tiepnhan_bancheo_v2', {
        tinhthicong_id: tinhthicong_id,
        vloaihd_id: loaihd_id,
        vkieuld_id: kieuld_id
    }),

    sp_lay_ds_makh_theo_sogt: async (axios, so_gt, hdkh_id) => axios.post('/web-hopdong/lapdatmoi/sp_lay_ds_makh_theo_sogt', {
        vso_gt: so_gt,
        vhdkh_id: hdkh_id
    }),

    get_key_bancheo: async (axios, tinhthicong_id, keyname) => axios.post('/web-bancheo/tracuu/get_key', {
        tinhthicong_id: tinhthicong_id,
        keyname: keyname
    }),

    fn_tao_dulieu_chuyenphieu_ks_bancheo: async (axios, data) => axios.post('/web-baohong/TiepNhanBaoHong/fn_tao_dulieu_chuyenphieu_ks_bancheo', data),

    lay_tt_khdn_theo_makh: async (axios, ma_kh) => axios.get(`web-bancheo/khdn/lay_tt_khdn_theo_makh?ma_kh=${ma_kh}`),

    post_lay_diachi_theo_diachi_id : async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_diachi_theo_diachi_id',data),
}