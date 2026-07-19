import API from '../../api/NhapVatTuMuaLeAPI'
// var test = [{
//     ISCHECK:"0",
//     CHUNGTU_ID:"812",
//     LOAICT_ID:"2",
//     TTPH_ID:"4",
//     TRANGTHAI:"HT",
//     SOPHIEU:"17/46.NSG-X",
//     KHO_GIAO_ID:"69",
//     KHO_NHAN_ID:"287",
//     DONVI_GIAO_ID:"35691",
//     DONVI_NHAN_ID:"35691",
//     ND_GIAO:"",
//     TTPH_ID:"4",
//     NGAY_CT:"06-03-2020 8:40:30",
//     NGAY_HT:"10-03-2020 10:37:28",
//     NHANVIEN_GIAO_ID:"1246",
//     LOAI:"1",
//     MA_DV_SD:"",
//     MA_CSHT:"",
//     MA_DV_QL:"",
//     MA_HD_PO:"",
//     DONVI_GIAO:"HCM130000 - Trung Tâm Cung ứng Vật Tư" ,                                              
//     DONVI_NHAN:"HCM211100 - Đội Viễn thông Cần Giờ - TTVT Nam Sài Gòn",
//     NHANVIEN_TH_ID:"39940",
//     NGAY_CN:"06-03-2020 9:03:16",
//     NGUOI_CN:"11036",
//     MAY_CN:"HOANK-CUVT",
//     IP_CN:"10.70.104.11",
//     KHO_GIAO:"46.NSG - Kho TTCUVT_Nam Sài Gòn",
//     KIEU:"2",
//     SPVT:"0",
//     SPBH:"0",
//     KHO_NHAN:"59.514 - Đội Viễn thông Cần Giờ - TTVT Nam Sài Gòn",
//     MUCDICH_ID:"10001",
//     MUCDICH:"Xuất vật tư cấp phát theo đăng ký",
//     LOAI_CT:"Xuất",
//     STATUS:"",
//     TIEN:"3800000",
//     VAT:"380000",
//     TONGTIEN:"4180000",
//     NGUOI_TH:"12972",
//     NGAYCT_GOC:"",
//     NGAYIN_PXK:"",
//     SOPHIEU_IN:"",
//     NGUOI_TIEPNHAN:"",
//     NGAY_VB:"",
//     SO_VB:"",
//     LOAIPHIEU_ID:"10106",
//     LOAIPHIEU:"LĐSC",
//     CONGTRINH_ID:"",
//     SOPHIEU_GOP:"",
//     NGAYGUI_TT:"10/03/2020",
//     NGAYNHAN_TT:"10/03/2020",
//     PHIEU_KT_ID:"544/20",
//     NGAYCT_KT:"",
//     MA_DH:"",
//     MA_HD:"",
//     SOPHIEUCHA:"",
//     NGUOI_XN:"05660",
//     NGUOI_CTKT:"",
//     SO_VB2:"396/VTNSG-TH",
//     NGAY_VB2:"02-03-2020",
//     MAPHIEU_DK:"NSG.CGI.20.03.29",
//     NGUOI_HT:"12972",
//     VANBAN_ID:"",
//     SL_VANBAN:"1",
//     SO_HOADON:"",
//     NGAY_HOADON:"",
//     SOLAN_IN:"1",
//     NGAY_XD:"",
//     NGUOI_XD:"",
//     NGHIEPVU_ID:"",
//     MA_PDA:"",
//     XACNHAN_IN:"",
//     DENGHI_ID:"",
//     MA_DN:"",    
//     },{
//     ISCHECK:"0",
//     CHUNGTU_ID:"54289",
//     LOAICT_ID:"2",
//     TTPH_ID:"4",
//     TRANGTHAI:"HT",
//     SOPHIEU:"X-1-21/000038",
//     KHO_GIAO_ID:"5766",
//     KHO_NHAN_ID:"5708",
//     DONVI_GIAO_ID:"36522",
//     DONVI_NHAN_ID:"36522",
//     ND_GIAO:"Xuất điểu chuyển ONT 2FE/GE+Wifi Dualband cho VNPT Bình Phước theo văn bản số 862 ĐH/KTM-VNPT Net ngày 09/06/2021",
//     TTPH_ID:"4",
//     NGAY_CT:"14-06-2021 9:53:55",
//     NGAY_HT:"14-06-2021 9:53:55",
//     NHANVIEN_GIAO_ID:"42468",
//     LOAI:"1",
//     MA_DV_SD:"",
//     MA_CSHT:"",
//     MA_DV_QL:"",
//     MA_HD_PO:"",
//     DONVI_GIAO:"" ,                                              
//     DONVI_NHAN:"",
//     NHANVIEN_TH_ID:"42468",
//     NGAY_CN:"14-06-2021 9:53:18",
//     NGUOI_CN:"nguyennth.dlc",
//     MAY_CN:"NGUYENNTH-DLC-1",
//     IP_CN:"10.55.200.180",
//     KHO_GIAO:"F.1.7 - --- Kho vật tư Trần Hoài Nam",
//     KIEU:"1",
//     SPVT:"0",
//     SPBH:"0",
//     KHO_NHAN:"100.9010 - Hải Lăng Tổng hợp - Vật tư công trình",
//     MUCDICH_ID:"18",
//     MUCDICH:"Xuất vật tư",
//     LOAI_CT:"Xuất",
//     STATUS:"",
//     TIEN:"0",
//     VAT:"0",
//     TONGTIEN:"0",
//     NGUOI_TH:"",
//     NGAYCT_GOC:"",
//     NGAYIN_PXK:"",
//     SOPHIEU_IN:"",
//     NGUOI_TIEPNHAN:"",
//     NGAY_VB:"",
//     SO_VB:"",
//     LOAIPHIEU_ID:"10114",
//     CONGTRINH_ID:"",
//     SOPHIEU_GOP:"",
//     NGAYGUI_TT:"",
//     NGAYNHAN_TT:"",
//     PHIEU_KT_ID:"",
//     NGAYCT_KT:"",
//     MA_DH:"",
//     MA_HD:"",
//     SOPHIEUCHA:"",
//     NGUOI_XN:"",
//     NGUOI_CTKT:"",
//     SO_VB2:"",
//     NGAY_VB2:"",
//     MAPHIEU_DK:"",
//     NGUOI_HT:"",
//     VANBAN_ID:"",
//     SL_VANBAN:"0",
//     SO_HOADON:"",
//     NGAY_HOADON:"",
//     SOLAN_IN:"",
//     NGAY_XD:"",
//     NGUOI_XD:"",
//     NGHIEPVU_ID:"",
//     MA_PDA:"",
//     XACNHAN_IN:"",
//     DENGHI_ID:"",
//     MA_DN:"",    
//     },
//     {
//     ISCHECK:"0",
//     CHUNGTU_ID:"21951",
//     LOAICT_ID:"3",
//     TTPH_ID:"4",
//     TRANGTHAI:"HT",
//     SOPHIEU:"C-DLC040504-20/000007",
//     KHO_GIAO_ID:"5725",
//     KHO_NHAN_ID:"5733",
//     DONVI_GIAO_ID:"36343",
//     DONVI_NHAN_ID:"36343",
//     ND_GIAO:"Chuyển đến 1007 - Kho khách hàng TTVT Bắc Đắk Lắk",
//     TTPH_ID:"4",
//     NGAY_CT:"30-03-2020 12:01:00",
//     NGAY_HT:"02-04-2020 17:41:02",
//     NHANVIEN_GIAO_ID:"42115",
//     LOAI:"1",
//     MA_DV_SD:"",
//     MA_CSHT:"",
//     MA_DV_QL:"",
//     MA_HD_PO:"",
//     DONVI_GIAO:"" ,                                              
//     DONVI_NHAN:"",
//     NHANVIEN_TH_ID:"42115",
//     NGAY_CN:"02-04-2020 17:40:54",
//     NGUOI_CN:"huyhq.dlc",
//     MAY_CN:"LAPTOP-T623I2VB",
//     IP_CN:"10.55.155.28",
//     KHO_GIAO:"035P180006035 - CT CPTM XL Thái Bình Dương tc ctr: LĐ tuyến cống bể cáp quang qua cầu Đình Vũ- đường Tân Vũ- Lạch huyện",
//     KIEU:"1",
//     SPVT:"0",
//     SPBH:"0",
//     KHO_NHAN:"20S18023VT - CT Lan Anh tc ctr: SC CB cáp tuyến ngầm hóa gdd1 khu Quang Minh -Thủy Nguyên",
//     MUCDICH_ID:"10183",
//     MUCDICH:"Đổi mã",
//     LOAI_CT:"Chuyển",
//     STATUS:"",
//     TIEN:"12741000",
//     VAT:"1274100",
//     TONGTIEN:"14015100",
//     NGUOI_TH:"",
//     NGAYCT_GOC:"",
//     NGAYIN_PXK:"",
//     SOPHIEU_IN:"",
//     NGUOI_TIEPNHAN:"",
//     NGAY_VB:"",
//     SO_VB:"",
//     LOAIPHIEU_ID:"10114",
//     CONGTRINH_ID:"",
//     SOPHIEU_GOP:"",
//     NGAYGUI_TT:"",
//     NGAYNHAN_TT:"",
//     PHIEU_KT_ID:"",
//     NGAYCT_KT:"",
//     MA_DH:"",
//     MA_HD:"",
//     SOPHIEUCHA:"",
//     NGUOI_XN:"",
//     NGUOI_CTKT:"",
//     SO_VB2:"",
//     NGAY_VB2:"",
//     MAPHIEU_DK:"",
//     NGUOI_HT:"",
//     VANBAN_ID:"",
//     SL_VANBAN:"0",
//     SO_HOADON:"",
//     NGAY_HOADON:"",
//     SOLAN_IN:"",
//     NGAY_XD:"",
//     NGUOI_XD:"",
//     NGHIEPVU_ID:"",
//     MA_PDA:"",
//     XACNHAN_IN:"",
//     DENGHI_ID:"",
//     MA_DN:"",    
//     },
//     {
//     ISCHECK:"0",
//     CHUNGTU_ID:"35753",
//     LOAICT_ID:"1",
//     TTPH_ID:"4",
//     TRANGTHAI:"HT",
//     SOPHIEU:"N-DLC040504-20/000293",
//     KHO_GIAO_ID:"5733",
//     KHO_NHAN_ID:"5725",
//     DONVI_GIAO_ID:"36343",
//     DONVI_NHAN_ID:"36343",
//     ND_GIAO:"Nhập kho vật tư thuê bao ngung hủy Tháng 09 Krông Buk",
//     TTPH_ID:"4",
//     NGAY_CT:"15-09-2020 17:18:47",
//     NGAY_HT:"15-09-2020 17:19:13",
//     NHANVIEN_GIAO_ID:"42115",
//     LOAI:"1",
//     MA_DV_SD:"",
//     MA_CSHT:"",
//     MA_DV_QL:"",
//     MA_HD_PO:"",
//     DONVI_GIAO:"" ,                                              
//     DONVI_NHAN:"",
//     NHANVIEN_TH_ID:"42115",
//     NGAY_CN:"15-09-2020 17:19:12",
//     NGUOI_CN:"huyhq.dlc",
//     MAY_CN:"LAPTOP-T623I2VB",
//     IP_CN:"10.55.155.49",
//     KHO_GIAO:"20S18023VT - CT Lan Anh tc ctr: SC CB cáp tuyến ngầm hóa gdd1 khu Quang Minh -Thủy Nguyên ",
//     KIEU:"2",
//     SPVT:"4",
//     SPBH:"0",
//     KHO_NHAN:"035P180006035 - CT CPTM XL Thái Bình Dương tc ctr: LĐ tuyến cống bể cáp quang qua cầu Đình Vũ- đường Tân Vũ- Lạch huyện",
//     MUCDICH_ID:"127",
//     MUCDICH:"Nhập vật tư bảo hành",
//     LOAI_CT:"Chuyển",
//     STATUS:"",
//     TIEN:"1136509",
//     VAT:"113650.9",
//     TONGTIEN:"1250159.9",
//     NGUOI_TH:"",
//     NGAYCT_GOC:"",
//     NGAYIN_PXK:"",
//     SOPHIEU_IN:"",
//     NGUOI_TIEPNHAN:"",
//     NGAY_VB:"",
//     SO_VB:"",
//     LOAIPHIEU_ID:"10107",
//     CONGTRINH_ID:"",
//     SOPHIEU_GOP:"",
//     NGAYGUI_TT:"",
//     NGAYNHAN_TT:"",
//     PHIEU_KT_ID:"",
//     NGAYCT_KT:"",
//     MA_DH:"",
//     MA_HD:"",
//     SOPHIEUCHA:"",
//     NGUOI_XN:"",
//     NGUOI_CTKT:"",
//     SO_VB2:"",
//     NGAY_VB2:"",
//     MAPHIEU_DK:"",
//     NGUOI_HT:"",
//     VANBAN_ID:"",
//     SL_VANBAN:"0",
//     SO_HOADON:"",
//     NGAY_HOADON:"",
//     SOLAN_IN:"",
//     NGAY_XD:"",
//     NGUOI_XD:"",
//     NGHIEPVU_ID:"",
//     MA_PDA:"",
//     XACNHAN_IN:"",
//     DENGHI_ID:"",
//     MA_DN:"",    
//     }]
export const actions = {
    async getDSQuyenNguoiDung ({ commit, state }, data) {
        let response = await API.getDSQuyenNguoiDung(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhMucLoaiCT ({ commit, state }, data) {
        let response = await API.getDanhMucLoaiCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhMucKho ({ commit, state }, data) {
        let response = await API.getDanhMucKho(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhMucMucDich ({ commit, state }, data) {
        let response = await API.getDanhMucMucDich(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getDSCongTrinh ({ commit, state }, data) {
        let response = await API.getDSCongTrinh(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhMucTinhTrangTB ({ commit, state }, data) {
        let response = await API.getDanhMucTinhTrangTB(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhMucChatLuong ({ commit, state }, data) {
        let response = await API.getDanhMucChatLuong(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhMucNghiepVu ({ commit, state }, data) {
        let response = await API.getDanhMucNghiepVu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getDSHopDongMuaSam ({ commit, state }, data) {
        let response = await API.getDSHopDongMuaSam(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSDuAn ({ commit, state }, data) {
        let response = await API.getDSDuAn(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getThamSoMacDinh ({ commit, state }, data) {
        let response = await API.getThamSoMacDinh(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getLayTenMenu ({ commit, state }, data) {
        let response = await API.getLayTenMenu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getAnHienCot ({ commit, state }, data) {
        let response = await API.getAnHienCot(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSChungTuTheoLoai ({ commit, state }, data) {
        let response = await API.getDSChungTuTheoLoai(this._vm.axios, data)
        try {
            return response.data.data || []
            // return test
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSVatTuChungTuV1 ({ commit, state }, data) {
        let response = await API.getDSVatTuChungTuV1(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getThongTinNgayDH ({ commit, state }, data) {
        let response = await API.getThongTinNgayDH(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getThongTinNgayChungTu ({ commit, state }, data) {
        let response = await API.getThongTinNgayChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getMaMucDichTheoID ({ commit, state }, data) {
        let response = await API.getMaMucDichTheoID(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getKieuChungTu ({ commit, state }, data) {
        let response = await API.getKieuChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSVanBanChungTu ({ commit, state }, data) {
        let response = await API.getDSVanBanChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getDSTraCuuVatTuChungTu ({ commit, state }, data) {
        let response = await API.getDSTraCuuVatTuChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSVatTuChungTuSerial ({ commit, state }, data) {
        let response = await API.getDSVatTuChungTuSerial(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraChungTuDieuChinh ({ commit, state }, data) {
        let response = await API.KiemTraChungTuDieuChinh(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraTonTaiChiTietVatTu ({ commit, state }, data) {
        let response = await API.KiemTraTonTaiChiTietVatTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async NapDSVatTu ({ commit, state }, data) {
        let response = await API.NapDSVatTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getTenTatMucDich ({ commit, state }, data) {
        let response = await API.getTenTatMucDich(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSLoaiKhoTruyenDan ({ commit, state }, data) {
        let response = await API.getDSLoaiKhoTruyenDan(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getIDLoaiChungTu ({ commit, state }, data) {
        let response = await API.getIDLoaiChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async CapNhatChungTuV2 ({ commit, state }, data) {
        let response = await API.CapNhatChungTuV2(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatChungTu ({ commit, state }, data) {
        let response = await API.CapNhatChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getKeys ({ commit, state }, data) {
        let response = await API.getKeys(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async SinhSoPhieuVatTuV5 ({ commit, state }, data) {
        let response = await API.SinhSoPhieuVatTuV5(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getIDChungTuCha ({ commit, state }, data) {
        let response = await API.getIDChungTuCha(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getIDChungTuTheoSoPhieu ({ commit, state }, data) {
        let response = await API.getIDChungTuTheoSoPhieu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatChungTuTheoKieu ({ commit, state }, data) {
        let response = await API.CapNhatChungTuTheoKieu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraCapNhatChungTu ({ commit, state }, data) {
        let response = await API.KiemTraCapNhatChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async KiemTraChiTietVatTu ({ commit, state }, data) {
        let response = await API.KiemTraChiTietVatTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraXoaPhieuDaHoanThanh ({ commit, state }, data) {
        let response = await API.KiemTraXoaPhieuDaHoanThanh(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getThongTinChungTuTheoPhieuGop ({ commit, state }, data) {
        let response = await API.getThongTinChungTuTheoPhieuGop(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getThongTinChungTuTheoCTCha ({ commit, state }, data) {
        let response = await API.getThongTinChungTuTheoCTCha(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async KiemTraKhoDenCoTonKho ({ commit, state }, data) {
        let response = await API.KiemTraKhoDenCoTonKho(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraHanChungTu ({ commit, state }, data) {
        let response = await API.KiemTraHanChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraSuaThuHoi ({ commit, state }, data) {
        let response = await API.KiemTraSuaThuHoi(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getTonKhoTheoID ({ commit, state }, data) {
        let response = await API.getTonKhoTheoID(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getThongTinQuyenTrenKho ({ commit, state }, data) {
        let response = await API.getThongTinQuyenTrenKho(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraLoaiKhoMaKho ({ commit, state }, data) {
        let response = await API.KiemTraLoaiKhoMaKho(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getThongTinTrangThaiPhieu ({ commit, state }, data) {
        let response = await API.getThongTinTrangThaiPhieu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async XoaChungTuBaoHanh ({ commit, state }, data) {
        let response = await API.XoaChungTuBaoHanh(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async GuiSMSChungTu ({ commit, state }, data) {
        let response = await API.GuiSMSChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async XoaPhieuDaHoanThanh ({ commit, state }, data) {
        let response = await API.XoaPhieuDaHoanThanh(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatDangKy ({ commit, state }, data) {
        let response = await API.CapNhatDangKy(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatVatTuDangKy ({ commit, state }, data) {
        let response = await API.CapNhatVatTuDangKy(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getIDTrangThaiPhieu ({ commit, state }, data) {
        let response = await API.getIDTrangThaiPhieu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async XoaTatCaChiTietChungTu ({ commit, state }, data) {
        let response = await API.XoaTatCaChiTietChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async XoaThongTinLienQuanCT ({ commit, state }, data) {
        let response = await API.XoaThongTinLienQuanCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async GiaoPhieuCTHoanTra ({ commit, state }, data) {
        let response = await API.GiaoPhieuCTHoanTra(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    
    async getSoPhieu ({ commit, state }, data) {
        let response = await API.getSoPhieu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraXoaChungTu ({ commit, state }, data) {
        let response = await API.KiemTraXoaChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraCTXuatHoanTraTheoSoPhieu ({ commit, state }, data) {
        let response = await API.KiemTraCTXuatHoanTraTheoSoPhieu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraCTXuatHoanTraCoCTCon ({ commit, state }, data) {
        let response = await API.KiemTraCTXuatHoanTraCoCTCon(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async KiemTraXoaDongBo ({ commit, state }, data) {
        let response = await API.KiemTraXoaDongBo(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async XoaThongTinLienQuanCT_V2 ({ commit, state }, data) {
        let response = await API.XoaThongTinLienQuanCT_V2(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getIDNhomNguoiDung ({ commit, state }, data) {
        let response = await API.getIDNhomNguoiDung(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraHanChungTuV2 ({ commit, state }, data) {
        let response = await API.KiemTraHanChungTuV2(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getMaPrefixLoHang ({ commit, state }, data) {
        let response = await API.getMaPrefixLoHang(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraThayDoiChiTietCT ({ commit, state }, data) {
        let response = await API.KiemTraThayDoiChiTietCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getIDLoaiKho ({ commit, state }, data) {
        let response = await API.getIDLoaiKho(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async XoaChiTietChungTu ({ commit, state }, data) {
        let response = await API.XoaChiTietChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getThongTinNgayNhanCT ({ commit, state }, data) {
        let response = await API.getThongTinNgayNhanCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatSerialV2 ({ commit, state }, data) {
        let response = await API.CapNhatSerialV2(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatSerialV2_1 ({ commit, state }, data) {
        let response = await API.CapNhatSerialV2_1(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSThietBiChungTu ({ commit, state }, data) {
        let response = await API.getDSThietBiChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraMaVatTu ({ commit, state }, data) {
        let response = await API.KiemTraMaVatTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async KiemTraDonViTinh ({ commit, state }, data) {
        let response = await API.KiemTraDonViTinh(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraTonVatTuCT ({ commit, state }, data) {
        let response = await API.KiemTraTonVatTuCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatChungTuThietBi ({ commit, state }, data) {
        let response = await API.CapNhatChungTuThietBi(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getThongTinPhieuTheoCTCha ({ commit, state }, data) {
        let response = await API.getThongTinPhieuTheoCTCha(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async KiemTraHoanThienCT ({ commit, state }, data) {
        let response = await API.KiemTraHoanThienCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async HoanThienChungTu ({ commit, state }, data) {
        let response = await API.HoanThienChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraHoanThanhCT ({ commit, state }, data) {
        let response = await API.KiemTraHoanThanhCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraIDChiTietQLTS ({ commit, state }, data) {
        let response = await API.KiemTraIDChiTietQLTS(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async CapNhatThongTinQLTS ({ commit, state }, data) {
        let response = await API.CapNhatThongTinQLTS(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatLog ({ commit, state }, data) {
        let response = await API.CapNhatLog(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async XoaTatCaChungTuThietBi ({ commit, state }, data) {
        let response = await API.XoaTatCaChungTuThietBi(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatSLChungTuVatTu ({ commit, state }, data) {
        let response = await API.CapNhatSLChungTuVatTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async CapNhatSoLanInCT ({ commit, state }, data) {
        let response = await API.CapNhatSoLanInCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatSoLanIn ({ commit, state }, data) {
        let response = await API.CapNhatSoLanIn(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraSerialTonTai ({ commit, state }, data) {
        let response = await API.KiemTraSerialTonTai(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraSerialDangCap ({ commit, state }, data) {
        let response = await API.KiemTraSerialDangCap(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async KiemTraLoHangCuaSerial ({ commit, state }, data) {
        let response = await API.KiemTraLoHangCuaSerial(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async XoaChungTuThietBiTheoTBI ({ commit, state }, data) {
        let response = await API.XoaChungTuThietBiTheoTBI(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getKieuNhapCap ({ commit, state }, data) {
        let response = await API.getKieuNhapCap(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhSachCap ({ commit, state }, data) {
        let response = await API.getDanhSachCap(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async TachCuonCap ({ commit, state }, data) {
        let response = await API.TachCuonCap(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getTongSoLuongTonTheoCTCT ({ commit, state }, data) {
        let response = await API.getTongSoLuongTonTheoCTCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async XoaChiTietThietBi ({ commit, state }, data) {
        let response = await API.XoaChiTietThietBi(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CopyChungTuV2 ({ commit, state }, data) {
        let response = await API.CopyChungTuV2(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getDSKhoVatTuLe ({ commit, state }, data) {
        let response = await API.getDSKhoVatTuLe(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSKieuPhieu ({ commit, state }, data) {
        let response = await API.getDSKieuPhieu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatGhiChuChiTietCT ({ commit, state }, data) {
        let response = await API.CapNhatGhiChuChiTietCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatTaiKhoanDoiUngNet ({ commit, state }, data) {
        let response = await API.CapNhatTaiKhoanDoiUngNet(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async CapNhatTaiKhoanDoiUngKhac ({ commit, state }, data) {
        let response = await API.CapNhatTaiKhoanDoiUngKhac(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraTonKho ({ commit, state }, data) {
        let response = await API.KiemTraTonKho(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getSLDatCho ({ commit, state }, data) {
        let response = await API.getSLDatCho(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSTonKhoTheoCT ({ commit, state }, data) {
        let response = await API.getDSTonKhoTheoCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async KiemTraUpdateSerial ({ commit, state }, data) {
        let response = await API.KiemTraUpdateSerial(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatMaThungCTTB ({ commit, state }, data) {
        let response = await API.CapNhatMaThungCTTB(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatGhiChuCTTB ({ commit, state }, data) {
        let response = await API.CapNhatGhiChuCTTB(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getChungTuTapDoan ({ commit, state }, data) {
        let response = await API.getChungTuTapDoan(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async KiemTraDongBo ({ commit, state }, data) {
        let response = await API.KiemTraDongBo(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async XoaDuLieuChungTu ({ commit, state }, data) {
        let response = await API.XoaDuLieuChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getTongSoLuongChuaGan ({ commit, state }, data) {
        let response = await API.getTongSoLuongChuaGan(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getIDCongTrinh ({ commit, state }, data) {
        let response = await API.getIDCongTrinh(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getThongTinNgayNhanNguoiNhan ({ commit, state }, data) {
        let response = await API.getThongTinNgayNhanNguoiNhan(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async KiemTraXacNhanChungTu ({ commit, state }, data) {
        let response = await API.KiemTraXacNhanChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatNgayGuiCT ({ commit, state }, data) {
        let response = await API.CapNhatNgayGuiCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatNgayChungTuHCM ({ commit, state }, data) {
        let response = await API.CapNhatNgayChungTuHCM(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getIDPhieuKT ({ commit, state }, data) {
        let response = await API.getIDPhieuKT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getThongTinNgayGui ({ commit, state }, data) {
        let response = await API.getThongTinNgayGui(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatNgayNhanCT ({ commit, state }, data) {
        let response = await API.CapNhatNgayNhanCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatNgayNhanTTChungTu ({ commit, state }, data) {
        let response = await API.CapNhatNgayNhanTTChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async InPhieuInKim ({ commit, state }, data) {
        let response = await API.InPhieuInKim(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSDuAnV1 ({ commit, state }, data) {
        let response = await API.getDSDuAnV1(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async GanHDDuAnChiTietCT ({ commit, state }, data) {
        let response = await API.GanHDDuAnChiTietCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async SinhQRCode ({ commit, state }, data) {
        let response = await API.SinhQRCode(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async KiemTraHuyGanMaDuAn ({ commit, state }, data) {
        let response = await API.KiemTraHuyGanMaDuAn(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async GoGanHDDuAnCTCT ({ commit, state }, data) {
        let response = await API.GoGanHDDuAnCTCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSDuAnV2 ({ commit, state }, data) {
        let response = await API.getDSDuAnV2(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSKhoTapDoan ({ commit, state }, data) {
        let response = await API.getDSKhoTapDoan(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getDSDongThietBi ({ commit, state }, data) {
        let response = await API.getDSDongThietBi(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSHeThong ({ commit, state }, data) {
        let response = await API.getDSHeThong(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getNoiDungGiaoCT ({ commit, state }, data) {
        let response = await API.getNoiDungGiaoCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatNgayChuyenCT ({ commit, state }, data) {
        let response = await API.CapNhatNgayChuyenCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async CapNhatNgayChungTu ({ commit, state }, data) {
        let response = await API.CapNhatNgayChungTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatNgayChungTuV2 ({ commit, state }, data) {
        let response = await API.CapNhatNgayChungTuV2(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatLoaiKhoTapDoan ({ commit, state }, data) {
        let response = await API.CapNhatLoaiKhoTapDoan(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatKhoTapDoan ({ commit, state }, data) {
        let response = await API.CapNhatKhoTapDoan(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async KiemTraVatTuDocfile ({ commit, state }, data) {
        let response = await API.KiemTraVatTuDocfile(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatNoiDungGiaoSuaCT ({ commit, state }, data) {
        let response = await API.CapNhatNoiDungGiaoSuaCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatChungTuVanBan ({ commit, state }, data) {
        let response = await API.CapNhatChungTuVanBan(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getThongTInTrangThaiPhieuV2 ({ commit, state }, data) {
        let response = await API.getThongTInTrangThaiPhieuV2(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async CapNhatNgayKyCT ({ commit, state }, data) {
        let response = await API.CapNhatNgayKyCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatSoPhieuIn ({ commit, state }, data) {
        let response = await API.CapNhatSoPhieuIn(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getThongTinTrangThaiPhieuV3 ({ commit, state }, data) {
        let response = await API.getThongTinTrangThaiPhieuV3(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatNguoiTiepNhanCT ({ commit, state }, data) {
        let response = await API.CapNhatNguoiTiepNhanCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async LayThongTinDuAn3rd ({ commit, state }, data) {
        let response = await API.LayThongTinDuAn3rd(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getMaCoSoHaTang ({ commit, state }, data) {
        let response = await API.getMaCoSoHaTang(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async SinhCTQuanLyTaiSan ({ commit, state }, data) {
        let response = await API.SinhCTQuanLyTaiSan(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async SinhChungTuIMSBienBan ({ commit, state }, data) {
        let response = await API.SinhChungTuIMSBienBan(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    
}