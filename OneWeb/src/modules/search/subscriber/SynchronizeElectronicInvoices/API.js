export default{
    //BSS-38479_001
    lay_ds_thamso_md_ds_mats: async (axios, data) => axios.post('/web-quantri/vattu/lay_ds_thamso_md_ds_mats', data),
    listInvByCus: async (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCus', data),
    getInvViewFkeyNoPay: async (axios, data) => axios.post('/tichhop/vnptinvoice/getInvViewFkeyNoPay', data),
    listInvByCusFkey: async (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCusFkey', data),
    confirmPaymentFkey: async (axios, data) => axios.post('/tichhop/vnptinvoice/confirmPaymentFkey', data),
    unConfirmPaymentFkey: async (axios, data) => axios.post('/tichhop/vnptinvoice/unConfirmPaymentFkey', data),
    fn_map_id: async (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),
    postDownloadInvFkeyNoPayXml: (axios, data) => axios.post('/app-com/hoadon_dientu/downloadInvFkeyNoPayXml', data),
    postDownloadInvPDFFkeyNoPay: (axios, data) => axios.post('/app-com/hoadon_dientu/downloadInvPDFFkeyNoPay', data),
    getInvViewNoPay: (axios, data) => axios.post('/app-com/hoadon_dientu/getInvViewNoPay', data)
}