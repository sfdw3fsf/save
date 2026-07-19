export default{
    getDsTTHD: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TRANGTHAI_HD'),
    getDsLoaiHD: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD'),
    getDsDichVu: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    getDsTraCuuTheo: (axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_ht_kieu_tk', data),
    searchTheoDKBanCheo: (axios, data) => axios.post('/web-thicong/hopdong-thicong/sp_timkiem_theodk_bancheo', data),
    searchHopDongTC: (axios, data) => axios.post('/web-thicong/hopdong-thicong/tra_cuu_hop_dong_tc', data),
    getDsHDTTTheoHDKHId: (axios, data) => axios.get('/web-tracuu/hopdong/ds_hopdong_tt_theo_hdkh_id?hdkhId=' + data),
    getDsHDTBTheoHDKHId: (axios, data) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/lay_ds_hopdong_tb_theo_hdkh_id', data),
    getDsHDTBTraBanTheoHDKHId: (axios, data) => axios.post('/web-thicong/hopdong-thicong/lay_ds_hd_tb_traban_theo_hdkh_id', data),
    mapId: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/map_id', data),
    mapId_v2: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/map_id_v2', data),
    fnThoaiTraBan: (axios, data) => axios.post('/web-thicong/hopdong-thicong/fn_thoaitraban_bancheo', data),
    getChuyenNvBC: (axios, data) => axios.post('/web-thicong/hopdong-thicong/sp_chuyennv_bancheo', data),
    bcChuyenHDNV: (axios, data) => axios.post('/web-thicong/hopdong-thicong/bc_chuyen_hd_nhanvien', data),
    capNhatThoaiTraHD: (axios, data) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/bancheo_thoaitra_hd', data),
    thoaiTraHD: (axios, data) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/thoaitra_hd', data),
    getHDBCTheoHDTC: (axios, data) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/sp_hd_bancheo_theo_hdtb_thicong', data),
    getPhieuVIP: (axios, data) => axios.get('/web-hopdong/khuyenmai_tocdo_internet/bancheo_lay_phieu_vip_net_media?vhdtb_id=' + data),
    bcThoaiUpdateVip: (axios, data) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/bancheo_thoai_update_vip_net_media', data),
    getLayThongTinNV: (axios, data) => axios.get('/web-hopdong/khuyenmai_tocdo_internet/bc_lay_thongtin_nhanvien?vma_nv=' + data),
    getDsHDBCTheoLoai: (axios, data) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/sp_ds_hd_bancheo_theoloai', data),
    getTinhTCTheoTinhId: (axios, data) => axios.get('/web-hopdong/khuyenmai_tocdo_internet/sp_lay_tinh_tc_theo_tinh_id?p_tinh_id=' + data),
    getBCLayTTNVTinhBan: (axios, data) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/bancheo_lay_tt_nhanvien_tinhban', data),
    // Lấy thông tin văn bản và thông tin triển khai
    getDsThongTin: (axios, data) => axios.get('/web-khdn/hopdong/lay_ttvanban_va_tttrienkhai?p_hdkh_id='+data),
    // Lấy thông tin phân chia doanh thu
    getPhanChiaDoanhThu:  (axios, data) => axios.get('web-khdn/doanhthu/lay_ds_thuebao_gan_coche_bancheo?p_hdkh_id='+data),
    // lấy danh sách file đính kèm
    getDsFileDinhKem: (axios, data) => axios.get('web-khdn/danhmucbancheo/lay_danhsach_file_congvan?p_congvan_id='+data),
    //{vnhanvien_id, vdonvi_id, vma_gd}
    nhanHopDong:(axios, {vnhanvien_id, vdonvi_id, vma_gd}) => axios.post('/web-thicong/hopdong-thicong/bc_chuyen_hd_nhanvien', {vnhanvien_id, vdonvi_id, vma_gd}),
    download_file: async (axios, data) => axios.post('/web-quantri/upload/link_view_file', data),
}
