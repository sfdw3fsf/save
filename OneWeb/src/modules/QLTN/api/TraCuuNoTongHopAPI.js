export default{
  getThongTinThanhToan: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/thong-tin-thanh-toan/thanh-toan-thue-bao', { params : data}),
  getTTNguoiDungTheoQuanLy: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/nguoi-dung/nhan-vien-quan-ly/' + data),
  getTTNhanVienAM: async (axios, data) => axios.get('/web-thuno/api/thu-no/common/nhan-vien-AM/' + data),
  getTraCuuNoTongHop: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/no-tong-hop', { params : data}),
  getTraCuuNoChiTiet: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/no-chi-tiet', { params : data}),
  getTraCuuChiTietThanhToan: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/phieu-thanh-toan/chi-tiet', { params : data}),
  getTraCuuLichSuChuyenPhieu: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/lich-su-chuyen-phieu/ma-tt/' + data),
  getTraCuuTrangThaiHoaDon: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/trang-thai-hoa-don/ma-tt/' + data),
  getTraCuuNhatKyThuNo: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/nhat-ky-thu-no/ma-tt/' + data),
  getThongTinThueBao: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/thongtin-thuebao-no-tonghop', { params : data}),
  getThongTinDiaBan: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/lay-quanly-diaban-theo-matb', { params : data})  
}
