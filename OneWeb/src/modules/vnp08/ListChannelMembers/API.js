export default{
    sp_lay_ds_tv_kenh : async (axios, data,header) => axios.post('/web-khdn/thanhvienkenh/sp_lay_ds_tv_kenh', data, header),
    sp_lay_ds_hang_kenh : async (axios, data,header) => axios.get('/web-quantri/vnp08/dm_hang_kenh/sp_lay_ds_hang_kenh', data, header),
    sinh_ma_tv_kenh : async (axios, data,header) => axios.get('/web-quantri/vnp08/dm_thanh_vien_kenh/sinh_ma_tv_kenh', data, header),
    sp_insert_update_tv_kenh : async (axios, data,header) => axios.post('/web-khdn/thanhvienkenh/sp_insert_update_tv_kenh', data, header),
    sp_deactive_tv_kenh : async (axios, data,header) => axios.post('/web-quantri/vnp08/dm_thanh_vien_kenh/sp_deactive_tv_kenh', data, header),
    sinh_ma_tvk : async (axios, data,header) => axios.get('/web-khdn/thanhvienkenh/sinh_ma_tvk', data, header),
}