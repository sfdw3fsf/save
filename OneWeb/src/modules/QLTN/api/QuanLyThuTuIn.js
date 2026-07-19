export default {
  getDanhSachNVThuCuoc: (axios, data) => axios.get('/web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-tinh-cuoc', { params: data }),
  fetchKiemtraSolieu: (axios, data) => axios.post('/web-thuno/api/thu-no/ql-thu-tu-in/kiem-tra-so-lieu-tuyen-thu-tu-file', data),
  capNhatDotGiao: (axios, data) => axios.put('/web-thuno/api/thu-no/ql-thu-tu-in/cap-nhat-dot-giao', data)
}
