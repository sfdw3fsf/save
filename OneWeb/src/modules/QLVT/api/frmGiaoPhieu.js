export default {
    getDSDonViGP: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/ds-don-vi-giao-phieu/${data.huongGiaoId}/${data.nhanVienId}/${data.donViId}`
        ),
    getDSHuongGiao: (axios, data) =>
        axios.get(`/web-qlvt/api/giao-phieu/ds-huong-giao/${data.huongGiaoId}`),
    getDSGiaoPhieu: (axios, data) =>
        axios.post(`/web-qlvt/api/giao-phieu/ds-giao-phieu`, data),
    getOTPDS: (axios, data) =>
        axios.post(`/web-qlvt/api/giao-phieu/gui-otp-ds`, data),
    getOTP: (axios, data) =>
        axios.get(`/web-qlvt/api/giao-phieu/gui-otp/${data.phieuId}`),
    getDSThaoTacHuongGiao: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/lay-thao-tac-huong-giao/${data.huongGiaoId}`
        ),
    getDSNVGPThaoTac: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/lay-nv-giao-phieu-thao-tac/${data.huongGiaoId}/${data.donViId}`
        ),
    getDateCurrent: (axios, data) =>
        axios.get(`/web-qlvt/api/giao-phieu/lay-date-hien-tai`),
    getKiemTraBoQuaKySo: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/kiem-tra-bo-qua-ky-so/${data.phieuGiaoId}/${data.huongGiaoId}`
        ),
    postGiaoPhieu: (axios, data) =>
        axios.post(`/web-qlvt/api/giao-phieu/giao-phieu`, data),
    getThongTinPhieu: (axios, data) =>
        axios.get(`/web-qlvt/api/giao-phieu/thong-tin-phieu/${data.phieuId}`),
    getDSPhieuBC: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/ds-phieu-bao-cao/${data.loaiPhieuId}/${data.huongGiaoId}`
        ),
    postHuyPhieu: (axios, data) =>
        axios.post(`/web-qlvt/api/giao-phieu/huy-phieu`, data),
    deleteFileObjectStorage: (axios, data) =>
        axios.delete(`/web-qlvt/api/minioStorage/deleteFile`, {
            params: data
        }),
    postAnhChuKyPhieuVT: (axios, data) =>
        axios.post(
            `/web-qlvt/api/giao-phieu/cap-nhat-anh-chu-ky-phieu-vt`,
            data
        ),
    getMotaGP: (axios, data) =>
        axios.get(`/web-qlvt/api/giao-phieu/ds-mo-ta/${data.phieuId}`),
    getDSDonViNVGP: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/lay-don-vi-nv-giao-phieu/${data.phieuId}/${data.huongGiaoTiepTheoId}`
        ),
    postXuatReport: (axios, data) =>
        axios.post(
            `/web-qlvt/api/giao-phieu/xuat-phieu-report`, data
        ),
    getGTThamSoMDMATS: (axios, data) =>
        axios.get(
            `/web-qlvt/api/nhap-xuat-chuyen-vt/lay-ds-thamso-md-mats/${data}`
        ),

    getKiemTraXacThucKySo: (axios, data) => axios.get(`/web-qlvt/api/cap-nhat-chu-ky-so-vt/xac-thuc-ky-so/${data.phieuGiaoId}/${data.huongGiaoId}`),
    getTaiKhoanKySo: (axios,data) => axios.get(`/web-qlvt/api/giao-phieu/tk-ky-so`),
    kySoSmartCAPhieuVT: (axios, data) => axios.post(`/web-qlvt/api/giao-phieu/ky-so-smart-ca-phieu-vt`,data),
    getDSTinh: (axios, ) => axios.get(`/web-qlvt/api/luanchuyen-vattu/ds-tinh`),  
};
