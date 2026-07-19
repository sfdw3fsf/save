export default {
    getDSKieuPhieu: (axios, loaictId) =>  axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-kieu-phieu/${loaictId}`),
    getDSChatLuong: (axios, ) =>  axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/chat-luong`),
    getDSLoaiKhoMaKho: (axios, data) =>  axios.get(`/web-qlvt/api/loai-kho-ma-kho/ds-loai-kho-ma-kho/${data.kieu}/${data.loaiKhoId}`),
    getDSLoaiCT: (axios, ) =>  axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-loai-chung-tu`),
    capNhatMaKhoLoaiKho: (axios, data) =>  axios.post(`/web-qlvt/api/loai-kho-ma-kho/cap-nhat-loai-kho-ma-kho`,data),
    getDSLoaiKhoTheoMucDich: (axios, mucDichId) =>  axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-loai-kho-td/${mucDichId}`),
    getDSKhoTD: (axios, loaiKhoId) =>  axios.get(`/web-qlvt/api/tra-cuu-ton-kho/danh-sach-vat-tu/${loaiKhoId}`),
    getDSDongThietBi: (axios, data) =>  axios.post(`/web-qlvt/api/loai-kho-ma-kho/ds-dong-tbi-v2`,data),
    getDSHeThong: (axios, dongTBId) =>  axios.get(`/web-qlvt/api/danh-sach-vat-tu/he-thong-theo-dong-thiet-bi?dongtbi-id=${dongTBId}`)
};