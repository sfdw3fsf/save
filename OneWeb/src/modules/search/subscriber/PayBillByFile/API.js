export default{
    fn_insert_kiemtra_ma_gd_tmp: async(axios, data) => axios.post('web-ccdv/thanh-toan-hoa-don-theo-file/fn_insert_kiemtra_ma_gd_tmp', data),
    lay_ds_ma_gd_loi: async(axios, data) => axios.post('/web-ccdv/thanh-toan-hoa-don-theo-file/lay_ds_ma_gd_loi', data),
    lay_ds_ma_gd_khong_loi: async(axios, data) => axios.post('/web-ccdv/thanh-toan-hoa-don-theo-file/lay_ds_ma_gd_khong_loi', data),
    fn_kiemtra_gd_da_tt: async(axios, data) => axios.post('/web-ccdv/thanh-toan-hoa-don-theo-file/fn_kiemtra_gd_da_tt', data),
    thanhtoan_trasau_theo_file: async(axios, data) => axios.post('/web-ccdv/thanh-toan-hoa-don-theo-file/thanhtoan_trasau_theo_file', data),
}