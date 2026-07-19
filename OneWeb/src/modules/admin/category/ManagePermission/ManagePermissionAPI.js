export default{
    lay_danhsach_loai_ct: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_LOAI_CT'),
    lay_danhsach_quyen: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DS_QUYEN'),
    lay_danhsach_nhom_menu: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_NHOM_MENU'),
    lay_danhsach_nhom_baocao: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_NHOM_BC'),
    lay_danhsach_nhom_ham: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_NHOMHAM'),
    lay_danhsach_bang: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DS_BANG'),

    // Tab báo cáo (Bỏ hàm này dùng hàm dưới)
    sp_ds_baocao_phanquyen: async (axios, nhom_bc_id, quyen_id, type) => axios.post('/web-quantri/quanlyquyen/sp_ds_baocao_phanquyen',{
        vnhom_bc_id: nhom_bc_id,
        vquyen_id: quyen_id,
        type: type
    }),
    sp_lay_ds_baocao_quyen: async (axios, nhom_bc_id, quyen_id) => axios.post('/web-quantri/quanlyquyen/sp_lay_ds_baocao_quyen',{
        p_quyen_id: quyen_id,
        p_nhom_bc_id: nhom_bc_id
    }),
    //Sử dụng tạm tham chiếu UR1.2.026 trung với task hiện tại
    sp_action_ds_quyen_baocao: async (axios, nhom_bc_id, baocao_id_arr, quyen_id, kieu) => axios.post('/web-quantri/quanlyquyen/sp_action_ds_quyen_baocao',{
        p_baocao_id_arr: baocao_id_arr,
        p_kieu: kieu,
        p_nhom_bc_id: nhom_bc_id,
        p_quyen_id: quyen_id
    }),
    
    //không dùng đang không đúng
    sp_phanquyen_baocao_update: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_phanquyen_baocao_update',{
        js_data: data
    }),
    sp_phanquyen_baocao_delete: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_phanquyen_baocao_delete',{
        js_data: data
    }),

    
    


    // Tab quyền menu (Bỏ hàm này)
    sp_ds_menu_phanquyen: async (axios, nhom_menu_id, quyen_id, type) => axios.post('/web-quantri/quanlyquyen/sp_ds_menu_phanquyen',{
        vnhom_menu_id: nhom_menu_id,
        vquyen_id: quyen_id,
        type: type
    }),

    sp_lay_ds_menu_quyen: async (axios, nhom_menu_id, quyen_id) => axios.post('/web-quantri/quanlyquyen/sp_lay_ds_menu_quyen',{
        p_quyen_id: quyen_id,
        p_nhom_menu_id: nhom_menu_id
    }),
    //Sử dụng tạm tham chiếu UR1.2.026 trung với task hiện tại
    sp_action_ds_quyen_menu: async (axios, nhom_menu_id, menu_id_arr, quyen_id, kieu) => axios.post('/web-quantri/quanlyquyen/sp_action_ds_quyen_menu',{
        p_kieu: kieu,
        p_menu_id_arr: menu_id_arr,
        p_nhom_menu_id: nhom_menu_id,
        p_quyen_id: quyen_id
    }),
    // không dùng
    // sp_phanquyen_menu_update: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_phanquyen_menu_update',{
    //     js_data: data
    // }),
    // sp_phanquyen_menu_delete: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_phanquyen_menu_delete',{
    //     js_data: data
    // }),

    // Tab quyền gọi hàm(bỏ hàm này dùng hàm sp_lay_ds_ham_quyen)
    sp_ds_ham_phanquyen: async (axios, nhomham_id, quyen_id, type) => axios.post('/web-quantri/quanlyquyen/sp_ds_ham_phanquyen',{
        vnhomham_id: nhomham_id,
        vquyen_id: quyen_id,
        type: type
    }),
    
    sp_lay_ds_ham_quyen: async (axios, nhomham_id, quyen_id) => axios.post('/web-quantri/quanlyquyen/sp_lay_ds_ham_quyen',{
        p_quyen_id: quyen_id,
        p_nhom_ham_id: nhomham_id
    }),
    
    //Sử dụng tạm tham chiếu UR1.2.026 trung với task hiện tại
    sp_action_ds_quyen_ham: async (axios, nhom_ham_id, ham_id_arr, quyen_id, kieu) => axios.post('/web-quantri/quanlyquyen/sp_action_ds_quyen_ham',{
        p_ham_id_arr: ham_id_arr,
        p_kieu: kieu,
        p_nhom_ham_id: nhom_ham_id,
        p_quyen_id: quyen_id
    }),

    // không dùng
    // sp_phanquyen_ham_update: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_phanquyen_ham_update',{
    //     js_data: data
    // }),
    // sp_phanquyen_ham_delete: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_phanquyen_ham_delete',{
    //     js_data: data
    // }),

    
    // Tab quyền gọi hàm
    //Sử dụng tạm tham chiếu UR1.2.026 trung với task hiện tại
    sp_lay_ds_bang_quyen: async (axios, tenbang, quyen_id) => axios.post('/web-quantri/quanlyquyen/sp_lay_ds_bang_quyen',{
        p_quyen_id: quyen_id,
        p_ten_bang: tenbang
    }),

    sp_action_ds_quyen_bang: async (axios, tenbang, string_arr_id_chon, quyen_id, kieu) => axios.post('/web-quantri/quanlyquyen/sp_action_ds_quyen_bang',{
        p_kieu: kieu, 
        p_id_chon: string_arr_id_chon,
        p_quyen_id: quyen_id,
        p_ten_bang: tenbang
    }),

    // không dùng
    // sp_ds_tudien_phanquyen: async (axios, tenbang, quyen_id, type) => axios.post('/web-quantri/quanlyquyen/sp_ds_tudien_phanquyen',{
    //     vtenbang: tenbang,
    //     vquyen_id: quyen_id,
    //     type: type
    // }),

    


}