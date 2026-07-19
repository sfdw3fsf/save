export default {
  insert_json_import_tbi_idc: async (axios, data) => axios.post('/web-bancheo/tracuu/insert_json_import_tbi_idc', data),
  lay_ds_tbi_idc: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_ds_tbi_idc', data),
  delete_hd_thuebao_ct: async (axios, data) => axios.post('/web-bancheo/tracuu/delete_hd_thuebao_ct', data),
}