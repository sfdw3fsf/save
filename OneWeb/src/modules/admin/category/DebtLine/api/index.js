export default{
    getDonviQL: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_donvi_theo_loaidv', data),
    getKieuthu: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEUTHU'),
    getKieuTuyenthu: (axios) => axios.post('/web-quantri/tuyenthu/sp_lay_ds_kieu_tuyenthu'),
    getHTTT: (axios) => axios.get('web-hopdong/danhmuc/lay_hinhthuc_tt'),
    sp_lay_ds_tuyenthu_nhanvientc_id: (axios, data) => axios.post('/web-quantri/tuyenthu/sp_lay_ds_tuyenthu_nhanvientc_id', data),
    sp_lay_ds_tuyenthu_theo_tuyenthu_id: (axios, data) => axios.post('/web-quantri/tuyenthu/sp_lay_ds_tuyenthu_theo_tuyenthu_id', data),
    sp_lay_ds_nhanvien_tc_theo_donvi_dl: (axios, data) => axios.post('/web-quantri/tuyenthu/sp_lay_ds_nhanvien_tc_theo_donvi_dl', data),
    fn_themmoi_tuyenthu: (axios, data) => axios.post('/web-quantri/tuyenthu/fn_themmoi_tuyenthu', data),
    fn_capnhat_tuyenthu: (axios, data) => axios.post('/web-quantri/tuyenthu/fn_capnhat_tuyenthu', data),
    sp_lay_thongtin_nhanvien_dv: (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_lay_thongtin_nhanvien_dv', data),
    xoa_tuyenthu: (axios, data) => axios.post('/web-quantri/tuyenthu/xoa_tuyenthu', data),

    dongbo_diadanh_ccbs: (axios, data) => axios.get('/web-quantri/diaban/dongbo_diadanh_ccbs'),
    ts_danhmuc_delete_tuyenthus: (axios, data) => axios.post('ccbs/executor/ts_danhmuc_delete_tuyenthus', data),
    ts_danhmuc_update_tuyenthus: (axios, data) => axios.post('ccbs/executor/ts_danhmuc_update_tuyenthus', data),
    ts_danhmuc_insert_tuyenthus: (axios, data) => axios.post('ccbs/executor/ts_danhmuc_insert_tuyenthus', data),
}
