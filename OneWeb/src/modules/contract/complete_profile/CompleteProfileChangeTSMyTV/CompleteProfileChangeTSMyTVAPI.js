export default {
  // GET ALL DVVT
  GET_DICHVU_VT: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
  // UR2.4.090.001
  SP_LAY_DS_NHANVIEN_THEO_DONVIDONVI: (axios, donViId) => axios.get(`/web-quantri/danhmuc/ds_nhanvien_donvi/${donViId}`),
  // UR2.4.090.002
  LAY_DK_CHO_BANG: (axios, tenbang, nguoidungid) => axios.get(`/web-quantri/quyen/dk_theo_bang/${tenbang}/${nguoidungid}`),
  // UR2.4.090.003
  LAY_DS_KIEU_LD: (axios, loaihdid, loaitbid) => axios.get(`/web-hopdong/danhmuc/ds_kieu_ld/${loaihdid}/${loaitbid}`),
  // UR2.4.090.004_1
  SP_LAY_QUYTRINH_THEO_LOAIHD_DICHVU: (axios, request) => axios.post('/web-hopdong/danhmuc/ds_quytrinh', request),
  // UR2.4.090.004_2
  SP_LAY_HUONGGIAO_THEO_QUYTRINH: (axios, quytrinhid, tthdid) => axios.get(`/web-hopdong/danhmuc/ds_huonggiao/${quytrinhid}/${tthdid}`),
  // UR2.4.090.004_3
  LAY_DS_PHIEU_HC_119_HUYEN: (axios, request) => axios.post('/web-ccdv/tiepnhanhopdong/ds_phieu_hc', request),
  // UR2.4.090.005_1
  SP_LAY_DS_THAOTAC_THEO_LUONG_ID_CODE: (axios, request) => axios.post('/web-hopdong/thanhly/sp_lay_ds_thaotac_theo_luong_id_code', request),
  // UR2.4.090.005_2
  SP_LAY_CT_THAOTAC_CONTROL: (axios, request) => axios.post('/web-hopdong/thanhly/sp_lay_ct_thaotac_control', request),
  // UR2.4.090.007 UR2.4.090.008
  LAY_DS_NHANVIEN_THEO_PHIEU_ID: (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id', request),
  // Lấy danh sách loại hình thuê bao
  CSS_LOAIHINH_TB: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
  // Lấy danh sách kiểu lắp đặt theo loại hợp đồng
  CSS_KIEU_LD: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD'),
  // UR2.4.090.010_2
  PHAILAM: (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/phailam', request),
  // UR2.4.090.010_3
  LAY_TT_TRANGBI_V2: (axios, request) => axios.post('/web-ccdv/hoanthien_hoso_thaydoi_tsmytv/lay_tt_trangbi_v2', request),
  // UR2.4.090.011
  // SP_LAY_THONGTIN_THEO_HDTB: (axios, hdtbid) => axios.get(`/web-hopdong/danhmuc/ds_tttb_hdtb/${hdtbid}`),
  // UR2.4.090.013
  FN_HOANCONG_HTHSTHAYDOIMYTV: (axios, request) => axios.post('/web-ccdv/hoanthien_hoso_thaydoi_tsmytv/fn_hoancong_hthsthaydoimytv', request),
  // UR2.4.090.014
  FN_HOANTHIEN_HTHSTHAYDOIMYTV: (axios, request) => axios.post('/web-ccdv/hoanthien_hoso_thaydoi_tsmytv/fn_hoanthien_hthsthaydoimytv', request),
  // UR2.4.090.015
  FN_MAP_ID: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', data),
  // UR2.4.090.017
  KICHHOAT_MYTV: (axios, request) => axios.post('/web-ccdv/hoanthien_hoso_thaydoi_tsmytv/kichhoat_mytv', request),
  // UR2.4.090.019
  FN_CAPNHAT_HTHSTHAYDOIMYTV: (axios, request) => axios.post('/web-ccdv/hoanthien_hoso_thaydoi_tsmytv/fn_capnhat_hthsthaydoimytv', request),
  // UR2.4.090.020
  SP_LAY_THONGTIN_THEO_HDTB: (axios, request) => axios.post('/web-ccdv/hoanthien_hoso_thaydoi_tsmytv/sp_lay_thongtin_theo_hdtb', request),
  // UR2.4.090_013
  XOA_PHIEU: async (axios, request) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/sp_delete_giaophieu_nv`, request),
  // UR2.4.090_013
  INSERT_GIAOPHIEU_NV: async (axios, request) => axios.post('/web-ccdv/tiepnhanhopdong/insert_giaophieu_nv', request),
  // UR2.5.015
  SP_UPDATE_TT_GIAOVIEC: async (axios, request) => axios.post('/web-ccdv/khaibaoims/sp_update_tt_giaoviec', request),
  // UR2.4.038_042
  UPDATE_ND_THUCHIEN: async (axios, request) => axios.post('/web-hopdong/thaydoi_thongso_megawan/update_nd_thuchien', request),
  fn_tt_hd_thuebao: async (axios, req) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao', req)
}
