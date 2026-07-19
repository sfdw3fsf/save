
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
    sp_nap_ds_phieu_giaophieuphoihop: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_nap_ds_phieu_giaophieuphoihop`, data),
 
}