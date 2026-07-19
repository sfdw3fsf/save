export default {

  // BSS-52135_001
  SP_LAY_DS_TIEN_KHOANMUC: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/sp_lay_ds_tien_khoanmuc', request),
  // BSS-52135_002
  GET_DICHVU_VT: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
  // BSS-52135_003
  LAY_DS_THAMSO_MD: async (axios) => axios.get(`/web-quantri/danhmuc/thamso_md_theo_ma`),
  // BSS-52135_004, UR2.1.008_022, UR2.1.008_031
  LAY_DS_HOPDONG_THEO_MA_GD: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', data),
  // BSS-52135_005
  LAY_DS_HOPDONG_TB_THEO_HDKH_ID: async (axios, hdkhId) => axios.get(`/web-tracuu/hopdong/ds_hopdong_tb_theo_hdkh_id?hdkhId=${hdkhId}`),
  // BSS-52135_006, UR2.1.008_020, UR2.1.008_030
  LAY_DS_HOPDONG_TB_THEO_HDTB_ID: async (axios, request) => axios.post('/web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id', request),
  // UR2.1.008_007, UR2.1.008_02
  LAY_DANHBA_THEO_MATB: async (axios, data) => axios.post('/web-tracuu/tracu-danhba/LAY_DANHBA_THEO_MATB', data),
  // UR2.1.008_008
  LAY_DS_TB_DVGT_THEO_TT_IMS: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/lay_ds_tb_dvgt_theo_tt_ims', data),
  // UR2.1.008_009
  LAY_DS_THUEBAO_DICHVU_IMS: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/lay_ds_thuebao_dichvu_ims', data),
  LAY_DS_THUEBAO_DICHVU_IMS_BC: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_ds_thuebao_dichvu_ims', data),
  // UR2.1.008_010
  LAY_DS_THUEBAO_DICHVU_LINHVUC: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/lay_ds_thuebao_dichvu_linhvuc', data),
  // LAY_DS_THUEBAO_DICHVU {"hdtbId":0,"thueBaoId":0,"loaiTbId":0,"kieuId":0}
  LAY_DS_THUEBAO_DICHVU: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_thuebao_dichvu', data),
  // UR2.1.008_011, UR2.1.008_014 {"hdtb_id":0,"thuebao_id":0,"loaitb_id":0,"kieu_id":0,"kieuld_id":0}
  LAY_DS_THUEBAO_DICHVU_V2: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/lay_ds_thuebao_dichvu_v2', request),
  LAY_DS_THUEBAO_DICHVU_V2_BC: async (axios, request) => axios.post('/web-bancheo/danhmuc/lay_ds_thuebao_dichvu_v2', request),
  // UR2.1.008_012
  LAY_DS_TB_DVGT_THEO_TRANGTHAI: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/lay_ds_tb_dvgt_theo_trangthai', request),
  // LAY_DS_DANGKY_DVGT_BC
  LAY_DS_DANGKY_DVGT_BC: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/lay_ds_dangky_dvgt_bc', request),
  // UR2.1.008_015 {"in_hdtb_id":2115443}, UR2.1.008_045
  LAY_DS_DK_LYDOHUY: async (axios, request) => axios.post('/web-hopdong/thanhly/lay_ds_dk_lydohuy', request),
  // UR2.1.008_016 { "thuebao_id": 0 } UR2.1.008_028
  LAY_DS_DIACHI_THEO_DBTBID: async (axios, request) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid', request),
  // UR2.1.008_017 { "hdtb_id": 0}
  SP_LAY_THONGTIN_PHIEU_TN: async (axios, request) => axios.post('/web-hopdong/thanhly/sp_lay_thongtin_phieu_tn', request),
  // UR2.1.008_018  { "hdtb_id": 0}
  LAY_TIENHD_KM_THEO_HDTB: async (axios, request) => axios.post('/web-hopdong/khoiphucthanhly/lay_tienhd_km_theo_hdtb', request),
  // UR2.1.008_019
  LAY_DS_DANGKY_DVK: async (axios, request) => axios.post('/web-hopdong//khoiphucthanhly/lay_ds_dangky_dvk', request),
  // UR2.1.008_021
  GET_DICHVUGT_ID_V2: async (axios) => axios.post('/web-hopdong/thaydoidichvu/get_dichvugt_id_v2', {}),
  // UR2.1.008_023 Lấy danh sách hợp đồng theo mã thuê bao, UR2.1.008_032
  LAY_DS_HOPDONG_THEO_MA_TB: (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/lay_ds_hopdong_theo_ma_tb', data),
  // UR2.1.008_025  {"ma_tb":"str","ma_gd":0,"dichvuvt_id":0,"loaihd_id":0}
  KIEMTRA_MA_TB_LAP_HOPDONG_KHAC: async (axios, request) => axios.post('/web-hopdong/khoiphucthanhly/kiemtra_ma_tb_lap_hopdong_khac', request),
  // UR2.1.008_026
  KIEM_TRA_QUYEN_NGUOIDUNG_CHUQUAN: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/kiem_tra_quyen_nguoidung_chuquan', request),
  // UR2.1.008_027 thuebao_id
  LAY_DS_ISDN_THEO_THUEBAO_ID: async (axios, request) => axios.post('/web-hopdong/thanhly/lay_ds_isdn_theo_thuebao_id', request),
  // UR2.1.008_029 {"db_id":1548920,"kieuld_id":39,"loaiid":1}
  LAY_DS_SUDUNG_DVK_KIEU_LD: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/lay_ds_sudung_dvk_kieu_ld', request),
  // UR2.1.008_033
  CSS_LOAIHINH_TB: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
  // UR2.1.008_035 Lấy danh sách kiểu lắp đặt theo loại hợp đồng
  CSS_KIEU_LD: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD'),
  // UR2.1.008_041 {"dichvugt_id":15}
  LAY_THONGTIN_GOICODINH: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/lay_thongtin_goicodinh', request),
  // UR2.1.008_044
  LAY_BIENDONG_THEO_HDTB_ID: async (axios, hdtbId) => axios.get(`/web-hopdong/hopdong/lay_biendong_thongtin_thuebao?hdtbId=${hdtbId}`),
  // UR2.1.008_047
  LAY_DS_LYDO_HUY: async (axios, request) => axios.post('/web-hopdong/thaydoilydohuy/lay_ds_lydo_huy', request),
  // UR2.1.008_051 {"thanhtoan_id":1,"thuebao_id":192040}
  KIEMTRA_NO_THANHLY_YC: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/kiemtra_no_thanhly_yc', request),
  // UR2.1.008_052 {"ma_tb":"dlk.vietnghia1","kieu":1}
  KIEMTRA_TB_MYTV_CHINHPHU: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/kiemtra_tb_mytv_chinhphu', request),
  // UR2.1.008_054 {"thuebao_id":2992885,"loaihd_id":1}
  KIEMTRA_HD_BANCHEO_TC: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/kiemtra_hd_bancheo_tc', request),
  // UR2.1.008_055 Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
  KIEMTRA_MAKH_MTT: async (axios, data) => axios.post('/web-thicong/thicong/kiemtra_makh_mtt', data),
  // UR2.1.008_056 {"thuebao_id":3400543,"kieu_yc":1,"kieu":0}
  FN_KIEMTRA_DK_SD_GREENONLINE: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/fn_kiemtra_dk_sd_greenonline', data),
  // UR2.1.008_057
  KIEMTRA_THAYDOI_GOI_DADV_2: async (axios, loaiHdId, thueBaoId) => axios.get(`/web-hopdong/hopdong/kiemtra_thaydoi?loaiHdId=${loaiHdId}&thueBaoId=${thueBaoId}`),
  // UR2.1.008_058 {"thuebao_id":402733,"ds_dichvugt_id":868}
  KIEMTRA_KM_DATCOC_DVGT: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/kiemtra_km_datcoc_dvgt', request),
  // UR2.1.008_059 {"thuebao_id":518325,"dichvugt_id":714,"kieu_id":1}
  KIEMTRADICHVUDADK: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/kiemtradichvudadk', request),
  // BSS-52135_060
  LAYHUONGGIAO_TIEPNHAN_V2: async (axios, request) => axios.post('/web-hopdong/khoiphucthanhly/layhuonggiao_tiepnhan_v2', request),
  // UR2.1.008_061
  KT_FIBER_COLAPCHUNG: async (axios, request) => axios.post('/web-hopdong/thaydoidichvu/kt_fiber_colapchung', request),
  // UR2.1.008_062 Kiểm tra danh bạ tb khi thêm mới
  KIEMTRA_DB_KHI_THEMMOI: async (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_danhba_khi_themmoi', data),
  // UR2.1.008_063 {"hdtb_id":0,"dshdkh":"str","dshdtb":"str","dshdtb_dv":"str","dsdkdvgt":"str","dstttt":"str","dsctthd":"str","dshdtb_cd":"str","dshdtb_adsl":"str","dshdtb_ims":"str","dshdtb_gp":"str","dshdtb_mgw":"str","dshdtb_tsl":"str","dshdtbdd":"str","dshdtb_cntt":"str","dsdk_huy":"str","huonggiao_id":0,"dsdk_dvk":"str","nhanvien_id":0,"thuhoi":0}
  FN_THEM_HD_THAYDOI_DVGT: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/fn_them_hd_thaydoi_dvgt', data),
  // UR2.1.008_064 {"dshdtb":"str","dsdkdvgt":"str","dstttt":"str","dsctthd":"str","dshdtb_cd":"str","dsdk_huy":"str","huonggiao_id":0,"dshdtbdv":"str","hdtb_id":0,"nhanvien_id":0,"thuhoi":0}
  FN_THEM_HD_DVGT_ISDN: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/fn_them_hd_dvgt_isdn', data),
  // UR2.1.008_065 {"hdkh_id":0,"loaihd_id":0}
  XOA_HDKH_V2: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/xoa_hdkh_v2', data),
  // UR2.1.008_067 {"dshdtb":"str","dshdtb_dv":"str","dsdkdvgt":"str","dstttt":"str","dsctthd":"str","dshdtb_cd":"str","dshdtb_adsl":"str","dshdtb_gp":"str","dshdtb_mgw":"str","dshdtb_tsl":"str","dshdtbdd":"str","dsdk_huy":"str","huonggiao_id":0,"hdtb_id":0,"nhanvien_id":0,"thuhoi":0}
  FN_THEM_THUEBAO_DVGT: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/fn_them_thuebao_dvgt', data),
  // UR2.1.008_069 {"hdtb_id":0}
  XOA_HDTB: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/xoa_hdtb', data),
  // UR2.1.008_074 {"thuebao_id":"str","dichvugt_id":"str"}
  KIEMTRA_DANGKY_DVGT: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/kiemtra_dangky_dvgt', data),
  // UR2.1.008_075
  LAY_DS_TTPH_KH_TAMDUNG_ROIMANG_V2: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/lay_ds_ttph_kh_tamdung_roimang_v2', data),
  // LAY_TIEN_KHOANMUC_LD
  LAY_TIEN_KHOANMUC_LD: async (axios, request) => axios.post('/web-hopdong/khoiphucthanhly/lay_tien_khoanmuc_ld', request),
  // LAY_TIENKHOANMUC_LOAIHD
  LAY_TIENKHOANMUC_LOAIHD: async (axios, vloaihdid) => axios.get(`/web-hopdong/chuyendich/lay_tienkhoanmuc_loaihd?vloaihd_id=${vloaihdid}`),
  // Kiểm tra thông tin gói đồng bộ
  FN_MAP_ID: async (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', data),
  // LAY_DS_HDTB_ADSL_THEO_HDTB_ID
  LAY_DS_HDTB_ADSL_THEO_HDTB_ID: async (axios, hdtbid) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl', {params: {hdtbId: hdtbid}}),
  // LAY_DS_HDTB_MGWAN_THEO_HDTB_ID
  LAY_DS_HDTB_MGWAN_THEO_HDTB_ID: (axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_hdtb_mgwan_theo_hdtb_id', data),
  // LAY_DS_HDTB_TSL_THEO_HDTB_ID
  LAY_DS_HDTB_TSL_THEO_HDTB_ID: (axios, hdtbid) => axios.get(`web-hopdong/hopdong/lay_ds_hdtb_tsl_theo_hdtb_id?hdtbId=${hdtbid}`),
  // LAY_DS_HDTB_CNTT_THEO_HDTB_ID
  LAY_DS_HDTB_CNTT_THEO_HDTB_ID: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_hdtb_cntt_theo_hdtb_id', data),
  // LAY_THUEBAOID_THICONG {"tinh_id":0,"thuebao_id":0}
  //LAY_THUEBAOID_THICONG: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_thuebaoid_thicong', data),
  LAY_THUEBAOID_THICONG: async (axios, data) => axios.post('/web-hopdong/chuyendoi-codinh-sang-ims-bancheo/lay_thuebaoid_thicong', data),

  // LAY_TINHTC_ID_TSL {"ma_tb":"str","dichvuvt_id":0}
  LAY_TINHTC_ID_TSL: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_tinhtc_id_tsl', data),
  // TINH_THICONG {"tinhthicong_id":"str"}
  TINH_THICONG: async (axios, request) => axios.post('/web-bancheo/danhmuc/tinh_thicong', request),
  // LAY_DANHBA_THEO_THUEBAOID {'tinhthicong_id': 0,"thuebao_id":0,"dichvuvt_id":0,"donvi_id":0,"loaihdbc":0}
  LAY_DANHBA_THEO_THUEBAOID: async (axios, request) => axios.post('/web-bancheo/tracuu/lay_danhba_theo_thuebaoid', request),
  // Lay_danhsach_thuoctinh_v2s
  Lay_danhsach_thuoctinh_v2: async (axios, request) => axios.post('/web-bancheo/tracuu/Lay_danhsach_thuoctinh_v2', request),
  // GET_HDTB_TSL_BY_HDTB_ID
  GET_HDTB_TSL_BY_HDTB_ID: async (axios, request) => axios.post('/web-bancheo/tracuu/get_hdtb_tsl_by_hdtb_id', request),
  // BSS-52172 LAY_TINH_THEO_THUEBAOID
  LAY_TINH_THEO_THUEBAOID: async (axios, thuebaoid) => axios.get(`/web-bancheo/thaydoimegawan/lay_tinh_theo_thuebaoid?thuebaoid=${thuebaoid}`),
  // LAY_KIEULD_HDBC_THEO_HDTB_ID
  LAY_KIEULD_HDBC_THEO_HDTB_ID: async (axios, hdtbid) => axios.get(`/web-bancheo/tracuu/lay_kieuld_hdbc_theo_hdtb_id?hdtb_id=${hdtbid}`),
  // lay_kieuld_daucuoi_hdbc_theo_hdtb_id
  lay_kieuld_daucuoi_hdbc_theo_hdtb_id: async (axios, hdtbid) => axios.post(`/web-bancheo/tracuu/lay_kieuld_daucuoi_hdbc_theo_hdtb_id?hdtb_id=${hdtbid}`),
  // UR2.1.008_088 SP_HT_KIEULD_LOAITB_BANCHEO
  SP_HT_KIEULD_LOAITB_BANCHEO: async (axios, req) => axios.post('/web-hopdong/thanhly-bancheo-chonlydohuy/sp_ht_kieuld_loaitb_bancheo', req),
  // TTCT_LyDoHuy
  TTCT_LyDoHuy: async (axios, lydohuyid) => axios.get(`/web-hopdong/thaydoilydohuy/lay_ttct_lydohuy?lydohuy_id=${lydohuyid}`),
  // SP_HIENTHI_TINH_KN
  SP_HIENTHI_TINH_KN: async (axios) => axios.get('/web-bancheo/tracuu/sp_hienthi_tinh_kn'),
  // LAY_TINH_THEO_MATB
  LAY_TINH_THEO_MATB: async (axios, matb) => axios.get(`/web-bancheo/tracuu/lay_tinhthicong_theo_matb?matb=${matb}`),
  // LAY_THUEBAO_KN_ID_THEO_MATB
  LAY_THUEBAO_KN_ID_THEO_MATB: async (axios, matb) => axios.get(`/web-bancheo/tracuu/lay_thuebao_kn_id_theo_matb?matb=${matb}`),
  // SP_LAY_THUEBAOID_THICONG_THEO_MATB { "vma_tb": "ninhgiang_vnnet", "vtinhthicong": 27 }
  SP_LAY_THUEBAOID_THICONG_THEO_MATB: async (axios, req) => axios.post('/web-baohong/TiepNhanBaoHong/sp_lay_thuebaoid_thicong_theo_matb', req),
  // {"ma_tb":"str","dichvuvt_id":0,"donvi_dl_id":0,"loaihd_bancheo":0}
  LAY_DANHBA_THEO_MATB_BC: async (axios, req) => axios.post('/web-bancheo/thaydoimegawan/lay_danhba_theo_matb', req),
  // LAY_DS_DB_CD_THEO_TBID
  LAY_DS_DB_CD_THEO_TBID: async (axios, thuebaoid) => axios.get(`/web-hopdong/chuyendich-codinh/lay_ds_db_cd_theo_tbid?thuebao_id=${thuebaoid}`),
  // LAY_DS_DB_CD_THEO_TBID BC
  LAY_DS_DB_CD_THEO_TBID_BC: async (axios, req) => axios.post('/web-bancheo/tracuu/lay_ds_db_cd_theo_tbid', req),
  // LAY_DS_DB_IMS_THEO_TBID {"thuebao_id":0}
  LAY_DS_DB_IMS_THEO_TBID: async (axios, req) => axios.post('/web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_ims_theo_tbid', req),
  // LAY_DS_DB_ADSL_THEO_TBID
  LAY_DS_DB_ADSL_THEO_TBID: async (axios, thuebaoid) => axios.get(`/web-hopdong/chuyendich-adsl/lay_ds_db_adsl_theo_tbid?thuebao_id=${thuebaoid}`),
  // LAY_DS_DB_DD_THEO_TBID
  LAY_DS_DB_DD_THEO_TBID: async (axios, req) => axios.post('/web-hopdong/tachnhapthuebao/lay_ds_db_adsl_theo_tbid', req),
  LAY_DS_DB_MEGAWAN_THEO_TBID: async (axios, thuebaoid) => axios.get(`/web-hopdong/hopdong/lay_ds_danhba_megawan?thueBaoId=${thuebaoid}`),
  LAY_DS_DB_TSL_THEO_TBID: async (axios, thuebaoid) => axios.get(`/web-hopdong/chuyendich-tsl/lay_ds_db_tsl_theo_tbid?thuebao_id=${thuebaoid}`),
  LAY_DS_DB_GP_THEO_TBID: async (axios, thuebaoid) => axios.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_db_gp_theo_tbid?thuebao_id=${thuebaoid}`),
  LAY_DS_DB_CNTT_THEO_TBID: async (axios, req) => axios.post('/web-hopdong/khuyenmai-thaydoi-loaihinhtb/lay_ds_db_cntt_theo_tbid', req),
  fn_tt_db_thuebao: async (axios, req) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao', req),
  fn_tt_doitac: async (axios, req) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_doitac', req),
  lay_ds_db_ims_theo_tbid_bc: async (axios, req) => axios.post('/web-bancheo/tracuu/lay_ds_db_ims_theo_tbid', req),
  LAY_DS_DB_MEGAWAN_THEO_TBID_BC: async (axios, req) => axios.post('/web-bancheo/tracuu/lay_ds_db_megawan_theo_tbid', req),
  LAY_DS_DB_ADSL_THEO_TBID_BC: async (axios, req) => axios.post('/web-bancheo/tracuu/lay_ds_db_adsl_theo_tbid', req),
  LAY_DS_DB_TSL_THEO_TBID_BC: async (axios, req) => axios.post('/web-bancheo/tracuu/lay_ds_db_tsl_theo_tbid', req),
  thamso_md_theo_ma: (axios, mats) => axios.get(`/web-quantri/danhmuc/thamso_md_theo_ma?MA_TS=${mats}`),
  LAY_ND_SMS_CANHBAO_THIETHAI: async (axios, req) => axios.post('/web-hopdong/thanhly/lay_nd_sms_canhbao_thiethai', req),
  Lay_tienkhoanmuc_loaihd: async (axios, loaihd) => axios.get(`/web-hopdong/chuyendich/lay_tienkhoanmuc_loaihd?vloaihd_id=${loaihd}`),
  XOA_HDBC: async (axios, req) => axios.post(`/web-bancheo/thaydoidvgtbc/xoa_hdbc`, req),
  lay_huonggiao_tiepnhan_v2: async (axios, data) => axios.post(`/web-hopdong/thanhly-thuebao-bancheo/layhuonggiao_tiepnhan_v2`, data),
  KIEMTRA_HDTB_KHI_THEMMOI: async (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_hdtb_khi_themmoi', data),
  kiemtra_tb_cung_makh_tb_pl: (axios, data) => axios.post(`/web-hopdong/khoiphucthanhly/kiemtra_tb_cung_makh_tb_pl`, data),
  // GET KEYS
  POST_GET_KEYS: async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
  KT_THAOTAC: async (axios, luongid, code) => axios.get(`/web-hopdong/chuyendich/kt_thaotac?luong_id=${luongid}&code=${code}`),
  lay_thongtin_dbtb_dv: async (axios, thuebaoid) => axios.get(`/web-hopdong/hopdong/lay_thongtin_dbtb_dv?thuebao_id=${thuebaoid}`),
  LAY_KHACHHANGID_THICONG: async (axios, req) => axios.post('/web-bancheo/tracuu/lay_khachhangid_thicong', req),
  lay_cuoctb_theo_cuocid: async (axios, req) => axios.post('/web-bancheo/tracuu/lay_cuoctb_theo_cuocid', req),
  LAY_DS_DB_THUEBAO_THEO_TBID: async (axios, req) => axios.post('/web-bancheo/tracuu/lay_ds_db_thuebao_theo_tbid', req),
  lay_thanhtoan_theo_thuebaoid: async (axios, thuebaoid) => axios.get(`/web-bancheo/tracuu/lay_thanhtoan_theo_thuebaoid?thuebao_id=${thuebaoid}`),
  lay_donvi_dau_cuoi_theo_thuebaoid: async (axios, req) => axios.post('/web-bancheo/tracuu/lay_donvi_dau_cuoi_theo_thuebaoid', req),
  // Sinh mã giao dịch theo đơn vị
  SINH_MAGD_V2: async (axios, data) => axios.post('/web-hopdong/CapNhatTraTruoc/sinh_magd_theo_dv', data),
  // Sinh mã giao dịch
  SINH_MAGD: async (axios, data) => axios.post('/web-hopdong/CapNhatTraTruoc/sinh_magd', data),
  FN_TT_DB_KHACHHANG: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_khachhang', data),
  lay_ds_lydohuy: async (axios, loaildId, loaitbId) => axios.get(`/web-hopdong/thanhly/lay_ds_lydohuy?loaildId=${loaildId}&loaitbId=${loaitbId}`),
  LAY_DS_DB_LYDOHUY_THEO_TBID: async (axios, thuebaoid) => axios.get(`web-hopdong/thaydoidichvu/lay_ds_db_lydohuy_theo_tbid?thuebao_id=${thuebaoid}`),
  fn_tt_bd_thuebao: async (axios, req) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_bd_thuebao', req),
  LAY_DS_DB_TSL_THEO_TBID_2: async (axios, thuebaoid) => axios.get(`/web-hopdong/hopdong/lay_ds_danhba_tsl?thueBaoId=${thuebaoid}`),
  FN_TT_DB_THUEBAO: async (axios, req) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao', req),
  tracuu_no_thanhtoan: async (axios, thanhtoanid) => axios.get(`/web-hopdong/hopdong/tracuu_no_thanhtoan?thanhToanId=${thanhtoanid}`),
  tracuuno_thuebao: async (axios, thuebaoid) => axios.get(`/web-hopdong/hopdong/tracuuno_thuebao?vthuebao_id=${thuebaoid}`),
  lay_ds_thamso_md_mats: async (axios, req) => axios.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_thamso_md_mats', req),
  kiem_tra_dvgt: async (axios, req) => axios.post('/web-hopdong/thaydoidichvu/kiem_tra_dvgt', req),
  layhuonggiao_tiepnhan_v3: async (axios, req) => axios.post('/web-hopdong/chuyen-dich-ban-cheo/layhuonggiao_tiepnhan', req),
  lay_ds_ttph_kh_tamdung_roimang: async (axios, thuebaoid) => axios.get(`/web-hopdong/thaydoidichvu/lay_ds_ttph_kh_tamdung_roimang?vthuebao_id=${thuebaoid}`),
  LAY_DS_HOPDONG_TB_THEO_HDTB_ID_BC: async (axios, hdtbid) => axios.post('/web-tracuu/thuebaoims/lay_ds_hopdong_tb_theo_hdtb_id', { 'in_hdtb_id': hdtbid }),
  // UR2.1.008_080 { "p_ds_para": "" }
  fn_themmoi_hdbancheo_biendong: async (axios, req) => axios.post('/web-bancheo/thaydoidvgtbc/fn_themmoi_hdbancheo_biendong', req),
  // UR2.1.008_078 { "p_ds_para": "" }
  fn_themmoi_hdbancheo_biendong_v2: async (axios, req) => axios.post('/web-bancheo/thaydoidvgtbc/fn_themmoi_hdbancheo_biendong_v2', req),
  // {"p_ds_para":"str"}
  fn_capnhat_hdbancheo_biendong_dvgt: async (axios, req) => axios.post('/web-bancheo/thaydoidvgtbc/fn_capnhat_hdbancheo_biendong_dvgt', req),
  // {"p_huonggiao_id":0,"p_js_HDKH":"str","p_js_HDTB":"str","p_js_HDTB_DV":"str","p_js_dangky_dvgt":"str","p_js_TTTT":"str","p_js_CTTHD":"str","p_js_HDTB_CD":"str","p_js_HDTB_ADSL":"str","p_js_HDTB_IMS":"str","p_js_HDTB_GP":"str","p_js_HDTB_MGW":"str","p_js_HDTB_TSL":"str","p_js_HDTB_DD":"str","p_js_HDTB_CNTT":"str","p_js_dangky_huy":"str","p_js_dangky_dvk":"str"}
  sp_thaydoidv_themmoi_hopdong_v2: async (axios, req) => axios.post('/web-bancheo/thaydoidvgtbc/sp_thaydoidv_themmoi_hopdong_v2', req),
  // {"p_js_bd_lydohuy":"str"}
  sp_insert_bd_lydohuy: async (axios, req) => axios.post('/web-bancheo/thaydoidvgtbc/sp_insert_bd_lydohuy', req),
  // {"vdsHDTB":"str","vdsDKDVGT":"str","vdsTTTT":"str","vdsCTTHD":"str","vdsHDTB_CD":"str","vdsDK_HUY":"str","vhuonggiao_id":0,"vdsHDTBDV":"str"}
  fn_them_hd_dvgt_isdn_bancheo: async (axios, req) => axios.post('/web-bancheo/thaydoidvgtbc/fn_them_hd_dvgt_isdn_bancheo', req),
  lay_ds_kieu_ld_bc: async (axios, req) => axios.post('/web-hopdong/thaydoitocdoadsl/lay_ds_kieu_ld_bc', req),
  // {"p_huonggiao_id":0,"p_js_hd_thuebao":"str","p_js_HDTB_DV":"str","p_js_dangky_dvgt":"str","p_js_TTTT":"str","p_js_CTTHD":"str","p_js_HDTB_CD":"str","p_js_HDTB_ADSL":"str","p_js_HDTB_IMS":"str","p_js_HDTB_GP":"str","p_js_HDTB_MGW":"str","p_js_HDTB_TSL":"str","p_js_HDTB_DD":"str","p_js_dangky_huy":"str"}
  sp_thaydoidv_themmoi_hopdong: async (axios, req) => axios.post('/web-bancheo/thaydoidvgtbc/sp_thaydoidv_themmoi_hopdong', req),
  // {"vthuebao_id":"str","vkieu":"str"}
  lay_ds_chitiet_km_theo_tbid: async (axios, req) => axios.post('/web-bancheo/tracuu/lay_ds_chitiet_km_theo_tbid', req),
  layhuonggiao_tiepnhan : async (axios, data) => axios.post('/web-bancheo/danhmuc/layhuonggiao_tiepnhan',data),
  sp_thaydoidv_capnhat_hopdong_v2 : async (axios, data) => axios.post('/web-bancheo/thaydoidvgtbc/sp_thaydoidv_capnhat_hopdong_v2',data),
  post_layhuonggiao_tiepnhan_v4: async (axios, data) => axios.post('/web-bancheo/danhmuc/layhuonggiao_tiepnhan_v4', data),
}
