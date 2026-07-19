import axios from "axios";
export default{
  getDsChuongTrinhCSKH: (axios, data) => axios.get('/web-cskh/api/thuc-hien-cskh/ds-ct-theo-nv', {params: data}),
  getDsLoaiDoiTuong: (axios, data) => axios.get('/web-cskh/api/danh-muc/loai-doi-tuong'),
  getDsCongViecTheoCT: (axios, data) => axios.post('/web-cskh/api/thuc-hien-cskh/ds-cv-theo-ct', data),
  getDsChamSocTheoNV: (axios, data) => axios.post('/web-cskh/api/thuc-hien-cskh/ds-cs-theo-nv', data),
  capNhapThucHienCSKH: (axios, data) => axios.post('/web-cskh/api/thuc-hien-cskh/cap-nhat', data),
  hoanThanhCSKH: (axios, data) => axios.post('/web-cskh/api/thuc-hien-cskh/hoan-thanh', data),
  xoaCSKH: (axios, data) => axios.get('/web-cskh/api/thuc-hien-cskh/xoa', {params: data}),

  // xoaCongViecCSKH: (axios, data) => axios.post('/web-cskh/api/cong-viec-cskh/xoa', data),


}
