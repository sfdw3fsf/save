export default {
  // select box dich vu
  GET_DICHVU_VT: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
  // select box ten goi
  GET_GOI_DA_DV_ALL: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_ds_goi_dadv', data),
  // select box hieu luc
  SP_DS_HIEULUC: (axios) => axios.post('/web-hopdong/goi-da-dichvu/sp_ds_hieuluc', {}),
  // select box nghiep vu
  SP_DS_NGHIEPVU: (axios) => axios.post('/web-hopdong/goi-da-dichvu/sp_ds_nghiepvu', {}),
  // select box chuyen loai goi
  LAY_DS_GOI_CV_VNP_V1: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/lay_ds_goi_cv_vnp_v1', data),
  // Lấy danh sách tham số MD
  SP_DS_THAMSO_MD: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_ds_thamso_md', data),
  // Lấy danh sách hợp đồng thuê bao theo hdkh_id
  LAY_DS_HOPDONG_TB_THEO_HDKH_ID: (axios, hdkhId) => axios.get(`/web-tracuu/hopdong/ds_hopdong_tb_theo_hdkh_id?hdkhId=${hdkhId}`),
  // Lấy danh sách hợp đồng thuê bao gói đa dịch vụ theo hdtb_id
  SP_DS_HDTB_GOI_DADV_THEO_HDTB: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_ds_hdtb_goi_dadv_theo_hdtb', data),
  // Lấy danh sách thuê bao gói đa dịch vụ theo nhóm
  LAY_DS_TB_GOI_DADV_1: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/lay_ds_tb_goi_dadv_1', data),
  // Lấy danh sách thuê bao gói đa dịch vụ theo mã tb
  LAY_DS_TB_GOI_DADV: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/lay_ds_tb_goi_dadv', data),
  // Lấy thông tin gói cũ, gói mới theo dbtb_id và kieu lắp đặt
  SP_HDTB_GOI_DADV_THEO_KIEULD: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_hdtb_goi_dadv_theo_kieuld', data),
  // Lấy thông tin công văn
  SP_LAY_SOCONGVAN_GOIDADV: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_lay_socongvan_goidadv', data),
  // Lấy thông tin tách ghép
  KT_TACH_GHEP: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/kt_tach_ghep', data),
  // Lấy danh sách thuê bao gói đa dịch vụ theo mã tb
  SP_LAY_TT_GOI_THEO_NHOMNEO: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_lay_tt_goi_theo_nhomneo', data),
  // Lấy danh sách hợp đồng theo mã giao dịch
  LAY_DS_HOPDONG_THEO_MA_GD: (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', data),
  // Lấy danh sách loại hình thuê bao
  CSS_LOAIHINH_TB: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
  // Lấy danh sách kiểu lắp đặt theo loại hợp đồng
  CSS_KIEU_LD: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD'),
  layds_kieuld_theo_lhtb: async (axios, data) => axios.post(`/web-hopdong/thaydoimytv/lay_ds_kieu_ld`, data),
  // Kiểm tra điều kiện chuyển gói
  KT_GDVP_HOME: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/kt_gdvp_home', data),
  // Kiểm tra thay đổi gói đa dịch vụ
  KIEMTRA_THAYDOI_GOI_DADV: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/kiemtra_thaydoi_goi_dadv', data),
  // Kiểm tra fiber mytv cùng cáp
  KT_FIBER_MYTV_CUNG_CAP: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/kt_fiber_mytv_cung_cap', data),
  // Kiểm tra gói còn hiệu lực không
  KT_GOI_DADV_HIEULUC: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/kt_goi_dadv_hieuluc', data),
  // Kiểm tra hủy gói gia đình
  KT_HUYGOI_FAMILY: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/kt_huygoi_family', data),
  // GET KEYS
  POST_GET_KEYS: async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
  // Sinh mã giao dịch theo đơn vị
  SINH_MAGD_V2: async (axios, data) => axios.post('/web-hopdong/CapNhatTraTruoc/sinh_magd_theo_dv', data),
  // Sinh mã giao dịch
  SINH_MAGD: async (axios, data) => axios.post('/web-hopdong/CapNhatTraTruoc/sinh_magd', data),
  // Lấy hướng giao tiếp nhận
  SP_LAYHUONGGIAO_TIEPNHAN: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_layhuonggiao_tiepnhan', data),
  // Lấy mức cước thuê bao
  SP_LAY_MUCCUOC_TB: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_lay_muccuoc_tb', data),
  // Lấy danh sách gói công văn
  LAY_DS_GOI_CV_VNP: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/lay_ds_goi_cv_vnp', data),
  // Lấy thông tin nhóm neo thuê bao gói theo mã thuê bao
  SP_LAY_NHOMNEO_GOI_THEO_MATB: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_lay_nhomneo_goi_theo_matb', data),
  // Lấy thông tin nhóm tb theo nhóm tb neo
  SP_LAY_NHOMTB_THEO_NHOMNEO: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_lay_nhomtb_theo_nhomneo', data),
  // Lấy thông tin nhóm tb theo matb
  SP_LAY_NHOMTB_THEO_MA_TB: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_lay_nhomtb_theo_ma_tb', data),
  // Lấy thông tin loại tb chủ nhóm
  SP_LAY_LOAITB_CHUNHOM: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_lay_loaitb_chunhom', data),
  // Lấy thông tin nhóm tb gói, đổi nhóm tb
  SP_LAY_NHOMTB_DOINHOMTB: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_lay_nhomtb_doinhomtb', data),
  // Xóa hợp đồng khách hàng
  XOAHDKH_V2: (axios, data) => axios.post('/web-hopdong/hopdong/khoa_mo_thuebao_no_cuoc/xoa_hopdong', data),
  // Kiểm tra hợp đồng thuê bao khi thêm mới, UR2.1.030_078
  KIEMTRA_HDTB_KHI_THEMMOI: (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_hdtb_khi_themmoi', data),
  // Kiểm tra danh bạ tb khi thêm mới
  KIEMTRA_DB_KHI_THEMMOI: (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_danhba_khi_themmoi', data),
  // Thêm mới hợp đồng thuê bao CLOB
  SP_INS_HDTB_THAYDOI_GOIDADV: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_ins_hdtb_thaydoi_goidadv', data),
  // Kiểm tra thông tin gói đồng bộ
  FN_MAP_ID: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', data),
  // thông tin hủy gói cước tb trên CCBS
  TS_THONGTIN_HUY_GOICUOC_TB: (axios, data) => axios.post('/ccbs/executor/ts_thongtin_huy_goicuoc_tb', data),
  // Xóa hợp đồng thuê bao
  XOA_HDTB: (axios, hdtbId) => axios.post(`/web-hopdong/hopdong/xoa_hopdong_thuebao?hdtbId=${hdtbId}`),
  // Lấy danh sách hợp đồng theo mã thuê bao
  LAY_DS_HOPDONG_THEO_MA_TB: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/lay_ds_hopdong_theo_ma_tb', data),
  // Lấy danh bạ theo mã tb
  LAY_DANHBA_THEO_MATB: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_danhba_theo_matb', data),
  // Lấy thông tin nhóm gói tách
  SP_TT_NHOM_GOI_TACH: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_tt_nhom_goi_tach', data),
  // Lấy ID thuê bao chủ nhóm theo nhóm tb, gói
  SP_LAY_THUEBAO_THEO_NHOMTB: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_lay_thuebao_theo_nhomtb', data),
  // Lấy danh sách nhóm thuê bao sử dụng gói v1
  SP_LAYDS_TB_THEO_NHOMTB_GOI_V1: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_layds_tb_theo_nhomtb_goi_v1'),
  // Lấy id nhóm neo theo nhóm tb, id gói
  FN_LAYNHOMNEO_THEO_NHOMTB_GOI: (axios, goiId, nhomtbId) => axios.get(`/web-hopdong/goi-da-dichvu/laynhomneo_theo_nhomtb_goi?goiId=${goiId}&nhomtbId=${nhomtbId}`),
  // Lấy danh sách nhóm thuê bao sử dụng gói v2
  SP_LAYDS_TB_THEO_NHOMTB_GOI_V2: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_layds_tb_theo_nhomtb_goi_v2', data),
  // Lấy danh sách nhóm thuê bao sử dụng gói v3
  SP_LAYDS_TB_THEO_NHOMTB_GOI_V3: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_layds_tb_theo_nhomtb_goi_v3', data),
  // Lấy danh sách nhóm thuê bao sử dụng gói v4
  SP_LAYDS_TB_THEO_NHOMTB_GOI_V4: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/sp_layds_tb_theo_nhomtb_goi_v4', data),
  // Cập nhật hợp đồng gói đa dịch vụ
  SP_UPDATE_HDTB_GOIDADV: (axios, data) => axios.post('/web-hopdong/dk_goi_dadichvu/sp_update_hdtb_goidadv', data),
  // Thêm mới hợp đồng
  SP_INS_HOPDONG_THAYDOI_GOIDADV: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/themmoi_hopdong', data),
  // Cap nhat trang thai ttphdv_id sau khi lien ket hd
  B2A_HT_PHIEUCSKHDV: (axios, data) => axios.post('/web-hopdong/dk_goi_dadichvu/b2a_ht_phieucskhdv', data),
  // SP_LAY_DULIEU_BANG_THEO_DIEUKIEN
  SP_LAY_DULIEU_BANG_THEO_DIEUKIEN: async (axios, request) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', request),
  // SP_HUONGGIAO_RUNSQL
  SP_HUONGGIAO_RUNSQL: async (axios, request) => axios.post('/web-danhba/huonggiao/sp_huonggiao_runsql', request),
  // UR2.1.018_042
  FN_KTRA_GOI_CHUA_DIDONG: async (axios, request) => axios.post('/web-hopdong/goi-da-dichvu/fn_ktra_goi_chua_didong', request),
  // UR2.1.018_041
  SP_LAY_TT_NHOMTB_SD_GOI: async (axios, request) => axios.post('/web-hopdong/goi-da-dichvu/sp_lay_tt_nhomtb_sd_goi', request),
  // hoanthien_hoso
  hoanthien_hoso: async (axios, req) => axios.post('/web-hopdong/goi-da-dichvu/hoanthien_hoso', req),
  FN_TT_GOI_DADV: async (axios, req) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_goi_dadv', req),
  FN_TT_GOI_DADV_LHTB: async (axios, req) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_goi_dadv_lhtb', req),
  sp_tt_db_thuebao: async (axios, req) => axios.post(' /web-quantri/lay-dulieu/sp_tt_db_thuebao', req),
  lay_ds_goi_theo_nhomneo: async (axios, nhomtbidneo) => axios.get(`/web-hopdong/goi-da-dichvu/lay_ds_goi_theo_nhomneo?nhomtb_id_neo=${nhomtbidneo}`),
  fn_ktra_hieuluc_goi_dadv_lhtb: async (axios, goi_id) => axios.get(`/web-tracuu/hopdong/fn_ktra_hieuluc_goi_dadv_lhtb?goi_id=${goi_id}`) 
}
