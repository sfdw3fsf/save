export default {
    sp_ds_chatluong: async (axios, hdtb_id, baohong_id) => axios.post('/web-ccdv/khaibaotsl/sp_ds_chatluong',{
        vhdtb_id: hdtb_id,
        vbaohong_id: baohong_id
    }),

    capnhat_chatluong_hdtb: async (axios, kieu, ds_chatluong) => axios.post('/web-ccdv/khaibaotsl/capnhat_chatluong_hdtb',{
        vkieu: kieu,
        ds_chatluong: ds_chatluong
    }),

    capnhat_chatluong_bh: async (axios, kieu, ds_chatluong_bh) => axios.post('/web-ccdv/khaibaotsl/capnhat_chatluong_bh',{
        vkieu: kieu,
        ds_chatluong_bh: ds_chatluong_bh
    }),
    
}