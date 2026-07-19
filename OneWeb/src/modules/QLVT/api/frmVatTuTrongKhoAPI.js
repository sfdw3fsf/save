export default {
    getDSQuyenND: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-quyen-nd/${data.nguoiDungId}`,),
    KiemTraThamSoMucDich: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-ten-tham-so/${data.maThamSo}`,),
    getDSKhoGanTheoNV: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-kho-nhan-vien/${data.nhanVienId}`,),
    getDSKhoNhaCC: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-kho-nha-cung-cap/${data.nhanVienId}`,),

    getDSThietBiTonKho: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-thietbi-tonkho/${data.khoId}`,),
    getDSHangSanXuat: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-hangsx/`,),
    getDSLinhVuc: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-linhvuc`,),
    KiemTraTrangThaiVT: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-ton-kho-user/kiem-tra-trang-thai-vt`,data),

    getDSVatTuTonKho_V3: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-vattu-ton-kho-v3`,data),
    getDSVatTuTonKho_V2: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-vattu-ton-kho`,data),
    getDSTonKhoNhaCC: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-vattu-ton-kho`,data),
    getDSLoaiKho: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-loai-khotd`,),

    getDSKhoTapDoan: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-khotd`,),
    getDSDangKy: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-dangky-theo-dk`,data),
    getDSVatTuDK: (axios, data) => axios.get (`/web-qlvt/api/tra-cuu-ton-kho-user/ds-da-dangky/${data.dangKyId}`,),
    XoaDuLieuSLDuyetVT: (axios, data) => axios.put(`/web-qlvt/api/tra-cuu-ton-kho-user/xoa-soluong-duyet-vattu/${data.dangKyId}`,),

    getDSVatTuDangKy: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-vattu-thdk/${data.vatTuDkId}`,),
    CapNhatVatTuDaDangKy: (axios, data) => axios.put(`/web-qlvt/api/tra-cuu-ton-kho-user/vattu-da-dang-ky/${data.soLuongDuyet}/${data.vatTuDkId}`,),
    CapNhatVatTuDKKhiXoa: (axios, data) => axios.put(`/web-qlvt/api/tra-cuu-ton-kho-user/vattu-da-dang-ky-khi-xoa/${data.vatTuDkId}`,),
    getDSDangKyTheoDKTT: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-dangky-theo-dktt`,data),

    CapNhatTraPhieu: (axios, data) => axios.put(`/web-qlvt/api/tra-cuu-ton-kho-user/vattu-dang-ky-tra-phieu/${data.dangKyId}`,),
    getDSChuaHoanTamUng: (axios, data) => axios.get (`/web-qlvt/api/tra-cuu-ton-kho-user/ds-vttb-chua-hoan-tamung/${data.khoId}/${data.thietBiId}`,),
    getDSDongThietBi: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-dong-tbi`,data),
    getDSHeThongVatTu: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-he-thong/${data.linhVucId}`,),
    kiemTraTrangThaiVatTu: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-ton-kho-user/kiem-tra-trang-thai-vt`,data),
}
