export default {
    getDSDonVi: (axios, data) =>
        axios.get("/web-qlvt/api/tra-cuu-ccdc/lay-ds-don-vi"),
    getDSNhanVien: (axios, data) =>
        axios.get("/web-qlvt/api/tra-cuu-ccdc/lay-ds-nhan-vien"),
    getDSNhanVienTheoDV: (axios, data) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-ccdc/lay-ds-nhanvien-donvi/${data.kieu}/${data.donViId}`
        ),
    getDanhSachCCDC: (axios, data) =>
        axios.post(
            "/web-qlvt/api/tra-cuu-ccdc/lay-ds-cdcc-nhanvien-donvi",
            data
        ),

    getDSTheoMaND: (axios, data) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-ccdc/lay-ds-nhanvien-nguoidung/${data.maNd}`
        ),
    getDSDonViTheoMaNV: (axios, data) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-ccdc/lay-donvi-manhanvien/${data.maNv}`
        ),
    getDonViTheoTenNV: (axios, data) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-ccdc/lay-donvi-tennhanvien/${data.tenNv}`
        ),

    /*  report */
    postRPBARCODE_CCDC: (axios, data) =>
        axios.post(
            "/web-qlvt/api/tra-cuu-ccdc/lay-ds-cdcc-nhanvien-donvi",
            data
        )
    /* end report */
};
