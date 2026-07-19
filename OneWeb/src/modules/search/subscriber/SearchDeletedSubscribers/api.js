export default {
    //POST: web-tracuu/tracuu-tb-bixoa/lay_danhmuc_lapmoi_tt_chung_v4
    // {
    // "vchuoi": "DOITUONG|DICHVU_VT"
    // }
    // BSS-38782_001 -> Sự kiện form load
    form_load: (axios, data) => axios.post(`/web-hopdong/tracuu-tb-bixoa/lay_danhmuc_lapmoi_tt_chung_v4`, data),

    form_load_loaihinhTB: (axios) => axios.get(`/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB`),

    // POST: web-tracuu/tracuu-tb-bixoa/lay_ds_biendong_xoa_thuebao
    // {
    // "vma_tb": "3563160",
    // "vtungay": "",
    // "vdenngay": ""
    // }
    // BSS-38782_002 -> Nút tra cứu
    search_ds: (axios, data) => axios.post(`/web-tracuu/tracuu-tb-bixoa/lay_ds_biendong_xoa_thuebao`, data),
}