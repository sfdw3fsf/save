
export default { 
    CSS_DICHVU_VT: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/CSS_DICHVU_VT`, data),
    ht_tocdo_kenh_combobox: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/ht_tocdo_kenh_combobox`, data),
    ht_tvdonvi_giaophieu_net: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/ht_tvdonvi_giaophieu_net`, data),
    load_tt_tb_giaophieu_net: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/load_tt_tb_giaophieu_net`, data),
    lay_tocdo_giaophieu_net: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_tocdo_giaophieu_net`, data),
    lay_diachi_daucuoi_giaophieu_net: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_diachi_daucuoi_giaophieu_net`, data),
    lay_ds_hd_theo_ma_tb: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_ds_hd_theo_ma_tb`, data),
    lay_danhsach_phieu_net_con: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_danhsach_phieu_net_con`, data),
    lay_danhsach_phieu_net_con_bancheo: async (axios, data) => axios.get(`/web-bancheo/hoan_cong_megawan_net/lay_danhsach_phieu_net_con_bancheo?vphieu_cha_id=${data.vphieu_cha_id}&vphanvung_id=${data.vphanvung_id}`),
    fn_check_thongtin_donvi_giao_bancheo: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_check_thongtin_donvi_giao_bancheo`, data),
    fn_thuchien_giaophieu_frm_giaophieu_net_khdn: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_thuchien_giaophieu_frm_giaophieu_net`, data),
    fn_huygiao_frm_giaophieu_net: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_huygiao_frm_giaophieu_net`, data),
    lay_chitiet_huonggiao: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_chitiet_huonggiao`, data),
    get_keys: (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
}