export default {
    getDsQuyen: (axios, data) =>
        axios.get(
            `/web-qlvt/api/tra-cuu-ton-kho-user/ds-quyen-nd/${data.nguoiDungId}`
        ),
    getDsNhomCT: (axios, data) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-cong-trinh/ds-nhom-cong-trinh`),
    getLoaiCongTrinhs: (axios, data) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-cong-trinh/ds-loai-cong-trinh`),
    getLoaiSoHieu: (axios, data) =>
        axios.get(`/web-qlvt/api/quan-ly-cong-trinh/ds-loai-so-hieu`),
    getDsNguonVon: (axios, data) =>
        axios.get(`/web-qlvt/api/quan-ly-cong-trinh/danh-muc-nguon-von`),
    getDsDViQL: (axios, data) =>
        axios.get(`/web-qlvt/api/quan-ly-cong-trinh/ds-don-vi-quan-ly-pd`),
    getDsDonViTC: (axios, data) =>
        axios.get(`/web-qlvt/api/quan-ly-cong-trinh/ds-don-vi-cong-trinh`),
    getDsNhanVien: (axios, data) =>
        axios.get(`/web-qlvt/api/quan-ly-cong-trinh/ds-nhan-vien-ct`),
    getLoaiFile: (axios, data) =>
        axios.get(`/web-qlvt/api/quan-ly-cong-trinh/ds-loai-vb`),
    getDsLyDoTC: (axios, data) =>
        axios.get(`/web-qlvt/api/quan-ly-cong-trinh/ly-do-cong-trinh`),
    getDsChungLoaiTB: (axios, data) =>
        axios.get(`/web-qlvt/api/cap-vat-tu-cong-trinh/ds-chung-loai-tbi`),
    getDsHeThong: (axios, data) =>
        axios.get(`/web-qlvt/api/quan-ly-cong-trinh/ds-he-thong-linh-vuc`),
    getDsFileVBCT: (axios, data) =>
        axios.get(
            `/web-qlvt/api/quan-ly-cong-trinh/ds-file-vb-ct/${data.congTrinhId}`
        ),
    getDsChiTietVTCongTrinh: (axios, data) =>
        axios.get(
            `/web-qlvt/api/quan-ly-cong-trinh/ds-chi-tiet-vt-ct/${data.kieu}/${data.congTrinhId}`
        ),
    postTraCuuCT: (axios, data) =>
        axios.post(`/web-qlvt/api/quan-ly-cong-trinh/ds-cong-trinh`, data),

    getCountPhieuVTCT: (axios, data) =>
        axios.get(
            `/web-qlvt/api/quan-ly-cong-trinh/kiem-tra-cong-trinh/${data.congTrinhId}`
        ),
    postCapNhatCongTrinh: (axios, data) =>
        axios.post(
            `/web-qlvt/api/quan-ly-cong-trinh/cap-nhat-cong-trinh`,
            data
        ),
    getXoaPhieu: (axios, data) =>
        axios.get(
            `/web-qlvt/api/quan-ly-cong-trinh/xoa-phieu-ct/${data.congTrinhId}`
        ),
    postThemVaCapNhatFileCT: (axios, data) =>
        axios.post(
            `/web-qlvt/api/quan-ly-cong-trinh/cap-nhat-file-chi-tiet-ct`,
            data
        ),
    uploadFile: (axios, data, header) =>
        axios.post(`/web-qlvt/api/minioStorage/uploadFile`, data, header),
    updateFileUrl: (axios, data) =>
        axios.post(
            `/web-qlvt/api/quan-ly-cong-trinh/cap-nhat-url-file-ct`,
            data
        ),
    deleteFileMinIO: (axios, data) =>
        axios.delete(`/web-qlvt/api/minioStorage/deleteFile`, { params: data }),
    deleteFileDSFile: (axios, data) =>
        axios.get(
            `/web-qlvt/api/quan-ly-cong-trinh/xoa-file-vb/${data.congTrinhId}/${data.fileId}`
        ),
    getMaDonVi: (axios, data) =>
        axios.get(`/web-qlvt/api/quan-ly-cong-trinh/sinh-so-hieu`),
    postCapNhatNgayNT: (axios, data) =>
        axios.post(
            `/web-qlvt/api/quan-ly-cong-trinh/cap-nhat-ngay-nt-ct`,
            data
        ),
    postCapNhatNgayHT: (axios, data) =>
        axios.post(
            `/web-qlvt/api/quan-ly-cong-trinh/cap-nhat-ngay-ht-ct`,
            data
        ),
    postGoGanCT: (axios, data) =>
        axios.post(`/web-qlvt/api/quan-ly-cong-trinh/xu-ly-gan-go-ct`, data),
    getDSCTDCGan: (axios, data) =>
        axios.get(
            `/web-qlvt/api/quan-ly-cong-trinh/ds-ct-gan-go/${data.kieu}/${data.congTrinhId}`
        ),
    postDoiMaCT: (axios, data) =>
        axios.post(`/web-qlvt/api/quan-ly-cong-trinh/doi-ma-cong-trinh`, data),
    getDSIMS: (axios, data) => axios.get(`/web-qlvt/api/quan-ly-cong-trinh/ds-ims-ct`),
    getLinkFile: (axios, data) => axios.post(`/web-qlvt/api/storage/getPresignedUrl`,data),
    uploadFileMinio: (axios, data) => axios.post(`/web-qlvt/api/storage/uploadFile`,data),    
};
