export default {
    traCuuPhieuDeNghi: (axios, data) => axios.post("/web-qlvt/api/de-nghi/danh-sach-phieu-dn-ht",data),
    layDSDeNghiChiTiet: (axios, deNghiId) => axios.get(`/web-qlvt/api/de-nghi/ds-de-nghi-ct?deNghiId=${deNghiId}`),
    layDanhMucQuanLyDeNghi: (axios, data) => axios.post("/web-qlvt/api/de-nghi/danh-muc-ql-dn",data),
    downloadFile: (axios, linkFile) => axios.get(`/web-qlvt/api/storage/downloadFile?fileSource=${linkFile}`),
    getLinkFile: (axios, data) => axios.post(`/web-qlvt/api/storage/getPresignedUrl`,data),
    checkFileExist: (axios, data) => axios.post(`/web-qlvt/api/storage/checkExistsFile`,data), 
};
