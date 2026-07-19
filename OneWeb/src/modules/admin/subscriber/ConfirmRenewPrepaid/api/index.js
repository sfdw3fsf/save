export default{
    
    getDanhSachNVQL: (axios, data) => axios.post('/web-hopdong/xac_nhan_giahan_datcoc_tt/lay_nvql_theo_donvi_id', data),
    getDanhSachDonVi: (axios, data) => axios.post('/web-hopdong/xac_nhan_giahan_datcoc_tt/lay_donvi_gan_nhomcno', data),
    getDanhSachNVTC: (axios, data) => axios.post('/web-hopdong/xac_nhan_giahan_datcoc_tt/lay_nvtc_theo_nvkd', data),
    getDanhSach: (axios, data) => axios.post('/web-hopdong/xac_nhan_giahan_datcoc_tt/lay_ds_datcoc_theo_thoihan', data),
    sendSMS: (axios, data) => axios.post('/tichhop/sms/send', data),
  }