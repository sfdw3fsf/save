import AssignSuppliesToSubsType from "../../admin/category/AssignSuppliesToSubsType";

export default {
  getDanhSachHTTT: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-danh-sach-hinh-thuc-thanh-toan", { params: data }),
  getDSThuNgan: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-danh-sach-nhan-vien-thu-ngan"),
  getTongPhieuTheoUser: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-tong-tien-thu-theo-nguoi-dung", { params: data}),
  getThamSoGachNo: (axios, data) => axios.get("/web-thuno/api/thu-no/common/tham-so-gach-no"),
  getDonViQuanLy: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-don-vi-quan-ly", { params: data }),
  getDSLoaiTien: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-danh-sach-loai-tien", { params: data }),
  getThongTinThueBao: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/tra-cuu-thong-tin-tong-no", { params: data }),
  getThongTinThueBao2: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/tra-cuu-thong-tin-tong-no-2", data),
  getPayment_Info_ForPay: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-thong-tin-thanh-toan-de-thanh-toan", { params: data }),
  getPayment_Info_ForPay2: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/payment-infor-v2", { params: data }),
  getTTThanhhToanTheoDBHienTai: (axios, data) => axios.get("/web-thuno/api/thu-no/lay-thanh-toan-theo-danh-ba-hien-tai/" + data),
  getTongNoTheoKyHoaDon: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-tong-no-thanh-toan-theo-ky-hoa-don", { params: data }),
  getTongNoTheoThueBao: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-danh-sach-no-theo-thue-bao", { params: data }),
  getChiTietNoTheoChuKyNo1: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-chu-ky-no-thanh-toan-k1", data),
  getChiTietNoTheoChuKyNo2: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-chu-ky-no-thanh-toan-k2", data),
  getChiTietNoTheoKhoanMuc1: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-khoan-muc-thanh-toan-k1", data),
  getChiTietNoTheoKhoanMuc2: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-khoan-muc-thanh-toan-k2", data),
  getChiTietNoTheoDichVu1: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-dich-vu-thanh-toan-k1", data),
  getChiTietNoTheoDichVu2: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-no-theo-dich-vu-thanh-toan-k2", data),
  getDSNVThuCuoc1: (axios, data) => axios.get("/web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-thu-cuoc", { params: data }),
  getDSNVThuCuoc2: (axios, data) => axios.get("/web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-thu-cuoc-2", { params: data }),
  getDSNVThuNo1: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu-danh-ba/ds-nhan-vien-thu-no", { params: data }),
  getDSNVThuNo2: (axios, data) => axios.get("/web-thuno/api/thu-no/common/ds-nhan-vien-thu-no-v2", { params: data }),
  getDSPhieuDaGach: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/danh-sach-phieu-da-gach", { params: data }),
  getChiTietPhieuDaGach: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-chi-tiet-phieu-tra", { params: data }),
  getChiTietNoKMDieuChinh: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/dieu-chinh-no/lay-chi-tiet-no-khoan-muc-dieu-chinh", data),
  getThongTinThanhToan: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/thong-tin-thanh-toan/thanh-toan-thue-bao", { params: data }),
  
  getChiTietNoTheoChuKyNoHoanTra: (axios, data) => axios.post("/web-thuno/api/thu-no/gach-no/lay-chi-tiet-no-theo-ckn-tt-hoan-tra", data),

  getDanhSachTBNo: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/ds-thue-bao-no", { params: data }),
  getMaTTNeo: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-ma-tt-neo-vnp", { params: data }),



  gachNoLeTienMat: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/gach-no-le", data),
  gachNoDSTienMat: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/gach-no-danh-sach", data),
  checkDSDaGach: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/kiem-tra-trang-thai-gach-no-ds", { params: data }),
  getDSDaGach: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/kiem-tra-chi-tiet-gach-no-ds", { params: data }),
  getTongNoTuFile: (axios, data) => axios.post("/web-thuno/api/thu-no/gach-no-tien-mat/lay-no-theo-danh-sach-ma-thanh-toan", data),

  /* */
  gachNoLeNganHang: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-chuyen-khoan/gach-no-le-ngan-hang", data),
  gachNoDSNganHang: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-chuyen-khoan/gach-no-le-ngan-hang", data),
  getDSThanhToanMIG: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/ds-thanh-toan-no-mig", { params: data }),


  gachNoLeDaiLy: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/gach-no-le", data),
  gachNoDSDaiLy: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/gach-no-danh-sach", data),

  gachNoLeThoaiTra: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no/thoai-tra/gach-no-le", data),
  gachNoDSThoaiTra: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no/thoai-tra/gach-no-danh-sach", data),

  gachNoLeHoanTraTamThu: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/hoan-tra-tien-tam-thu/gach-le", data),
  gachNoDSHoanTraTamThu: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/hoan-tra-tien-tam-thu/gach-no-danh-sach", data),
  getChiTietNoTheoChuKyNoHoanTra: (axios, data) => axios.post("/web-thuno/api/thu-no/gach-no/lay-chi-tiet-no-theo-ckn-tt-hoan-tra", data),

  getTienDatCoc: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-tien-dat-coc", data),
  gachNoLeDatCoc: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-dieu-chinh-tien-dat-coc-tra-truoc/gach-no-dat-coc", data),


  gachNoLeThanhLy: (axios, data) => axios.put("/web-thuno/api/thu-no/gach-no/gach-no-thanh-ly", data),
  gachNoThanhLyDanhSach: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/thanh-ly-no/gach-no-danh-sach", data),


  updateNgayTT: (axios, data) => axios.put("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/doi-ngay-thanh-toan", data),
  deletePhieuThu: async (axios, data) => axios.delete('/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/xoa-phieu-thanh-toan-gach-no', { data: data}),
  deletePhieuThuMK: async (axios, data) => axios.delete('/web-thuno/api/thu-no/sp-xoa-phieu-thanh-toan-mk', { data: data}),

  



  /// Phiếu thu xóa
  getDSPhieuDaXoa: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-danh-sach-phieu-huy", { params: data }),
  getDSChenNo: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/danh-sach-thue-bao-chen-no", { params: data }),
  doChenNo: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/chen-no", data),
/*

  updateXacNhanKetQuaCongNoTheoId: (axios, data) => axios.put("/web-thuno/api/thu-no/cong-no/xac-nhan-ket-qua-cong-no", data),
  getDanhSachCongNoDaGan: (axios, data) => axios.get("/web-thuno/api/thu-no/cong-no/ds-cong-no-da-gan", { params: data }),
  getDanhSachDonViXacNhanNo: (axios) => axios.get("/web-thuno/api/thu-no/xu-ly-no/don-vi-xac-nhan-no"),
  updateGiaoPhieuNhanVienXacNhan: (axios, data) => axios.put("/web-thuno/api/thu-no/cong-no/giao-phieu-nv-xac-nhan-cong-no", data),
*/
  getDSUserNEO: (axios) => axios.get("/web-thuno/api/thu-no/common/lay-ds-user-neo"),
  getDSDonViCTV: (axios, data) => axios.get('/web-thuno/api/thu-no/common/don-vi/loai-don-vi', {params: data}),
  getDSNhomCTV: (axios, data) => axios.get("/web-thuno/api/thu-no/common/lay-ds-nhom-nhan-vien/" + data),
  getDSGachNoVinaphone: (axios, data) => axios.put("/web-thuno/api/thu-no/tra-cuu/ds-gach-no-vinaphone", data),
  getDSPhieuTTVinaphone: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-ds-phieu-thanh-toan-vinaphone", {params: data}),
  getDSTBaoTToanVinaphone: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/ds-thue-bao-tt-vinaphone", {params: data}),
  dongboCCBSsangDHSX: (axios, data) => axios.put("/web-thuno/api/quan-ly-thu-no/dong-bo-ccbs-sang-dhsx", data),
  /** */


  gachNodatCoc: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-dieu-chinh-tien-dat-coc-tra-truoc/gach-no-dat-coc", data),

  /* Danh sách phiếu đã gạch nợ */
  getDanhSachNVTCTheoTuyen: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/tong-tien/nhan-vien-thu-cuoc", {params: data}),
  deletePhieuThanhToan: async (axios, data) => axios.delete('/web-thuno/api/thu-no/sp-xoa-phieu-thanh-toan', { data: data}),
  updateNgayThanhToan: (axios, data) => axios.put("/web-thuno/api/thu-no/sp-sua-ngay-thanh-toan", data),
  updateThongTinThanhToan: (axios, data) => axios.put("/web-thuno/api/quan-ly-thu-no/thong-tin-thanh-toan", data),
  updateSeriThanhToan: (axios, data) => axios.put("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/cap-nhat-so-seri-phieu-tra", data),
  updateSeriPhieuHuy: (axios, data) => axios.put("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/cap-nhat-phieu-huy", data),
  updateGhiChuPhieuTra: (axios, data) => axios.put("/web-thuno/api/thu-no/tra-cuu/cap-nhat-ghi-chu/phieu-tra", data),

  /** Điều chỉnh nợ */
  getChiTietKhoanMucDieuChinh: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/dieu-chinh-no/lay-chi-tiet-no-khoan-muc-dieu-chinh", {params: data}),
  getTieuChiTimKiem: (axios) => axios.get("/web-thuno/api/thu-no/common/ds-tieu-chi-tim-kiem/nhom-id/6"),
  getKhoanMucTT: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/ds-khoan-muc-da-thanh-toan", {params: data}),
  insertKhoanMucNo: (axios, data) => axios.post("/web-thuno/api/thu-no/tra-cuu/khoan-muc-chi-tiet-no", data),
  tracuuDSTTDieuChinhNo: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/dieu-chinh-no/tra-cuu-danh-sach-thanh-toan", data),
  dieuchinhNoLe: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/dieu-chinh-no/dieu-chinh-no-le", data),
  dieuchinhNo: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/dieu-chinh-no/dieu-chinh-no", data),
  dieuchinhNoDanhSach: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/dieu-chinh-no/dieu-chinh-no-danh-sach", data),
  dieuchinhNoLeCanBang: (axios, data) => axios.post("/web-thuno/api/thu-no/quan-ly-thu-no/dieu-chinh-no-can-bang/dieu-chinh-no", data),



  /* IN */
  printPhieuThu: (axios, data) => axios.post("/web-thuno/api/thu-no/gach-no/bien-nhan", data),

  suaThongTinLuotThanhToan: (axios, data) => axios.put("/web-thuno/api/thu-no/tra-cuu/cap-nhat-ghi-chu/phieu-tra", data),


  /* ĐỒNG BỘ CCBS & thanh toán CCBS*/
  syncCCBS: (axios, data) => axios.put("/web-thuno/api/thu-no/dong-bo/ccbs", data),
  syncCCBS2DHSX: (axios, data) => axios.post('/web-thuno/api/quan-ly-thu-no/dong-bo-vnp-theo-phieu', data),
  gachNoTrucTiepCCBS: (axios, data) => axios.post("/web-thuno/api/thu-no/dong-bo/gach-no-truc-tiep", data),


  /* ĐỒNG BỘ HDDT */
  syncHDDT: (axios, data) => axios.put("/web-thuno/api/thu-no/dong-bo/hoa-don-dien-tu", data),

  /* Ví VNPT Pay */

  
  getBalanceViVNPTPay: (axios) => axios.post("/tichhop/vnptpay/getBalance"),
  getViVNPTPayToken: (axios) => axios.post("/tichhop/vnptpay/login"),
  insertLogViVNPTPay: (axios, data) => axios.post("/web-thuno/api/thu-no/xu-ly-thue-bao/insert-logs-vnpt-pay-smp-v2", data),

  /* */

  getThanhToanTheoMaVach: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/tra-cuu-thong-tin-thanh-toan/ma-vach/" + data),
  
  /** */
  getThongTinSeriMaTT: (axios, data) => axios.get("/web-thuno/api/thu-no/common/thong-tin-seri/ma-tt/" + data),

  /* CCBS */

  // kiemtra_tuvan_mytv
  get_kiemtra_tuvan_mytv: async (axios, data) => axios.get(`/web-ccdv/tuvan_mytv/kiemtra_tuvan_mytv`,data)


};
