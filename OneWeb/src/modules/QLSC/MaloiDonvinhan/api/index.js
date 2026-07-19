export default{       

    sp_layds_donvi: (axios) => axios.get('/web-hopdong/maloi_donvi_nhan/sp_layds_donvi'),
    sp_layds_lam: (axios) => axios.get('/web-hopdong/maloi_donvi_nhan/sp_layds_lam'),
    sp_layds_tinhtrangsc: (axios) => axios.get('/web-hopdong/maloi_donvi_nhan/sp_layds_tinhtrangsc'),
    sp_layds_maloi_dvn: (axios) => axios.get('/web-hopdong/maloi_donvi_nhan/sp_layds_maloi_dvn'),
    
    sp_tao_maloi_dvn: (axios, data) => axios.post(`/web-hopdong/maloi_donvi_nhan/sp_tao_maloi_dvn`,data),
    //{ "p_tinhtrang_id" : 48,  ( dữ liệu từ trường tinhtrang_id của response web-hopdong/maloi_donvi_nhan/sp_layds_tinhtrangsc) "p_donvi_id" : 284686,   ( dữ liệu từ trường donvi_id của response web-hopdong/maloi_donvi_nhan/sp_layds_donvi) "p_thietbi_id" : 100000051, ( dữ liệu từ trường dslam_id của response web-hopdong/maloi_donvi_nhan/sp_layds_lam) "p_ghichu" : "test" }
    sp_capnhat_maloi_dvn: (axios, data) => axios.post(`/web-hopdong/maloi_donvi_nhan/sp_capnhat_maloi_dvn`,data),
    //{ "p_tinhtrang_id_moi" : 48, "p_donvi_id_moi" : 284686, "p_thietbi_id_moi" : 100000052, "p_tinhtrang_id_cu" : 10, "p_donvi_id_cu" : 10, "p_thietbi_id_cu" : 10, "p_ghichu" : "test " }
    sp_xoa_maloi_dvn: (axios, data) => axios.post(`/web-hopdong/maloi_donvi_nhan/sp_xoa_maloi_dvn`,data),
    //{ "p_tinhtrang_id" : 48, "p_donvi_id" : 284686, "p_thietbi_id" :100000051 }    
}