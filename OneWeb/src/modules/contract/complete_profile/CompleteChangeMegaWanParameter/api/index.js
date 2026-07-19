export default{
    getQuyTrinh: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_quytrinh_v2', data),
    // {
    //     "dsloaihd_id": "1",
    //     "dichvuvt_id": 8,
    //     "tthd_id": 4,
    //     "kieu": 0
    //    }
    getDichVu: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    getLoaiKenh: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_KENH'),
    getTocDoPir: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH'),
    getTocDoNix: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH'),
    getTocDoIsp: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH'),
    getModem: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_MODEM'),
    getLoaiTBWan: (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc', data),
    // {"nghiepvu":"DS_THIETBI_DAUCUOI","ds_para":null}
    getTinhKN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TINH'),
    getChuQuanMEGAWAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_CHUQUAN'),
    getTrangBiMEGAWAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TRANGBI'),
    getThoiHanMEGAWAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_THOIHAN'),
    // getDanhSachHDTB: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    getDanhSachNguoiGV: (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc', data),

    getThongTin: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/lay_ds_phieu_giaoden', data),
    getDanhSachHDTB: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/lay_ds_phieu_giaoden', data)
//   "dichvuvt_id":4,
//     "tthd_id":1,
//     "nhanvien_id":6857,
//     "ma_gd":0,
//     "phieu_id":0,
//     "loaihd_id":1,
//     "caidat_dv":0,
//     "kieu_id":0,
//     "huonggiao_id":0,
//     "loai_id":0
    // getDichVu: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    // getDichVu: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),

}