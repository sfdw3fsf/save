export default {
    KiemTraThamSoMucDich: (axios, maThamSo) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-ten-tham-so/${maThamSo}`),
    getDSQuyenNguoiDung: (axios, nguoiDungId) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-quyen-nd/${nguoiDungId}`),
    getDSKhoTheoNhanVien: (axios, nhanVienId) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-kho-nhan-vien/${nhanVienId}`),
    getDSKhoTheoNCC: (axios, nhanVienId) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-kho-nha-cung-cap/${nhanVienId}`),
    getDSThietBiTonKho: (axios, khoId) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-thietbi-tonkho/${khoId}`),
};
