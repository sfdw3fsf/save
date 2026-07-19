export default {
  SP_LAY_DS_TINH: async (axios, data) => axios.post('/web-quantri/chien-dich-b2a/lay_ds_tinh'),
  GetListVendor: async (axios, data) => axios.get('/tichhop/bnm/getListVendor'),
  // vtype in varchar2, --C: Create; R:Read; U: Update; D: Delete
  sp_update_profile_bnm: async (axios, data) => axios.post('/web-thicong/capnhatthongtin/sp_update_profile_bnm', data),
}
