export default{
  css_tocdo_kenh: async (axios) => axios.get("/web-quantri/danhmuc-chung/CSS_TOCDO_KENH"),
  sp_lay_ds_loai_tbi: async (axios, dichvuvt_id) => axios.post("/web-ccdv/hths-thaydoi-tocdo/sp_lay_ds_loai_tbi", {
    p_dichvuvt_id: dichvuvt_id
  }),
  cabman_loai_cap: async (axios) => axios.get("/web-quantri/danhmuc-chung/CABMAN_LOAI_CAP"),
  sp_ht_tatca_vetinh_combobox1: async (axios, data) => axios.post("/web-ccdv/khaibaotsl/sp_ht_tatca_vetinh_combobox1", data), // {"loaidv_id":5}
  sp_lay_ds_tramtc_thuebao: async (axios, data) => axios.post("/web-hopdong/thaydoi-thongsokythuat-tsl/sp_lay_ds_tramtc_thuebao", data),
  // {
  // "p_loai": 1 danh ba, 2 hop dong ,
  // "p_kieudv": 2,
  // "p_db_id": 274191
  // }
  map_id: async (axios, data) => axios.post("/web-ccdv/khaibaotsl/map_id", data),
  // {
  //   "dk": "string",
  //   "id_neo": "string",
  //   "phanvung_id": 0,
  //   "table": "string"
  //   }
  lay_ds_hopdong_theo_matb: async (axios, data) => axios.post("/web-hopdong/hopdong/lay_ds_hopdong_theo_matb", data),
  // { "maTb": this.txtMaTB, "loaiHdId": 2 ,"ttHdId": 1,"donViDlId": donviId, "dichVuVtId": this.dichvu_id, "loaiTbId": this.loaihinh_id}
  lay_danhba_theo_matb: async (axios, maTb) => axios.post("/web-hopdong/hopdong/lay_danhba_theo_matb",
    {"dichVuVtId": 9,
      "donViDlId": 0,
      "maTb": maTb}
  ),
  lay_ds_db_con_theo_tbid: async (axios, thuebaoId) => axios.post("/web-ccdv/hths-thaydoi-tocdo/lay_ds_db_con_theo_tbid/", {
    "in_dichvuvt_id": "9",
    "in_thuebao_id": thuebaoId
  }),
  lay_ds_hopdong_thuebao_tls: async (axios, hdtbId) => axios.get(`/web-hopdong/hopdong/lay_ds_hopdong_thuebao_tls?hdtbId=${hdtbId}`),
  css_dichvu_vt: async (axios, data) =>
    axios.get("/web-quantri/danhmuc-chung/CSS_DICHVU_VT"),
  css_loaihinh_tb: async (axios, data) =>
    axios.get("/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB"),
  get_keys: async (axios, data) => axios.post("web-ccdv/tien_trinh_bao_hong/get_keys", data),
  sp_insert_tdtt_db_tsl: async (axios, data) => axios.post("web-hopdong/thaydoi-thongsokythuat-tsl/sp_insert_tdtt_db_tsl", data),
  fn_capnhat_port_thaydoi_tskt_tsl: async (axios, data) => axios.post("web-hopdong/thaydoi-thongsokythuat-tsl/fn_capnhat_port_thaydoi_tskt_tsl", data),
  fn_tt_db_tsl: async (axios, data) => axios.post("web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_tsl", data),
  fn_tt_hdtb_tsl: async (axios, data) => axios.post("web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_tsl", data),
  fn_tt_hd_thuebao: async (axios, data) => axios.post("web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao", data),
  fn_tt_port: async (axios, data) => axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_port", data),
  fn_tt_dslam: async (axios, data) => axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_dslam", data),
  fn_capnhat_port_thaydoi_tskt_tsl_v2: async (axios, data) => axios.post("web-hopdong/thaydoi-thongsokythuat-tsl/fn_capnhat_port_thaydoi_tskt_tsl_v2", data),
}
