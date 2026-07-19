export default {
    sp_lay_ds_madoanhthu: async (axios) => axios.post('/web-hopdong/khaibao_dm_madoanhthu_dichvu/sp_lay_ds_madoanhthu'),
    fn_capnhat_madoanhthu: async (axios,data) => axios.post('/web-hopdong/khaibao_dm_madoanhthu_dichvu/fn_capnhat_madoanhthu',data),
}