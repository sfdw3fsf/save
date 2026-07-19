import API from '../../api/NhapXuatVT_V2_API'

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
    async getDSTraCuuVatTuChungTuHCM ({ commit, state }, data) {
        let response = await API.getDSTraCuuVatTuChungTuHCM(this._vm.axios, data)
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
            return response.data.data || ""
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
    async HoanThienChungTuHCM ({ commit, state }, data) {
        let response = await API.HoanThienChungTuHCM(this._vm.axios, data)
        try {
            return response.data.data || ""
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
        return response.data.data        
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
            return response.data.data
        } catch (error) {
            throw error
        }
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
    
    async getDSThamSoMacDinh ({ commit, state }, data) {
        let response = await API.getDSThamSoMacDinh(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getDSTinh ({ commit, state }, ) {
        let response = await API.getDSTinh(this._vm.axios, )
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSDonViSuDung ({ commit, state }, kieu) {
        let response = await API.getDSDonViSuDung(this._vm.axios, kieu)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async kiemTraChungTuCha ({ commit, state }, chungTuId) {
        let response = await API.kiemTraChungTuCha(this._vm.axios, chungTuId)
        try {
            return response.data || 0
        } catch (error) {
            throw error
        }
        return response
    },
    async capNhapDonGiaChiTietCT ({ commit, state }, data) {
        let response = await API.capNhapDonGiaChiTietCT(this._vm.axios, data)
        try {
            return response.data || 0
        } catch (error) {
            throw error
        }
        return response
    },
    async kiemTraChungTuDongBo ({ commit, state }, chungTuId) {
        let response = await API.kiemTraChungTuDongBo(this._vm.axios, chungTuId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async capNhapMaIMS ({ commit, state }, chungTuId) {
        let response = await API.capNhapMaIMS(this._vm.axios, chungTuId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getHuongGiaoTiepTheo ({ commit, state }, denghi_id) {
        let response = await API.getHuongGiaoTiepTheo(this._vm.axios, denghi_id)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async layDSSerialNullIMS ({ commit, state }, data) {
        let response = await API.layDSSerialNullIMS(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },

    async capNhatSerialNullIMS ({ commit, state }, data) {
        let response = await API.capNhatSerialNullIMS(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },

    async uploadFile({ commit, state },data) {
        let response = await API.uploadFile(this._vm.axios,data)
        try {
            return response.data.data || {}
        } catch (error) {
            throw error
        }
        return response
    },
    async downloadFile({ commit, state },linkFile) {
        let response = await API.downloadFile(this._vm.axios,linkFile)
        try {
            return response.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getLinkFile({ commit, state },data) {
        let response = await API.getLinkFile(this._vm.axios,data)
        try {
            return response.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async checkFileExist({ commit, state },data) {
        let response = await API.checkFileExist(this._vm.axios,data)
        try {
            return response.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    
    async deleteFile({ commit, state },data) {
        let response = await API.deleteFile(this._vm.axios,data)
        try {
            return response.data || []
        } catch (error) {
            throw error
        }
    },

    async updateFileVB ({ commit, state }, data) {
        let response = await API.updateFileVB(this._vm.axios, data)
        try {
            return response.data.data || 0
        } catch (error) {
            throw error
        }
    },

    async kiemTraDongBoIMS ({ commit, state }, chungTuId) {
        let response = await API.kiemTraDongBoIMS(this._vm.axios, chungTuId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }        
    },

    async xuLyDongBoIMS ({ commit, state }, data) {
        let response = await API.xuLyDongBoIMS(this._vm.axios, data)
        try {
            return response.data.data
        } catch (error) {
            throw error
        }       
    },
    async xuLyDongBoIMS ({ commit, state }, data) {
        let response = await API.xuLyDongBoIMS(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }       
    },
    async kiemTraXuatHoanTra ({ commit, state }, data) {
        let response = await API.kiemTraXuatHoanTra(this._vm.axios, data)
        try {
            return response.data.data || ""
        } catch (error) {
            throw error
        }       
    },
    async kiemTraXoaIMS ({ commit, state }, chungTuId) {
        let response = await API.kiemTraXoaIMS(this._vm.axios, chungTuId)
        return response.data.data              
    },
    async KiemTraTrangThaiIMS ({ commit, state }, data) {
        let response = await API.KiemTraTrangThaiIMS(this._vm.axios, data)
        return response.data.data              
    },
    async capNhatBBBGIMS ({ commit, state }, data) {
        let response = await API.capNhatBBBGIMS(this._vm.axios, data)
        return response.data.data              
    },
    async getDSRefIMS ({ commit, state }, data) {
        let response = await API.getDSRefIMS(this._vm.axios, data)
        return response.data.data              
    },
    async getDSPhieuNV ({ commit, state }, mucDichId) {
        let response = await API.getDSPhieuNV(this._vm.axios, mucDichId)
        return response.data.data              
    },
    async getDSCSHT ({ commit, state }, data) {
        let response = await API.getDSCSHT(this._vm.axios, data)
        return response.data.data              
    },
    async getDSAssetTheoMaDuAn ({ commit, state }, MaDuAn) {
        let response = await API.getDSAssetTheoMaDuAn(this._vm.axios, MaDuAn)
        return response.data.data             
    },
    async getDSVatTuTheoNghiepVu ({ commit, state }, MaDuAn) {
        let response = await API.getDSVatTuTheoNghiepVu(this._vm.axios, MaDuAn)
        return response.data.data             
    },
    async KiemTraDonGiaIMS ({ commit, state }, data) {
        let response = await API.KiemTraDonGiaIMS(this._vm.axios, data)
        return response.data.data             
    },
    async getDSVatTuTonKho ({ commit, state }, data) {
        let response = await API.getDSVatTuTonKho(this._vm.axios, data)
        return response.data.data             
    },
    async getContractInfo ({ commit, state }, data) {
        let response = await API.getContractInfo(this._vm.axios, data)
        return response.data.data             
    },
    async kiemTraLocCSHT ({ commit, state }, data) {
        let response = await API.kiemTraLocCSHT(this._vm.axios, data)
        return response.data.data             
    },
    async getAssetByKeys ({ commit, state }, data) {
        let response = await API.getAssetByKeys(this._vm.axios, data)
        return response.data.data             
    },
    async kiemTraSoLuongQLTS ({ commit, state }, data) {
        let response = await API.kiemTraSoLuongQLTS(this._vm.axios, data)
        return response.data.data             
    },
    async getDanhMucLoaiChungTu ({ commit, state }, kieu) {
        let response = await API.getDanhMucLoaiChungTu(this._vm.axios, kieu)
        return response.data.data             
    },
    async kiemTraKhoTon ({ commit, state }, data) {
        let response = await API.kiemTraKhoTon(this._vm.axios, data)
        return response.data.data             
    },
    async sinhDieuChuyenQLTS ({ commit, state }, data) {
        let response = await API.sinhDieuChuyenQLTS(this._vm.axios, data)
        return response.data.data             
    },
}