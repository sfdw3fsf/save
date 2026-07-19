export default{
    lay_donvi_gan_nhomcno: async (axios, data,header) => axios.post('/web-hopdong/xac_nhan_giahan_datcoc_tt/lay_donvi_gan_nhomcno', data,header),
    lay_nvql_theo_donvi_id: async (axios, data,header) => axios.post('/web-hopdong/xac_nhan_giahan_datcoc_tt/lay_nvql_theo_donvi_id', data,header),
    lay_nvtc_theo_nvkd: async (axios, data,header) => axios.post('/web-hopdong/xac_nhan_giahan_datcoc_tt/lay_nvtc_theo_nvkd', data,header),
    lay_ds_datcoc_theo_thoihan: async (axios, data,header) => axios.post('/web-hopdong/xac_nhan_giahan_datcoc_tt/lay_ds_datcoc_theo_thoihan', data,header),
    send_sms: async (axios, data,header) => axios.post('/web-hopdong/xac_nhan_giahan_datcoc_tt/send_sms', data,header),

}
    