export default {
    layDSQuyTrinh: (axios) => axios.get(`/web-qlvt/api/giao-phieu-bao-hanh/ds-he-thong-quy-trinh/7`),
    layDSBaoCao: (axios) => axios.get(`/web-qlvt/api/cap-nhat-chu-ky-so-vt/ds-bao-cao-theo-nhom/31`),
    layDSHuongGiao: (axios) => axios.get(`/web-qlvt/api/cap-nhat-chu-ky-so-vt/ds-huong-giao-cks/7/0`),
    layDSTuKhoa: (axios) => axios.get(`/web-qlvt/api/cap-nhat-chu-ky-so-vt/ds-tu-khoa`),
    layDSChuKySo: (axios) => axios.get(`/web-qlvt/api/cap-nhat-chu-ky-so-vt/ds-chu-ki-so`),
    layDSDanhMucLoaiPhieu: (axios) => axios.get(`/web-qlvt/api/cap-nhat-chu-ky-so-vt/danh-muc-loai-phieu`),
    layDSThietLapPhieuBC: (axios) => axios.get(`/web-qlvt/api/cap-nhat-chu-ky-so-vt/ds-thiet-lap-phieu-bc`),
    layDSLoaiPhieu: (axios) => axios.get(`/web-qlvt/api/cap-nhat-chu-ky-so-vt/ds-loai-phieu`),
    kiemTraLoaiPhieuTheoCKS: (axios, cksId) => axios.get(`/web-qlvt/api/cap-nhat-chu-ky-so-vt/kiem-tra-loai-phieu-theo-cks/${cksId}`),
    capNhatCKS: (axios, data) => axios.post('/web-qlvt/api/cap-nhat-chu-ky-so-vt/xu-ly-cap-nhat-chu-ky-so', data),
    capNhatThietLapBaoCao: (axios, data) => axios.post('/web-qlvt/api/cap-nhat-chu-ky-so-vt/xu-ly-cap-nhat-loai-phieu-bc', data),

    xoaCKS: (axios, cksId) => axios.delete(`/web-qlvt/api/cap-nhat-chu-ky-so-vt/xoa-chu-ky-so/${cksId}`),
    xoaLoaiPhieuBC: (axios, loaiPhieuBCId) => axios.delete(`/web-qlvt/api/cap-nhat-chu-ky-so-vt/xoa-loai-phieu-bc/${loaiPhieuBCId}`),
};
