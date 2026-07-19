export default {
    getDSThueBaoChungTu: (axios, data) => axios.get(`/web-qlvt/api/chung-tu-thue-bao/danh-sach-thue-bao-chung-tu?chung-tu-id=${data.chungTuId}`),
    getDSThueBaoChungTuV2: (axios, data) => axios.get(`/web-qlvt/api/chung-tu-thue-bao/danh-sach-thue-bao-chung-tu-v2?chung-tu-id=${data.chungTuId}`),
    CHECK_IS_EXIST: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-ten-tham-so/${data.maThamSo}`),
    xemBaoCao: (axios, data) => axios.post(`/web-qlvt/api/bao-cao/bao-cao-bien-ban-nghiem-thu?extend=pdf`,data, {responseType: 'blob'}),
}