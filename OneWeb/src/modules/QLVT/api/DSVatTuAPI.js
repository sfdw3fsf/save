export default {
    getDSVatTuTheoThamSo: (axios, data) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/vat-tu-theo-tham-so?thamso=${data.thamSo}`),
    getDSLoHang: (axios, data) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/lo-hang`),
    getDanhMucChatLuong: (axios, data) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/chat-luong`),
    getDSLoHangTheoMa: (axios, data) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/prefix-theo-ma-lo-hang?lo-hang=${data.loHang}`),
    getThongTinThietBi: (axios, data) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/dong-thiet-bi-theo-vat-tu?vattu-id=${data.vatTuId}`),
    getThongTinHeThongTheoTB: (axios, data) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/he-thong-theo-dong-thiet-bi?dongtbi-id=${data.dongTbi}`),

  }