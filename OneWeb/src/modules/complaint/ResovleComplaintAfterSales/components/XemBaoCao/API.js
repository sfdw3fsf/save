export default {
  SP_LAY_TEMPLATE_BAOCAO: async (axios, request) => axios.post('/web-ccdv/nhantra_tiensau/sp_lay_template_baocao', request),
  RUN: async (axios, request) => axios.post('/web-report/report/bi/run', request),
  GET_BI_PARAMS: async (axios, path) => axios.get(`/web-report/report/bi/parameters?report=${path}`),
  FN_MAP_ID: async (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', data),
  FN_TT_BAOCAO: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_baocao', data)
}
