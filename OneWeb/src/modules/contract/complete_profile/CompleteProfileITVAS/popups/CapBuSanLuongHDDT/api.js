export default {
    fn_get_status_hdtb: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_get_status_hdtb', data),
    call_dynamic_api: async (axios, data) => axios.post('/web-thicong/cnttdynamic/api', data),
    them_hd_thuebao_ct_common: async (axios, {vhdtb_id, vds_hdtb_ct}) => axios.post(`/web-thicong/hoinghi_truyenhinh/them_hd_thuebao_ct_common`, {vhdtb_id, vds_hdtb_ct}), 
    lay_thong_tin_da_dang_ky:(axios, vhdtb_id)=>axios.post(`/web-quantri/chuyendoihoadon/lay_ds_hd_thuebao_ct_common`, {vhdtb_id}),
}