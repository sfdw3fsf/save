export default {
    getDSDonViTinh: (axios) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/don-vi-tinh`),
    getDSTrangThaiDangKy: (axios) => axios.get(`/web-qlvt/api/tra-cuu-hop-dong-don-hang/trang-thai-dang-ky`),
    getDSDonViDangKy: (axios) => axios.get(`/web-qlvt/api/duyet-nhu-cau-dang-ky-mua-sam/danh-sach-don-vi-dang-ky`),
    getDSNhuCauDangKy: (axios, data) => axios.post('/web-qlvt/api/tra-cuu-ton-kho-user/ds-dangky-theo-dk', data),
    getDSChiTietVatTu: (axios, dang_ky_id) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-da-dangky/${dang_ky_id}`),
    kiemTraTrangThai: (axios, data) => axios.post('/web-qlvt/api/duyet-nhu-cau-dang-ky-mua-sam/kiem-tra-trang-thai-dang-ky', data),
    capNhatNhuCauDangKy: (axios, data) => axios.post('/web-qlvt/api/duyet-nhu-cau-dang-ky-mua-sam/cap-nhat-nhu-cau-dang-ky', data),

    // API cho NhanPhieuLuanChuyen
    getDsQuyTrinh: (axios, data) => axios.post(`/web-qlvt/api/de-nghi/danh-muc-ql-dn`, data),
    getDsLuanChuyen: (axios, data) => axios.post(`/web-qlvt/api/luanchuyen-vattu/lay-ds-luan-chuyen`, data),    
    getDsChiTietLuanChuyen: (axios, data) => axios.get(`/web-qlvt/api/luanchuyen-vattu/lay-ds-chi-tiet-luan-chuan/${data.kieu}/${data.deNghiId}`),
    traPhieuLuanChuyen: (axios, data) => axios.post(`/web-qlvt/api/luanchuyen-vattu/tra-phieu-luan-chuyen`, data),
    layDSSerialExcel: (axios, deNghiId) => axios.get(`/web-qlvt/api/luanchuyen-vattu/lay-ds-serial-tinh-giao/${deNghiId}`),
    hoanThanhGiaoPhieuLuanChuyen: (axios, data) => axios.post(`/web-qlvt/api/luanchuyen-vattu/ht-giaophieu-luanchuyen`, data),
}