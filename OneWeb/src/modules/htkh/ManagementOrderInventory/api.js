export default {
  getTrangThai: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_ds_trangthai_timkiem'),

  getDSLuong: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_ds_luong'),
  getDsDonviDai: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_ds_donvi_dai_htkh'),
  getDsDonViDaiV2: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_ds_donvi_dai_htkh_v2'),
  getDanhSachDonViTinh: async (axios) => axios.get(`/web-thicong/capnhatthongtin/sp_division_getlist_v2`),
  deleteOrderInventory: async (axios, { tinh_id, hdkh_id, tthd_id }) =>
    axios.post(`/web-tracuu/quanlydonhang/fn_xoa_donhang`, {
      p_phanvung_id: +tinh_id,
      p_hdkh_id: +hdkh_id,
      p_loaihd_id: 38,
      p_tthd_id: +tthd_id
    }),
  huy_giaophieu: async ( axios, data) => axios.post(`/web-thicong/quanlydonhang/huygiao_donhang` , data),
}
