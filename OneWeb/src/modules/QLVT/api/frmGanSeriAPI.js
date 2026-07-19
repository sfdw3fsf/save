export default {
    // getDanhMucTinhTrangTBi: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/tinh-trang-thiet-bi`),
    // getSeriDieuChinh: (axios, data) => axios.get(`/web-qlvt/api/gan-serial-thiet-bi/ds-seri-dieu-chinh/${data.chungTuId}/${data.kieu}/${data.ctctId}`),
    // CapNhatSeriDieuChinh: (axios, data) => axios.post(`/web-qlvt/api/gan-serial-thiet-bi/cap-nhat-seri-dieu-chinh`,data),

    getDSThietBiSeri: (axios, data) => axios.post(`/web-qlvt/api/gan-seri/danh-sach-thiet-bi-seri-v1`,data),
    getDSKhoTheoNV: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-kho-nhan-vien/${data.nhanVienId}`),
    KiemTraSLGanSeri: (axios, data) => axios.post(`/web-qlvt/api/gan-seri/kiem-tra-so-luong-gan-seri`,data),
    KiemTraChiTietVatTu: (axios, data) => axios.get(`/web-qlvt/api/gan-seri/kiem-tra-chi-tiet-vat-tu?chungtu-id=${data.chungTuId}`),

    CapNhatSLChiTietVatTu: (axios, data) => axios.post(`/web-qlvt/api/gan-seri/cap-nhat-so-luong-chi-tiet-vat-tu`,data),
    CapNhatChungTu: (axios, data) => axios.put(`/web-qlvt/api/gan-seri/cap-nhat-chung-tu`,data),
    getDSCuonCapV2: (axios, data) => axios.get(`/web-qlvt/api/gan-seri/danh-sach-cuon-cap-v2?kho-id=${data.khoId}&vattu-id=${data.vatTuId}&lohang=${data.loHang}`),
    ThemChiTietThietBi: (axios, data) => axios.put(`/web-qlvt/api/gan-seri/them-chi-tiet-thiet-bi`,data),

    CatChuyenCap: (axios, data) => axios.post(`/web-qlvt/api/gan-seri/cat-chuyen-cap`,data),
    getKeys: (axios, data) => axios.post(`/web-qlvt/api/cap-phat-ccdc/get-key`,data),
  }