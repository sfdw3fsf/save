var axios
export default{
    khoitao: (a => {axios = a}),
    MAP_ID: ((id_neo, in_table, in_dk) => {
        console.log("Bat dau check map_id")
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.post("/web-hopdong/thanhly/thanh_ly_map_id", 
                        { id_neo: id_neo, in_table: in_table, in_dk: in_dk })
                resolve(rs.data.data)
            } catch (e) {
                console.log("Map_id loi:" + e)
                reject("")
            }
        })
    }),

    TTCT_LoaiHinh_TB: ((loaitb_id) => {
        return new Promise(async (resolve, reject) => {
            try {
                let rs = await axios.post("web-quantri/lay-dulieu/sp_tt_loaihinh_tb", 
                        { type: 2, param: loaitb_id })
                resolve(rs.data.data)
            } catch (e) {
                console.log("Map_id loi:" + e)
                reject("")
            }
        })
    }),
}
