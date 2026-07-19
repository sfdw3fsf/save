export default {
    getDSThietBiChungTu: (axios, chungTuId) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-thietbi-chungtu/${chungTuId}`),
    getDSSerialDHSX: (axios, data) => axios.post(`/web-qlvt/api/quan-ly-tai-san/thong-tin-serial-dhsx`,data),
    getDSThietBiChuaGan: (axios, chungTuId) => axios.get(`/web-qlvt/api/quan-ly-tai-san/ds-tbi-chua-gan-serial/${chungTuId}`),
    capNhatSerialQLTS: (axios, data) => axios.post(`/web-qlvt/api/quan-ly-tai-san/cap-nhat-serial-qlts`,data),
    kiemTraTonSerial: (axios, serial) => axios.get(`/web-qlvt/api/quan-ly-tai-san/kiem-tra-ton-serial?serial=${serial}`),
    kiemTraTonTaiMaVT: (axios, maVT) => axios.get(`/web-qlvt/api/quan-ly-tai-san/kiem-tra-ma-vt-ton-tai?maVt=${maVT}`),
    kiemTraChungTuKhoNhan: (axios, chungTuId) => axios.get(`/web-qlvt/api/quan-ly-tai-san/kiem-tra-chung-tu-kho-nhan/${chungTuId}`),
    getTTQLTS: (axios, data) => axios.post(`/web-qlvt/api/quan-ly-tai-san/thong-tin-qlts`,data),
    // kiemTraSeriTonTaiQLTS: (axios, data) => axios.post(`/web-qlvt/api/quan-ly-tai-san/kiem-tra-serial-qlts`,data),
};
