export default {
    lay_danhsach_nhomnd: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_NHOM_ND'),
    lay_danhsach_tinh: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TINH'),
    lay_danhsach_quan_theo_tinh: async (axios, tinh_id) => axios.get('/web-quantri/danhmuc/diaban/quan?tinh_id='+tinh_id),
    lay_danhsach_donvicha: async (axios) => axios.post('/web-quantri/nguoidung/sp_cbo_donvi_dichvu_cha',{}),
    
    lay_danhsach_nguoidung_donvi: async (axios, donvi_id) => axios.post('/web-quantri/nguoidung/sp_ds_nguoidung_donvi',{
        donvi_id:donvi_id
    }),

    sp_ds_nguoidung_thungan: async (axios, nguoidung_id, donvi_id, type, checked) => axios.post('/web-quantri/nguoidung/sp_ds_nguoidung_thungan',{
        type:type,
        checked:checked,
        donvi_id:donvi_id,
        nguoidung_id:nguoidung_id
    }),

    lay_danhsach_donvi: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DONVI'),

    lay_danhsach_donvi_treeview: async (axios, donvicha_id) => axios.post('/web-quantri/donvi/SP_GIAOSIM_GET_TREEVIEW_NEW',{
        p_donvicha_id: donvicha_id
    }),

    sp_get_nhanvien_donvi: async (axios, donvi_id, kieu) => axios.post('/web-quantri/quanlytinnhanv2/sp_get_nhanvien_donvi',{
        donvi_id: donvi_id,
        kieu: kieu
    }),
    //js_nguoidung: "[{\"TEN_ND\": \"test1\",\"MA_ND\": \"test1\",\"MATKHAU\": \"mxja29nhkKcSvh0+nuH6ew==\",\"TRANGTHAI\": 1,\"QUANTRI\": 1,\"GHICHU\": \"test1\",\"NHOM_ND_ID\": 0,\"NHANVIEN_ID\": 452,\"USER_NEO\": \"test1\", \"USER_VASC\": \"hpgdhsx\", \"USER_VISA\": \"hpg_webservice_gd\", \"USER_VTN \": \"admin_hpg\",\"USER_MAP\": \"\",\"PASS_VASC\": \"eiplVcSsGpIYLEgnVcD5qw==\",\"DIEMGIAODICH_VASC\": \"HPG0000\",\"PASS_VISA\": \"8gXCpxehr2A=\", \"PASS_VTN \": \"QPH3f+al4eqCrPmdn5xSxw==\",\"USER_HDDT \": \"hpservice\",\"PASS_HDDT\": \"1234567\",\"USER_HDDT_TD\": \"webservice_hpg\",\"PASS_HDDT_TD\": \"Webservice123@\",\"QUAN_ID\": 0}]",
    //js_nguoidung_tn:"[{\"NGUOIDUNG_ID\": 5932,\"NGUOIDUNG_TN_ID\": 2447}]"
    sp_nguoidung_update: async (axios, js_nguoidung, js_nguoidung_tn, dtvnp2, is_insert) => axios.post('/web-quantri/nguoidung/sp_nguoidung_update',{
        js_nguoidung:js_nguoidung,
        js_nguoidung_tn:js_nguoidung_tn,
        dtvnp2:dtvnp2,
        is_insert:is_insert
    }),

    fn_lay_quyen_login: async (axios, ma_nd, kieu) => axios.post('/web-quantri/nguoidung/fn_lay_quyen_login',{
        ma_nd: ma_nd,
        kieu: kieu
    }),
    
    //reset mật khẩu
    sp_nguoidung_resetmk: async (axios, data) => axios.post('/web-quantri/nguoidung/sp_nguoidung_resetmk', data),

    //form gán người dùng VNP
    sp_nguoidung_vnp: async (axios, nguoidung_id) => axios.post('/web-quantri/nguoidung/sp_nguoidung_vnp', {
        nguoidung_id:nguoidung_id
    }),

    //https://cntt.vnpt.vn/browse/BSS-21943
    //Fix ATBM theo api mới link trên thay thế hàm map_id
    lay_nhanvien_id_theo_mand: async (axios, ma_nd) => axios.post('/web-quantri/huonggiao/lay_nhanvien_id_theo_mand', {
        ma_nd: ma_nd
    }),
    lay_nguoidung_id_theo_mand: async (axios, ma_nd) => axios.post('/web-quantri/huonggiao/lay_nguoidung_id_theo_mand', {
        ma_nd: ma_nd
    }),

    lay_donvi_id_theo_nhanvien_id: async (axios, nhanvien_id) => axios.post('/web-quantri/huonggiao/lay_donvi_id_theo_nhanvien_id', {
       nhanvien_id: nhanvien_id
    }),

    sp_kiemtratontaind: async (axios, ma_nd) => axios.get('/web-quantri/nguoidung/sp_kiemtratontaind?ma_nd='+ma_nd),
    
    //https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=803715326&range=E198
    //Dùng thủ công, sau check thử lại hàm gộp 
    //https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=751825192&range=H316

    sp_load_treeview: async (axios) => axios.get('/web-quantri/donvi/sp_load_treeview'),
    
    sp_lay_donvi_id_theo_ma_nd: async (axios, ma_nd) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_lay_donvi_id_theo_ma_nd', {
        v_ma_nd:ma_nd
    }),
    sp_lay_donvi_theo_id: async (axios, donvi_id) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_lay_donvi_theo_id', {
        v_donvi_id: donvi_id
    }),

    lay_nv_theo_ndid: async (axios, data) => axios.post('/web-tracuu/tracuu/lay_nv_theo_ndid', data),

    gan_useripcc_v2: (axios, data) => axios.post('/web-tracuu/tracuu/gan_useripcc_v2', data),

    xoa_useripcc_v2: (axios, data) => axios.get(`/web-tracuu/tracuu/xoa_agent_v2?p_user_ipcc=${data.p_user_ipcc}`, data),

    lay_ds_agent_v2: (axios, data) => axios.get(`/web-tracuu/tracuu/lay_ds_agent_v2?p_ma_nd=${data.p_ma_nd}`, data),
    lay_ds_agent: (axios, data) => axios.get(`/web-tracuu/tracuu/lay_ds_agent?p_ma_nv=${data.p_ma_nv}`, data),
    lay_ten_nd_by_ndid: (axios, data) => axios.post(`/web-tracuu/tracuu/lay_ten_nd_by_ndid?p_ma_nd=${data.p_ma_nd}`, data),

}