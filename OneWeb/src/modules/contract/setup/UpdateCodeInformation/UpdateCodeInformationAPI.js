export default {
  // UR2.1.044_001
  SP_HT_DONVI_LOAIDV_COMBOBOX: (axios, data) => axios.post('/web-hopdong/tachnhapthuebao/sp_ht_donvi_loaidv_combobox', data),
  // UR2.1.044_002
  LAY_DS_MIG_HDDB_KH: (axios, data) => axios.post('/web-hopdong/thongtin_maghep/lay_ds_mig_hddb_kh', data),
  // UR2.1.044_003
  LAY_DS_THANHTOAN_MAIG_KHID: (axios, data) => axios.post('/web-hopdong/thongtin_maghep/lay_ds_thanhtoan_maig_khid', data),
  // UR2.1.044_004
  // UR2.1.044_011_2
  LAY_DS_DONVIQL: (axios, data) => axios.post('/web-quantri/thutienphatsinh/lay_ds_donviql', data),
  // UR2.1.044_011_3
  SP_HT_NVTC_THEODC: (axios, data) => axios.post('/web-hopdong/thongtin_maghep/sp_ht_nvtc_theodc', data),
  // UR2.1.044_012_1
  SP_LAY_MANVTC_TUYENTHU: (axios, data) => axios.post('/web-hopdong/thongtin_maghep/sp_lay_manvtc_tuyenthu', data),
  // UR2.1.044_012_2
  LAY_DS_DIACHI_THEO_DBIGID: (axios, data) => axios.post('/web-hopdong/thongtin_maghep/lay_ds_diachi_theo_dbigid', data),
  // Kiểm tra thông tin gói đồng bộ
  FN_MAP_ID: async (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', data),
  LAY_DB_THEO_MATB: (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_db_theo_matb', data),
  // UR2.1.044_015
  SINH_MA_MIG: (axios, request) => axios.post('/web-hopdong/thongtin_maghep/sinh_ma_mig', request),
  // GET KEYS
  POST_GET_KEYS: async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
  // UR2.1.044_015
  SP_THEMMOI_DANHBA_INGHEP: async (axios, request) => axios.post('/web-hopdong/thongtin_maghep/sp_themmoi_danhba_inghep', request),
  // UR2.1.044_016_4
  CAPNHAT_GO_MIG: async (axios, request) => axios.post('/web-hopdong/thongtin_maghep/capnhat_go_mig', request),
  // UR2.1.044_016_3
  CAPNHAT_LS_THAYDOI_DBTT: async (axios, request) => axios.post('/web-hopdong/thongtin_maghep/capnhat_ls_thaydoi_dbtt', request),
  // UR2.1.044_016_1
  SP_CAPNHAT_THONGTIN_MIG: async (axios, request) => axios.post('/web-hopdong/thongtin_maghep/sp_capnhat_thongtin_mig', request),
  // UR2.1.044_016_1
  XOA_DS_MIG_TMP: async (axios, request) => axios.post('/web-hopdong/thongtin_maghep/xoa_ds_mig_tmp', request),
  // UR2.1.044_017
  XOA_DANHBA_MIG: async (axios, request) => axios.post('/web-hopdong/thongtin_maghep/xoa_danhba_mig', request),
  // LAY_KY_CUOC_HIEN_HANHN_HANH
  LAY_KY_CUOC_HIEN_HANHN_HANH: async (axios) => axios.get('/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh'),
  GET_CHUKYNO_THEO_KYCUOC: async (axios, param) => axios.get(`/web-thuno/api/thu-no/common/chu-ky-no/ky-cuoc/${param}`)
}
