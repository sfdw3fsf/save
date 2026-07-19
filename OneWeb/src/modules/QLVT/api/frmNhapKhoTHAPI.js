export default {
    KiemTraMaThamSo: (axios, data) => axios.get(`/web-qlvt/api/nhap-kho-th/kiem-tra-ma-tham-so?maTs=${data.maTS}`),
    getDSThuHoiVatTu: (axios, data) => axios.get(`/web-qlvt/api/nhap-kho-th/ds-yeu-cau-thu-hoi-vt`),
    getDSLoHang: (axios, data) => axios.get(`/web-qlvt/api/nhap-kho-th/ds-lo-hang?suaDonGia=${data.suaDonGia}`),
    KiemTraHienThiIDVT: (axios, data) => axios.get(`/web-qlvt/api/nhap-kho-th/kiem-tra-hien-thi-theo-vt/${data.vatTuId}`),

    KiemTraSerialThuHoi: (axios, data) => axios.post(`/web-qlvt/api/quyet-toan-vat-tu-cong-trinh/kiem-tra-serial-thu-hoi`, data),
    KiemTraKhoThuHoi: (axios, data) => axios.post(`/web-qlvt/api/quyet-toan-vat-tu-cong-trinh/kiem-tra-kho-thu-hoi`, data),
    TaoPhieuChungTuV4_HCM: (axios, data) => axios.post(`/web-qlvt/api/nhap-kho-th/tao-phieu-chung-tu-v4`, data),
    TaoPhieuChungTuV2: (axios, data) => axios.post(`/web-qlvt/api/quyet-toan-vat-tu/tao-phieu-chung-tu-v2`, data),

    TaoPhieuCTKemGiaoPhieu: (axios, data) => axios.post(`/web-qlvt/api/nhap-kho-th/tao-phieu-chung-tu-giao-phieu`, data),
    QuyetToanThuHoiThueBao: (axios, data) => axios.post(`/web-qlvt/api/nhap-kho-th/quyet-toan-thu-hoi-tbao`, data),
    XoaChungTuTheoIDChungTu: (axios, data) => axios.delete(`/web-qlvt/api/nhap-xuat-vt/xoa-chung-tu-theo-id?chung-tu-id=${data.chungTuId}`),
    XoaPhieuVatTu: (axios, data) => axios.post(`/web-qlvt/api/quyet-toan-vat-tu/xoa-phieu-vat-tu`, data),

    XoaTatCaChiTietChungTu: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/xoa-chi-tiet-chung-tu?ct-chitiet-id=${data.chungTuId}`, data),
    getDSKieuPhieu: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-kieu-phieu/${data.loaiCtId}`),
    KiemTraSLThietBiTrongKho: (axios, data) => axios.get(`/web-qlvt/api/nhap-kho-th/kiem-tra-tbi-trong-kho-gan?serial=${data.serial}`),
    getDSPhieuVatTu: (axios, data) => axios.post(`/web-qlvt/api/quyet-toan-vat-tu/phieu-vat-tu`, data),
    getDSLoaiKho: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-loai-kho-td/${data.mucDichId}`),
    getDSMaKhoTapDoan: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho/danh-sach-vat-tu/${data.loaiKhoTdId}`),

    nhapKhoThuHoi: (axios, data) => axios.post(`/web-qlvt/api/nhap-kho-th/nhapkho_thuhoi`, data),
    nhapKhoThuHoi_v2: (axios, data) => axios.post(`/web-qlvt/api/nhap-kho-th/nhapkho_thuhoi_v2`, data),

}

