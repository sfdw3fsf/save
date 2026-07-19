export default{
    lay_loai_hinh_tb: async (axios, data) => axios.post('/web-tracuu/thuebaoims/lay_ds_lhtb_theo_dv', data),
    // load_trang_thai: async (axios, data) => axios.get('/web-ccdv/tiepnhan-hdtuvan/sp_frmtiepnhankyhd_load_trangthai', data),
    // ly_do_tra: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LYDOTON_TC', data),
    // loai_giay_to: async (axios, data) => axios.post('/web-ccdv/tiepnhan-hdtuvan/sp_frmtiepnhankyhd_load_loaigt', data),
    lay_node: async (axios, data) => axios.post('/tichhop/sps/get_by_prn_only', data),
    tracuu: async (axios, data) => axios.post('/web-tracuu/thuebaoims/tracuu_tt_ims', data),
    ttthuebao: async (axios, data) => axios.post('/tichhop/sps/tracuu_thongtin_thuebao', data),
    getdauso: async (axios, data) => axios.post('/web-hopdong/khaibao-ims-bosung/sp_lay_dauso_ims_theo_tinh', data),
}