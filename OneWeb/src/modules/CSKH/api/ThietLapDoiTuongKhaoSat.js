import axios from "axios"

export default {
    getDanhSachDichVu: (axios, no, pageNo, pageSize) => axios.get('/web-cskh/api/thiet-lap-doi-tuong-ks/dich-vu?kieu=' + no + '&pageNo= ' + pageNo + '&pageSize=' + pageSize),
    getDanhSachKieuLD: (axios, no, pageNo, pageSize) => axios.get('/web-cskh/api/thiet-lap-doi-tuong-ks/kieu-lap-dat?kieu=' + no + '&pageNo= ' + pageNo + '&pageSize=' + pageSize),
    getDanhSachTinhTrangHong: (axios, no, pageNo, pageSize) => axios.get('/web-cskh/api/thiet-lap-doi-tuong-ks/trang-thai-hong?kieu=' + no + '&pageNo= ' + pageNo + '&pageSize=' + pageSize),
    capNhapThongTinThietLap: (axios, data) => axios.post('/web-cskh/api/thiet-lap-doi-tuong-ks', data),
}