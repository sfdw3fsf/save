export default {
    lay_ds_chucdanh: async (axios) => axios.get('/web-quantri/vitrinhanvien/sp_lay_ds_chucdanh'),

    lay_ds_loaild: async (axios) => axios.get('/web-quantri/danhmuc-chung/HRM_LOAI_LD'),

    // {
    //     "flag": 0,
    //     "id": 1000,
    //     "kieu": 3,
    //     "json_data":""
    // }
    // flag:0-Chức danh, 1: Vị trí
    // kieu: 3:xoá, 1: insert(thêm json_data), 2: update (thêm json_data)
    fn_frmchucdanh_xuly: async (axios, data) => axios.post('/web-quantri/vitrinhanvien/fn_frmchucdanh_xuly', data),

    // lay_ds_vitri: async (axios) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md', {
    //     list_of_cols: "*",
    //     order: "",
    //     schema: "hrm",
    //     table_name: "vitri",
    //     where: ""
    // }),

    lay_ds_vitri: async (axios) => axios.post('/web-hopdong/laydulieu/sp_tt_vitri', {
        p_param: "",
        p_type:1
    }),

    

    

    
    
}