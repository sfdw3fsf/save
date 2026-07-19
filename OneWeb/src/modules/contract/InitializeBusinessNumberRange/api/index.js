export default {
    layThongTinNguoiDung: (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2', data),
    layLoaiThueBao: (axios) => axios.post('/ccbs/executor/ts_loaitb_kt_daiso_dn'),
    getDsLoai: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_quytrinh_v2', data),
    layMaTinh : (axios, data) => axios.post('/web-ccdv/khaibaotsl/map_id', data),
    layDSDaiSo: (axios, data) => axios.post('/ccbs/executor/ts_daisodn_dstb', data),
    khoiTaoDSDN: (axios, data) => axios.post('/web-hopdong/hopdong/fn_didong', { vinput : data, vtype: 6, vhdtb_id: data.hdtb_id }),
    // {,
    //     "loai_tb": "string",
    //     "ma_tinh": "string",
    //     "ghi_chu": "string",
    //     "so_tb": "string",
    //     "so_msin": "string",
    //     "ma_xt": "string",
    //     "userid": "string"
    // }
    kiemTraThueBaoTT: (axios, data) => axios.get('/web-hopdong/phattrienthuebao/fn_kiemtra_so_trongkho/' + data),
    themMoiVaoKhoSo: (axios, data) => axios.post('/web-hopdong/phattrienthuebao/sp_nhapkho_somay_didong', data),
    capNhatVaoKhoSo: (axios, data) => axios.post('/web-hopdong/phattrienthuebao/sp_capnhat_somay_didong_trong_kho', data),
}
