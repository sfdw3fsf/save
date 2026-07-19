// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=343386414&range=A2:F2
// https://api-onebss-dev.vnptit3.vn/web-gqkn/swagger-ui/#/UR5.1.008
// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=803715326&range=D1001
// frmDaiLy_XLKN
export default {
  // Danh muc
  getDmQuyTrinhKhieuNais: (axios, data) => axios.post('/web-gqkn/danhmuc/quytrinh_khieunai', data),
  getDmChuDeKhieuNais: async (axios, data) => axios.post('/web-gqkn/danhmuc/chude_khieunai', data),
  getDmKetQuaLois: async (axios, data) => axios.post('/web-gqkn/danhmuc/ketqua_khieunai', data),
  getDmKetQuaXuLys: async (axios, data) => axios.get('/web-gqkn/danhmuc/ketqua_xuly', data),
  getDmNhanVienXuLys: async (axios, data) => axios.get('/web-quantri/danhmuc/donvi/' + data.id + '/nhanviens', data),
  lay_dulieu_bang_theo_dieukien: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
  lay_ds_huong_giao: async (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/lay_ds_huong_giao?quytrinhid=${data.quytrinhid}&ttknId=${data.ttknId}`, data),
  lay_thongtin_table: async (axios, data) => axios.post('/web-ccdv/khaibaoims/lay_thongtin_table', data),
  lay_luong_thaotac: (axios, data) => axios.post(`/web-ccdv/khaibaoims/sp_lay_luong_thaotac`, data),
  kiemtra_chudekn_knbs: (axios, data) => axios.post(`/web-ccdv/khaibaoims/kiemtra_chudekn_knbs`, data),
  laythangno: (axios, data) => axios.get(`/web-quantri/thamso-quanly-thuno/laythangno`, data),
  // UR2.6.019_007 ->UR2.5.012_006 Lấy danh sách control thao tác theo id thao tác
  lay_ct_thaotac_control: (axios, data) => axios.post(`/web-thicong/hoancongmegawan/sp_lay_ct_thaotac_control`, data),
  // Update
  capnhatTrangThaiKhieuNai: async (axios, data) => axios.post('/web-gqkn/khieunai/phieu_khieunai/trangthai', data),
  capnhatKhieuNaiKemLoi: async (axios, data) => axios.post('/web-gqkn/khieunai/capnhat_khieunai_kem_loi', data),
  capnhatKhieuNai: async (axios, data) => axios.post('/web-gqkn/khieunai/capnhat_khieunai', data),
  capnhatKetQuaKhieuNai: async (axios, data) => axios.post('/web-gqkn/khieunai/capnhat_ketqua_khieunai', data),
  capNhatNhanVienGiaoXuLy: async (axios, data) => axios.post('/web-gqkn/khieunai/capnhat_nhanvien_giaiquyet_khieunai', data),
  capNhatKhoaPhieu: async (axios, data) => axios.post('/web-gqkn/khieunai/khoa_phieu', data),
  capNhatGiaoXuLy: async (axios, data) => axios.post('/web-gqkn/khieunai/capnhat_nhanvien_giaiquyet_khieunai', data),
  capNhatBaoTonPhieu: async (axios, data) => axios.post('/web-gqkn/khieunai/baoton_phieu', data),
  // Data
  getChiTietPhieu: async (axios, data) => axios.post('/web-gqkn/khieunai/ds_phieu_khieunai', data),
  getBoXungKhieuNai_Old: async (axios, data) => axios.post('/web-gqkn/khieunai/ds_bs_phieu_khieunai', data),
  getBoXungKhieuNai: async (axios, data) => axios.get(`/web-gqkn/khieunai/sp_lay_ds_khieunai_bs_by_id/${data.khieunai_id}`, data),
  getDanhSachPhieus: async (axios, data) => axios.post('/web-gqkn/khieunai/ds_xuly_khieunai', data),
  getPhieuBaoTons: async (axios, data) => axios.post('/web-gqkn/khieunai/baoton_phieu', data),
  getDanhSachThueBaoTheoKyHoaDon: async (axios, data) => axios.post('/web-gqkn/tracuu/thuebao/theo_kyhoadon', data),
  map_id: (axios, data) => axios.post(`/web-hopdong/thanhly/thanh_ly_map_id`, data),
  fn_kiemtra_chudekn_knbs: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_kiemtra_chudekn_knbs`, data),
  fn_tt_giaophieu_kn: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieu_kn`, data),
  fn_tt_donvi: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi`, data),
  fn_tt_ds_quyen_nd: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_ds_quyen_nd`, data),
  ds_thamso_md: (axios, data) => axios.get(`/web-quantri/danhmuc/ds_thamso_md`, data),
  fn_xulykn_capnhat: (axios, data) => axios.post(`/web-gqkn/khieunai/xu-ly-khieu-nai/fn_xulykn_capnhat`, data),
  //
  fn_daily_xlkn_capnhat: (axios, data) => axios.post(`/web-gqkn/khieunai/fn_daily_xlkn_capnhat`, data),
  fn_daily_xlkn_giaoxl: (axios, data) => axios.post(`/web-gqkn/khieunai/fn_daily_xlkn_giaoxl`, data),
  fn_daily_xlkn_khoaphieu: (axios, data) => axios.post(`/web-gqkn/khieunai/fn_daily_xlkn_khoaphieu`, data),
  fn_daily_xlkn_ton: (axios, data) => axios.post(`/web-gqkn/khieunai/fn_daily_xlkn_ton`, data),
  fn_daily_xlkn_huygiao: (axios, data) => axios.post(`/web-gqkn/khieunai/fn_daily_xlkn_huygiao`, data)

}
