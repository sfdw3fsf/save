export default {
    //Lấy danh mục
    getDanhmuc: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/danhmuc', data),
    getUserNeo: (axios) => axios.get('/web-thuno/api/thu-no/common/user-neo'),
    //web-hopdong/tracuu-tt-thuebao/sp_lay_tt_nam
    //web-hopdong/phan-cong-cong-viec/sp_nap_ds_nhanvien_ql_congviec
    sp_nap_ds_nhanvien_ql_congviec: (axios, data) => axios.post('/web-hopdong/phan-cong-cong-viec/sp_nap_ds_nhanvien_ql_congviec', data),
    //web-hopdong/phan-cong-cong-viec/sp_lay_ds_nhanvien_theo_donvi_id
    sp_lay_ds_nhanvien_theo_donvi_id: (axios, data) => axios.post('/web-hopdong/phan-cong-cong-viec/sp_lay_ds_nhanvien_theo_donvi_id', data),
    //web-hopdong/phan-cong-cong-viec/lay_danhsach_cv_phancong
    lay_danhsach_cv_phancong: (axios, data) => axios.post('/web-hopdong/phan-cong-cong-viec/lay_danhsach_cv_phancong', data),    
    //web-hopdong/phan-cong-cong-viec/sp_capnhat_ql_congviec
    sp_capnhat_ql_congviec: (axios, data) => axios.post('/web-hopdong/phan-cong-cong-viec/sp_capnhat_ql_congviec', data),
    //web-hopdong/phan-cong-cong-viec/sp_lichsu_nhanvien_nghi
    sp_lichsu_nhanvien_nghi: (axios, data) => axios.post('/web-hopdong/phan-cong-cong-viec/sp_lichsu_nhanvien_nghi', data),
    //web-hopdong/phan-cong-cong-viec/sp_gan_go_nhanvien_ql_congviec
    sp_gan_go_nhanvien_ql_congviec: (axios, data) => axios.post('/web-hopdong/phan-cong-cong-viec/sp_gan_go_nhanvien_ql_congviec', data),
    //phan-cong-cong-viec/sp_xoa_ql_congviec
    sp_xoa_ql_congviec: (axios, data) => axios.post('/web-hopdong/phan-cong-cong-viec/sp_xoa_ql_congviec', data),
    //"web-hopdong/phan-cong-cong-viec/kiemtra_pc_congviec
    kiemtra_pc_congviec: (axios, data) => axios.post('/web-hopdong/phan-cong-cong-viec/kiemtra_pc_congviec', data),


}
  