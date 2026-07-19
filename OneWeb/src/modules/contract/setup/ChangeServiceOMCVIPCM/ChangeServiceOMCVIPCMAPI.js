export default {
  // select box dich vu
  GET_DICHVU_VT: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
  // UR2.1.036_006
  LAY_DS_THAMSO_MD: async (axios) => axios.get(`/web-quantri/danhmuc/thamso_md_theo_ma`),
  // UR2.5.010_009
  LAY_DS_NV_GIAO_NHAN: async (axios) => axios.get('/web-ccdv/khaibaotsl/lay_ds_nv_giao_nhan'),
  LAY_DS_NHANVIEN_THEO_DONVI: async (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_donvi', request),
  // UR2.6.022_007
  LAY_HDTB_THAYDOI_DV_HC: async (axios, request) => axios.post('/web-ccdv/ht_hoso_thaydoidichvu/lay_hdtb_thaydoi_dv_hc', request),
  // UR2.4.083_010
  SP_LAY_DS_QUYTRINH_V2: async (axios, request) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_quytrinh_v2', request),
  // DS_HUONGGIAO
  DS_HUONGGIAO: async (axios, tthdId, quytrinhId) => axios.get(`/web-hopdong/danhmuc/ds_huonggiao/${quytrinhId}/${tthdId}`),
  // UR2.4.063_016
  SP_LAY_LUONG_THAOTAC: async (axios, request) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_luong_thaotac', request),
  // UR2.6.042_008_003
  SP_LAY_DULIEU_THAOTAC_CONTROL_THEO_THAOTAC_ID: async (axios, request) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_lay_dulieu_thaotac_control_theo_thaotac_id', request),
  // UR2.4.083_014
  LAY_NHOM_KQXL_THEO_HUONGGIAO: async (axios, request) => axios.post('/web-thicong/thanhly-thuebao/lay_nhom_kqxl_theo_huonggiao', request),
  // UR2.4.083_015
  LAY_DS_NGUYENNHAN_NOCUOC: async (axios, request) => axios.post('/web-thicong/thanhly-thuebao/lay_ds_nguyennhan_nocuoc', request),
  // UR2.4.090_013
  XOA_PHIEU: async (axios, request) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/sp_delete_giaophieu_nv`, request),
  // UR2.4.090_013
  INSERT_GIAOPHIEU_NV: async (axios, request) => axios.post('/web-ccdv/tiepnhanhopdong/insert_giaophieu_nv', request),
  // UR2.6.022_022
  CAPNHAT_KETQUAXL_PHIEU_ID: async (axios, request) => axios.post('/web-ccdv/ht_hoso_thaydoidichvu/capnhat_ketquaxl_phieu_id', request),
  // UR2.5.015
  SP_UPDATE_TT_GIAOVIEC: async (axios, request) => axios.post('/web-ccdv/khaibaoims/sp_update_tt_giaoviec', request),
  // UR2.4.038_042
  UPDATE_ND_THUCHIEN: async (axios, request) => axios.post('/web-hopdong/thaydoi_thongso_megawan/update_nd_thuchien', request),
  // UR4.1.003_024
  LAY_DS_NHANVIEN_THEO_PHIEU_ID: async (axios, request) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_ds_nhanvien_theo_phieu_id', request),
  // UR2.4.083_008
  SP_LAY_DS_LYDOHUY_HDTB: async (axios, request) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_lydohuy_hdtb', request),
  // UR2.5.063_014_06
  SP_HT_PHIEU_TRA: async (axios, request) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_ht_phieu_tra', request),
  // UR2.5.015_011_002
  FN_LAY_THONGTIN_LH_LAPDAT: async (axios, request) => axios.post('/web-ccdv/khaibaoims/fn_lay_thongtin_lh_lapdat', request),
  // UR2.6.022_017
  LAY_DS_DANGKY_DVGT_V3: async (axios, request) => axios.post('/web-ccdv/ht_hoso_thaydoidichvu/lay_ds_dangky_dvgt_v3', request),
  // LAY_THONGTIN_HOST
  LAY_THONGTIN_HOST: async (axios, hdtbId, kieu) => axios.get(`/web-ccdv/hoanthien_hoso_doiso_account/lay_thongtin_host?hdtbId=${hdtbId}&kieu=${kieu}`),
  // UR2.5.001_045
  LAY_DS_PHIEUVT_HDBH: async (axios, request) => axios.post('/web-ccdv/khaibaocodinh/lay_ds_phieuvt_hdbh', request),
  // UR2.6.022_008
  LAY_HDTB_THAYDOI_DV_HC_THEO_DV: async (axios, request) => axios.post('/web-ccdv/ht_hoso_thaydoidichvu/lay_hdtb_thaydoi_dv_hc_theo_dv', request),
  // UR2.4.083_022
  SP_HT_LOAIDV_TIMKIEM: async (axios) => axios.get('/web-hopdong/thaydoiloaihinhtb/sp_ht_loaidv_timkiem'),
  // UR2.4.076_011
  SP_LAY_NE_MOI_THEO_HDTB_ID: async (axios, request) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_ne_moi_theo_hdtb_id', request),
  // UR2.4.083_012
  SP_LAY_DS_DONVI_THEO_LOAIDV: async (axios, request) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_donvi_theo_loaidv', request),
  // Kiểm tra thông tin gói đồng bộ
  FN_MAP_ID: async (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', data),
  // LAY_HUONGGIAO_THEO_LUONG_ID
  LAY_HUONGGIAO_THEO_LUONG_ID: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id', request),
  // DELETE_LYDOHUY_HDTB
  DELETE_LYDOHUY_HDTB: async (axios, request) => axios.post('/web-hopdong/thaydoiloaihinhtb/delete_lydohuy_hdtb', request),
  // UR2.6.022_032
  HOANCONG_HTHS: async (axios, request) => axios.post('/web-ccdv/ht_hoso_thaydoidichvu/hoancong_hths', request),
  // UR2.6.042_012_007
  CAPNHAT_NGAYKH_NGAY_HT_V2: async (axios, request) => axios.post('/web-thicong/hths-thaydoi-tocdo/capnhat_ngaykh_ngay_ht_v2', request),
  // UR2.6.022_078
  HOANTHANH_HTHS: async (axios, request) => axios.post('/web-ccdv/ht_hoso_thaydoidichvu/hoanthanh_hths', request),
  // UR2.6.022_097_001
  FN_HTHSTHAYDOIDV_CHUYENDB: async (axios, request) => axios.post('/web-ccdv/ht_hoso_thaydoidichvu/hoanthien_hoso', request),
  // UR2.6.022_051
  KICHHOAT_HTHS: async (axios, request) => axios.post('/web-ccdv/ht_hoso_thaydoidichvu/kichhoat_hths', request),
  // UR2.6.022_088
  THOAITRA_XACMINH_HDTB: async (axios, request) => axios.post('/web-ccdv/ht_hoso_thaydoidichvu/thoaitra_xacminh_hdtb', request),
  fn_tt_hd_thuebao: async (axios, req) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao', req),
  lay_hdtb_thaydoi_dv_hc_chuquan: async (axios, data) => axios.post('/web-thicong/new-life/lay_hdtb_thaydoi_dv_hc_chuquan', data),
  kiemtra_hoanthien_thoaitra_ban: async (axios, hdtb_ban, kieu) => axios.post('/web-ccdv/khaibaoims/kiemtra_hoanthien_thoaitra_ban', {
    vhdtb_ban:hdtb_ban,
    vkieu:kieu
  }),
  fn_kiemtra_ngay_tdtl: async (axios, data) => axios.post('/web-thicong/capnhatthongitn/fn_kiemtra_ngay_tdtl', data),
}
