export default {
  ks03_sp_lay_ds_doituong_theo_dieukien: (axios, data) =>
    axios.post(
      "web-hopdong/thaydoitocdoadsl/sp_lay_ds_doituong_theo_dieukien",
      data
    ),
  ks04_load_dm_doi_tuong: (axios, data) =>
    axios.post("web-bancheo/danhmuc/load_dm_doi_tuong", data),
  sp_ht_kieuld_loaitb: (axios, data) =>
    axios.post("web-bancheo/danhmuc/sp_ht_kieuld_loaitb", data),

  lay_thongtin_sp_tt_loai_kenh: (axios, data) =>
    axios.post("web-hopdong/laydulieu/sp_tt_loai_kenh", data),
  lay_thongtin_sp_tt_tocdo_kenh: (axios, data) =>
    axios.post("web-hopdong/laydulieu/sp_tt_tocdo_kenh", data),
  lay_thongtin_sp_tt_trangbi: (axios, data) =>
    axios.post("web-hopdong/laydulieu/sp_tt_trangbi", data),

  lay_thongtin_ds_mohinh_kn: (axios, data) =>
    axios.get("web-hopdong/hopdong/lay_thongtin_ds_mohinh_kn", data),
  lay_thongtin_ds_giaodien_kn: (axios, data) =>
    axios.get("web-hopdong/hopdong/lay_thongtin_ds_giaodien_kn", data),
  lay_thongtin_ds_kieu_nghiemthu: (axios, data) =>
    axios.get("web-hopdong/hopdong/lay_thongtin_ds_kieu_nghiemthu", data),

  lay_thongtin_table: (axios, data) =>
    axios.post("web-ccdv/khaibaoims/lay_thongtin_table", data),
  sp_ht_loai_kenh_combobox: (axios, data) =>
    axios.post("web-bancheo/danhmuc/sp_ht_loai_kenh_combobox", data),
  ks10_lay_ds_chuquan: (axios, data) =>
    axios.post("web-hopdong/TocDoMucCuoc/lay_ds_chuquan", data),
  ks11_load_dm_chu_quan: (axios, data) =>
    axios.post("web-bancheo/danhmuc/load_dm_chu_quan", data),
  sp_ht_tocdo_kenh_combobox: (axios, data) =>
    axios.post("web-bancheo/danhmuc/sp_ht_tocdo_kenh_combobox", data),
  sp_ht_trangbi_combobox: (axios, data) =>
    axios.post("web-bancheo/danhmuc/sp_ht_trangbi_combobox", data),
  sp_ht_loai_tb_lhtb_combobox: (axios, data) =>
    axios.post("web-bancheo/danhmuc/sp_ht_loai_tb_lhtb_combobox", data),
  sp_ht_mohinh_kn_combobox: (axios, data) =>
    axios.post("web-bancheo/danhmuc/sp_ht_mohinh_kn_combobox", data),
  sp_ht_giaodien_kn_combobox: (axios, data) =>
    axios.post("web-bancheo/danhmuc/sp_ht_giaodien_kn_combobox", data),

  lay_chuquan_macdinh_theo_donvi_bancheo: (axios, data) =>
    axios.get(
      "web-hopdong/hopdong/lay_chuquan_macdinh_theo_donvi_bancheo",
      data
    ),
  bancheo_kiemtra_dichvu_vnpti: (axios, data) =>
    axios.get(
      `web-hopdong/hopdong/bancheo_kiemtra_dichvu_vnpti?loaitb_id=${data.loaitb_id}&loaihd_id=${data.loaihd_id}&dichvuvt_id=${data.dichvuvt_id}`,
      data
    ),
  map_id: (axios, data) => axios.post("web-bancheo/tracuu/map_id", data),
  ks08_lay_ds_mucuoc_tb_v2: (axios, data) =>
    axios.post("web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2", data),
  ks09_lay_ds_mucuoc_tb_v2: (axios, data) =>
    axios.post("web-bancheo/tracuu/lay_ds_mucuoc_tb_v2", data),
  bancheo_kiemtra_thongsokt: (axios, data) =>
    axios.post("web-hopdong/hopdong/bancheo_kiemtra_thongsokt", data)
};
