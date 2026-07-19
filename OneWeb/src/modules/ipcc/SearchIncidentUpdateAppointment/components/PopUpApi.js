export default {
  // frmThayDoiTTBH
  getDsLsPhieuBaoHong: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/lay_lichsu_hen_baohong`, data),
  getDsLsHenThiCong: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/lay_lichsu_hen_thicong`, data),
  fn_thaydoi_tt_bh: (axios, data) => axios.post(`/web-gqkn/xu-ly-khieu-nai/fn_thaydoi_tt_bh`, data),
  // End frmThayDoiTTBH
  // https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=D719
  sp_lay_kqxl_huonggiao_thuhoi: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_kqxl_huonggiao_thuhoi`, data),
  sp_lay_ds_vattu_hdtb: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_vattu_hdtb`, data),
  lay_ds_vattu_baohong: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/lay_ds_vattu_baohong`, data),
  fn_lay_mota_vattu_theo_phieuid: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/fn_lay_mota_vattu_theo_phieuid`, data),
  // frmVatTuBaoHong
  // https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=E760
  lay_ds_trangbi: (axios, data) => axios.post(`/web-hopdong/TocDoMucCuoc/lay_ds_trangbi`, data),
  sp_lay_ds_kieu_tbi: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_kieu_tbi`, data),
  sp_lay_ds_donvi_tbi: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_donvi_tbi`, data),
  sp_lay_ds_tinhtrang_tbi: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_tinhtrang_tbi`, data),
  sp_lay_ds_loaitbi_baohong: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_loaitbi_baohong`, data),
  sp_lay_ds_vattu_dbtb: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_vattu_dbtb`, data),
  sp_giaophieutbi_baohong_ins: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_giaophieutbi_baohong_ins`, data),
  sp_capnhat_sua_vattu_bh: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_capnhat_sua_vattu_bh`, data),
  sp_sua_giaophieu_tbi_upd: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_sua_giaophieu_tbi_upd`, data),
  sp_xoa_gptbi_theo_rvt_del: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_xoa_gptbi_theo_rvt_del`, data),
  xoa_cap_vattu_bh: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/xoa_cap_vattu_bh`, data),
  // frmPhanAnhBH
  lay_ds_phananh: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/lay_ds_phananh`, data),
  lay_ds_nhanvienth_bh: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/lay_ds_nhanvienth_bh`, data),
  sp_lay_nhanvien_kv_theo_thuebao_id: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_nhanvien_kv_theo_thuebao_id`, data),
  sp_capnhat_phananh_bh: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_capnhat_phananh_bh`, data),
  xoa_phananh_bh_theo_id: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/xoa_phananh_bh_theo_id`, data),
  lay_tram_vt_baohong: (axios, data) => axios.get(`/web-ccdv/tien_trinh_bao_hong/lay_tram_vt_baohong/${data.baohong_id}`, data),
  // frmTracuu_NhacViec
  lay_ds_tientrinh_sms: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/lay_ds_tientrinh_sms`, data),
  // frmTraCuuPhieuJira
  // https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=E1053
  sp_lay_trangthai_jira: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_trangthai_jira`, data),
  sp_lay_ds_jira_theo_phieuid: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_jira_theo_phieuid`, data),
  get_issue_info: (axios, data) => axios.post(`/tichhop/jira/get_Issue_Info`, data),
  // frmHenKH
  // https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=D833
  lay_lichsu_hen_baohong: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/lay_lichsu_hen_baohong`, data),
  capnhat_giohen: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/capnhat_giohen`, data),
  layso_ngay_hen: (axios, data) => axios.post(`/web-thicong/ktvt-gphone/layso_ngay_hen`, data),
  send_sms_ld: (axios, data) => axios.post(`web-baohong/xacminh-nhaphen/send_sms_ld`, data),
  send_sms_hen_kythuat: (axios, data) => axios.post(`web-baohong/xacminh-nhaphen/send_sms_hen_kythuat`, data),
  // frmHenKHV2
  sp_lydohen_bh: (axios, data) => axios.post(`/web-baohong/xacminh-nhaphen/sp_lydohen_bh`, data),
  kiemtra_hen_baohong: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/kiemtra_hen_baohong_v2`, data),
  capnhat_giohen_v2: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/capnhat_giohen_v2`, data),
  capnhat_giohen_ttkd: (axios, data) => axios.post(`/web-baohong/TraCuuPhieuGiaoDenBH/capnhat_giohen_ttkd`, data),
  // frmNhacViecBH
  sp_lay_nvtn_theo_baohong_id: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_nvtn_theo_baohong_id`, data),
  lay_tinnhan_mau_nhacviec: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/lay_tinnhan_mau_nhacviec`, data),
  lay_noidung_sms_giaoviec: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_noidung_sms_giaoviec_v2`, data),
  sp_lay_tientrinh_nvn_theo_id: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_tientrinh_nvn_theo_id`, data),
  kiemtra_thoigian_nhacviec_bh: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/kiemtra_thoigian_nhacviec_bh`, data),
  sp_capnhat_tientrinh_sms: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_capnhat_tientrinh_sms`, data),
  sp_lay_donvi_id_theo_ma_nd: (axios, data) => axios.post(`/web-gqkn/TraCuuPhieuKN/sp_lay_donvi_id_theo_ma_nd`, data),
  sp_lay_donvi_theo_id: (axios, data) => axios.post(`/web-gqkn/TraCuuPhieuKN/sp_lay_donvi_theo_id`, data),
  lay_ds_cay_donvi: (axios, data) => axios.get(`/web-thicong/tientrinh_thicong/lay_ds_cay_donvi`, data),
  lay_nv_giao_phkh_v2: (axios, data) => axios.post(`/web-ccdv/tientrinh-khieunai/lay_nv_giao_phkh_v2`, data),
  // {
  //   "vdonvi_id": 187,
  //   "vhuonggiao_id": 641,
  //   "vloaidv_id": 5
  // }
  lay_ds_donvi_phkh: (axios, data) => axios.post(`/web-gqkn/TraCuuPhieuKN/lay_ds_donvi_phkh`, data),
  send_sms: (axios, data) => axios.post(`/tichhop/sms/send`, data),
  // frmKhoaMo_TP
  sp_lay_ds_kieuyc_khoamotp: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_kieuyc_khoamotp`, data),
  sp_lay_ds_phieubh_khoamotp: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_phieubh_khoamotp`, data),
  sp_capnhat_khoamotp: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_capnhat_khoamotp`, data),
  // frmTraCuuTBBaoHong
  // {
  //   "nhom_id": 1
  // }
  lay_dk_tracuu: (axios, data) => axios.post(`/web-tracuu/TraCuuDanhBaTB/lay_dk_tracuu`, data),
  lay_ds_dvvt: (axios, data) => axios.post(`/web-tracuu/TraCuuDanhBaTB/lay_ds_dvvt`, data),
  // https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=288822852&range=E1743
  tra_cuu_tb_bh: (axios, data) => axios.post(`/web-ccdv//thicong_ghone/tra_cuu_tb_bh`, data),
  nhantin_nv_qldb_v2: (axios, data) => axios.post(`/web-baohong/baohong/nhantin_nv_qldb_v2`, data),
  capnhat_giohen_ttvt: (axios, data) => axios.post(`/web-baohong/baohong/capnhat_giohen_ttvt`, data),
}
