export default {
  thong_tin_don_hang: (axios, data) => axios.post('/web-qlvt/api/chia-cuon-cap/thong-tin-don-hang', data),
  so_luong_lo_hang_dang_ky: (axios, data) => axios.post('/web-qlvt/api/chia-cuon-cap/so-luong-lo-hang-dang-ky', data)
}
