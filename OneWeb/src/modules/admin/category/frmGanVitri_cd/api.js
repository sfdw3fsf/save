export default {
  sp_lay_ds_chucdanh: (axios, data) => axios.get('/web-quantri/chucdanh/sp_lay_ds_chucdanh', data),
  sp_lay_ds_vitri_chucdanh: (axios, data) => axios.post('/web-quantri/chucdanh/sp_lay_ds_vitri_chucdanh', data),
  sp_them_vitri_chucdanh: (axios, data) => axios.post('/web-quantri/chucdanh/sp_them_vitri_chucdanh', data),
  sp_xoa_vitri_chucdanh: (axios, data) => axios.post(`/web-quantri/chucdanh/sp_xoa_vitri_chucdanh`, data),
  get_keys: (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
}