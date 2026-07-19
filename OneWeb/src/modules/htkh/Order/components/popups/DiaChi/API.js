export default {
  lay_thongtin_tinh: async (axios) => axios.get('/web-hopdong/lapdatmoi/sp_lay_ds_tinhthanh'),
  lay_ds_quan_huyen: async (axios, tinhId) => axios.get(`/web-cabman/bando_tuyencot/lay_ds_quan_huyen/${tinhId}`),
  lay_ds_phuong_xa: async (axios, huyenId) => axios.get(`/web-cabman/bando_tuyencot/lay_ds_phuong_xa/${huyenId}`),
  lay_ds_pho_ap_khu: async (axios, phuongId) => axios.get(`/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/${phuongId}`),
  lay_ds_dacdiem: async (axios, params) => axios.get('/web-cabman/bando_tuyencot/lay_ds_dacdiem', {params: params}),
  isExistMaTS: async (axios, params) => axios.get('/web-cabman/danhmuc/isExistMaTS', {params: params})
}
