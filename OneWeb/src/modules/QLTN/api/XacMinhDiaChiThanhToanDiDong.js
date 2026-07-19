export default {
  getDanhSachKhuVuc: async (axios, data) => axios.get('/web-thuno/api/thu-no/common/don-vi/loai-don-vi', { params: data }),
  getDanhSachThanhToan: async (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/thanh-toan-xac-minh', { params: data }),
  inPhieuXacMinhByMaBaoCao: async (axios, data) => axios.get('/web-thuno/api/thu-no/common/in-phieu-xac-minh/' + data),
  updateXacMinh: async (axios, data) => axios.post('/web-thuno/api/thu-no/quan-ly-dai-ly/xac-minh-thanh-toan', data),
  sendSMS: async (axios, data) => axios.post('/web-thuno/api/thu-no/send-sms', data),
}
