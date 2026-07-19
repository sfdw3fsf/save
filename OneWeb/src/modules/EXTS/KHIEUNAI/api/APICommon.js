export default {
    getDSDichVuVT: async (axios) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_lay_ds_dichvu_vt'),
    getDSLoaiHinhThueBao: async (axios, dichVuVTID) => axios.post('/web-gqkn/giaophieu-nhanvien/get-loaihinh-thuebao', {pDichVuId: dichVuVTID}),
    getDSChuDe: async (axios) => axios.post('/web-gqkn/xu-ly-khieu-nai/getDsChuDe'),
    getDSTrangThaiKN: async (axios) => axios.post('/web-gqkn/giaophieu-nhanvien/get-trangthai-kn'),
    getDXGiamTruCuocTheoTT: async (axios, params) => axios.post('/web-gqkn/dx-giamtru-cuoc/ds-theo-trangthai', params),
    getChiTietDXGiamTruCuocTheoMaKN: async (axios, params) => axios.post('/web-gqkn/dx-giamtru-cuoc/chitiet-theo-ma-kn', params),
    createOrUpdatePhieuDeXuatGTC: (axios, data) => axios.post('/web-gqkn/dx-giamtru-cuoc/create-or-update', data),
    tuChoiDeXuatGiamTruCuoc: (axios, data) => axios.post('/web-gqkn/dx-giamtru-cuoc/tu-choi', data),
    khoaPhieuDeXuatGiamTruCuoc: (axios, data) => axios.post('/web-gqkn/dx-giamtru-cuoc/khoa-phieu', data),
    xoaPhieuDeXuatGiamTruCuoc: (axios, data) => axios.post('/web-gqkn/dx-giamtru-cuoc/xoa-phieu', data),
}