export default {
  load_danhmuc_frm_bangcuoc_smsbrandname: async (axios, data) =>  axios.get('/web-thicong/dvcntt/load_danhmuc_frm_bangcuoc_smsbrandname'),
  lay_danhsach_bangcuoc_brandname: async (axios, data) =>  axios.get('/web-thicong/dvcntt/lay_danhsach_bangcuoc_brandname'),
  fn_ghilai_bangcuoc_brandname: async (axios, data) =>  axios.post('/web-thicong/dvcntt/fn_ghilai_bangcuoc_brandname', data),
}



