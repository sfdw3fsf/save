export default {
  // UR2.5.010_049_001
  SP_DS_CHATLUONG: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_ds_chatluong', request),
  // UR2.5.010_049_002_1
  CAPNHAT_CHATLUONG_HDTB: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/capnhat_chatluong_hdtb', request),
  // UR2.5.010_049_002_1
  CAPNHAT_CHATLUONG_BH: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/capnhat_chatluong_bh', request)
}
