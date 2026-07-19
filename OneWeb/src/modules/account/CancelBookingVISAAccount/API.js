export default{
    fn_kiemtra_hopdong_huyvisa: async (axios, data) => axios.post('/web-hopdong/HuyDatChoAccountVisa/fn_kiemtra_hopdong_huyvisa', data),
    unReservedAccount: async (axios, data) => axios.post('/tichhop/visa-app/unReservedAccount', data),
}