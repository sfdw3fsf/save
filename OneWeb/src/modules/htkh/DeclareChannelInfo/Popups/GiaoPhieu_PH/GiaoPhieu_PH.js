export default {
    sp_giaophieu_ph_loaddichvuvt: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_giaophieu_ph_loaddichvuvt', data),
    lay_ds_huonggiao_ph: (axios, data) => axios.get(`web-thicong/hoancongmegawan/lay_ds_huonggiao_ph?quytrinh_id=${data.quytrinh_id}`, data),
    fn_check_giaophieu: (axios, data) => axios.post('web-thicong/hoancongmegawan/fn_check_giaophieu', data),
    sp_lay_danhsach_phieu_ph_dagiao: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_danhsach_phieu_ph_dagiao', data),
    get_keys: (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
    sp_giaophieu_ph_thicong: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_giaophieu_ph_thicong', data),
    sp_kiemtra_donvi_giaophieu_ph: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_kiemtra_donvi_giaophieu_ph', data),
    sp_huygiao_ph_thicong: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_huygiao_ph_thicong', data),

    lay_ds_phieu_chuagiao: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/lay_ds_phieu_chuagiao', data),
    lay_ds_donvi_nhan_phieu_bh: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/lay_ds_donvi_nhan_phieu_bh', data),

}