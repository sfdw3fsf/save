export default {
    getDsDonVi: (axios, data) => axios.get('/web-cskh/api/don-vi', {params: data} ),
    getDsLoaiHopDong: (axios, data) => axios.get('/web-cskh/api/danh-muc/loai-hop-dong-theo-nhom', {params: data}),
    getDsDichVu: (axios) => axios.get('/web-cskh/api/danh-muc/dich-vu-vt'),
    getDsNhanVienGiao: (axios, data) => axios.get('/web-cskh/api/mo-phieu-khao-sat/nhan-vien-giao-khao-sat', {params: data}),
    getDsNhanVienNhan: (axios, data) => axios.get('/web-cskh/api/mo-phieu-khao-sat/nhan-vien-nhan-khao-sat', {params: data}),
    getDsThueBaoMoPhieu: (axios, pageNo, pageSize, data) => axios.post('/web-cskh/api/mo-phieu-khao-sat/ds-thue-bao-mo-phieu?pageNo=' + pageNo + '&pageSize=' + pageSize, data),
    capNhapGiaoPhieuKS: (axios, data) => axios.post('/web-cskh/api/mo-phieu-khao-sat/cap-nhat-giao-phieu', data),
}
