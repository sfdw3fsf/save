import axios from "axios";

export default{
  getDanhSachNVTC:  async (axios, data) => axios.get('/web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-tinh-cuoc', { params : data}),
  getTuyenThuTheoNhanVien:  async (axios, data) => axios.get('/web-thuno/api/thu-no/tuyenthu/lay-danh-sach-tuyen-thu-theo-nhan-vien', { params : data}),
  getDotGiaoTheoNhanVien:  async (axios, data) => axios.get('/web-thuno/api/thu-no/tuyenthu/lay-danh-sach-dot-giao', { params : data}),
  getDanhSachHoaDonChuyenKhoan:  async (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/hoa-don-chuyen-khoan', { params : data}),
  giaophieuChuyenKhoanNVTC: async (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/giao-phieu-chuyen-khoan-nvtc', data),
  huyphieuChuyenKhoanNVTC: async (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/huy-phieu-chuyen-khoan-nvtc', data),
}
