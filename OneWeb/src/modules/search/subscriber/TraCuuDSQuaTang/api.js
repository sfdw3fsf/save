export default {
  getDanhSachDaiVienThong: (axios, params) => axios.get('/web-tracuu/port/ds_dai_vt', { params }),
  getDanhSachPortTheoDaiVienThong: (axios, params) => axios.get('/web-tracuu/port/ds_port_theo_dai_vt', { params }),
  sp_tracuu_tangqua_yagi_v2: (axios, data) => axios.post('/web-tracuu/tracuu/sp_tracuu_tangqua_yagi_v2', data),
  fn_capnhat_trangthai_phieu_tangqua: (axios, data) => axios.post('/web-tracuu/tracuu/fn_capnhat_trangthai_phieu_tangqua', data),
  sp_tracuu_file_yagi: (axios, data) => axios.post('/app-thicong/nghiemthubaohong/sp_tracuu_file_yagi', data),
}
