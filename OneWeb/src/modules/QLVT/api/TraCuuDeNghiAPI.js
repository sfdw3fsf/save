export default {
    getDSLoaiPhieu: (axios, data) => axios.post(`/web-qlvt/api/de-nghi/danh-muc-ql-dn`, data),
    getDSPhieu: (axios, data) => axios.post(`/web-qlvt/api/de-nghi/tra-cuu-chi-tiet-giao-phieu`, data),
    getDSChiTietPhieu: (axios, data) => axios.post(`/web-qlvt/api/de-nghi/tra-cuu-chi-tiet-phieu-tong-hop`, data),
    getDSNhanVienTheoPhieu: (axios, phieuId) => axios.get(`/web-qlvt/api/de-nghi/danh-sach-nhan-vien-theo-phieu?phieu-id=${phieuId}`),
    downloadFile: (axios, linkFile) => axios.get(`/web-qlvt/api/storage/downloadFile?fileSource=${linkFile}`),
    getLinkFile: (axios, data) => axios.post(`/web-qlvt/api/storage/getPresignedUrl`,data),
    checkFileExist: (axios, data) => axios.post(`/web-qlvt/api/storage/checkExistsFile`,data),    
    deleteFile: (axios, data) => axios.post(`/web-qlvt/api/storage/deleteFile`,data),
};
