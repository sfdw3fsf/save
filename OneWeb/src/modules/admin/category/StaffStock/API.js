export default{
    
    tree_donvi: async (axios, data) => axios.post('/web-quantri/gankhonv/sp_tree_donvi', null),
    grv_nhanvien: async (axios, data) => axios.post('/web-quantri/gankhonv/sp_grv_nhanvien', data),
    kho_dagan: async (axios, data) => axios.post('/web-quantri/gankhonv/sp_grv_kho_dagan', data),
    kho_chuagan: async (axios, data) => axios.post('/web-quantri/gankhonv/sp_grv_kho_chuagan', data),
    quyen_xacnhan: async (axios, data) => axios.post('/web-quantri/gankhonv/sp_grv_quyen_xacnhan', data),
    gankho_nhanvien: async (axios, data) => axios.post('/web-quantri/gankhonv/sp_gankho_nhanvien', data),
    gokho_nhanvien: async (axios, data) => axios.post('/web-quantri/gankhonv/sp_gokho_nhanvien', data),
    upd_quyexn: async (axios, data) => axios.post('/web-quantri/gankhonv/sp_upd_quyexn', data),

    sp_loc_ma_nd_nhanvien_gankhonhanvien: async (axios, data) => axios.post('/web-quantri/gankhonv/sp_loc_ma_nd_nhanvien_gankhonhanvien', data),

  }
  