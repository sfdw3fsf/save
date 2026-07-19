export default {
    getDSDichVu: (axios) => axios.get(`/web-qlvt/api/quyet-toan-vat-tu/dich-vu-vat-tu`),
    getDSNhanVien: (axios, data) => axios.get(`/web-qlvt/api/cap-vat-tu-thue-bao/ds-nhan-vien-giao-nhan/${data.donViId}/${data.nhanVienId}`),
    getDSKieuLapDat: (axios) => axios.get(`/web-qlvt/api/ds-quyet-toan-vt/danh-muc-kieu-lap-dat`),
    getDSKhoTheoNV: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-vat-tu/ds-kho-theo-nhanvien/${data.nhanVienId}/${data.donViId}`),
    getDSLyDoCap: (axios, data) => axios.post(`/web-qlvt/api/ds-quyet-toan-vt/ds-ly-do-cap`,data),
    getDSLoaiHinhThueBao: (axios, dichVuId) => axios.get(`/web-qlvt/api/ds-quyet-toan-vt/ds-loai-hinh-thue-bao/${dichVuId}`),
    getDSBaoCaoQuyetToan: (axios, data) => axios.post(`/web-qlvt/api/bao-cao/bao-cao-quyet-toan-vt`,data),
    getDSTongHopThuHoi: (axios, data) => axios.post(`/web-qlvt/api/bao-cao/bao-cao-tong-hop-vt`,data),
}