// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1543663746&range=A2

// https://cntt.vnpt.vn/browse/BSS-1329 Đặt mới ( Cancel)
// https://cntt.vnpt.vn/browse/BSS-1175 Hoàn thiện hồ sơ lắp đặt mới Hội Nghị Truyền Hình ( Cancel) -https://cntt.vnpt.vn/browse/BSS-5892
// https://cntt.vnpt.vn/browse/BSS-1227 [UR2.6.067] - Hoàn thiện hồ sơ mua tivi Samsung
// https://cntt.vnpt.vn/browse/BSS-1237 [UR2.8.008] - Chi tiết lắp mới

export default {
  fn_lay_load_hths_datmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_lay_load_hths_datmoi', data),
  fn_hienthi_tt_hd_khachhang_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_hienthi_tt_hd_khachhang_hths_datmmoi', data),
  // UR2.6.067_011
  lay_ds_hdtt_theo_hdkh_id_1: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/lay_ds_hdtt_theo_hdkh_id_1', data),
  // UR2.6.067_012
  fn_hienthi_tt_hd_thanhtoan_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_hienthi_tt_hd_thanhtoan_hths_datmmoi', data),
  // UR2.6.067_014
  lay_ds_hopdong_tt_theo_hdtt_id: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/lay_ds_hopdong_tt_theo_hdtt_id', data),
  // UR2.6.067_015
  fn_hienthi_tt_hd_thuebao_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_hienthi_tt_hd_thuebao_hths_datmmoi', data),
  // UR2.6.067_016
  fn_ht_tt_morong_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_ht_tt_morong_hths_datmmoi', data),
  // UR2.6.067_017
  lay_danhsach_thuoctinh_v3: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/lay_danhsach_thuoctinh_v3', data),
  // UR2.6.067_018
  fn_ht_tt_danhba_hths_datmmoi: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/fn_ht_tt_danhba_hths_datmmoi', data),
  // UR2.6.067_021
  sp_ht_kv_cskh_theo_diaban: async (axios, data) => axios.post('/web-ccdv/hoanthien-hs-muativi/sp_ht_kv_cskh_theo_diaban', data)
  //
}
