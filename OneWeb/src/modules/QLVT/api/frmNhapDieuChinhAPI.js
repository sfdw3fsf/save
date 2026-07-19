export default {
    layDSChungTuV3: (axios, data) => axios.post("/web-qlvt/api/nhap-dieu-chinh/ds-chung-tu-v3", data),
    layDSVatTuChungTuSerial: (axios, chungTuId) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-vattu-chungtu/${chungTuId}`),
    dsTinhTrangThietBi: (axios) => axios.get("/web-qlvt/api/cap-phat-ccdc/tinh-trang-thiet-bi"),
    goChungTu: (axios, chungTuId) => axios.post(`/web-qlvt/api/nhap-dieu-chinh/go-chung-tu?chungTuGanId=${chungTuId}`),
    layNgayCT: (axios, chungTuId) => axios.get(`/web-qlvt/api/cap-phat-ccdc/lay-ngay-ct/${chungTuId}`),
    capNhatChungTuCha: (axios, data) => axios.post(`/web-qlvt/api/nhap-dieu-chinh/cap-nhat-chung-tu-cha`, { params: data }),
    getDSVatTuTheoMa: (axios, maVT) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-vat-tu-theo-ma?maVt=${maVT}`),
    kiemTraFileDieuChinh: (axios, data) => axios.post(`/web-qlvt/api/nhap-dieu-chinh/kiem-tra-file-dieu-chinh`, data),
    getDSKhoTheoNV: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-vat-tu/ds-kho-theo-nhanvien/${data.nhanVienId}/${data.donViId}`,),
    sinhChungTuDieuChinhPSIS: (axios, data) => axios.post(`/web-qlvt/api/giao-phieu-bao-hanh/tao-chung-tu-dieu-chinh-psis`, data),
};
