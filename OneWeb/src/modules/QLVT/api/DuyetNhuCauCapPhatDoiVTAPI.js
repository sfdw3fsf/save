export default {
  getThamSoMacDinh: (axios, data) => axios.get(`/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/tham-so-mac-dinh/${data}`),
  getDSQuyenND: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-quyen-nd/${data.nguoiDungId}`),
  getDSTenLoaiKhoTD: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-loai-khotd`),
  getDSMaKhoTD: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-khotd`),
  getDSKhoDonViDK: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/ds-donvi-dk`),

  getDSVatTuDaDK: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-vat-tu-da-dangky`),
  getDSVatTuMDDK: (axios, data) => axios.get(`/web-qlvt/api/dang-ky-cap-phat/ds-vattu-mucdich-dangky/${data.nhomMucDichId}`),
  getDSMucDich: (axios, data) => axios.get(`/web-qlvt/api/dang-ky-cap-phat/ds-muc-dich-dang-ky/${data.kieu}`),
  getDSVatTuDKTheoMD: (axios, data) => axios.get(`/web-qlvt/api/dang-ky-cap-phat/ds-vat-tu-dk-mucdich/${data.donViId}/${data.nhomMucDichId}`),

  getDSKho: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-kho-duyet-vat-tu/${data.tagFrm}/${data.nhanVienId}`),
  getDSKhoTheoDVVT: (axios, data) => axios.get(`/web-qlvt/api/nhan-chung-tu-hoan-tra/ds-kho-giao-ct-vt/${data.nhanVienId}`),
  getDSKhoDatDen: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-kho-nhap-xuat/${data.nhanVienId}`),

  getDSDangKyCapPhatV3: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-dang-ky-cap-phat`, data.body),
  getDanhSachDaDK: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-duyet-da-dang-ky/${data.dangKyId}`),

  getDSChungTu: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-chung-tu-duyet-dangky`, data),
  getDSVatTuTonKho: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-vat-tu-ton-kho_v2`, data.body),
  getDSCuonCap: (axios, data) => axios.post(`/web-qlvt/api/dang-ky-cap-phat/ds-cuon-cap`, data),
  getFileCVDK: (axios, data) => axios.get(`/web-qlvt/api/dang-ky-cap-phat/ds-file-cv-theo-dangky/${data.dangKyId}`),

  getTenMenu: (axios, data) => axios.post(`/web-qlvt/api/dang-ky-cap-phat/ds-ten-menu`, data),

  KiemTraNguoiDuyet: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-nguoi-duyet/${data.maNd}/${data.dangKyId}/${data.trangThai}`),
  NhanPhieuDuyetDK: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-nhan-phieu-duyet-dangky`, data),

  TraPhieuDKCapPhat: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-tra-phieu-dangky`, data),
  CapNhatVatTuDK: (axios, data) => axios.put(`/web-qlvt/api/duyet-dang-ky-cap-phat/cap-nhat-ghi-chu-vattu-dangky`, data),
  getSLVatTuDK: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-so-luong-vat-tu-dangky`, data),
  getSLDangKyTheoTT: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-so-luong-dangky-khi-duyet/${data.dangKyId}`),
  KiemTraTrangThaiDangKy: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-so-luong-dangky-theo-vattu/${data.dangKyId}`),

  CapNhatVatTuDKTheoFile: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/cap-nhat-vattu-dangky-theo-file`, data),
  SinhNoiDungDK: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-sinh-noidung-dang-ky/${data.dangKyId}/${data.chungTuId}`),
  TaoChungTuDKCapPhatV2: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/tao-chung-tu-dangky-cap-phat`, data),
  TaoChungTuDKCapPhatV3: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/tao-chung-tu-dangky-cap-phat`, data),

  TaoVatTuDk: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/tao-vat-tu-dang-ky`, data),
  SLChungTuDKTheoVT: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-chung-tu-dang-ky/${data.dangKyId}`),
  XoaVatTuDK: (axios, data) => axios.delete(`/web-qlvt/api/dang-ky-cap-phat/xoa-vattu-dky/${data.dangKyId}/${data.vtdkId}`),
  getDSVatTuChungTu: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-vattu-chungtu/${data.chungTuId}`),

  KiemTraHanChungTu: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-han-chung-tu`, data),
  ThongTinXacQuyenTrenKho: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-quyen-xn/${data.nhanVienId}/${data.khoId}`),
  ThongTinTrangThaiPhieu: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-thay-doi/${data.chungTuId}`),
  KiemTraXoaChungTu: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-xoa-chung-tu/${data.chungTuId}/${data.vma_nd}`),

  KiemTraXoaPhieuHoanThanh: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-xoa-phieu-da-ht/${data.chungTuId}`),
  KiemTraXoaDongBo: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-chung-tu-tap-doan/${data.chungTuId}`),
  XoaChungTuBaoHanh: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/xoa-chung-tu-bao-hanh`, data),
  XoaTatCaChiTietChungTu: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/xoa-tat-ca-chi-tiet-chung-tu?chung-tu-id=${data.chungTuId}`),

  XoaChungTu: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/xoa-chung-tu-duyet-vattu?chung-tu-id=${data.chungTuId}`),
  SLPhieuDKThayDoi: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-so-luong-phieu-thay-doi`, data),
  CapNhatDKTheoFile: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/cap-nhat-dang-ky-duyet`, data),
  getDSDatCho: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-dat-cho`, data),

  getDSVatTuLoHangBiTrung: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-dat-cho`, data),
  getNgayNhanThanhToan: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-ngay-nhan-thanhtoan-chungtu/${data.chungTuId}`),
  XoaChiTietChungTu: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/xoa-chi-tiet-chung-tu?ct-chitiet-id=${data.ctctId}`),
  getThongTinDSVatTu: (axios, data) => axios.post(`/web-qlvt/api/dang-ky-cap-phat/ds-vattu-theo-dang-ky-muc-dich`, data),

  xemBaoCao_TongHopTheoThang: (axios, data) => axios.post(`web-qlvt/api/bao-cao/bao-cao-in-phieu-vt-v2?extend=pdf`, data, { responseType: 'blob' }),
  xemBaoCao_DSDuyetDK: (axios, data) => axios.post(`web-qlvt/api/bao-cao/de-nghi-cap-vat-tu?extend=pdf`, data, { responseType: 'blob' }),
  XuatExcel_DSDuyetDK: (axios, data) => axios.post(`web-qlvt/api/bao-cao/de-nghi-cap-vat-tu?extend=xlsx`, data, { responseType: 'blob' }),
  GIAHAN_DATCHO_CHUNGTU: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/gia-han-dat-cho-chung-tu`, data),
  CapNhatDoiMucDich: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/cap-nhat-doi-muc-dich`, data),


  xemBaoCao_PXDDK: (axios, data) => axios.post(`web-qlvt/api/bao-cao/bao-cao-duyet-dang-ky-nhu-cau-vt?extend=pdf`, data, { responseType: 'blob' }),
  xemBaoCao_PDDK_NET: (axios, data) => axios.post(`web-qlvt/api/bao-cao/in-du-toan-vat-tu`, data, { responseType: 'blob' }),
  xoaChungTu3In1: (axios, data) => axios.post(`/web-qlvt/api/xoa-chung-tu/xoa`, data),

  getDSTonKhoDatCho: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/lay_ds_vattu_tonkho_datcho`, data),

  getLinkFile: (axios, data) => axios.post(`/web-qlvt/api/storage/getPresignedUrl`,data),
  checkFileExist: (axios, data) => axios.post(`/web-qlvt/api/storage/checkExistsFile`,data),   

  capNhatLyDoDuyet: (axios, data) => axios.put(`/web-qlvt/api/duyet-dang-ky-cap-phat/cap-nhat-ly-do-duyet`, data),
  getDSTinh: (axios, ) => axios.get(`/web-qlvt/api/luanchuyen-vattu/ds-tinh`),   
  taoChungTuKHDN: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/taochungtu-dk-capphat-khdn`, data),
  layDSDeNghiKHDN: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/lay-ds-denghi-khdn`, data),
  themSuaXoaDieuChuyen: (axios,data) => axios.post(`/web-qlvt//api/luanchuyen-vattu/ins-upd-luanchuyen`,data),
  getDanhSachDaDKKHDN: (axios, dangKyId) => axios.get(`/web-qlvt/api/dang-ky-cap-phat/lay-ds-dadangky-duyetcapphat-khdn/${dangKyId}`),
  getDSVatTuDeNghi: (axios,denghi_id) => axios.get(`/web-qlvt/api/de-nghi/ds-de-nghi-ct?deNghiId=${denghi_id}`),
}