export default{
    loai_file: async (axios, data,header) => axios.get('/web-quantri/danhmuc/hoso/loai_file', data,header),
    
    
    sp_ht_dichvuvt_combobox: async (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_ht_dichvuvt_combobox', data),
    sp_lay_ds_nhanvien_theo_donvi_id: async (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_nhanvien_theo_donvi_id', data),
    sp_lay_ds_quytrinh_v2: async (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_quytrinh_v2', data),
    
    sp_lay_huonggiao_theo_quytrinh: (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_lay_huonggiao_theo_quytrinh', data),
    sp_lay_ds_theotac: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_theotac', data),
    sp_lay_ds_donvi_theo_loaidv: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_donvi_theo_loaidv', data),
    lay_nhom_kqxl_theo_huonggiao: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/lay_nhom_kqxl_theo_huonggiao', data),
    lay_ds_nguyennhan_nocuoc: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/lay_ds_nguyennhan_nocuoc', data),


    sp_lay_ds_thuebao_thanhly:async (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_thuebao_thanhly', data),
    sp_lay_ds_lydohuy_hdtb:async (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_lydohuy_hdtb', data),
    lay_ds_nhanvien_theo_phieu_id:async (axios, data) => axios.post('/web-thicong/thanhly-thuebao/lay_ds_nhanvien_theo_phieu_id', data),
    sp_lay_thietbi_sudung:async (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_thietbi_sudung', data),

    sp_capnhat_thanhly:async (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_capnhat_thanhly', data),
}
    