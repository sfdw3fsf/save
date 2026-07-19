export default {
    getDSDonViTinh: (axios, data) => axios.get('/web-qlvt/api/tra-cuu-hop-dong-don-hang/don-vi-tinh', ),
    getDSMucDichDK: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/muc-dich-dang-ki/${data.DK}`),
    getDSDonVi: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/don-vi-hop-dong`),
    getDSVatTu: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/danh-sach-vat-tu`),

    getDSKhoNhaCC: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/kho-nha-cung-cap/${data.DK}`,),
    getDSTrangThaiHD: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/trang-thai-hop-dong/${data.DK}`,),
    getDSTrangThaiDK: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/trang-thai-dang-ky`),
    getDSMucDichMuaSam: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/muc-dich-mua-sam/${data.DK}`,),

    getDSTrangThaiDonHang: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/trang-thai-don-hang`),
    getDSHopDongDK: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/hop-dong-dang-ky/${data.donViId}`),

    getDSNhanVienNghiemThu: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/nhan-vien-nghiem-thu?page-no=0&page-size=1000`),
    getDSDonHangDK: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/don-hang-dang-ky/${data.donHangChaId}`,),
    getTraCuuHopDong: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/tra-cuu-hop-dong`,data),

    getTraCuuDonHang: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/tra-cuu-don-hang`,data),
    getDSDonHangTheoHD: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/don-hang-theo-ma-hop-dong/${data.maHopDong}`),
    CapNhatHDHetHan: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/cap-nhat-hop-dong-het-han`,data),

    getLichSuGiaHan: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/lich-su-gia-han/${data.maHopDong}`,),
    getDSVatTuTheoHD: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/vat-tu-trong-hop-dong/${data.maHopDong}`,),

    getVatTuChiTietTheoDH: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/chi-tiet-don-hang/${data.maHopDong}`,),
    TraCuuHDTheoDonHang: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/tra-cuu-hop-dong-don-hang-v2`,data),
    XuatDuLieu: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/hop-dong-don-hang-xuat-du-lieu`,data),
    
    
    getDS_NV_TG_V2: (axios, data) => axios.get(`/web-qlvt/api/hop-dong-mua-sam/ds-nv-tham-gia-v2/${data.hdms_id}`,),
}