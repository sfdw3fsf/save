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

  getDanhSachQuyTrinh: (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_lay_ds_quytrinh_v2', data),


  getDanhSachHuongGiao: (axios, data) => axios.get(`/web-hopdong/danhmuc/ds_huonggiao/${data.quytrinh_id}/${data.tthd_id}`),


  getDanhSachThueBao: (axios, data) => axios.post('/web-ccdv/khaibaotsl/lay_ds_phieu_giaoden_tsl', data),


  getTongTien: (axios, data) => axios.post('/web-ccdv/khaibaotsl/lay_tongtien_theo_hdkh_id', data),

  getKenhThu: (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_lay_kenhthu_theo_hdkh', data),

  getThongTinLH: (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_lay_thongtin_lh_lapdat_tsl', data),

  getDanhSachThietBi: (axios, data) => axios.get(`/web-hopdong/danhmuc/ds_loaitb/${data.dichvuvt_id}`),

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

  postHoanhThanh: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_hoanthanh_hoancongtsl`,data),

  postHoanThien: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_hths_hoancongtsl`,data),

  postCapNhat: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/fn_capnhat_hoancongtsl`,data),

}

