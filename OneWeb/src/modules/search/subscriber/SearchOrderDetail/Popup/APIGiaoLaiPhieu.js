export default{
    lay_tt_don_hang: async (axios, data) => axios.get('/web-ccdv/tien_trinh_bao_hong/lay_thongtin_donhang_v2/'+data),
    huy_don_hang:  (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/fn_huydonhang_thongtindh', data),
    giao_phieu_tc:  (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/thanhtoan_donhang', data),
    lay_mo_ta_sp: async (axios, data) => axios.get('/web-ccdv/tien_trinh_bao_hong/laythongtinsp_v2/'+data),

    // popup ds đơn hàng
    lay_ds_don_hang: async (axios, data) => axios.post('/web-hopdong/danhsach-donhang/laythongtintracuudonhang', data),
}