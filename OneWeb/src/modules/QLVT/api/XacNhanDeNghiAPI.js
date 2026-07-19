export default {
    getDSLoaiPhieu: (axios, data) => axios.post(`/web-qlvt/api/de-nghi/danh-muc-ql-dn`, data),
    getDSThamSoMacDinh: (axios, kieu ) => axios.get(`/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/tham-so-mac-dinh/${kieu}`),
    getDSDeNghiQuyetToan: (axios, data ) => axios.post(`/web-qlvt/api/de-nghi-quyet-toan/ds-de-nghi-quyet-toan?trangThaiQt=${data.trangThaiQt}&loaiPhieuId=${data.loaiPhieuId}`),
    getDSThamSoMacDinhTheoMaTS: (axios, data) => axios.post(`/web-qlvt/api/test-port-v2/tham-so-mac-dinh`, data),
    getDSDeNghiChungTu: (axios, deNghiId ) => axios.get(`/web-qlvt/api/de-nghi/ds-de-nghi-ct?deNghiId=${deNghiId}`),
    getDSDeNghiQuyetToanChungTu: (axios, quyetToanId ) => axios.post(`/web-qlvt/api/de-nghi-quyet-toan/ds-de-nghi-quyet-toan-ct?quyetToanId=${quyetToanId}`),
    getHuongGiaoTiepTheo: (axios,huongGiaoId) => axios.get(`/web-qlvt/api/nhap-xuat-vt/huong-giao-tiep-theo?huong-giao-id=${huongGiaoId}`),
    traPhieuChiTiet: (axios, data) => axios.post(`/web-qlvt/api/de-nghi-quyet-toan/tra-phieu-chi-tiet`, data),
    hoanThanhPhieu: (axios,phieuId) => axios.get(`/web-qlvt/api/de-nghi-quyet-toan/hoan-thanh-phieu?phieuId=${phieuId}`),
    downloadFile: (axios, linkFile) => axios.get(`/web-qlvt/api/storage/downloadFile?fileSource=${linkFile}`),
    checkFileExist: (axios, data) => axios.post(`/web-qlvt/api/storage/checkExistsFile`,data),
}