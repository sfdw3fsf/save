export default {
  // API
  getComboxDataLHTB: (axios) => axios.get('/web-ccdv/ban-thiet-bi/sp_ht_loaihinh_tb_all'),
  //   [{
  //     "loaitb_id": 73,
  //     "loaihinh_tb": "Trực thông quang"
  // }]

  getComboxDataLHDV: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
  // [{DICHVUVT_ID: 17
  //   GHICHU: null
  //   MA_DVVT: "VSAT"
  //   SUDUNG: 1
  //   SU_DUNG: null
  //   TEN_DVVT: "VSAT IP"}]

  //   {
  //     "ma_tb":3956205
  // }
  sp_lay_ds_db_thuebao_theo_ma_tb: (axios, data) => axios.post('web-ccdv/ban-thiet-bi/sp_lay_ds_db_thuebao_theo_ma_tb', data),

  //   {
  //     "thuebao_id":3956205
  // }
  sp_lay_diachi_theo_thuebao_id: (axios, data) => axios.post('web-ccdv/ban-thiet-bi/sp_lay_diachi_theo_thuebao_id', data),

  // {
  //   "in_thuebao_id": 1469947
  // }
  lay_ds_db_dd_theo_tbid: (axios, data) => axios.post('web-quantri/thutienphatsinh/lay_ds_db_dd_theo_tbid', data),

  // {
  //   "p_thuebao_id": 274191
  // }
  sp_lay_ds_dbtb_dv: (axios, data) => axios.post('web-quantri/thutienphatsinh/sp_lay_ds_dbtb_dv', data),

  //   {
  //     "in_ma_tb": "ha3837281",
  //     "in_dichvuvt_id": 4,
  //     "in_donvi_dl_id": 0
  // }
  lay_danhba_theo_matb: (axios, data) => axios.post('web-danhba/chuanhoa_chuquan/lay_danhba_theo_matb', data),

  //   {
  //     "dichvuvt_id":1550191,
  //      "tocdo":1550191,
  //       "muccuoc":1550191,
  //        "loaihinh":1550191
  // }
  sp_lay_ds_quytrinh_ban_thietbi: (axios, data) => axios.post('web-ccdv/ban-thiet-bi/sp_lay_ds_quytrinh_ban_thietbi', data),

  // {
  //   "dichVuVtId": 0,
  //   "donViDlId": 0,
  //   "maTb": "string"
  // }
  lay_ds_danhba_cntt: (axios, data) => axios.get('web-hopdong/hopdong/lay_ds_danhba_cntt', { params: data }),

  // {
  //   "p_donviql_id": 6,
  //   "p_loaidv_id": 54
  // }
  sp_lay_ds_donvi_tramtc_banthietbi: (axios, data) => axios.post('web-quantri/thutienphatsinh/sp_lay_ds_donvi_tramtc_banthietbi', data),
  // [
  //   {
  //     "donvi_id": 0,
  //     "ten_dv": null
  //   },
  //   {
  //     "donvi_id": 305,
  //     "ten_dv": "Đài 1080 - Tổ Khai thác - 1080"
  //   }
  // ]

  // List<TraCuuHopDong> ds = _hdtbServices.TraCuuHopDongKH_V2(txtMaGD.Text, LoaiHopDong.BAN_THIETBI, tt_nd.donvi_id, TrangThaiHD.MOI, tt_nd.nhanvien_id, 0);
  // CSS.LAY_DS_HOPDONG_THEO_MA_GD
  lay_ds_hopdong_theo_ma_gd: (axios, data) => axios.post('web-thicong/hoinghi_truyenhinh/lay_ds_hopdong_theo_ma_gd', data),
  // REQ:
  // {
  // "in_ma_gd": 1,
  // "in_loaihd_id": 24,
  // "in_donvi_id": 1,
  // "in_tthd_id": 9,
  // "in_nhanvien_id": 0,
  // "in_donvi_dl_id": 0
  // }
  // RES:
  // {
  //     "error": "204",
  //     "error_code": "BSS-00000204",
  //     "message": "No Content",
  //     "message_detail": null,
  //     "request_id": null,
  //     "data": []
  // }

  // + Lấy danh sach hợp đồng thuê bao thoe HDKH
  // List<TraCuuHDTBTheoHDKH> ds_hdtb = _hdtbServices.LayDanhSachHDTBTheoHDKH_V2(hdkh_id);
  // CSS.TRACUU_HOPDONG.LAY_DS_HOPDONG_TB_THEO_HDKH_ID
  ds_hopdong_tb_theo_hdkh_id: (axios, params) => axios.get('/web-tracuu/hopdong/ds_hopdong_tb_theo_hdkh_id', { params }),
  // hdkhId=4922568

  // + Lấy danh sách thiết bị
  //  List<DSThietBi_HDTB> dstbi = _hdtbServices.LayDSThietBi_HDTB(hdtb_id);
  // CSS.LAPHOPDONG_COMMON.LAY_DS_MUA_TBI
  lay_ds_mua_tbi: (axios, data) => axios.post('web-hopdong/thanhly/lay_ds_mua_tbi', data),

  // {"hdtb_id":3340622}

  // + Lấy thông tin tinhg tranh, tên thiết bị
  // DataSet ds = bangts.TTCT_ThietBi(Convert.ToInt32(dsCTM_TBI.Tables[0].Rows[i]["loaitbi_id"]));
  //                 string tenTbi = ds.Tables[0].Rows[0]["LOAI_TBI"].ToString() + " - Số lượng: " + dsCTM_TBI.Tables[0].Rows[i]["SOLUONG"].ToString();
  // CSS.SP_HT_TTCT_THIETBI_LOAITBI
  sp_ht_ttct_thietbi_loaitbi: (axios, data) => axios.post('web-ccdv/ban-thiet-bi/sp_ht_ttct_thietbi_loaitbi', data),
  // REQ:{
  //     "loaitbi_id":181
  // }
  // RESP:

  // + Lấy danh sach KM Thiết bị
  //  DataTable dtTMP_KMTBI = bangts.LayDS_KM_ThietBi(ds_CTKM(), dsCTM_TBI.Tables[0].Rows[i]["loaitbi_id"].ToString());
  // CSS.SP_LAY_CTKM_LOAI_TBI
  sp_lay_ctkm_loai_tbi: (axios, data) => axios.post('web-ccdv/ban-thiet-bi/sp_lay_ctkm_loai_tbi', data),
  // REQ:{
  //     "ds_ctkm_id":"13120,13121,13122,13123",
  //     "loaitbi_id":"266"
  // }
  // RESP:

  // + Lấy danh sach tiền Khoan mục
  //  string sql = @"SELECT DISTINCT a.kieuld_id,c.khoanmuctt_id,0 tien, 0 vat, c.tyle_vat
  //                             FROM {?DB2}.kmtt_kieuld a,
  //                             {?DB2}.kieu_ld b,
  //                             {?DB2}.khoanmuc_tt c
  //                             WHERE a.kieuld_id = b.kieuld_id
  //                             AND a.khoanmuctt_id = c.khoanmuctt_id
  //                             AND b.loaihd_id = 15
  //                             ORDER BY c.khoanmuctt_id";
  //             DataTable dt = tdan.GET_DATA_SQL(sql);
  // CSS.SP_LAY_TIENKHOANMUC_LOAIHD_BANTHIETBI
  sp_lay_tienkhoanmuc_loaihd_banthietbi: (axios) => axios.get('web-ccdv/ban-thiet-bi/sp_lay_tienkhoanmuc_loaihd_banthietbi'),
  lay_ds_tien_kmtt: (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_ds_tien_kmtt', data),
  // --------------------------------------------------------
  //   CSS.LAYHUONGGIAO_TIEPNHAN
  // Sử dụng API tại UR2.1.030_081

  lay_huonggiao_tiepnhan: (axios, data) => axios.post('web-hopdong/hopdong/lay_huonggiao_tiepnhan', data),

  // {
  //   "kieuLdId": 0,
  //   "loaiHdId": "string",
  //   "loaiTbId": 0,
  //   "mucCuocId": 0,
  //   "tocDoId": 0
  // }
  // --------------------------------------------------------
  // CSS.LAP_HOP_DONG.SINH_MAGD
  // Sử dụng API tại UR2.1.007_051
  sinh_magd: (axios, data) => axios.post('web-hopdong/khoiphucthanhly/sinh_magd', data),

  // {"loaihd_id":3}
  // --------------------------------------------------------
  // ADMIN.GET_KEYS
  // Sử dụng API tại UR1.1.072.04
  get_keys: (axios, data) => axios.post('web-quantri/donvitinh/get_keys', data),
  // REQ:{

  // "keyname": "DVITINH_ID",
  // "numblocksize": 1,
  // "numretry": 10

  // }
  // RES:{
  //     "error": "200",
  //     "error_code": "BSS-00000000",
  //     "message": "Success",
  //     "message_detail": null,
  //     "request_id": null,
  //     "data": "523398"
  // }
  // --------------------------------------------------------
  // CSS.CAPNHAT_HD_MUA_THIETBI
  capnhat_hd_mua_thietbi: (axios, data) => axios.post('web-ccdv/ban-thiet-bi/capnhat_hd_mua_thietbi', data),
  // REQ:{
  //     "vthuebao_id":,
  //     "vkhachhang_id":,
  //     "vhdtb_id":,
  //     "vhuonggiao_id":,
  //     "vds_hdkh":,
  //     "vds_hdtb":,
  //     "vds_cttt":,
  //     "vds_ctmua_tbi":,
  //     "vds_hdtb_dv":,
  //     "vnguoi_cn":,
  //     "vmay_cn":,
  //     "vip_cn":,
  //     "vkieu":
  // }
  // RESP:
  // {
  //     "error": "200",
  //     "error_code": "BSS-00000000",
  //     "message": "Success",
  //     "message_detail": null,
  //     "request_id": null,
  //     "data": null
  // }

  // POST: web-ccdv/ban-thiet-bi/fn_lay_tthd_theo_hdtb_id
  // REQ:{
  //     "hdtb_id":1478274
  // }
  fn_lay_tthd_theo_hdtb_id: (axios, data) => axios.post('web-ccdv/ban-thiet-bi/fn_lay_tthd_theo_hdtb_id', data),

  //   CSS.LAP_HOP_DONG.XOA_HDKH_V2
  // Sử dụng API tại UR2.1.007_057

  // {{base_url}}/hopdong/xoa_hopdong_thuebao
  // {"hdkh_id":1530683,"loaihd_id":30}
  xoa_hdkh_v2: (axios, data) => axios.post('web-hopdong/khoiphucthanhly/xoa_hdkh_v2', data)
}
