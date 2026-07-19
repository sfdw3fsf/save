// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=698575146&range=C716
// WinUI.WinUIDieuHanhThiCong.frmGiaoPhieu_Ton
export default {
  getDsNhomTon: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/QLTN_NHOM_TON`),
  sp_ds_giaophieu_ton_load: (axios, data) => axios.post(`/web-tracuu/tracuu_chitiet_thicong/sp_ds_giaophieu_ton_load`, data),
  sp_ghilai_giaophieu_ton: (axios, data) => axios.post(`/web-tracuu/tracuu_chitiet_thicong/sp_ghilai_giaophieu_ton`, data),
  getDsLyDoTon: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_cbo_lydoton_tc`, data),
  getDsPhieuTon: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_ht_grid_nguyennhan_ton`, data),
  updatePhieuTon: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_an_capnhat_giaophieu_ton`, data),
  xoaPhieuTon: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_an_xoa_giaophieu_ton`, data),
  thongtin_nguoidung: (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2', data),
  // Phieu tra -UR2.1.001_045
  // https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=A142
  getDsPhieuTT: (axios, data) => axios.post(`/web-ccdv/phieu_thanhtoan/lay_ds_phieutt`, data),
  xoaPhieuTT: (axios, data) => axios.post(`/web-ccdv/tiepnhan_yc_ks_lapdat/sp_ds_phieutt_xoaphieu_tt`, data),
  lay_thongtin_gachvi_theo_matt: (axios, data) => axios.post(`/web-ccdv/phieu_thanhtoan/lay_thongtin_gachvi_theo_matt`, data),
  // Print
  get_hoadon_vt01: (axios, data) => axios.post(`/web-ccdv/phieu_thanhtoan/get_hoadon_vt01`, data),
  lay_tttb_theo_phieutt_id: (axios, data) => axios.post(`/web-ccdv/phieu_thanhtoan/lay_tttb_theo_phieutt_id`, data),
  kiemtra_xoa_phieutt_v2: (axios, data) => axios.post(`/web-ccdv/phieu_thanhtoan/kiemtra_xoa_phieutt_v2`, data),
  capnhat_status_hopdong_thuebao: (axios, data) => axios.post(`/web-hopdong/hopdong/capnhat_status_hopdong_thuebao`, data),
  thongtin: (axios, data) => axios.get('/web-quantri/nguoidung/thongtin', data),
  //
  // CCBS
  ts_thuphi_hoamangmoi_layds_tb_ld: (axios, data) => axios.post('/ccbs/executor/ts_thuphi_hoamangmoi_layds_tb_ld', data),
  ts_laphd_chuyenquyen_sudung_cacphieutra: (axios, data) => axios.post('/ccbs/executor/ts_laphd_chuyenquyen_sudung_cacphieutra', data),
  ts_thuphi_hoamangmoi_thuphi_xoaphieu: (axios, data) => axios.post('/ccbs/executor/ts_thuphi_hoamangmoi_thuphi_xoaphieu', data),
  ts_laphd_chuyenquyen_sudung_xoaphieu: (axios, data) => axios.post('/ccbs/executor/ts_laphd_chuyenquyen_sudung_xoaphieu', data),
  ts_thuphi_hoamangmoi_thuphi_thanhtoan: (axios, data) => axios.post('/ccbs/executor/ts_thuphi_hoamangmoi_thuphi_thanhtoan', data),
  ts_laphd_chuyenquyen_sudung_thanhtoan: (axios, data) => axios.post('/ccbs/executor/ts_laphd_chuyenquyen_sudung_thanhtoan', data),

}
