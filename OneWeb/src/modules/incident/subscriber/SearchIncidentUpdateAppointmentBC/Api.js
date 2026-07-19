/*
[UR4.1.027] - Tra cứu báo hỏng - cập nhật giờ hẹn
https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=915077779&range=A1210:E1211
https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=915077779&range=A1210:E1210
https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=915077779 ( khao sat lai)
WinUI.WinUITraCuu.frmTraCuuPhieuBH,PTTB.exe,1
https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=915077779&range=A979:E979
https://ej2.syncfusion.com/vue/documentation/grid/row/detail-template/
*/
export default {
  lay_dulieu_bang_theo_dieukien: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
  sp_tt_baohong: async (axios, data) => axios.post('/web-hopdong/laydulieu/sp_tt_baohong', data),
  sp_tt_donvi: async (axios, data) => axios.post('/web-hopdong/laydulieu/sp_tt_donvi', data),
  sp_tt_trangthai_bh: async (axios, data) => axios.post('/web-hopdong/laydulieu/sp_tt_trangthai_bh', data),
  map_id: (axios, data) => axios.post(`/web-hopdong/thanhly/thanh_ly_map_id`, data),
  fn_tt_count_bh: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_count_bh`, data),
  fn_tt_giaophieuph: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieuph`, data),
  fn_tt_huonggiao: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_huonggiao`, data),
  fn_tt_giaophieubh: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieubh`, data),
  fn_tt_baohong: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_baohong`, data),
  fn_tt_nguoidung: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nguoidung`, data),
  fn_tt_donvi: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi`, data),
  lay_ds_thamso_macdinh: (axios, data) => axios.get(`/web-quantri/danhmuc/ds_thamso_md/${data.kieu_id}`, data),
  lay_ds_thamso_macdinh_dv: (axios, data) => axios.post(`/web-quantri/thamso-macdinh/lay_ds_thamso_macdinh_dv`, data),
  lay_ds_thamso_macdinh_dv2: (axios, data) => axios.get(`/web-hopdong/phanquyenhths/xuly_nghiemthubaohong/lay_ds_thamso_macdinh_dv`, data),
  sp_lay_ds_thamso_md: (axios, data) => axios.get(`/web-hopdong/thanhly/sp_lay_ds_thamso_md`, data), // {"ma_tb":"diep2981","dichvuvt_id":4}
  lay_danhba_theo_matb_new: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/lay_danhba_theo_matb_new`, data), // {"ma_tb":"diep2981","dichvuvt_id":4}
  sp_lay_ds_baohong_theo_ma_tb: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_baohong_theo_ma_tb`, data), // {"ma_tb":"minhhp1994","dichvuvt_id":4}
  sp_lay_tinh_tc_theo_tinh_id: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_tinh_tc_theo_tinh_id`, data), // {"tinh_id":26}
  sp_lay_cthong_theo_ds_baohong: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_cthong_theo_ds_baohong`, data), // {"ds_baohong_id": "5015,5019"}
  sp_lay_ctton_theo_ds_baohong: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ctton_theo_ds_baohong`, data), // {"ds_baohong_id": "4642,9153"}
  sp_lay_ds_nvth_theo_ds_baohong: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_nvth_theo_ds_baohong`, data),  // {"ds_baohong_id":"20641,19870"}
  lay_danhsach_phieuth_bh: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/lay_danhsach_phieuth_bh`, data), // {"baohong_id":4471646}
  lay_danhsach_phieuth_bh_v2: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/lay_danhsach_phieuth_bh_v2`, data), // {"baohong_id":4471646}
  // https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=915077779&range=C1038:D1038
  lay_ds_baohong_theo_thuebao_id: (axios, data) => axios.post(`/web-ccdv/tien_trinh_bao_hong/lay_ds_baohong_theo_thuebao_id`, data), // {vthuebao_id, vma_bh, vbaohong_id}

  // Popups
  getDsLsPhieuBaoHong: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/lay_lichsu_hen_baohong`, data),
  getDsLsHenThiCong: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/lay_lichsu_hen_thicong`, data),
  lay_tram_vt_baohong: (axios, data) => axios.get(`web-ccdv/tien_trinh_bao_hong/lay_tram_vt_baohong/${data}`),
  getDanhSachTienTrinhXuLy: (axios, data) => axios.post(`/web-ccdv/tien_trinh_bao_hong/nap_grd_tientrinh`, data),

  getDanhSachTienTrinhXuLyBH: (axios, hdtbId) => axios.get(`web-baohong/baohong/lay_ds_tientrinh_xuly_bh?baohong_id=${hdtbId}`),
  lay_ds_baohongbc_theo_baohong_id: (axios, data) => axios.get(`web-baohong/baohong/lay_ds_baohongbc_theo_baohong_id?vbaohong_id=${data.baohong_id}&vma_bh=${data.ma_bh}`, data),
  tinh_thicong: (axios, data) => axios.post(`/web-bancheo/danhmuc/tinh_thicong`, data),
  lay_danhsach_phieuth_bh_BC: (axios, data) => axios.post(`/web-bancheo/tracuubaohong/lay_danhsach_phieuth_bh`, data),
}
