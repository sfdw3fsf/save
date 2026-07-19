export default {
    phutro_lay_ds_khmuc: async (axios) => axios.post('/web-quantri/qlkhoanmuc/phutro_lay_ds_khmuc',{
        vkieu:1
    }),

    // {
    //     "vkhoanmuc_id": 0,
    //     "vghichu": "string",
    //     "vkieu": 0,
    //     "vkhoanmuc": "string"
    // }
    phutro_capnhap_kmuc: async (axios, data) => axios.post('/web-quantri/qlkhoanmuc/phutro_capnhap_kmuc', data),
}