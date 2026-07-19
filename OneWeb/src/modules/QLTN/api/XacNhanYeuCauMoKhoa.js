export default {
  getDSYeuCauTheoNgay: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/xac-nhan-yeu-cau-khoa-mo/lay-thong-tin-thue-bao-yeu-cau-theo-ngay', { params : data}),
  updateThongTinYeuCau: (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/xac-nhan-yeu-cau-khoa-mo/cap-nhat-thong-tin-yeu-cau', data),
  updateHoanThanhThongTinYeuCau: (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/xac-nhan-yeu-cau-khoa-mo/cap-nhat-hoan-thanh-trang-thai-xac-nhan-khoa-may', data),
}
