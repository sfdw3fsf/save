export default {
  sp_lay_tt_combobox_dm_toanha: async (axios, data, header) => axios.get('/web-toanha/danhmuc-toanha/sp_lay_tt_combobox_dm_toanha', data, header),

  QLTN_DOITAC: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_DOITAC', data, header),
  CSS_LOAI_TOANHA: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_TOANHA', data, header),
  CSS_LOAICAP_TOANHA: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAICAP_TOANHA', data, header),
  CSS_PHAMVI_DTU_MANG: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_PHAMVI_DTU_MANG', data, header),
  CSS_TN_PHANCHIA_DT: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_TN_PHANCHIA_DT', data, header),
  CSS_DOITUONG_DUAN: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_DOITUONG_DUAN', data, header),
  CSS_HIENTRANG_TOANHA: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_HIENTRANG_TOANHA', data, header),
  CSS_CHUQUAN: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_CHUQUAN', data, header),
  sp_lay_ds_toanha: async (axios, data, header) => axios.post('/web-toanha/danhmuc-toanha/sp_lay_ds_toanha', data, header),
  lay_tt_nhanvien_toanha: async (axios, data, header) => axios.post('/web-quantri/nhanvien/lay_tt_nhanvien_toanha', data, header),
  sp_ghilai_toanha: async (axios, data, header) => axios.post('/web-toanha/danhmuc-toanha/sp_ghilai_toanha', data, header),
  sp_del_toanha_by_id: async (axios, data, header) => axios.post('/web-toanha/danhmuc-toanha/sp_del_toanha_by_id', data, header),

  sp_lay_ds_doi_vt_by_dvi: async (axios, data, header) => axios.post('/web-toanha/danhmuc-toanha/sp_lay_ds_doi_vt_by_dvi', data, header),
  fn_dongia_delete: async (axios, data, header) => axios.post('/web-quantri/dongia/fn_dongia_delete', data, header),

  kiemtra_quyen_toanha: async (axios, data) => axios.get('/web-toanha/danhmuc-hopdong-dautu/kiemtra-quyen-toanha?' + data),
  lay_ds_diachi_duan: async (axios, data, header) => axios.post('/web-quantri/toanha/lay_ds_diachi_duan', data, header),
  lay_loai_tt_duan: async (axios, data) => axios.post('/web-quantri/toanha/lay_loai_tt_duan_v2', data),

  tt_quangcao_toanha: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/tt_quangcao_toanha', data),
  cp_hoahong_toanha: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/cp_hoahong_toanha', data),
  fn_capnhat_ct_nhacc_toanha: async (axios, data) => axios.post('/web-quantri/toanha/fn_capnhat_ct_nhacc_toanha', data),

}





