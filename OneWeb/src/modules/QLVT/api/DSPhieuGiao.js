export default {
    getDsLoaiPhieu: (axios, data) =>
        axios.post(`/web-qlvt/api/de-nghi/danh-muc-ql-dn`, data),
    getDSGPNVQLDN: (axios, data) =>
        axios.post(`/web-qlvt/api/giao-phieu/ds-giao-phieu-nv`, data),
    getThaoTacHG: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/lay-thao-tac-huong-giao/${data.huongGiaoId}`
        ),
    getDSHuongGiaoTiep: (axios, data) =>
        axios.get(`/web-qlvt/api/nhap-xuat-vt/huong-giao-tiep-theo`, {
            params: data
        }),
    getDSDonViNVGP: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/lay-don-vi-nv-giao-phieu/${data.phieuId}/${data.huongGiaoTiepTheoId}`
        ),
    getDanhMucQLDN: (axios, data) =>
        axios.post(`/web-qlvt/api/de-nghi/danh-muc-ql-dn`, data),
    getDSVTChungTu: (axios, data) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-chung-tu/lay-ds-vattu-chungtu/${data.chungTuId}`
        ),
    getDSThamSoMacDinh: (axios, data) =>
        axios.get(
            `/web-qlvt/api/nghiem-thu-don-hang/tham-so-mac-dinh-theo-ma/${data.tsmd}`
        ),
    getDeNghiQTTheoChungTu: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/ds-qt-theo-chung-tu/${data.chungTuId}/${data.ttPhId}`
        ),
    getDSDonViGiaoPhieu: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/ds-don-vi-giao-phieu/${data.huongGiaoId}/${data.nhanVienId}/${data.donViId}`
        ),
    postSendOTPV2: (axios, data) =>
        axios.post(`/web-qlvt/api/de-nghi-quyet-toan/gui-otp-v2`, data),
    postXacNhanOtpDeNghiV2: (axios, data) =>
        axios.post(
            `/web-qlvt/api/giao-phieu/xac-nhan-otp-phieu-de-nghi-v2`,
            data
        ),
    getKTHuongGiaoLanhDao: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/kiem-tra-huong-giao-lanh-dao/${data.huongGiaoId}`
        ),
    postGiaoPhieu: (axios, data) =>
        axios.post(`/web-qlvt/api/giao-phieu/giao-phieu`, data),
    getOtpKyTuDong: (axios, data) =>
        axios.get(`/web-qlvt/api/giao-phieu/lay-otp-ky-tu-dong`, {
            params: data
        }),
    getPhieuIdTheoQuyetToan: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/lay-phieu-theo-quyet-toan/${data.quyetToanId}`
        ),
    getDSNVGiaoPhieuThaoTac: (axios, data) =>
        axios.get(
            `/web-qlvt/api/giao-phieu/lay-nv-giao-phieu-thao-tac/${data.huongGiaoId}/${data.donViId}`
        ),
    getMota: (axios, data) =>
        axios.get(`/web-qlvt/api/giao-phieu/ds-mo-ta/${data.phieuId}`),
    postHuyPhieu: (axios, data) =>
        axios.post(`/web-qlvt/api/giao-phieu/huy-phieu`, data),
    deleteFileObjectStorage: (axios, data) =>
        axios.delete(`/web-qlvt/api/minioStorage/deleteFile`, {
            params: data
        }),
    getBaoCao04QLVT: (axios, data) =>
        axios.post(
            `/web-qlvt/api/bao-cao/bao-cao-de-nghi-thanh-qt-vt?extend=pdf&quyetToanId=${data.quyetToanId}`
        ),
    getBaoCao02QLVTTH: (axios, data) =>
        axios.post(
            `/web-qlvt/api/de-nghi-quyet-toan/bc-de-nghi-nhap-kho-qt?extend=pdf&quyetToanId=${data.quyetToanId}`
        ),
    dowloadFileFromMinIO: (axios, data) =>
        axios.delete(
            `/web-qlvt/api/de-nghi-quyet-toan/bc-de-nghi-nhap-kho-qt?extend=pdf&quyetToanId=${data.quyetToanId}`
        ),
    downloadFile: (axios, linkFile) => axios.get(`/web-qlvt/api/storage/downloadFile?fileSource=${linkFile}`),
    getLinkFile: (axios, data) => axios.post(`/web-qlvt/api/storage/getPresignedUrl`,data),
    checkFileExist: (axios, data) => axios.post(`/web-qlvt/api/storage/checkExistsFile`,data),    
    deleteFile: (axios, data) => axios.post(`/web-qlvt/api/storage/deleteFile`,data),

    getTaiKhoanKySo: (axios,data) => axios.get(`/web-qlvt/api/giao-phieu/tk-ky-so`),
    xacNhanKySoSmartCA: (axios, data) => axios.post(`/web-qlvt/api/giao-phieu/xac-nhan-ky-so-smart-ca-phieu-vt`,data),
    kiemTraChungTuKySo: (axios, data) => axios.post(`/web-qlvt/api/giao-phieu/kiem-tra-chung-tu-ky-so`,data),
};
