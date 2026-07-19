export default {
  getDanhSachDaiVienThong: (axios, params) => axios.get('/web-tracuu/port/ds_dai_vt', { params }),
  getDanhSachPortTheoDaiVienThong: (axios, params) => axios.get('/web-tracuu/port/ds_port_theo_dai_vt', { params })
}
