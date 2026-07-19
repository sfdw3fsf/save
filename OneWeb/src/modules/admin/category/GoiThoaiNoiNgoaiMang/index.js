export default {
  load_danhmuc_frm_GoiThoaiNoiNgoaiMang: async (axios, data) =>  axios.get('/web-thicong/dvcntt/load_danhmuc_frm_GoiThoaiNoiNgoaiMang'),
  lay_ds_goicuocthoainoingoaimang: async (axios, data) =>  axios.get('/web-thicong/dvcntt/lay_ds_goicuocthoainoingoaimang'),
  fn_ghilai_goicuocthoainoingoaimang: async (axios, data) =>  axios.post('/web-thicong/dvcntt/fn_ghilai_goicuocthoainoingoaimang', data),
}