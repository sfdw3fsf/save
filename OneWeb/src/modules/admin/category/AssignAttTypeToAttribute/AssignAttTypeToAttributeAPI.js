export default{
    lay_danhsach_thuoctinh: async (axios) => axios.get('/web-quantri/danhmuc-chung/QLVT_THUOCTINH'),
    lay_ds_kieu_tt_da_and_chua_gan: async (axios, thuoctinh_id) => axios.post('/web-quantri/ganthuoctinhktt/sp_lay_ds_kieu_tt_da_and_chua_gan',{
        thuoctinh_id:thuoctinh_id
    }),
    sp_ins_thuoctinh_kieutt: async (axios, data)=> axios.post('/web-quantri/ganthuoctinhktt/sp_ins_thuoctinh_kieutt', data),
    sp_del_thuoctinh_kieutt: async (axios, data)=> axios.post('/web-quantri/ganthuoctinhktt/sp_del_thuoctinh_kieutt', data),
    
}