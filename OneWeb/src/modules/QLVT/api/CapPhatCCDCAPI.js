export default {
    getKey: (axios, data) => axios.post('/web-qlvt/api/cap-phat-ccdc/get-key', data),
    getTinhTrangTBi: (axios, data) => axios.get('/web-qlvt/api/cap-phat-ccdc/tinh-trang-thiet-bi'),
    getMucDichPhanBo: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/muc-dich-theo-nhom-phan-bo-ccdc/${data.nhomMDId}`),
    getDSDonVi: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/danh-sach-don-vi/${data.nhanVienId}`),
    getChuoiDonVi: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/chuoi-danh-sach-don-vi/${data.nhanVienId}`),

    getDSNhanVien: (axios, data) => axios.get('/web-qlvt/api/cap-phat-ccdc/danh-sach-nhan-vien',),
    ThemMoiChungTuCCDC: (axios, data) => axios.post(`/web-qlvt/api/cap-phat-ccdc/them-moi-ct-ccdc`,data),
    CapNhatChungTuCCDC: (axios, data) => axios.post(`/web-qlvt/api/cap-phat-ccdc/cap-nhat-ct-ccdc`,data),
    getDSCapCCDC: (axios, data) => axios.post(`/web-qlvt/api/cap-phat-ccdc/danh-sach-cap-ccdc`,data),

    getDSChiTietCCDC: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/danh-sach-chi-tiet-nxcc/${data.NXCCId}`),
    getDSTongHopNXCC: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/danh-sach-tong-hop-nxcc/${data.NXCCId}`),

    getNgayChungTu: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/lay-ngay-ct/${data.chungTuId}`),
    HoanThienCCDC: (axios, data) => axios.post(`/web-qlvt/api/cap-phat-ccdc/hoan-thien-ccdc`,data),
    XoaChungTuCCDC: (axios, data) => axios.post(`/web-qlvt/api/cap-phat-ccdc/xoa-ccdc/${data.nxccId}`),

    CapNhatGhiChuNXCC: (axios, data) => axios.put(`/web-qlvt/api/cap-phat-ccdc/cap-nhat-chi-tiec-nxcc`,data),
    getMaDVTheoMaNV: (axios, maNV) => axios.get(`/web-qlvt/api/cap-phat-ccdc/lay-ma-dv-theo-ma-nv/${maNV}`),
    getLoaiThietBiTheoVT: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/loai-tb-theo-ma-vt/${data.vatTuId}`),

    getThongTinID: (axios, data) => axios.post(`/web-qlvt/api/cap-phat-ccdc/lay-thong-tin-excel`,data),
    CapNhatThongTinThietBi: (axios, data) => axios.put(`/web-qlvt/api/cap-phat-ccdc/cap-nhat-trang-thai-thiet-bi`,data),

    xemBaoCao: (axios, data) => axios.post(`/web-qlvt/api/bao-cao/bao-cao-bien-ban-nghiem-thu?extend=pdf`,data, {responseType: 'blob'}),

    getDSNhanVienTheoDonVi: (axios, donViId) => axios.get(`/web-qlvt/api/cap-phat-ccdc/danh-sach-nhan-vien-theo_dv/${donViId}`),

}