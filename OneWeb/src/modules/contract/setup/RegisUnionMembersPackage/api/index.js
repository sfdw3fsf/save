
export default {
    upload_file: async (axios, data, header) => axios.post('/web-quantri/upload', data,header),
    link_view_file: (axios) => axios.post('/web-quantri/upload/link_view_file'),
    layDSQuocTich: (axios) => axios.get('/web-quantri/danhmuc-form/LAY_DANHMUC_TT_DK_GOI_DOANVIEN/QUOCTICH'),
    getDSLoaiGT: (axios) => axios.get('/web-quantri/danhmuc-form/LAY_DANHMUC_TT_DK_GOI_DOANVIEN/LOAI_GT'),
    getDSTinhNeo: (axios) => axios.get('/web-quantri/danhmuc-form/LAY_DANHMUC_TT_DK_GOI_DOANVIEN/TINH_NEO'),
    getDSDoiTuongSD: (axios) => axios.get('/web-quantri/danhmuc-form/LAY_DANHMUC_TT_DK_GOI_DOANVIEN/DOITUONG_SD'),
    getDSLoaiKH: (axios) => axios.get('/web-quantri/danhmuc-form/LAY_DANHMUC_TT_DK_GOI_DOANVIEN/LOAI_KH'),
    getDSDTTB: (axios) => axios.get('/ccbs/executor/tt_8_dk_doanvien_doituong'),
    getDSDonViD: (axios) => axios.get('/ccbs/executor/tt_8_dk_doanvien_donvi'),
    getDSGiayXN: (axios) => axios.get('/ccbs/executor/tt_8_dk_doanvien_loai_giay_xn'),
    layDanhBaTheoTB: (axios, data) => axios.post('/web-hopdong/phattrienthuebao/lay_danhba_theo_matb', data),
    // {
    //     "dichvuvt_id": 2,
    //     "ma_tb": "828411169"
    // }
    layDsThueBaoLapKem: (axios, data) => axios.post('/web-quantri/thongtin-cap/sp_lay_ds_tb_cung_madoicap', data),
    // {
    //  "vthuebao_id": 122812,
    //  "vkieu": 1
    // }
    layDSHopDongTheoMGD: (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', data),
    // {
    //     "vloaihd_id": 26,
    //     "vma_gd": "HPG-TT/01134401",
    //     "vtthd_id": 6
    // }
    layDSHopDongTheoTB: (axios, data) => axios.post('/web-hopdong/phattrienthuebao/lay_ds_dbtb_theo_tbid', data),
    // {
    //     "thuebao_id": "string"
    // }
    layDSDanhBaKHTheoMaKH: (axios, data) => axios.post('/web-hopdong/phattrienthuebao/lay_ds_dbkh_theo_khid', data),
    // { "ID": 2635 }
    sinhMaGiaoDich: (axios, data) => axios.post('/web-baohong/giao_cho_hths/sp_lay_ds_thaotac', data),
    // {
    //     "quytrinh_id": 620,
    //     "huonggiao_id": 1003
    // }
    KTMaTBLapHDKhac: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/kiemtra_ma_tb_lap_hopdong_khac', data),
    // {
    //     "vma_tb": "congtycplandshp1",
    //     "vma_gd": 0,
    //     "vdichvuvt_id": 4,
    //     "vloaihd_id": 41
    // }
    xoaHDKHTheoLoaiHD: (axios, data) => axios.post('/web-hopdong/hopdong/khoa_mo_thuebao_no_cuoc/xoa_hopdong', data),
    // {
    //     "list":[
    //         {
    //             "hdkh_id":1395564,
    //             "loaihd_id":1
    //         }
    //     ]
    // }
    layDSHDTBTheohdkh_tthd: (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hdtb_theo_hdkh_tthd', data),
    guiMaXacThuc: (axios, data) => axios.post('/ccbs/oneBss/app_tb_xacthuc_otp', data),
    LAY_DS_DB_DD_THEO_TBID: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/lay_ds_db_dd_theo_tbid', data),
    upload_img_dktt: (axios, data) => axios.post('/ccbs/prepaid/ts_upload_img_tt_khcn', data),
    app_tb_image_dktt: (axios, data) => axios.post('/ccbs/oneBss/app_tb_image_dktt', data),
    dangKyCCBS: (axios, data) => axios.post('/ccbs/executor/tt_8_dk_doanvien', data),
    tt_8_dk_doanvien_lay_thongtin: (axios, data) => axios.post('/ccbs/executor/tt_8_dk_doanvien_lay_thongtin', data),
    tt_8_dk_doanvien_lichsu_sub: (axios, data) => axios.post('/ccbs/executor/tt_8_dk_doanvien_lichsu_sub', data),
    hths_dk_goicuoc_sv: (axios, data) => axios.get('/web-hopdong/hopdong/hths_dk_goicuoc_sv?hdtb_id=' + data ),
    capNhatDoanVien: (axios, data) => axios.post('/web-hopdong/hopdong/fn_dk_doanvien_capnhat', data),
    layAnhTheoId: (axios, data) => axios.post('/web-quantri/upload/link_view_file', data),
    // GetKey: (axios, data) => axios.post('/web-quantri/upload/link_view_file', data),
}