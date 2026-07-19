export default {
    async ChangeCustomerType(account, activeDate, cusType, context) {
        let data = {
            customerModel: {
                accountName: account,
                customerType: cusType
            },
            login: {
                userId: context.$root.context.user.getProperty('user_visa'),
                password: context.$root.context.user.getProperty('pass_visa')
            },
            note: "Thay doi loai khach hang qua WS ngay : " + activeDate
        }
        try {
            let response = await context.$root.context.post(`/tichhop/visa-app/changeCustomerType`, data)
            return {
                ok: response.errorCode == 0,
                message: !response.faultString ? "" : response.faultString
            }
        } catch (err) {
            return { ok: false, message: "" }
        }
    }
}
