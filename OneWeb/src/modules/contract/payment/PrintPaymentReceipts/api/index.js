export default{
    getDanhSachThuNgan: (axios, data) => axios.post('/web-gqkn/thanhtoan/inphieuchi/ds_thungan', data),
    getDanhSachLoaiTien: (axios, data) => axios.post('/web-gqkn/thanhtoan/inphieuchi/ds_loaitien', data),
    getDanhSachLoaiHoaDon: (axios, data) => axios.post('/web-gqkn/thanhtoan/inphieuchi/ds_loaihoadon', data),
    getDanhSachSeri: (axios, data) => axios.post('/web-gqkn/thanhtoan/inphieuchi/ds_seri', data),
    getDanhSachThoaiTra: (axios, data) => axios.post('/web-gqkn/thanhtoan/inphieuchi/lay_thoaitra', data)
  }