export default {
    
    //web-baohong/baohong-lachuong/sp_lay_ds_donvi_theo_loaidv
    lay_ds_phieu_bh_lachuong: (axios, data) => axios.post('/web-baohong/baohong-lachuong/lay_ds_phieu_bh_lachuong', data),
    //web-baohong/baohong-lachuong/sp_lay_ds_donvi_theo_loaidv
    sp_lay_ds_donvi_theo_loaidv: (axios, data) => axios.post('/web-baohong/baohong-lachuong/sp_lay_ds_donvi_theo_loaidv', data),

    // web-baohong/baohong-lachuong/sp_load_form_phieu_bh_lachuong
    sp_load_form_phieu_bh_lachuong: (axios, data) => axios.post('/web-baohong/baohong-lachuong/sp_load_form_phieu_bh_lachuong', data),

    //web-baohong/baohong-lachuong/lay_danhsach_phieuth_bh
    lay_danhsach_phieuth_bh: (axios, data) => axios.post('/web-baohong/baohong-lachuong/lay_danhsach_phieuth_bh', data),

    //web-baohong/baohong-lachuong/fn_lay_dvcha_theo_dvcon
    fn_lay_dvcha_theo_dvcon: (axios, data) => axios.post('/web-baohong/baohong-lachuong/fn_lay_dvcha_theo_dvcon', data),

    getTTDanhBa_MaTB:async (axios, data) => axios.post('/web-hopdong/hopdong/lay_danhba_theo_matb', data),

    //web-baohong/baohong-lachuong/chuyenphieu_lachuong_bh
    chuyenphieu_lachuong_bh:async (axios, data) => axios.post('/web-baohong/baohong-lachuong/chuyenphieu_lachuong_bh', data),

    //web-baohong/baohong-lachuong/chuyenphieu_baohong_v2
    chuyenphieu_baohong_v2:async (axios, data) => axios.post('/web-baohong/baohong-lachuong/chuyenphieu_baohong_v2', data),

    getDSDichVu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    
    ht_donvicon_loaidv_combobox:async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/ht_donvicon_loaidv_combobox', data),
}
  