export default{
    getDanhSachDichVuVT: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data),
    getDanhSach_LoaiHopDong_PhuLuc: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD', data),
    getDanhSach_DonVi: (axios, data) => axios.get('/web-tracuu/port/ds_dai_vt?loaiDVId=3', data),
    LayTongHDTBChuaHoanThanh: (axios, data) => axios.post('/web-tracuu/tracuu_dieuhanh_tc/lay_tong_hdtb_chua_ht', data),
    LayDanhSachHDTBChuaHoanThanh: (axios, data) => axios.post('/web-tracuu/tracuu_dieuhanh_tc/lay_ds_hdtb_chua_ht', data),
    LayDanhSachDieuHanhThiCong: (axios, data) => axios.post('/web-tracuu/tracuu_dieuhanh_tc/lay_ds_dieuhanh_tc', data),

  }