export default {
  RUN: async (axios, request) => axios.post('/web-report/report/bi/run', request),
  GET_BI_PARAMS: async (axios, path) => axios.get(`/web-report/report/bi/parameters?report=${path}`),
  POST_BI_PARAMS: async (axios, request) => axios.post('/web-report/report/bi/parameters', request),
  // Kiểm tra thông tin gói đồng bộ
  FN_MAP_ID: async (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', data),
  SP_LAY_DS_MAU_IN: async (axios, request) => axios.post('/web-quantri/danhmuc-chung/sp_lay_ds_mau_in', request),
  SP_LAY_DS_DONVI_LDV_MAUIN: async (axios, request) => axios.post('/web-quantri/donvi/SP_LAY_DS_DONVI_LDV_MAUIN', request),
  FN_TT_NHANVIEN: async (axios, request) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', request),
  FN_TT_HD_KHACHHANG: async (axios, request) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_khachhang', request),
  SP_CAPNHAT_INPHIEU: async(axios, req) => axios.post('/web-quantri/danhmuc-chung/sp_capnhat_inphieu', req)
}
