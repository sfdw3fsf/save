export default {
    sp_lay_ds_doisoat: async (axios, data) => axios.post("/web-ccdv/doisoat_it/sp_lay_ds_doisoat", data),
    // sp_lay_ds_doisoat_v3: async (axios, data) => axios.post("/web-thicong/capnhatthongtin/sp_lay_ds_doisoat_v3", data),
    sp_lay_ds_doisoat_v3: async (axios, data) => axios.post("/web-ccdv/doisoat_it/sp_lay_ds_doisoat_v4", data),
    sp_lay_ds_tinh_khac: async (axios, data) => axios.post("/web-ccdv/doisoat_it/sp_lay_ds_tinh_khac", data),
    sp_capnhat_tt_dl_doisoat: async (axios, data) => axios.post("/web-bancheo/doisoat_it/sp_capnhat_tt_dl_doisoat", data),
    fn_tt_tinh: async (axios, data) => axios.post("/web-ccdv/doisoat_it/fn_tt_tinh", data),
    sp_them_dl_doisoat: async (axios, data) => axios.post("/web-bancheo/doisoat_it/sp_them_dl_doisoat_v2", data),
    sp_xoa_ds_doisoat: async (axios, data) => axios.post("/web-ccdv/doisoat_it/sp_xoa_ds_doisoat", data),
    sp_capnhat_xacnhan_cq: async (axios, data) => axios.post("/web-ccdv/doisoat_it/sp_capnhat_xacnhan_cq", data),
    sp_dongbo_doisoat: async (axios, data) => axios.post("/web-bancheo/doisoat_it/sp_dongbo_doisoat", data),
    sp_lay_ds_tinh: async (axios, data) => axios.post("/web-ccdv/doisoat_it/sp_lay_ds_tinh", data),
    sp_capnhat_cq_doisoat_tinh: async (axios, data) => axios.post("/web-ccdv/doisoat_it/sp_capnhat_cq_doisoat_tinh", data),
    sp_capnhat_tb_doisoat_xacnhan_cq: async (axios, data) => axios.post("/web-ccdv/doisoat_it/sp_capnhat_tb_doisoat_xacnhan_cq", data),
    sp_them_log_dl_doisoat: async (axios, data) => axios.post("/web-ccdv/doisoat_it/sp_them_log_dl_doisoat", data)
}
