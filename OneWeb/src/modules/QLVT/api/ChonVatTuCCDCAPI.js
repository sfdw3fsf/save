export default {
    getDSNhanVien: (axios, ) => axios.get('/web-qlvt/api/dieu-chuyen-thu-hoi-ccdc/ds-nv-dieu-chuyen-ccdc'),
    getDSCCDCNVDV: (axios, data) => axios.post('/web-qlvt/api/dieu-chuyen-thu-hoi-ccdc/ds-ccdc-nv-dvi', data),
}