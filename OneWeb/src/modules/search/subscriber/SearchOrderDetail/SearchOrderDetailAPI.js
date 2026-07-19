export default{
    toc_do_kenh: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH', data),
    dich_vu: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data),
    sp_lay_ds_tb_dk_goi: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_tb_dk_goi', data),
    // loai_giay_to: async (axios, data) => axios.post('/web-ccdv/tiepnhan-hdtuvan/sp_frmtiepnhankyhd_load_loaigt', data),
    tra_cuu_thue_bao: async (axios, data) => axios.post('/web-tracuu/thuebaoims/tracuu_tt_ims', data),
    tracuu: async (axios, data) => axios.post('/web-tracuu/thuebaoims/sp_lay_dichvu_gt_theo_idvtn', data),

    // popup phieu ton
    sp_lay_ds_ton_phieu_hist: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_ton_phieu_hist', data),

    //popup chi tiết hợp đồng phụ lục
    sp_lay_tt_khachhang_tien_by_hdtb_id:  (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_khachhang_tien_by_hdtb_id', data),
    sp_lay_tt_thuebao_chinh_by_hdtb_id:  (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_chinh_by_hdtb_id', data),
    sp_lay_tt_thuebao_them_by_hdtb_id:  (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_them_by_hdtb_id', data),
    sp_lay_tt_thuebao_kythuat_by_hdtb_id: (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_kythuat_by_hdtb_id', data),
    sp_lay_tt_thuebao_khac_by_hdtb_id:  (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_khac_by_hdtb_id', data),
    sp_lay_tt_khachhang_by_hdtb_id:  (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_khachhang_by_hdtb_id', data),
    sp_lay_tt_thanhtoan_by_hdtb_id:  (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thanhtoan_by_hdtb_id', data),
    fn_get_econtract_id_by_ma_gd: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/fn_get_econtract_id_by_ma_gd', data),
    
    //popup địa chỉ lắp đặt
    sp_lay_tt_show_diachi_ld: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_show_diachi_ld', data),

    // API tìm kiếm
    danh_sach_thue_bao: async (axios, data) => axios.post('/web-tracuu/tcnone/lay_ds_hd_tb_theo_hdkh_id_vsecond', data),
    lich_su_don_hang: async (axios, data) => axios.post('/web-tracuu/tcnone/lay_danhdanhsach_hd_dh', data),
    chi_tiet_thi_cong: async (axios, data) => axios.post('/web-tracuu/tcnone/lay_danhsach_phieuth', data),
    thong_tin_phieu_yc: async (axios, data) => axios.post('/web-tracuu/tcnone/lay_danhsachhd_dhvsecond', data),
    lay_huong_kn: async (axios, data) => axios.get('/web-ccdv/tien_trinh_bao_hong/lay_huongkn_moi_cu/'+data),
    lay_tb_cu: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_matb_cu_theo_hdtb', data),
    lay_ctv: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_ten_ctv_theo_hdkh_id', data),
    map_id: async (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),
    fn_tt_hd_khachhang: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_khachhang', data),

    // popup giao phiếu tc, hủy phiếu
    giao_lai_phieu: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/mo_chuyen_dl_hdtb_sang_giaophieu', data),
    lay_tt_don_hang: async (axios, data) => axios.get('/web-ccdv/tien_trinh_bao_hong/lay_thongtin_donhang_v2/'+data),

    // popup đổi gói
    lay_tt_kh: async (axios, data) => axios.get('/web-ccdv/tien_trinh_bao_hong/laythongtinkhachhang_theodh/'+data),
    lay_tt_sp: async (axios, data) => axios.get('/web-ccdv/tien_trinh_bao_hong/lay_thongtin_donhang_v2/'+data),
    lay_ds_goi: async (axios, data) => axios.get('/web-ccdv/tien_trinh_bao_hong/chon_sanpham_donhang/'+data),
    lay_mo_ta_sp: async (axios, data) => axios.get('/web-ccdv/tien_trinh_bao_hong/laythongtinsp_v2/'+data),

    // popup ds đơn hàng
    lay_ds_don_hang: async (axios, data) => axios.post('/web-hopdong/danhsach-donhang/laythongtintracuudonhang', data),

}