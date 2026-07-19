export default {
  lay_danhsach_nhom_nd: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_NHOM_ND'),
  lay_nhom_quyen_by_nd_id: async (axios, nhom_nd_id) =>
    axios.post('/web-quantri/nhanvien/sp_nhomnd_get_tt_by_id', {
      nhom_nd_id: nhom_nd_id
    }),
  fn_nhomnd_get_id_by_ten_nhom: async (axios, ten_nhom) =>
    axios.post('/web-quantri/nhanvien/fn_nhomnd_get_id_by_ten_nhom', {
      ten_nhom: ten_nhom
    }),

  ghilai_ds_quyen_gan_nhomnd: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_ghilai_ds_quyen_gan_nhomnd', data),

  add_ds_quyen_gan_nhomnd: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_add_ds_quyen_gan_nhomnd', data),

  del_ds_quyen_gan_nhomnd: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_del_ds_quyen_gan_nhomnd', data),
  kiemtra_quyen_chucnang: async (axios, data) => axios.get(`/web-quantri/quyen/kiemtra_quyen_chucnang?matkhau=${data.password}`)
}
