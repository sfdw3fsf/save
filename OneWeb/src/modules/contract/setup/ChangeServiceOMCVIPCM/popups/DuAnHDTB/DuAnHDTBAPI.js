export default {
  // UR2.5.010_048_001
  LAY_DS_DUAN: async (axios) => axios.get('/web-ccdv/khaibaotsl/lay_ds_duan'),
  // UR2.5.010_048_002
  LAY_DS_HDTB_BY_ID: async (axios, params) => axios.get(`/web-ccdv/khaibaotsl/lay_ds_hdtb_by_id`, {params: params}),
  // UR2.5.010_048_003
  SP_UPDATE_DUAN_HDTB: async (axios, request) => axios.post('/web-ccdv/khaibaotsl/update_duan_hdtb', request)
  // UR2.5.010_048_004
}
