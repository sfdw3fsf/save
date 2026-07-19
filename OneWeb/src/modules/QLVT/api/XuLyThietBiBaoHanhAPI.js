export default {
    getDSTrangThaiBHDich: (axios, data) => axios.get(`/web-qlvt/api/xu-ly-thiet-bi-bao-hanh/trang-thai-bao-hanh-dich?luong-id=${data.luong_id}&ttbh-nguon-id=${data.ttbh_nguon_id}`),
    getDSBaoHanhTBHienTai: (axios, data) => axios.get(`/web-qlvt/api/xu-ly-thiet-bi-bao-hanh/bao-hanh-thiet-bi?nhanvien-id=${data.nhanvien_id}&ttbh-id=${data.ttbh_id}&quytrinh-id=${data.quytrinh_id}`),
    getThongTinTrangThaiBH: (axios, data) => axios.get(`/web-qlvt/api/xu-ly-thiet-bi-bao-hanh/thong-tin-theo-trang-thai-bao-hanh?ttbh-id=${data.ttbh_id}`),
    getDSKhoNhaCungCap: (axios, data) => axios.get(`/web-qlvt/api/xu-ly-thiet-bi-bao-hanh/kho-nha-cung-cap?loaikho-id=${data.loaikho_id}&nhanvien-id=${data.nhanvien_id}`),
    chuyenTrangThaiBaoHanh: (axios, data) => axios.post('/web-qlvt/api/xu-ly-thiet-bi-bao-hanh/chuyen-trang-thai-bao-hanh',data),
    huyChuyenTrangThaiBH: (axios, data) => axios.post(`/web-qlvt/api/xu-ly-thiet-bi-bao-hanh/huy-bao-hanh`,data),
    capNhatKetQuaBH: (axios, data) => axios.put(`/web-qlvt/api/xu-ly-thiet-bi-bao-hanh/ket-qua-bao-hanh`,data),
    xemBaoCao: (axios, data) => axios.post(`/web-qlvt/api/bao-cao/bao-cao-bao-hanh-ncc?extend=pdf`,data, {responseType: 'blob'}),
}