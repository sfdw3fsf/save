export default{
    tao_cap_moi: async (axios, data) => axios.post('/web-quantri/thongtin-cap/sp_tao_madoicap_theo_dsthuebao_id', data),
    ghep_doi_ma_cap: async (axios, data) => axios.post('/web-quantri/thongtin-cap/sp_tao_madoicap_theo_dsthuebao_id', data),
    tracuu: async (axios, data) => axios.post('/web-quantri/thongtin-cap/sp_lay_ds_tb_cung_madoicap', data),
    sua_ma_truy_nhap: async (axios, data) => axios.post('/web-quantri/thongtin-cap/sp_capnhat_matb_tn', data),
}
    