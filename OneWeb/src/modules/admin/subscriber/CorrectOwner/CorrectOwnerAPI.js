export default {
    lay_danhsach_dichvu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    lay_danhsach_loaihinh_tb: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
    lay_danhsach_chuquan: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_CHUQUAN'),
    lay_danhba_theo_matb: async (axios, in_ma_tb, in_dichvuvt_id, in_donvi_dl_id) => axios.post('/web-danhba/chuanhoa_chuquan/lay_danhba_theo_matb',{
        in_ma_tb: in_ma_tb,
        in_dichvuvt_id: in_dichvuvt_id,
        in_donvi_dl_id: in_donvi_dl_id
    }),
    lay_ds_tt_danhba: async (axios, thuebao_id, dichvu_id) => axios.post('/web-danhba/chuanhoa_chuquan/lay_ds_tt_danhba',{
        thuebao_id: thuebao_id,
        dichvu_id: dichvu_id
    }),

    ghilai_thay_doi_chuquan_bc: async (axios, data) => axios.post('/web-danhba/chuanhoa_chuquan/ghilai_thay_doi_chuquan_bc', data),
 
}