export default {
  vat_tu_theo_tham_so: (axios, data) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/vat-tu-theo-tham-so?thamso=${data.thamSo}`),
  chat_luong: (axios, data) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/chat-luong`),
  prefix_theo_ma_lo_hang: (axios, data) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/prefix-theo-ma-lo-hang?lo-hang=${data.loHang}`),
  dong_thiet_bi_theo_vat_tu: (axios, data) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/dong-thiet-bi-theo-vat-tu?vattu-id=${data.vatTuId}`),
  he_thong_theo_dong_thiet_bi: (axios, data) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/he-thong-theo-dong-thiet-bi?dongtbi-id=${data.dongTbi}`)
}
