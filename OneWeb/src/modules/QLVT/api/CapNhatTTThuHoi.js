export default {
    getDSTrangThaiThuHoi: (axios, data) =>
        axios.get("/web-qlvt/api/cap-vat-tu-thue-bao/ds-tinh-trang-vat-tu"),
    getDSChungTuThuHoi: (axios, data) =>
        axios.post(
            "/web-qlvt/api/tt-tinh-trang-thiet-bi/ds-chung-tu-thu-hoi",
            data
        ),
    getDSThuHoi: (axios, data) =>
        axios.get(
            "/web-qlvt/api/tt-tinh-trang-thiet-bi/ds-chi-tiet-thu-hoi/" +
                data.chungTuId
        ),
    postKiemTraHanChungTu: (axios, data) =>
        axios.post(
            "/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-han-chung-tu",
            data
        ),
    postCapNhatTTTB: (axios, data) =>
        axios.post(
            "/web-qlvt/api/tt-tinh-trang-thiet-bi/cap-nhat-tinh-trang-tbi",
            data
        )
    // getTinhTrangTBi: (axios, data) => axios.get('/web-qlvt/api/cap-phat-ccdc/tinh-trang-thiet-bi'),
    // getMucDichPhanBo: (axios, data) => axios.get(`/web-qlvt/api/cap-phat-ccdc/muc-dich-theo-nhom-phan-bo-ccdc/${data.nhomMDId}`),

    //  xemBaoCao: (axios, data) => axios.post(`/web-qlvt/api/bao-cao/bao-cao-bien-ban-nghiem-thu?extend=pdf`,data, {responseType: 'blob'}),
};
