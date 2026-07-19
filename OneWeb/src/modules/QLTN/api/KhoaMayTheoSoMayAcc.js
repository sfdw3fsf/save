export default {
  getDSTheoMaTB: (axios, data) => axios.get('/web-thuno/api/thu-no/danh-ba/ma-thue-bao', { params: data}),
  getDiemTinNhiemKhachHang: (axios, data) => axios.get('/web-thuno/api/thu-no/khoa-may-no-cuoc-theo-thanh-toan/lay-diem-tinh-nhiem', { params: data}),
  getThamSoMacDinh: (axios, data) => axios.get('/web-thuno/api/thu-no/mo-may-theo-danh-sach/lay-danh-sach-tham-so-md-mats', { params: data }),
  getLyDoKhoa: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/lay-ly-do-khoa-theo-thanh-toan', { params: data}),
  getDSISDNTheoThueBao: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/lay-danh-sach-isdn-theo-thue-bao', { params: data}),
  checkThueBaoTruocKhiCapNhat: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/kiem-tra-thue-bao-truoc-khi-cap-nhat', { params: data}),
  checkThueBaoTrongDSDacBiet: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/kiem-tra-thue-bao-co-nam-trong-danh-sach-dac-biet-thang', { params: data}),
  checkThueBaoTraTruoc: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-tien-tra-truoc', { params: data }),
  checkMyTVChinhPhu: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/kiem-tra-thue-bao-mytv-chinh-phu', { params: data}),
  checkTongNoTB: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/lay-tong-no-thue-bao', { params: data}),
  checkKhieuNaiTB: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/kiem-tra-co-khieu-nai', { params: data}),
  giaophieuKhoaMay: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/giao-phieu-khoa-may', { params: data}),
  chuyenTBsangHDKhoaMo: (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/khoa-may-theo-so-may-va-tai-khoan/chuyen-thue-bao-khoa-mo-may-sang-hop-dong-khoa-mo', data),
  getDSQuyTrinhKhoaMay: (axios, data) => axios.get('/web-thuno/api/thu-no/css/ds-quy-trinh-khoa-may', { params: data }),
  getHuongGiaoTheoQuyTrinh: (axios, data) => axios.get('/web-thuno/api/thu-no/css/huong-giao/quy-trinh', { params: data }),
  getHuongGiaoTheoLuong: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-huong-giao/luong/' + data),
  getThaoTacTheoLuong: (axios, data) => axios.get('/web-thuno/api/thu-no/common/thao-tac/luong/' + data)
}
