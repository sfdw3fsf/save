export default {
  getDanhSachDonVi: (axios, params) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/lay_ds_donvi_theo_loaidv', params),
  getDanhSachLyDoTra: (axios, params) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/lay_ds_lydotra', params),
  getDanhSachTocDoKenh: (axios, params) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/lay_ds_tocdo_kenh', params),
  getDanhSachLoaiHD: (axios, params) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/lay_ds_loai_hd', params),
  getDanhSachNhanVienTheoDonVi: (axios, params) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/lay_ds_nhanvien_theo_dv', params),
  getDanhSachThueBao: (axios, params) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/lay_ds_phieu_giao_diemchia_tg', params),
  getDanhSachNhanVienTheoPhieuID: (axios, params) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_ds_nhanvien_theo_phieu_id', params),
  updateNoiDungTH: (axios, params) => axios.post('/web-hopdong//DiemChiaTrungGianTSL/capnhat_noidung_th', params),
  getHopDongTSLDiemDau: (axios, params) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/lay_hdtb_tsl_diemdau', params),
  getHopDongTSLDiemCuoi: (axios, params) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/lay_hdtb_tsl_diemcuoi', params),
  updateTTPhieu: (axios, params) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/capnhat_tt_phieu', params),
  updateTrangThaiPhieu: (axios, params) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/capnhat_trangthai_phieugiao', params),
  updateTraPhieu: (axios, params) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/traphieu_dctg', params),
  getDanhSachHuongGiao: (axios, params) => axios.post('/web-ccdv/giaophieu/sp_lay_ds_huonggiao', params),

}
