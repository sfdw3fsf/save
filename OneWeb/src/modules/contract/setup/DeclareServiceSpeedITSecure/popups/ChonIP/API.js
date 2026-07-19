export default {
    lay_ds_bras_adsl: async (axios) => axios.post('/web-ccdv/khaibaoims/sp_lay_ds_bras_adsl',{}),
    //fn_map_id: async (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),
    fn_tt_profile_id_tocdo_adsl_theo_tocdo_id: async (axios, tocdo_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl',{
        param: tocdo_id,
        type: 5
    }),
    fn_tt_bras_vdc_id_theo_bras_id: async (axios, bras_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_bras',{
        param: bras_id,
        type: 1
    }),

    fn_tt_doituong_vdc_id_theo_doituong_id: async (axios, doituong_id) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_doituong',{
        param: doituong_id,
        type: 1
    }),

    lay_ip_tinh: async (axios, brasId, serviceId) => axios.post('/tichhop/visa-report/getIps', {
        brasId:brasId,
        serviceId:serviceId
    }),
    lay_ip_tinh_new: async (axios, brasId, serviceId, groupCustomerCode) => axios.post('tichhop/visa-report/getIpsbyGroupCustomer', {
        brasId:brasId,
        serviceId:serviceId,
        groupCustomerCode: groupCustomerCode
    }),
    lay_ip_v6: async (axios, brasId, serviceId) => axios.post('/tichhop/visa-report/getIpsV6', {
        brasId:brasId,
        serviceId:serviceId
    }),
    lay_ip_v6_new: async (axios, brasId, serviceId, groupCustomerCode) => axios.post('/tichhop/visa-report/getIpsV6byGroupCustomer', {
        brasId:brasId,
        serviceId:serviceId,
        groupCustomerCode: groupCustomerCode
    }),
    kiemtra_ip_tb_ftth: async (axios, vip, vkieu) => axios.post('/web-thicong/thicong-internet/kiemtra_ip_tb_ftth', {
        vip: vip,
        vkieu: vkieu
    }),

    
}