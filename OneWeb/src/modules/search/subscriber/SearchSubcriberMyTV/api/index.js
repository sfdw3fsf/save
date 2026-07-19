export default {
  getSubscriberInfo: (axios, data) => axios.post('/tichhop/vasc/getSubscriberInfo', data),
  getSubscriberInfoV2: (axios, data) => axios.post('/tichhop/vasc/getSubscriberInfoV2', data),
  getStatusBlockPayTV: (axios, data) => axios.post('/tichhop/vasc/getStatusBlockPayTV', data),
  getDsMegaMyTV: (axios, data) => axios.post('/web-tracuu/tracuu_tb_mytv/sp_mytv_tc_goi', data),
  capnhatMatKhau: (axios, data) => axios.post('/tichhop/vasc/changePassword', data),
  Get_DS_TB_FromSR: (axios, data) => axios.post('/tichhop/vasc/getSubscriberBySTB_SN', data),

  unbindKplus: (axios, data) => axios.post('/tichhop/vasc/unbindKplus', data),
  resetCCU: (axios, data) => axios.post('/tichhop/vasc/resetCCU', data),
  resetPasswordLockChildren: (axios, data) => axios.post('/tichhop/vasc/resetPasswordLockChildren', data),
  errorSolution: (axios, data) => axios.post('/tichhop/vasc/errorSolution', data),

  checkCCU: (axios, data) => axios.post('/tichhop/vasc/checkCCU', data),
  getDeviceKplus: (axios, data) => axios.post('/tichhop/vasc/getDeviceKplus', data),
  loginHistory: (axios, data) => axios.post('/tichhop/vasc/loginHistory', data),
}
