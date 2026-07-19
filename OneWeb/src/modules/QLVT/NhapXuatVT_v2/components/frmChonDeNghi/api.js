export default {
    ds_serial_chua_doi_soat: (axios, data) => axios.post(`/web-qlvt/api/vat-tu-thue-bao/ds-serial-chua-doi-soat`, data),
    cap_nhat_serial_doi_soat: (axios, data) => axios.post(`/web-qlvt/api/vat-tu-thue-bao/cap-nhat-serial-doi-soat`, data),
}