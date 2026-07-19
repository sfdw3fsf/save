export default{
    getDmKieuHD: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_ds_kieu_hd', data),
    getDmKenhThu: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_get_data_kenhthu', data),
    getDmLoaiHD: async (axios,data) => axios.get(`/web-thicong/danhmuc/lay_ds_loai_hd?loaiHdId=${data.loaiHdId}`, data),
    getDmQuyTrinh: async (axios,data) => axios.post('web-thicong/hoancongmegawan/lay_ds_quytrinh_v2',data),
    getDmNguoiGV: async (axios,data) => axios.post('web-thicong/hoancongmegawan/sp_lay_ds_nhanvien_theo_donvi',data),
    getDmThoiHan: async (axios,data) => axios.post('web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc',data),
    sp_tt_loai_kenh: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_loai_kenh',data),
    sp_tt_loai_modem: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_loai_modem',data),
    sp_tt_pe: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_pe',data),
    sp_tt_tocdo_kenh: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_tocdo_kenh',data),
    sp_tt_trangbi: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_trangbi',data),
    getDmLoaiNode: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_loai_node_mgwan',data),
    getDmLoaiTBi: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc',data),
    sp_tt_chuquan: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_chuquan',data),
    getDmThietBi: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc',data),
    getDmLoaiCap: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_ds_loai_cap',data),
    sp_tt_doitac: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_doitac',data),


    sp_lay_huonggiao_theo_quytrinh: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_huonggiao_theo_quytrinh',data),
    getDsHopDongTB: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_ds_phieu_giao_den',data),
    sp_lay_luong_thaotac: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_luong_thaotac',data),
    getDsLam: async (axios,data) => axios.post('/web-ccdv/khaibaotsl/lay_ds_down_uplink_theo_port_id',data),
    getThongTinLH: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/fn_lay_thongtin_lh_lapdat_v2',data),
    getThongTongTien: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/lay_tongtien_theo_hdkh_id',data),
    getThongTinNVYC: async (axios,data) => axios.get(`/web-hopdong/danhmuc/lay_thongtin_nhanvien/${data.nhanvien_id}`,data),
    getThongTinNV_TiepThi: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_thongtin_nhanvien_dv',data),
    getThongTinKenhThu: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_kenhthu_theo_hdkh',data),
    getThongTinPhieuTra: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_ht_phieu_tra',data),
    getThongTinLyDoTra: async (axios,data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_lydotra_tc',data),
    getThongTinLapKem: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/lay_thuebao_lapkem',data),
    getThongTinDonVi: async (axios,data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_dv',data),
    getThongTinIP: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_thongso_kt',data),
    getThongTinTram: async (axios,data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi',data),
    getDsNhanVien: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/lay_ds_nhanvien_theo_phieu_id',data),
    getDsDichVuDK: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/lay_ds_dangky_dvgt',data),
    post_hoancong_megawan: async (axios,data) => axios.post('/web-ccdv/tiepnhanhopdong/fn_capnhat_hoancongmegawan',data),
    hoancong_hoancongmegawan: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/fn_hoancong_hoancongmegawan',data),

    lay_idx_huonggiao_theoquytrinh_id: async (axios,data) => axios.post('/web-hopdong/thanhly/lay_idx_huonggiao_theoquytrinh_id',data),
    sp_lay_huonggiao_giaophieu_hoancong_megawan: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_huonggiao_giaophieu_hoancong_megawan',data),
    lay_ds_phieuvt_hdbh: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/lay_ds_phieuvt_hdbh',data),

    sp_tt_kieu_ld: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_kieu_ld',data),
    lay_thongso_thicong: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/lay_thongso_thicong',data),
    // btn Port N.Công
    sp_update_hdtb_mgwan_theo_dslam: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/sp_update_hdtb_mgwan_theo_dslam',data),
    capnhat_port_hoancong_megawan: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/capnhat_port_hoancong_megawan',data),
    // btn DauNoiCap
    lay_ds_thamso_md_mats: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/lay_ds_thamso_md_mats',data),
    sp_tt_diachi_hdtb: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_diachi_hdtb',data),
    sp_tt_donvi: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_donvi',data),
    sp_tt_daunoi_cap_tb: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_daunoi_cap_tb',data),
    sp_tt_diachi_tb: async (axios,data) => axios.post('/web-hopdong/laydulieu/sp_tt_diachi_tb',data),
    // btn NDTH
    update_nd_thuchien: async (axios,data) => axios.post('/web-thicong/hoancongmegawan/update_nd_thuchien',data),

    sp_lay_ds_loai_cap: async (axios, data,header) => axios.post('/web-thicong/hoancongmegawan/sp_lay_ds_loai_cap', data,header),
    sp_thaydoi_ts_mgwan_danhmuc: async (axios, data,header) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc', data,header),
    sp_lay_loai_node_mgwan: async (axios, data,header) => axios.post('/web-thicong/hoancongmegawan/sp_lay_loai_node_mgwan', data,header),
    sp_lay_ds_nhanvien_theo_donvi: async (axios, data,header) => axios.post('/web-thicong/hoancongmegawan/sp_lay_ds_nhanvien_theo_donvi', data,header),
    sp_lay_luong_thaotac: async (axios, data,header) => axios.post('/web-thicong/hoancongmegawan/sp_lay_luong_thaotac', data,header),
    sp_lay_ct_thaotac_control: async (axios, data,header) => axios.post('/web-thicong/hoancongmegawan/sp_lay_ct_thaotac_control', data,header),
    sp_lay_ds_phieu_giao_den: async (axios, data,header) => axios.post('/web-thicong/hoancongmegawan/sp_lay_ds_phieu_giao_den', data,header),

    fn_tt_chonport_tudong_hoancong_mgwan: (axios, data) => axios.post('/web-thicong/hoancongmegawan/fn_tt_chonport_tudong_hoancong_mgwan', data),

    fn_tt_hdtb_hen: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_hen', data),
    fn_tt_hdtb_mgwan: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_mgwan', data),
    fn_tt_hd_thuebao: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao', data),
    fn_tt_diachi_hdtb: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_hdtb', data),

    fn_kiemtra_giaophieu_vattu: (axios, data) => axios.post('/web-thicong/khaibao-truyensolieu/fn_kiemtra_giaophieu_vattu', data),
    lay_ds_thongtin_kythuat_hdtb: (axios, data) => axios.post('/web-thicong/hoancongmegawan/lay_ds_thongtin_kythuat_hdtb', data),

    fn_hths_hoancongmegawan: (axios, data) => axios.post('/web-thicong/hoancongmegawan/fn_hths_hoancongmegawan', data),
    lay_huonggiao_gp: (axios, data) => axios.get(`web-thicong/hoancongmegawan/lay_huonggiao_gp?luongId=${data.luongId}`, data),

    // Them ban cheo
    hoancong_thicong_2: (axios, data) => axios.post(`/web-ccdv/hoanthienhschuyenquyen/hoancong_thicong_2?hdtb_id=${data.hdtb_id}`, data),
    lay_thongtin_tinh: (axios, data) => axios.get(`/web-hopdong/hopdong/lay_thongtin_tinh?tinh_id=${data.tinh_id}`, data),
    hoancong_thicong_v3: (axios, data) => axios.post('/web-bancheo/tracuu/hoancong_thicong_v3', data),
    sp_lay_thongtin_bancheo: (axios, data) => axios.post('/web-thicong/danhmuc/sp_lay_thongtin_bancheo', data),
    sp_them_db_bancheo_v2: (axios, data) => axios.post('/web-bancheo/tracuu/sp_them_db_bancheo_v2', data),
    log_action: (axios, data) => axios.post('/web-thicong/hoancongmegawan/log_action', data),
    fn_tt_hd_bancheo: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_bancheo', data),
    hc_net_sms_vip_ph: (axios, data) => axios.post('/web-bancheo/tracuu/hc_net_sms_vip_ph', data),
    
    sp_capnhattoadohdtb: (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_capnhattoadohdtb', data),
}
    