export default {

    layThongTinNguoiDung: (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2', data),
    ts_loaitb_kt_daiso_dn: (axios) => axios.post('/ccbs/executor/ts_loaitb_kt_daiso_dn'),
    layMaTinh : (axios, data) => axios.post('/web-ccdv/khaibaotsl/map_id', data),
    layDSChiTietDaiSo: (axios, data) => axios.post('/ccbs/executor/ts_layds_daiso_dn', data),
    DangKyDaiSo: (axios, data) => axios.post('/ccbs/executor/ts_dangky_daiso_dn', data),

    ts_kieu_daiso_dn: async (axios) => axios.post('/ccbs/executor/ts_kieu_daiso_dn'),
}