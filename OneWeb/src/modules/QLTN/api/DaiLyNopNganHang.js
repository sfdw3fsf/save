export default {
  getDanhSachNganHang: (axios, data) => axios.get('/web-thuno/api/thu-no/common/ngan-hang-gach-no', { params: data }),
  getDanhSachKieuNop: (axios, data) => axios.get('/web-thuno/api/thu-no/common/danh-muc/kieu-nop', { params: data }),
  getDanhSachThuQuy: (axios, data) => axios.get('/web-thuno/api/thu-no/common/nhan-vien-thu-ngan/nguoi-dung-hien-tai'),
  getDanhSachDaiLyNganHang: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/dai-ly-nop-ngan-hang', { params: data }),
  themDaiLyNganHang: (axios, data) => axios.post('/web-thuno/api/quan-ly-thu-no/dai-ly-nop-ngan-hang', data),
  capNhatDaiLyNganHang: (axios, data) => axios.put('/web-thuno/api/quan-ly-thu-no/dai-ly-nop-ngan-hang', data),
  themDaiLyNganHang: (axios, data) => axios.post('/web-thuno/api/quan-ly-thu-no/dai-ly-nop-ngan-hang', data),
  xoaDaiLyNganHang: (axios, data) => axios.delete('/web-thuno/api/quan-ly-thu-no/dai-ly-nop-ngan-hang/' + data),
  getThongTinDaiLy: (axios, data) => axios.get('/web-thuno/api/thu-no/common/nhan-vien-thu-cuoc/' + data),
}
