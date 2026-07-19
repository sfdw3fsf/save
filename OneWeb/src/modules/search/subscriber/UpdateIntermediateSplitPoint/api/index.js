export default {
  HT_DichVuVT_Combobox: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
  lay_db_theo_matb: (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_db_theo_matb', data),
  HT_LoaiHinh_TB_Combobox: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
  lay_ds_diemchia_tg: (axios, id) => axios.get('/web-hopdong/capnhat-diemchiatg/lay_ds_diemchia_tg', {params: {thuebao_id: id}}),
  fn_capnhat_diemchia_tg: (axios, data) => axios.post('/web-hopdong/capnhat-diemchiatg/fn_capnhat_diemchia_tg', data),
}
