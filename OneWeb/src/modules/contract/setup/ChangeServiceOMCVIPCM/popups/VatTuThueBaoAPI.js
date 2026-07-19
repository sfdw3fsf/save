export default {
  // UR2.4.063_032_01
  CSS_TRANGBI: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TRANGBI'),
  // UR2.4.063_032_02
  CSS_NHOM_TBI: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_NHOM_TBI'),
  // UR2.4.063_032_03
  SP_LAY_DS_LOAITBI_HDTB: async (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_loaitbi_hdtb', request),
  // UR2.4.063_032_04
  CSS_KIEU_TBI: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_TBI'),
  // UR2.4.063_032_05
  CSS_TINHTRANG_TBI: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TINHTRANG_TBI'),
  // UR2.4.063_032_06
  SP_LAY_DS_DONVI_TBI: async (axios) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_donvi_tbi'),
  // UR2.4.063_032_07
  LAY_DS_VATTU_HDTB: async (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_vattu_hdtb', request),
  // UR2.4.063_032_08
  LAY_DS_VATTU_DBTB: async (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_vattu_dbtb', request),
  // UR2.4.063_032_09
  FN_LAY_DONVI_TBI_THEO_LOAITBI: async (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_lay_donvi_tbi_theo_loaitbi', request),
  // UR2.4.063_032_11
  FN_AN_GHILAI_FRMVATTUTHUEBAO: async (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_ghilai_frmvattuthuebao', request),
  // UR2.4.063_032_12
  FN_AN_XOA_VATTU: async (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_xoa_vattu', request),
  // Kiểm tra thông tin gói đồng bộ
  FN_MAP_ID: async (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', data),
  // GET KEYS
  POST_GET_KEYS: async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
  // UR2.4.063_035_01
  KIEMTRA_CAP_VATTU: async (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/kiemtra_cap_vattu', request),
  // UR2.4.063_035_01
  INSERT_VATTU: async (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/insert_vattu', request),
  fn_tt_loai_tbi: async (axios, request) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_tbi', request)
}
