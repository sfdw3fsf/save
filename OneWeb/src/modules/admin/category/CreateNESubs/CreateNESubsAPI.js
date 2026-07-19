export default{
    ds_don_vi: async (axios, data) => axios.post('/web-quantri/tainguyen/sp_lay_ds_donvi_theo_ds_ldv', data),
    get_key: async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
    nhap_moi: async (axios, data) => axios.post('/web-quantri/tainguyen/fn_insert_sone', data),
    xoa: async (axios, data) => axios.post('/web-quantri/tainguyen/fn_delete_sone', data),
    ghi_lai: async (axios, data) => axios.post('/web-quantri/tainguyen/fn_update_sone', data),
    tracuu: async (axios, data) => axios.post('/web-quantri/tainguyen/sp_lay_ds_daisone_theo_donvi', data),
    chap_nhan: async (axios, data) => axios.post('/web-hopdong/taosone/fn_capnhat_sone_tmp', data),
    lay_ds_tmp: async (axios, data) => axios.post('/web-hopdong/taosone/sp_lay_ds_sone_tmp', data),
    cap_nhat_ds_tmp: async (axios, data) => axios.post('/web-hopdong/taosone/fn_capnhat_sone', data),
}