export default{
    lay_ds_dvvt: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data),
    lay_ds_thiet_bi: async (axios, data) => axios.get('/web-quantri/tb-dau-cuoi/sp_ds_thietbi_dc', data),
    lay_ds_gan: async (axios, data) => axios.post('/web-quantri/tb-dau-cuoi/sp_tbdc_ds_loaihinh', data),
    ghi_lai: async (axios, data) => axios.post('/web-quantri/tb-dau-cuoi/sp_tbdc_update', data),
    xoa_tb: async (axios, data) => axios.post('/web-quantri/tb-dau-cuoi/sp_tbdc_delete', data),
}
    