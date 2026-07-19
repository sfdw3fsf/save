export default {
  load_danhmuc_frm_KhoSoCNTT: async (axios, data) =>  axios.get('/web-thicong/dvcntt/load_danhmuc_frm_KhoSoCNTT?tag=' + data.tag),
  lay_ds_khoso_cntt: async (axios, data) =>  axios.post("/web-bancheo/danhmuc/lay_ds_khoso_cntt",data),
  fn_ghilai_khoso_cntt: async (axios, data) =>  axios.post('/web-thicong/dvcntt/fn_ghilai_khoso_cntt', data),
}