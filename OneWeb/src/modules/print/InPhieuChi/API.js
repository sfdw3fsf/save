export default {
  // UR2.2.005_001
  sp_hienthi_thungan_phieuchi: async (axios, request) => axios.post('/web-gqkn/thanhtoan/inphieuchi/ds_thungan', request),
  // UR2.2.005_002
  sp_ht_loaitien: async (axios) => axios.post('/web-gqkn/thanhtoan/inphieuchi/ds_loaitien', {}),
  // UR2.2.005_003
  sp_ht_loai_hoadon: async (axios, request) => axios.post('/web-gqkn/thanhtoan/inphieuchi/ds_loaihoadon', request),
  // UR2.2.005_004
  sp_ht_seri: async (axios, request) => axios.post('/web-gqkn/thanhtoan/inphieuchi/ds_seri', request),
  // UR2.2.005_005
  sp_lay_max_hoadon: async (axios, request) => axios.post('/web-gqkn/thanhtoan/inphieuchi/lay_so_hoadon', request),
  // UR2.2.005_006
  lay_ds_thoaitra: async (axios, request) => axios.post('/web-gqkn/thanhtoan/inphieuchi/lay_thoaitra', request),
  SP_LAY_TEMPLATE_BAOCAO: async (axios, request) => axios.post('/web-hopdong/nhantra_tiensau/sp_lay_template_baocao', request),
  RUN: async (axios, request) => axios.post('/web-quantri/report/bi/run', request),
  GET_BI_PARAMS: async (axios, path) => axios.get(`/web-quantri/report/bi/parameters?report=${path}`),
  INPHIEUCHI: async (axios, request) => axios.post('/web-gqkn/thanhtoan/inphieuchi/inphieuchi', request),
  lay_gt_thamso_md_mats: (axios, ten_ts) => axios.get(`/web-quantri/danhmuc/thamso_md_theo_ma_ts?MA_TS=${ten_ts}`)
}
