export default {
    getDSVTChungTu: (axios, data) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-chung-tu/lay-ds-vattu-chungtu/${data.chungTuId}`
        ),
    postDSVTGiao: (axios, data) =>
        axios.post(`/web-qlvt/api/nhap-tbi-bao-hanh/ds-tbi-giao-nhan-ct`, data),
    postDieuChuyenVTCT: (axios, data) =>
        axios.post(
            `/web-qlvt/api/nhap-tbi-bao-hanh/dieu-chuyen-vt-cong-trinh`,
            data
        )
};
