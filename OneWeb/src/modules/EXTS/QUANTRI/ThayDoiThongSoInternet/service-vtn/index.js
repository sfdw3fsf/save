export default {
    async LayThongTinThueBao(context, ma_tb) {
        
        let subs = null;
        let sub = null;

        try{
            subs = await this.querySubscriber(context, ma_tb);

            for (let i = 0; i < subs.length; i++)
            {
                let item = subs[i]

                if (parseInt(item.STATUS) == 1)
                {
                    sub = item;
                    break;
                }
            }

            let matkhau = !sub.PASSWORD ? "" : sub.PASSWORD;

            if (!matkhau)
            {
                matkhau = await this.getPassword(context, ma_tb);
                
                if (!!matkhau && matkhau.startsWith("1[") && matkhau.length > 3) {
                    matkhau = matkhau.substring(2, matkhau.length - 3);
                }
            }

            sub.PASSWORD = matkhau;
        }
        catch (ex)
        {
            sub = null;
        }

        return sub;
    },
    async getPassword(context, external_id) {
        let data = {
            external_id: external_id
        }
        try {
            let response = await context.$root.context.post(`/tichhop/vtn/getPassword`, data)
            return response.data
        } catch (err) {
            return null
        }
    },
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
    async setPasswordForRoot(context, maTB, vpass_moi) {
        let data = {
            external_id: maTB,
            subscirber_pass: vpass_moi
        }

        try {
            let response = await context.$root.context.post(`/tichhop/vtn/setPasswordForRoot`, data)
            return response.data
        } catch (err) {
            return '0[Có lỗi xảy ra.]'
        }
    }
}