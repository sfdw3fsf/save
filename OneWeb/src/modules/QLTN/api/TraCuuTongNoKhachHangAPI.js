export default {
  getDanhSachTongNoKH: (axios, data) => axios.get('/web-thuno/api/thu-no/sp-lay-tong-no-dich-vu-vien-thong-theo-ky-hoa-don', {params: data}),
  getDanhSachTongTraKH: (axios, data) => axios.get('/web-thuno/api/thu-no/sp-tra-cuu-tong-tra-theo-thanh-toan', {params: data}),
  getThongTinTTThueBao: (axios, data) => axios.get('/web-thuno/api/thu-no/sp-lay-thong-tin-thanh-toan-theo-thue-bao', {params: data}),
  getThongTinTTThanhToan: (axios, data) => axios.get('/web-thuno/api/thu-no/sp-lay-thong-tin-thanh-toan-theo-thanh-toan', {params: data})
}
