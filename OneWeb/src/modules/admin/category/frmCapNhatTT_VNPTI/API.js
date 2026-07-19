export default {
  getDSDichVuVT: (axios) => axios.get('/web-thicong/quanlydonhang/dsDichVu'),
  getTTTB: (axios,data) => axios.post('/web-thicong/capnhatthongtin/lay_thongtin_tb_capnhattt_vnpti',data),
  timKiem: (axios,data) => axios.post('/web-thicong/capnhatthongtin/timkiem_kenhtoantrinh',data),
  getSoMay: (axios,data) => axios.post('/web-thicong/capnhatthongtin/lay_thongtin_phandoan',data),
  ganKenh: (axios,data) => axios.post('/web-thicong/capnhatthongtin/gan_kenh_toantrinh_vti',data),
}