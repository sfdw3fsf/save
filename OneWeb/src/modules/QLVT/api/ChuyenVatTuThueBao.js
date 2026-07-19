export default {
    postGanVatTuTB: (axios, data) => axios.post(`/web-qlvt/api/chuyen-vat-tu-tb/chuyen-vat-tu-tb-khac`,data),
    postGoVatTuTB: (axios, data) => axios.post(`/web-qlvt/api/chuyen-vat-tu-tb/chuyen-vat-tu-tb-khac`,data),
    getThueBao: (axios, data) => axios.get(`/web-qlvt/api/chuyen-vat-tu-tb/thue-bao/${data.maTB}`),
    getDSVTTBSD: (axios, data) => axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-vat-tu-su-dung/${data.thueBaoId}`),
    getDSThueBaoCungDoiCap: (axios, data) => axios.get(`/web-qlvt/api/chuyen-vat-tu-tb/ds-thue-bao-cung-doi-cap/${data.maDoiCap}/${data.thueBaoId}`),
    getTTThueBaoTheoTBId: (axios, data) => axios.get(`/web-qlvt/api/chuyen-vat-tu-tb/tt-thue-bao/${data.thueBaoId}`),
   
}