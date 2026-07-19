export default {
    postMapID: (axios, data) => axios.post("/web-thuno/api/thu-no/common/map-id", data),
    getDanhMucHinhThucTra: (axios) => axios.get("/web-thuno/api/thu-no/css/danh-muc/hinh-thuc-tra"),
    getDanhMucKenhThu: (axios) => axios.get("/web-thuno/api/thu-no/css/danh-muc/kenh-thu"),
    getDanhMucKenhThu2: (axios,id) => axios.get("/web-thuno/api/thu-no/css/danh-muc/kenh-thu-httt/" + id),
    getDanhSachHopDongTheoMaGD: (axios, data) => axios.get("/web-thuno/api/thu-no/css/ds-hop-dong-theo-ma-giao-dich", { params: data }),
    getHopDongKhachHang: (axios, id) => axios.get("/web-thuno/api/thu-no/css/hop-dong-khach-hang/" + id),
    getTienHDTBTheoHDKH: (axios, data) => axios.get("/web-thuno/api/thu-no/css/tong-tien-hd/hop-dong-khach-hang", { params: data }),
    updateThayDoiKenhThu: (axios, data) => axios.put("/web-thuno/api/thu-no/css/thay-doi-kenh-thu", data),

    //PopupDSHopDongCanThuTien:
    getDSLoaiHopDong: (axios) => axios.get("/web-thuno/api/thu-no/css/ds-loai-hop-dong"),
    getDSTrangThaiHD: (axios) => axios.get("/web-thuno/api/thu-no/css/danh-muc/trang-thai-hd"),
    getDSHopDongCanThanhToan: (axios, data) => axios.get("/web-thuno/api/thu-no/css/ds-hop-dong-can-thanh-toan", { params: data }),
};