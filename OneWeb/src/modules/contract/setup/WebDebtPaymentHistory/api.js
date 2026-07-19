export default {
  getDanhSachChuKyNo: (axios) => axios.post('web-hopdong/lichsu_no_thanhtoan/lay_ds_chukyno'),
  getKyHDHienTai: (axios) => axios.get('web-quantri/thamso-quanly-thuno/laythangno'),
  traCuuLSTra: (axios, data) => axios.post('ccbs/qltn/ts_tracuu_ls_tra', data),
  traCuuNoTongHop: (axios, data) => axios.post('ccbs/qltn/ts_tt_no_tonghop', data)
}
