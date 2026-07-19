export default {
  getTrangThai: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_trangthai_kn'),

  getDSLuong: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_ds_luong_xl_khieunai'),
  getDsDonviDai: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_ds_donvi_dai_htkh'),
  getDsDonViDaiV2: async (axios) => axios.get('/web-thicong/capnhatthongtin/sp_lay_ds_donvi_dai_htkh_v2'),
  getDanhSachDonViTinh: async (axios) => axios.get(`/web-thicong/capnhatthongtin/sp_division_getlist_v2`),
  chuyenphieu_chuagiao: async (axios, data) => axios.get(`web-thicong/capnhatthongtin/chuyenphieu_chuagiao?p_phanvung_id=${data.phanvung_id}`, data), 
  deletePhieu: async (axios, { tinh_id, khieunai_id }) =>
    axios.post(`/web-thicong/capnhatthongtin/xoa_khieunai`, {
      vphanvung_id: +tinh_id,
      vkhieunai_id: +khieunai_id      
    })
}
