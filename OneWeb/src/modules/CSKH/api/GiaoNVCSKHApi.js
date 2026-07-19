import axios from "axios";
export default{
  getDsDonVi: (axios) => axios.get('/web-cskh/api/giao-nv-cs-kh/danh-sach-don-vi'),
  getDsChuongTrinh: (axios, data) => axios.get('/web-cskh/api/giao-nv-cs-kh/danh-sach-ct', {params: data}),
  getDsDaGanChuaGan: (axios, data) => axios.post('/web-cskh/api/giao-nv-cs-kh/nhan-vien-cskh', data),
  ganNVCSKH: (axios, data) => axios.post('/web-cskh/api/giao-nv-cs-kh/gan', data),
  // getDsNhanVienCSKH: (axios, data) => axios.post('/web-cskh/api/duyet-cong-viec-cskh/danh-sach-nhan-vien', data),
  // getDsCongViecCSKHTheoNguoiTao: (axios, data) => axios.post('/web-cskh/api/duyet-cong-viec-cskh/ds-cv-theo-nguoi-tao', data),
  // duyetCongViecCSKH: (axios, data) => axios.post('/web-cskh/api/duyet-cong-viec-cskh/duyet', data),
}
