export default {
  lay_luong_thaotac: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_luong_thaotac', data),
  giaophieu_td_khi_hc: async (axios, data) => axios.post('/web-hopdong/dongbo_tb_codinh_lentongdai/giaophieu_td_khi_hc', data),
  hoanthien_hs_thaydoi_matb: async (axios, data) => axios.post(`/web-hopdong/hoanthien_hoso_doiso_account/hoanthien_hs_thaydoi_matb`, data),
  giaophieu_td_host_thuhoi_1: async (axios, data) => axios.post('/web-hopdong/dongbo_tb_codinh_lentongdai/giaophieu_td_host_thuhoi_1', data)
}
