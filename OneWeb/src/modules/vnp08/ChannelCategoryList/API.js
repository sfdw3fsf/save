export default{
    sp_lay_ds_hang_kenh : async (axios, data,header) => axios.post('/web-khdn/quanlykenh/get_list_hangkenh', data, header),
    sinh_ma_hang_kenh : async (axios, data,header) => axios.get('/web-quantri/vnp08/dm_hang_kenh/sinh_ma_hang_kenh', data, header),
    sp_insert_update_hang_kenh : async (axios, data,header) => axios.post('/web-quantri/vnp08/dm_hang_kenh/sp_insert_update_hang_kenh', data, header),
    sp_deactive_hang_kenh : async (axios, data,header) => axios.post('/web-quantri/vnp08/dm_hang_kenh/sp_deactive_hang_kenh', data, header),
    update_hangkenh : async (axios, data,header) => axios.post('/web-khdn/quanlykenh/update_hangkenh', data, header),
    insert_hangkenh : async (axios, data,header) => axios.post('/web-khdn/quanlykenh/insert_hangkenh', data, header),
    delete_hangkenh : async (axios, data,header) => axios.post('/web-khdn/quanlykenh/delete_hangkenh', data, header),
}