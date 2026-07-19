export default {
    ur_41003_002_001_1_lay_ds_nhanvien_theo_phieu_id: (axios, data) => axios.get(`/web-baohong/baohong/lay_ds_nhanvien_theo_phieu_id?phieu_id=${data.phieu_id}&kieu_id=${data.kieu_id}`, data),
    ur_41003_002_001_3_lay_ds_nhiemvu_hg_vsecond: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_ds_nhiemvu_hg_vsecond', data),
    ur_41003_002_001_4_sp_lay_donvi_quanly_ldv: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_lay_donvi_quanly_ldv', data),
    ur_41003_002_001_5_lay_ds_thamso_md_mats: (axios, data) => axios.post('/web-ccdv/khaibaotsl/lay_ds_thamso_md_mats', data),
    ur_41003_002_001_6_lay_ds_thamso_md_mats: (axios, data, header) => axios.get('/web-hopdong/chon-port/lay_ds_thamso_md_mats', data, header),

    ur_41003_002_004_lay_ds_nhanvien_theo_phieu_v2: (axios, data,) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_ds_nhanvien_theo_phieu_v2', data),
    ur_41003_002_005_lay_donvi_theo_manv: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_donvi_theo_manv', data),
    ur_41003_002_006_ht_donvicon_loaidv_combobox: (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/ht_donvicon_loaidv_combobox', data),

    ur_41003_002_009_1_sp_delete_giaophieu_nv: (axios, data) => axios.post('/web-ccdv/hoanthien_hoso_doiso_account/sp_delete_giaophieu_nv_bh', data),
    ur_41003_002_009_2_lay_nhanvien_thaythe_bh: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_nhanvien_thaythe_bh', data),
    fn_tt_nhanvien: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', data),
    ur_41003_002_009_4_sp_insert_giaophieu_nv: (axios, data) => axios.post('/web-ccdv/hoanthien_hoso_doiso_account/sp_insert_giaophieu_nv_bh', data),
    ur_41003_002_009_5_lay_noidung_sms_giaoviec_nd: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_noidung_sms_giaoviec_nd', data),
    ur_41003_002_009_6_sp_capnhat_status: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_capnhat_status', data),
    ur_41003_002_009_7_send_SMS: async (axios, data) => axios.post('/tichhop/sms/send', data),
    ur_41003_002_010_sp_xoa_giaophieu_nv_all: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_xoa_giaophieu_nv_all', data),
    ur_41003_002_011_sp_capnhat_status_theophieu: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_capnhat_status_theophieu', data),

    kiemtra_quyen_xoa_giaoviec: (axios, data) => axios.get('/web-baohong/baohong/kiemtra_quyen_xoa_giaoviec', data),

}
  