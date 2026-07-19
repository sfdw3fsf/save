export default {
    getDSSerialChuaDoiSoat: (axios, data) => axios.post(`/web-qlvt/api/vat-tu-thue-bao/ds-serial-chua-doi-soat`, data),
    capNhatSerialDoiSoat: (axios, data) => axios.post(`/web-qlvt/api/vat-tu-thue-bao/cap-nhat-serial-doi-soat`, data),
}