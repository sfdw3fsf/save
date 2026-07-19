export default {
  getDanhSachKetNoi: (axios, data) => axios.post('/web-ecms/dien/danhsach-ketnoi', data),
  getDanhMuc: (axios, data) => axios.post('/web-ecms/idc/danh-muc/common', data),
  deleteKetNoi: (axios, id) => axios.post(`/web-ecms/dien/ketnoi/delete?idKetNoi=${id}`),
  seachDSKetQua: (axios, data) => axios.post('/web-ecms/dien/phieutc-ketqua/search', data),
  getNhanVien: (axios, data) => axios.get('/web-ecms/ha-tang/thongtin-u/danhsach-nhanvien/' + data),
}
