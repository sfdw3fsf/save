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
    getDanhSachHinhThucThanhToan: async (axios, data) =>
        axios.get("/web-thuno/api/thu-no/common/hinh-thuc-thanh-toan-css", {
            params: data
        }),

    getNVQuanLy: (axios, data) =>
        axios.get(
            `/web-thuno/api/thu-no/common/nhan-vien-quan-ly/don-vi/${data}`
        ),

    getDanhSachLoaiKH: async axios =>
        axios.get(`web-thuno/api/thu-no/common/danh-muc/loai-khach-hang/`),

    getNVThuCuoc: (axios, data) =>
        axios.get(
            `/web-thuno/api/thu-no/common/nhan-vien-thu-cuoc/quan-ly/${data}`
        ),

    getNhomCongNo: (axios, data) =>
        axios.get("web-thuno/api/quan-ly-thu-no/nhom-cong-no"),

    getKyHoaDon: (axios, data) =>
        axios.get("web-thuno/api/thu-no/cong-no/ky-hoa-don-cong-no"),

    getListCongNo: (axios, data) =>
        axios.get("web-thuno/api/thu-no/cong-no/ds-cong-no", { params: data }),

    capNhatCongNo: (axios, data) =>
        axios.put("web-thuno/api/thu-no/cong-no/nhom-cno", data),

    huyCongNo: (axios, data) =>
        axios.delete("web-thuno/api/thu-no/cong-no/nhom-cno", { data: data })
};
