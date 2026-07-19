export default {
    getNhomKh: (axios, data) =>
        axios.get(
            `/web-thuno/api/thu-no/common/phan-loai-khach-hang/nhom-kh/${data}`
        ),
    getDanhSachDonVi: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=598",
            data
        ),
    getNVQuanLy: (axios, data) =>
        axios.get(
            `/web-thuno/api/thu-no/common/nhan-vien-quan-ly/don-vi/${data}`
        ),

    getDanhSachLoaiKH: async (axios, id = null) =>
        axios.get("web-thuno/api/thu-no/common/loai-khach-hang/nhom-kh/" + id),
    // axios.get(`web-thuno/api/thu-no/common/danh-muc/loai-khach-hang/`),

    getNVThuCuoc: (axios, data) =>
        axios.get(
            `/web-thuno/api/thu-no/common/nhan-vien-thu-cuoc/quan-ly/${data}`
        ),
    getDanhSachHinhThucThanhToan: async (axios, data) =>
        axios.get("/web-thuno/api/thu-no/common/hinh-thuc-thanh-toan-css", {
            params: data
        }),
    getTuyenThu: (axios, data) =>
        axios.get("/web-thuno/api/thu-no/common/tuyen-thu"),

    getLyDo: (axios, data) =>
        axios.get("web-thuno/api/thu-no/common/ds-ly-do-xac-nhan", {
            params: data
        }),

    getNguyenNhan: (axios, data) =>
        axios.get("/web-thuno/api/thu-no/common/ds-nguyen-nhan-xac-nhan", {
            params: data
        }),

    getNhomCongNo: (axios, data) =>
        axios.get("web-thuno/api/quan-ly-thu-no/nhom-cong-no"),

    getKyHoaDon: (axios, data) =>
        axios.get("web-thuno/api/thu-no/cong-no/ky-hoa-don-cong-no"),

    getListCongNo: (axios, data) =>
        axios.get("web-thuno/api/thu-no/cong-no/ds-cong-no-da-gan", {
            params: data
        }),

    capNhatCongNo: (axios, data) =>
        axios.put(
            "web-thuno/api/thu-no/cong-no/cap-nhat-ket-qua-xac-nhan-cong-no",
            data
        ),
    getDanhSachLoaiKhachHang: async (axios, id = -1) =>
        axios.get("web-thuno/api/thu-no/common/loai-khach-hang/nhom-kh/" + id),

    capNhatCongNoTheoFile: (axios, data) =>
        axios.put(
            "web-thuno/api/thu-no/cong-no/cap-nhat-ket-qua-xac-nhan-cong-no-theo-file",
            data
        ),

    kiemTraFile: (axios, data) =>
        axios.post(
            "web-thuno/api/thu-no/cong-no/kiem-tra-file-xac-nhan-cong-no",
            data
        ),
    taiBieuMau: async (axios, id = -1) =>
        axios.get("web-thuno/api/thu-no/cong-no/tai-bieu-mau-xac-nhan-cong")
};
