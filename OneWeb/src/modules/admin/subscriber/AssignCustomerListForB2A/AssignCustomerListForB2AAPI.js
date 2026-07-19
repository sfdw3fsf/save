export default {
  post_getds_onload: async (axios, data) => axios.post('/web-quantri/tap_khachhang_b2a/lay_ds_chinhsach_phangiao_and_phongbanhang', data),
  post_getds_phangiao_chua_giao: async (axios, data) => axios.post('/web-quantri/tap_khachhang_b2a/lay_ds_phangiao_chua_giao', data),
  post_getds_phangiao_da_giao: async (axios, data) => axios.post('/web-quantri/tap_khachhang_b2a/lay_ds_phangiao_da_giao', data),
  post_getds_nlv_theo_donvi: async (axios, data) => axios.post('/web-quantri/tap_khachhang_b2a/lay_ds_nlv_theo_donvi', data),
  post_upd_phangiao: async (axios, data) => axios.post('/web-quantri/tap_khachhang_b2a/upd_phangiao', data),

  b2a_lay_ds_donvi_ql: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_lay_ds_donvi_ql', data),
  b2a_lay_ds_chuagiao: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_lay_ds_chuagiao', data),
  b2a_lay_ds_chuagiao_v2: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_lay_ds_chuagiao_v2', data),
  b2a_lay_khuvuc_theo_donvi: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_lay_khuvuc_theo_donvi', data),
  b2a_lay_quan_theo_phongbh: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_lay_quan_theo_phongbh', data),
  b2a_get_phuong_theo_quan: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_get_phuong_theo_quan', data),
  b2a_lay_ds_nhanvien_duocgiao: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_lay_ds_nhanvien_duocgiao', data),
  b2a_phangiao: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_phangiao', data),
  b2a_phangiao_v2: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_phangiao_v2', data),
  b2a_huy_phangiao: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_huy_phangiao', data),
  b2a_huy_phangiao_v2: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_huy_phangiao_v2', data),
  b2a_lay_danhsach_dagiao: async (axios, data) => axios.post('/web-hopdong/hopdong/b2a_lay_danhsach_dagiao', data),
  CSS_LOAIHINH_TB: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', data)
}
