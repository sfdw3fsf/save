export default {
    getDSNhanVienGiaoNhan: (axios, data) => axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-nhan-vien-giao-nhan/${data.donViId}/${data.nhanVienId}`),
    getDSKho: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-vat-tu/ds-kho-theo-nhanvien/${data.nhanVienId}/${data.donViId}`),
    getDSVatTu: (axios, data) => axios.post(`/web-qlvt/api/tam-ung-vat-tu/ds-vat-tu-kho-giao-nhan`,data),
    getDSNhanVien: (axios, khoId) => axios.get(`/web-qlvt/api/tam-ung-vat-tu/ds-nhan-vien-tam-ung/${khoId}`),
    getDSVatTuKhoGiaoNhan: (axios, data) => axios.post(`/web-qlvt/api/tam-ung-vat-tu/ds-vat-tu-kho-giao-nhan`,data),
    getIDKhoCha: (axios, khoId) => axios.get(`/web-qlvt/api/tam-ung-vat-tu/ds-kho-theo-kho-giao/${khoId}`),
    getKey: (axios, data) => axios.post('/web-qlvt/api/cap-phat-ccdc/get-key', data),
    addTamUngVatTu: (axios, data) => axios.post('/web-qlvt/api/tam-ung-vat-tu/tao-lich-su-tam-ung-vt', data),
    tamUngVatTuNhanVien: (axios, data) => axios.post('/web-qlvt/api/tam-ung-vat-tu/tam-ung-vt-nhanvien', data),
}