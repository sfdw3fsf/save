export default {
    getCodeContract: async (axios) => axios.get("/web-quantri/vnp08/dm_loai_hop_dong/sinh_ma_loai_hop_dong"),
    getListContracts: async (axios) => axios.get("/web-quantri/vnp08/dm_loai_hop_dong/sp_lay_ds_loai_hop_dong"),
    insertUpdateContracts: async (axios, data) => axios.post("/web-quantri/vnp08/dm_loai_hop_dong/sp_insert_update_loai_hop_dong", data),
    deleteContract: async (axios, data) => axios.post("/web-quantri/vnp08/dm_loai_hop_dong/sp_deactive_loai_hop_dong", data),
    uploadFile:  async (axios, data) => axios.post('web-quantri/upload', data)
    // getDetailCategory: async (axios, data) => axios.post("/web-ccdv/doisoat_it/sp_doisoatit_readdata", data),
}
