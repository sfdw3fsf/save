export default {
    sp_lay_tienkhoanmuc_loaihd: (axios, data) => axios.get(`web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_TIENKHOANMUC_LOAIHD?P_LOAIHD_ID=${data.loaihd_id}`),
    sp_lay_ngaygio_hethong: (axios) => axios.get(`web-ccdv/tiepnhan_hdbc/sp_lay_ngaygio_hethong`),
    sp_lay_ds_phieu_bancham: (axios, data) => axios.post(`web-ccdv/tiepnhan_hdbc/lay_ds_phieu_bancham`, data),
    sp_thaotac_phieu_bancham: (axios, data) => axios.post(`web-ccdv/tiepnhan_hdbc/thaotac_phieu_bancham`, data),
    sp_kiemtra_hopdong_thaydoi_dt: (axios, data) => axios.get(`web-hopdong/thaydoithongtinkhuyenmai/kiemtra_hopdong_thaydoi_dt?vma_tb=${data.ma_tb}&vdichvuvt_id=${data.dichvuvt_id}&vloaihd_id=${data.loaihd_id}`),
    sp_lay_danhba_theo_matb: (axios, data) => axios.post(`web-hopdong/hopdong/lay_danhba_theo_matb`, data),
    sp_lay_ds_danhba_adsl: (axios, data) => axios.get(`web-hopdong/hopdong/lay_ds_danhba_adsl?thueBaoId=${data.thuebao_id}`),
    sp_lay_huonggiao_tiepnhan: (axios, data) => axios.post(`web-hopdong/hopdong/lay_huonggiao_tiepnhan`, data),
    sp_kiemtra_thuebao_lap_hopdong_khac: (axios, data) => axios.post(`web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac`, data),
    sp_kiemtra_danhba_khi_themmoi: (axios, data) => axios.post(`web-hopdong/hopdong/kiemtra_danhba_khi_themmoi`, data),

    sp_kiemtra_makh_matt_magd: (axios, data) => axios.post(`web-hopdong/hopdong/kiemtra_makh_matt_magd`, data),
    getKey: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/get_keys', data),
    post_sinh_magd_v2: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd_v2', data),
    post_sinh_magd: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd', data),
    sp_load_hdtb_dv: async (axios, data) => axios.post('/web-hopdong/dichvu-cntt-gtgt/sp_load_hdtbdv_giahancntt', data),
    sp_lay_ds_db_cntt_theo_tbid: async (axios, req) => axios.post('/web-hopdong/khuyenmai-thaydoi-loaihinhtb/lay_ds_db_cntt_theo_tbid', req),

    sp_themmoi_hopdong_tdtd_v1: async (axios, data) => axios.post('/web-ccdv/tiepnhan_hdbc/sp_themmoi_hopdong_tdtd_v1', data),
    sp_themmoi_hopdong_datcoc: async (axios, data) => axios.post('/web-hopdong/tiepnhan_hdbc/sp_themmoi_hopdong_datcoc', data),
    sp_capnhat_giaophieu_hs_v2: async (axios, data) => axios.post('/web-ccdv/tiepnhan_hdbc/sp_capnhat_giaophieu_hs_v2', data),

    sp_lay_ten_loaihd_theo_thuebao_id: async (axios, data) => axios.get(`/web-ccdv/tiepnhan_hdbc/sp_lay_ten_loaihd_theo_thuebao_id?thuebao_id=${data.thuebao_id}`),
    sp_tracuu_no_thuebao: async (axios, data) => axios.get(`/web-hopdong/hopdong/tracuu_no_thuebao?thueBaoId=${data.thuebao_id}`),
    sp_kiemtra_loaitb_id_db_thuebao: async (axios, data) => axios.post('/web-hopdong/datcoc-tratruoc/fn_kiemtra_loaitb_id_db_thuebao', data),
    fn_kiemtra_tb_datcoc: async (axios, data) => axios.post('/web-hopdong/datcoc-tratruoc/fn_kiemtra_tb_datcoc', data),
    fn_kiemtra_datcoc_tyle_km: async (axios, data) => axios.post('/web-hopdong/datcoc-tratruoc/kiemtra_datcoc_tyle_km', data),

    sp_xoa_hdpl_bancham: async (axios, data) => axios.post('/web-ccdv/tiepnhan_hdbc/xoa_hdpl_bancham', data),
    sp_lay_ds_thamso_md_mats: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_ds_thamso_md_mats', data),
    sp_kiemtra_dk_goidadv: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/kiemtra_dk_goidadv', data),

    fn_tt_db_khachhang: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_khachhang', data),
    fn_tt_muccuoc_tb: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_muccuoc_tb', data),
}