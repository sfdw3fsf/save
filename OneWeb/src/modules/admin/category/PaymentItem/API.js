export default {
  sp_lay_ds_khoanmuc_tt: async (axios, data) => axios.post('/web-quantri/khoanmuctt/sp_lay_ds_khoanmuc_tt', data),
  lay_ds_tylevat: async (axios, data) => axios.get('/web-ccdv/thanhtoan/lay_ds_tylevat'),
  sp_khoanmuc_tt_capnhat_v2: async (axios, data) => axios.post('/web-quantri/khoanmuctt/sp_khoanmuc_tt_capnhat_v2', data)
}
