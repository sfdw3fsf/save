
export default {
    lay_ds_donvi_ql: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_ds_donvi_ql`, data),
    lay_ds_tramvt: (axios, data) => axios.post(`/web-hopdong/tachnhapthuebao/sp_ht_donvi_loaidv_combobox`, data),
    lay_ds_donvi_chuyento_baohong: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_ds_donvi_chuyento_baohong`, data),
    lay_ds_phieu_bh_lachuong: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_ds_phieu_bh_lachuong`, data),
    lay_tt_baohong_chuyento_chung: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_tt_baohong_chuyento_chung`, data),
    hienthi_ds_phieuth_chuyento: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/hienthi_ds_phieuth_chuyento`, data),
    capnhat_chuyento: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/an_capnhat_chuyento`, data),
    lay_ds_baohong_theo_ma_tb: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_baohong_theo_ma_tb`, data),
    lay_danhsach_phieuth_bh: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/lay_danhsach_phieuth_bh_v2`, data),
    lay_ds_thamso_md: (axios, data) => axios.get(`/web-quantri/diaban_nvkd/lay_ds_thamso`, data),
    map_id: (axios, data) => axios.post(`/web-hopdong/thanhly/thanh_ly_map_id`, data),
    
    lay_ds_file_hdtb: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_ds_file_hdtb`, data),
    sp_nap_thongtin_phieu_hientai: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_nap_thongtin_phieu_hientai`, data),
    
    sp_nap_ds_phieu_giaophieuphoihop: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_nap_ds_phieu_giaophieuphoihop`, data),
    fn_kiemtra_giaophieuphoihop: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_kiemtra_giaophieuphoihop`, data),

    giaophieu_phoihop_thicong: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/giaophieu_phoihop_thicong`, data), 
    fn_huy_giao_giaophieuphoihop: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_huy_giao_giaophieuphoihop`, data),
    sp_lay_ds_tokt_theo_nv: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_ds_tokt_theo_nv`, data),
    sp_lay_ds_dvtc_theo_phieu_id: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_ds_dvtc_theo_phieu_id`, data),
 
    fn_tt_hd_thuebao: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao`, data),

    lay_loaitb_id_theo_hopdong: (axios, data) => axios.post(`/web-thicong/thicong-itcare/lay_loaitb_id_theo_hopdong`, data),
    
    
}