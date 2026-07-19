// [UR4.1.030] - Tra cứu phiếu giao đến báo hỏng
// docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=915077779&range=B373:F374
// WinUI.WinUIQLDHXLSuCo.frmTraCuuPhieuGiaoDen_BH,PTTB.exe
// #/incident/SearchIncidentTicket
// user:04054
// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=915077779&range=A590:E590
export default {
  getDmDichVu: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/sp_ht_dichvuvt_cbobox_lm`, data),
  getDmTrangThaiPhieu: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/sp_sp_ht_trangthai_ph_combobox`),
  getDmTrangThaiBaoHong: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/sp_ht_trangthai_baohong_combobox`),
  getDsDonVi: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/sp_ht_donvi_combobox_1`, data),
  getDsPhieuBaoHong: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/sp_lay_ds_phieu_giaoden_baohong_v2`, data),
  getDsLsPhieuBaoHong: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/lay_lichsu_hen_baohong`, data),
  getDsLsHenThiCong: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/lay_lichsu_hen_thicong`, data),
  lay_ds_danhba_tsl: (axios, data) => axios.get(`/web-hopdong/hopdong/lay_ds_danhba_tsl?thueBaoId=${data.thueBaoId}`, data),
  fn_ghilai_noidungtt: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/fn_ghilai_noidungtt`, data),
  lay_ds_thongtin_kythuat: (axios, data) => axios.post(`/web-baohong/TiepNhanBaoHong/lay_ds_thongtin_kythuat`, data),

  getDanhSachTienTrinhXuLy: (axios, hdtbId) => axios.get(`web-baohong/baohong/lay_ds_tientrinh_xuly_bh?baohong_id=${hdtbId}`),
}
