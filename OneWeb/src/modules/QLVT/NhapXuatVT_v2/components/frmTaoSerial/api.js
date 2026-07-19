export default {
    tham_so_mac_dinh_theo_ma: (axios, data) => axios.get(`/web-qlvt/api/nghiem-thu-don-hang/tham-so-mac-dinh-theo-ma/${data.ma}`),
    thong_tin_chi_tiet_chung_tu: (axios, data) => axios.get(`/web-qlvt/api/tao-serial/thong-tin-chi-tiet-chung-tu?ctct-id=${data.chungTuId}`),
    get_key: (axios, data) => axios.post(`/web-qlvt/api/cap-phat-ccdc/get-key`,data),
    nhap_xuat_vt_in_barcode: (axios, data) => axios.post(`web-qlvt/api/bao-cao/nhap-xuat-vt-in-barcode?extend=pdf`,data, {responseType: 'blob'}),
    zip_file_bar_code: (axios, data) => axios.post(`web-qlvt/api/bao-cao/zip-file-bar-code`,data, {responseType: 'blob'}),
}