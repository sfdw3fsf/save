export default{
  getDichvu: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data),
  getLoaihinh: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', {params: data}),
  getKieuyeucau: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD', {params: data}),
  getDonviTT: (axios, data) => axios.post('/web-quantri/thutienphatsinh/sp_ht_donvi_combobox', data),
  getDonviQL: (axios, data) => axios.post('/web-quantri/thutienphatsinh/sp_ht_donvi_combobox', data),
  getPhuluc: (axios, data) => axios.post('/web-quantri/thutienphatsinh/lay_ds_hdtb_thukhac', data),
  getTTKM: (axios, data) => axios.post('/web-quantri/thutienphatsinh/lay_ds_kmtt_thukhac_v2', data),
  getDataByTB: (axios, data) => axios.post('/web-quantri/thutienphatsinh/sp_lay_ds_dbtb_dv', data),
  getDataByTBID: (axios, data) => axios.post('/web-quantri/thutienphatsinh/lay_ds_db_dd_theo_tbid', data),

  lay_ds_hopdong_theo_ma_gd: (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', data),
  lay_ds_hopdong_theo_matb: (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_matb', data)
}
