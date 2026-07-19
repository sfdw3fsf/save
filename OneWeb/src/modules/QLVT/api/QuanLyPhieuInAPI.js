export default {
    layDSLoaiChungTu: (axios) => axios.get("/web-qlvt/api/tra-cuu-chung-tu/lay-ds-all-loai-ct"),
    lapDSChungTuInV3: (axios, data) => axios.post(`/web-qlvt/api/quan-ly-phieu-in/ds-chung-tu-in-v3`, data),
    capNhatNgayInPXK: (axios, data) => axios.post(`/web-qlvt/api/quan-ly-phieu-in/cap-nhat-ngay-in-pxk`, data),
    layDSVatTu: (axios) => axios.get("/web-qlvt/api/tra-cuu-chung-tu/ds-vat-tu"),
    xuLyNoInKim: (axios, data) => axios.post(`/web-qlvt/api/quan-ly-phieu-in/in-kim-phieu-nx`, data),
    capNhatSoLanIn: (axios, chungTuId ) =>axios.post(`/web-qlvt/api/quan-ly-phieu-in/cap-nhat-so-lan-in-phieu-in?chungTuId=${chungTuId }`),
};
