export default {
    getNhanVienTheoDonVi: (axios, data) => axios.get('/web-cskh/api/nhan-vien-khao-sat', { params: data }),
    getDsLoaiHinhThueBaoKS: (axios, data) => axios.get('/web-cskh/api/phan-quyen-nvks/loai-thue-bao', {params: data}),
    getDsHopDongThueBaoKS: (axios, data) => axios.get('/web-cskh/api/phan-quyen-nvks/loai-hop-dong', {params: data}),
    getDsPhanLoaiKhachHangKS: (axios, data) => axios.get('/web-cskh/api/phan-quyen-nvks/phan-loai-khach-hang', {params: data}),
    capNhapPhanQuyenNhanVien: (axios, data) => axios.post('/web-cskh/api/phan-quyen-nvks/cap-nhat-nv', data),
    
    getDsDonViKS: (axios, data) => axios.get('/web-cskh/api/phan-quyen-nvks/don-vi', {params: data}),
    capNhapDonViKS: (axios, data) => axios.post('/web-cskh/api/phan-quyen-nvks/cap-nhat-don-vi', data),
    
    getDsLoaiKhachHangKS: (axios, data) => axios.get('/web-cskh/api/phan-quyen-nvks/loai-khach-hang', {params: data}),
    capNhapLoaiKhachHangKS: (axios, data) => axios.post('/web-cskh/api/phan-quyen-nvks/cap-nhat-loai-kh', data),

    capNhatTnKhachHangKS: (axios, data) => axios.post('/web-cskh/api/nhan-vien-khao-sat/nhan-tn', data),
}
