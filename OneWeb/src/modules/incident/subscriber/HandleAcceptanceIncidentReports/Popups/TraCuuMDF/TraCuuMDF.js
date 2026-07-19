export default {
    ur_41003_06_001_ht_donvicon_loaidv_combobox: (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/ht_donvicon_loaidv_combobox', data),
    ur_41003_06_002_lay_thongtin_mdf_pd: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_db_theo_matb_one', data),
    ur_41003_06_003_lay_db_theo_matb_one: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_thongtin_mdf_pd', data),
    ur_41003_06_005_lay_tt_cap_theo_matb: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_tt_cap_theo_matb', data),
    ur_41003_06_005_lay_giando_theodaunoi: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_giando_theodaunoi', data),
    ur_41003_06_005_lay_tt_tongdai_theo_cap: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_tt_tongdai_theo_cap', data),

}