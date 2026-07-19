export default {
  getDanhSachBienNhanThuTaiNha: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/bien-nhan-thu-tai-nha', {params: data}),
  getDanhSachHoaDonChuyenKhoan: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/hoa-don-chuyen-khoan', {params: data})
}
