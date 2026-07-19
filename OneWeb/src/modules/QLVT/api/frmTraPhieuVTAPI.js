export default {
    layDanhMucQLDN: (axios, data) => axios.post("/web-qlvt/api/de-nghi/danh-muc-ql-dn", data),
    layThongTinPhieuGiao: (axios, phieuId) => axios.get(`/web-qlvt/api/de-nghi-quyet-toan/ds-thong-tin-phieu-giao?phieuId=${phieuId}`),
    capNhatTraPhieu: (axios, data) => axios.post("/web-qlvt/api/de-nghi-quyet-toan/tra-phieu-v2",data),
    layLaiOTP: (axios, data) => axios.post("/web-qlvt/api/de-nghi-quyet-toan/gui-otp-v2",data)
};
