export default {
  getFeeList: (axios, data) => axios.post('web-quantri/he_so_thu_cuoc_roc/sp_ds_heso_tcr', data),
  getInvoicePeriods: (axios, data) => axios.post('web-quantri/he_so_thu_cuoc_roc/sp_ds_heso_tcr_chukyno', data),
  getEmployeeTypes: (axios, data) => axios.post('web-quantri/he_so_thu_cuoc_roc/sp_ds_heso_tcr_nhanvien', data),
  save: (axios, data) => axios.post('web-quantri/he_so_thu_cuoc_roc/sp_heso_tcr_capnhat', data),
  deleteRecord: (axios, data) => axios.post('web-quantri/he_so_thu_cuoc_roc/sp_heso_tcr_xoa', data)
}
