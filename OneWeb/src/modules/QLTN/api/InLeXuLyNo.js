export default {
  getDanhSachKyHoaDon: (axios, data) => axios.get('/web-thuno/api/thu-no/common/ky-hoa-don', { params: data }),
  getDanhSachXuLyNo: (axios, data) => axios.get('/web-thuno/api/thu-no/common/thong-bao-xu-ly-no-le', { params: data }),
  kiemtraMaXLN: (axios, data) => axios.post('/web-thuno/api/thu-no/xu-ly-no/kiem-tra-xu-ly-no', data),
  getDsMaTT: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-no/ds-ma-tt/ma-tt', { params: data }),
  getDanhSachXuLyNoTheoFile: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-no/ds-xu-ly-no-in-le-theo-file', { params: data }),
}
