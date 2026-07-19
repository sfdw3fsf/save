export default {
  updateCus_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/updateCus_VTT', data),
  ConfirmPaymentFkey_VTT: (axios, data) => axios.post('tichhop/vnptinvoice/confirmPaymentFkey_VTT', data),
  ImportAndPublishInv_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/importAndPublishInv_VTT', data)
}
