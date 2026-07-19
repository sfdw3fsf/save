export default {
  // UR2.5.010_001
  SP_KT_KH_BOITHUONG: async (axios) => axios.get('/web-ccdv/khaibaotsl/sp_kt_kh_boithuong'),
  // UR2.5.010_002
  CSS_KIEU_TBI: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_TBI'),
  // UR2.5.010_003
  SP_LAY_DS_MUCDICH_LYDO: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_lay_ds_mucdich_lydo', request),
  // UR2.5.010_004
  CSS_TRANGBI: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TRANGBI'),
  // UR2.5.010_005
  // SP_LAY_DS_MUCDICH_LYDO: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_lay_ds_mucdich_lydo', data),
  // UR2.5.010_006
  SP_LAY_DS_LOAIHD_MD: async (axios) => axios.get('/web-ccdv/khaibaotsl/sp_lay_ds_loaihd_md'),
  // UR2.5.010_008
  QLVT_DVI_TINH: async (axios) => axios.get('/web-quantri/danhmuc-chung/QLVT_DVI_TINH'),
  // UR2.5.010_009
  LAY_DS_NV_GIAO_NHAN: async (axios) => axios.get('/web-ccdv/khaibaotsl/lay_ds_nv_giao_nhan'),
  // UR2.5.010_010
  SP_GETDANHMUCVT_DVT_ALL: async (axios) => axios.get('/web-ccdv/khaibaotsl/sp_getdanhmucvt_dvt_all'),
  // UR2.5.010_011
  SP_LAY_TK_DOKIEM: async (axios) => axios.get('/web-ccdv/khaibaotsl/sp_lay_tk_dokiem'),
  // UR2.5.010_012_1
  SP_LAY_DS_VATTU_GDV: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_lay_ds_vattu_gdv', request),
  // UR2.5.010_012_2
  SP_LAY_DS_PHIEUVT_HDBH: async (axios, request) => axios.post('/web-ccdv/xuly_nghiemthubaohong/sp_lay_ds_phieuvt_hdbh', request),
  // UR2.5.010_013
  LAY_DS_VATTU_SD: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/lay_ds_vattu_sd', request),
  // UR2.5.010_014
  SP_LAY_DS_THAMSO_MD: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_lay_ds_thamso_md', request),
  // UR2.5.010_017
  LAY_DS_TUVT_LHD_V2: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/lay_ds_tuvt_lhd_v2', request),
  // UR2.5.010_018
  SP_GET_DSHINHANH: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_get_dshinhanh', request),
  // UR2.5.010_019
  LAY_DS_NHANVIEN_KV_THEO_HDTB_ID: async (axios, request) => axios.post('/web-ccdv/hieuchinhhopdong/lay_ds_nhanvien_kv_theo_hdtb_id', request),
  // UR2.5.010_020
  LAY_DS_KHO_THEO_NV: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/lay_ds_kho_theo_nv', request),
  // UR2.5.010_022
  LAY_TT_VATTU_THEO_SERIAL: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/lay_tt_vattu_theo_serial', request),
  // UR2.5.010_023
  SP_GETDS_TINHTRANG: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_getds_tinhtrang', request),
  // UR2.5.010_027_1
  UPDATE_PVT_KT: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/update_pvt_kt', request),
  // UR2.5.010_027_2
  SP_KIEMTRATB_SDVT: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_kiemtratb_sdvt', request),
  // UR2.5.010_027_3
  SP_LAY_DS_THIETBI_DACAP_SERIAL: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_lay_ds_thietbi_dacap_serial', request),
  // UR2.5.010_027_4
  FN_KIEMTRA_VT_THUHOI: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/fn_kiemtra_vt_thuhoi', request),
  // UR2.5.010_027_5
  FN_UPDATE_SERIALTH_V2: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/fn_update_serialth_v2', request),
  // UR2.5.010_027_6
  SP_GETTHIETBI: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_getthietbi', request),
  // UR2.5.010_027_7
  SP_GETSL_DD: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_getsl_dd', request),
  // UR2.5.010_027_8
  CAPNHAT_PHIEUVT_THUHOI: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/capnhat_phieuvt_thuhoi', request),
  // UR2.5.010_027_9
  SP_KIEMTRA_TAMTHU_THEO_SERIAL: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_kiemtra_tamthu_theo_serial', request),
  // UR2.5.010_027_9
  SP_INSERT_TAMTHU_VT: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_insert_tamthu_vt', request),
  // UR2.5.010_028
  SP_XOA_PHIEU_VT_CAP_THUHOI: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_xoa_phieu_vt_cap_thuhoi', request),
  // UR2.5.010_029_1
  DIEUKIEN_CAP_VT: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/dieukien_cap_vt', request),
  // UR2.5.010_029_2
  FN_KIEMTRA_DM_VT: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/fn_kiemtra_dm_vt', request),
  // UR2.5.010_029_3
  SP_KIEMTRA_DAY: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_kiemtra_day', request),
  // UR2.5.010_029_4
  FN_KIEMTRA_VTCAPRA_V2: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/fn_kiemtra_vtcapra_v2', request),
  // UR2.5.010_029_5
  // KIEMTRA_VTCAPRA: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/kiemtra_vtcapra', request),
  // UR2.5.010_029_6
  // INSERT_PHIEU_VATTU_V2: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/insert_phieu_vattu_v2', request),
  // UR2.5.010_029_7
  CAPNHAT_SUDUNG_VATTU: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/capnhat_sudung_vattu', request),
  // UR2.5.010_036
  KH_BOITHUONG_TB: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/kh_boithuong_tb', request),
  SP_LAY_DS_TINHTRANG_TBI: async (axios) => axios.post('/web-tracuu/tracuubaohong/sp_lay_ds_tinhtrang_tbi', {}),
  // UR2.5.010_035_1
  LAY_THAMSO_MACDINH: async (axios, request) => axios.post('web-ccdv/khaibaotsl/lay_thamso_macdinh', request),
  // UR2.5.010_035_2
  LAY_PORT_THEO_HDTB: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/lay_port_theo_hdtb', request),
  // UR2.5.010_035_3
  SP_LAY_DS_TAMUNG_THEO_SERIAL: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/sp_lay_ds_tamung_theo_serial', request),
  // UR2.5.010_035_4
  KIEMTRA_VTCAPRA: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/kiemtra_vtcapra', request),
  // UR2.5.010_035_5
  INSERT_PHIEU_VATTU_V2: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/insert_phieu_vattu_v2', request),
  // GET KEYS
  POST_GET_KEYS: async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
  // TEST_PORT
  TEST_PORT: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/test_port',request),
  // GETPORT_QUANLITYV2
  GETPORT_QUANLITYV2: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/getPort_QuanlityV2', request),
  upload_file: async (axios, request, header) => axios.post('/web-quantri/upload', request, header),
  update_delete_img: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/update_delete_img', request),
  link_view_file: async (axios, listFiles) => axios.post('/web-quantri/upload/link_view_file', {
    list_file: listFiles
  }),
  thongtin_nguoidung: async (axios) => axios.post('/quantri/user/thongtin_nguoidung2', {}),
  delete_file: (axios, listFiles) => axios.post('/web-quantri/upload/xoa_file', {
    list_file: listFiles
  })
}
