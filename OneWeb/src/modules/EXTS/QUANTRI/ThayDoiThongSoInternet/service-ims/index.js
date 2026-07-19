export default {
    async DoiPass(context, msisdn, newpass, type) {
        try {
            let response = await context.$root.context.post(`/tichhop/sps/ts_ism_modify_subscriber`, {
                msisdn: msisdn,
                service_action: type == "SIP" ? "ism-sip-change-pass" : "ism-pots-change-pass",
                user_password: newpass
            })

            let result = {
                code: !!response.data && response.data[0].Status == 9 ? 0 : 1,
                message: ""
            }

            if (result.code == 1) {
                result.message = !!response.data ? response.data[0].StatusMessage : ""
            }

            return result
        } catch (err) {
            return {
                code: 1,
                message: ""
            }
        }
    }
}