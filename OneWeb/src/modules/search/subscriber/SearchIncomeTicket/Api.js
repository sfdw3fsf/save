// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=0&range=A575
// WinUI.WinUITraCuu.frmTraCuuPhieuGiaoDen,PTTB.exe
// UR2.8.029
// UR2.3.007: WinUI.WinUITraCuu.frmTraCuuPhieuGiaoDen
export default {
  getDmLoaiHds: (axios, data) => axios.post('/web-tracuu/TraCuuPhieuGiaoDen/layds_loaihd', data),
  getDmLDichVuVTs: (axios, data) => axios.post('/web-hopdong/TraCuuPhieuGiaoDen/lay_ds_dichvu_vt', data),
  getDmKieuYeuCaus: (axios, data) => axios.post('/web-tracuu/TraCuuPhieuGiaoDen/layds_kieuld_theo_dichvuvt_loaihd', data),
  getDmTTPhieus: (axios, data) => axios.post('/web-tracuu/TraCuuPhieuGiaoDen/lay_ds_trangthai_phieu', data),
  getDsTTPhieus: (axios, data) => axios.post('/web-tracuu/TraCuuPhieuGiaoDen/layds_trangthai_phieu', data),
  getDmKieuKhoaMo: (axios, data) => axios.post('/web-tracuu/TraCuuPhieuGiaoDen/layds_kieu_khoamo', data),
  getDmChieuKhoaMay: (axios, data) => axios.post('/web-tracuu/TraCuuPhieuGiaoDen/layds_chieu_khoamay', data),
  getDmTrangThaiHD: (axios, data) => axios.post('/web-hopdong/TraCuuPhieuGiaoDen/lay_ds_trangthai_hd', data),
  // https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=508537646&range=C2469:G2471
  getDanhSachHenLap: (axios, data) => axios.post('/web-tracuu/TraCuuPhieuGiaoDen/lay_ds_henlap_hdtb_id', data),
  getDanhSachHdtbTSL: (axios, data) => axios.post('/web-hopdong/TraCuuPhieuGiaoDen/LAY_DS_HDTB_TSL_THEO_HDTB_ID', data),
  lay_ds_hdtb_tsl_theo_hdtb_id: (axios, data) => axios.get(`/web-hopdong/hopdong/lay_ds_hdtb_tsl_theo_hdtb_id?hdtb_id=${data.vhdtb_id}`, data),
  Search: (axios, data) => axios.post('/web-tracuu/TraCuuPhieuGiaoDen/lay_ds_phieu_giaoden_nvth', data),
  kiemtra_dvgt_khoamomay: (axios, data) => axios.post('/web-hopdong/TraCuuPhieuGiaoDen/kiemtra_dvgt_khoamomay', data),
  // popup dia chi lap dat
  sp_lay_tt_show_diachi_ld: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_show_diachi_ld', data),
  lay_ds_hopdong_thuebao_mgwan: (axios, data) => axios.get(`/web-hopdong/hopdong/lay_ds_hopdong_thuebao_mgwan?hdtbId=${data.hdtbId}`, data),
  lay_ds_hopdong_thuebao_tls: (axios, data) => axios.get(`/web-hopdong/hopdong/lay_ds_hopdong_thuebao_tls?hdtbId=${data.hdtbId}`, data),
  sp_lay_donvi_thicong_hdtb: async (axios, data) => axios.post('/web-ccdv/HoanThanhKhaoSat/sp_lay_donvi_thicong_hdtb', data),
  sp_lay_diachi_ld: async (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_lay_diachi_ld', data),
  // sp_lay_tt_show_diachi_ld: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_show_diachi_ld', data),
  fn_lay_dienthoai_lienhe: async (axios, data) => axios.post('/web-ccdv/HoanThanhKhaoSat/fn_lay_dienthoai_lienhe', data),
  // map_id: (axios, data) => axios.post(`/web-hopdong/thanhly/thanh_ly_map_id`, data),
  fn_tt_tocdo_kenh: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_kenh`, data)
}
