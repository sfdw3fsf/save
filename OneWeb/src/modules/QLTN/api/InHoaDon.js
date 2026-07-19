export default {
    getDSMauIn: (axios, data) =>
        axios.get("/web-thuno/api/thu-no/in-hoa-don/ds-mau-in", {
            params: data
        }),
    getDSQuan: (axios, data) =>
        axios.get("/web-thuno/api/thu-no/common/quan-huyen"),
    getDSInHoaDonTheoMaTT: (axios, data) =>
        axios.post(
            "/web-thuno/api/quan-ly-thu-no/ds-in-hoa-don/ma-thanh-toan",
            data
        ),
    getTTNoTheoMaTT: (axios, data) =>
        axios.post(
            "/web-thuno/api/quan-ly-thu-no/ds-thanh-toan-no/ma-thanh-toan",
            data
        ),
    getKiemTraMaKHTT: (axios, data) =>
        axios.post(
            "/web-thuno/api/quan-ly-thu-no/kiem-tra-ds-ma-thanh-toan",
            data
        ),
    getKiemTraInHD: (axios, data) =>
        axios.post("/web-thuno/api/quan-ly-thu-no/kiem-tra-in-hoa-don", data),
    putEditSeri: (axios, data) =>
        axios.put("/web-thuno/api/quan-ly-thu-no/seri-huy-3", data),
    getHoaDonVAT: (axios, data) =>
        axios.get("/web-thuno/api/thu-no/in-hoa-don/in-hoa-don-vat", {
            params: data
        }),
    getInBienNhan: (axios, data) =>
        axios.get("/web-thuno/api/thu-no/in-hoa-don/in-bien-nhan", {
            params: data
        })
};
