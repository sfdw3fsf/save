export default {
  // API
  sp_chonnv_sms_treeview: (axios, data) => axios.post('web-ccdv/tientrinh-khieunai/sp_chonnv_sms_treeview', data),
  lay_nv_giao_phkh_v2: (axios, data) => axios.post('web-ccdv/tientrinh-khieunai/lay_nv_giao_phkh_v2', data),
  sp_chonnv_sms_nhantin: (axios, data) => axios.post('web-ccdv/tientrinh-khieunai/sp_chonnv_sms_nhantin', data)
}
