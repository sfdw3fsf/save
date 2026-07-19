export default{
    sp_ds_tyle_nqh: async (axios, data,header) => axios.get('/web-quantri/tylethulaoquahan/sp_ds_tyle_nqh', data,header),
    sp_ds_tyle_nqh_chukyno: async (axios, data,header) => axios.post('/web-quantri/tylethulaoquahan/sp_ds_tyle_nqh_chukyno', data,header),
    sp_ds_tyle_nqh_nhanvien: async (axios, data,header) => axios.post('/web-quantri/tylethulaoquahan/sp_ds_tyle_nqh_nhanvien', data,header),
    sp_ds_tyle_nqh_khuvuc: async (axios, data,header) => axios.post('/web-quantri/tylethulaoquahan/sp_ds_tyle_nqh_khuvuc', data,header),
    sp_tyle_nqh_capnhat: async (axios, data,header) => axios.post('/web-quantri/tylethulaoquahan/sp_tyle_nqh_capnhat', data,header),
    sp_tyle_nqh_xoa: async (axios, data,header) => axios.post('/web-quantri/tylethulaoquahan/sp_tyle_nqh_xoa', data,header),

}
    