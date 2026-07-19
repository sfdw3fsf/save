// [UR1.5.020] - Đổi port thuê bao
export default {
  // form load
  //UR1.5.020_001
  lay_ds_thuebao_doiport: (axios, data) => axios.post(`/web-danhba/doiport/lay_ds_thuebao_doiport`, data),
  capnhat_doiport_thuebao: (axios, data) => axios.post(`/web-danhba/doiport/capnhat_doiport_thuebao`, data),
  hoanthien_doiport_thuebao2: (axios, data) => axios.post(`web-danhba/doiport/hoanthien_doiport_thuebao2`, data),
  sp_doiport_tmp_file: (axios, data) => axios.post(`web-danhba/doiport/sp_doiport_tmp_file`, data),
  lay_tt_dslam: (axios, dslam_id) => axios.get(`web-quantri/thaydoi-thongso-internet/lay_tt_dslam?dslam_id=${dslam_id}`),
  changePortMegaVNN: (axios, data) => axios.post('/tichhop/visa-app/changePortMegaVNN', data)
}
