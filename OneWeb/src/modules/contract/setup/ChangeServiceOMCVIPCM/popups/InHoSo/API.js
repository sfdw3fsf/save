export default {
  RUN: async (axios, request) => axios.post('/web-report/report/bi/run', request),
  GET_BI_PARAMS: async (axios, path) => axios.get(`/web-report/report/bi/parameters?report=${path}`),
  POST_BI_PARAMS: async (axios, request) => axios.post('/web-report/report/bi/parameters', request),
  CSS_TINH: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TINH'),
  SP_LAY_DS_MAU_IN: async (axios, request) => axios.get('/web-quantri/danhmuc-chung/sp_lay_ds_mau_in', request)
}
