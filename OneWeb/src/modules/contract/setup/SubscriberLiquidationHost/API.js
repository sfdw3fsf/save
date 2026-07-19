export default {
  // load form
  sp_ht_dichvuvt_combobox: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_ht_dichvuvt_combobox', data),
  sp_lay_ds_quytrinh_v2: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_quytrinh_v2', data),
  sp_lay_ds_nhanvien_theo_donvi_id: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_nhanvien_theo_donvi_id', data),
  sp_lay_huonggiao_theo_quytrinh: (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_lay_huonggiao_theo_quytrinh', data),
  sp_lay_ds_thaotac: (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_luong_thaotac', data),
  lay_nhom_kqxl_theo_huonggiao: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/lay_nhom_kqxl_theo_huonggiao', data),
  sp_lay_ds_donvi_theo_loaidv: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_donvi_theo_loaidv', data),
  lay_ds_nguyennhan_nocuoc: (axios, request) => axios.post('/web-thicong/thanhly-thuebao/lay_ds_nguyennhan_nocuoc', request),
  // nut lay tt
  sp_lay_ds_thuebao_thanhly: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_thuebao_thanhly', data),
  fn_lay_thongtin_lh_lapdat: (axios, data) => axios.get('/web-thicong/thanhly-thuebao/fn_lay_thongtin_lh_lapdat/' + data),
  sp_ht_phieu_tra: (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_ht_phieu_tra', data),
  lay_ds_hdtb_mgwan_theo_hdtb_id: (axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_hdtb_mgwan_theo_hdtb_id', data),
  lay_ds_hdtb_tsl_theo_hdtb_id: (axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_hdtb_tsl_theo_hdtb_id', data),
  phailam: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/phailam', data),
  lay_thongtin_host: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/lay_thongtin_host', data),
  sp_ht_thongtin_rowindex_extend_cancelled: (axios, request) => axios.post('/web-thicong/thanhly-thuebao/sp_ht_thongtin_rowindex_extend_cancelled', request),
  sp_lay_ds_lydohuy_hdtb: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_lydohuy_hdtb', data),
  lay_ds_nhanvien_theo_phieu_id: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/lay_ds_nhanvien_theo_phieu_id', data),
  sp_lay_thietbi_sudung: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_thietbi_sudung', data),
  lay_ds_hopdong_huonggiao_theo_dv: (axios, request) => axios.post('/web-thicong/thanhly-thuebao/lay_ds_hopdong_huonggiao_theo_dv', request),
  // nut cap nhat
  sp_capnhat_thanhly: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_capnhat_thanhly', data),
  // UR2.4.038_042
  UPDATE_ND_THUCHIEN: async (axios, request) => axios.post('/web-hopdong/thaydoi_thongso_megawan/update_nd_thuchien', request),
  // nut kich hoat
  fn_kichhoat_thanhly: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/fn_kichhoat_thanhly', data),
  fn_kichhoat_thanhly_v2: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/fn_kichhoat_thanhly_v2', data),

  // duybn
  sp_tt_thamso_md: (axios, data) => axios.post('web-hopdong/laydulieu/sp_tt_thamso_md', data),
  lay_ds_phieuvt_hdbh: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/lay_ds_phieuvt_hdbh', data),
  fn_huykichhoat_thanhly: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/fn_huykichhoat_thanhly', data),
  fn_huykichhoat_thanhly_test: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/fn_huykichhoat_thanhly_test', data),
  thoaitra_xacminh_hdtb: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/thoaitra_xacminh_hdtb', data),
  fn_nut_giaonet: (axios, request) => axios.post('/web-thicong/thanhly-thuebao/fn_nut_giaonet', request),
  sp_hoanthanh_thanhly: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_hoanthanh_thanhly', data),
  fn_hoancong_thanhly: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/fn_hoancong_thanhly', data),
  fn_hoanthien_thanhly: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/fn_hoanthien_thanhly', data),
  // DELETE_LYDOHUY_HDTB
  DELETE_LYDOHUY_HDTB: async (axios, request) => axios.post('/web-hopdong/thaydoiloaihinhtb/delete_lydohuy_hdtb', request),
  // UR2.4.090.015
  fn_tt_donvi: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi', data),
  fn_tt_giaophieu_nv: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieu_nv', data),
  fn_tt_giaophieu: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieu', data),
  fn_tt_hd_thuebao: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao', data),
  // UR2.6.042_008_003
  SP_LAY_DULIEU_THAOTAC_CONTROL_THEO_THAOTAC_ID: async (axios, request) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_lay_dulieu_thaotac_control_theo_thaotac_id', request),
  // LAY_HUONGGIAO_THEO_LUONG_ID
  LAY_HUONGGIAO_THEO_LUONG_ID: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id', request),

  // IVAN
  lay_dulieu_cho_api: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/lay_dulieu_cho_api', data),
  lay_dulieu_cho_api_theo_command: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/lay_dulieu_cho_api_theo_command', data),
  ivan_xac_thuc: async (axios, request) => axios.post('/tichhop/ivan/a109', request),
  ivan_thanhly_dv: async (axios, request) => axios.post('/tichhop/ivan/a103', request),
  ivan_dongbo: async (axios, request) => axios.post('/tichhop/ivan/a113', request),

  // them ban cheo
  hoancong_thicong_2: (axios, data) => axios.post(`/web-ccdv/hoanthienhschuyenquyen/hoancong_thicong_2?hdtb_id=${data.hdtb_id}`, data),
  lay_thongtin_tinh: (axios, data) => axios.get(`/web-hopdong/hopdong/lay_thongtin_tinh?tinh_id=${data.tinh_id}`, data),
  hoancong_thicong_v3: (axios, data) => axios.post('/web-bancheo/tracuu/hoancong_thicong_v3', data),
  sp_lay_thongtin_bancheo: (axios, data) => axios.post('/web-thicong/danhmuc/sp_lay_thongtin_bancheo', data),
  sp_them_db_bancheo_v2: (axios, data) => axios.post('/web-bancheo/tracuu/sp_them_db_bancheo_v2', data),
  log_action: (axios, data) => axios.post('/web-thicong/hoancongmegawan/log_action', data),

  get_app_config: (axios, data) => axios.post('web-hopdong/hopdong/get_app_config', data),
  lay_ds_quytrinh_newlife: async (axios, data) => axios.post('/web-thicong/new-life/lay_ds_quytrinh_newlife', data),
  // lay_ds_hopdong_huonggiao_chuquan: async (axios, data) => axios.post('/web-thicong/new-life/lay_ds_hopdong_huonggiao_chuquan', data),
  sp_lay_ds_thuebao_thanhly_newlife: async (axios, data) => axios.post('/web-thicong/new-life/sp_lay_ds_thuebao_thanhly', data),
}
