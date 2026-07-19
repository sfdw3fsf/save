export default{
    sp_lay_ds_kieu_tt: async (axios, data,header) => axios.get('/web-quantri/capnhat-kieuthuoctinh/sp_lay_ds_kieu_tt', data,header),
    fn_capnhat_kieu_tt: async (axios, data,header) => axios.post('/web-quantri/capnhat-kieuthuoctinh/fn_capnhat_kieu_tt', data,header),

}
    