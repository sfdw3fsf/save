export default {
  SP_LAY_TEMPLATE_BAOCAO: async (axios, request) => axios.post('/web-ccdv/nhantra_tiensau/sp_lay_template_baocao', request),
  RUN: async (axios, request) => axios.post('/web-report/report/bi/run', request),
  GET_BI_PARAMS: async (axios, path) => axios.get(`/web-report/report/bi/parameters?report=${path}`),
  FN_INPHIEUTHU_LOAD: async (axios) => axios.get('/web-tracuu/inphieuthu/fn_inphieuthu_load'),
  FN_INPHIEUTHU_CBOLOAITIEN: async (axios, request) => axios.post('/web-tracuu/inphieuthu/fn_inphieuthu_cboloaitien', request),
  FN_INPHIEUTHU_TXTMAGD: async (axios, request) => axios.post('/web-tracuu/inphieuthu/fn_inphieuthu_txtmagd', request)
}
