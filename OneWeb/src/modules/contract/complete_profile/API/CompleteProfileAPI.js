export default{
    CSS_KIEU_HD: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_HD', data, header),
    CSS_KENHTHU: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_KENHTHU', data, header),
    lay_ds_loai_hd: async (axios, data,header) => axios.post('/web-ccdv/khaibaoims/lay_ds_loai_hd', data, header),
    ds_quytrinh: async (axios, data,header) => axios.post('/web-hopdong/danhmuc/ds_quytrinh', data, header),
    sp_lay_huonggiao_theo_quytrinh: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_lay_huonggiao_theo_quytrinh', data, header),
    lay_luong_thaotac: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_luong_thaotac', data, header),
    lay_ct_thaotac_control: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ct_thaotac_control', data, header),
    sp_lay_kenhthu_theo_hdkh: async (axios, data,header) => axios.post('/web-ccdv/khaibaotsl/sp_lay_kenhthu_theo_hdkh', data, header),
    lay_huonggiao_theo_luong_id: async (axios, data,header) => axios.post('/web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id', data, header),
    lay_ds_trangthai_jira: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_trangthai_jira', data, header),

    
    
    //lấy tổng tiền
    lay_tongtien_theo_hdkh_id: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_tongtien_theo_hdkh_id', data, header),
    lay_thongtin_nhanvien: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_thongtin_nhanvien', data, header),
    kiemtra_hen_hdtb: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/kiemtra_hen_hdtb', data, header),
    sp_ht_phieu_tra: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_ht_phieu_tra', data, header),
    lay_ds_hdtb_dv: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_hdtb_dv', data, header),
    lay_ten_loai_khl: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ten_loai_khl', data, header),
    phailam: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/phailam', data, header),
    kt_phieu_phoihop: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/kt_phieu_phoihop', data, header),
    lay_thuebao_lapkem: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_thuebao_lapkem', data, header),
    

    //******TABS
    //tab_dv_cntt
    lay_ds_nhanvien_theo_donvi: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_donvi', data, header),
    lay_ds_phieu_hc_dv_cntt_v2: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_phieu_hc_dv_cntt_v2', data, header),
    lay_danhsach_thuoctinh_v3: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_danhsach_thuoctinh_v3', data, header),
    lay_danhsach_thuoctinh_v3_tichhop_sql: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_danhsach_thuoctinh_v3_tichhop_sql', data, header),
    
    lay_ds_nhanvien_theo_phieu_id: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id', data, header),
    lay_thongtin_lh_lapdat: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_thongtin_lh_lapdat', data, header),
    lay_ds_hdtb_cntt_theo_hdtb_id: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_hdtb_cntt_theo_hdtb_id', data, header),
    lay_danhsach_phieu_net_con: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_danhsach_phieu_net_con', data, header),
 
    //tab_it360
    lay_ds_issue_cua_hopdongtb: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_issue_cua_hopdongtb', data, header),
    
    //tab thông tin gói cước
    lay_ds_thamso_tskt: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_thamso_tskt', data, header),
    //tab thông tin dịch vụ gia tăng
    lay_ds_dangky_dvgt: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_dangky_dvgt', data, header),
    //tab tiến trình thi công  
    lay_tinh_thi_cong: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_tinh_thi_cong', data, header),
    lay_tientrinh_tinh_tc: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_tientrinh_tinh_tc', data, header),
    //******END TABS

    
    //******CÁC SỰ KIỆN BUTTONS
    update_nd_thuchien: async (axios, data,header) => axios.post('/web-hopdong/thaydoi_thongso_megawan/update_nd_thuchien', data, header),    


    //cap nhật F9
    fn_an_capnhat_hoancong_dvcntt: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_capnhat_hoancong_dvcntt', data, header),    
    //kích hoạt VISA
    capnhat_ngaykh_status_v2: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/capnhat_ngaykh_status_v2', data, header),
    //hoàn công
    fn_an_hoancong_dvcntt: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_hoancong_dvcntt', data, header),

    fn_an_hoanthanh: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_hoanthanh', data, header),

    fn_ghilai_noidung_tientrinh_thicong: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_ghilai_noidung_tientrinh_thicong', data, header),
    
    
    //******END CÁC SỰ KIỆN
}


    