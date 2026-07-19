export default {
  sp_thietke_baocao_danhmuc: async (axios, request) => axios.post('/web-quantri/danhmuc-chung/sp_thietke_baocao_danhmuc', request),
  fn_thietke_baocao_capnhat: async (axios, request) => axios.post('/web-quantri/danhmuc-chung/fn_thietke_baocao_capnhat', request),
  // GET KEYS
  POST_GET_KEYS: async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data)
}
