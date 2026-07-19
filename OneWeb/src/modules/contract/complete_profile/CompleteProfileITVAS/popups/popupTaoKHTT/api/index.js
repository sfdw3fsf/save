export default {
  // API
  fn_khaibao_api: (axios, data) => axios.post('web-ccdv/tao_khachhhang_ttkd/fn_khaibao_api', data),
  sp_lay_thongtin_khoitao_acc_khtt: (axios, data) => axios.post('web-ccdv/tao_khachhhang_ttkd/sp_lay_thongtin_khoitao_acc_khtt', data),
  fn_capnhat_thongtin_khachhang_ttkd_brn: (axios, data) => axios.post('web-ccdv/tao_khachhhang_ttkd/fn_capnhat_thongtin_khachhang_ttkd_brn', data),
  fn_capnhat_khachhang_ttkd_brn: (axios, data) => axios.post('web-ccdv/tao_khachhhang_ttkd/fn_capnhat_khachhang_ttkd_brn', data),
  TraCuuKhachHang: (axios, data) => axios.post('/tichhop/brandname/TraCuuKhachHang', data),
  TAO_KHTTKD: (axios, data) => axios.post('/tichhop/brandname/TAO_KHTTKD', data),
  UPDATE_KHTTKD: (axios, data) => axios.post('/tichhop/brandname/UPDATE_KHTTKD', data),

  cap_nhat_status_hdtb: async (axios, data, header) => axios.post('/web-ccdv/tram-thicong-dv-cntt/cap_nhat_status_hdtb', data, header)
  // fn_khaibao_api: (axios, params) => axios.get('web-thicong/kichhoat-brandname/fn_khaibao_api', { params })
}
