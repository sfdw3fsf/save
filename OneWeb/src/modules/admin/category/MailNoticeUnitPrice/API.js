export default{
    sp_ds_dongia_tbn: async (axios, data,header) => axios.get('/web-quantri/dongiathutb/sp_ds_dongia_tbn', data,header),
    sp_ds_dongia_tbn_chukyno: async (axios, data,header) => axios.post('/web-quantri/dongiathutb/sp_ds_dongia_tbn_chukyno', data,header),
    sp_ds_dongia_tbn_nhanvien: async (axios, data,header) => axios.post('/web-quantri/dongiathutb/sp_ds_dongia_tbn_nhanvien', data,header),
    sp_ds_dongia_tbn_khuvuc: async (axios, data,header) => axios.post('/web-quantri/dongiathutb/sp_ds_dongia_tbn_khuvuc', data,header),
    sp_ds_dongia_tbn_capnhat: async (axios, data,header) => axios.post('/web-quantri/dongiathutb/sp_ds_dongia_tbn_capnhat', data,header),
    sp_ds_dongia_tbn_xoa: async (axios, data,header) => axios.post('/web-quantri/dongiathutb/sp_ds_dongia_tbn_xoa', data,header),

}
    