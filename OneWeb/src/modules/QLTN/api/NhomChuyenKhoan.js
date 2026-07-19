export default {
  tracuuNhomChuyenKhoan: (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/tra-cuu-nhom-chuyen-khoan/ma-tim-kiem/' + data),
  getDSThanhToanDaGan: (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/ds-thanh-toan-da-gan-nhom-chuyen-khoan/' + data),
  taoNhomChuyenKhoan: (axios, data) => axios.post(`/web-thuno/api/quan-ly-thu-no/nhom-chuyen-khoan`, data),
  taoDSTheoNhom: (axios, data) => axios.post('/web-thuno/api/quan-ly-thu-no/ds-thanh-toan-nhom-chuyen-khoan', data),

  capnhatNhomChuyenKhoan: (axios, data) => axios.put(`/web-thuno/api/quan-ly-thu-no/nhom-chuyen-khoan`, data),
  xoaNhomChuyenKhoan: (axios, data) => axios.delete(`/web-thuno/api/quan-ly-thu-no/nhom-chuyen-khoan/id/` + data),
  xoaKhoiNhomChuyenKhoan: (axios, data) => axios.delete(`/web-thuno/api/quan-ly-thu-no/nhom-ck-chi-tiet`, {params: data}),

  thongtinNhomChuyenKhoan: (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/thong-tin-nhom-chuyen-khoan', { params: data }),
  // cập nhật tham số ngày quét
  capnhatTSNgayQuet: (axios, data) => axios.put(`/web-thuno/api/thu-no/common/ngay-quet-ts-nhom-ck`, data),

  // check 
  checkDSTheoNhom: (axios, data) => axios.post(`/web-thuno/api/thu-no/quan-ly-dai-ly/kiem-tra-danh-sach`, data),
  

}
