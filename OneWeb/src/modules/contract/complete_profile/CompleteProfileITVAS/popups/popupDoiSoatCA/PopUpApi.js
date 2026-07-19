
export default {

    sp_lay_kieuld_doisoatca: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_kieuld_doisoatca`, data),
    sp_lay_dichvuvt_doisoatca: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_dichvuvt_doisoatca`, data),
    sp_lay_loaigt_doisoatca: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_loaigt_doisoatca`, data),
    sp_lay_tocdo_doisoatca: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_tocdo_doisoatca`, data),
    sp_lay_mucdich_doisoatca: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_mucdich_doisoatca`, data),
    sp_lay_loaitb_doisoatca: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_loaitb_doisoatca`, data),
    lay_ds_hopdong_theo_ma_gd: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_ds_hopdong_theo_ma_gd`, data),
    lay_ds_hdtb_cntt_theo_hdtb_id: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_ds_hdtb_cntt_theo_hdtb_id`, data),
    sp_lay_hdtbsub_doisoatca: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_hdtbsub_doisoatca`, data),
    sp_lay_ds_chungchi_ssl: async (axios, data,header) => axios.get('/web-ccdv/tram-thicong-dv-cntt/sp_lay_ds_chungchi_ssl', data, header),
    lay_ds_hopdong_xemcms: (axios, data) => axios.post(`/web-tracuu/tracuu-thuebao/lay_ds_hopdong_xemcms`, data),
    fn_capnhat_chuanhoa_xemcms: async (axios, data, header) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_capnhat_chuanhoa_xemcms`, data, header),

}
