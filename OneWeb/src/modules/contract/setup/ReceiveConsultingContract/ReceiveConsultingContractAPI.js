export default{
    lay_yc: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD', data),
    load_trang_thai: async (axios, data) => axios.get('/web-ccdv/tiepnhan-hdtuvan/sp_frmtiepnhankyhd_load_trangthai', data),
    ly_do_tra: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LYDOTON_TC', data),
    loai_giay_to: async (axios, data) => axios.post('/web-ccdv/tiepnhan-hdtuvan/sp_frmtiepnhankyhd_load_loaigt', data),
    thao_tac_phieu: async (axios, data) => axios.post('/web-ccdv/tiepnhan-hdtuvan/fn_frmtiepnhankyhd_thaotacphieu', data),
    tracuu: async (axios, data) => axios.post('/web-ccdv/tiepnhan-hdtuvan/tuvan_ds_phieu_tiepnhan', data),
}