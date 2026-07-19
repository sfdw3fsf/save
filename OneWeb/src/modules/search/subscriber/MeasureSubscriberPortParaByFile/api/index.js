export default {
  sp_lay_dulieu_dokiem_theofile: (axios, data) => axios.post('/web-hopdong/dothongso-congthuebao-theofile/sp_lay_dulieu_dokiem_theofile', data),
  sp_insert_ma_tb_dokiem: (axios, data) => axios.post('/web-hopdong/dothongso-congthuebao-theofile/sp_insert_ma_tb_dokiem', data),
  sp_dokiem_theo_matb: (axios, data) => axios.post('/web-hopdong/dothongso-congthuebao-theofile/sp_dokiem_theo_matb', data),

  fn_dokiem_theo_matb: (axios, id) => axios.get('/web-hopdong/dothongso-congthuebao-theofile/fn_dokiem_theo_matb', {params: {p_ma_tb: id}}),

  sp_capnhat_dokiem: (axios, data) => axios.post('/web-hopdong/dothongso-congthuebao-theofile/sp_capnhat_dokiem', data),

  testPortV2: (axios, data) => axios.post('/tichhop/bnm/testPortV2', data),

  getTTND: (axios) => axios.post('/quantri/user/thongtin_nguoidung2', {}),
}
