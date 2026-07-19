export default{
    lay_danhsach_quan: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_QUAN'),

    lay_danhsach_phuong_theo_quan: async (axios, quan_id) => axios.post('/web-quantri/danhmuc-chung/CSS_PHUONG',{
        ID:quan_id
    }),

    lay_danhsach_dacdiem: async (axios, quan_id, phuong_id) => axios.post('/web-quantri/dacdiem/sp_lay_ds_dacdiem',{
        phuong_id:phuong_id,
        quan_id: quan_id
    }),
    
    //nhompho_id 1: phố, 2: ấp, 3:khu
    lay_danhsach_pho_chuagan: async (axios, nhompho_id, dacdiem_id, quan_id, phuong_id) => axios.post('/web-quantri/dacdiem/sp_lay_ds_pho_chuagan',{
        nhompho_id: nhompho_id,
        dacdiem_id: dacdiem_id,
        quan_id: quan_id,
        phuong_id: phuong_id
    }),

    lay_danhsach_pho_dagan: async (axios, nhompho_id, dacdiem_id) => axios.post('/web-quantri/dacdiem/sp_lay_ds_pho_dagan',{
        nhompho_id: nhompho_id,
        dacdiem_id: dacdiem_id
    }),

    sp_xoa_dacdiem: async (axios, dacdiem_id) => axios.post('/web-quantri/dacdiem/sp_xoa_dacdiem',{
        dacdiem_id:dacdiem_id
    }),
    
    // {
    //     "is_insert": 1,
    //     "dacdiem_id": 364,
    //     "dacdiem": "test đặc điểm 1",
    //     "danhsach": "[{'pho_id':5678, 'phuong_id':419}]"
    // }
    sp_capnhat_dacdiem: async (axios, data) => axios.post('/web-quantri/dacdiem/sp_capnhat_dacdiem', data)
   
}