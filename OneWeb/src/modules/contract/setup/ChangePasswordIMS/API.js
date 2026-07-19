export default{
    sp_lay_ds_tinh : async (axios, data,header) => axios.post('/web-hopdong/change_password_ims/sp_lay_ds_tinh', data,header),
    sp_lay_ds_thuebao_ims_sync : async (axios, data,header) => axios.post('/web-hopdong/change_password_ims/sp_lay_ds_thuebao_ims_sync', data,header),
    sp_insert_json_gw240_ims_account : async (axios, data,header) => axios.post('/web-hopdong/change_password_ims/sp_insert_json_gw240_ims_account', data,header),
}