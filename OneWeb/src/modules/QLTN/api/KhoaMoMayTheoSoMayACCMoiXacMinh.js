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

    //new
    getDichVuVienThong: (axios, data) =>
        axios.get("/web-thuno/api/thu-no/common/dich-vu-vien-thong"),

    getDanhSachHinhThucThanhToan: async (axios, data) =>
        axios.get("/web-thuno/api/thu-no/common/hinh-thuc-thanh-toan-css", {
            params: data
        }),

    getThongTinTheoMaTB: (axios, data) =>
        axios.get(
            `/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/lay-danh-sach-isdn-theo-thue-bao`,
            {
                params: data
            }
        ),

    getQuyTrinh: (axios, data) =>
        axios.get(`/web-thuno/api/thu-no/xu-ly-thue-bao/quy-trinh-khoa-may`, {
            params: data
        }),

    getDiemTinNhiem: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/khoa-may-no-cuoc-theo-thanh-toan/lay-diem-tinh-nhiem",
            { params: data }
        ),
    getDiemTinNhiemTheoNhanVien: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/lay-diem-tin-nhiem-nhan-vien",
            { params: data }
        ),

    checkTongNoTB: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/lay-tong-no-thue-bao",
            { params: data }
        ),
    kiemTraTB: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/kiem-tra-thue-bao-truoc-khi-cap-nhat",
            { params: data }
        ),
    checkMyTVChinhPhu: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/kiem-tra-thue-bao-co-nam-trong-danh-sach-dac-biet-thang",
            { params: data }
        ),
    checkThueBaoTrongDSDacBiet: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/kiem-tra-thue-bao-co-nam-trong-danh-sach-dac-biet-thang",
            { params: data }
        ),
    checkTBMyTVChinhPhu: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/kiem-tra-thue-bao-mytv-chinh-phu",
            { params: data }
        ),
    checkKhieuNai: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/kiem-tra-co-khieu-nai",
            { params: data }
        ),
    checkTBLapHD: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/xu-ly-thue-bao/ds-mo-may/kiem-tra-thue-bao-lap-hop-dong",
            { params: data }
        ),
    getDSMoMayTheoMaTB: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/xu-ly-thue-bao/ds-mo-may/ma-thue-bao",
            { params: data }
        ),
    getKyCuocHienHanh: (axios, data) =>
        axios.get("web-thuno/api/thu-no/common/ky-cuoc-hien-hanh"),

    capNhatDanhSach: (axios, data) =>
        axios.put(
            "/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/chuyen-thue-bao-khoa-mo-may-sang-hop-dong-khoa-mo",
            data
        )
};
