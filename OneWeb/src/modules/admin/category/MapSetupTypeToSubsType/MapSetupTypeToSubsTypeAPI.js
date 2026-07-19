export default{
    lay_loai_hd: async (axios) => axios.post('/web-quantri/kieuldlhtb/sp_loai_hd',{}),

    lay_kieuld_tb: async (axios, loaihd_id) => axios.post('/web-quantri/kieuldlhtb/sp_lay_kieuld_tb',{
        vloaihd_id: loaihd_id
    }),

    lay_ds_dichvuvt: async (axios) => axios.post('/web-quantri/kieuldlhtb/sp_lay_dk_cho_bang',{
        ten_bang: "DICHVU_VT"
    }),
    

    // {
    //     "vloai": 2,
    //     "vkieuld_id": 2,
    //     "vkieu": 2,
    //     "vdichvuvt_id": 8
    // }

    lay_kieuld_lhtb: async (axios, data) => axios.post('/web-quantri/kieuldlhtb/sp_lay_kieuld_lhtb', data),


    capnhat_kieuld_lhtb: async (axios, data) => axios.post('/web-quantri/kieuldlhtb/sp_capnhat_kieuld_lhtb', data),

}