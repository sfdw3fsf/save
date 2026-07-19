export default{
    getLoainhanvien: (axios, data) => axios.get('/web-quantri/danhmuc-chung/ADMIN_LOAI_NV', data),
    getDonviNguonDich: (axios, data) => axios.post('/web-danhba/gan_nvchamsoc_khdn/lay_ds_donvi_nguon_dich', data),
    getNhanvienNguonDich: (axios, data) => axios.post('/web-danhba/gan_nvchamsoc_khdn/lay_ds_donvi_nguon_dich', data)
  }
  