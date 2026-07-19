export default {
    getCodeProducts: async (axios) => axios.get("/web-quantri/vnp08/dm_san_pham/sinh_ma_sp"),
    getListProducts: async (axios, data) => axios.get("/web-quantri/vnp08/dm_san_pham/sp_lay_ds_sp", data),
    insertUpdateProduct: async (axios, data) => axios.post("/web-quantri/vnp08/dm_san_pham/sp_insert_update_sp", data),
    deleteProduct: async (axios, data) => axios.post("/web-quantri/vnp08/dm_san_pham/sp_deactive_sp", data),
    getListProductCategories: async (axios) => axios.get("/web-quantri/vnp08/dm_loai_sp/sp_lay_ds_loai_sp"),
}
