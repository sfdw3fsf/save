export default{
    get_loai_ct: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/ADMIN_LOAI_CT', data),
    get_ds_quyen: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DS_QUYEN', data),
    get_nhom_ham: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/ADMIN_NHOMHAM', data),
    get_ds_bang: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DS_BANG', data),
    get_nhom_menu: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/ADMIN_NHOM_MENU', data),
    get_nhom_bc: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/ADMIN_NHOM_BC', data),
    post_ds_menu_quyen: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_lay_ds_menu_quyen', data),
    post_sp_action_ds_quyen_menu: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_action_ds_quyen_menu', data),
    post_ds_ham_quyen: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_lay_ds_ham_quyen', data),
    post_sp_action_ds_quyen_ham: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_action_ds_quyen_ham', data),
    post_ds_bang_quyen: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_lay_ds_bang_quyen', data),
    post_sp_action_ds_quyen_bang: async (axios, data) => axios.post('/web-quantri/quanlyquyen/sp_action_ds_quyen_bang', data),
  }