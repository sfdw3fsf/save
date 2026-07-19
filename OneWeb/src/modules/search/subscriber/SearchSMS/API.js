export default {
  dau_so: async (axios, data) => axios.post('/ccbs/danhMuc/ts_danhmuc_tracuu_sms_dauso', data),
  danhmuc_tracuu_sms_888_9123_1414_9889: async (axios, data) => axios.post('/ccbs/executor/ts_danhmuc_tracuu_sms_888_9123_1414_9889', data),
  danhmuc_tracuu_sms_900_USSD: async (axios, data) => axios.post('/ccbs/executor/ts_danhmuc_tracuu_sms_900_USSD', data),
  nhom_taikhoan: async (axios, data) => axios.post('/ccbs/didong/nhom-taikhoan', data)
}
