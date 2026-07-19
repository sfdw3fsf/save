export default {
    getDSLichSuSuaSerialGP: (axios, ) => axios.get(`/web-qlvt/api/tra-cuu-serial-thietbi/lay-ds-sua-serial-gp`),
    capNhapSerialGP: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-serial-thietbi/cap-nhat-log-sua-serial-gp`,data),
    uploadFile: (axios, data) => axios.post(`/web-qlvt/api/storage/uploadFile`,data),
    downloadFile: (axios, linkFile) => axios.get(`/web-qlvt/api/storage/downloadFile?fileSource=${linkFile}`),
    getLinkFile: (axios, data) => axios.post(`/web-qlvt/api/storage/getPresignedUrl`,data),
    checkFileExist: (axios, data) => axios.post(`/web-qlvt/api/storage/checkExistsFile`,data),
    deleteFile: (axios, data) => axios.post(`/web-qlvt/api/storage/deleteFile`,data),
}