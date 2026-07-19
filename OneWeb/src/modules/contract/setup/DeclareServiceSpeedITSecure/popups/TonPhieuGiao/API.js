export default {
    lay_ds_nhomton: async (axios) => axios.get('/web-quantri/danhmuc-chung/QLTN_NHOM_TON'),

    sp_cbo_nhom_ton_xac_minh_no: async (axios, tthd_id) => axios.get('/web-ccdv/tram-thicong-dv-cntt/sp_cbo_nhom_ton?tthd_id='+tthd_id),

    sp_cbo_nhom_ton_ld: async (axios, kieu_id, nhomton_id) => axios.post('/web-ccdv/tram-thicong-dv-cntt/sp_cbo_nhom_ton_ld',{
        kieu_id: kieu_id,
        nhomton_id: nhomton_id
    }),

    lay_ds_lydoton_tc: async (axios, nhomton_id, tthd_id) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_cbo_lydoton_tc',{
        nhomton_id: nhomton_id,
        tthd_id: tthd_id  
    }),
    sp_ht_grid_nguyennhan_ton: async (axios, phieu_id) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_ht_grid_nguyennhan_ton',{
        phieu_id:phieu_id
    }),
    
    // lay_lydoton_tc: async (axios) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien',{
    //     schema: "css",
    //     table_name: "lydoton_tc",
    //     list_of_cols: "*",
    //     where: "",
    //     order: ""
    // }),
    lay_lydoton_tc: async (axios) => axios.post('/web-hopdong/laydulieu/sp_tt_lydoton_tc',{
        p_param: "",
        p_type:1
    }),

    fn_an_capnhat_giaophieu_ton: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_capnhat_giaophieu_ton', data),

    fn_an_xoa_giaophieu_ton: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_xoa_giaophieu_ton', data),

    
}