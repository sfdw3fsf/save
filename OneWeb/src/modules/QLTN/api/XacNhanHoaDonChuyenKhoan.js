export default {
    getNVTC: (axios, data) =>
        axios.get("/web-thuno/api/thu-no/common/nhan-vien-thu-cuoc"),
    getLyDoTra: (axios, data) =>
        axios.get("/web-thuno/api/thu-no/css/ly-do-tra-phieu/loai/2"),
    getLuotGiao: (axios, data) =>
        axios.post(
            "/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-luot-giao",
            data
        ),
    getListHoaDon: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/quan-ly-dai-ly/hoa-don/chuyen-khoan-nvtc",
            { params: data }
        ),
    getTuFile: (axios, data) =>
        axios.post(
            "web-thuno/api/thu-no/cong-no/du-lieu-xac-nhan-cong-no-theo-file",
            data
        ),
    capNhatTraPhieuChuyenKhoan: (axios, data) =>
        axios.put(
            "web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-tra-phieu-chuyen-khoan",
            data
        ),
    capNhatHuyTraPhieuChuyenKhoan: (axios, data) =>
        axios.put(
            "web-thuno/api/thu-no/quan-ly-dai-ly/huy-tra-phieu-chuyen-khoan",
            data
        ),
    huyHoaDonPhatDuoc: (axios, data) =>
        axios.put(
            "web-thuno/api/thu-no/quan-ly-dai-ly/huy-hoa-don-chuyen-khoan-phat-duoc",
            data
        ),
    capNhatNgayTraPhieu: (axios, data) =>
        axios.put(
            "web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-ngay-tra-phieu-chuyen-khoan",
            data
        ),
    getKyCuocHienHanh: axios =>
        axios.get("/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh")
};
