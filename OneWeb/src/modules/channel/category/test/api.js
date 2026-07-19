export default {
  layThongTinChung: (axios, data) => axios.post('/web-net/thongtinchung/layThongTinChung', data),
  dsDichVu: (axios, data) => axios.post('/web-net/thongtinchung/dsDichVu', data),
  dsLoaiHinh: (axios, data) => axios.post('/web-net/thongtinchung/dsLoaiHinh', data),
  dsTocDo: (axios, data) => axios.post('/web-net/thongtinchung/dsTocDo', data),
  dsDanhMucChung: (axios, data) => axios.post('/web-net/thongtinchung/dsDanhMucChung', data),
  capNhatThongTinChung: (axios, data) => axios.post('/web-net/thongtinchung/capNhatThongTinChung', data)
}
