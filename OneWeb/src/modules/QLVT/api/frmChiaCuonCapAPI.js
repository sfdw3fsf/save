export default {
    getDSVatTuDangKy: (axios, data) => axios.post('/web-qlvt/api/chia-cuon-cap/thong-tin-don-hang', data),
    kiemTraSoLuongLoHang: (axios, data) => axios.post('/web-qlvt/api/chia-cuon-cap/so-luong-lo-hang-dang-ky', data),
}