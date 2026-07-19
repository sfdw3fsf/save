export default {
    ur_41003_003_001_sp_lay_ds_thamso_md: (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_lay_ds_thamso_md', data),
    ur_41003_003_002_sp_ht_loai_phieutra_cbox: async (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/sp_ht_loai_phieutra_cbox/${data.kieu}`, data, header),
    ur_41003_003_003_lay_ds_phieu_yc_bh_test: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_ds_phieu_yc_bh_test', data),

    ur_41003_003_005_fn_lay_tinhtrang_theo_bhid: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_lay_tinhtrang_theo_bhid', data),
    
    ur_41003_003_007_fn_kt_trangthai_khoamo_tp: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_kt_trangthai_khoamo_tp', data),
    ur_41003_003_007_01_traphieu_baoduong_ttdh: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/traphieu_baoduong_ttdh', data),
    ur_41003_003_007_02_fn_kt_vattu_baohong: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_kt_vattu_baohong', data),
    ur_41003_003_007_03_fn_capnhat_traphieu_baohong: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_capnhat_traphieu_baohong', data),
    ur_41003_003_007_04_fn_kiemtra_traphieu: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_kiemtra_traphieu', data),

    sp_tt_giaophieu: (axios, data) => axios.post('/web-hopdong/laydulieu/sp_tt_giaophieu', data),

    traphieu_giaophieu_ph: (axios, data) => axios.post('/web-bancheo/tracuu/traphieu_giaophieu_ph', data)
    
}