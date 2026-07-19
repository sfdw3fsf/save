var axios
export default{
    khoitao: (a => {axios = a}),
    TRACUUNO_THANHTOAN: ((thanhToanId ) => {
        console.log("Bat dau check map_id")
        return new Promise(async (resolve, reject) => {
            try {
                
                let rs = await axios.get("/web-hopdong/hopdong/tracuu_no_thanhtoan", 
                        {params : { thanhToanId: thanhToanId }})
                resolve(rs.data.data)
            } catch (e) {
                console.log("Map_id loi:" + e)
                reject("")
            }
        })
    }),
}
