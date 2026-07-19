export default {
    getDSVTChungTu: (axios,data ) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-vattu-chungtu-hcm/${data.chungTuId}`),
    getCboKieuPhieu: (axios,data ) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-kieu-phieu/${data.loaiCtId}`),
    getDsChatLuong: (axios,data ) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/chat-luong`),
    getDSLoaiKhoTapDoan: (axios,data ) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-loai-kho-td/${data.mucDichId}`),
    getDSKhoTapDoan: (axios,data ) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho/danh-sach-vat-tu/${data.loaiKhoTdId}`),
    postKiemTraLoaiKhoMaKho: (axios,data ) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-loai-ma-kho`,data),
    postCapNhatChungTuCoQuyen: (axios,data ) => axios.post(`/web-qlvt/api/sua-ct/cap-nhat-ct-co-quyen`,data),
    getThongTinNgayChungTu: (axios,data ) => axios.get(`/web-qlvt/api/sua-ct/ds-thong-tin-ngay-ct/${data.chungTuId}`),
    getDSMucDichTheoMDId: (axios,data ) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-ma-muc-dich-theo-muc-dich/${data.mucDichId}`),
    getCheckFile: (axios,data ) => axios.get(`/web-qlvt/api/sua-ct/kiem-tra-ct-file-cv/${data.chungTuId}`),
    uploadFile: (axios,data,header ) => axios.post(`/web-qlvt/api/minioStorage/uploadFile`,data,header),
    updateFileCongVan: (axios,data ) => axios.post(`/web-qlvt/api/sua-ct/cap-nhat-upload-file-cv`,data),
}