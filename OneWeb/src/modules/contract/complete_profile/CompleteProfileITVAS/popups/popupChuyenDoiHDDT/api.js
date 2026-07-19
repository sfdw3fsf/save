export default {
    lay_thong_tin_da_dang_ky:(axios, vhdtb_id)=>axios.post(`/web-quantri/chuyendoihoadon/lay_ds_hd_thuebao_ct_common`, {vhdtb_id}),
    lay_thong_tin_KHHDDT: (axios, mst) => axios.get(`/tichhop/hddt/LayThongTinKHHDDT_V2`, {params:{taxcode:mst}}),
    lay_thongso_dkmua_them_hd: (axios, hdtb_id) => axios.post(`/web-ccdv/tram-thicong-dv-cntt/lay_thongso_dkmua_them_hd`, {hdtb_id}),
    them_hd_thuebao_ct_common: (axios, {vhdtb_id, vds_hdtb_ct}) => axios.post(`/web-thicong/hoinghi_truyenhinh/them_hd_thuebao_ct_common`, {vhdtb_id, vds_hdtb_ct}),  

    MuaThemHoaDon: (axios, data) => axios.post(`/tichhop/hddt/MuaThemHoaDon`, data),
    //
    tao_req_cntt: async (axios, hdtb_id) => axios.post('/web-thicong/hoinghi_truyenhinh/tao_req_cntt', { "vhdtb_id": hdtb_id, "dem": 0, "vkey": "MTHHDT_MTT"}),
    cap_nhat_status_hdtb: async (axios, data) => axios.post('/web-ccdv/tram-thicong-dv-cntt/cap_nhat_status_hdtb', data),

    fn_get_status_hdtb: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_get_status_hdtb', data),
}