export default {
    getDSNhanVienTinhCuoc: async (axios, data) => axios.get('/web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-tinh-cuoc', { params: data }),
    getDSPhieuLacHuongPhanChiaKH: async (axios, data) => axios.get('/web-thuno/api/thu-no/sp-lay-danh-chuyen-phieu-lac-huong-phan-chia-khach-hang', { params: data })
}