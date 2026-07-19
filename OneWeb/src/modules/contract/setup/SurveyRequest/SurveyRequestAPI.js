export default{
    //"chuoi":"DOITUONG|DICHVU_VT|LOAIHINH_TB|LOAI_GT|LOAI_KH|DANTOC|NGANHNGHE|QUOCTICH"
    lay_danhmuc_lapmoi_tt_chung_v7: async (axios, chuoi) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/lay_danhmuc_lapmoi_tt_chung_v7',{
        chuoi:chuoi
    }),

    lay_danhsach_kieu_ld: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_LD'),

    //LoaiHopDong.TIEPNHANHD lấy loaidv_id:86 phòng bán hàng
    //LoaiHopDong.TUVAN_DV lấy đơn vị id LOAI_DV.DONVIQL_TT: 2 Lấy phòng tiếp nhận cho loại hình tư vấn
    //Trường hợp còn lại LOAI_DV.TRAM_VT=5
    sp_lay_ds_donvi_ldv: async (axios, loaidv_id) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_lay_ds_donvi_ldv',{
        loaidv_id:loaidv_id
    }),
    //hdkh_id=0, TrangThaiHD.MOI mặc định
    sp_lay_ds_hdtb_tiepnhan_ks: async (axios, loaihd_id, nhanvien_id, ma_gd) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_lay_ds_hdtb_tiepnhan_ks',{
        hdkh_id:0,
        tthd_id:1,
        loaihd_id:loaihd_id,
        nhanvien_id:nhanvien_id,
        ma_gd:ma_gd
    }),

    lay_thongtin_khachhang_nhanquyen: async (axios, ma_kh) => axios.get('/web-hopdong/hopdong/lay_thongtin_khachhang_nhanquyen?maKh='+ma_kh),

    lay_ds_diachi_theo_hdtbid: async (axios, hdtbId) => axios.get('/web-thicong/thuebao/lay_ds_diachi_theo_hdtbid?hdtbId='+hdtbId),
    lay_ds_diachi_theo_hdkh: async (axios, hdKhId) => axios.get('/web-hopdong/hopdong/lay_ds_diachi_theo_hdkh?hdKhId='+hdKhId),

    fn_lay_thongtin_nhanvien: async (axios, nhanvien_id) => axios.post('/web-ccdv/khaibaotsl/fn_lay_thongtin_nhanvien',{
        nhanvien_id: nhanvien_id
    }),

    lay_ds_diachi_theo_dbtbid: async (axios, thuebao_id) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid',{
        in_thuebao_id: thuebao_id
    }),

    //Lấy tham số
    // lay_ds_thamso_md: async (axios, ma_ts) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md',{
    //     list_of_cols: "1",
    //     order: "",
    //     schema: "admin",
    //     table_name: "thamso_md",
    //     where: "ma_ts = '"+ma_ts+"' and ten_ts = '1'"
    // }),

    lay_ds_thamso_md: async (axios, ma_ts) => axios.post('/web-hopdong/laydulieu/sp_tt_thamso_md',{
        p_param: ma_ts, 
        p_type:3
    }),

    lay_ds_khuvuc_theo_hd_db: async (axios, hdtb_id) => axios.post('/web-hopdong/thanhly/lay_ds_khuvuc_theo_hd_db',{
        hdtb_id:hdtb_id,
        vinhd_db:1
    }),

    lay_ds_nhanvien_khuvuc_lnv: async (axios, khuvuc_id, loainhanvien_id) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/lay_ds_nhanvien_khuvuc_lnv',{
        khuvuc_id:khuvuc_id,
        loainhanvien_id:loainhanvien_id
    }),

    //chỉnh lại thêm tham số
    lay_ds_nhanvien_khuvuc_lnv_v2: async (axios, khuvuc_id, loainhanvien_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/lay_ds_nhanvien_khuvuc_lnv_v2',{
        khuvuc_id:khuvuc_id,
        loainhanvien_id:loainhanvien_id,
        phuong_id: phuong_id,
        pho_id:pho_id,
        ap_id: ap_id,
        khu_id: khu_id,
        dacdiem_id: dacdiem_id
    }),


    sp_lay_ds_donvi_thicong: async (axios, thuebao_id, loaidv_id) => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_ds_donvi_thicong',{
        kieu:1,
        id:thuebao_id,
        loaidv_id:loaidv_id,
        kieudv_id:2
    }),

    lay_ct_khuvuc_thuebao: async (axios, thuebao_id) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/lay_ct_khuvuc_thuebao',{
        thuebao_id: thuebao_id
    }),
    //Tham chiếu UR2.1.007_062
    xoa_hdtb: async (axios, hdtb_id) => axios.post('/web-hopdong/khoiphucthanhly/xoa_hdtb',{
        hdtb_id: hdtb_id
    }),
    //Tham chiếu  UR2.1.007_057
    xoa_hdkh_v2: async (axios, hdtb_id, loaihd_id) => axios.post('/web-hopdong/khoiphucthanhly/xoa_hdkh_v2',{
        hdkh_id:hdtb_id,
        loaihd_id:loaihd_id
    }),


    sp_capnhat_hopdong_ks: async (axios, data) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_capnhat_hopdong_ks', data),

    sp_themmoi_hopdong_ks: async (axios, data) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_themmoi_hopdong_ks', data),
  
    //Kiểu: 1: Kiem tra ma kh,2 ktra ma thanh toan, 3: kiem tra ma giao dich
    kiemtra_makh_matt_magd: async (axios, ma_kh, kieu, loaihd_id) => axios.post('/web-hopdong/hopdong/kiemtra_makh_matt_magd',{
        ma_kh:ma_kh,
        kieu:kieu,
        loaihd_id:loaihd_id
    }),

    fn_kiemtra_hd_chuahoanthien: async (axios, thuebao_id) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/fn_kiemtra_hd_chuahoanthien',{
        thuebao_id:thuebao_id
    }),

    get_keys: async (axios, keyname) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',{
        keyname: keyname
    }),

    lay_magd: async (axios, loaihd_id) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd',{
        loaihd_id: loaihd_id
    }),

    sp_themmoi_hopdong_ks_v2: async (axios, data) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_themmoi_hopdong_ks_v2', data),

    //ds = laphd.LAY_DS_DONVIQL(obj.phuong_id, obj.pho_id, obj.ap_id, obj.khu_id, 2); trong chọn địa chỉ TB
    lay_ds_donviql: async (axios, phuong_id, pho_id, ap_id, khu_id, kieu) => axios.post('/web-hopdong/lapdatmoi/lay_ds_donviql',{
        vap_id: ap_id,
        vkhu_id: khu_id,
        vkieu: kieu,
        vpho_id: pho_id,
        vphuong_id: phuong_id
    }),

    lay_ds_donvi_theo_loaidv_diachild: async (axios, loaidvId, phuongId, phoId, apId, khuId) => axios.post('/web-thicong/thuebao/lay_ds_donvi_theo_loaidv_diachild', {
        loaidvId: loaidvId,
        phuongId: phuongId,
        phoId: phoId, 
        apId: apId, 
        khuId: khuId
    }),

    // {
    //     "vphuong_id": 414,
    //     "vpho_id": 594,
    //     "vap_id": 0,
    //     "vkhu_id": 0,
    //     "vkieu": 1,
    //     "vloaikh_id": 38
    // }
    lay_ds_donviql_v1: async (axios, data) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/lay_ds_donviql',data),

    
    capnhat_tthd: async (axios, hdtb_id, tthd_id) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/capnhat_tthd_id',{
        hdtb_id:hdtb_id,
        tthd_id:tthd_id
    }),

    // {
    //     "hdkh_id":5686780,
    //     "nhanvien_id":452,
    //     "may_cn":"x",
    //     "ip_cn":"x"
    // } 
    sp_giaophieu_khaosat: async (axios, data) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_giaophieu_khaosat', data),
    
    sp_lay_khuvuc_theo_diadanh: async (axios, data) => axios.post('/web-ccdv/hieuchinhhopdong/sp_lay_khuvuc_theo_diadanh', data),

    //bắt theo error==20, error_code đang không đúng 
    giaoviec_nv_khaosat: async (axios, data) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/giaoviec_nv_khaosat', data),

    sendSMS: async (axios, sdt, noidung) => axios.post('/tichhop/sms/send', {
        msisdn: sdt,
        content : noidung
    }),

    // lay_nhanvien_theo_id: async (axios, nhanvien_id) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md', {
    //     list_of_cols: "*",
    //     order: "",
    //     schema: "admin",
    //     table_name: "nhanvien",
    //     where: "nhanvien_id = "+nhanvien_id
    // }),

    lay_nhanvien_theo_id: async (axios, nhanvien_id) => axios.post('/web-hopdong/laydulieu/sp_tt_nhanvien', {
        p_param: nhanvien_id,
        p_type:3
    }),

    sinh_matb_khaosat: async (axios, kieu) => axios.post('/web-tracuu/tracuu-thuebao/sinh_matb_khaosat', {
        vkieu:kieu
    }),
    
    //https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=620043524&range=E3927
    sp_layhuonggiao_tiepnhan: async (axios, loaihd_id, kieuld_id) => axios.post('/web-hopdong/goi-da-dichvu/sp_layhuonggiao_tiepnhan', {
        loaihd_id: loaihd_id,
        kieuld_id: kieuld_id
    }),

    sp_lay_ds_makh_theo_sogt: async (axios, so_gt, hdkh_id) => axios.post('/web-hopdong/lapdatmoi/sp_lay_ds_makh_theo_sogt', {
        vso_gt: so_gt,
        vhdkh_id: hdkh_id
    }),

    lay_ds_nhanvien_kd: async (axios, loainv_id, donvi_id) => axios.get('/web-hopdong/dk_goi_dadichvu/lay_ds_nhanvien_kd?loainv_id='+loainv_id+'&donvi_id='+donvi_id),
    fn_tt_hd_thuebao:async (axios, param, type) => axios.post(`web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao`, { param: param, type: type }).then(rs => rs.data.data),
    fn_giaophieu_chamdutsd: (axios,data) => axios.post(`web-hopdong/thanhly/fn_giaophieu_chamdutsd`, data).then(rs => rs.data.data),

}