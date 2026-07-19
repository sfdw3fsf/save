export default {
  getDSHinhThuc_TT: (axios, data) => axios.get('/web-thuno/api/thu-no/common/hinh-thuc-thanh-toan', { params: data }),
  getDSKhieuNai: (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/ds-khieu-nai-giam-cuoc', { params: data }),
  xoaPhieu: (axios, data) => axios.delete('/web-thuno/api/thu-no/sp-xoa-phieu-thanh-toan', {data : data}),
  huyPhieu: (axios, data) => axios.put('/web-thuno/api/quan-ly-thu-no/huy-khieu-nai-goi-cuoc', data),
  gachNoDieuChinh: (axios, data) => axios.put('/web-thuno/api/quan-ly-thu-no/gach-no-dieu-chinh-khieu-nai', data),
  getDanhSachThuQuy: (axios, data) => axios.get('/web-thuno/api/thu-no/common/nhan-vien-thu-ngan/nguoi-dung-hien-tai'),
  getDanhSachLoaiTien: (axios, data) => axios.get('/web-thuno/api/thu-no/common/ds-loai-tien'),
  getDanhSachGC: (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/ds-giam-cuoc/khieu-nai/' + data),
  xoaPhieuDHSX: (axios, data) => axios.delete('/web-thuno/api/thu-no/sp-xoa-phieu-thanh-toan-dhsx', {data : data}),
}
