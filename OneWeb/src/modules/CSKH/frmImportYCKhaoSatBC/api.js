export default {
  lay_ds_loaihinhtb: (axios, data) => axios.get(`/web-bancheo/khdn/lay_ds_loaihinhtb`),
  lay_ds_tinh: (axios, data) => axios.get(`/web-bancheo/khdn/lay_ds_tinh`),
  lay_tt_khdn_theo_makh: (axios, data) => axios.get(`/web-bancheo/khdn/lay_tt_khdn_theo_makh?ma_kh=${data.ma_kh}`),
  lay_tt_khdn_theo_makh_v2: (axios, data) => axios.get(`/web-bancheo/khdn/lay_tt_khdn_theo_makh_v2?ma_kh=${data.ma_kh}`),
  lay_diachi: (axios, data) => axios.post(`/web-bancheo/khdn/lay_diachi`),
  layhuonggiao_tiepnhan_bancheo_v2: (axios, data) => axios.post(`/web-bancheo/tracuu/layhuonggiao_tiepnhan_bancheo_v2`, data),
  get_keys: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/get_keys', data),
  sinh_magd: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd', data),
  sp_themmoi_hopdong_ks: async (axios, data) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_themmoi_hopdong_ks', data),
  fn_ins_update_hd_datmoi_bancheo_v2: async (axios, data) => axios.post('/web-baohong/TiepNhanBaoHong/fn_ins_update_hd_datmoi_bancheo_v2', data),
}
