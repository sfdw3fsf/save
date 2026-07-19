export default{
    get_CaTruc: (axios, data) => axios.post('/web-quantri/danhmuc/lay_ds_danhmuc_chung', data),
    get_NhanPhieuCauHinh: (axios, data) => axios.post('/web-quantri/danhmuc/lay_ds_danhmuc_chung', data),
    get_LoaiMang: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', data),
    get_TrangThai: (axios, data) => axios.post('/web-quantri/danhmuc/lay_ds_danhmuc_chung', data),
    get_NguoiThucHien: (axios, data) => axios.post('/web-quantri/danhmuc/lay_ds_nhanvien_donvi', data),
    get_NhanvienNhanPhieu: (axios, data) => axios.get('/web-thicong/giao_phieu_cau_hinh_tu_dong/lay_ds_nhanvien_nhanphieu', data),
    CapNhat: (axios, data) => axios.post('/web-thicong/giao_phieu_cau_hinh_tu_dong/capnhat_nhanvien_nhanphieu', data),
    LayDS_CauHinh_CaNhan: (axios, data) => axios.post('/web-thicong/giao_phieu_cau_hinh_tu_dong/lay_ds_phieu_ch_omc', data),
    LayDS: (axios, data) => axios.post('web-thicong/giao_phieu_cau_hinh_tu_dong/lay_ds_thongke_phieu_ch_omc', data),
    Lay_TT_ND: (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2', data),
    update_trangthai_phieu_omc: (axios, data) => axios.post('/web-thicong/giao_phieu_cau_hinh_tu_dong/update_trangthai_phieu_omc', data),
    
  }