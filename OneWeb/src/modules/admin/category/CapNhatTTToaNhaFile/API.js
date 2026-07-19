export default {

    kt_capnhat_tt_duan_toanha: async (axios, vds) => axios.post('web-danhba/toanha/kt_capnhat_tt_duan_toanha', {
        vds:vds
    }),
    
    fn_capnhat_tt_duan_toanha: async (axios, vds) => axios.post('web-danhba/toanha/fn_capnhat_tt_duan_toanha', {
        vds:vds
    }),

}