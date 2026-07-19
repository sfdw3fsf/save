export default {
    //BSS-38770_001- Sự kiện form load
    cb_dichvu: (axios) => axios.get(`/web-quantri/danhmuc-chung/CSS_DICHVU_VT`),
    cb_trangthai: (axios) => axios.get(`/web-quantri/danhmuc-chung/CSS_TRANGTHAI_HD`),
    cb_loaiHD: (axios) => axios.get(`/web-quantri/danhmuc-chung/CSS_LOAI_HD`),
    cb_dkTraCuu: (axios, params) => axios.get(`/web-tracuu/danhmuc/tieuchi_tracuu`, { params }),

    //BSS-38770_002- HienThiDanhSacHDTB input: in_hdkh_id=4627
    hienthi_ds_hdtb: (axios, params) => axios.get(`/web-hopdong/capnhat-ngay-truythu/lay_ds_hopdong_tb_truythu`, { params }),

    //BSS-38770_003 - ds_kh 
    hienthi_ds_kh: (axios, params) => axios.post(`/web-hopdong/capnhat-ngay-truythu/tra_cuu_hop_dong`, params),

    // input: {
    //     "p_ds_para": "{'DK_TRACUU':'Site ID','DICHVUVT_ID':4, 'TXT_TC':'3G_TDU169M','LOAI':1,'DIEUKIEN':'','LOAIHD_ID':31,'TTHD_ID':6,'DONVI_ID':0,'NHANVIEN_ID':0,'DONVI_DL_ID':0,'TUNGAY_YC':'01/04/2019','DENNGAY_YC':'01/05/2020','KT_INHD':0}"
    // }
    hienthi_ds_kh_kethop: (axios, params) => axios.post(`/web-hopdong/capnhat-ngay-truythu/fn_tracuu_hopdong_truythu`, params),

    // BSS-38770_005
    fn_tt_hdtb_mgwan: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_mgwan`, data),
    fn_tt_hdtb_tsl: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_tsl`, data),
    fn_tt_hd_thuebao: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao`, data),

    getKeys: (axios, params) => axios.post('web-quantri/donvitinh/get_keys', params),

    // input: {
    // "p_ds_para": "{'HDTB_ID':12140194,'DICHVUVT_ID':15,'LOAITB_ID':35,'LOAIHD_ID':1,'TTHD_ID':6,'DTP_NGAYTRUYTHU':'10/01/2020 00:00:00','KIEU':2}"
    // }
    check_data: (axios, data) => axios.post(`/web-hopdong/capnhat-ngay-truythu/fn_ktra_dulieu_truythu`, data),

    // input: {
    //     "p_js_hdtb_truythu": "{'HDTB_ID':1234,'NGAY_TRUYTHU':'01/01/2020 00:00:00','MAY_CN':'admin','NGAY_CN':'01/02/2020 00:00:00','NGUOI_CN':'admin'}"
    // }
    ps_insert_hdtb_truythu: (axios, data) => axios.post(`/web-hopdong/capnhat-ngay-truythu/ps_insert_hdtb_truythu`, data),

    // input: {
    //     "p_js_hdtb_truythu": "{'HDTB_ID':1234,'NGAY_TRUYTHU':'01/01/2020 00:00:00','MAY_CN':'admin','NGAY_CN':'01/02/2020 00:00:00','NGUOI_CN':'admin'}"
    // }
    ps_update_hdtb_truythu: (axios, data) => axios.post(`/web-hopdong/capnhat-ngay-truythu/ps_update_hdtb_truythu`, data),

    // POST: web-hopdong/capnhat-ngay-truythu/sp_delete_hdtb_truythu
    // input: {"p_hdtb_id": 1234}
    ps_delete_hdtb_truythu: (axios, data) => axios.post(`/web-hopdong/capnhat-ngay-truythu/sp_delete_hdtb_truythu`, data),

    // POST: web-hopdong/capnhat-ngay-truythu/ps_insert_tdtt_db_mgwan
    // input: {
    //     "p_js_tdtt_db_mgwan": "{'THUEBAO_ID':504999,'MA_TB':'TSL002664','NHANVIEN_ID':4035,'DONVI_ID':877,'TRANGTHAI_ID':1,'NGAY_DK':'23/03/2022 09:16:01','NGAY_HT':'23/03/2022 09:16:01','GHICHU':'23/03/2022 09:16:01','NGAY_CN':'23/03/2022 09:16:01','NGUOI_CN':'daduong','MAY_CN':'DESKTOP-9TNT4FD','CUOC_TC':'','CUOC_TK':'','CUOC_TBI':'','CUOC_HT':'','CUOC_TKDT':'','CUOC_TCDT':'','TEN_TBI':'','BRAS_ID':'','DSLAM_ID':'','PORT_ID':485226,'VCI_VPI_ID':6144,'SLOT':'','PORT':'','TIMESLOT':'','LINK':'','IP_WAN':'','VRF':'','TUYENCAP':'','LIENTU':'','LOAICAP_ID':'','TUCAP_GOC':'','DOICAP_GOC':'','CAP_GOC':'','TUCAP_P':'','HOPCAP_P':'','DOICAP_P':'','CAP_P':'','CAP_ID':'','DOICAP':'','SERIAL':'','CULY':'','CUOC_TC_CU':'','CUOC_TK_CU':'','CUOC_TBI_CU':'','CUOC_HT_CU':'','CUOC_TKDT_CU':'','CUOC_TCDT_CU':'','TEN_TBI_CU':'','BRAS_ID_CU':'','DSLAM_ID_CU':'','PORT_ID_CU':339921,'VCI_VPI_ID_CU':6144,'SLOT_CU':'','PORT_CU':'','TIMESLOT_CU':'','LINK_CU':'','IP_WAN_CU':'','VRF_CU':'','TUYENCAP_CU':'','LIENTU_CU':'','LOAICAP_ID_CU':'','TUCAP_GOC_CU':'','DOICAP_GOC_CU':'','CAP_GOC_CU':'','TUCAP_P_CU':'','HOPCAP_P_CU':'','DOICAP_P_CU':'','CAP_P_CU':'','CAP_ID_CU':'','DOICAP_CU':'','SERIAL_CU':'','CULY_CU':'','DAUCUOI_ID':534620,'TBIDC_DAU_ID':'','TBIDC_DAU_ID_CU':'','TBIDC_CUOI_ID':'','TBIDC_CUOI_ID_CU':'','TDTT_DB_ID':'','HDTB_ID':0,'MA_PL':'','MA_PL_CU':'','NHAN_HD':'','KETCUOI_ID_CU':'','KETCUOI_ID':'','VITRI_CU':'','VITRI':'','KIEU':'','NGUON_YC':'','CHUQUANLD_ID':'','CHUQUAN_ID':'','CHUQUAN_ID_CU':'','CHUQUANLD_ID_CU':'','TEN_KH_CU':'','TEN_KH':'','DIACHI_ID_CU':'','DIACHI_ID':'','TOCDO_ID_CU':'','TOCDO_ID':'','MA_VTT_CU':'','MA_VTT':'','VTT_ID':'','VTT_ID_CU':'','MA_HD':'','MA_HD_CU':''}",
    //     "p_txt_mahd": "26/CV-MLC",
    //     "p_hdtb_id": 0
    // }
    ps_insert_tdtt_db_mgwan: (axios, data) => axios.post(`/web-hopdong/capnhat-ngay-truythu/ps_insert_tdtt_db_mgwan`, data),

    // POST: web-hopdong/capnhat-ngay-truythu/ps_insert_tdtt_db_tsl
    // input: {
    //     "p_js_tdtt_db_tsl": "{'THUEBAO_ID':504999,'MA_TB':'TSL002664','NHANVIEN_ID':4035,'DONVI_ID':877,'TRANGTHAI_ID':1,'NGAY_DK':'23/03/2022 09:16:01','NGAY_HT':'23/03/2022 09:16:01','GHICHU':'23/03/2022 09:16:01','NGAY_CN':'23/03/2022 09:16:01','NGUOI_CN':'daduong','MAY_CN':'DESKTOP-9TNT4FD','CUOC_TC':'','CUOC_TK':'','CUOC_TBI':'','CUOC_HT':'','CUOC_TKDT':'','CUOC_TCDT':'','TEN_TBI':'','BRAS_ID':'','DSLAM_ID':'','PORT_ID':485226,'VCI_VPI_ID':6144,'SLOT':'','PORT':'','TIMESLOT':'','LINK':'','IP_WAN':'','VRF':'','TUYENCAP':'','LIENTU':'','LOAICAP_ID':'','TUCAP_GOC':'','DOICAP_GOC':'','CAP_GOC':'','TUCAP_P':'','HOPCAP_P':'','DOICAP_P':'','CAP_P':'','CAP_ID':'','DOICAP':'','SERIAL':'','CULY':'','CUOC_TC_CU':'','CUOC_TK_CU':'','CUOC_TBI_CU':'','CUOC_HT_CU':'','CUOC_TKDT_CU':'','CUOC_TCDT_CU':'','TEN_TBI_CU':'','BRAS_ID_CU':'','DSLAM_ID_CU':'','PORT_ID_CU':339921,'VCI_VPI_ID_CU':6144,'SLOT_CU':'','PORT_CU':'','TIMESLOT_CU':'','LINK_CU':'','IP_WAN_CU':'','VRF_CU':'','TUYENCAP_CU':'','LIENTU_CU':'','LOAICAP_ID_CU':'','TUCAP_GOC_CU':'','DOICAP_GOC_CU':'','CAP_GOC_CU':'','TUCAP_P_CU':'','HOPCAP_P_CU':'','DOICAP_P_CU':'','CAP_P_CU':'','CAP_ID_CU':'','DOICAP_CU':'','SERIAL_CU':'','CULY_CU':'','DAUCUOI_ID':534620,'TBIDC_DAU_ID':'','TBIDC_DAU_ID_CU':'','TBIDC_CUOI_ID':'','TBIDC_CUOI_ID_CU':'','TDTT_DB_ID':'','HDTB_ID':0,'MA_PL':'','MA_PL_CU':'','NHAN_HD':'','KETCUOI_ID_CU':'','KETCUOI_ID':'','VITRI_CU':'','VITRI':'','KIEU':'','NGUON_YC':'','CHUQUANLD_ID':'','CHUQUAN_ID':'','CHUQUAN_ID_CU':'','CHUQUANLD_ID_CU':'','TEN_KH_CU':'','TEN_KH':'','DIACHI_ID_CU':'','DIACHI_ID':'','TOCDO_ID_CU':'','TOCDO_ID':'','MA_VTT_CU':'','MA_VTT':'','VTT_ID':'','VTT_ID_CU':'','MA_HD':'','MA_HD_CU':''}",
    //     "p_txt_mahd": "26/CV-MLC",
    //     "p_hdtb_id": 0
    // }
    ps_insert_tdtt_db_tsl: (axios, data) => axios.post(`/web-hopdong/capnhat-ngay-truythu/ps_insert_tdtt_db_tsl`, data),
    // input: {
    //     "p_dichvuvt_id": 8,
    //     "p_hdtb_id": 4164482,
    //     "p_nhan_hd": 1
    // }
    sp_update_nhanhd_megawan_tsl: (axios, data) => axios.post(`/web-hopdong/capnhat-ngay-truythu/sp_update_nhanhd_megawan_tsl`, data),
}