var axios
export default{
    khoitao: (a => {axios = a}),
    LAY_DS_ISDN_THEO_THUEBAO_ID: ((thuebao_id ) => {
        console.log("Bat dau check LAY_DS_ISDN_THEO_THUEBAO_ID")
        return new Promise(async (resolve, reject) => {
            try {
                
                let rs = await axios.post("/web-hopdong//thanhly/lay_ds_isdn_theo_thuebao_id", {
                thuebao_id: thuebao_id })
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_ISDN_THEO_THUEBAO_ID loi:" + e)
                reject("")
            }
        })
    }),
    LAY_DS_DBTB_THEO_TBID: ((thuebao_id ) => {
        console.log("Bat dau LAY_DS_DBTB_THEO_TBID")
        return new Promise(async (resolve, reject) => {
            try {
                
                let rs = await axios.post("web-hopdong/phattrienthuebao/lay_ds_dbtb_theo_tbid", {
                thuebao_id: thuebao_id })
                resolve(rs.data.data)
            } catch (e) {
                console.log("LAY_DS_DBTB_THEO_TBID loi:" + e)
                reject("")
            }
        })
    }),
}
