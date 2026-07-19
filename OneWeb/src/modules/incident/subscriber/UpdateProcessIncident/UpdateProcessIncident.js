export default {

    ur_41005_001_nap_tvw_donvi: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/nap_tvw_donvi', data),
    ur_41005_002_sp_ht_loainhom: async (axios, data, header) => axios.get('/web-ccdv/tien_trinh_bao_hong/sp_ht_loainhom', data, header),
    ur_41005_003_CSS_DICHVU_VT: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT_V2', data, header),
    ur_41005_004_sp_ht_trangthaibh: async (axios, data, header) => axios.get('/web-ccdv/tien_trinh_bao_hong/sp_ht_trangthaibh', data, header),
    sp_tt_thamso_md: (axios, data) => axios.post('web-hopdong/laydulieu/sp_tt_thamso_md', data),

    fn_tt_baohong: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_baohong', data),
    fn_tt_db_adsl: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_adsl', data),
    fn_tt_db_tsl: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_tsl', data),
    fn_tt_db_cd: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_cd', data),
    fn_tt_tientrinhsms: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tientrinhsms', data),
    fn_tt_tientrinh_bh: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tientrinh_bh', data),
    fn_tt_baohong_bc: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_baohong_bc', data),
    fn_tt_tinh: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tinh', data),

    ur_41005_009_002_nap_grd_tientrinh: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/nap_grd_tientrinh', data),
    ur_41005_009_003_sp_lay_tinh_tc_theo_tinh_id: async (axios, data) => axios.post('/web-tracuu/tracuubaohong/sp_lay_tinh_tc_theo_tinh_id', data),

    ur_41005_012_001_lay_ds_phieu_cn_tientrinh_bh: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_ds_phieu_cn_tientrinh_bh', data),
    ur_41005_012_002_lay_ds_phieu_cn_tientrinh_bd: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_ds_phieu_cn_tientrinh_bd', data),
    ur_41005_012_003_lay_tt_hs_tb: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_tt_hs_tb', data),

    ur_41005_014_lay_tt_loai_hd_pl: async (axios, data, header) => axios.get(`/web-ccdv/tien_trinh_bao_hong/lay_tt_loai_hd_pl/${data.vnhom_id}`, data, header),
    ur_41005_015_lay_ds_tientrinh_bh_theo_matb: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_ds_tientrinh_bh_theo_matb', data),

    ur_41005_020_fn_xoa_tientrinh_bh: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_xoa_tientrinh_bh', data),

    ur_41005_024_001_sp_ds_nd_nhacmau: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_ds_nd_nhacmau', data),
    ur_41005_024_002_fn_ghilai_noidungtt: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_ghilai_noidungtt', data),
    ur_41005_025_capnhat_tientrinh_bh: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/capnhat_tientrinh_bh', data),
    ur_41005_026_capnhat_ngaygiao_td: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/capnhat_ngaygiao_td', data),
    ur_41005_026_tracuucap: "UR4.1.001 – Tiếp nhận báo hỏng_Mục 1.1.5",
    ur_41005_027_showport: "UR4.1.001 – Tiếp nhận báo hỏng_Mục 1.1.8",
    ur_41005_029_khgoi: "UR4.1.001_Tiếp nhận báo hỏng_Mục 1.1.11",
    ur_41005_030_capnhat_file_tientrinh_bh: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/capnhat_file_tientrinh_bh', data),

    ur_41005_000_getPortStatus: async (axios, data) => axios.post('/tichhop/bnm/getPortStatus', data),
     
}
  