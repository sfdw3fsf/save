export default{    
    post_lay_danhmuc_lapmoi_tt_chung_v8 : async (axios, data) => axios.post('/web-danhba/lapdatmoi/lay_danhmuc_lapmoi_tt_chung_v8', data),
    getQuyTrinh: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_quytrinh_v2', data),    
    getDichVu: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    getLoaiHinh: (axios, data) => axios.post('/web-hopdong/danhmuc/loaihinh_thuebao', data),
    post_get_keys : async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys',data),
    post_lay_ds_dbkh_theo_khid: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/lay_ds_dbkh_theo_khid',data),
    sinh_magd: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd', data),
    getMaKH: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/sinh_makh', data),
    getDanhSachNguoiGV: (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc', data),

    getThongTin: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/lay_ds_phieu_giaoden', data),
    getDanhSachHDTB: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/lay_ds_phieu_giaoden', data),
    
    lay_ds_nhanvien_theo_phieu_id: (axios, data) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_ds_nhanvien_theo_phieu_id', data),

    lay_map_thamso: (axios, data) => axios.post('/web-quantri/thamso/map_id', data),
    
    getThongTinNhanVien: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/fn_lay_thongtin_nhanvien`,data),

    ///web-thicong/hoinghi_truyenhinh/lay_luong_thaotac
    lay_luong_thaotac: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_luong_thaotac`,data),
    ///lapdatmoi-csht/layhuongiao_tiepnhan_v3
    layhuongiao_tiepnhan_v3: (axios, data) => axios.post(`/web-hopdong/lapdatmoi-csht/layhuongiao_tiepnhan_v3`,data),

    //lapdatmoi-csht/sp_lay_ds_to_ql
    sp_lay_ds_to_ql: (axios) => axios.get('/web-hopdong/lapdatmoi-csht/sp_lay_ds_to_ql'),
    ///ds_yeucau_khaosat/lay_ds_kieu_ld
    lay_ds_kieu_ld: (axios, data) => axios.post(`/web-ccdv/ds_yeucau_khaosat/lay_ds_kieu_ld`,data),
    ///lapdatmoi-csht/sp_lay_ds_hd_csht_chuagiao
    sp_lay_ds_hd_csht_chuagiao: (axios, data) => axios.post(`/web-hopdong/lapdatmoi-csht/sp_lay_ds_hd_csht_chuagiao`,data),

    // /lapdatmoi-csht/sp_lay_donvi_theo_loaidv_diachi_ld
    sp_lay_donvi_theo_loaidv_diachi_ld: (axios, data) => axios.post(`/web-hopdong/lapdatmoi-csht/sp_lay_donvi_theo_loaidv_diachi_ld`,data),
//     web-thicong/thanhly-thuebao/sp_lay_ds_donvi_theo_loaidv
//  {
//   "loaidv_id": 2
//  }
    sp_lay_ds_donvi_theo_loaidv: (axios, data) => axios.post(`web-thicong/thanhly-thuebao/sp_lay_ds_donvi_theo_loaidv`,data),
    ///hoanthien-hs-muativi/sp_ht_kv_cskh_theo_diaban
    sp_ht_kv_cskh_theo_diaban: (axios, data) => axios.post(`/web-ccdv/hoanthien-hs-muativi/sp_ht_kv_cskh_theo_diaban`,data),
    ///lapdatmoi-csht/sp_lay_thongtin_kh_csht
    sp_lay_thongtin_kh_csht: (axios, data) => axios.post(`/web-hopdong/lapdatmoi-csht/sp_lay_thongtin_kh_csht`,data),
    //web-hopdong/thong-tin-kenh-trang/sinh_matb
    sinh_matb: (axios, data) => axios.post(`/web-hopdong/thong-tin-kenh-trang/sinh_matb`,data),
    //fn_ins_update_hd_datmoi_csht
    fn_ins_update_hd_datmoi_csht: (axios, data) => axios.post(`/web-hopdong/lapdatmoi-csht/fn_ins_update_hd_datmoi_csht`,data),    
    xoa_hdkh_v2: async (axios, data) => axios.post('/web-hopdong/lapdatmoi-csht/xoa_hdkd_v2',data),
    ///lapdatmoi-csht/fn_giaophieu_thicong_csht
    fn_giaophieu_thicong_csht: (axios, data) => axios.post(`/web-hopdong/lapdatmoi-csht/fn_giaophieu_thicong_csht`,data),    
}