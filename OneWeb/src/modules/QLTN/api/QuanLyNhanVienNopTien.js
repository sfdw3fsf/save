export default {
    getNhanVienNopTien: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-no/ds-xu-ly-no', {params: data}),
    getDanhSachDonViNhanVien: (axios, data) => axios.get('/web-thuno/api/thu-no/common/don-vi/nhan-vien', {params: data}),
    getNhomHinhThuc: (axios, data) => axios.get('/web-thuno/api/thu-no/common/nhom-hinh-thuc-thanh-toan', {params: data}),
    getHinhThucThanhToan: (axios, data) => axios.get('/web-thuno/api/thu-no/common/hinh-thuc-thanh-toan', {params: data}),
    getKieuNop: (axios, data) => axios.get('/web-thuno/api/thu-no/common/danh-muc/kieu-nop', {params: data}),
    getDanhSachThuNgan: (axios, data) => axios.get('/web-thuno/api/thu-no/common/nhan-vien-thu-ngan/nguoi-dung-hien-tai', {params: data}),
    getDanhSachNopTien: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/nop-tien-nhan-vien', {params: data}),
    getDanhSachDaiLyNopNH: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/dai-ly-nop-ngan-hang', {params: data}),
    addNhanVienNopTien: (axios, data) => axios.post('/web-thuno/api/thu-no/quan-ly-dai-ly/nop-tien-nhan-vien', data),
    updateNhanVienNopTien: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/nop-tien-nhan-vien', data),
    deleteLanNopTien: (axios, data) => axios.delete('/web-thuno/api/thu-no/quan-ly-dai-ly/nop-tien-nhan-vien', { data: data }),
}
