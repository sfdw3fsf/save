export default {
    getDanhSachCongNo: (axios) => axios.get('/web-thuno/api/quan-ly-thu-no/nhom-cong-no'),
    getDanhSachNhanVienCongNo: (axios, data) => axios.get('/web-thuno/api/thu-no/cong-no/ds-nhan-vien-nhom-cong-no', {params: data}),
    capNhapDanhSachNhomCongNo: (axios, data) => axios.post('/web-thuno/api/thu-no/cong-no/ds-nhan-vien-nhom-cong-no', data),
}