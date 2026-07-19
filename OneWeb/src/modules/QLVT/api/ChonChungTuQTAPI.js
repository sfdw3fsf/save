export default {
    getDSDonVi: (axios, data) =>  axios.post("/web-qlvt/api/quyet-toan-vat-tu/ds-chung-tu-donvi", data),
    getDSChungTuHoanUng: (axios, data) =>  axios.post("/web-qlvt/api/quyet-toan-vat-tu/ds-ct-hoan-ung", data),
    getDSVatTu: (axios, chungTuId) =>  axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-vattu-chungtu/${chungTuId}`),
    themDeNghiChungTu: (axios, data) =>  axios.post(`/web-qlvt/api/quyet-toan-vat-tu/them-de-nghi-ct`,data),
}
