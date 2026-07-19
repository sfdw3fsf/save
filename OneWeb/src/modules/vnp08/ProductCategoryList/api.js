export default {
    getCodeCategories: async (axios) => axios.get("/web-quantri/vnp08/dm_loai_sp/sinh_ma_loai_sp"),
    getListCategories: async (axios, data) => axios.get("/web-quantri/vnp08/dm_loai_sp/sp_lay_ds_loai_sp", data),
    insertUpdateCategory: async (axios, data) => axios.post("/web-quantri/vnp08/dm_loai_sp/sp_insert_update_loai_sp", data),
    deleteCategory: async (axios, data) => axios.post("/web-quantri/vnp08/dm_loai_sp/sp_deactive_loai_sp", data),
    // getDetailCategory: async (axios, data) => axios.post("/web-ccdv/doisoat_it/sp_doisoatit_readdata", data),
}
