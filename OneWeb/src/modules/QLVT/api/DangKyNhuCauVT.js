export default {
  getDSThoiGianDK: (axios, ckdkId) =>
    axios.get(`/web-qlvt/api/dang-ky-cap-phat/ds-chu-ki-dang-ky/${ckdkId}`),
  getDotDK: axios => axios.get("/web-qlvt/api/dang-ky-cap-phat/ds-dot-dang-ky"),
  getDuAnNV: (axios, nhanVienId) =>
    axios.get(
      `/web-qlvt/api/dang-ky-cap-phat/ds-du-an-theo-nhanvien/${nhanVienId}`
    ),
  getDSCongTrinh: (axios, data) =>
    axios.post("/web-qlvt/api/dang-ky-cap-phat/ds-cong-trinh", data),
  getNhanVienLienHe: (axios, nhanVienId) =>
    axios.get(
      `/web-qlvt/api/dang-ky-cap-phat/ds-nhan-vien-lien-he/${nhanVienId}`
    ),
  getKhoDVCungUng: axios =>
    axios.get(`/web-qlvt/api/dang-ky-cap-phat/ds-kho-theo-donvi-vattu`),
  getDSDangKyNhuCauTinhTrang: (axios, data) =>
    axios.post(
      "/web-qlvt/api/dang-ky-cap-phat/ds-nhu-cau-theo-tinhtrang",
      data
    ),
  getDSVatTuDaDK: (axios, dangKyId) =>
    axios.get(
      `/web-qlvt/api/tra-cuu-ton-kho-user/ds-da-dangky/${dangKyId}`
    ),
  getDSCuonCapTheoKho: (axios, khoId) =>
    axios.get(`/web-qlvt/api/dang-ky-cap-phat/ds-cuon-cap-theo-kho/${khoId}`),
  getDSMucDichDK: (axios, kieu) =>
    axios.get(
      `/web-qlvt/api/dang-ky-cap-phat/ds-muc-dich-dang-ky/${kieu}`
    ),
  getDSVatTuMD: (axios, donViId, nhomMd) =>
    axios.get(
      `/web-qlvt/api/dang-ky-cap-phat/ds-vat-tu-dk-mucdich/${donViId}/${nhomMd}`
    ),
  getDSVatTuTimKiem: (axios) =>
    axios.get(
      "/web-qlvt/api/dang-ky-cap-phat/ds-vat-tu-tim-kiem"
    ),
  deleteVatTuDangKy: (axios, dangKyId, vtuDkId) =>
    axios.delete(
      `/web-qlvt/api/dang-ky-cap-phat/xoa-vattu-dky/${dangKyId}/${vtuDkId}`
    ),
  getVatTuTon: (axios) =>
    axios.get("/web-qlvt/api/dang-ky-cap-phat/ds-vat-tu-ton"),
  getVattu_MDDK: (axios, nhomMucDichId) =>
    axios.get(
      `/web-qlvt/api/dang-ky-cap-phat/ds-vattu-mucdich-dangky/${nhomMucDichId}`
    ),
  deleteDuLieuVT: (axios, dangKyId) =>
    axios.delete(
      `/web-qlvt/api/dang-ky-cap-phat/xoa-dang-ky/${dangKyId}`
    ),
  kiemTraDangKyCT: (axios, data) =>
    axios.post(
      "/web-qlvt/api/dang-ky-cap-phat/kiem-tra-dky-cong-trinh", data
    ),
  kiemTraDSDangKyTheoTime: (axios, data) =>
    axios.post(
      "/web-qlvt/api/dang-ky-cap-phat/ds-dangky-theo-thoi-gian", data
    ),
  themMoiVatTuDK: (axios, data) =>
    axios.post(
      "/web-qlvt/api/dang-ky-cap-phat/vat-tu-dang-ky", data
    ),
  getKeyDKVTMuaSam: (axios, data) => axios.post("/web-qlvt/api/cap-phat-ccdc/get-key", data),
  checkInfoVatTu: (axios, data) => axios.post("/web-qlvt/api/dang-ky-cap-phat/ds-vattu-theo-dang-ky-muc-dich", data),
  addDuLieuVatTuDKv2: (axios, data) => axios.post("/web-qlvt/api/dang-ky-cap-phat/vat-tu-dang-ky-v2", data),
  getDSThamSoMacDinh: (axios) => axios.get("/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/tham-so-mac-dinh/0"),
  getDSDangKy_frmCopy: (axios, data) => axios.post("/web-qlvt/api/copy-dang-ky-vat-tu/ds-vat-tu-dang-ky", data),
  postCapNhatVatTuDK: (axios, data) => axios.post("/web-qlvt/api/dang-ky-cap-phat/cap-nhat-vat-tu-dang-ky", data),
  IU_DANGKY_TT: (axios, data) => axios.post("/web-qlvt/api/dang-ky-cap-phat/vat-tu-dang-ky-trung-tam", data),
  getVattu_MDDK_v2: (axios) => axios.get("/web-qlvt/api/nhap-de-nghi-vt/ds-vt-muc-dich"),
  getDSKhoTheoNV: (axios, data) => axios.post("/web-qlvt/api/dang-ky-cap-phat/ds-kho-theo-quyen-nhanvien", data),
  listVTThuocNhomMDDK: (axios) => axios.get("/web-qlvt/api/dang-ky-cap-phat/ds_vattu_thuoc_nhom_mucdich_dangky"),

  // Đức thêm vào để fix excel
  getVattuMucDich: (axios, nhomMucDichId) => axios.get(`/web-qlvt/api/dang-ky-cap-phat/ds-vattu-mucdich-dangky/${nhomMucDichId}`),

  // API KHDN
  themMoiVatTuKHDN: (axios, data) => axios.post("/web-qlvt/api/dang-ky-cap-phat/vat-tu-dang-ky-khdn", data),
  getVattuMucDichKHDN: (axios, nhomMucDichId) => axios.get(`/web-qlvt/api/dang-ky-cap-phat/ds-vattu-mucdich-dangky-khdn/${nhomMucDichId}`),
  addDuLieuVatTuDKKHDN: (axios, data) => axios.post("/web-qlvt/api/dang-ky-cap-phat/iu-vtdk-khdn", data),
  getDSVatTuDaDKKHDN: (axios, dangKyId) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-duyet-da-dang-ky-khdn/${dangKyId}`),
  getDSDangKY_CDK: (axios, data) => axios.post("/web-qlvt/api/duyet-dang-ky-cap-phat/lay-ds-dk-duyetcapphat", data),
  getDSChiTietDangKY_CDK: (axios, data) => axios.post("/web-qlvt/api/duyet-dang-ky-cap-phat/lay-ds-vtdk-capphat", data),
  chapNhanDangKy: (axios, data) => axios.post("/web-qlvt/api/duyet-dang-ky-cap-phat/ins-del-ct-vattu-dk", data),
  getDSXemDangKy: (axios, vdangky_ms_id) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/lay-ds-ct-vattu-dk?vdangky_ms_id=${vdangky_ms_id}`),
  xoaVatTuDangKyKHDN: (axios, data) => axios.post("/web-qlvt/api/dang-ky-cap-phat/del-vattu-dangky2key-khdn", data),
  getVatTuDangKyKHDN: (axios, data) => axios.post("/web-qlvt/api/dang-ky-cap-phat/getvt-dkvt-khdn", data),
  XoaDangKyKHDN: (axios, dangKyId) => axios.get(`/web-qlvt/api/dang-ky-cap-phat/xoa-dang-ky-khdn/${dangKyId}`),    
};


