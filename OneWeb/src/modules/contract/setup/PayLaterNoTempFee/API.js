export default{
  // form chính
  laydsThueBao_TraTruoc: (axios, data) => axios.post('web-tracuu/tracuu/fn_ds_thuebao_tratruoc_sap_hethan', data),
  chuyenTraSau: (axios, data) => axios.post('web-tracuu/tracuu/fn_chuyen_trasau_khong_tamthu', data),
  xoaTraSau: (axios, data) => axios.post('web-tracuu/tracuu/fn_xoa_ghtt_chuyendoi', data),
}
  