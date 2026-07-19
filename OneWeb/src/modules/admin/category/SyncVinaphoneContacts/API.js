export default{
  // UR1.5.019_001 - load form
  fn_load_dl_dongbo_vnp: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/fn_load_dl_dongbo_vnp', data),
  // UR1.5.019_004 + UR1.5.019_006 => loai in (1,2) - Lấy dữ liệu thuê bao (thuê bao loai = 1, khach hang loai = 2)
  lay_dl_danhba_vnp: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/lay_dl_danhba_vnp', data),
  // UR1.5.019_008 Hiển thị danh sách
  fn_ht_ds_dongbo_vnp: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/fn_ht_ds_dongbo_vnp', data),
  // UR1.5.019_008_1_001 - Hiển thị danh sách chuẩn hoá tuyến thu
  lay_ds_chuanhoa_tuyenthu_vnp: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/lay_ds_chuanhoa_tuyenthu_vnp', data),
  // UR1.5.019_008_1_002 - Nút chuẩn hoá tuyến thu
  fn_chuanhoa_tuyenthu_dongbo_vnp: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/fn_chuanhoa_tuyenthu_dongbo_vnp', data),
  // UR1.5.019_008_1_003 - Nút cap nhat
  capnhat_danhsach_thuebao: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/capnhat-danhsach-thuebao', data),
  // UR1.5.019_008_001  + UR1.5.019_013 - Nút địa danh
  kiemtra_dl_laytu_vina: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/kiemtra_dl_laytu_vina', data),
  // UR1.5.019_008_002 - Nút chuẩn hoá
  chuanhoa_dl_diadanh_vina: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/chuanhoa_dl_diadanh_vina', data),
  // UR1.5.019_009 - HIển thị danh sách đã đồng bộ
  lay_dl_danhba_vnp_da_dongbo: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/lay_dl_danhba_vnp_da_dongbo', data),
  // UR1.5.019_011 - Nút đồng bộ
  fn_thuchien_dongbo_vnp: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/fn_thuchien_dongbo_vnp', data),
  // UR1.5.019_012_001 - lay dl combobox loai giay to trong formload Sửa số GT
  load_capnhat_giayto: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/load_capnhat_giayto', data),
  // UR1.5.019_012_002 - Hiển thị danh sách
  lay_ds_dbkh_chuanhoa_sogt: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/lay_ds_dbkh_chuanhoa_sogt', data),
  // UR1.5.019_012_003 - lay ds thue bao 
  load_danhsach_thuebao: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/load-danhsach-thuebao', data),
  // UR1.5.019_012_004 - nut cap nhat
  fn_capnhat_sogt: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/fn_capnhat_sogt', data),
   
  // UR1.5.019_014 - Nút tổng hợp
  sp_ht_ds_th_dongbo_vnp: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/sp_ht_ds_th_dongbo_vnp', data),
  // UR1.5.019_015 - Nút tuyến thu (loai = 1, khong xai nua) + UR1.5.019_016 - Nút xuất dữ liệu (loai = 2)
  sp_lay_tt_xuatdl_dongbo_vnp: async (axios, data) => axios.post('/web-quantri/dongbo-danhba/sp_lay_tt_xuatdl_dongbo_vnp', data),
}
