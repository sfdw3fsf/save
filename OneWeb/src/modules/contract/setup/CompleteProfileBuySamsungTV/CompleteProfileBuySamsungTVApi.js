// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1543663746&range=A2
// https://cntt.vnpt.vn/browse/BSS-1329 Đặt mới ( Cancel)
// https://cntt.vnpt.vn/browse/BSS-1175 Hoàn thiện hồ sơ lắp đặt mới Hội Nghị Truyền Hình ( Cancel) -https://cntt.vnpt.vn/browse/BSS-5892
// https://cntt.vnpt.vn/browse/BSS-1227 [UR2.6.067] - Hoàn thiện hồ sơ mua tivi Samsung
// https://cntt.vnpt.vn/browse/BSS-1237 [UR2.8.008] - Chi tiết lắp mới
// git checkout origin/cuongvn-dev -- src\modules\contract\setup\CompleteProfileBuySamsungTV
// WinUI.WinUIHoanThienHS.frmHTHSDatMoi
export default {
  fn_lay_load_hths_datmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_lay_load_hths_datmoi', data),
  fn_hienthi_tt_hd_khachhang_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_hienthi_tt_hd_khachhang_hths_datmmoi', data),
  // UR2.6.067_011
  //lay_ds_hdtt_theo_hdkh_id_1: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/lay_ds_hdtt_theo_hdkh_id_1', data),
  // UR2.6.067_012
  fn_hienthi_tt_hd_thanhtoan_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_hienthi_tt_hd_thanhtoan_hths_datmmoi', data),
  // UR2.6.067_014
  lay_ds_hopdong_tt_theo_hdtt_id: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/lay_ds_hopdong_tt_theo_hdtt_id', data),
  lay_ds_hopdong_tb_theo_hdtb_id: async (axios, data) => axios.post('/web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id', data),
  // UR2.6.067_015
  fn_hienthi_tt_hd_thuebao_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_hienthi_tt_hd_thuebao_hths_datmmoi', data),
  // UR2.6.067_016
  fn_ht_tt_morong_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_ht_tt_morong_hths_datmmoi', data),
  // UR2.6.067_017
  lay_danhsach_thuoctinh_v3: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/lay_danhsach_thuoctinh_v3', data),
  lay_danhsach_thuoctinh_datmoi: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_danhsach_thuoctinh_datmoi', data),
  // UR2.6.067_018
  fn_ht_tt_danhba_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_ht_tt_danhba_hths_datmmoi', data),
  //UR2.6.067_023
  fn_hoanthien_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_hoanthien_hths_datmmoi', data),
  //
  lay_dshd_theo_ma_gd: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/lay_dshd_theo_ma_gd', data),
  lay_ds_hopdong_theo_ma_gd: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', data),
  post_lay_ds_hopdong_theo_ma_gd : async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_hopdong_theo_ma_gd',data),
  fn_lay_hd_theo_ma_gd_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_lay_hd_theo_ma_gd_hths_datmmoi', data),
  lay_ds_hdtt_theo_hdkh_id_1: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/lay_ds_hdtt_theo_hdkh_id_1', data),

  // UR2.6.067_021
  sp_ht_kv_cskh_theo_diaban: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/sp_ht_kv_cskh_theo_diaban', data),
  //
  ds_kieu_ld: async (axios, data) => axios.get(`/web-hopdong/danhmuc/ds_kieu_ld/${data.loaihd_id}/${data.loaitb_id}`, data),
  lay_ds_donvi_theo_loaidv: async (axios, data) => axios.post(`/web-hopdong/khoiphucthanhly/sp_lay_ds_donvi_theo_loaidv_v5`, data),
  lay_dulieu_bang_theo_dieukien: async (axios, data) => axios.post(`/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien`, data),
  sp_tt_hdtb_dv: async (axios, data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_hdtb_dv`, data),
  sp_tt_bras: async (axios, data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_bras`, data),
  sp_tt_dslam: async (axios, data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_dslam`, data),
  sp_tt_tocdo_adsl: async (axios, data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_tocdo_adsl`, data),
  sp_tt_loai_modem: async (axios, data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_loai_modem`, data),
  sp_tt_goicuoc_dd: async (axios, data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_goicuoc_dd`, data),
  sp_tt_tocdo_kenh: async (axios, data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_tocdo_kenh`, data),
  sp_tt_loai_kenh: async (axios, data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_loai_kenh`, data),
  sp_tt_trangbi: async (axios, data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_trangbi`, data),
  sp_tt_muccuoc_tb: async (axios, data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_muccuoc_tb`, data),
  lay_ds_loaihinh_tb_theo_dvvt_id: async (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/lay_ds_loaihinh_tb_theo_dvvt_id?dvvtId=${data.dvvtId}`, data),



  map_id: (axios, data) => axios.post(`/web-hopdong/thanhly/thanh_ly_map_id`, data),
  fn_tt_dslam: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_dslam`, data),
  fn_tt_tocdo_adsl: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl`, data),
  ht_donvi_loaidonvi: (axios, data) => axios.post(`/web-quantri/nhanviennganhnghe/sp_ht_donvi_loaidonvi`, data),
  post_fn_lay_thongtin_tc : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/fn_lay_thongtin_tc',data),
  ht_nvtc_theodc_v2 : async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/ht_nvtc_theodc_v2',data),
  lay_ds_diachi_theo_hdtt_id : async (axios, data) => axios.post('/web-thicong/khaibao-truyensolieu/lay_ds_diachi_theo_hdtt_id',data),
  lay_ds_diachi_theo_hdkh_id : async (axios, data) => axios.post('/web-thicong/khaibao-truyensolieu/lay_ds_diachi_theo_hdkh_id',data),
  lay_ds_diachi_theo_hdtb_id : async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_hdtb_id',data),
  capnhat_ngaykh_ngay_ht_v2 : async (axios, data) => axios.post('/web-hopdong/CapNhatTraTruoc/capnhat_ngaykh_ngay_ht_v2',data),
  vtc_subscriptionActivate: async (axios, data) => axios.post('/vtc/subscriptionActivate', data),
  ht_loaigt_combobox : async (axios, data) => axios.post('/web-hopdong/chuyen-dich-ban-cheo/ht_loaigt_combobox',data),

}
