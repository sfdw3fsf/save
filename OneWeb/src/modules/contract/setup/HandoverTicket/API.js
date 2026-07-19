export default{
    lay_ds_thamso_md_mats: async (axios, data,header) => axios.post('/web-ccdv/khaibaotsl/lay_ds_thamso_md_mats', data,header),
    CSS_LOAI_HD: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD', data,header),
    CSS_DICHVU_VT: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data,header),
    list_all_quytrinh: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/list_all_quytrinh', data,header),
    sp_cbb_donvith: async (axios, data,header) => axios.post('/web-hopdong/capnhat-donhang/sp_cbb_donvith', data,header),
    sp_lay_ds_huonggiao: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/sp_lay_ds_huonggiao', data,header),
    lay_ds_huonggiao: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_huonggiao', data,header),

    fn_lay_huonggiao_quytrinh: async (axios, data,header) => axios.post('/web-hopdong/thanhly/fn_lay_huonggiao_quytrinh', data,header),
    sp_lay_ds_hdtb_chuagiao: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/sp_lay_ds_hdtb_chuagiao', data,header),
    lay_ds_hdtb_dagiao: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/lay_ds_hdtb_dagiao', data,header),
    fn_lay_loaidv_dich_theo_hg: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/fn_lay_loaidv_dich_theo_hg', data,header),
    sp_ht_tatca_vetinh_combobox_v2: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/sp_ht_tatca_vetinh_combobox_v2', data,header),
    fn_giaophieu: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/fn_giaophieu', data,header),
    fn_huyphieu: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/fn_huyphieu', data,header),
    capnhat_donvinhan: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/capnhat_donvinhan', data,header),
    list_all_quytrinh:async (axios, data) => axios.post('/web-ccdv/giaophieu/list_all_quytrinh', data),
}
    