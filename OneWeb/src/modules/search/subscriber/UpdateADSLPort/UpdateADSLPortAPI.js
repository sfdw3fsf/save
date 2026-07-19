export default{
    get_ds_donvi: async (axios, data) => axios.post('/web-quantri/thutienphatsinh/sp_ht_donvi_combobox', data),
    get_ds_tramtc: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/ht_donvicon_loaidv_combobox', data),
    get_ds_tramtb: async (axios, data) => axios.post('/web-hopdong/bss_group1/sp_ht_donvi_loaidv_cbo_dev', data),
    get_ds_dslam: async (axios, data) => axios.post('/web-hopdong/bss_group1/ht_dslam_theo_tramtb_id', data),
    get_ds_port_vci_vpi: async (axios, data) => axios.post('/web-hopdong/bss_group1/lay_ds_port_vci_vpi', data),
    update_adsl_port: async (axios, data) => axios.post('/web-hopdong/bss_group1/sp_update_adsl_port', data),      
}