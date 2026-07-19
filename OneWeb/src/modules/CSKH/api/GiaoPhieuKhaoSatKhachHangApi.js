export default {
    getDsDichVu: (axios) => axios.get('/web-cskh/api/danh-muc/dich-vu-vt' ),
    getDsNhanVienGiao: (axios, data) => axios.get('/web-cskh/api/mo-phieu-khao-sat/nhan-vien-giao-khao-sat', {params: data}),
    getDsNhanVienNhan: (axios, data) => axios.get('/web-cskh/api/mo-phieu-khao-sat/nhan-vien-nhan-khao-sat', {params: data}),
    getDsLuotGiaoKhaoSat: (axios, data) => axios.post('/web-cskh/api/giao-phieu-ks-kh/luot-giao?pageNo=' + data.pageNo + '&pageSize=' + data.pageSize, data),
    getDsPhieuDaGiaoKhaoSat: (axios, data) => axios.post('/web-cskh/api/phieu-khao-sat/phieu-da-giao?pageNo=' + data.pageNo + '&pageSize=' + data.pageSize, data),
    huyGiaoPhieuKhaoSat: (axios, data) => axios.post('/web-cskh/api/giao-phieu-ks-kh/huy', data),
    xoaPhieuGiaoKhaoSat: (axios, data) => axios.post('/web-cskh/api/giao-phieu-ks-kh/xoa', data),
    giaoPhieuKhaoSat: (axios, data) => axios.post('/web-cskh/api/giao-phieu-ks-kh/giao-phieu', data),
    getDsThongTinGiaoKhaoSat: (axios, data) => axios.post('/web-cskh/api/giao-phieu-ks-kh/thong-tin-giao-ks?pageNo=' + data.pageNo + '&pageSize=' + data.pageSize, data),
    getDsLoaiHopDong: (axios, data) => axios.get('/web-cskh/api/danh-muc/loai-hop-dong-theo-nhom', {params: data}),
}