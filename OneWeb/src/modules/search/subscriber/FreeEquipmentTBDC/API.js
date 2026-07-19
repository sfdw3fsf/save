export default{
    // BSS-38480_001
    css_dichvu_vt: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    // BSS-38480_002
    css_loaihinh_tb: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
    // BSS-38480_003
    lay_ds_kieu_ld: async (axios, data) => axios.post('/web-ccdv/ds_yeucau_khaosat/lay_ds_kieu_ld', data),
    // BSS-38480_005_1
    lay_danhba_theo_matb: async (axios, data) => axios.post('/web-hopdong/thongtin_tratruoc_dn/lay_danhba_theo_matb', data),
    // BSS-38480_005_2
    lay_ds_diachi_theo_dbtbid: async (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_diachi_theo_dbtbid', data),
    // BSS-38480_005_3
    lay_ds_db_cntt_theo_tbid: async (axios, data) => axios.post('/web-hopdong/khuyenmai-thaydoi-loaihinhtb/lay_ds_db_cntt_theo_tbid', data),
    // BSS-38480_005_4
    sp_ht_giaodien_donvi_theo_loaihd: async (axios, data) => axios.post('/web-hopdong/dichvu-ca/sp_ht_giaodien_donvi_theo_loaihd', data),
    sp_lay_ds_donvi_tramtc_banthietbi: async (axios, data) => axios.post('/web-hopdong/dichvu-ca/sp_lay_ds_donvi_tramtc_banthietbi', data),
    sp_lay_ds_donvi_theo_loaidv_banthietbi: async (axios, data) => axios.post('/web-hopdong/dichvu-ca/sp_lay_ds_donvi_theo_loaidv_banthietbi', data),
    // BSS-38480_005_5    
    sp_lay_ds_tien_khoanmuc: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/sp_lay_ds_tien_khoanmuc', data), // respon k du, 
    lay_khoanmuc_tt_theo_loaihd: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_khoanmuc_tt_theo_loaihd', data),
    // BSS-38480_005_6
    lay_tt_donvi_theo_thuebao: async (axios, data) => axios.get(`/web-tracuu/danhba/lay_tt_donvi_theo_thuebao?thuebaoId=${data.thuebao_id}`),
    // BSS-38480_006_1
    lay_ds_hopdong_theo_ma_gd: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_gd', data),
    // BSS-38480_006_2
    lay_ds_hopdong_tb_theo_hdkh_id: async (axios, data) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/lay_ds_hopdong_tb_theo_hdkh_id', data),
    // BSS-38480_006_3
    lay_ds_mua_tbi: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_mua_tbi', data),
    // BSS-38480_006_4 //TODO xem lai khao sat co bi trung BSS-38480_005_5
    sp_ht_ttct_thietbi_loaitbi: async (axios, data) => axios.post('/web-ccdv/ban-thiet-bi/sp_ht_ttct_thietbi_loaitbi', data),
    // BSS-38480_006_5
    lay_ds_gui_chitiet: async (axios, data) => axios.post('/web-ccdv/ban_thietbi_ttvt/lay_ds_gui_chitiet', data),
    // BSS-38484_014_001
    lay_ds_dbtb_theo_khid_v3: async (axios, data) => axios.post('/web-ccdv/ban_thietbi_ttvt/lay_ds_dbtb_theo_khid_v3', data),
    lay_goi_cuoc_ban_tbi: async (axios, data) => axios.post('/web-ccdv/ban_thietbi_ttvt/lay_goi_cuoc_ban_tbi', data),
    // BSS-38484_016
    layhuonggiao_tiepnhan: async (axios, data) => axios.post('/web-hopdong/dichvu-ca/layhuonggiao_tiepnhan', data),
    post_lay_ds_tien_kmtt : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_ds_tien_kmtt',data),

}