export default {
    getDSThamSoMucDich: (axios, data) => axios.get(`/web-qlvt/api/nghiem-thu-don-hang/tham-so-mac-dinh-theo-ma/${data.ma}`),
    getChiTietChungTu: (axios, data) => axios.get(`/web-qlvt/api/tao-serial/thong-tin-chi-tiet-chung-tu?ctct-id=${data.chungTuId}`),
    getKeys: (axios, data) => axios.post(`/web-qlvt/api/cap-phat-ccdc/get-key`,data),
    xemBaoCao: (axios, data) => axios.post(`web-qlvt/api/bao-cao/nhap-xuat-vt-in-barcode?extend=pdf`,data, {responseType: 'blob'}),
    XuatFileZip: (axios, data) => axios.post(`web-qlvt/api/bao-cao/zip-file-bar-code`,data, {responseType: 'blob'}),
}