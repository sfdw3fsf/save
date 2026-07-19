export default{
    CSS_DICHVU_VT: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data,header),
    sp_nap_ds_goiddv: async (axios, data,header) => axios.post('/web-quantri/capnhatgoiddv/sp_nap_ds_goiddv', data,header),
    sp_lay_ds_thuebao: async (axios, data,header) => axios.post('/web-quantri/capnhatgoiddv/sp_lay_ds_thuebao', data,header),
    fn_capnhat_goidadv_v2: async (axios, data,header) => axios.post('/web-quantri/capnhatgoiddv/fn_capnhat_goidadv_v2', data,header),
    LAY_DANHBA_THEO_MATB: async (axios, data,header) => axios.post('/web-tracuu/tracu-danhba/LAY_DANHBA_THEO_MATB', data,header),
    thanh_ly_map_id: async (axios, data,header) => axios.post('/web-hopdong/thanhly/thanh_ly_map_id', data,header),
    fn_tt_loaihinh_tb: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb', data,header),


}
    