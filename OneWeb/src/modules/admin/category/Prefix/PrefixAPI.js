export default {
    lay_danhsach_loai_so: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_SO'),
    lay_danhsach_tongdai: async (axios) => axios.get('/web-tracuu/khoso/ds_host'),
    lay_danhsach_nhacungcap: async (axios) => axios.get('/web-quantri/prefix/sp_nha_cung_cap'),

    lay_danhsach_dichvu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    lay_danhsach_prefix: async (axios, loaiso_id, dichvuvt_id) => axios.post('/web-quantri/prefix/sp_ds_prefix',{
        loaiso_id: loaiso_id,
        dichvuvt_id: dichvuvt_id
    }),
    sp_prefix_ds_loaihinh: async (axios, dichvuvt_id, prefix, kieu) => axios.post('/web-quantri/prefix/sp_prefix_ds_loaihinh',{
        dichvuvt_id: dichvuvt_id,
        prefix: prefix,
        kieu: kieu
    }),
    sp_prefix_ds_khoso: async (axios, loaiso_id, dichvuvt_id, prefix) => axios.post('/web-quantri/prefix/sp_prefix_ds_khoso',{
        loaiso_id: loaiso_id,
        dichvuvt_id: dichvuvt_id,
        prefix: prefix
    }),

    //xoá
    sp_prefix_delete: async (axios, prefix, donvi_id) => axios.post('/web-quantri/prefix/sp_prefix_delete',{
        prefix: prefix,
        donvi_id: donvi_id
    }),
    //1 là thêm mới, 0 là update
    //"js_lhtb": "[{\"PREFIX\": \"1234\", \"LOAITB_ID\": 1}]",
    //"js_prefix": "[{\"PREFIX\": \"1234\", \"LOAISO_ID\": 1, \"DONVI_ID\": 399, \"LENGTH\": 7, \"SUFFIX\": null, GHICHU: \"1234\"}]"
    sp_prefix_update: async (axios, ds_lhtb, prefix, is_insert) => axios.post('/web-quantri/prefix/sp_prefix_update',{
        is_insert: is_insert,                        
        js_lhtb: ds_lhtb,
        js_prefix: prefix
    }),

    sp_tk_prefix: async (axios, prefix) => axios.post('/web-quantri/prefix/sp_tk_prefix',{
        prefix: prefix
    }),

    sp_prefix_khoso_delete: async (axios, loaiso_id, dichvuvt_id, js_data) => axios.post('/web-quantri/prefix/sp_prefix_khoso_delete',{
        loaiso_id:loaiso_id,
        dichvuvt_id:dichvuvt_id,
        js_data:js_data
    }),

    

}