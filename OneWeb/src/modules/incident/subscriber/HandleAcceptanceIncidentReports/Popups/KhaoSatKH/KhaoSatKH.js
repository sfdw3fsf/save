export default {
    ur_41003_5_000_sp_capnhat_giaophieu_ks_tmp: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_capnhat_giaophieu_ks_tmp', data),
    ur_41003_5_000_get_keys: (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),

    ur_41003_5_001_lay_ds_thamso_md_mats: (axios, data) => axios.post('/web-ccdv/khaibaotsl/lay_ds_thamso_md_mats', data),
    ur_41003_5_002_sp_ht_lydo_ktl: (axios, data, header) => axios.get('/web-ccdv/tien_trinh_bao_hong/sp_ht_lydo_ktl/0', data, header),

    ur_41003_5_003_01_lay_tt_baohong_theo_baohong_id: (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/lay_tt_baohong_theo_baohong_id/${data.baohong_id}`, data, header),
    ur_41003_5_003_02_fn_lay_tocdo_loaihinh: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_lay_tocdo_loaihinh', data),
    ur_41003_5_003_03_solan_bh_trong_thirtyngay: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/solan_bh_trong_thirtyngay', data),
    ur_41003_5_003_04_lay_lichsu_hen_baohong: (axios, data) => axios.post('/web-tracuu/tracuubaohong/lay_lichsu_hen_baohong', data),
    ur_41003_5_003_05_langoi_chuatraloi: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/langoi_chuatraloi', data),
    ur_41003_5_003_06_sp_tb_traloi_theo_tbtl_id: (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/sp_tb_traloi_theo_tbtl_id/${data.tbtl_id}`, data, header),
    ur_41003_5_003_07_sp_tb_traloi_theo_baohong_id: (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/sp_tb_traloi_theo_baohong_id/${data.baohong_id}`, data, header),

    ur_41003_5_004_01_lay_ds_cauhoi_theoloainv: (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/lay_ds_cauhoi_theoloainv/${data.vnhanvien_id}/${data.vhinhthucks_id}`, data, header),
    ur_41003_5_004_02_sp_ct_tbtraloi: (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/sp_ct_tbtraloi/${data.tbtl_id}`, data, header),

    ur_41003_5_006_kiemtra_xacnhan_ks: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/kiemtra_xacnhan_ks', data),

    fn_tt_tb_traloi: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tb_traloi', data),
    ur_41003_5_007_01_lay_ds_cauhoi_phu: (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/lay_ds_cauhoi_phu/${data.rch_id}`, data, header),
    ur_41003_5_007_02_kiemtra_cauhoi_hailong: (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/kiemtra_cauhoi_hailong/${data.rch_id}`, data, header),
    ur_41003_5_007_03_sp_ds_tb_traloi: (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/sp_ds_tb_traloi/${data.traloi_id}`, data, header),
    ur_41003_5_007_04_sp_lay_ds_nhom_cauhoi_bh: (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/sp_lay_ds_nhom_cauhoi_bh/${data.nhom_ch}`, data, header),
    ur_41003_5_007_06_kiemtra_dieukien_khaosat: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/kiemtra_dieukien_khaosat', data),
    ur_41003_5_007_07_kiemtra_cauhoi_khl: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/kiemtra_cauhoi_khl', data),   
    ur_41003_5_008_08_sp_lay_gt_thamso_md_tents: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_lay_gt_thamso_md_tents', data),
    ur_41003_5_008_09_Kiemtra_nhanvien_sua_khaosat: (axios, data) => axios.post('/web-hopdong/tien_trinh_bao_hong/Kiemtra_nhanvien_sua_khaosat', data),
    ur_41003_5_008_10_sp_lay_tb_traloi_nd: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_lay_tb_traloi_nd', data),
    ur_41003_5_008_11_fn_quyen_sua_khaosat: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_quyen_sua_khaosat', data),
    ur_41003_5_008_12_kiemtra_phieu_quagio: (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/kiemtra_phieu_quagio/${data.giaophieu_ks_id}`, data, header),
    ur_41003_5_008_13_sp_lay_thuebao_cd: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_lay_thuebao_cd', data),

    ur_41003_5_009_05_sp_insert_tb_traloi: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_insert_tb_traloi', data),
    ur_41003_5_009_06_sp_delete_ct_tbtl: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_delete_ct_tbtl', data),
    ur_41003_5_009_07_sp_insert_ct_tbtraloi: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_insert_ct_tbtraloi', data),
    ur_41003_5_009_08_kiemtra_ct_traloi_hl_khl: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/kiemtra_ct_traloi_hl_khl', data),
    ur_41003_5_010_09_chapnhan_phieu_khaosat: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/chapnhan_phieu_khaosat', data),
    ur_41003_5_010_11_sp_insert_hen_khaosat: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_insert_hen_khaosat', data),
    ur_41003_5_010_12_sp_capnhat_lanks_baohong: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_capnhat_lanks_baohong', data),
    ur_41003_5_010_13_kiemtra_ct_traloi_khong_goilai: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/kiemtra_ct_traloi_khong_goilai', data),
    ur_41003_5_010_14_sp_capnhat_hen_ks: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_capnhat_hen_ks', data),
    ur_41003_5_010_15_capnhat_nguoinhan_phieu_cohen: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/capnhat_nguoinhan_phieu_cohen', data),
    ur_41003_5_010_16_sp_capnhat_ttks_tb_traloi: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_capnhat_ttks_tb_traloi', data),
    ur_41003_5_010_17_sp_capnhat_hen_tb_traloi: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_capnhat_hen_tb_traloi', data),
    ur_41003_5_010_18_sp_capnhat_ngay_nhanvien_ks_tmp: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_capnhat_ngay_nhanvien_ks_tmp', data),
    ur_41003_5_010_19_nhantin_khl: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/nhantin_khl', data),
    ur_41003_5_010_20_sp_tao_hd_thongtin_lh: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_tao_hd_thongtin_lh', data),
    ur_41003_5_010_21_dexuat_bsc_khl_nvkt: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/dexuat_bsc_khl_nvkt', data),
    ur_41003_5_010_22_capnhat_phieu_dieulai_xn: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/capnhat_phieu_dieulai_xn', data),
    ur_41003_5_010_23_dieu_phieu_xn: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/dieu_phieu_xn', data),
    
    ur_41003_5_011_23_capnhat_so_dt_khi_outbound: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/capnhat_so_dt_khi_outbound', data),
    ur_41003_5_011_27_sp_dieuhonglai: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_dieuhonglai', data),
    
    ur_41003_5_012_fn_kiemtradieuhonglai: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_kiemtradieuhonglai', data),
    
}