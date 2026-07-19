export default {
  getDanhSachUngDung: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_cbb_ungdung', {}),
  getDanhSachKetQua: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_cbb_ketqua', body),
  getDanhSachDonViTH: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_cbb_donvith', body),
  getDanhSachDonViNhan: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_cbb_donvinhan', body),
  getDanhSachLyDoTT: (axios) => axios.get('web-hopdong/capnhat-donhang/sp_cbb_lydott'),
  getDanhSachTuVan: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_grv_dstuvan', body),
  getThongTinGiaoViec: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_grv_thongtingiaoviec', body),
  getDanhSachLyDoTra: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_cbb_lydotra', body),
  kiemTraCanhBaoCapNhat: (axios, body) => axios.post('web-hopdong/laydulieu/sp_tt_nguoi_gt_home_yt', body),
  capNhat: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_udp_thongtin_ptdv', body),
  getDanhSachNhiemVuGiao: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_cbb_nhiemvugiao', body),
  getDanhSachKetQuaXuLy: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_cbb_kqxuly', body),
  getDanhSachNhanVienGiaoViec: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_grv_nhanvien_giaoviec', body),
  getDanhSachNhanVienDaGiaoViec: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_grv_dagiao_nv', body),
  giaoViecNhanVien: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_udp_tuvan_giaoviec', body),
  xoaGiaoViecNhanVien: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_del_grvnhiemvu', body),
  getThongTinNguoiGT: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_inf_nguoigt', body),
  getDanhSachLichSu: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_grv_lichsu', body),
  luuThongTinGT: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_udp_thongtingt', body),
  getThongTinHopDongCanGhep: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_inf_hdpl', body),
  getThongTinHopDongYeuCau: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_inf_hdyc', body),
  kiemTraHopDong: (axios, body) => axios.post('web-ccdv/tiepnhanhopdong/kiemtra_ghepma', body),
  ghepMaHopDong: (axios, body) => axios.post('web-hopdong/capnhat-donhang/sp_udp_ghepma', body),
  lapHopDong: (axios, body) => axios.post('web-hopdong/capnhat-donhang/fn_frmcapnhat_tt_ptdv_linkmenu', body),
  tuvan_ptdv_kyhd: (axios, body) => axios.post('web-hopdong/capnhat-donhang/tuvan_ptdv_kyhd', body),
  getThongTinNVMD: (axios, body) => axios.post('web-hopdong/laydulieu/sp_tt_nhanvien_macdinh', body),
  getThongTinLnv: (axios, body) => axios.post('web-hopdong/laydulieu/sp_tt_nhanvien_lnv', body),
  getThongTinB2aForm: (axios, body) => axios.post('web-hopdong/laydulieu/sp_tt_b2a_form', body),

  //thoains api
  getcboDichVuVT: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data),
  getcboLoaiHinhTBTheoDV: async (axios, data) => axios.get('/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu', data), 
  getCboTTThanhToan :async (axios) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_trangthai_tt', {}),
  getCboHinhThucTT: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_HINHTHUC_TT'),
  getCboDonViXuLyDon: (axios) => axios.get('/web-thicong/quanlydonhang/sp_lay_ds_donvi_xulydon'),
  getCboDonViTH:(axios) => axios.post('/web-thicong/quanlydonhang/sp_lay_donvi_thuchien_don',{}),
  getCboLyDoTra:(axios, p_huonggiao_id) => axios.get(`web-thicong/quanlydonhang/sp_lay_ds_lydotra?p_huonggiao_id=${p_huonggiao_id}`),

  // params: {p_ungdung_id, p_khdn, p_dichvuvt_id, p_loaitb_id, p_dichvuvt_id, p_ma_nv, p_so_dt, p_tungay, p_dengay, p_ma_gd, p_tthd_id}
  getDanhSachDonHang:  (axios, params) => axios.post('/web-thicong/quanlydonhang/sp_lay_ds_phieu_phangiao', params),

  getCboGoiCuoc : (axios, {vtinh_id, vloaitb_id}) => axios.get(`/web-bancheo/danhmuc/sp_ht_goicuoc_dd?vtinh_id=${vtinh_id}&vloaitb_id=${vloaitb_id}`),
  getCboLoaiKH: (axios) => axios.post('/web-thicong/quanlydonhang/sp_lay_ds_loai_kh', {}),
  ghiLaiLyDoHuy: (axios, params) => axios.post('/web-hopdong/quanlydonhang/tuvan_thoaitra_v3', params),

  getInfoOrderMoney: (axios, p_hdtb_id) => axios.post('/web-hopdong/quanlydonhang/sp_lay_thongtin_tien_donhang', {p_hdtb_id})

}
