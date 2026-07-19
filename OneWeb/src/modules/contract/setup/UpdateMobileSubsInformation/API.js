export default{

    sp_chuyenmang_pi_danhmuc:async (axios, data) => axios.post('/web-hopdong/capnhat-ttdd-chuyenmang-den-pi/sp_chuyenmang_pi_danhmuc',data),
    lay_ds_nhom_nn_pi:async (axios) => axios.get('/web-hopdong/capnhat-ttdd-chuyenmang-den-pi/lay_ds_nhom_nn_pi'),
    lay_ds_trangthai_pi:async (axios) => axios.get('/web-hopdong/capnhat-ttdd-chuyenmang-den-pi/lay_ds_trangthai_pi'),
    lay_ds_nguyennhan_pi:async (axios,data) => axios.get('/web-hopdong/capnhat-ttdd-chuyenmang-den-pi/lay_ds_nguyennhan_pi?vnhomnn_id='+data),
    lay_ds_thuebao_pi:async (axios,data) => axios.get('/web-hopdong/capnhat-ttdd-chuyenmang-den-pi/lay_ds_thuebao_pi?vnhanvien_id='+data),
    post_get_keys: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',data),
    sp_capnhat_hd_chuyenmang_pi:async (axios, data) => axios.post('/web-hopdong/capnhat-ttdd-chuyenmang-den-pi/sp_capnhat_hd_chuyenmang_pi',data),
    sp_chuyenmang_pi_danhmuc:async (axios, data) => axios.post('/web-hopdong/capnhat-ttdd-chuyenmang-den-pi/sp_chuyenmang_pi_danhmuc',data),
    fn_lay_tennv_theo_nhanvien_id:async (axios, data) => axios.get('/web-hopdong/capnhat-ttdd-chuyenmang-den-pi/fn_lay_tennv_theo_nhanvien_id?p_nhanvien_id='+data),
    view_diachi:async (axios, data) => axios.get('/web-hopdong/capnhat-ttdd-chuyenmang-den-pi/view_diachi?param='+data),
}