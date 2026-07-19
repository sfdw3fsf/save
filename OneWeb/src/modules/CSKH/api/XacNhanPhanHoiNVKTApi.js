export default {
    getDsDonViLoiXacNhan: (axios, data) => axios.get('/web-cskh/api/danh-muc/don-vi-theo-nv', {params: data}),
    getDsNhanVienLoi: (axios, data) => axios.get('/web-cskh/api/danh-muc/dien-thoai-vien', {params: data}),
    getDsNhomNguyenNhan: (axios, data) => axios.get('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/nhom-nguyen-nhan', {params: data}),
    getDsNguyenNhan: (axios, data) => axios.get('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/nguyen-nhan', {params: data}),
    getDsPhieuDieuLaiXacNhan: (axios, data) => axios.post('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/phieu-dieu-lai-xac-nhan?pageNo=' + data.pageNo + '&pageSize=' + data.pageSize, data),
    getThongTinPhieuKSTheoHDBH: (axios, data) => axios.get('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/thong-tin-phieu-khao-sat', {params: data}),
    getDsThueBaoTraLoi: (axios, data) => axios.get('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-thue-bao-tra-loi', {params: data}),
    getDsPhieuXacNhanKSKhachHang: (axios, data) => axios.get('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/phieu-xac-nhan-khao-sat-khach-hang', {params: data}),
    getThongTinPhieuKSPhanHoi: (axios, data) => axios.get('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/thong-tin-phieu-khao-sat-phan-hoi', {params: data}),
    getDsCauHoiTheoLoaiNV: (axios, data) => axios.get('/web-cskh/api/khao-sat-thue-bao/cau-hoi', {params: data}),
    getDsCauHoiPhu: (axios, data) => axios.get('/web-cskh/api/khao-sat-thue-bao/tra-loi', {params: data}),
    getDsChiTietTraLoi: (axios, data) => axios.get('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/chi-tiet-tbtl', {params: data}),
    capNhapThongTinKhaoSat: (axios, data) => axios.post('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/cap-nhat-tt-khao-sat', data),

    getDsNhanVienThucHien: (axios, data) => axios.get('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-nhan-vien-thuc-hien', {params: data}),

}