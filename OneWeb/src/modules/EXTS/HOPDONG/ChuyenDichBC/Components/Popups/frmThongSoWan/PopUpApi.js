export default {
  tinh_thicong: (axios, data) => axios.post(`/web-bancheo/danhmuc/tinh_thicong`, data),
  lay_ds_tinh_thicong_combobox: (axios, data) => axios.post(`/web-hopdong/bss_group1/lay_ds_tinh_thicong_combobox`, data),
  // frmThongSoWan
  // https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=177639690&range=F426:K426
  kiemtra_tocdo_internet_tt: (axios, data) => axios.post(`/web-bancheo/thaydoimegawan/kiemtra_tocdo_internet_tt`, data),
  bancheo_kiemtra_thongsokt: (axios, data) => axios.post(`/web-bancheo/thaydoimegawan/bancheo_kiemtra_thongsokt`, data),
  map_id_kieuld_id: (axios, data) => axios.post(`/web-hopdong/chuyen-dich-ban-cheo/map_id_kieuld_id`, data),
  sp_ds_phantach_tocdo: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_ds_phantach_tocdo?vkieu=${data.vkieu}`, data),
  sp_lay_mohinh_kn: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_lay_mohinh_kn`),
  sp_lay_doituong_tb: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_lay_doituong_tb?nhanvienId=${data.nhanvienId}`),
  sp_lay_doituong_tb_tc: (axios, data) => axios.post(`/web-bancheo/danhmuc/load_dm_doi_tuong`, data),
  sp_ds_phanloai_dv: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_ds_phanloai_dv`),
  sp_lay_ds_tocdo_kenh: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_lay_ds_tocdo_kenh`),
  sp_ht_tocdo_kenh_combobox: (axios, data) => axios.post(`/web-bancheo/danhmuc/sp_ht_tocdo_kenh_combobox`, data),
  sp_ht_loai_kenh_combobox: (axios, data) => axios.post(`/web-bancheo/danhmuc/sp_ht_loai_kenh_combobox`, data),
  ht_loai_tb_lhtb_combobox: (axios, data) => axios.post(`/web-bancheo/danhmuc/ht_loai_tb_lhtb_combobox`, data),
  sp_lay_giaodien_kn: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_lay_giaodien_kn`),
  sp_lay_kieu_duplex: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_lay_kieu_duplex`),
  sp_lay_cauhinh_dt: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_lay_cauhinh_dt`),
  sp_lay_kieu_nghiemthu: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_lay_kieu_nghiemthu`),
  sp_lay_loai_ip: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_lay_loai_ip?loaitbId=${data.loaitbId}`, data),
  sp_lay_kieu_kn: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_lay_kieu_kn`),
  sp_ds_chuquan: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_ds_chuquan?pcqtc=${data.pcqtc}`),
  sp_ds_chuquan2: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/sp_ds_chuquan?pcqtc=${data.pcqtc}&tinhthicong_id=${data.tinhthicong_id}`),
  load_dm_chu_quan: (axios, data) => axios.post(`/web-bancheo/danhmuc/load_dm_chu_quan`,data),
  get_ts_kt_tthdkhdn: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/get_ts_kt_tthdkhdn`),
  lay_ds_mucuoc_tb_v2: (axios, data) => axios.post(`/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2`, data),
  lay_ds_mucuoc_tb_v2_tinh: (axios, data) => axios.post(`/web-bancheo/tracuu/lay_ds_mucuoc_tb_v2`, data),
  sp_ht_kieuld_loaitb_bancheo: (axios, data) => axios.post(`/web-hopdong/thanhly-bancheo-chonlydohuy/sp_ht_kieuld_loaitb_bancheo`, data),
  sp_ht_kieuld_loaitb_bancheo2: (axios, data) => axios.post(`/web-bancheo/tracuu/sp_ht_kieuld_loaitb_bancheo`, data),
  lay_ds_kieu_ld_lhtb: (axios, data) => axios.post(`/web-hopdong/thaydoiloaihinhtb/lay_ds_kieu_ld_lhtb`, data),
  lay_ds_kieu_ld_lhtb_bc: (axios, data) => axios.post(`/web-bancheo/thaydoimegawan/lay_ds_kieu_ld_lhtb_bc`, data),
  sp_ht_loai_tb_lhtb_combobox: (axios, data) => axios.post(`/web-bancheo/danhmuc/sp_ht_loai_tb_lhtb_combobox`, data),
  lay_ds_loai_tb_megawan: (axios, data) => axios.post(`/web-bancheo/thaydoimegawan/lay_ds_loai_tb_megawan`, data),
  fn_tt_db_thuebao: (axios, data) => axios.post(`/web-bancheo/thaydoimegawan/fn_tt_db_thuebao`, data),
  load_dm_trang_bi_tc: (axios, data) => axios.post(`/web-bancheo/danhmuc/load_dm_trang_bi`, data),
  // end -frmThongSoWan
  // frmDsThueBaoWan
  lay_ds_tb_wan: (axios, data) => axios.post(`/web-bancheo/thaydoimegawan/lay_ds_tb_wan`, data),
  kt_thaydoi_thongtin_hangloat: (axios, data) => axios.post(`/web-bancheo/thaydoimegawan/kt_thaydoi_thongtin_hangloat`, data)
  // frmDsThueBaoWan
}
