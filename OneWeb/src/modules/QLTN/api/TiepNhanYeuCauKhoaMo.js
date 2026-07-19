export default{
  getDSDonViLapDat: async (axios) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/tiep-nhan-yeu-cau-khoa-mo/lay-danh-sach-don-vi'),
  getThongTinTBYCKhoaMay: async (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/tiep-nhan-yeu-cau-khoa-mo/lay-thong-tin-thue-bao-no-yeu-cau-khoa-may', { params : data}),
  getDanhBaTBTheoMa: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu-danh-ba/danh-ba-thue-bao-theo-ma', { params : data}),
  getDanhSachYeuCauKhoaMo: async (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/tiep-nhan-yeu-cau-khoa-mo/lay-danh-sach-yeu-cau-khoa-mo', { params : data}),
  addYeuCauKhoaMo: async (axios, data) => axios.post('/web-thuno/api/thu-no/xu-ly-thue-bao/tiep-nhan-yeu-cau-khoa-mo/them-yeu-cau-khoa-mo', data),
  updateYeuCauKhoaMo: async (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/tiep-nhan-yeu-cau-khoa-mo/cap-nhat-yeu-cau-khoa-mo', data),
  deleteYeuCauKhoaMo: async (axios, data) => axios.delete('/web-thuno/api/thu-no/xu-ly-thue-bao/tiep-nhan-yeu-cau-khoa-mo/xoa-yeu-cau-khoa-mo', { params : data}),
}
