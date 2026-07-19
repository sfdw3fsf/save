export default {
    ur_41005_001_nap_tvw_donvi: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/nap_tvw_donvi', data),
    ur_41003_5_001_lay_ds_thamso_md_mats: (axios, data) => axios.post('/web-ccdv/khaibaotsl/lay_ds_thamso_md_mats', data),
    sp_lay_ds_cauhoi_tcks: (axios, data, header) => axios.get('/web-ccdv/tien_trinh_bao_hong/sp_lay_ds_cauhoi_tcks', data, header),

}