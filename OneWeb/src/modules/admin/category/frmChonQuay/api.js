export default {
  lay_ds_quaythu_gan_nguoidung: async (axios, data) => axios.get('/web-quantri/quayhopdong/lay_ds_quaythu_gan_nguoidung', data),
  capnhat_quaythu_nhanvien: async (axios, data) => axios.post('/web-quantri/quayhopdong/capnhat_quaythu_nhanvien', data),
  getTenDonVi: async (axios, data) => axios.post('/web-gqkn/xu-ly-khieu-nai/getTenDonVi', data),
}
