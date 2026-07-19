export default {
    async UpdateDeviceInfo(IPTVAccount, strDeviceName, strPortID, context) {
        try {
            let response = await context.$root.context.post(`/tichhop/vasc/updateDeviceInfo`, {
                myTVAccount: IPTVAccount,
                strDeviceName: strDeviceName,
                strPortID: strPortID
            })
            return response.errorCode;
        } catch (err) {
            return 1
        }
    }
}
