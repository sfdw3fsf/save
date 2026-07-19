export default {
  thongtin_nguoidung2: (axios) => axios.post('quantri/user/thongtin_nguoidung2', {}),

  lay_ds_goi_mnp: (axios, data) => axios.post('/web-hopdong/dk_goidoanhnghiep_mnp/lay_ds_goi_mnp', data),
  lay_ds_km_mnp: (axios, data) => axios.post('/web-hopdong/dk_goidoanhnghiep_mnp/lay_ds_km_mnp', data),
  sp_lay_thuebao_id_huygoicuocmnp: (axios, data) => axios.post('/web-hopdong/dk_goidoanhnghiep_mnp/sp_lay_thuebao_id_huygoicuocmnp', data),
  sp_lay_thuebao_id_dkygoicuocmnpcanhan: (axios, data) => axios.post('/web-hopdong/dk_goidoanhnghiep_mnp/sp_lay_thuebao_id_dkygoicuocmnpcanhan', data),
  sp_lay_biendong_goidadv_id_dkygoicuocmnpcanhan: (axios, data) => axios.post('/web-hopdong/dk_goidoanhnghiep_mnp/sp_lay_biendong_goidadv_id_dkygoicuocmnpcanhan', data),
  sp_lay_thuebao_id_khuyenmai_dbtb_dkygoicuocmnpcanhan: (axios, data) => axios.post('/web-hopdong/dk_goidoanhnghiep_mnp/sp_lay_thuebao_id_khuyenmai_dbtb_dkygoicuocmnpcanhan', data),
  cn_goi_mnp: (axios, data) => axios.post('/web-hopdong/dk_goidoanhnghiep_mnp/cn_goi_mnp', data),
  cn_nhomtb_id_neo: (axios, data) => axios.post('/web-hopdong/dk_goidoanhnghiep_mnp/cn_nhomtb_id_neo', data),
  cn_nhomkm_id_neo: (axios, data) => axios.post('/web-hopdong/dk_goidoanhnghiep_mnp/cn_nhomkm_id_neo', data),
  sp_check_acccount_gdv_dkygoicuocmnpdoanhnghiep: (axios, data) => axios.post('/web-hopdong/dk_goidoanhnghiep_mnp/sp_check_acccount_gdv_dkygoicuocmnpdoanhnghiep', data),

  ts_mnp_check_tb: (axios, so_tb) => axios.post('ccbs/executor/ts_mnp_check_tb', {so_tb}),
  ts_mnp_check_tb_khdn: (axios, so_tb) => axios.post('ccbs/executor/ts_mnp_check_tb_khdn', {so_tb}),
  ts_mnp_ds_khdn_goicuoc: (axios, data) => axios.post('/ccbs/executor/ts_mnp_ds_khdn_goicuoc', data),
  ts_mnp_ds_goicuoc: (axios, data) => axios.post('/ccbs/executor/ts_mnp_ds_goicuoc', data),
  ts_mnp_ds_timkiem: (axios, data) => axios.post('/ccbs/executor/ts_mnp_ds_timkiem', data),
  ts_mnp_huy_goi: (axios, data) => axios.post('/ccbs/executor/ts_mnp_huy_goi', data),
  ts_mnp_khdn_huy_goi: (axios, data) => axios.post('/ccbs/executor/ts_mnp_khdn_huy_goi', data),
  ts_mnp_dangky_goi: (axios, data) => axios.post('/ccbs/executor/ts_mnp_dangky_goi', data),
  ts_get_package_info: (axios, data) => axios.post('/ccbs/executor/ts_get_package_info', data),
}
