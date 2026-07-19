export default {
    getDSVatTuChungTu: (axios, chungTuId) => axios.get(`/web-qlvt/api/chung-tu-thue-bao/ds-vat-tu-chung-tu-hcm?chung-tu-id=${chungTuId}`),
    getDSChiTietVTCT: (axios, data) => axios.get(`/web-qlvt/api/nhap-dieu-chinh/ds-chi-tiet-vt-ct/${data.kieu}/${data.chungTuId}`),
    ganGoDSChiTietVT: (axios, data) => axios.post(`/web-qlvt/api/nhap-dieu-chinh/gan-go-chi-tiet-vt`,data),
}
