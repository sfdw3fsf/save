export default {
  getDSLoaiBienLai: (axios, data) => axios.post('/web-quantri/loaibienlai/sp_lay_ds_loai_bienlai', data),
  getTuSoFromLuotTao: (axios, data) => axios.post('/web-quantri/loaibienlai/lay_tuso_from_luottao', data),
  getDSKhoBienLai: (axios, data) => axios.post('/web-quantri/loaibienlai/sp_lay_ds_kho_bienlai', data),
  getDSLuotTaoBienLai: (axios, data) => axios.post('/web-quantri/loaibienlai/lay_ds_luottao_vs_bienlai', data),
  delete_luot_tao: (axios, data) => axios.post('/web-quantri/loaibienlai/delete_luot_tao', data),
  Insert_LuotTao_BienLai: (axios, data) => axios.post('/web-quantri/loaibienlai/fn_insert_luottao_bienlai', data),
  Update_LuotTao_BienLai: (axios, data) => axios.post('/web-quantri/loaibienlai/fn_update_luottao_bienlai', data),
  Sinh_Bien_Lai_Auto: (axios, data) => axios.post('/web-quantri/loaibienlai/sinh_bienlai', data),
}
