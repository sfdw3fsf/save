export default {
    ht_donvi_loaidv_combobox: async (axios, donvi_id, loaidv_id) => axios.post('/web-thicong/hoinghi_truyenhinh/ht_donvi_loaidv_combobox', {
        donvi_id: donvi_id,
        loaidv_id:loaidv_id
    }),

    ht_donvicon_loaidv_combobox: async (axios, donvi_id, loaidv_id) => axios.post('/web-thicong/hoinghi_truyenhinh/ht_donvicon_loaidv_combobox', {
        donvi_id: donvi_id,
        loaidv_id: loaidv_id
    }),
    
    lay_ds_loainv: async (axios) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_loainv', {}),

    lay_ds_nhanvien_theo_phieu_id: async (axios, phieu_id, kieu_id, nhanvien_id) => axios.post('/web-ccdv/khaibaotsl/lay_ds_nhanvien_theo_phieu_id',{
        phieu_id: phieu_id,
        kieu_id: kieu_id,
        nhanvien_id:nhanvien_id
    }),

    sp_lay_ds_nhanvien_giaoviec_frmgiaoviecnv: async (axios, phieu_id, nhanvien_id, kieugiao, loainv) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_lay_ds_nhanvien_giaoviec_frmgiaoviecnv',{
        vphieu_id: phieu_id,
        vnhanvien_id: nhanvien_id,
        vkieugiao: kieugiao,
        vloainv: loainv
    }), 
    
    delete_giaophieu_nv: async (axios, phieu_id) => axios.post('/web-thicong/hoinghi_truyenhinh/delete_giaophieu_nv', {
        phieu_id: phieu_id
    }),

    capnhat_kqxl_giaoviec: async (axios, phieu_id, json_giaophieu_nv) => axios.post('web-thicong/hoinghi_truyenhinh/capnhat_kqxl_giaoviec', {
        phieu_id:phieu_id,
        json_giaophieu_nv:json_giaophieu_nv
    }),

    sendSMS: async (axios, sdt, noidung) => axios.post('/tichhop/sms/send', {
        msisdn: sdt,
        content : noidung
    }),

    lay_ds_nhiemvu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_NHIEMVU'),

    fn_an_capnhat_giaophieu_nv: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_capnhat_giaophieu_nv', data),
    
    fn_capnhat_trangthai_giaoviec_nv: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_capnhat_trangthai_giaoviec_nv', data),
    
}