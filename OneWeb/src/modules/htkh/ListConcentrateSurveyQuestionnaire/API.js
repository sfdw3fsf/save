export default {
  lay_ds_donvi: async (axios) => axios.post(`/web-hopdong/ds_phieukhaosat_taptrung/lay_ds_donvi`),
  sp_lay_ds_nguoidung_tinh: async (axios, nhanvien_id, quyen) => axios.get(`/web-baohong/TiepNhanBaoHong/sp_lay_ds_nguoidung_tinh?nhanVienID=${nhanvien_id}&quyen=${quyen}`),
  lay_ds_nd_dv_con: (axios, data) => axios.post('/web-hopdong/ds_phieukhaosat_taptrung/lay_ds_nd_dv_con', data),
  sp_lay_ds_phieu_ks_theodk: (axios, data) => axios.post('/web-hopdong/ds_phieukhaosat_taptrung/sp_lay_ds_phieu_ks_theodk', data),
  sp_boqua_chiaphieu: (axios, data) => axios.post('/web-hopdong/ds_phieukhaosat_taptrung/sp_boqua_chiaphieu', data),
  sp_dieulai_thuebao_ks: (axios, data) => axios.post('/web-hopdong/ds_phieukhaosat_taptrung/sp_dieulai_thuebao_ks', data),
  giuphieu_ob_lm_tt_cskh: (axios, data) => axios.post('/web-hopdong/ds_phieukhaosat_taptrung/giuphieu_ob_lm_tt_cskh', data),
  giuphieu_ob_bh_tt_cskh: (axios, data) => axios.post('/web-hopdong/ds_phieukhaosat_taptrung/giuphieu_ob_bh_tt_cskh', data),
  sp_boqua_giaophieu_cskh: (axios, data) => axios.post('/web-hopdong/ds_phieukhaosat_taptrung/sp_boqua_giaophieu_cskh', data),
  sp_them_tb_khong_ks: (axios, data) => axios.post('/web-hopdong/ds_phieukhaosat_taptrung/sp_them_tb_khong_ks', data),
  lay_ds_baohong_theo_thuebao_id: (axios, data) => axios.post(`/web-ccdv/tien_trinh_bao_hong/lay_ds_baohong_theo_thuebao_id`, data), // {vthuebao_id, vma_bh, vbaohong_id}
  lay_ds_hd_theo_ma_tb: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_hd_theo_ma_tb', data),
}
