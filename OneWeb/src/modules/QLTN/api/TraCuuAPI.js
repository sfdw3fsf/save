export default{
  getTraCuuXuLyNoPhieuDaGiao: async (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-no/phieu-giao-xu-ly-no', { params : data}),
  getDanhSachLuotGiao: async (axios, data) => axios.get('/web-thuno/api/thu-no/lay-danh-sach-luot-giao', { params : data}),
  getTongTienUser: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/tong-tien/user', { params : data}),
  getTongTienHinhThucThanhToanUser: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/tong-tien/hinh-thuc-thanh-toan-user', { params : data}),
  getChiTietPhieuThanhToanUser: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/phieu-thanh-toan/user', { params : data}),
  getTraCuuYeuCauKhoaMo: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu-danh-sach-yeu-cau-khoa-mo', { params : data}),
  getTraCuuKhachHangTreoNo: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/khach-hang-treo-no/ma-tim-kiem/' + data),
  getTraCuuNoTongHop: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/no-tong-hop', { params : data}),
  getTraCuuNoChiTiet: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/no-chi-tiet', { params : data}),
  getTraCuuChiTietThanhToan: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/phieu-thanh-toan/chi-tiet', { params : data}),
  getTraCuuLichSuNhacNo: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu-danh-ba/lich-su-nhac-no-thue-bao', { params : data}),
  getTraCuuKhachHangKhatNo: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu-khach-hang-khat-no', { params : data}),
  getTraCuuKhachHangTreoNo: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/khach-hang-treo-no/ma-tim-kiem/' +  data),
  getThongTinThanhToanTheoThueBao: async (axios, data) => axios.get('web-thuno/api/thu-no/sp-lay-thong-tin-thanh-toan-theo-thue-bao', { params: data}),
  getThongTinThanhToanTheoThanhToan: async (axios, data) => axios.get('web-thuno/api/thu-no/sp-lay-thong-tin-thanh-toan-theo-thanh-toan', { params: data}),
  getTraCuuTongHopPhieuGiao: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/phieu-giao/ky-cuoc/' + data)
}
