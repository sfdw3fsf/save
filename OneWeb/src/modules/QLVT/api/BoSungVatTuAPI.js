export default {
    layTTVatTuThueBao: (axios, data) => axios.get("/web-qlvt/api/bo-sung-vat-tu/thong-tin-vat-tu-thue-bao",{params: data}),
    layDanhMuc: (axios) => axios.get("/web-qlvt/api/bo-sung-vat-tu/danh-muc"),
    dayVatTuBoSung: (axios, data) => axios.put("/web-qlvt/api/bo-sung-vat-tu/day-vat-tu-bo-sung",data),
    kiemTraVatTuBoSungV2: (axios, data) => axios.post("/web-qlvt/api/bo-sung-vat-tu/kiem-tra-vat-tu-bs-v2",data),
    sntogponsnWS: (axios, serial) => axios.post(`/web-qlvt/api/bo-sung-vat-tu/sntogponsn-ws/${serial}`),
    getKO_KT_KHO_DV: (axios) => axios.get(`/web-qlvt/api/nghiem-thu-don-hang/tham-so-mac-dinh-theo-ma/KO_KT_KHO_DV`),
    capNhatVatTuSuDung: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-vat-tu-thue-bao/cap-nhat-vt-su-dung`, data)
};
