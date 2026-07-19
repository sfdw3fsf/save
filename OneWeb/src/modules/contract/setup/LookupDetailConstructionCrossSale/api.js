// https://api-onebss-dev.vnptit3.vn/proxy/swagger-ui.html?urls.primaryName=web-hopdong#/UR2.12.006/lay_dm_tocdo_mgwanUsingPOST

export default {

  /*
  { param: hdtb_thicong, type: 6 }
   */
  fn_tt_hd_khachhang: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_khachhang', data),

  /*
  {
    "type": 6,
    "param": hdtb_ban,
    "param1": tinh_thicong trong dk where,
    "tinhthicong_id": tinh_thicong
  }
   */
  fn_tt_hd_bancheo: (axios, data) => axios.post('/web-bancheo/tracuu/fn_tt_hd_bancheo', data),

  lay_ds_tinh: async (axios) => axios.get('/web-tracuu/cntt_tskt/ds_tinh'),
  lay_dm_tocdo_kenh: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH'),
  lay_dm_dichvu_vt: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),

  /*
    Click "tìm kiếm"
    ma_tb co 1 bien dong:hung3776598
    ma_tb co nhieu bien dong:anh33277
   */
  lay_ds_hd_theo_ma_tb: (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_hd_theo_ma_tb', data),

  // vphanvung_id, ma_gd, ma_tb
  giaophieu_ph: (axios, data) => axios.post("web-thicong/giaoviec-vip/select/giaophieu_ph", data),

  bancheo_lay_ds_hd_theo_ma_tb: (axios, data) => axios.post('/web-bancheo/tracuu/lay_ds_hd_theo_ma_tb', data),

  /*
  {
    "hdtb_ban": 0,
    "phanvung_id": 0
  }
   */
  bancheo_lay_ndthoai_ttkd: (axios, data) => axios.post('/web-tracuu/tracuu-thicong/bancheo_lay_ndthoai_ttkd', data),

  /*
  {
    "hdtb_ban": 0,
    "phanvung_id": 0,
    "tinh_id": 0
  }
   */
  //bancheo_lay_thongtin_hd_thicong: (axios, data) => axios.post('/web-tracuu/tracuu-thicong/bancheo_lay_thongtin_hd_thicong', data),
  bancheo_lay_thongtin_hd_thicong: (axios, data) => axios.post('/web-bancheo/tracuu/bancheo_lay_thongtin_hd_thicong', data),

  /*
  grid "danh sách thuê bao"
  {
    "hdkh_id": 0,
  }
   */
  lay_ds_hd_tb_theo_hdkh_id: (axios, data) => axios.post("/web-tracuu/tracuu-thicong/lay_ds_hd_tb_theo_hdkh_id", data),
  bancheo_lay_ds_hd_tb_theo_hdkh_id: (axios, data) => axios.post("/web-bancheo/tracuu/lay_ds_hd_tb_theo_hdkh_id_v2", data),

  /*
  grid "chi tiết điều hành thi công"
  {
    "hdtb_id": 0,
    "phanvung_id": 0 // this.$root.token.getPhanVungID()
  }
   */
  sp_bancheo_lay_danhsach_phieuth: (axios, data) => axios.post("/web-bancheo/tracuu/sp_bancheo_lay_danhsach_phieuth", data),

  /*
   {
     "hdtb_id": 0,
     "phanvung_id": 0 // this.$root.token.getPhanVungID()
   }
   */
  sp_lay_tocdo_tsl: (axios, data) => axios.post("/web-tracuu/tracuu-thicong/sp_lay_tocdo_tsl", data),

  // danh mục trạng thái hợp đồng
  sp_lay_trangthai_hd: (axios, data) => axios.post("/web-tracuu/tracuu-thicong/sp_lay_trangthai_hd", data),

  // danh mục tốc độ kênh
  lay_dm_tocdo_mgwan: (axios, data) => axios.post("/web-tracuu/TraCuuPhieuTCBC/lay_dm_tocdo_mgwan", data),

  /*
  {
    "hdtb_id": 0
  }
   */
  lay_huongkn_moi_cu: (axios, data) => axios.post("/web-tracuu/TraCuuPhieuTCBC/lay_huongkn_moi_cu", data),

  /*
  {
    "hdtb_id": 0
  }
   */
  lay_tocdo_mgwan_metronet: (axios, data) => axios.post("/web-tracuu/TraCuuPhieuTCBC/lay_tocdo_mgwan_metronet", data),

  // double click trên grid "chi tiết điều hành thi công", popup "danh sách nhân viên thực hiện"
  /*
  {
    "baohong_id": 0,
    "chitietcc_id": 0,
    "giaophieu_vt_id": 0,
    "hdtb_id": 0,
    "metaClass": {},
    "nhanvien_id": 0,
    "phieu_bh_id": 0,
    "phieu_id": 0,
    "phieu_kn_id": 0
  }
   */
  sp_lay_ds_nhanvien_thuchien: (axios, data) => axios.post("/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_nhanvien_thuchien", data),
  fn_lay_tt_frmtracuuphieuth_load: (axios, data) => axios.post("/web-tracuu/tracuu-thicong/fn_lay_tt_frmtracuuphieuth_load", data),

  /*
  {
    "phieu_id": 3110167697,
    "phieu_bh_id": 0,
    "phieu_kn_id": 0,
    "huonggiao": 13431,
    "nhanvien_id": 4348,
    "tt_nd_tinh_id": 26,
    "ts_tc_bancheo": 0
  }
   */
  sp_frmnhanvienthbc_load: (axios, data) => axios.post("/web-tracuu/chitiet-thicong-bancheo/sp_frmnhanvienthbc_load", data),


  // popup Port
  lay_ds_port_hdtb: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/lay_ds_port_hdtb', data),

  // popup xuất phiếu
  // TODO

  // popup vật tư
  sp_lay_kqxl_huonggiao_thuhoi: async (axios, data) => axios.post('/web-tracuu/tracuubaohong/sp_lay_kqxl_huonggiao_thuhoi', data),
  sp_lay_ds_vattu_hdtb: async (axios, data) => axios.post('/web-tracuu/tracuubaohong/sp_lay_ds_vattu_hdtb', data),
  lay_ds_vattu_baohong: async (axios, data) => axios.post('/web-tracuu/tracuubaohong/lay_ds_vattu_baohong', data),

  /*
  popup địa chỉ lắp đặt
  {
    "hdtb_id":this.phieu_yc.hdtb_id,
    "dichvu_id":this.listbox.dichvu_vt.value
  }
   */
  sp_lay_tt_show_diachi_ld: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_show_diachi_ld', data),

  //popup gói đa dịch vụ
  sp_lay_ds_tb_dk_goi: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_tb_dk_goi', data),

  //popup tồn phiếu
  sp_lay_ds_ton_phieu_hist: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_ton_phieu_hist', data),
};
