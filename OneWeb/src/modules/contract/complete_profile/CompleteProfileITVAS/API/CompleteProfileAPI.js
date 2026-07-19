export default {
  CSS_KIEU_HD: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_HD', data, header),
  CSS_KENHTHU: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_KENHTHU', data, header),
  lay_ds_loai_hd: async (axios, data, header) => axios.post('/web-ccdv/khaibaoims/lay_ds_loai_hd', data, header),
  ds_quytrinh: async (axios, data, header) => axios.post('/web-hopdong/danhmuc/ds_quytrinh', data, header),
  lay_ds_quytrinh_v2: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_quytrinh_v2', data, header),

  sp_lay_huonggiao_theo_quytrinh: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_lay_huonggiao_theo_quytrinh', data, header),
  lay_luong_thaotac: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_luong_thaotac', data, header),
  lay_ct_thaotac_control: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ct_thaotac_control', data, header),
  sp_lay_kenhthu_theo_hdkh: async (axios, data, header) => axios.post('/web-ccdv/khaibaotsl/sp_lay_kenhthu_theo_hdkh', data, header),
  lay_huonggiao_theo_luong_id: async (axios, data, header) => axios.post('/web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id', data, header),
  lay_ds_trangthai_jira: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_trangthai_jira', data, header),

  //lấy tổng tiền
  lay_tongtien_theo_hdkh_id: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_tongtien_theo_hdkh_id', data, header),
  lay_thongtin_nhanvien: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_thongtin_nhanvien', data, header),
  kiemtra_hen_hdtb: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/kiemtra_hen_hdtb', data, header),
  sp_ht_phieu_tra: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_ht_phieu_tra', data, header),
  lay_ds_hdtb_dv: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_hdtb_dv', data, header),
  lay_ten_loai_khl: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ten_loai_khl', data, header),
  phailam: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/phailam', data, header),
  kt_phieu_phoihop: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/kt_phieu_phoihop', data, header),
  lay_thuebao_lapkem: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_thuebao_lapkem', data, header),

  //******TABS
  //tab_dv_cntt
  lay_ds_nhanvien_theo_donvi: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_donvi', data, header),
  lay_ds_phieu_hc_dv_cntt_v2: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_phieu_hc_dv_cntt_v2', data, header),
  lay_ds_phieu_hc_dv_cntt_v3: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_phieu_hc_dv_cntt_v3', data, header),
  lay_danhsach_thuoctinh_v3: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_danhsach_thuoctinh_v3', data, header),
  lay_danhsach_thuoctinh_v3_tichhop_sql: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_danhsach_thuoctinh_v3_tichhop_sql', data, header),

  lay_ds_nhanvien_theo_phieu_id: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id', data, header),
  lay_thongtin_lh_lapdat: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_thongtin_lh_lapdat', data, header),
  lay_ds_hdtb_cntt_theo_hdtb_id: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_hdtb_cntt_theo_hdtb_id', data, header),
  lay_danhsach_phieu_net_con: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_danhsach_phieu_net_con', data, header),

  //tab_it360
  lay_ds_issue_cua_hopdongtb: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_issue_cua_hopdongtb', data, header),

  //tab thông tin gói cước
  lay_ds_thamso_tskt: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_thamso_tskt', data, header),
  //tab thông tin dịch vụ gia tăng
  lay_ds_dangky_dvgt: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_dangky_dvgt', data, header),
  //tab tiến trình thi công
  lay_tinh_thi_cong: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_tinh_thi_cong', data, header),
  lay_tientrinh_tinh_tc: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_tientrinh_tinh_tc', data, header),
  //******END TABS

  //******CÁC SỰ KIỆN BUTTONS
  update_nd_thuchien: async (axios, data, header) => axios.post('/web-hopdong/thaydoi_thongso_megawan/update_nd_thuchien', data, header),

  //cap nhật F9
  fn_an_capnhat_hoancong_dvcntt: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_capnhat_hoancong_dvcntt', data, header),
  //kích hoạt VISA
  capnhat_ngaykh_status_v2: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/capnhat_ngaykh_status_v2', data, header),
  //hoàn công
  fn_an_hoancong_dvcntt: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_hoancong_dvcntt', data, header),

  fn_an_hoanthanh: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_hoanthanh', data, header),

  fn_ghilai_noidung_tientrinh_thicong: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_ghilai_noidung_tientrinh_thicong', data, header),

  fn_an_hoanthienhoso_dichvu_cntt: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_hoanthienhoso_dichvu_cntt', data, header),

  //******END CÁC SỰ KIỆN

  lay_dulieu_tao_thucthe_ca: async (axios, data, header) => axios.get('/web-ccdv/tram-thicong-dv-cntt/lay_dulieu_tao_thucthe_ca', data, header),
  fn_get_status_hdtb: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_get_status_hdtb', data, header),
  fn_kiemtra_dauvao_api: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_kiemtra_dauvao_api', data, header),
  lay_dulieu_cho_api: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_dulieu_cho_api', data, header),
  fn_khaibao_api: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_khaibao_api', data, header),
  sp_log_api: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_log_api', data, header),
  sp_capnhat_status_kichhoat: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_capnhat_status_kichhoat', data, header),
  lay_dulieu_cho_api_theo_command: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_dulieu_cho_api_theo_command', data, header),

  ivan_khoitao_dv: async (axios, data, header) => axios.post('/tichhop/ivan/a101', data, header),
  ivan_dongbo: async (axios, data, header) => axios.post('/tichhop/ivan/a113', data, header),
  ivan_giahan_dv: async (axios, data, header) => axios.post('/tichhop/ivan/a102', data, header),
  ivan_thaydoi_tt: async (axios, data, header) => axios.post('/tichhop/ivan/a105', data, header),
  ivan_khoiphuc_sd: async (axios, data, header) => axios.post('/tichhop/ivan/a101', data, header),
  ivan_thanhly_dv: async (axios, request) => axios.post('/tichhop/ivan/a103', request),

  //EDU API
  getToTalSmsByTruong: async (axios, data) => axios.get(`/tichhop/edu/l1?call=app.doisoat.serv.api.getToTalSmsByTruong&id_truong=${data}&type=1`, data),

  //CA
  GuiLaiSMS: async (axios, data, header) => axios.post('/tichhop/ca/GuiLaiSMS', data, header),
  GuiYeuCau: async (axios, data, header) => axios.post('/tichhop/ca/GuiYeuCau', data, header),
  DongBoRADatMoi: async (axios, data, header) => axios.post('/web-hopdong/dichvu-ca/dongbo_ra_cms_datmoi', data, header),
  DongBoDuyetCA: async (axios, data, header) => axios.post('/web-hopdong/dichvu-ca/dongbo_duyet_ca_cms', data, header),
  DongBoHuyDuyetCA: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/dongbo_huy_duyet_ca_cms', data, header),
  DongBoRAGiaHan: async (axios, data, header) => axios.post('/web-hopdong/dichvu-ca/dongbo_ra_cms_giahan', data, header),
  DongBoDuLieuCA: async (axios, data, header) => axios.post('/web-hopdong/dichvu-ca/dongbo_dulieu_ca', data, header),
  LayThongTinCAHienHuu: async (axios, data, header) => axios.post(`/tichhop/ca/LayThongTinCAHienHuu`, data, header),

  sp_lay_ds_chungchi_ssl: async (axios, data, header) => axios.get('/web-ccdv/tram-thicong-dv-cntt/sp_lay_ds_chungchi_ssl', data, header),
  kiemtra_trangthai_dongbocms: async (axios, data, header) => axios.get('/web-ccdv/tram-thicong-dv-cntt/kiemtra_trangthai_dongbocms', data, header),
  sp_ds_loai_hd_dongbocms: async (axios, data, header) => axios.get('/web-ccdv/tram-thicong-dv-cntt/sp_ds_loai_hd_dongbocms', data, header),
  ca_dhsx_lay_ketqua_cms: async (axios, data, header) => axios.post('/web-ccdv/tram-thicong-dv-cntt/ca_dhsx_lay_ketqua_cms', data, header),
  sp_update_hd_thuebao_duyetcachuanhoa: async (axios, data, header) => axios.post('/web-ccdv/tram-thicong-dv-cntt/sp_update_hd_thuebao_duyetcachuanhoa', data, header),
  capnhat_trangthai_smartca: async (axios, data, header) => axios.post('/web-ccdv/tram-thicong-dv-cntt/capnhat_trangthai_smartca', data, header),
  kiem_tra_quyen_duyet_ca: async (axios, data, header) => axios.post('/web-ccdv/tram-thicong-dv-cntt/kiem_tra_quyen_duyet_ca', data, header),
  lay_ds_hoso_ca: async (axios, data, header) => axios.get('/web-ccdv/tram-thicong-dv-cntt/lay_ds_hoso_ca', data, header),
  kiemtra_huy_acc_smartca_thuhoi_taoloi: async (axios, data, header) => axios.post('/web-ccdv/tram-thicong-dv-cntt/kiemtra_huy_acc_smartca_thuhoi_taoloi', data, header),
  kiemtra_bienban_nghiemthu_ca: async (axios, data, header) => axios.get('/web-ccdv/tram-thicong-dv-cntt/kiemtra_bienban_nghiemthu_ca', data, header),
  lay_ds_hoso_ca_nghiemthu: async (axios, data, header) => axios.get('/web-ccdv/tram-thicong-dv-cntt/lay_ds_hoso_ca_nghiemthu', data, header),
  ca_danhsach_driver: async (axios, data, header) => axios.get('/web-ccdv/tram-thicong-dv-cntt/ca_danhsach_driver', data, header),
  ca_lay_ly_dohuy_hdtb: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/ca_lay_ly_dohuy_hdtb', data, header),
  cap_nhat_status_hdtb: async (axios, data, header) => axios.post('/web-ccdv/tram-thicong-dv-cntt/cap_nhat_status_hdtb', data, header),
  fn_thuchien_dongbohs: async (axios, data, header) => axios.post('/web-ccdv/tram-thicong-dv-cntt/fn_thuchien_dongbohs', data, header),
  fn_kiemtra_trangthai_kichhoat_hoancong: async (axios, data, header) => axios.post('/web-ccdv/tram-thicong-dv-cntt/fn_kiemtra_trangthai_kichhoat_hoancong', data, header),

  // ELEARNING
  fn_get_kieuld_nhapbosung_elearning: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_get_kieuld_nhapbosung_elearning', data, header),
  fn_get_status_hdtb_elearning: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_get_status_hdtb_elearning', data, header),
  fn_thongbao_kichhoat_elearning: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_thongbao_kichhoat_elearning', data, header),
  fn_kichhoat_elearning: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_kichhoat_elearning', data, header),

  //brandname

  //   getToTalSmsByTruong: async (axios, data) => axios.get(`/tichhop/edu/l1?call=app.doisoat.serv.api.getToTalSmsByTruong&id_truong=${data}&type=1`, data),
  fn_kiemtra_hien_form_tao_brn: async (axios, data) => axios.get(`/web-hopdong/vbn/fn_kiemtra_hien_form_tao_brn?hdtb_id=${data.vhdtb_id}`, data),
  //HKD
  fn_tao_req_cntt: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/tao_req_cntt', data, header),
  fn_dongbo_hkd: async (axios, data, header) => axios.post('/web-hopdong/hopdong/dongbo_thongtin_hkd', data, header),
  lay_tt_thuebao_hkd: async (axios, data, header) => axios.get('/tichhop/cntt/HKD/LayThongTinKHV2/' + data.mst, null, header),
  fn_layds_quan: async (axios, data, header) => axios.get('/tichhop/cntt/HKD/LayDanhSachQuan/' + data.tinh_id, null, header),
  fn_datmoi_hkd: async (axios, data, header) => axios.post('/tichhop/cntt/HKD/DangKyKhachHang', data, header),
  fn_giahan_hkd: async (axios, data, header) => axios.post('/tichhop/cntt/HKD/GiaHan', data, header),
  fn_nanggoi_hkd: async (axios, data, header) => axios.post('/tichhop/cntt/HKD/NangCapGoiCuoc', data, header),
  fn_tang_sl_hoadon: async (axios, data, header) => axios.post('/tichhop/cntt/HKD/MuaThemHoaDon', data, header),
  fn_layds_phuong: async (axios, data, header) => axios.get('/tichhop/cntt/HKD/LayDanhSachPhuong/' + data.tinh_id + '/' + data.quan_id, null, header),
  lay_ds_tinh_thicong_hkd: (axios, data) => axios.get(`/web-hopdong/thaydoimegawan/sp_lay_ds_tinh_kn`, data),
  fn_capnhat_diachi_hkd: (axios, data, header) => axios.post(`web-hopdong/dichvu-ca/fn_capnhat_dulieu_hkd`, data, header),
  lay_thongso_dkmua_them_hd: (axios, data) => axios.post(`/web-ccdv/tram-thicong-dv-cntt/lay_thongso_dkmua_them_hd`, data),
  update_hd_thuebao: (axios, data) => axios.post(`/web-ccdv/tram-thicong-dv-cntt/cap_nhat_status_hdtb`, data),
  MuaThemHoaDon: (axios, data) => axios.post(`/tichhop/hddt/MuaThemHoaDon`, data),
  TamNgungDichVuHDDT: (axios, data) => axios.post(`/tichhop/hddt/TamNgungDichVuHDDT`, data),
  KhoiPhucDichVuHDDT: (axios, data) => axios.post(`/tichhop/hddt/KhoiPhucDichVuHDDT`, data),

  //EKYC
  fn_datmoi_ekyc: async (axios, data, header) => axios.post('/tichhop/cntt/EKYC/DatMoi', data, header),
  fn_giahan_ekyc: async (axios, data, header) => axios.post('/tichhop/cntt/EKYC/GiaHan', data, header),
  fn_thanhly_ekyc: async (axios, data, header) => axios.post('/tichhop/cntt/EKYC/ThanhLy', data, header),
  fn_themreq_ekyc: async (axios, data, header) => axios.post('/tichhop/cntt/EKYC/ThemReq', data, header),

  //IQMS
  fn_layds_quan_iqms: async (axios, data, header) => axios.get('/tichhop/cntt/IQMS/LayDanhSachQuan/' + data.tinh_id, header),
  fn_layds_phuong_iqms: async (axios, data, header) => axios.get('/tichhop/cntt/IQMS/LayDanhSachPhuong/' + data.tinh_id + '/' + data.quan_id, header),
  fn_datmoi_iqms: async (axios, data, header) => axios.post('/tichhop/cntt/IQMS/DatMoi', data, header),
  fn_giahan_iqms: async (axios, data, header) => axios.post('/tichhop/cntt/IQMS/GiaHan', data, header),
  fn_tdtt_iqms: async (axios, data, header) => axios.post('/tichhop/cntt/IQMS/ThayDoiThongTin', data, header),
  fn_cathuy_iqms: async (axios, data, header) => axios.post('/tichhop/cntt/IQMS/CatHuy', data, header),
  fn_thaysoluong_phong_iqms: async (axios, data, header) => axios.post('/tichhop/cntt/IQMS/ThayDoiSoLuongPhong', data, header),
  fn_chuyen_chinhthuc_iqms: async (axios, data, header) => axios.post('/tichhop/cntt/IQMS/ChuyenChinhThuc', data, header),
  fn_capnhat_diachi_iqms: (axios, data, header) => axios.post(`web-hopdong/dichvu-ca/fn_capnhat_dulieu_hkd`, data, header),

  //smartca
  lay_ds_thuebaoca_theo_magd: (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_thuebaoca_theo_magd', data),
  laythongtin_nguoiduyet: (axios, data) => axios.post('/web-thicong/dvcntt/laythongtinnguoiduyet', data),
  duyet_smartca_onesme: (axios, data) => axios.post('/tichhop/cntt/SmartCA/DuyetCAOneSME', data),
  huyduyet_smartca_onesme: (axios, data) => axios.post('/tichhop/cntt/smartca/DuyetCAOneSME', data),
  ra_smartca_v2: (axios, data) => axios.post('/web-thicong/vnptsmartca/ra_v2', data),
  call_dynamic_api:(axios, data) => axios.post('/web-thicong/cnttdynamic/api', data),
  // kiem tra nang cap smartcav2
  kiemtra_nangcap_phienban: (axios, data) => axios.post('/web-thicong/dvcntt/trienkhaithunghiemcntt_v2', data),
  kiemtra_hoso_du: (axios, data) => axios.post("/web-thicong/dvcntt/kt_soluong_file_smca_daydu", data),
  ra_ca_full_hoso: (axios, data) => axios.post("/web-thicong/dvcntt/ra_ca_full_hoso", data),
  lay_thongtin_duyet_smca_full_hoso: (axios, data) => axios.post("/web-thicong/dvcntt/lay_thongtin_duyet_smca_full_hoso", data),
  duyet_smca_du_hoso: (axios, data) => axios.post("/web-thicong/vnptsmartca/duyet_smca_du_hoso", data),
  //VNPT Enrollment
  tao_req_cntt: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/tao_req_cntt', data, header),
  sp_insert_log_cntt: async (axios, data, header) => axios.post('/web-hopdong/dichvu-ca/sp_insert_log_cntt', data, header),
  VNPT_Enrollment: async (axios, loai, data, header) => axios.post(`/tichhop/vnEduEnrollment/${loai}`, data, header),
  // VNPT_Enrollment_DVGT: async (axios, data,header) => axios.post('/tichhop/vnEduEnrollment/DVGT', data, header),
  // VNPT_Enrollment_THANHLY: async (axios, data,header) => axios.post('/tichhop/vnEduEnrollment/THANHLY', data, header),
  tao_req_duyet_smca: async (axios, data, header) => axios.post('/web-thicong/vnptsmartca/taoreqduyetsmca', data, header),
  tao_req_huy_duyet_smca: async (axios, data, header) => axios.post('/web-thicong/vnptsmartca/taoreqhuyduyetsmca', data, header),
  tao_req_duyet_doi_email_smca: async (axios, data, header) => axios.post('/web-thicong/vnptsmartca/TaoMessageDuyetSMCADoiEmail', data, header),
  duyet_smca_doi_email_v2: async (axios, data, header) => axios.post('/web-thicong/vnptsmartca/duyet_doi_email_smca', data, header),
  duyet_smca_v2: async (axios, data, header) => axios.post('/web-thicong/vnptsmartca/duyet_smca_v2', data, header),

  lay_dulieu_cho_api_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_dulieu_cho_api_elearning`, data, header),
  sp_insert_log_action_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_insert_log_action_elearning`, data, header),
  fn_khaibao_api_kickhoat_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_khaibao_api_kickhoat_elearning`, data, header),
  sp_capnhat_status_dv_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_capnhat_status_dv_elearning`, data, header),
  fn_thuchien_capnhat_userpass_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_thuchien_capnhat_userpass_elearning`, data, header),
  fn_update_user_password_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_update_user_password_elearning`, data, header),

  //DICH VU KAS
  vnptfiber_kichhoat_dichvu: async (axios, data, header) => axios.post(`/tichhop/vtc/vnptfiber/kichhoat_dichvu`, data, header),
  lay_ds_chitiet_km_theo_tbid: async (axios, data, header) => axios.post(`/web-hopdong/thaydoiloaihinhtb/lay_ds_chitiet_km_theo_tbid`, data, header),
  fn_lay_thongtin_kichhoat_bhs_kas: async (axios, data, header) => axios.post(`/web-hopdong/hopdong/fn_lay_thongtin_kichhoat_bhs_kas`, data, header),
  capnhat_ngaykh_ngay_ht_v2: async (axios, data, header) => axios.post(`/web-ccdv/dongbo-visa-vasc/capnhat_ngaykh_ngay_ht_v2`, data, header),
  get_kas_product_code: async (axios, data, header) => axios.post('/web-thicong/dvcntt/get_kas_product_code', data, header),

  // ELEARNING
  fn_get_kieuld_nhapbosung_elearning: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_get_kieuld_nhapbosung_elearning', data, header),
  fn_get_status_hdtb_elearning: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_get_status_hdtb_elearning', data, header),
  fn_thongbao_kichhoat_elearning: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_thongbao_kichhoat_elearning', data, header),
  fn_kichhoat_elearning: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_kichhoat_elearning', data, header),

  //HKD
  fn_tao_req_cntt: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/tao_req_cntt', data, header),
  fn_layds_quan: async (axios, data, header) => axios.get('/tichhop/cntt/HKD/LayDanhSachQuan/' + data.tinh_id, null, header),
  fn_datmoi_hkd: async (axios, data, header) => axios.post('/tichhop/cntt/HKD/DangKyKhachHang', data, header),
  fn_giahan_hkd: async (axios, data, header) => axios.post('/tichhop/cntt/HKD/GiaHan', data, header),
  fn_nanggoi_hkd: async (axios, data, header) => axios.post('/tichhop/cntt/HKD/NangCapGoiCuoc', data, header),
  fn_tang_sl_hoadon: async (axios, data, header) => axios.post('/tichhop/cntt/HKD/MuaThemHoaDon', data, header),
  fn_layds_phuong: async (axios, data, header) => axios.get('/tichhop/cntt/HKD/LayDanhSachPhuong/' + data.tinh_id + '/' + data.quan_id, null, header),
  lay_ds_tinh_thicong_hkd: (axios, data) => axios.get(`/web-hopdong/thaydoimegawan/sp_lay_ds_tinh_kn`, data),
  fn_capnhat_diachi_hkd: (axios, data, header) => axios.post(`web-hopdong/dichvu-ca/fn_capnhat_dulieu_hkd`, data, header),
  lay_thongso_dkmua_them_hd: (axios, data) => axios.post(`/web-ccdv/tram-thicong-dv-cntt/lay_thongso_dkmua_them_hd`, data),
  update_hd_thuebao: (axios, data) => axios.post(`/web-ccdv/tram-thicong-dv-cntt/update_hd_thuebao`, data),
  MuaThemHoaDon: (axios, data) => axios.post(`/tichhop/hddt/MuaThemHoaDon`, data),
  MuaThemBienLaiDienTu: (axios, data) => axios.post(`/tichhop/hddt/MuaThemHoaDon/BienLaiDienTu`, data),
  TamNgungDichVuHDDT: (axios, data) => axios.post(`/tichhop/hddt/TamNgungDichVuHDDT`, data),
  KhoiPhucDichVuHDDT: (axios, data) => axios.post(`/tichhop/hddt/KhoiPhucDichVuHDDT`, data),
  TamNgungBienLaiDienTu: (axios, data) => axios.post(`/tichhop/hddt/TamNgungBienLaiDienTu`, data),
  KhoiPhucBienLaiDienTu: (axios, data) => axios.post(`/tichhop/hddt/KhoiPhucBienLaiDienTu`, data),
  KhoaDichVuHDDT: (axios, data) => axios.post(`/tichhop/hddt/KhoaDichVuHDDT`, data),
  MoKhoaDichVuHDDT: (axios, data) => axios.post(`/tichhop/hddt/MoDichVuHDDT`, data),

  //smartca
  lay_ds_thuebaoca_theo_magd: (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_thuebaoca_theo_magd', data),
  duyet_smartca_onesme: (axios, data) => axios.post('/tichhop/cntt/SmartCA/DuyetCAOneSME', data),
  huyduyet_smartca_onesme: (axios, data) => axios.post('/tichhop/cntt/smartca/DuyetCAOneSME', data),
  tao_req_cntt_v2: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/tao_req_cntt_v2', data, header),

  //dong bo hddt
  laythongtin_hddt: (axios, data) => axios.get('/tichhop/cntt/HoaDonDienTu/LayThongTinKHHDDT' + data.mst),

  //VNPT Enrollment
  // tao_req_cntt: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/tao_req_cntt', data, header),
  // sp_insert_log_cntt: async (axios, data,header) => axios.post('/web-hopdong/dichvu-ca/sp_insert_log_cntt', data, header),
  // VNPT_Enrollment: async (axios, loai, data, header) => axios.post(`/tichhop/vnEduEnrollment/${loai}`, data, header),
  // VNPT_Enrollment_DVGT: async (axios, data,header) => axios.post('/tichhop/vnEduEnrollment/DVGT', data, header),
  // VNPT_Enrollment_THANHLY: async (axios, data,header) => axios.post('/tichhop/vnEduEnrollment/THANHLY', data, header),

  lay_dulieu_cho_api_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_dulieu_cho_api_elearning`, data, header),
  sp_insert_log_action_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_insert_log_action_elearning`, data, header),
  fn_khaibao_api_kickhoat_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_khaibao_api_kickhoat_elearning`, data, header),
  sp_capnhat_status_dv_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_capnhat_status_dv_elearning`, data, header),
  fn_thuchien_capnhat_userpass_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_thuchien_capnhat_userpass_elearning`, data, header),
  fn_update_user_password_elearning: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_update_user_password_elearning`, data, header),

  //DICH VU KAS
  vnptfiber_kichhoat_dichvu: async (axios, data, header) => axios.post(`/tichhop/vtc/vnptfiber/kichhoat_dichvu`, data, header),
  lay_ds_chitiet_km_theo_tbid: async (axios, data, header) => axios.post(`/web-hopdong/thaydoiloaihinhtb/lay_ds_chitiet_km_theo_tbid`, data, header),
  fn_lay_thongtin_kichhoat_bhs_kas: async (axios, data, header) => axios.post(`/web-hopdong/hopdong/fn_lay_thongtin_kichhoat_bhs_kas`, data, header),
  capnhat_ngaykh_ngay_ht_v2: async (axios, data, header) => axios.post(`/web-ccdv/dongbo-visa-vasc/capnhat_ngaykh_ngay_ht_v2`, data, header),

  //HDDT - Máy tính tiền
  MuaThemHoaDonMTT: (axios, data) => axios.post(`/tichhop/hddt/MuaThemHoaDonMTT`, data),
  //Dịch vụ VNPT BMIS

  DatMoi_DV_BMIS: async (axios, data, header) => axios.post(`/web-thicong/vnptbmis/datmoi`, data, header),
  GiaHan_DV_BMIS: async (axios, data, header) => axios.post(`/web-thicong/vnptbmis/giahan`, data, header),
  DoiGoiCuoc_DV_BMIS: async (axios, data, header) => axios.post(`/web-thicong/vnptbmis/doigoicuoc`, data, header),
  TamDung_DV_BMIS: async (axios, data, header) => axios.post(`/web-thicong/vnptbmis/tamdung`, data, header),
  KhoiPhuc_DV_BMIS: async (axios, data, header) => axios.post(`/web-thicong/vnptbmis/khoiphuc`, data, header),
  ThanhLy_DV_BMIS: async (axios, data, header) => axios.post(`/web-thicong/vnptbmis/thanhly`, data, header),
  huyhocsinh: async (axios, { hdtb_id }) => axios.post(`/web-thicong/hocbadientu/huyhocsinh`, { hdtb_id }),

  //smartca Ngan Sach
  dangky_ngansach_smartca: async (axios, data, header) => axios.post(`/tichhop/cntt/SmartCA/TaoNganSach`, data, header),
  dangky_luotky_smartca: async (axios, data, header) => axios.post(`/tichhop/cntt/SmartCA/DangKyLuotKy`, data, header),

  // dich vu GoMeet
  fn_datmoi_gomeet: async (axios, data, header) => axios.post('/tichhop/cntt/GoMeet/DatMoi', data, header),
  fn_giahan_gomeet: async (axios, data, header) => axios.post('/tichhop/cntt/GoMeet/GiaHan', data, header),
  fn_cathuy_gomeet: async (axios, data, header) => axios.post('/tichhop/cntt/GoMeet/CatHuy', data, header),
  fn_thaydoi_goicuoc_gomeet: async (axios, data, header) => axios.post('/tichhop/GoMeet/ThayDoiGoiCuoc', data, header),
  fn_tamngung_gomeet: async (axios, data, header) => axios.post('/tichhop/GoMeet/TamNgung', data, header),
  // Thêm api từ anh Duy
  lay_ct_thaotac_control_v2: async (axios, data, header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ct_thaotac_control_v2', data, header),
  //Dịch Vụ VNPT Pharmacy
  fn_datmoi_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/datmoi', data, header),
  fn_chuyendungchinhthuc_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/chuyendungchinhthuc', data, header),
  fn_giahan_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/giahan', data, header),
  fn_tamdung_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/tamdung', data, header),
  fn_khoiphuc_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/khoiphuc', data, header),
  fn_thanhly_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/thanhly', data, header),
  fn_thaydoithongtin_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/thaydoithongtin', data, header),
  fn_muathemcuahang_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/muathemcuahang', data, header),
  fn_muathemtaikhoan_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/muathemtaikhoan', data, header),
  fn_cathuycuahang_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/cathuycuahang', data, header),
  fn_cathuytaikhoan_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/cathuytaikhoan', data, header),
  fn_layDsUser_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/layDsUser', data, header),
  fn_layDsCuaHang_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/layDsCuaHang', data, header),
  fn_thongtinchitiet_pharmacy: async (axios, data, header) => axios.post('/web-thicong/pharmacy/thongtinchitiet', data, header),
  them_hd_thuebao_ct_common: async (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/them_hd_thuebao_ct_common`, data),
  SP_LAY_DS_DL_NGHIEPVU: async (axios, data) => axios.post(`/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu`, data),
  lay_thong_tin_da_dang_ky: (axios, data) => axios.post(`/web-quantri/chuyendoihoadon/lay_ds_hd_thuebao_ct_common`, data),

  LayThongTinKHHDDT: (axios, data) => axios.get(`/tichhop/hddt/LayThongTinKHHDDT`, data),
  capnhat_thongtin_kh_hddt: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/capnhat_thongtin_kh_hddt`, data),

  lay_thongtin_tao_hddt: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_thongtin_tao_hddt`, data),
  DangKyKhachHang: (axios, data) => axios.post(`/tichhop/hddt/DangKyKhachHang`, data),
  KhoiTaoDemo: (axios, data) => axios.post(`/tichhop/hddt/KhoiTaoDemo`, data),
  DangKyMauHoaDon: (axios, data) => axios.post(`/tichhop/hddt/DangKyMauHoaDon`, data),
  layds_mau_hddt_dangky: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/layds_mau_hddt_dangky`, data),
  XoaMauHoaDon: (axios, data) => axios.post(`/tichhop/hddt/XoaMauHoaDon`, data),
  HTMLMauHoaDon: (axios, data) => axios.get(`/tichhop/hddt/LayChiTietMauHoaDon`, data),
  sp_loadmaudadangky_hdtb_cntt: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_loadmaudadangky_hdtb_cntt`, data),
  LayDSMauHoaDonDaDangKy: (axios, data) => axios.post(`/tichhop/hddt/LayDSMauHoaDonDaDangKy`, data),
  KhoiTaoHeThongThat: (axios, data) => axios.post(`/tichhop/hddt/KhoiTaoHeThongThat`, data),
  lay_soluong_hddt_dk: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_soluong_hddt_dk`, data),
  xoa_mau_hddt_chuadk: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/xoa_mau_hddt`, data),
  cntt_dynamic_api: (axios, data) => axios.post(`web-thicong/cnttdynamic/api`, data),
  gui_mail: (axios, data) => axios.post(`/web-ccdv/gui-mail-kich-hoat-brand-name/gui_mail`, data),
  kichHoatThanhLyKas: (axios, data) => axios.post(`/web-thicong/dvcntt/kichHoatThanhLyKas`, data),

  api_capnhat_tinh_quan_phuong_id_hopdong: async (axios, data) => axios.post('/web-thicong/capnhatthongtin/capnhat_tinh_quan_phuong_id_hopdong', data),
  sp_lay_ds_nghiepvu_cntt_common : async(axios, data) => axios.post('/web-thicong/dvcntt/lay_ds_nghiepvu_cntt_common',data),
  fn_thongbao_kichhoat_dvcntt_common : async(axios, data) => axios.post('/web-thicong/dvcntt/fn_thongbao_kichhoat_dvcntt_common', data),

}
