export default {
  getDsBangTS: (axios, data) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DS_BANGTS', data),
  getDsDichVuVT: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data),  
  getLoaiHinhTB: (axios, data) => axios.post('/web-quantri/menu/lay_ds_loaihinh_tb_theo_kieuld', data),
  getDsNhomMenu: (axios, data) => axios.post('/web-quantri/menu/sp_ds_nhapdltudien', data),
  getDsCotTB: (axios, data) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DS_COT', data),
  getDsTenCotTRB: (axios, data) => axios.post('/web-quantri/menu/sp_ds_tencotrb', data),
  getDsCot: (axios, data) => axios.post('/web-quantri/menu/lay_ds_cot', data),
  postDLTuDien: (axios, data) => axios.post('/web-quantri/menu/sp_capnhat_nhapdltudien', data),

}
