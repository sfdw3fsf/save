export default{
    tra_cuu_fkey: async (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCusFkey', data),
    tracuu_ma_gd: async (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCus', data),
    tracuu_token: async (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvNoPay', data),
    getInvViewNoPay: (axios, data) => axios.post('/tichhop/vnptinvoice/getInvViewFkeyNoPay', data),
    postDownloadInvPDFFkeyNoPay: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvPDFFkeyNoPay', data),
    postDownloadInvFkeyNoPayXml: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvFkeyNoPay', data),
    GET_BI_PARAMS: async (axios, data) => axios.get('/web-report/report/bi/parameters?report=BIEUMAU/HOPDONGPHULUC/RP_BC_BSS_42800', data),
    RUN: async (axios, data) => axios.post('/web-report/report/bi/run', data),
}