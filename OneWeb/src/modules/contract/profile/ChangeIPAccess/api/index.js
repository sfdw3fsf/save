export default{
    sp_loaihinh_tb_tocdo: (axios, data) => axios.post('/web-quantri/doiiptruynhap/sp_loaihinh_tb_tocdo', data),
    sp_lay_db_theo_matb_1: (axios, data) => axios.post('/web-quantri/doiiptruynhap/sp_lay_db_theo_matb_1', data),
    sp_lay_ds_thamso_md:  (axios) => axios.post('/web-hopdong/thanhly/sp_lay_ds_thamso_md', {"kieu_id":-1}),
    lay_ds_danhba_adsl:  (axios, linkAPI) => axios.get(linkAPI),
    sp_lay_ds_bras_adsl:  (axios) => axios.post('/web-ccdv/khaibaoims/sp_lay_ds_bras_adsl'),
    fn_map_id:  (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),
    fn_tt_tocdo_adsl:  (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl', data),
    fn_tt_bras:  (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_bras', data),
    getIps:  (axios, data) => axios.post('/tichhop/visa-report/getIps', data),
    getIpsV6:  (axios, data) => axios.post('/tichhop/visa-report/getIpsV6', data),
    kiemtra_ip_tb_ftth:  (axios,data) => axios.post('/web-thicong/thicong-internet/kiemtra_ip_tb_ftth', data),
    sp_frmdoiiptruynhap_capnhat:  (axios,data) => axios.post('/web-quantri/doiiptruynhap/sp_frmdoiiptruynhap_capnhat', data), 
    modifyServiceIPAccessMegaIPv6:  (axios,data) => axios.post('/tichhop/visa-com/modifyServiceIPAccessMegaIPv6', data),
    
}