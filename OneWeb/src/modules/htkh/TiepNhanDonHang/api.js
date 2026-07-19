export default{
    lay_danhmuc_lapmoi_tt_chung_v7: async (axios, chuoi) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/lay_danhmuc_lapmoi_tt_chung_v7',{
        chuoi:chuoi
    }),
    lay_danhsach_kieu_ld: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD'),
    lay_danhsach_tinh: async (axios) => axios.post('/web-hopdong/bss_group1/lay_ds_tinh_thicong_combobox',{}),
    lay_danhsach_trangthai_tt: async (axios) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_trangthai_tt',{}),

    lay_tocdo_adsl_theo_loaitb: async (axios, data) => axios.post('/web-bancheo/danhmuc/load_dm_tocdo_adsl', data),
    lay_ds_goi_cv_vnp_v1: (axios, data) => axios.get('/web-bancheo/danhmuc/sp_lay_ds_goi_cv_vnp_v1?vtinh_id='+data.tinh_id+'&vloaitb_id='+data.loaitb_id),
    sp_lay_ds_donhang: async (axios, jsdata) => axios.post('/web-bancheo/htkh/sp_lay_ds_donhang',{
        tinhthicong_id:0,
        jsondata:jsdata
    }),
    tim_khachhang_theo_ma_kh: async (axios, ma_kh) => axios.get('/web-bancheo/htkh/tim_khachhang_theo_ma_kh?vtinh_id=0&vma_kh='+ma_kh),
    lay_ds_diachi_theo_hdtb: async (axios, tinh_id, hdtb_id) => axios.get('/web-bancheo/htkh/lay_ds_diachi_theo_hdtb?vtinh_id='+tinh_id+'&vhdtb_id='+hdtb_id),
    lay_ds_diachi_theo_hdkh: async (axios, tinh_id, hdkh_id) => axios.get('/web-bancheo/htkh/lay_ds_diachi_theo_hdkh?vtinh_id='+tinh_id+'&vhdkh_id='+hdkh_id),
    lay_ds_khuvuc_theo_hdtb: async (axios, tinh_id, hdtb_id) => axios.get('/web-bancheo/htkh/lay_ds_khuvuc_theo_hdtb?vtinh_id='+tinh_id+'&vhdtb_id='+hdtb_id),
    lay_ds_khuvuc_theo_thuebao_id: async (axios, tinh_id, thuebao_id) => axios.get('/web-bancheo/htkh/lay_ds_khuvuc_theo_thuebao_id?vtinh_id='+tinh_id+'&vthuebao_id='+thuebao_id),

    fn_lay_thongtin_nhanvien: async (axios, nhanvien_id) => axios.post('/web-ccdv/khaibaotsl/fn_lay_thongtin_nhanvien',{
        nhanvien_id: nhanvien_id
    }),

    lay_ds_diachi_theo_dbtbid: async (axios, thuebao_id) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid',{
        in_thuebao_id: thuebao_id
    }),
    sp_lay_ds_donvi_thicong: async (axios, thuebao_id, loaidv_id) => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_ds_donvi_thicong',{
        kieu:1,
        id:thuebao_id,
        loaidv_id:loaidv_id,
        kieudv_id:2
    }),
    xoa_hdtb: async (axios, tinh_id, hdtb_id, tt_nd) => axios.post('/web-bancheo/htkh/fn_lap_hopdong_donhang',{
        tinhthicong_id:tinh_id,
        jsondata:JSON.stringify({
            "ACTION":3, //Xóa HDTB
            "HD_THUEBAO": {"HDTB_ID":hdtb_id},
            "THONGTIN_NGUOIDUNG":tt_nd
        })
    }),
    xoa_hdkh: async (axios, tinh_id, hdkh_id,tt_nd) => axios.post('/web-bancheo/htkh/fn_lap_hopdong_donhang',{
        tinhthicong_id:tinh_id,
        jsondata:JSON.stringify({
            "ACTION":4, //Xóa HDKH
            "HD_KHACHHANG": {"HDKH_ID":hdkh_id},
            "THONGTIN_NGUOIDUNG":tt_nd
        })
    }),
    giaophieu_hdtb: async (axios, tinh_id, hdtb_id,tt_nd) => axios.post('/web-bancheo/htkh/fn_lap_hopdong_donhang',{
        tinhthicong_id:tinh_id,
        jsondata:JSON.stringify({
            "ACTION":5, //Giao phiếu về bộ phận tiếp theo
            "HD_THUEBAO": {"HDTB_ID":hdtb_id},
            "THONGTIN_NGUOIDUNG": tt_nd
        })
    }),

    fn_lap_hopdong_donhang: async (axios, data) => axios.post('/web-bancheo/htkh/fn_lap_hopdong_donhang', data),

    lay_ds_donviql: async (axios, data) => axios.post('/web-bancheo/chuyendich-bancheo-dichvu/lay_ds_donviql',data),

    sp_lay_ds_donvi_xuly: async (axios, tinh_id, options) => axios.post('/web-bancheo/htkh/sp_lay_ds_danhmuc_theo_tuychon',{
        tinhthicong_id:tinh_id,
        jsondata:JSON.stringify({
            "danhmuc":"DS_DONVI_XULY_DONHANG",
            "loaikh_id":options.loaikh_id,
            "ungdung_id":10,
            "loaitb_id":options.loaitb_id,
            "hinhthuc_dh":options.htdh_id,
            "loaihd_id":options.loaihd_id,
            "kieuld_id":options.kieuld_id
        })
    }),

    sp_lay_khuvuc_theo_diadanh: async (axios, data) => axios.post('/web-bancheo/danhmuc/sp_lay_khuvuc_theo_diadanh', data),

    lay_nhanvien_theo_id: async (axios, nhanvien_id) => axios.post('/web-hopdong/laydulieu/sp_tt_nhanvien', {
        p_param: nhanvien_id,
        p_type:3
    })
}