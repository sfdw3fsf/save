export default {
    getLoaiHinhTB: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
    getThongTinTB: (axios, data) => axios.post('web-danhba/chuanhoa_chuquan/lay_danhba_theo_matb', data),
    getMatKhauTB: (axios, data) => axios.get('web-thicong/thicong-internet/xem_matkhau?account=' + data),
    getMatKhauTBMyTV: (axios, data) => axios.post('tichhop/vasc/getSubscriberInfo', data),
    doiMatKhauTB: (axios, data) => axios.post('web-quantri/doimatkhau/fn_doimatkhau', data),
    doiMatKhauMYTV: (axios, data) => axios.post('tichhop/vasc/changePassword', data),
    doiMatKhauTBVISA: (axios, data) => axios.post('tichhop/visa-app/modifyPasswordVNN', data),
    capNhatMatKhauTB: (axios, data) => axios.post('web-danhba/adsl_ftth_mytv/sp_insert_tdtt_db_adsl', data)
}