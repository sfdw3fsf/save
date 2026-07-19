export default {
    async GetPortStatus(linetestUser, linetestPassword, input, getMac, context) {
        try {
            console.log(context, "vào đây này")
            let response = await context.$root.context.post(`/tichhop/bnm/getPortStatus`, {
                input: input,
                linetestPassword: linetestPassword,
                linetestUser: linetestUser,
                mac: getMac
            })
            return response.data
        } catch (err) {
            return null
        }
    },
    async Xem_Temp(param, context) {
        try {
            let response = await context.$root.context.post(`/tichhop/bnm/xemTemp`, param)
            return response.data
        } catch (err) {
            return null
        }
    },
    async getPass(account, context) {
        try {
            let response = await context.$root.context.get(`/tichhop/bnm/getPass`, {account})
            return response.data
        } catch (err) {
            return null
        }
    }
}