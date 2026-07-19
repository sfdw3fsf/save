export default {
    async downloadInvNoPay_V2(context, invToken) {
        try {
            let response = await context.$root.context.post(`/tichhop/vnptinvoice/downloadInvNoPay`, { invToken })
            return !response.data ? "ERR:" + response.faultString : response.data
        } catch (err) {
            return "ERR:"
        }
    },
    async downloadInvNoPay_VTT(context, invToken) {
        try {
            let response = await context.$root.context.post(`/tichhop/vnptinvoice/downloadInvNoPay_VTT`, { invToken })
            return !response.data ? "ERR:" + response.faultString : response.data
        } catch (err) {
            return "ERR:"
        }
    },
    async listInvByCus_TT78(context, cusCode, fromDate, toDate) {
        try {
            let response = await context.$root.context.post(`/tichhop/vnptinvoice/listInvByCus`, { cusCode, fromDate, toDate })
            return !response.data ? "ERR:" + response.faultString : response.data
        } catch (err) {
            return "ERR:"
        }
    },
    async listInvByCus_V2(context, cusCode, fromDate, toDate, pattern) {
        try {
            let response = await context.$root.context.post(`/tichhop/vnptinvoice/listInvByCus_v2`, { cusCode, fromDate, toDate, pattern })
            return !response.data ? "ERR:" + response.faultString : response.data
        } catch (err) {
            return "ERR:"
        }
    },
    async listInvByCus_TT78_VTT(context, cusCode, fromDate, toDate) {
        try {
            let response = await context.$root.context.post(`/tichhop/vnptinvoice/listInvByCus_VTT`, { cusCode, fromDate, toDate })
            return !response.data ? "ERR:" + response.faultString : response.data
        } catch (err) {
            return "ERR:"
        }
    },
    async listInvByCus_V2_VTT(context, cusCode, fromDate, toDate, pattern) {
        try {
            let response = await context.$root.context.post(`/tichhop/vnptinvoice/listInvByCus_v2_VTT`, { cusCode, fromDate, toDate, pattern })
            return !response.data ? "ERR:" + response.faultString : response.data
        } catch (err) {
            return "ERR:"
        }
    },
    async adjustInv_V2(context, xmlInvData, username, password, fkey, convert, serial, pattern) {
        try {
            let response = await context.$root.context.post(`/tichhop/vnptinvoice/adjustInv_v2`, { 
                acPass: password,
                account: username,
                convert: convert,
                fkey: fkey,
                xmlInvData: xmlInvData,
                serial: serial, 
                pattern: pattern
             })
             return !response.data ? "ERR:" + response.faultString : response.data
        } catch (err) {
            return "ERR:"
        }
    },
    async AdjustInvoiceAction_TT78(context, xmlInvData, username, password, fkey, convert, serial, pattern, attachFile) {
        try {
            let response = await context.$root.context.post(`/tichhop/vnptinvoice/adjustInvoiceAction`, { 
                acPass: password,
                account: username,
                convert: convert,
                fkey: fkey,
                xmlInvData: xmlInvData,
                serial: serial, 
                pattern: pattern,
                attachFile
             })
             return !response.data ? "ERR:" + response.faultString : response.data
        } catch (err) {
            return "ERR:"
        }
    },
    async adjustInv_V2_VTT(context, xmlInvData, username, password, fkey, convert, serial, pattern) {
        try {
            let response = await context.$root.context.post(`/tichhop/vnptinvoice/adjustInv_v2_VTT`, { 
                acPass: password,
                account: username,
                convert: convert,
                fkey: fkey,
                xmlInvData: xmlInvData,
                serial: serial, 
                pattern: pattern
             })
             return !response.data ? "ERR:" + response.faultString : response.data
        } catch (err) {
            return "ERR:"
        }
    },
    async AdjustInvoiceAction_TT78_VTT(context, xmlInvData, username, password, fkey, convert, serial, pattern, attachFile) {
        try {
            let response = await context.$root.context.post(`/tichhop/vnptinvoice/adjustInvoiceAction_VTT`, { 
                acPass: password,
                account: username,
                convert: convert,
                fkey: fkey,
                xmlInvData: xmlInvData,
                serial: serial, 
                pattern: pattern,
                attachFile
             })
             return !response.data ? "ERR:" + response.faultString : response.data
        } catch (err) {
            return "ERR:"
        }
    }
}