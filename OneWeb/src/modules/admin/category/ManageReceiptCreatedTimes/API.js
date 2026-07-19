export default{
    sp_lay_ds_kho_bienlai: async (axios, data,header) => axios.post('/web-quantri/loaibienlai/sp_lay_ds_kho_bienlai', data,header),
    sp_lay_ds_loai_bienlai: async (axios, data,header) => axios.post('/web-quantri/loaibienlai/sp_lay_ds_loai_bienlai', data,header),
    lay_tuso_from_luottao: async (axios, data,header) => axios.post('/web-quantri/loaibienlai/lay_tuso_from_luottao', data,header),
    lay_ds_luottao_vs_bienlai: async (axios, data,header) => axios.post('/web-quantri/loaibienlai/lay_ds_luottao_vs_bienlai', data,header),
    fn_update_luottao_bienlai: async (axios, data,header) => axios.post('/web-quantri/loaibienlai/fn_update_luottao_bienlai', data,header),
    fn_insert_luottao_bienlai: async (axios, data,header) => axios.post('/web-quantri/loaibienlai/fn_insert_luottao_bienlai', data,header),
    delete_luot_tao: async (axios, data,header) => axios.post('/web-quantri/loaibienlai/delete_luot_tao', data,header),
    sinh_bienlai: async (axios, data,header) => axios.post('/web-quantri/loaibienlai/sinh_bienlai', data,header),

}
    