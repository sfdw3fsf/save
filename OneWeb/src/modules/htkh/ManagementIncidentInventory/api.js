export default {
  //getTrangThai: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_ds_trangthai_timkiem'),

  getDSLuong: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_ds_luong'),
  getDsDonviDai: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_ds_donvi_dai_htkh'),
  getDsDonViDaiV2: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_ds_donvi_dai_htkh_v2'),
  getDanhSachDonViTinh: async (axios) => axios.get(`/web-thicong/capnhatthongtin/sp_division_getlist_v2`),

  xoaphieu_baohong: async (axios, { tinh_id, baohong_id }) =>
    axios.post(`/web-thicong/capnhatthongtin/xoaphieu_baohong`, {
      p_phanvung_id: +tinh_id,
      v_baohong_uid: +baohong_id
    }),
  huygiao_baohong: async (axios, { tinh_id, phieu_id }) =>
    axios.post(`/web-thicong/capnhatthongtin/huygiao_baohong`, {
      p_phanvung_id: +tinh_id,
      p_phieu_id: +phieu_id
  }),
  chuyenphieu_chuagiao: async (axios, data) => axios.get(`web-thicong/capnhatthongtin/chuyenphieu_chuagiao?p_phanvung_id=${data.phanvung_id}`, data),
 
  sp_tracuubaohongtoanquoc: async (axios, data) => axios.get('/web-thicong/capnhatthongtin/sp_tracuubaohongtoanquoc', data),
  giuphieu_bh_nhanhkhac: async (axios, data) => axios.post('/web-hopdong/kiemsoat_ton_phieubaohong/giuphieu_bh_nhanhkhac', data),
  fn_kiemtra_thamso_md: async (axios, data) => axios.post('/web-thicong/capnhatthongtin/fn_kiemtra_thamso_md', data),
  fn_boqua_phieu_tien_xuly: async (axios, data) => axios.post('/web-thicong/capnhatthongtin/fn_boqua_phieu_tien_xuly', data),
  sp_tracuugiaophieu_bh_toanquoc: async (axios, data) => axios.post('/web-thicong/capnhatthongtin/sp_tracuugiaophieu_bh_toanquoc', data),

  getTrangThai: async (axios, data) => axios.get(`web-thicong/capnhatthongtin/sp_lay_ds_trangthai_timkiem_phieubh?p_tag=${data.p_tag}`, data),

  fn_tudong_giaophieu_baohong_dai: async (axios, data) => axios.post('/web-thicong/capnhatthongtin/fn_tudong_giaophieu_baohong_dai', data),
  lay_ds_nhanvien_theo_dv: async (axios, data) => axios.post('/web-thicong/quanlydonhang/lay_ds_nhanvien_theo_dv', data),
  
}
