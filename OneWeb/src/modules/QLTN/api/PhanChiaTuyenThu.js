export default {
  getDanhSachTuyenThuByNhanVien: (axios, data) => axios.get('/web-thuno/api/thu-no/tuyenthu/lay-danh-sach-tuyen-thu-theo-nhan-vien', {params: data}),
  getDanhSachDotGiao: (axios, data) => axios.get('/web-thuno/api/thu-no/tuyenthu/lay-danh-sach-dot-giao', {params: data}),
  getDanhSachQuan: (axios, data) => axios.get(`/web-thuno/api/thu-no/common/quan-huyen`),
  getDanhSachPhuong: (axios, data) => axios.get('/web-thuno/api/thu-no/danh-muc/lay-danh-sach-phuong-theo-quan', {params: data}),
  getDanhSachPho: (axios, data) => axios.get('/web-thuno/api/thu-no/danh-muc/lay-danh-sach-pho-ap-khu-theo-phuong', {params: data}),
  getDanhSachAp: (axios, data) => axios.get('/web-thuno/api/thu-no/danh-muc/lay-danh-sach-pho-ap-khu-theo-phuong', {params: data}),
  getDanhSachPhanChiaTuyenThu: (axios, data) => axios.get('/web-thuno/api/thu-no/lay-danh-sach-phan-chia-tuyen-thu', {params: data}),
  getDanhSachChuaPhanTuyen: (axios, data) => axios.get('/web-thuno/api/thu-no/lay-danh-sach-chua-phan-tuyen-thu', {params: data}),
  capNhatDanhSachPhanChiaTuyenThu: (axios, data) => axios.put('/web-thuno/api/thu-no/tuyen-thu/cap-nhat-danh-sach-phan-chia-tuyen-thu', data),
}
