export default {
    getTrungTamVT: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=3&donViId=0",
            { params: data }
        ),
    getDMKieuTamDung: (axios, data) =>
        axios.get(
            "web-thuno/api/thu-no/danh-sach-thanh-ly-thue-bao-tam-dung/lay-danh-muc-kieu-tam-dung",
            { params: data }
        ),
    getListTDTuFile: (axios, data) =>
        axios.post(
            "web-thuno/api/thu-no/danh-sach-thanh-ly-thue-bao-tam-dung/lay-danh-sach-thanh-ly-tam-dung-tu-file",
            data
        ),
    updateListTDTuFile: (axios, data) =>
        axios.post(
            "web-thuno/api/thu-no/danh-sach-thanh-ly-thue-bao-tam-dung/cap-nhat-danh-sach-thanh-ly-tam-dung-tu-file",
            data
        ),
    getDanhSachThanhLyThueBao: (axios, data) =>
        axios.get(
            "web-thuno/api/thu-no/danh-sach-thanh-ly-thue-bao-tam-dung/lay-danh-sach-thanh-ly-tam-dung-theo-dieu-kien",
            { params: data }
        ),
    taoDanhSachThanhLyThueBao: (axios, data) =>
        axios.post(
            "web-thuno/api/thu-no/danh-sach-thanh-ly-thue-bao-tam-dung/tao-danh-sach-thanh-ly-tam-dung-theo-dieu-kien",
            data
        ),
    getDanhSachDonVi: (axios, data) =>
        axios.get(
            "/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=2",
            data
        ),

    taoPhieuThanhLy: (axios, data) =>
        axios.post(
            "web-thuno/api/thu-no/danh-sach-thanh-ly-thue-bao-tam-dung/tao-phieu-thanh-ly-tam-dung",
            data
        ),
    xoaThueBao: (axios, data) =>
        axios.delete(
            "web-thuno/api/thu-no/danh-sach-thanh-ly-thue-bao-tam-dung/xoa-thue-bao-trong-danh-sach",
            { data: data }
        ),
    xoaHopDong: (axios, data) =>
        axios.delete(
            "web-thuno/api/thu-no/danh-sach-thanh-ly-thue-bao-tam-dung/xoa-hop-dong-thanh-ly-da-cap-nhat",
            { data: data }
        )
};
