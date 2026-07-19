export default {
    getDSDonVi: (axios, data) => axios.post(`/web-qlvt/api/quyet-toan-vat-tu/don-vi-theo-loai-dich-vu`,data),
    getDSKhoTheoNV: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-vat-tu/ds-kho-theo-nhanvien/${data.nhanVienId}/${data.donViId}`,),
    getDSKhoKhachHang: (axios, data) => axios.get(`/web-qlvt/api/quyet-toan-vat-tu/kho-theo-nhan-vien?nhan-vien-id=${data.nhanVienId}`,),
    getDSDichVuVienThong: (axios, data) => axios.get(`/web-qlvt/api/quyet-toan-vat-tu/dich-vu-vat-tu`,),

    getDSVatTuTimKiem: (axios, data) => axios.get(`/web-qlvt/api/tong-hop-thu-hoi/ds-vat-tu-tim-kiem/${data.vatTuId}`),
    getDanhMucHopDong: (axios, data) => axios.get(`/web-qlvt/api/quyet-toan-vat-tu/loai-hop-dong`),
    getDSThueBaoThuHoi: (axios, data) => axios.post(`/web-qlvt/api/tong-hop-thu-hoi/ds-thue-bao-thu-hoi-ttkd`,data),
    getDSVatTuThuHoi: (axios, data) => axios.post(`/web-qlvt/api/tong-hop-thu-hoi/ds-thue-bao-thu-hoi-vt`,data),

    getDSLichSuVatTuThuHoi: (axios, data) => axios.post(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-phieu-vattu-hop-dong-bao-hong`,data),
    ThemMoiPhieuVatTu: (axios, data) => axios.post(`/web-qlvt/api/quyet-toan-vat-tu/phieu-vat-tu`,data),
    KiemTraTraPhieu: (axios, data) => axios.post(`/web-qlvt/api/quyet-toan-vat-tu/kiem-tra-phieu-tra`,data),
    TraPhieuQuyetToanVatTu: (axios, data) => axios.post(`/web-qlvt/api/tong-hop-thu-hoi/tra-phieu-quyet-toan-vt`,data),

    HuyPhieuQuyetToan: (axios, data) => axios.post(`/web-qlvt/api/quyet-toan-vat-tu/huy-tra-phieu-quyet-toan-vat-tu`,data),
    KiemTraVatTuNhapKho: (axios, data) => axios.post(`/web-qlvt/api/tong-hop-thu-hoi/kiem-tra-vt-nhap-kho`,data),
    getDSMucDichBaoHong: (axios, data) => axios.get(`/web-qlvt/api/quyet-toan-vat-tu/lay-muc-dich-xuat?ma-muc-dich=${data.maMucDich}`),
    CapMaVatTuPhieu_V2: (axios, data) => axios.post(`/web-qlvt/api/quyet-toan-vat-tu-cong-trinh/cap-ma-vat-tu-pvt`,data),

    CapNhatMaPhieuVatTu: (axios, data) => axios.post(`/web-qlvt/api/quyet-toan-vat-tu-cong-trinh/cap-nhat-ma-pvt`,data),
    getDSVatTuDanhMuc: (axios, data) => axios.get(`/web-qlvt/api/tong-hop-thu-hoi/ds-vat-tu-danh-muc`),
    getDSKhoDonVi: (axios, data) => axios.get(`/web-qlvt/api/quyet-toan-vat-tu/danh-sach-kho-don-vi?nhan-vien-id=${data.nhanVienId}&don-vi-id=${data.donViId}`),

    LAY_DS_THUHOI_QT: (axios, data) => axios.post(`/web-qlvt/api/tong-hop-thu-hoi/ds-thue-bao-thu-hoi-vt-qt`,data),
    DS_LECH_PHIEUVT_QUYETTOAN: (axios, data) => axios.post(`/web-qlvt/api/tong-hop-thu-hoi/ds-lech-phieu-vt-qt`,data),
    LAY_DS_DENGHI_HT: (axios, data) => axios.get(`/web-qlvt/api/tong-hop-thu-hoi/ds-de-nghi-ht/${data.donViId}/${data.loaiPhieuId}`,),
    CHECK_IS_EXIST: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-ten-tham-so/${data.maThamSo}`),

    KT_SL_PHIEUVT_QUYETTOAN: (axios, data) => axios.post(`/web-qlvt/api/tong-hop-thu-hoi/kiem-tra-soluong-phieu-vt`,data),
    getTSMacDinh: (axios, tsmd) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/lay-ds-thamso-md-mats/${tsmd}`),
  }