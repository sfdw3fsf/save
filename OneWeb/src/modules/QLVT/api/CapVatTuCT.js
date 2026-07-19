export default {
    getNhomCT: (axios) =>
        axios.get("/web-qlvt/api/cap-vat-tu-cong-trinh/ds-nhom-cong-trinh"),
    getLoaiTBi: (axios) =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/ds-kieu-trang-bi"),
    getLoaiCT: (axios) =>
        axios.get("/web-qlvt/api/cap-vat-tu-cong-trinh/ds-loai-cong-trinh"),
    getKhofrmCVTCT: (axios, nhanVienId) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-cong-trinh/ds-kho-cong-trinh/${nhanVienId}`),
    getDanhMucVT_DVT: (axios) =>
        axios.get("/web-qlvt/api/cap-vat-tu-cong-trinh/ds-ten-vat-tu-thiet-bi"),
    getKieuThietBi: (axios) =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/ds-kieu-thiet-bi-v1"),
    getTinhTrang: (axios) =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/ds-kieu-trang-bi"),
    getDSCongTrinhCapVT: (axios, data) =>
        axios.post("/web-qlvt/api/cap-vat-tu-cong-trinh/ds-cong-trinh-cap-vat-tu", data),
    getChungLoaiTB: (axios) =>
        axios.get("/web-qlvt/api/cap-vat-tu-cong-trinh/ds-chung-loai-tbi"),
    getds_LSVT: (axios, data) =>
        axios.post("/web-qlvt/api/cap-vat-tu-cong-trinh/ds-phieu-vat-tu-cong-trinh", data),
    getDSVatTuTrongKho_CT: (axios, data) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-cong-trinh/ds-vat-tu-cong-trinh_v2/${data.khoId}/${data.congTrinhId}`),
    getDSVatTuSD_CT: (axios, congTrinhId) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-cong-trinh/ds-vat-tu-su-dung/${congTrinhId}`),
    ins_vattu_congtrinh_v2: (axios, data) =>
        axios.post("/web-qlvt/api/cap-vat-tu-cong-trinh/tao-vat-tu-cong-trinh-v2", data),
    kiemtra_vt_thuhoi: (axios, hdTbId, baoHongId, serial) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-vat-tu-thu-hoi?hdTbId=${hdTbId}&baoHongId=${baoHongId}&serial=${serial}`),
    getThongTinThueBaoTheoSerial: (axios, serial) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-cong-trinh/ds-cong-trinh-su-dung-vt-theo-serial?serial=${serial}`),
    KiemTraTB_SDVT: (axios, serial) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/kiem-tra-thuebao-sd-vt?serial=${serial}`),
    getThietBi_DaCap: (axios, serial) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-thiet-bi-da-cap?serial=${serial}`),
    capnhat_sdvt_th: (axios, data) =>
        axios.post("/web-qlvt/api/cap-vat-tu-cong-trinh/cap-nhat-su-dung-vt-th-v2", data),
    getSL_DD: (axios, sdVtId) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/so-luong-thu-hoi-vat-tu-theo-phieu/${sdVtId}`),
    getSL_HT: (axios, sdVtId) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/so-luong-thu-hoi-vat-tu/${sdVtId}`),
    CAPNHAT_PHIEUVT_THUHOI: (axios, data) =>
        axios.get("/web-qlvt/api/cap-vat-tu-cong-trinh/cap-nhat-phieu-vat-tu-thu-hoi-v2", data),
    kiemtra_serial: (axios, data) =>
        axios.post("/web-qlvt/api/cap-vat-tu-cong-trinh/kiem-tra-ma-vat-tu-va-ton-tai-serial", data),
    kiemtra_serial_file: (axios, data) =>
        axios.post("/web-qlvt/api/cap-vat-tu-cong-trinh/kiem-tra-serial-trong-file-excel", data),
    kiemtra_serial_thuhoi: (axios, id, data) =>
        axios.post(`/web-qlvt/api/cap-vat-tu-cong-trinh/kiem-tra-serial-thu-hoi-dung-cong-trinh?cong-trinh-id=${id}`, data),
    LAY_TT_VATTU_THEO_SERIAL: (axios, data) =>
        axios.get(`/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/vat-tu-theo-serial/${data}`),
    LAY_ID_VATTU_THEO_MA_VT: (axios, data) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-cong-trinh/vat-tu-id-theo-ma-vat-tu?ma-vt=${data}`),
    thuHoiVatTuCongTrinh: (axios, data) => axios.post(`/web-qlvt/api/cap-vat-tu-cong-trinh/thuhoi-vt-congtrinh`, data),
    GiaoPhieuCongTrinh: (axios, data) => axios.post(`/web-qlvt/api/cap-vat-tu-cong-trinh/kiem-tra-va-giao-phieu-vat-tu`, data),
    HuyPhieuCongTrinh: (axios, data) => axios.post(`/web-qlvt/api/cap-vat-tu-cong-trinh/kiem-tra-va-huy-giao-phieu-vat-tu`, data),
    XoaPhieuVatTu: (axios, data) => axios.post(`/web-qlvt/api/cap-vat-tu-cong-trinh/xoa-phieu-vat-tu`, data),
    ThuHoiVatTu: (axios, data) => axios.post(`/web-qlvt/api/cap-vat-tu-thue-bao/cap-nhat-thu-hoi-vat-tu-v2`, data),
    getTinhTrangThuHoi: axios => axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/ds-tinh-trang-vat-tu"),
}