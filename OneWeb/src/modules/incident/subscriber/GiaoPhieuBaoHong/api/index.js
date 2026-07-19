export default {
  trangthai_phieu: (axios, params) => axios.get('web-baohong/danhmuc/trangthai_phieu', { params }),
  fn_frmgiaophieubh_load: (axios, data) => axios.post('web-baohong/giaophieu-baohong/fn_frmgiaophieubh_load', data),
  getDataHienThiDanhSach: (axios, data) => axios.post('web-baohong/giaophieu-baohong/fn_hienthi_danhsach_frmgiaophieubh', data),
  getDsDonViTheoHuongGiao: (axios, data) => axios.post('web-baohong/giaophieu-baohong/lay_donvi_ldv_theo_huonggiao', data),
  fn_lay_ds_huonggiao_frmgiaophieubh: (axios, data) => axios.post('web-baohong/giaophieu-baohong/fn_lay_ds_huonggiao_frmgiaophieubh', data),
  getDsBaoHong: (axios, data) => axios.post('web-baohong/baohong/timkiem', data),
  sp_giaophieubh_layds_quytrinh: (axios, data) => axios.post('web-baohong/giaophieu-baohong/sp_giaophieubh_layds_quytrinh', data),
  huyGiaoPhieu: (axios, data) => axios.post('web-baohong/baohong/fn_tsbtnhuygiao_click_frmgiaophieubh', data),
  capNhatDV: (axios, data) => axios.post('web-baohong/giaophieu-baohong/capnhat_donvi_bh', data),
  giaoPhieu: (axios, data) => axios.post('web-baohong/giaophieu-baohong/fn_giaophieu_frmgiaophieubh', data),
  getKey: (axios, data) => axios.post('web-hopdong/khoiphucthanhly/get_keys', data)
}
