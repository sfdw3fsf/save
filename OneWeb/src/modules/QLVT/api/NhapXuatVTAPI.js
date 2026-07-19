export default {
    getDSQuyenNguoiDung: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-quyen-nd/${data.nguoiDungId}`),
    getDSVatTu: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/ds-vat-tu`),
    getDanhMucLoaiCT_V2: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-loai-chung-tu`),
    getDanhMucLoaiCT: (axios, data) => axios.get(`/web-qlvt/api/chon-chung-tu-ccdc/danh-muc-loai-chung-tu`),

    getDanhMucKho: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-kho-nhap-xuat/${data.nhanVienId}`),
    getDanhMucMucDich: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-muc-dich-nhap-xuat/${data.nhomMdId}`),
    getDSCongTrinh: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-cong-trinh`),
    getDSHopDongMuaSam: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-hop-dong-mua-sam`),
    
    getDSDuAn: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-du-an/${data.chkDuAn}`),
    getDSDuAnTheoDonVi: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-du-an-theo-don-vi`),
    getDSChungTu_V2: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-chung-tu-v2`,data),
    getDSVatTuChungTu: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-vattu-chungtu/${data.chungTuId}`),
    getDSVatTuChungTuV1: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-vat-tu-chung-tu-v1/${data.chungTuId}`),

    getThongTinNgayDH: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/thong-tin-ngay-don-hang/${data.chungTuId}`),
    getThongTinNgayXuat: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-ngay-nhan-gui-thanhtoan/${data.chungTuId}`),
    getMaMucDichTheoID: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-ma-muc-dich-theo-muc-dich/${data.mucDichId}`),
    getKieuChungTu: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-kieu-chung-tu/${data.chungTuId}`),

    getDSTraCuuVatTuChungTu: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-vattu-chungtu/${data.chungTuId}`),
    getDSVatTuChungTuSerial: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-vattu-chung-tu-serial/${data.chungTuId}`),
    getDSThietBiChungTu: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-thietbi-chungtu/${data.chungTuId}`),
    getThamSoMacDinh: (axios, data) => axios.get(`/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/tham-so-mac-dinh/${data.kieuId}`),

    getKeys: (axios, data) => axios.post(`/web-qlvt/api/cap-phat-ccdc/get-key`,data),
    ThemMoiDuLieuChiTietCT: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-chi-tiet-chung-tu`,data),
    CapNhatDuLieuChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-chi-tiet-chung-tu`,data),
    ThemMoiChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-chung-tu`,data),
    CapNhatChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-chung-tu`,data),

    
    CapNhatDuLieuChungTuTBi: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-chung-tu-thiet-bi?kho-den-id=${data.khoDenId}&chung-tu-id=${data.chungTuId}`),

    XoaTatCaChiTietChungTu: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/xoa-tat-ca-chi-tiet-chung-tu?chung-tu-id=${data.chungTuId}`),
    CapNhatDonHangChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-don-hang-chung-tu`,data),
    CapNhatVatTuChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-vat-tu-chung-tu`,data),
    CapNhatChiTietChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-trang-thai-chi-tiet-ct`,data),
    
    CapNhatVatTuMuaLe: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-vat-tu-mua-le`,data),
    KiemTraCapNhatChungTu: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-cap-nhat-chung-tu/${data.chungTuId}`),
    KiemTraXoaPhieuDaHoanThanh: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-xoa-phieu-hoan-thanh/${data.chungTuId}`),
    KiemTraHanChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-han-chung-tu`,data),
    
    KiemTraSuaThuHoi: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-sua-thu-hoi/${data.chungTuId}`),
    SinhSoPhieuVatTuV3: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/sinh-so-phieu-vat-tu-v3`,data),
    getIDLoaiKho: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-loai-kho-theo-kho/${data.khoId}`),
    getMaPrefixLoHang: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-prefix-lo-hang/${data.loHang}`),
    
    XoaChiTietChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/xoa-chi-tiet-chung-tu?ctct-id=${data.ctctId}`),
    getThongTinNgayNhanCT: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-ngay-nhan-thanhtoan-chungtu/${data.chungTuId}`),
    getThongTinQuyenTrenKho: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-quyen-xn/${data.nhanVienId}/${data.khoId}`),
    CapNhatSerialV2: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-serial`,data),
    CapNhatSerialV2_1: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-serial-v1`,data),
    
    KiemTraMaVatTuTonTai: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-vat-tu-ton-theo-ma/${data.maVatTu}`),
    KiemTraDonViTinh: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-don-vi-tinh/${data.donViTinh}`),
    KiemTraTonVatTuCT: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-ton-vattu-chung-tu`,data),
    CapNhatChungTuThietBi: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-chung-tu-thiet-bi-excel`,data),
    
    getThongTinTrangThaiPhieu: (axios, data) => axios.get(`/web-qlvt/api/duyet-dang-ky-cap-phat/kiem-tra-thay-doi/${data.chungTuId}`),
    
    XoaChungTuBaoHanh: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/xoa-chung-tu-bao-hanh`,data),
    GuiSMSChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/send-sms-chung-tu`,data),
    
    XoaPhieuDaHoanThanh: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/xoa-phieu-da-hoan-thanh`,data),
    CapNhatDangKy: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-dang-ky?chung-tu-id=${data.chungTuId}&dang-ky-id=${data.dangKyId}`),
    CapNhatVatTuDangKy: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-vat-tu-dang-ky`,data),
    getDSChiTietChungTu: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-chi-tiet-chung-tu`),
    
    getMaMucDichTheoChungTu: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/ma-muc-dich-theo-chung-tu-id?chung-tu-id=${data.chungTuId}`),
    CapNhatTrangThaiChiTietCT: (axios, data) => axios.put(`/web-qlvt/api/nhap-xuat-vt/cap-nhat-trang-thai-chi-tiet-chung-tu?chung-tu-id=${data.chungTuId}`),
    getSoPhieu: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/so-phieu-theo-chung-tu-id?chung-tu-id=${data.chungTuId}`),
    KiemTraXoaDongBo: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-xoa-dong-bo`,data),
    
    XoaChungTu: (axios, data) => axios.delete(`/web-qlvt/api/nhap-xuat-vt/xoa-chung-tu?chung-tu-id=${data.chungTuId}`),
    getIDTrangThaiPhieu: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/trang-thai-phieu-tu-chung-tu-id?chung-tu-id=${data.chungTuId}`),
    KiemTraCanhBaoHoanThienCT: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/kiem-tra-canh-bao-hoan-thien-chung-tu?chung-tu-id=${data.chungTuId}`),
    KiemTraHoanThanhCT: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/kiem-tra-hoan-thien-chung-tu?chung-tu-id=${data.chungTuId}`),
    
    KiemTraHoanThienCT: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/kiem-tra-hoan-thien-chung-tu-v1?chung-tu-id=${data.chungTuId}`),
    HoanThienChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-vt/hoan-thien-chung-tu`,data),
    XoaTatCaChungTuThietBi: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/xoa-tat-ca-chung-tu-thiet-bi?chung-tu-id=${data.chungTuId}`),
    KiemTraSerialTonTai: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/kiem-tra-ton-tai-serial?serial=${data.serial}`),
    
    KiemTraSerialDangCap: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/kiem-tra-serial-dang-cap?serial=${data.serial}`),
    KiemTraLoHangCuaSerial: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/kiem-tra-lo-hang-serial?serial=${data.serial}&chung-tu-id=${data.chungTuId}`),
    XoaCTThietBiTheoTBi: (axios, data) => axios.delete(`/web-qlvt/api/nhap-xuat-vt/xoa-chung-tu-theo-ctct-va-tbi-id?ctct-id=${data.ctctId}&thiet-bi-id=${data.thietBiId}`),
    getKieuNhapCap: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/kieu-nhap-cap?vat-tu-id=${data.vatTuId}&kho-id=${data.khoId}&lo-hang-id=${data.loHang}`),
    
    getDanhSachCap: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/danh-sach-cap?ctct-id=${data.ctctId}`),
    TachCuonCap: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-vt/tach-cuon-cap`,data),
    getTongSoLuongTonTheoCTCT: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/so-luong-thiet-bi?ctct-id=${data.ctctId}`),
    CopyChungTuV2: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/copy-chung-tu-v2?chung-tu-id=${data.chungTuId}&kieu=${data.kieu}`),
    
    getDSKhoVatTuLe: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/kho-vat-tu-le?nhan-vien-id=${data.nhanVienId}&don-vi-id=${data.donViId}`),
    getDSKhoTheoNV: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/kho-theo-nhan-vien?nhan-vien-id=${data.nhanVienId}`),
    KiemTraTonKho: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-vt/kiem-tra-ton-kho`,data),
    getDSTonKhoTheoCT: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/ton-kho-theo-chung-tu?chung-tu-id=${data.chungTuId}`),
    
    KiemTraUpdateSerial: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-vt/kiem-tra-cap-nhat-so-serial`,data),
    CapNhatGhiChuCTTB: (axios, data) => axios.put(`/web-qlvt/api/nhap-xuat-vt/cap-nhat-ghi-chu-chung-tu-thiet-bi`,data),
    CapNhatMaThungCTTB: (axios, data) => axios.put(`/web-qlvt/api/nhap-xuat-vt/cap-nhat-ma-thung-chung-tu-thiet-bi?ma-thung=${data.maThung}&ctct-id=${data.ctctId}&tbi-id=${data.thietBiId}`),
    getChungTuTapDoan: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/id-chung-tu?chung-tu-id=${data.chungTuId}`),
    
    KiemTraDongBo: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-dong-bo-fmis`,data),
    XoaDuLieuChungTu: (axios, data) => axios.delete(`/web-qlvt/api/nhap-xuat-vt/xoa-chung-tu-theo-id?chung-tu-id=${data.chungTuId}`),
    CapNhatNgayXacNhanCT: (axios, data) => axios.put(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-ngay-gui-ct`,data),
    getThongTinNgayGui: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/ngay-gui?chung-tu-id=${data.chungTuId}`),
    
    CapNhatNVThucHienCT: (axios, data) => axios.put(`/web-qlvt/api/nhap-xuat-vt/cap-nhat-nhan-vien-thuc-hien-chung-tu`,data),
    CapNhatXoaNgayNhanCT: (axios, data) => axios.put(`/web-qlvt/api/nhap-xuat-vt/xoa-ngay-nhan-chung-tu?chung-tu-id=${data.chungTuId}`),
    InPhieuInKim: (axios, data) => axios.get(`/web-qlvt/api`),
    getThamSoTheoNguoiDung: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/gia-tri-tham-so-theo-nguoi-dung?nguoi-dung-id=${data.nguoiDungId}&ma-ts=${data.maTS}`),
    
    getDanhMucDuAn: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/danh-muc-du-an?du-an-check=${data.duAn}`),
    GanHDDuAnChiTietCT: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-vt/cap-nhat-thong-tin-ctct`,data),
    KiemTraChiTietVatTuCoDauVao: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-vt/kiem-tra-dau-vao-ctct`,data),
    GoGanHDDuAnCTCT: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-vt/xoa-thong-tin-du-an`,data),
    
    RefreshDanhMucDuAn: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/refresh-danh-muc-du-an?du-an-check=${data.duAn}`),
    CapNhatTinhTrangCTTBi: (axios, data) => axios.put(`/web-qlvt/api/nhap-xuat-vt/cap-nhat-tinh-trang-cttb`,data),
    CapNhatLoHang: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-vt/cap-nhat-lo-hang`,data),
    getDSChungTuNhapLe: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-vt/danh-sach-chung-tu-nhap-le`,data),
    
    getDSChungTuTheoVatTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-vt/danh-sach-chung-tu-theo-vat-tu`,data),
    getQuyTrinhHuongGiaoTheoCT: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/quy-trinh-huong-giao-theo-chung-tu?chung-tu-id=${data.chungTuId}`),
    getHuongGiaoTiepTheo: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-vt/huong-giao-tiep-theo?huong-giao-id=${data.huongGiaoId}`),
    getNoiDungGiaoChungTu: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/noi-dung-giao-ct/${data.chungTuId}`),

    getDSSerialNullIMS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/lay-ds-serial-null-ims`,data),
    CapNhatSerialNullIMS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-serial-ims`,data),
    getDanhMucNghiepVu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-nghiep-vu`, data),
    getDSTinh: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-tinh`),

    getDSDonViSuDung: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-don-vi-su-dung/${data.kieu}`),
    getDSThamSoMacDinhTheoMaTS: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/lay-ds-thamso-md-mats/${data.maTS}`),

    getMaCSHT: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ma-co-so-ha-tang/${data.khoId}`),
    KT_PHIEU_QT: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-phieu-qt/${data.chungTuId}`),
    
    // KT_PHIEU_QT
    KIEMTRA_XOA_CHUNGTU: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-xoa-chung-tu/${data.chungTuId}`),
    KIEMTRA_TRANGTHAI_CT: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-trang-thai-ct/${data.chungTuId}`),
    XOA_GIAOPHIEU_CHUNGTU: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-xoa-giao-phieu-ct/${data.chungTuId}`),
    GAN_DENGHI_CHUNGTU: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/xu-ly-gan-de-nghi-chung-tu`,data),
    Insert_HDMS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/get-key-hd-mua-sam`,data),

    // THUHOI_QLTS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-ref-ims`,data),
    getContractInfoByProjectCode: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/contract-info-by-project-code?maDuan=${data.maDuan}&maCsht=${data.maCsht}`),
    getAssetByKey: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/asset-by-keys`,data),
    sinh_chungtu_ims_bbbg: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/sinh-ct-ims-bbg`,data),
    

    // Get_ListProject
    // FUNC_GAN_DENGHI_CHUNGTU

    //Đức thêm cho DeNghiVT    
    getDSChungTuDeNghi: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/chung-tu-de-nghi?tatCa=${data.tatCa}&tuNgay=${data.tuNgay}&denNgay=${data.denNgay}`),
    getQuyTrinhHuongGiaoTheoDeNghiId: (axios,denghi_id) => axios.get(`/web-qlvt/api/nhap-de-nghi-vt/huong-giao-tiep-theo-id/${denghi_id}`),
    capNhatDeNghiChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-de-nghi-vt/cap-nhat-chung-tu-de-nghi`, data),

    //Đức thêm cho IMS 
    getDSDonViFMIS: (axios, ) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-don-vi-fmis`),

    kiemTraXoaIMS: (axios, chungTuId) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-xoa-ims/${chungTuId}`),
    KiemTraTrangThaiIMS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-trang-thai-ims`,data),
    capNhatBBBGIMS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-bbbg-ims`,data),
    kiemTraChungTuDongBo: (axios, chungTuId) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-chung-tu-dong-bo/${chungTuId}`),

    kiemTraDongBoIMS: (axios, chungTuId) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-dong-bo-ims/${chungTuId}`),
    xuLyDongBoIMS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/xu-ly-dong-bo-ims`,data),
    // getDSCSHT: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/nap-ds-cs-ht`,data),


    getDSRefIMS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-ref-ims`,data),
    getDSPhieuNV: (axios, mucDichId) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-phieu-nv/${mucDichId}`),
    getDSCSHT: (axios, data) => axios.post(`/tichhop/qlts/asset_get_station_by_code`,data),
    getDSAssetTheoMaDuAn: (axios, MaDuAn) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-assset-by-project?maDuan=${MaDuAn}`),
    getDSVatTuTheoNghiepVu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-vat-tu-theo-nvu`,data),
    KiemTraDonGiaIMS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-don-gia-ims`,data),
    getDSVatTuTonKho: (axios, data) => axios.post(`/web-qlvt/api/duyet-dang-ky-cap-phat/ds-vat-tu-ton-kho`,data),
    getContractInfo: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/contract-info-by-project-code?maDuan=${data.maDuan}&maCsht=${data.maCsht}`),
    kiemTraLocCSHT: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-loc-csht`,data),
    getAssetByKeys: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/asset-by-keys`,data),
    kiemTraSoLuongQLTS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-sl-qlts`,data),
    getDanhMucLoaiChungTu: (axios, kieu) => axios.get(`/web-qlvt/api/chon-chung-tu-ccdc/danh-muc-loai-chung-tu-nx/${kieu}`),
    kiemTraKhoTon: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-ton-kho-den-kho/${data.khoId}/${data.kieu}`),
    sinhDieuChuyenQLTS: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/sinh-dieu-chuyen-qlts`,data),
    getMaCoSoHaTang: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ma-co-so-ha-tang/${data.khoId}`),
    SinhChungTuIMSBienBan: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/sinh-ct-ims-bbg`,data),
    capNhatNgayChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-ngay-ct-nx`,data),
    xoaChungTu3In1: (axios, data) => axios.post(`/web-qlvt/api/xoa-chung-tu/xoa`,data),
    KiemTraVatTuDocfile: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-vt-doc-file`,data),
    ghiLaiAllInOne: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/taomoi-capnhat-chungtu`,data),
    getDSChungTu_V3: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-chung-tu-v3`,data),
    uploadFileVatTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiemtra-ds-vattu-tao_chungtu`,data),
    getDSPhieuInDeAn07: (axios, data) => axios.get(`/web-qlvt/api/bao-cao/ds-bao-cao-nhap-xuat`,data),
    getParamsBaoCao: (axios, maBc) => axios.get(`/web-qlvt/api/in-phieu/ds-param-bc?maBc=${maBc}`),
    getDSFileChungTu: (axios, chungTuId) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-van-ban-ct/${chungTuId}`),
    
    getLinkFile: (axios, data) => axios.post(`/web-qlvt/api/storage/getPresignedUrl`,data),
    checkFileExist: (axios, data) => axios.post(`/web-qlvt/api/storage/checkExistsFile`,data),    
    deleteFile: (axios, data) => axios.post(`/web-qlvt/api/storage/deleteFile`,data),
    uploadFile: (axios, data) => axios.post(`/web-qlvt/api/storage/uploadFile`,data),
    uploadFileCVChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/cap-nhat-ct-van-ban`,data),
    kiemTraXoaFileChungTu: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiemtra-xoa-file-chungtu`,data),
}