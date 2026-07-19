export default {
  findSubcriber: (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_db_theo_matb', data),
  getSubcriberInfor: (axios, data) => axios.post('/web-danhba/thongtin_mienphi_ip/lay_ds_dbtb_theo_khid_v2', data),
  getSubcriberMoreInfor: (axios, data) => axios.post('/web-danhba/thongtin_mienphi_ip/lay_ds_db_adsl_theo_tbid', data),
  submitChangeSubcriberBillingInfo: (axios, data) => axios.post('/tichhop/visa-app/changeBillingInfo', data),
  submitChangeSubcriberFee: (axios, data) => axios.post('/web-danhba/thongtin_mienphi_ip/sp_update_tt_tinhcuoc', data)
}
