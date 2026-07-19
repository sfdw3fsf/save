export default{
    sp_lay_ds_thamso_md:  (axios, data) => axios.post('/web-hopdong/thanhly/sp_lay_ds_thamso_md', data),
    sp_frmgiaophieuhd_lay_ds_donvinhan:  (axios) => axios.post('/web-thicong/gp_tiepnhan_lapdat/sp_frmgiaophieuhd_lay_ds_donvinhan'),
    sp_frmgiaophieuhd_phieu_chuagiao:  (axios, data) => axios.post('/web-thicong/gp_tiepnhan_lapdat/sp_frmgiaophieuhd_phieu_chuagiao', data),
    sp_frmgiaophieuhd_phieu_dagiao:  (axios, data) => axios.post('/web-thicong/gp_tiepnhan_lapdat/sp_frmgiaophieuhd_phieu_dagiao', data),
    fn_frmgiaophieuhd_giaophieu:  (axios, data) => axios.post('/web-thicong/gp_tiepnhan_lapdat/fn_frmgiaophieuhd_giaophieu', data),
    sp_frmgiaophieuhd_lay_donvi:  (axios, data) => axios.post('/web-thicong/gp_tiepnhan_lapdat/sp_frmgiaophieuhd_lay_donvi', data),
    fn_frmgiaophieuhd_huyphieu:  (axios, data) => axios.post('/web-thicong/gp_tiepnhan_lapdat/fn_frmgiaophieuhd_huyphieu', data),
    
    
    
    
}