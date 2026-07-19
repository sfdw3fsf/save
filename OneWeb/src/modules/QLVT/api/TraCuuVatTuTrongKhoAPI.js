export default {
    getDSKhoTheoNV: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-vat-tu/ds-kho-theo-nhanvien/${data.nhanVienId}/${data.donViId}`),
    getDSTongHopTB: (axios, data) => axios.post('/web-qlvt/api/tra-cuu-vat-tu/ds-thiet-bi-trong-kho',data),
    getDSChiTietTB: (axios, data) => axios.post('/web-qlvt/api/tra-cuu-vat-tu/ds-chi-tiet-thietbi-tamung',data),
    getDSThueBaoDangCap: (axios, data) => axios.post('/web-qlvt/api/tra-cuu-vat-tu/ds-thong-tin-thue-bao',data),
}