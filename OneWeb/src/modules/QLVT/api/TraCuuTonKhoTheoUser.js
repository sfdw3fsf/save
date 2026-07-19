export default {
    getCbxLinhVucUser: (axios, data) =>
        axios.get("/web-qlvt/api/tra-cuu-ton-kho-user/ds-linhvuc"),
    getKhoNVUser: (axios, phanVungID, nhanVienId) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-ton-kho-user/ds-kho-nhan-vien/${phanVungID}/${nhanVienId}`
        ),
    getHeThongVTUser: (axios, linhVucId) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-ton-kho-user/ds-he-thong/${linhVucId}`
        ),
    getDSVatTuTonKho_v3: (axios, data) =>
        axios.post(
            "/web-qlvt/api/tra-cuu-ton-kho-user/ds-vattu-ton-kho-v3",
            data
        ),
    getQuyenND: (axios, nguoiDungId) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-ton-kho-user/ds-quyen-nd/${nguoiDungId}`
        ),
    getDSTuKho: (axios, nhanVienId) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-ton-kho-user/ds-kho-nhan-vien/${nhanVienId}`
        ),
    getDongThietBi: (axios, data) =>
        axios.post("/web-qlvt/api/tra-cuu-ton-kho-user/ds-dong-tbi", data),
    getHangSanXuat: axios =>
        axios.get("/web-qlvt/api/tra-cuu-ton-kho-user/ds-hangsx")
};
