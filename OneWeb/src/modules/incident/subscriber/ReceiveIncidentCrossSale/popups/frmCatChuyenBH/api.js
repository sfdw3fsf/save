export default {
  sp_ds_catchuyen_bh: (axios, data) => axios.post('web-ccdv/tien_trinh_bao_hong/sp_ds_catchuyen_bh', data),
  lay_ds_catchuyen_baohong: (axios, data) => axios.post('web-ccdv/tien_trinh_bao_hong/lay_ds_catchuyen_baohong', data),
  sp_insert_catchuyen_bh: (axios, data) => axios.post('web-ccdv/tien_trinh_bao_hong/sp_insert_catchuyen_bh', data),
  sp_update_catchuyen_bh: (axios, data) => axios.post('web-ccdv/tien_trinh_bao_hong/sp_update_catchuyen_bh', data),
  sp_delete_catchuyen_bh: (axios, data) => axios.post('web-ccdv/tien_trinh_bao_hong/sp_delete_catchuyen_bh', data),
  POST_GET_KEYS: async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
}
