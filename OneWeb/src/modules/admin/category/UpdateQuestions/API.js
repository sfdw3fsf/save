export default {
    getDSTieuChuan: axios =>axios.get("/web-quantri/capnhatcauhoi/sp_lay_ds_tieuchuan"),
    getDSHinhThuc: axios =>axios.get("/web-quantri/capnhatcauhoi/sp_lay_ds_hinhthuc"),
    getDSLoaiCauHoi: axios =>axios.get("/web-quantri/capnhatcauhoi/sp_lay_ds_loai_ch"),
    
    getDSCauHoi: axios =>axios.get("/web-quantri/capnhatcauhoi/sp_lay_ds_cauhoi"),
   
    getDSCauTraLoi: axios =>axios.get("/web-quantri/capnhatcautraloi/sp_ht_ds_traloi"),
    getDSCauTLChuaGan: async (axios, data) => axios.post('/web-quantri/capnhatcauhoi/sp_lay_cau_traloi_chuagan', data),
    getDSCauTLDaGan: async (axios, data) => axios.post('/web-quantri/capnhatcauhoi/sp_lay_cau_traloi_dagan', data),
   
    XoaCauHoi: async (axios, data) => axios.post('/web-quantri/capnhatcauhoi/sp_xoa_cauhoi', data),
    CapNhatCauHoi: async (axios, data) => axios.post('/web-quantri/capnhatcauhoi/sp_capnhat_cauhoi', data),

    popup_CHP_layDSCauHoi: axios =>axios.get("/web-quantri/capnhatcauhoi/sp_lay_cauhoi"),
    popup_CHP_layDSCauHoiPhu: async (axios, rch_id) => axios.post('/web-quantri/capnhatcauhoi/sp_lay_ds_cauhoiphu_load', {
        rch_id: rch_id
    }),
    popup_CHP_ghilai: async (axios, data) => axios.post('/web-quantri/capnhatcauhoi/sp_ghilai_cauhoiphu', data),

    popup_TNDV_donvidavachuagan: async (axios, data) => axios.post('/web-quantri/capnhatcauhoi/sp_lay_ds_loai_dvi_in_cautraloi', data),
    popup_TNDV_ghilai: async (axios, data) => axios.post('/web-quantri/capnhatcauhoi/sp_lay_ds_loai_dvi_in_cautraloi', data),
    

}

