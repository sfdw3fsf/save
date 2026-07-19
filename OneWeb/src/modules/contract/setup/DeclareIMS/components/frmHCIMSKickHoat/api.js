export default {  
  sp_lay_dauso_ims_theo_tinh: async (axios, data) => axios.post(`/web-hopdong/khaibao-ims-bosung/sp_lay_dauso_ims_theo_tinh`, data),
  sps_service_ims_7ts: async (axios, data) => axios.post(`/web-hopdong/hopdong/sps_service_ims_7ts`, data),
  fn_tt_bd_thuebao: async (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_bd_thuebao`, data),
  fn_tt_loaihinh_tb: async (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb`, data),
  
}
    