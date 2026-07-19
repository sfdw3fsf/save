export default {
  get_hd_bancheo_by_hdtb_ban: (axios, data) => axios.get('/web-tracuu/tracuu-thuebao/get_hd_bancheo_by_hdtb_ban/' + data),
  lay_ds_file_hs_bc_v2: (axios, data) => axios.post('/web-bancheo/tracuu/lay_ds_file_hs_bc_v2', data),
  lay_ds_file_hs_bc: (axios, data) => axios.post('/web-ccdv/completion/lay_ds_file_hs_bc', data),
  cap_nhat_file_hoso: async (axios, data) => axios.post('/web-quantri/quantri/cap_nhat_file_hoso', data),
 
}
