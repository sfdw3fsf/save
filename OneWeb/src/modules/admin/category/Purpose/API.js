export default{
    QLVT_NHOM_MD: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/QLVT_NHOM_MD', data,header),
    sp_lay_ds_loaict: async (axios, data,header) => axios.get('/web-quantri/mucdich/sp_lay_ds_loaict', data,header),
    sp_lay_mucdich_cha: async (axios, data,header) => axios.post('/web-quantri/mucdich/sp_lay_mucdich_cha', data,header),
    sp_lay_danhsach_mucdich: async (axios, data,header) => axios.get('/web-quantri/mucdich/sp_lay_danhsach_mucdich', data,header),
    sp_lay_kieuphieu_mucdich: async (axios, data,header) => axios.post('/web-quantri/mucdich/sp_lay_kieuphieu_mucdich', data,header),
    sp_lay_mucdich_theo_id: async (axios, data,header) => axios.post('/web-quantri/mucdich/sp_lay_mucdich_theo_id', data,header),
    fn_insert_mucdich: async (axios, data,header) => axios.post('/web-quantri/mucdich/fn_insert_mucdich', data,header),
    fn_delete_mucdich: async (axios, data,header) => axios.post('/web-quantri/mucdich/fn_delete_mucdich', data,header),
    fn_update_mucdich: async (axios, data,header) => axios.post('/web-quantri/mucdich/fn_update_mucdich', data,header),

}
    