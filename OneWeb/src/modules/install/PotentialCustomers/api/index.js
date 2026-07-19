export default{
    tuvan_ds_kh_tiemnang_nhom1:  (axios, data) => axios.post('/web-thicong/khachhang-tiemnang/tuvan_ds_kh_tiemnang_nhom1', data),
    css_nhom_ton:  (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_NHOM_TON', data),
    css_lydoton_tc:  (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LYDOTON_TC', data),
    lay_ds_nv_theo_phieu_id:  (axios, data) => axios.post('/web-thicong/khachhang-tiemnang/lay_ds_nv_theo_phieu_id', data),
    giaophieu_khtn_nhom1:  (axios, data) => axios.post('/web-thicong/khachhang-tiemnang/giaophieu_khtn_nhom1', data),
    tuvan_ds_nvtc_giaoviec:  (axios, data) => axios.post('/web-thicong/khachhang-tiemnang/tuvan_ds_nvtc_giaoviec', data),
    tuvan_khoaphieu_khtn:  (axios, data) => axios.post('/web-thicong/khachhang-tiemnang/tuvan_khoaphieu_khtn', data),
    sendsms:(axios,data) => axios.post('/tichhop/sms/send', data),  
    tuvan_xoaphieu_giaoviec_khtn:  (axios, data) => axios.post('/web-thicong/khachhang-tiemnang/tuvan_xoaphieu_giaoviec_khtn', data),
}