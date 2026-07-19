export default {
    getAllThamSoMD: (axios) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/trung-tam-vien-thong-xac-minh-no-cuoc/lay-tat-ca-tham-so-mac-dinh'),
    getThongTinThanhToanTheoDK: (axios, data) => axios.get('/web-thuno/api/thu-no/khoa-may-no-cuoc-theo-thanh-toan/lay-thong-tin-thanh-toan-theo-dieu-kien', { params: data }),
    getThongTinThueBaoTheoDK: (axios, data) => axios.get('/web-thuno/api/thu-no/khoa-may-no-cuoc-theo-thanh-toan/lay-thong-tin-thue-bao-theo-dieu-kien', { params: data }),
    getDiemTinNhiemKhachHang: (axios, data) => axios.get('/web-thuno/api/thu-no/khoa-may-no-cuoc-theo-thanh-toan/lay-diem-tinh-nhiem', { params: data }),
    getDiemTinNhiemNhanVien: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/lay-diem-tin-nhiem-nhan-vien', { params: data }),
    getThanhToanIdTheoMaVach: (axios, data) => axios.get('/web-thuno/api/thu-no/khoa-may-no-cuoc-theo-thanh-toan/lay-thanh-toan-id-theo-ma-vach', { params: data }),
    getKiemTraKhoaMoTBTheoTuyenThu: (axios, data) => axios.get('/web-thuno/api/thu-no/khoa-may-no-cuoc-theo-thanh-toan/kiem-tra-khoa-mo-thue-bao-theo-tuyen-thu', { params: data }),
    updateDanhSachKhoaMoMay: (axios, data) => axios.put('/web-thuno/api/thu-no/khoa-may-no-cuoc-theo-thanh-toan/cap-nhat-danh-sach-khoa-mo-may', data),
    getKiemTraTBLapHDKhac: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-mo-may/kiem-tra-thue-bao-lap-hop-dong', { params: data }),
  }
  