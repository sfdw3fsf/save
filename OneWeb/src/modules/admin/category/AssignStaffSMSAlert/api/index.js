export default{
    getTinnhan: (axios) => axios.post('/web-quantri/quanlytinnhan/sp_danhsach_tinnhan'),
    getNhanvien: (axios, data) => axios.post('/web-quantri/quanlytinnhan/sp_dsnhanvien_theo_tinnhan', data),
    getDonviChuagan: (axios, data) => axios.post('/web-quantri/quanlytinnhan/sp_ds_donvi_chuagan', data),
    getDonviDagan: (axios, data) => axios.post('/web-quantri/quanlytinnhan/sp_ds_donvi_dagan', data),
    ghiLai: (axios, data) => axios.post('/web-quantri/quanlytinnhan/sp_gan_nhanvien_sms', data)
  }
  