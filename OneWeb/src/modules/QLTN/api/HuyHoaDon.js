export default {
  getDanhSachHoaDon: (axios, data) => axios.get('/web-thuno/api/thu-no/hoa-don/danh-sach', { params: data }),
  huyBoHoaDon: (axios, data) => axios.get('/web-thuno/api/thu-no/hoa-don/huy-bo', { params: data }),
  getDsPattern:(axios, data) => axios.get('/web-thuno/api/thu-no/hoa-don/mau-so', { params: data }),
  getDsSerial:(axios, data) => axios.get('/web-thuno/api/thu-no/hoa-don/ky-hieu', { params: data }),
  getChiTietHoaDon:(axios, data) => axios.get('/web-thuno/api/thu-no/hoa-don/detail-hoa-don', { params: data }),
}
