export default{
  lay_ds_tb_cungdoicap_theo_khid : (axios,data) => axios.post('/web-bancheo/tracuu/lay_ds_tb_cungdoicap_theo_khid', data),
  check_map_id: (axios,data) => axios.post('/web-hopdong/thongtin_tratruoc_dn/map_id', data),
  fn_tt_loaihinh_tb: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb', data),
}
