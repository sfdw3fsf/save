export default{
    
    sp_ht_ds_danhmuc_vattu: async (axios)=> axios.post('/web-quantri/vattu/sp_ht_ds_danhmuc_vattu',{
        db_tbi: null,
        ht_id: null,
        kieu_check: 1
    }),
    sp_ds_vattu_cha: async (axios)=> axios.post('/web-quantri/thuoctinh/sp_ds_vattu_cha',{}),

    sp_ds_loai_dieukhien: async (axios)=> axios.post('/web-quantri/thuoctinh/sp_ds_loai_dieukhien',{}),

    lay_ds_ttktt_gan_chuagan: async (axios, vattu_id, kieu)=> axios.post('/web-quantri/thuoctinh/lay_ds_ttktt_gan_vattu',{
        vkieu: kieu,
        vvattu_id: vattu_id
    }),

    lay_ds_thuoctinh_ktt_vt: async (axios, vattu_id, rtt_id)=> axios.post('/web-quantri/thuoctinh/lay_ds_thuoctinh_ktt_vt',{
        vvattu_id: vattu_id,
        vrtt_id: rtt_id
    }),
    //dulieu: Array[{TTVT_ID:,VATTU_ID:,}]
    fn_xoa_vattu_thuoctinh: async (axios, dulieu)=> axios.post('/web-quantri/thuoctinh/fn_xoa_vattu_thuoctinh',{
        dulieu:dulieu
    }),
    //dulieu: Array[{TTVT_ID:,VATTU_ID:,RTT_ID}]
    fn_them_vattu_thuoctinh: async (axios, dulieu)=> axios.post('/web-quantri/thuoctinh/fn_them_vattu_thuoctinh',{
        dulieu:dulieu
    }),

    fn_capnhat_vatu_thuoctinh: async (axios, data)=> axios.post('/web-quantri/thuoctinh/fn_capnhat_vatu_thuoctinh',data),

}