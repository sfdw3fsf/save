export default {
    fn_lay_danh_muc_doisoat_it: async (axios, ds_danhmuc_id) => axios.post('/web-ccdv/doisoat_it/fn_lay_danh_muc_doisoat_it',{
        p_ds_danhmuc_id:ds_danhmuc_id
    }),
    sp_lay_ds_tb_doisoat_vnpt_it: async (axios, data) => axios.post('/web-ccdv/doisoat_it/sp_lay_ds_tb_doisoat_vnpt_it', data),

    sp_lay_ds_thuebao_theo_loaihinhtb: async (axios, loaitb_id) => axios.post('/web-ccdv/doisoat_it/sp_lay_ds_thuebao_theo_loaihinhtb', {
        loaitb_id:loaitb_id
    }),
    sp_lay_ds_ct_tb_chua_doisoat_it: async (axios, thuebao_id) => axios.post('/web-ccdv/doisoat_it/sp_lay_ds_ct_tb_chua_doisoat_it', {
        thuebao_id:thuebao_id
    }),
    sp_ghep_thuebao_doisoat_it: async (axios, js_data) => axios.post('/web-ccdv/doisoat_it/sp_ghep_thuebao_doisoat_it',{
        p_js_data:js_data
    }),
    sp_them_chitiet_doisoat_it: async (axios, js_data) => axios.post('/web-ccdv/doisoat_it/sp_them_chitiet_doisoat_it',{
        p_js_data:js_data
    }),
    sp_capnhat_trangthai_doisoat_it: async (axios, data) => axios.post('/web-ccdv/doisoat_it/sp_capnhat_trangthai_doisoat_it',data),
    fn_import_doisoat_it: async (axios, data) => axios.post('/web-ccdv/doisoat_it/fn_import_doisoat_it',data),
    exportExcelDataDoiSoatIT : async (axios, data) => axios.post('web-report-khdn/doisoat_it/sp_lay_ds_tb_doisoat_vnpt_it_excel', data, {responseType: 'arraybuffer'}),


}
