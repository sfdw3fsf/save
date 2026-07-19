export default {
  getTTKhachHangThanhLy: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/khach-hang-thanh-ly-thong-tin-no", { params: data}),
  gachNoThanhLy: (axios, data) => axios.put("/web-thuno/api/quan-ly-thu-no/gach-no-thanh-ly", data),
  getPhieuTra: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/phieu-tra-khach-hang-thanh-ly", {params: data}),
  getPhieuHuy: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/phieu-huy-khach-hang-thanh-ly", {params: data}),
  getPhieuTraTheoThuNgan: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/phieu-tra-khach-hang-thanh-ly-theo-thu-ngan", {params: data}),
  deletePhieuTra: (axios, data) => axios.delete("/web-thuno/api/quan-ly-thu-no/xoa-phieu-tra-khach-hang-thanh-ly/" + data),
  /*
  getThamSoGachNo: (axios, data) => axios.get("/web-thuno/api/thu-no/common/tham-so-gach-no"),
  getDonViQuanLy: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-don-vi-quan-ly", { params: data }),
  getDSLoaiTien: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-danh-sach-loai-tien", { params: data }),
  getThongTinThueBao: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/tra-cuu-thong-tin-tong-no", { params: data }),
  getPayment_Info_ForPay: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-thong-tin-thanh-toan-de-thanh-toan", { params: data }),
  getTTThanhhToanTheoDBHienTai: (axios, data) => axios.get("/web-thuno/api/thu-no/sp-lay-thanh-toan-theo-danh-ba-hien-tai", { params: data }),
  getTongNoTheoKyHoaDon: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-tong-no-thanh-toan-theo-ky-hoa-don", { params: data }),
  getChiTietNoTheoChuKyNo1: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-chu-ky-no-thanh-toan-k1", data),
  getChiTietNoTheoChuKyNo2: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-chu-ky-no-thanh-toan-k2", data),
  getChiTietNoTheoKhoanMuc1: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-khoan-muc-thanh-toan-k1", data),
  getChiTietNoTheoKhoanMuc2: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-khoan-muc-thanh-toan-k2", data),
  getChiTietNoTheoDichVu1: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-dich-vu-thanh-toan-k1", data),
  getChiTietNoTheoDichVu2: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-dich-vu-thanh-toan-k2", data),
  getDSNVThuCuoc: (axios, data) => axios.get("/web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-thu-cuoc-2", { params: data }),
  getDSPhieuDaGach: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/danh-sach-phieu-da-gach", { params: data }),
  getChiTietNoKMDieuChinh: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/dieu-chinh-no/lay-chi-tiet-no-khoan-muc-dieu-chinh", data),

  gachNoLe: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/gach-no-tien-mat/gach-no-le", data),


  updateXacNhanKetQuaCongNoTheoId: (axios, data) => axios.put("/web-thuno/api/thu-no/cong-no/xac-nhan-ket-qua-cong-no", data),
  getDanhSachCongNoDaGan: (axios, data) => axios.get("/web-thuno/api/thu-no/cong-no/ds-cong-no-da-gan", { params: data }),
  getDanhSachDonViXacNhanNo: (axios) => axios.get("/web-thuno/api/thu-no/xu-ly-no/don-vi-xac-nhan-no"),
  updateGiaoPhieuNhanVienXacNhan: (axios, data) => axios.put("/web-thuno/api/thu-no/cong-no/giao-phieu-nv-xac-nhan-cong-no", data)
  */
};
