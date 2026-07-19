import axios from "axios";

export default {
    getdsDonViTrungTamVienThong: (axios) => axios.get('/web-cskh/api/danh-muc/don-vi-ttvt'),
    getdsDienThoaiVien: (axios) => axios.get('/web-cskh/api/danh-muc/dien-thoai-vien'),
    getdsPhanLoaiKhachHang: (axios) => axios.get('/web-cskh/api/danh-muc/phan-loai-kh'),
    getdsLoaiKQGiamThinh: (axios) => axios.get('/web-cskh/api/danh-muc/loai-ket-qua-giam-thinh'),
    getdsPhieuGiamThinh: (axios, pageNo, pageSize, data) => axios.post('/web-cskh/api/giam-thinh-phieu-khao-sat/tra-cuu?pageNo=' + pageNo + '&pageSize=' + pageSize, data),
    getdsLoaiKQGiamThinh: (axios) => axios.get('/web-cskh/api/danh-muc/loai-ket-qua-giam-thinh'),
    getdsCauHoi: (axios) => axios.get('/web-cskh/api/thue-bao-khao-sat/cau-hoi'),
    getLishSuSuaPhieu: (axios, data) => axios.get('/web-cskh/api/giam-thinh-phieu-khao-sat/lich-su-sua-phieu', {params: data}),
    getLishSuGuiSMS: (axios, data) => {
        return axios.get('/web-cskh/api/giam-thinh-phieu-khao-sat/lich-su-gui-sms', {params: data})
    },
    getChiTietTraLoiKhaoSat: (axios, data) => axios.get('/web-cskh/api/khao-sat-thue-bao/chi-tiet-tra-loi', {params: data}),
}