export default {
  insertUpdatePhanLoaiDichVu: (axios, data) => axios.post('/web-quantri/phanloaidv/fn_phanloai_dichvu_update', data),
  deletePhanLoaiDichVu: (axios, data) => axios.post('/web-quantri/phanloaidv/fn_phanloai_dichvu_delete', data),
  getDanhSachDichVuVT: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', { params }),
  getDanhSachLoaiHinhTB: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', { params }),
  getPhanLoaiDichVu: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_PHANLOAI_DV', { params }),



  getDanhSachKenhThu: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_KENHTHU', { params }),
  getDanhSachDoiTac: (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_lay_ds_doitac', data),
  getLoaiHD: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD', { params }),

  getKieuHD: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_HD', { params }),

  getLoaiCap: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_CAP', { params }),
  getTocDoKenh: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH', { params }),


  getLoaiHinhTB: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', { params }),


  getNhanvienGV: (axios, data) => axios.post('/web-baohong/khaibaotsl/sp_lay_ds_nhanvien_thuchien_theo_dv', data),


  getComboDonViCuoi: (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_ht_tatca_vetinh_combobox1', data),

  getDanhSachChuQuan: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_CHUQUAN', { params }),



  getDanhSachNhanVienID: (axios, data) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_ds_nhanvien_theo_phieu_id', data),

  getDanhSachQuyTrinh: (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_quytrinh_v2', data),
  
  ds_quytrinh: (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_quytrinh_v2', data),


  getDanhSachHuongGiao: (axios, data) => axios.get(`/web-hopdong/danhmuc/ds_huonggiao/${data.quytrinh_id}/${data.tthd_id}`),


  getDanhSachThueBao: (axios, data) => axios.post('/web-ccdv/khaibaotsl/lay_ds_phieu_giaoden_tsl', data),


  getTongTien: (axios, data) => axios.post('/web-ccdv/khaibaotsl/lay_tongtien_theo_hdkh_id', data),

  getKenhThu: (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_lay_kenhthu_theo_hdkh', data),

  getThongTinLH: (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_lay_thongtin_lh_lapdat_tsl', data),

  getDanhSachThietBi: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/sp_ht_loai_tb_tsl_combobox`, data), 

  getDSHDTBDBKieudv: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/sp_lay_ds_hdtb_dv_kieudv`,data),


  getThongTinNhanVien: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/fn_lay_thongtin_nhanvien`,data),

  getThongTinNhanVienDV: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/sp_lay_thongtin_nhanvien_dv`,data),

  getDanhSachHopDongThueBaoTLS: (axios, data) => axios.get(`/web-hopdong/hopdong/lay_ds_hopdong_thuebao_tls?hdtbId=${data}`),
  
  getHT_PHIEU_TRA_TSL: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/sp_ht_phieu_tra_tsl`,data),

  getKIEMTRA_HEN_HDTB: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/sp_kiemtra_hen_hdtb `,data),

  getLay_DiaChi_LD: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/sp_lay_diachi_ld`,data),

  getlydotra: (axios, data) => axios.post(`/web-hopdong/thanhly/thanh_ly_map_id`,data),
  getDSLuongThaoTac: (axios, data) => axios.post(`/web-ccdv/khaibaoims/sp_lay_luong_thaotac`,data),


  getHuongGiaoByLuongID: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id`,data),

  getKiemTra_GiaoPhieu_VatTu: (axios, data) => axios.post(`/web-thicong/hoancongmegawan/fn_kiemtra_giaophieu_vattu`,data),


  getPortDauCuoi: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/lay_ds_down_uplink_theo_port_id`,data),
  getCapnhat_tt_phieugiao: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/capnhat_tt_phieugiao`,data),

  postCapnhat_noidung_th: (axios, data) => axios.post(`/web-hopdong/DiemChiaTrungGianTSL/capnhat_noidung_th`,data),

  lay_ds_thamso_md_ds_mats: (axios, data) => axios.post(`/web-quantri/vattu/lay_ds_thamso_md_ds_mats`,data),
  map_id: async (axios, data) => axios.post('/web-quantri/thamso/map_id',data),


  postHoanhThanh: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_hoanthanh_hoancongtsl`,data),

  postHoanThien: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_hths_hoancongtsl`,data),

  postCapNhat: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_capnhat_hoancongtsl`,data),


  lay_ds_hdtb_dv_loaidv: (axios, data) => axios.post(`/web-ccdv/dongbo_tb_codinh_lentongdai/lay_ds_hdtb_dv_loaidv`,data),
  lay_thamso_port: async (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/lay_thamso_port`,data),

  sp_capnhat_dslam_hdtb_tsl: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/sp_capnhat_dslam_hdtb_tsl`,data),
  
  ktra_dk_port: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/sp_ktra_dk_port`,data),
  capnhat_trangthai_port: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_capnhat_trangthai_port`,data),
  capnhat_trangthai_portid: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_capnhat_trangthai_portid`,data),
  capnhat_ds_matb: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_capnhat_ds_matb`,data),
  capnhat_port_vatly: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/capnhat_port_vatly`,data),
  capnhat_thongtin_port_tsl: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/capnhat_thongtin_port_tsl`,data),
  lay_dslam_theo_port_id: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/sp_lay_dslam_theo_port_id`,data),
  

  fn_kiemtra_hoancong_hoancongtsl: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_kiemtra_hoancong_hoancongtsl`,data),
  fn_thuchien_hoancong_hoancongtsl: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_thuchien_hoancong_hoancongtsl`,data),
  fn_co_thuebao_didong: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_co_thuebao_didong`,data),
  fn_theoloaihd_hoancong_hoancongtsl: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_theoloaihd_hoancong_hoancongtsl`,data),
  lay_ds_diachi_theo_hdkh_id: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/lay_ds_diachi_theo_hdkh_id`,data),
  lay_ds_diachi_theo_hdtt_id: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/lay_ds_diachi_theo_hdtt_id`,data),
  fn_kethuc_hoancong_hoancongtsl: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_ketthuc_hoancong_hoancongtsl`,data),  
  tudong_capnhat_toanha: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/tudong_capnhat_toanha`,data),

  fn_tt_hd_thuebao: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao`,data),
  fn_tt_huonggiao: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_huonggiao`,data),
  fn_tt_hdtb_tsl: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_tsl`,data),
  fn_tt_db_tsl: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_tsl`,data),
  fn_tt_donvi: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi`,data),
  fn_tt_lydotra_tc: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_lydotra_tc`,data), 
  fn_tt_giaophieu: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieu`,data), 
  sp_tt_thamso_md: (axios, data) => axios.post(`/web-hopdong/laydulieu/sp_tt_thamso_md`,data), 
  
}

