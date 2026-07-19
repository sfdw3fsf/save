export default {
    getDSDonViTheoNhanVien: (axios, nhanVienId) => axios.get(`/web-qlvt/api/dieu-chuyen-thu-hoi-ccdc/danh-muc-don-vi-ccdc/${nhanVienId}`),
    getDSAllNhanVien: (axios, ) => axios.get(`/web-qlvt/api/dieu-chuyen-thu-hoi-ccdc/ds-nv-dieu-chuyen-ccdc`),
    getDSTrangThai: (axios, ) => axios.get(`/web-qlvt/api/gan-serial-thiet-bi/danh-muc-tinh-trang-thiet-bi`),
    getToTruongId: (axios, donViId) => axios.get(`/web-qlvt/api/kiem-ke-ccdc/process-cbo-tu-don-vi/${donViId}`),
    getDSThietBi: (axios,data ) => axios.post(`/web-qlvt/api/dieu-chuyen-thu-hoi-ccdc/ds-ccdc-nv-dvi`,data),
    capNhatTinhTrangTbi: (axios,data ) => axios.post(`/web-qlvt/api/kiem-ke-ccdc/cap-nhat-tinh-trang-tbi`,data),
    themDSThietBi: (axios,data ) => axios.post(`/web-qlvt/api/kiem-ke-ccdc/tao-ds-thiet-bi-tinh-trang`,data),
}