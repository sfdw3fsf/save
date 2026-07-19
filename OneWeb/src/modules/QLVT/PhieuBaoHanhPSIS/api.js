export default {
  baohanhtracuuthongtinphieu: (axios, data) => axios.post('/tichhop/psis/baohanhtracuuthongtinphieu', data),
  sp_ds_khoncc_doitac: (axios, data) => axios.post('/web-qlvt/api/tra-cuu-bao-hanh/sp_ds_khoncc_doitac', data),
  sp_ds_phieubh_psis: (axios, data) => axios.post('/web-qlvt/api/tra-cuu-bao-hanh/sp_ds_phieubh_psis', data),
  sp_capnhat_phieubh_psis: (axios, data) => axios.post('/web-qlvt/api/tra-cuu-bao-hanh/sp_capnhat_phieubh_psis', data),
  sp_lay_ds_phieubhtb_giaoden_psis: (axios, data) => axios.post('/web-qlvt/api/tra-cuu-bao-hanh/sp_lay_ds_phieubhtb_giaoden_psis', data),
  fn_gan_phieubh_psis: (axios, data) => axios.post('/web-qlvt/api/tra-cuu-bao-hanh/fn_gan_phieubh_psis', data),
  fn_go_phieubh_psis: (axios, data) => axios.post('/web-qlvt/api/tra-cuu-bao-hanh/fn_go_phieubh_psis', data),
  taophieubaohanh: (axios, data) => axios.post('/web-tracuu/psis/taophieubaohanh', data),
  huyphieubaohanh: (axios, data) => axios.post('/web-tracuu/psis/huyphieubaohanh', data),
}
