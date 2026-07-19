export default {
  CSS_DICHVU_VT: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/CSS_DICHVU_VT`, data),
  ht_tocdo_kenh_combobox: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/ht_tocdo_kenh_combobox`, data),
  ht_tvdonvi_giaophieu_net: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/ht_tvdonvi_giaophieu_net`, data),
  load_tt_tb_giaophieu_net: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/load_tt_tb_giaophieu_net`, data),
  lay_tocdo_giaophieu_net: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_tocdo_giaophieu_net`, data),
  lay_diachi_daucuoi_giaophieu_net: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_diachi_daucuoi_giaophieu_net`, data),
  lay_ds_hd_theo_ma_tb: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_ds_hd_theo_ma_tb`, data),
  lay_danhsach_phieu_net_con: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_danhsach_phieu_net_con`, data),
  fn_check_thongtin_donvi_giao_bancheo: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_check_thongtin_donvi_giao_bancheo`, data),
  fn_thuchien_giaophieu_frm_giaophieu_net_khdn: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_thuchien_giaophieu_frm_giaophieu_net_khdn`, data),
  fn_huygiao_frm_giaophieu_net: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_huygiao_frm_giaophieu_net`, data),
  lay_chitiet_huonggiao: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_chitiet_huonggiao`, data),
  LAY_DS_LINHVUC: (axios, data) => axios.post(`/tichhop/brandname/LAY_DS_LINHVUC`, data),
  sp_load_dulieu_form_tao_brn: (axios, data) => axios.get(`/web-thicong/kichhoat-brandname/sp_load_dulieu_form_tao_brn?vhdtb_id=` + data),
  sp_lay_thongtin_dangky_brandname: (axios, data) => axios.get(`/web-thicong/kichhoat-brandname/sp_lay_thongtin_dangky_brandname?vhdtb_id=` + data),

  LAY_DS_NHAN: (axios, data) => axios.post(`/tichhop/brandname/LAY_DS_NHAN`, data),
  fn_update_thongtin_brn_tao_brn: (axios, data) => axios.post(`/web-thicong/kichhoat-brandname/fn_update_thongtin_brn_tao_brn`, data),
  fn_update_trangthai_hoancong_brn: (axios, data) => axios.post(`/web-thicong/kichhoat-brandname/fn_update_trangthai_hoancong_brn`, data),
  fn_lay_thongtin_gia_brn: (axios, data) => axios.post(`/web-quantri/sms-brandname/fn_lay_thongtin_gia_brn`, data),
  UPDATEBRANDNAME: (axios, data) => axios.post(`/tichhop/brandname/UPDATEBRANDNAME`, data),
  TAOBRANDNAME: (axios, data) => axios.post(`/tichhop/brandname/TAOBRANDNAME`, data)
}
