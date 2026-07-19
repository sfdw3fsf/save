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
  LAY_DS_LINHVUC: (axios, data) => axios.post(`/tichhop/a2p/GET_LABEL_TYPE`, data),
  sp_load_dulieu_form_tao_brn: (axios, data) => axios.get(`/web-thicong/kichhoat-brandname/sp_load_dulieu_form_tao_brn?vhdtb_id=` + data),
  sp_lay_thongtin_dangky_brandname: (axios, data) => axios.get(`/web-thicong/kichhoat-brandname/sp_lay_thongtin_dangky_brandname?vhdtb_id=` + data),
  LAY_DS_NHAN: (axios, data) => axios.post(`/tichhop/a2p/GET_LABEL`, data),
  fn_update_thongtin_brn_tao_brn: (axios, data) => axios.post(`/web-thicong/kichhoat-brandname/fn_update_thongtin_brn_tao_brn`, data),
  fn_update_trangthai_hoancong_brn: (axios, data) => axios.post(`/web-thicong/kichhoat-brandname/fn_update_trangthai_hoancong_brn`, data),
  fn_lay_thongtin_gia_brn: (axios, data) => axios.post(`/web-quantri/sms-brandname/fn_lay_thongtin_gia_brn`, data),
  UPDATEBRANDNAME: (axios, data) => axios.post(`/tichhop/a2p/UPDATEBRANDNAME`, data),
  TAOBRANDNAME: (axios, data) => axios.post(`/tichhop/a2p/CREATE_LABEL`, data),
  fn_khaibao_api: async (axios, hdtb_id) =>
    axios.post(`/web-quantri/sms-brandname/fn_khaibao_api`, {
      vhdtb_id: hdtb_id
    }),
  // BSS-87351_1.2 - LoadThongTin();

  sp_lay_danhmuc_khoitao_acc_khdl: async (axios) => axios.post(`/web-quantri/sms-brandname/sp_lay_danhmuc_khoitao_acc_khdl`, {}),
  // BSS-87351_1.3 - LoadThongTinKhachHang();
  sp_lay_thongtin_khoitao_acc_khdl: async (axios, hdtb_id) =>
    axios.post('/web-quantri/sms-brandname/sp_lay_thongtin_khoitao_acc_khdl', {
      vhdtb_id: hdtb_id
    }),
  // BSS-87351_5.1 - btnNhapMoi_Click
  fn_capnhat_khachhang_daily_brn: async (axios, data) =>
    axios.post('/web-quantri/sms-brandname/fn_capnhat_khachhang_daily_brn', {
      vhdtb_id: data.hdtb_id,
      vagent_id: data.agent_id,
      vadser_id: data.adser_id,
      vcontract_id: data.contract_id,
      vusername: data.username,
      vpassword: data.password,
      vjson: data.json
    }),
  // BSS-80223_5.2 - btnCapNhat_Click
  fn_capnhat_thongtin_khachhang_daily_brn: async (axios, data) =>
    axios.post('/web-quantri/sms-brandname/fn_capnhat_thongtin_khachhang_daily_brn', {
      vhdtb_id: data.hdtb_id,
      vjson: data.json
    }),
  //don hang tra truoc
  sp_load_danhmuc_dhtt_smsbrn: (axios, data) => axios.post('/web-thicong/dvcntt/sp_load_danhmuc_dhtt_smsbrn', data),
  sp_lay_dulieu_khachhang_dhtt_smsbrn: (axios,data) => axios.post('/web-thicong/dvcntt/sp_lay_dulieu_khachhang_dhtt_smsbrn', data),
  sp_lay_ds_dhtt_smsbrn: (axios, data) => axios.post('/web-thicong/dvcntt/sp_lay_ds_dhtt_smsbrn', data),
  sp_update_donhang_tratruoc_smsbrn: (axios, data) => axios.post('/web-thicong/dvcntt/sp_update_donhang_tratruoc_smsbrn', data),
  LAY_DS_LINHVUC_BRN: (axios, data) => axios.post(`/tichhop/brandname/LAY_DS_LINHVUC`, data),
  LAY_DS_LINHVUC_BRN_A2P: (axios, data) => axios.post(`/tichhop/a2p/get_label_type`, data),
  TAO_DHTT: (axios, data) => axios.post(`/tichhop/brandname/TAO_DHTT`, data),
  UPDATE_DHTT: (axios, data) => axios.post(`/tichhop/brandname/UPDATE_DHTT`, data),
  TAO_A2P_DHTT: (axios, data) => axios.post(`/tichhop/a2p/create_package`, data),
  UPDATE_A2P_DHTT: (axios, data) => axios.post(`/tichhop/a2p/update_package`, data),
  
  lay_tt_giao_vip_giamsat_it360: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_tt_giao_vip_giamsat_it360`, data),
}
