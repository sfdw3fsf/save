export default {
  getThanhToanTheoThueBao: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/thong-tin-thanh-toan/ma-thue-bao-va-ky-cuoc", {params: data}),
  getThanhToanTheoMaTT: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/thong-tin-thanh-toan/thanh-toan-thue-bao', {params: data}),
  getThongTinNoThueBao: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/thong-tin-no-thue-bao', {params: data}),
  getThongTinTraThueBao: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/thong-tin-tra-thue-bao', {params: data}),
  getThongTinTongNoThueBao: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/tong-no-thue-bao', {params: data}),
}
