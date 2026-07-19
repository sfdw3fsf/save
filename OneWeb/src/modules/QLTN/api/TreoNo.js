import axios from "axios"

export default{
  getDSTreoNo:  async (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/treo-no-thue-bao/lay-danh-sach-treo-no', { params : data}),
  getTBTreoNo:  async (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/treo-no-thue-bao/lay-thong-tin-thue-bao-da-treo-no', { params : data}),
  addTBTreoNo: async (axios, data) => axios.post('/web-thuno/api/thu-no/xu-ly-thue-bao/treo-no-thue-bao/them-thue-bao-treo-no', data),
  updateTBTreoNo: async (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/treo-no-thue-bao/cap-nhat-thue-bao-treo-no', data),
  deleteTBTreoNo: async (axios, data) => axios.delete('/web-thuno/api/thu-no/xu-ly-thue-bao/treo-no-thue-bao/xoa-thue-bao-treo-no', { data: data}),
  updateTBTreoNoFromFile: async (axios, data) => axios.post('/web-thuno/api/thu-no/treo-no-thue-bao/cap-nhat-danh-sach-no-tu-file', data),
  getTreoNoTuFile:  async (axios, data) => axios.post('/web-thuno/api/thu-no/xu-ly-thue-bao/treo-no-thue-bao/cap-nhat-danh-sach-no-tu-file', data),
  capnhatTreoNoTuFile:  async (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/treo-no-thue-bao/cap-nhat-danh-sach-no-theo-file', data),
}
