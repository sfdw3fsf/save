export default { 
    async querySubscriber(context, external_id) {
        let data = {
            external_id: external_id
        }
        try {
            let response = await context.$root.context.post(`/tichhop/vtn/querySubscriber`, data)
            return response.data
        } catch (err) {
            return null
        }
    },
    async updateSubscriber(context, SubscriberObject, strReasonId) {
        let data = {
            strReasonId: strReasonId,
            subscriberObject: SubscriberObject
        }
        let response = await context.$root.context.post(`/tichhop/vtn/updateSubscriber`, data)

        if (response.data != "ok") {
            throw "error"
        }
    },
}