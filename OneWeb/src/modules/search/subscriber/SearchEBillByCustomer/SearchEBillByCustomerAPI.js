export default{
    tra_cuu_fkey: async (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCusFkey', data),
    tra_cuu_fkey_vtt: async (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCusFkey_VTT', data),
    tracuu_ma_gd: async (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCus', data),
    tracuu_ma_gd_vtt: async (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCus_VTT', data),
    tracuu_token: async (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvNoPay', data),
    getInvViewNoPay: (axios, data) => axios.post('/tichhop/vnptinvoice/getInvViewNoPay', data),
    getInvViewNoPay_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/getInvViewNoPay_VTT', data),
    postDownloadInvPDFFkeyNoPay: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvPDFFkeyNoPay', data),
    postDownloadInvPDFNoPay: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvPDFNoPay', data),
    postDownloadInvPDFFkeyNoPay_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvPDFNoPay_VTT', data), //downloadInvPDFNoPay_VTT
    postDownloadInvFkeyNoPayXml: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvFkeyNoPay', data),
    lay_tham_so: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_ds_thamso_md_mats', data),
    check_vtt: async (axios, data) => axios.post('/web-ccdv/tienhopdong/fn_lay_thamso_macdinh', data),
    GET_BI_PARAMS: async (axios, data) => axios.get('/web-report/report/bi/parameters?report=BIEUMAU/HOPDONGPHULUC/RP_BC_BSS_42800', data),
    RUN: async (axios, data) => axios.post('/web-report/report/bi/run', data),
}
