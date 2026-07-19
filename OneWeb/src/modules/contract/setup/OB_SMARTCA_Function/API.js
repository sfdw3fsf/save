export default{
  // form chính
  getdsTTKetNoi: (axios) => axios.get(`/web-tratruoc/ob_ghtt/sp_lay_tt_ketnoi`),
  getListKetQuaOB: (axios) => axios.get(`/web-tratruoc/ob_ghtt/sp_lay_ketqua_ob`),
  KetQuaOB_new: (axios, data) => axios.post('web-tratruoc/ob_ghtt/sp_lay_ketqua_ob_theo_nv', data),
  laydsThueBao_KH: (axios, data) => axios.post('/web-tratruoc/ob_ghtt/sp_lay_thongtin_thuebao_cts', data),
  layThongtinKHG: (axios, data) => axios.post('/web-tratruoc/ob_ghtt/sp_lay_thongtin_kh', data),
  capnhat_ketqua_OB: (axios, data) => axios.post('web-tratruoc/ob_ghtt/fn_capnhat_kq_ob_smartca', data),
  lay_lichsu_OB: (axios, data) => axios.post('web-tratruoc/ob_ghtt/sp_lay_lichsu_ob', data),

  

  // popup Nhân vien CSKH
  lay_thongtin_tinh: async (axios) => axios.get('/web-hopdong/lapdatmoi/sp_lay_ds_tinhthanh'),
  lay_ds_quan_huyen: async (axios, tinhId) => axios.get(`/web-cabman/bando_tuyencot/lay_ds_quan_huyen/${tinhId}`),
  lay_ds_phuong_xa: async (axios, huyenId) => axios.get(`/web-cabman/bando_tuyencot/lay_ds_phuong_xa/${huyenId}`),
  lay_ds_pho_ap_khu: async (axios, phuongId) => axios.get(`/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/${phuongId}`),
  lay_ds_dacdiem: async (axios, params) => axios.get('/web-cabman/bando_tuyencot/lay_ds_dacdiem', {params: params}),
  layDanhSach_NV_CSKH: (axios, data) => axios.post('/web-tratruoc/ob_ghtt/sp_lay_ds_nhanvien_cskh', data),

  // popup Gói tư vấn
  laygoi_TuVan: (axios, data) => axios.post('/web-tratruoc/ob_ghtt/sp_thongtin_goi_tuvan', data),

  // popup Gửi Email
  guiEmail: (axios, data) => axios.post('/tichhop/smartca/sendMailOB', data),
}
  