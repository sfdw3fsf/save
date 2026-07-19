export default {
    getDSNhanVienXLN: async (axios, data) =>
        axios.get(
            "web-thuno/api/thu-no/lay-danh-sach-nhan-vien-xu-ly-no-theo-ds-loai-nhan-vien",
            {
                params: data
            }
        ),
    getDSLoaiKhachHang: async axios =>
        axios.get("web-thuno/api/thu-no/common/danh-muc/loai-khach-hang"),
    getDSDonViQuanLy: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-don-vi-quan-ly", {
            params: data
        }),
    getDSQuyTrinhNguoiDung: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-quy-trinh-nguoi-dung", {
            params: data
        }),
    getDonViQLTheoQuayThu: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-don-vi-quan-ly-theo-quay-thu", {
            params: data
        }),
    getThongTinChuKyNo: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-ngay-bat-dau-ket-thuc-chu-ky-no", {
            params: data
        }),
    getQuyenNguoiDung: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/kiem-tra-quyen-nguoi-dung", {
            params: data
        }),
    getNhanVienXLN: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-nhan-vien-xu-ly-no", {
            params: data
        }),
    getDSTrangThaiXuLyNo: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-trang-thai-xu-ly-no", {
            params: data
        }),
    getKieuXuLyId: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-kieu-xu-ly-id", {
            params: data
        }),
    getLanTaoXLN: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lan-tao-du-lieu-xu-ly-no", {
            params: data
        }),
    getDSBuocKhac: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-buoc-chuyen-khac", {
            params: data
        }),
    getDSBuocChuyenXLN: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-buoc-chuyen-xu-ly-no", {
            params: data
        }),
    getDSChuyenBuocXLN: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-chuyen-buoc-xu-ly-no", {
            params: data
        }),
    getThuocTinhBB: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-thuoc-tinh-bien-ban", {
            params: data
        }),
    getDSLuotGiaoXLN: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-luot-giao", {
            params: data
        }),
    getDSDotLaySoLieu: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-dot-lay-so-lieu", {
            params: data
        }),
    getDSPhieuChuaGiao: async (axios, data) =>
        axios.post("web-thuno/api/thu-no/lay-danh-sach-phieu-chua-giao", data),

    getDSPhieuHenTra: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-phieu-xu-ly-no-hen-tra", {
            params: data
        }),
    getDSPhieuDaGiao: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-phieu-da-giao", {
            params: data
        }),
    getDSThuocTinhBBXLN: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-thuoc-tinh-xu-ly-no", {
            params: data
        }),
    getLyDoXuLyDaGiao: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-xu-ly-da-giao", {
            params: data
        }),
    getDSPhieuDaGiaoTheoMaXLN: async (axios, data) =>
        axios.get(
            "web-thuno/api/thu-no/lay-danh-sach-xu-ly-no-da-giao-theo-ma-xu-ly",
            {
                params: data
            }
        ),
    putGiaoPhieuXuLyNo: async (axios, data) =>
        axios.put("web-thuno/api/thu-no/giao-phieu-xu-ly-no", data),
    putHuyGiaoPhieuXuLyNo: async (axios, data) =>
        axios.put("web-thuno/api/thu-no/huy-giao-phieu-xu-ly-no", data),
    getKiemTraKetThucXLN: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/kiem-tra-ket-thuc-xu-ly-no", {
            params: data
        }),
    postKetThucXLN: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/ket-thuc-xu-ly-no", {
            params: data
        }),
    postCapNhatPhieu: async (axios, data) =>
        axios.put("web-thuno/api/thu-no/cap-nhat-ket-qua-xu-ly-no", data),
    postChuyenBuocXLN: async (axios, data) =>
        axios.put("web-thuno/api/thu-no/cap-nhat-buoc-chuyen-xu-ly-no", data),
    postHuyKetQuaXLN: async (axios, data) =>
        axios.put("web-thuno/api/thu-no/huy-ket-qua-xu-ly-no", data),
    postCapNhatDiaChi: async (axios, data) =>
        axios.put("web-thuno/api/thu-no/cap-nhat-dia-chi-khach-hang", data),
    postCapNhatTienNhacNo: async (axios, data) =>
        axios.put("web-thuno/api/thu-no/cap-nhat-tien-giao-nhac-no", data),
    getDSTBNoTheoMaKH: async (axios, data) =>
        axios.get(
            "web-thuno/api/thu-no/lay-danh-sach-thue-bao-no-theo-ma-khach-hang",
            {
                params: data
            }
        ),
    postGhiLaiHoSoGocXLN: async (axios, data) =>
        axios.post("web-thuno/api/thu-no/luu-ho-so-xu-ly-no", data),
    getDSThueBaoXLN: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-danh-sach-thue-bao-xu-ly-no", {
            params: data
        }),
    getThongTinNoThanhToan: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-thong-tin-no-thanh-toan", {
            params: data
        }),
    getThongTinLichSuNoThanhToan: async (axios, data) =>
        axios.get(
            "web-thuno/api/thu-no/tra-cuu-lich-su-no-theo-khach-hang-thue-bao",
            {
                params: data
            }
        ),
    getThongTinNoThueBao: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-thong-tin-no-thue-bao", {
            params: data
        }),
    getDSMauIn: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/common/mau-in", {
            params: data
        }),
    getMaBC: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-ma-bao-cao", {
            params: data
        }),
    getDSKQThuHoi: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/danh-muc-ket-qua-thu-hoi-thiet-bi"),
    getTTSDThietBi: async (axios, data) =>
        axios.get("web-thuno/api/thu-no/lay-thong-tin-su-dung-thiet-bi", {
            params: data
        }),
    putCapNhatGPXLNFile: async (axios, data) =>
        axios.put(
            "web-thuno/api/thu-no/danh-sach-loi-xln/cap-nhat-danh-sach-xln-file",
            data
        ),
    putGanNhanVienXuLyNo: async (axios, data) =>
        axios.put("web-thuno/api/thu-no/gan-nhan-vien-xu-ly-no", data),
    putGanNhanVienXuLyNoTuFile: async (axios, data) =>
        axios.put(
            "web-thuno/api/thu-no/gan-nhan-vien-xu-ly/gan-nhan-vien-xln-theo-file",
            data
        )
};
