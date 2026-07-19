export default {
  getDanhSachHoaDonThanhToan: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/seri-hoa-don/ngay-thanh-toan', {params: data}),
  getThongTinTTByTT: (axios, data) => axios.get(`/web-thuno/api/thu-no/tra-cuu-danh-ba/thong-tin-thanh-toan/ma-thanh-toan/${data}`),
  getThongTinTTByTB: (axios, data) => axios.get(`/web-thuno/api/thu-no/tra-cuu-danh-ba/thong-tin-thanh-toan/ma-thue-bao/${data}`),
  getThongTinTTByKH: (axios, data) => axios.get(`/web-thuno/api/thu-no/tra-cuu-danh-ba/thong-tin-thanh-toan/ma-khach-hang/${data}`)
}
