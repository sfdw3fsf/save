export default {
  getDSKhieuNaiTheoMaKN: (axios, data) => axios.post('/web-gqkn/dx-giamtru-cuoc/chitiet-theo-ma-kn', data),
  getDSDichVuVT: (axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_lay_ds_dichvu_vt'),
  getDSLoaiHinhTB: (axios, data) => axios.post('/web-gqkn/xu-ly-khieu-nai/getDsLoaiHinhTB', { dichVuVTID: data }),
  getDanhBa: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu-danh-ba/ma-thue-bao?maTB=' + data),
  getTienTrinhKhieuNai: (axios, data) =>
    axios.get('/web-gqkn/TraCuuKhieuNaiChung/sp_lay_danhssach_tientrinh_khieunai?khieuNaiID=' + data),
  getGiamCuocKhieuNai: (axios, data) =>
    axios.get('/web-gqkn/TraCuuKhieuNaiChung/sp_lay_danhsach_giamcuoc_khieunai?khieuNaiID=' + data),
  getDSKhieuNaiTheoTrangThai: (axios, data) =>
    axios.get('/web-gqkn/TraCuuKhieuNaiChung/sp_lay_danhsach_khieunai_theo_trangthai?TTKNID=' + data),
  giaoPhieuGiamTruCuoc: (axios, data) => axios.post('web-gqkn/dx-giamtru-cuoc/giao-phieu', data),
  getLoaiPAGC: (axios) => axios.get('/web-cskh/api/danh-muc/ho-tro?loaiDanhMuc=LOAI_PAGC'),
  getKieuPAGC: (axios) => axios.get('/web-cskh/api/danh-muc/ho-tro?loaiDanhMuc=KIEU_PAGC'),
  ghiLai: (axios, data) => axios.post('/web-gqkn/de-xuat-giam-tru-cuoc/them-moi', data),
  capNhat: (axios, data) => axios.post('/web-gqkn/de-xuat-giam-tru-cuoc/cat-nhat', data),
  xoa: (axios, data) => axios.post('/web-gqkn/dx-giamtru-cuoc/xoa-phieu', data),
  khoaPhieu: (axios, data) => axios.post(`/web-gqkn/de-xuat-giam-tru-cuoc/khoa-phieu`, data),
  getThongTinKetQua: (axios, data) =>
    axios.get(
      `/web-gqkn/de-xuat-giam-tru-cuoc/ket-qua-xac-minh?khieuNaiID=${data.khieuNaiID}&phieuKNID=${data.phieuKNID}`
    ),
  getDSKhieuNaiTheoTrangThaiv2: (axios, data) => axios.post('/web-gqkn/dx-giamtru-cuoc/ds-theo-trangthai', data),
  ghilai_pheduyet: (axios, data) => axios.post('/web-gqkn/de-xuat-giam-tru-cuoc/ghilai_pheduyet', data),

  doChenNo: (axios, data) => axios.post('web-gqkn/dx-giamtru-cuoc/chen-no', data),
  getDSChenNo: (axios, data) =>
    axios.get('/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/danh-sach-thue-bao-chen-no', {
      params: data
    }),
  getTongNoTheoKyHoaDon: (axios, data) =>
    axios.get('/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-tong-no-thanh-toan-theo-ky-hoa-don', {
      params: data
    }),
  getPayment_Info_ForPay: (axios, data) =>
    axios.get('/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-thong-tin-thanh-toan-de-thanh-toan', {
      params: data
    }),
  getTTThanhhToanTheoDBHienTai: (axios, data) =>
    axios.get('/web-thuno/api/thu-no/lay-thanh-toan-theo-danh-ba-hien-tai/' + data),
  getDSGiamTruCuoc: (axios, data) => axios.post('web-gqkn/api/dx-giamtru-cuoc/ds-chen-no', data),
  getDSPADieuChinhHoaDon: async (axios) => axios.get('web-gqkn/dx-giamtru-cuoc/ds-dieuchinh-hoadon'),
  getDSPAXuLyDuLieu: async (axios) => axios.get('web-gqkn/dx-giamtru-cuoc/ds-xuly-dulieu'),
  ThemMoiGiamTruCuoc: (axios, data) => axios.post('web-gqkn/dx-giamtru-cuoc/them-moi', data),
  huyGiaoPhieuKhieuNai: (axios, data) => axios.post('web-gqkn/dx-giamtru-cuoc/huy-giao', data)
}
