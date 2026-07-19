export default {
  fn_capnhat_phieuyc_tdtt_kh: async (axios, data) => axios.post(`/web-thicong/thaydoi-thongtin-kh/fn_capnhat_phieuyc_tdtt_kh`, {
    p_kieu: data.p_kieu,
    p_tdtt_hdtb_id: data.p_tdtt_hdtb_id,
    p_hdkh_id: data.p_hdkh_id,
    p_hdtb_id: data.p_hdtb_id,
    p_json_phieuyc: data.p_json_phieuyc
  }),
}