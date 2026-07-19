export default {
  // UR2.1.036_002
  CSS_DICHVU_VT: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
  // UR2.1.036_003
  // Lấy danh sách kiểu lắp đặt theo loại hợp đồng
  CSS_KIEU_LD: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD'),
  // UR2.1.036_004
  CSS_TRANGBI: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TRANGBI'),
  // UR2.1.036_005
  SP_LAY_DS_LOAI_TB_LHTB: async (axios, data) => axios.post('/web-hopdong/thaydoimytv/sp_lay_ds_loai_tb_lhtb', data),
  // UR2.1.036_006
  LAY_DS_THAMSO_MD: async (axios) => axios.get(`/web-quantri/danhmuc/thamso_md_theo_ma`),
  // UR2.1.036_007
  CSS_LOAIHINH_TB: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
  // UR2.1.036_008
  LAY_TIEN_KHOANMUC_LD: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_tien_khoanmuc_ld', data),
  // UR2.1.036_009
  LAY_DS_HOPDONG_THEO_MA_GD: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_gd', data),
  // UR2.1.036_010
  LAY_DS_HOPDONG_THEO_MA_TB: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb', data),
  // UR2.1.036_011
  LAY_DS_HOPDONG_TB_THEO_HDKH_ID: async (axios, hdkhId) => axios.get(`/web-tracuu/hopdong/ds_hopdong_tb_theo_hdkh_id?hdkhId=${hdkhId}`),
  // UR2.1.036_012
  LAY_DS_HOPDONG_THUEBAO_ADSL: async (axios, hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId=${hdtbId}`),
  // UR2.1.036_013
  LAY_DS_MUCUOC_TB_V2: async (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2', data),
  // UR2.1.036_014
  LAY_TT_TRANGGBI: async (axios, data) => axios.post('/web-ccdv/khaibaotsl/lay_tt_tranggbi', data),
  // UR2.1.036_015
  LAY_DANHBA_THEO_MATB: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_danhba_theo_matb', data),
  // HienThiDanhBa
  KIEMTRA_MA_TB_LAP_HOPDONG_KHAC: async (axios, data) => axios.post('/web-hopdong/chuyendich/kiemtra_ma_tb_lap_hopdong_khac', data),
  // UR2.1.036_025
  SP_CAPNHAT_HD_THAYDOI_TS_MYTV: async (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_capnhat_hd_thaydoi_ts_mytv', data),
  // UR2.1.036_028
  HOANTHIEN_HS_THAYDOI_TS_MYTV: async (axios, data) => axios.post('/web-hopdong/thaydoimytv/hoanthien_hoso', data),
  // GET KEYS
  POST_GET_KEYS: async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
  // Sinh mã giao dịch theo đơn vị
  SINH_MAGD_V2: async (axios, data) => axios.post('/web-hopdong/CapNhatTraTruoc/sinh_magd_theo_dv', data),
  // Sinh mã giao dịch
  SINH_MAGD: async (axios, data) => axios.post('/web-hopdong/CapNhatTraTruoc/sinh_magd', data),
  // Api đa năng lấy tham số mặc định
  LAY_DS_THAMSO_MD_ALL: async (axios, data) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md', data),
  // Api đa năng lấy tham số mặc định v2
  LAY_DS_THAMSO_MD_ALL_V2: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
  // Lấy danh sách db adsl theo tb id
  LAY_DS_DB_ADSL_THEO_TBID: async (axios, thuebaoId) => axios.get(`/web-tracuu/tracu-danhba/ds_adsl_tb/${thuebaoId}`),
  // Lâys hướng giao tiếp nhận
  LAY_HUONGGIAO_TIEPNHAN: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_huonggiao_tiepnhan', data),
  // Lấy danh sách kiểu lắp đặt
  LAY_DS_KIEU_LD: async (axios, data) => axios.post('/web-hopdong/thaydoimytv/lay_ds_kieu_ld', data),
  // Kiểm tra thông tin gói đồng bộ
  FN_MAP_ID: async (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', data),
  // Kích hoạt tổng đài // UR2.1.036_029
  KICHHOAT_TONGDAI: async (axios, data) => axios.post('/web-hopdong/thaydoimytv/kichhoat_tongdai', data),
  // Kiểm tra danh bạ tb khi thêm mới
  KIEMTRA_DB_KHI_THEMMOI: (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_danhba_khi_themmoi', data),
  // V2
  KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_V2: async (axios, data) => axios.post('/web-hopdong/chuyendich/kiemtra_ma_tb_lap_hopdong_khac_v2', data),
  // Cap nhat trang thai ttphdv_id sau khi lien ket hd
  B2A_HT_PHIEUCSKHDV: (axios, data) => axios.post('/web-hopdong/dk_goi_dadichvu/b2a_ht_phieucskhdv', data),
  // Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
  KIEMTRA_MAKH_MTT: (axios, data) => axios.post('/web-thicong/thicong/kiemtra_makh_mtt', data),
  // SP_LAY_DULIEU_BANG_THEO_DIEUKIEN
  SP_LAY_DULIEU_BANG_THEO_DIEUKIEN: async (axios, request) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', request),
  FN_TT_DB_ADSL: async (axios, request) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_adsl', request),
  fn_tt_giaophieu: async (axios, req) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieu', req),
  FN_TT_HDTB_ADSL: async (axios, req) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_adsl', req),
  sp_tt_db_thuebao: async (axios, req) => axios.post('/web-quantri/lay-dulieu/sp_tt_db_thuebao', req),
  sp_tt_dbtb_dv: async (axios, req) => axios.post('/web-hopdong/laydulieu/sp_tt_dbtb_dv', req),
  sp_tt_loaihinh_tb: async (axios, req) => axios.post('/web-quantri/lay-dulieu/sp_tt_loaihinh_tb', req)
}
