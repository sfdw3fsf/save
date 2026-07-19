export default {
  getDSThuocTinh: async (axios, data, header) => axios.get('/web-quantri/gan-thuoctinh-loaivattu/lay_ds_thuoctinh_vt', data,header),
  NAP_DS_VATTU_DG: (axios, data) => axios.post('/web-quantri/gan-thuoctinh-loaivattu/lay_ds_vattu_dg', data),
  NAP_DS_VATTU_CG: (axios, data) => axios.post('/web-quantri/gan-thuoctinh-loaivattu/lay_ds_vattu_cg', data),
  gan_thuoctinh_vattu: (axios, data) => axios.post('/web-quantri/gan-thuoctinh-loaivattu/gan_thuoctinh_vattu', data),
  go_thuoctinh_vattu: (axios, data) => axios.post('/web-quantri/gan-thuoctinh-loaivattu/go_thuoctinh_vattu', data),
}
