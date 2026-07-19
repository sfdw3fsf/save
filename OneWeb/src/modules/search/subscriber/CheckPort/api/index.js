export default{
  //getListProfileDSL: (axios, params) => axios.get('/tichhop/bnm/getListProfileDSL', {params : params}),
  getListProfileDSL: (axios) => axios.get('/tichhop/bnm/getListProfileDSL'),
  lay_port_theomatb: (axios, params) => axios.post('/web-ccdv/TestPortV2/lay_port_theomatb', params),
  lay_ts_dokiem_theo_tinh: (axios) => axios.post('/web-ccdv/TestPortV2/lay_ts_dokiem_theo_tinh'),
  lay_ds_server_dokiem: (axios) => axios.post('/web-ccdv/TestPortV2/lay_ds_server_dokiem'),
  // lay_ds_thamso_md_mats: (axios, params) => axios.post('/web-ccdv/TestPortV2/lay_ds_thamso_md_mats', params),
  lay_ds_thamso_md_mats: (axios, params) => axios.post('/web-ccdv/tram-thicong-dv-cntt/lay_ds_thamso_md_mats', params),
  lay_port_theo_tbid: (axios, params) => axios.post('/web-ccdv/TestPortV2/lay_port_theo_tbid', params),
  // DSNhanVienThucHien2: (axios, data) => axios.get('/web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_DS_NHANVIEN_THEO_DONVI', { params: data })
  checkAccountOnline: (axios, params) => axios.post('/tichhop/bnm/checkAccountOnline', params),
  doKiem: (axios, params) => axios.post('/app-cskh/TestPortV2/dokiem', params),
  sp_khoamoi_may_pon_input: (axios, params) => axios.get('/web-ccdv/kichhoat-bnm/sp_khoamoi_may_pon_input', {params : params}),
  khoaMay_PON: (axios, params) => axios.post('/tichhop/bnm/khoaMay_PON', params),
  moMay_PON: (axios, params) => axios.post('/tichhop/bnm/moMay_PON', params),
 }
