export default {
  LAY_DANHMUC_VTCI: (axios) => axios.get('/web-hopdong/ThueBao-VienThong-CongIch/lay_danhmuc_vtci'),
  LAY_DS_TBVTCI: (axios, data) => axios.post('/web-hopdong/bss_group1/lay_ds_tbvtci', data),
  CSS_LOAIHINH_TB: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
  KT_DL: (axios, data) => axios.post('/web-hopdong/ThueBao-VienThong-CongIch/fn_kt_tb_goi_vtci_v2', data),
  lay_danhba_theo_matb: (axios, data) => axios.post('/web-hopdong/dichvu-ca/lay_danhba_theo_matb', data),
  LAY_DS_GOI_VTCI: (axios, data) => axios.post('/web-hopdong/ThueBao-VienThong-CongIch/lay_ds_goi_vtci', data),
  CAPNHAT_VTCI: (axios, data) => axios.post('/web-hopdong/ThueBao-VienThong-CongIch/capnhat_vtci', data),
  fn_xet_duyet_tb_vtci: (axios, data) => axios.post('/web-hopdong/ThueBao-VienThong-CongIch/fn_xet_duyet_tb_vtci', data),

  upload_file: async (axios, data, header) => axios.post('/web-quantri/upload', data,header),
  view_file: async (axios, data) => axios.post('/web-quantri/upload/link_view_file', data),
  getTTND: (axios) => axios.post('/quantri/user/thongtin_nguoidung2', {}),
  get_key: async (axios, keyname) => axios.post('/web-hopdong/khoiphucthanhly/get_keys', {"keyname": keyname}),
}
