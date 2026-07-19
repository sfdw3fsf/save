export default {
  getDSLoaiHD: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD', data,header),
  NAP_DS_MUCDICH_DG: (axios, data) => axios.post('/web-quantri/loaihd-mucdich/sp_nap_ds_mucdich_dg', data),
  NAP_DS_MUCDICH_CG: (axios, data) => axios.post('/web-quantri/loaihd-mucdich/sp_nap_ds_mucdich_cg', data),
  load_ds_kieuld: (axios, data) => axios.post('/web-quantri/loaihd-mucdich/sp_ht_ds_kieuld', data),
  fn_delete_loaihd_md: (axios, data) => axios.post('/web-quantri/loaihd-mucdich/fn_delete_loaihd_md', data),
  fn_gan_loaihd_md: (axios, data) => axios.post('/web-quantri/loaihd-mucdich/fn_gan_loaihd_md', data),
  fn_update_loaihd_md: (axios, data) => axios.post('/web-quantri/loaihd-mucdich/fn_update_loaihd_md', data),
}
