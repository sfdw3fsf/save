export default {
  sp_ht_donvi_loaidonvi: (axios, data) => axios.post('/web-quantri/nhanviennganhnghe/sp_ht_donvi_loaidonvi', data),
  ADMIN_LOAI_NV: (axios, params) => axios.get('web-quantri/danhmuc-chung/ADMIN_LOAI_NV', { params }),
  CSS_LOAI_KV: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_KV', { params }),
  sp_ds_nhanvien_nganhnghe: (axios, data) => axios.post('/web-quantri/nhanviennganhnghe/sp_ds_nhanvien_nganhnghe', data),
  sp_nhanvien_nganhnghe_ds_dagan: (axios, data) => axios.post('/web-quantri/nhanviennganhnghe/sp_nhanvien_nganhnghe_ds_dagan', data),
  sp_nhanvien_nganhnghe_ds_chuagan: (axios, data) => axios.post('/web-quantri/nhanviennganhnghe/sp_nhanvien_nganhnghe_ds_chuagan', data),
  fn_nhanvien_nganhnghe_update: (axios, data) => axios.post('/web-quantri/nhanviennganhnghe/fn_nhanvien_nganhnghe_update', data)
}
