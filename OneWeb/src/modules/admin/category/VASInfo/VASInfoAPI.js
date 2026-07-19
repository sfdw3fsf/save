export default{
    lay_danhsach_dichvuvt: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    lay_danhsach_hinhthuc_tc: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_HINHTHUC_TC'),
    lay_danhsach_trangthai_tb: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TRANGTHAI_TB'),
    lay_danhsach_kieu_dvgt: async (axios) => axios.post('/web-quantri/dichvu_giatang/ht_kieu_dvgt',{}),

    lay_danhsach_dvgt_theo_dichvu_vt: async (axios, dichvuvt_id) => axios.post('/web-quantri/dichvu_giatang/get_danhsach_dvgt_theo_dichvuvt',{
        dichvuvt_id: dichvuvt_id
    }),

    lay_danhsach_loaihinh_dvgt_dagan_chuagan: async (axios, dichvugt_id, dichvuvt_id, kieu) => axios.post('/web-quantri/dichvu_giatang/get_dsloaihinh_dvgt_daganchuagan',{
        dichvugt_id: dichvugt_id,
        kieu: kieu,
        dichvuvt_id: dichvuvt_id
    }),

    dvgt_update: async (axios, dichvugt_id, is_insert, data, lhtb_data) => axios.post('/web-quantri/dichvu_giatang/dvgt_update',{
        dichvugt_id: dichvugt_id,
        is_insert: is_insert,
        data: data,
        lhtb_data:lhtb_data
    }),

    dvgt_delete: async (axios, dichvugt_id) => axios.post('/web-quantri/dichvu_giatang/dvgt_delete',{
        dichvugt_id: dichvugt_id
    }),

    get_keys: async (axios, keyname) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',{
        keyname: keyname
    }),

    get_ds_kmtt: async (axios) => axios.post('web-quantri/dichvu_giatang/get_ds_kmtt',{}),
    
}