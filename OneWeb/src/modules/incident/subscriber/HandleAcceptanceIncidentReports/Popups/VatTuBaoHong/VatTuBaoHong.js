export default {
    sp_tt_trangbi: (axios, data) => axios.post('/web-hopdong/laydulieu/sp_tt_trangbi', data),
    sp_tt_donvi_tbi: (axios, data) => axios.post('/web-hopdong/laydulieu/sp_tt_donvi_tbi', data),
    sp_tt_kieu_tbi: (axios, data) => axios.post('/web-hopdong/laydulieu/sp_tt_kieu_tbi', data),
    sp_tt_tinhtrang_tbi: (axios, data) => axios.post('/web-hopdong/laydulieu/sp_tt_tinhtrang_tbi', data),
    ur_41003_10_000_lay_ds_thietbi: (axios, data) => axios.get(`/web-ccdv/tien_trinh_bao_hong/lay_ds_thietbi/${data.baohong_id}`, data),
    fn_tt_loai_tbi: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_tbi', data),
    ur_41003_10_000_sp_add_months: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_add_months', data),

    ur_41003_10_002_css_lay_ds_vattu_baohong: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/css_lay_ds_vattu_baohong', data),
    ur_41003_10_003_lay_ds_vattu_dbtb: async (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/lay_ds_vattu_dbtb/${data.thuebao_id}`, data, header),

    ur_41003_10_006_sp_del_giaophieu_tbi_bh: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_del_giaophieu_tbi_bh', data),
    ur_41003_10_006_xoa_cap_vattu_bh: (axios, data) => axios.post('/web-tracuu/tracuubaohong/xoa_cap_vattu_bh', data),
    ur_41003_10_006_sp_insert_log_error: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_insert_log_error', data),

    ur_41003_10_007_get_keys: (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
    ur_41003_10_007_sp_insert_update_vattu: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_insert_update_vattu', data),
    ur_41003_10_007_sp_sua_giaophieu_tbi_upd: (axios, data) => axios.post('/web-tracuu/tracuubaohong/sp_sua_giaophieu_tbi_upd', data),
    ur_41003_10_007_sp_kiemtra_cap_vattu_bh: (axios, data) => axios.post('/web-ccdv/xuly_nghiemthubaohong/sp_kiemtra_cap_vattu_bh', data),

}