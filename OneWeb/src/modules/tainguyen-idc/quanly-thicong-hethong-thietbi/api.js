export default {
  getDanhSachCongViec: (axios, data) =>
    axios.post('/web-ecms/idc/danhsach-congviec/hethong-thietbi', {
      loaicvId: data.maCv || data.loaicvId,
      donviId: data.donviId,
      idcId: data.idcId,
      nhanvienId: data.nhanvienId,
      phanloai: data.phanloai,
      trangthaiId: data.trangthaiId,
      ycBatdau: data.ycBatdau,
      ycKetthuc: data.ycKetthuc,
      ychtBatdau: data.ychtBatdau,
      ychtKetthuc: data.ychtKetthuc,
      khachhang: data.khachhang,
      congviec: data.congviec,
      tenHeThong: data.tenHeThong
    }),

  // Common dropdown data API
  getDanhMucLoaiCongViec: (axios, data) =>
    axios.post('/web-ecms/idc/danh-muc/common', {
      loaiDanhMuc: data.loaiDanhMuc,
      thamSo1: data.thamSo1 ?? null,
      thamSo2: data.thamSo2 ?? null
    }),

  // IDC dropdown API
  getDanhMucIDC: (axios, data) => 
    axios.post('/web-ecms/idc/danh-muc/common', {
      loaiDanhMuc: 'IDC-DS-IDC',
      thamSo1: data.donviId || data.thamSo1,
      thamSo2: null
    }),

  // Department dropdown API
  getDanhMucDonVi: (axios, data) => 
    axios.post('/web-ecms/idc/danh-muc/common', {
      loaiDanhMuc: 'IDC-DONVI',
      thamSo1: data.thamSo1 ?? null,
      thamSo2: null
    }),

  // Employee dropdown API
  getDanhMucNhanVien: (axios, data) => 
    axios.post('/web-ecms/idc/danh-muc/common', {
      loaiDanhMuc: 'NHANVIEN-DONVI-IDC',
      thamSo1: data.donviId,
      thamSo2: null
    }),

  // Get IDC list by employee ID
  getDanhMucIDCByNhanVien: (axios, data) => 
    axios.post('/web-ecms/idc/danh-muc/common', {
      loaiDanhMuc: 'GET-IDC-BY-NHANVIENID',
      thamSo1: data.nhanvienId,
      thamSo2: null
    }),

  // Construction status dropdown API
  getDanhMucTrangThaiThiCong: (axios, data) => 
    axios.post('/web-ecms/idc/danh-muc/common', {
      loaiDanhMuc: 'IDC-TRANGTHAI-THICONG',
      thamSo1: data.thamSo1 ?? null,
      thamSo2: null
    })
}
