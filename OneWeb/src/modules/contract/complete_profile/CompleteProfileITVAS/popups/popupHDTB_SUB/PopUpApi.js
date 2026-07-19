
export default { 
    //#region HDTB_SUB_V3
    lay_tt_thuebao_bosung: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_tt_thuebao_bosung', data, header),
    ivan_khoa_form_thongtin_bosung: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/ivan_khoa_form_thongtin_bosung', data, header),
    fn_an_capnhat_hdtb_sub_v3: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_an_capnhat_hdtb_sub_v3', data, header), 
    //#end region

    //#region HDTB_SUB_V2
    fn_lay_loaitb_theo_hdtb_id: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/fn_lay_loaitb_theo_hdtb_id', data, header), 
    lay_tt_thuebao_bosung_ca: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_tt_thuebao_bosung_ca', data, header), 
    sp_loadthongtinloaigt: async (axios, data,header) => axios.get('/web-ccdv/tram-thicong-dv-cntt/sp_loadthongtinloaigt', data, header),  
    ca_khoa_form_thongtin_bosung: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/ca_khoa_form_thongtin_bosung', data, header),
    capnhat_hdtb_sub_ra_ca: async (axios, data,header) => axios.post('/web-ccdv/tram-thicong-dv-cntt/capnhat_hdtb_sub_ra_ca', data, header),

    
    
    //#end region


    
    
    
}