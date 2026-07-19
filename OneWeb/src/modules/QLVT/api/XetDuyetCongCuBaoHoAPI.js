export default {
    getDSDonViTramThiCong: (axios, data) => axios.post(`/web-qlvt/api/duyet-cong-cu-bao-ho/ds-don-vi-tram-thi-cong`, data),
    getDSTiepNhan: (axios, data) => axios.post(`/web-qlvt/api/duyet-cong-cu-bao-ho/ds-phieu-tiep-nhan`, data),
    getDSThamSoMacDinh: (axios, kieu) => axios.get(`/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/tham-so-mac-dinh/` + kieu),
    getDSChiTietCCDC: (axios, phieuId) => axios.get(`/web-qlvt/api/duyet-cong-cu-bao-ho/ds-chi-tiet-phieu-ccdc/` + phieuId),
    duyetPhieuCapCCDC: (axios, data) => axios.post(`/web-qlvt/api/duyet-cong-cu-bao-ho/duyet-phieu-ccdc`, data),
    capNhatGhiChu: (axios, data) => axios.post(`/web-qlvt/api/duyet-cong-cu-bao-ho/cap-nhat-ghi-chu-phieu`, data),    
    downloadImageFromUrl: (axios, data) => axios.post(`/web-qlvt/api/duyet-cong-cu-bao-ho/download-file-from-url`,data),
    checkCodeUrl: (axios, data) => axios.post(`/web-qlvt/api/duyet-cong-cu-bao-ho/check-code-url`,data),
    capNhatChiTietDungCu: (axios, data) => axios.post(`/web-qlvt/api/duyet-cong-cu-bao-ho/cap-nhat-trang-thai-chi-tiet-phieu`, data),   
}