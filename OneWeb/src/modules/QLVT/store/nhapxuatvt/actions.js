import API from '../../api/NhapXuatVTAPI'
export const actions = {
    async getDSQuyenNguoiDung ({ commit, state }, data) {
        let response = await API.getDSQuyenNguoiDung(this._vm.axios, data)
        return response.data.data
    },
    async getDSVatTu ({ commit, state }, data) {
        let response = await API.getDSVatTu(this._vm.axios, data)
        return response.data.data
    },
    async getDanhMucLoaiCT_V2 ({ commit, state }, data) {
        let response = await API.getDanhMucLoaiCT_V2(this._vm.axios, data)
        return response.data.data
    },
    async getDanhMucLoaiCT ({ commit, state }, data) {
        let response = await API.getDanhMucLoaiCT(this._vm.axios, data)
        return response.data.data
    },

    async getDanhMucKho ({ commit, state }, data) {
        let response = await API.getDanhMucKho(this._vm.axios, data)
        return response.data.data
    },
    async getDanhMucMucDich ({ commit, state }, data) {
        let response = await API.getDanhMucMucDich(this._vm.axios, data)
        return response.data.data
    },
    async getDSCongTrinh ({ commit, state }, data) {
        let response = await API.getDSCongTrinh(this._vm.axios, data)
        return response.data.data
    },
    async getDSHopDongMuaSam ({ commit, state }, data) {
        let response = await API.getDSHopDongMuaSam(this._vm.axios, data)
        return response.data.data
    },

    async getDSDuAn ({ commit, state }, data) {
        let response = await API.getDSDuAn(this._vm.axios, data)
        return response.data.data
    },
    async getDSDuAnTheoDonVi ({ commit, state }, data) {
        let response = await API.getDSDuAnTheoDonVi(this._vm.axios, data)
        return response.data.data
    },
    async getDSChungTu_V2 ({ commit, state }, data) {
        let response = await API.getDSChungTu_V2(this._vm.axios, data)
        return response.data.data
    },
    async getDSVatTuChungTu ({ commit, state }, data) {
        let response = await API.getDSVatTuChungTu(this._vm.axios, data)
        return response.data.data
    },
    async getDSVatTuChungTuV1 ({ commit, state }, data) {
        let response = await API.getDSVatTuChungTuV1(this._vm.axios, data)
        return response.data.data
    },
    

    async getThongTinNgayDH ({ commit, state }, data) {
        let response = await API.getThongTinNgayDH(this._vm.axios, data)
        return response.data.data
    },
    async getThongTinNgayXuat ({ commit, state }, data) {
        let response = await API.getThongTinNgayXuat(this._vm.axios, data)
        return response.data.data
    },
    async getMaMucDichTheoID ({ commit, state }, data) {
        let response = await API.getMaMucDichTheoID(this._vm.axios, data)
        return response.data.data
    },
    async getKieuChungTu ({ commit, state }, data) {
        let response = await API.getKieuChungTu(this._vm.axios, data)
        return response.data.data
    },

    async getDSTraCuuVatTuChungTu ({ commit, state }, data) {
        let response = await API.getDSTraCuuVatTuChungTu(this._vm.axios, data)
        return response.data.data
    },
    async getDSVatTuChungTuSerial ({ commit, state }, data) {
        let response = await API.getDSVatTuChungTuSerial(this._vm.axios, data)
        return response.data.data
    },
    async getDSThietBiChungTu ({ commit, state }, data) {
        let response = await API.getDSThietBiChungTu(this._vm.axios, data)
        return response.data.data
    },
    async getThamSoMacDinh ({ commit, state }, data) {
        let response = await API.getThamSoMacDinh(this._vm.axios, data)
        return response.data.data
    },

    async getKeys ({ commit, state }, data) {
        let response = await API.getKeys(this._vm.axios, data)
        return response.data.data
    },
    async ThemMoiDuLieuChiTietCT ({ commit, state }, data) {
        let response = await API.ThemMoiDuLieuChiTietCT(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatDuLieuChungTu ({ commit, state }, data) {
        let response = await API.CapNhatDuLieuChungTu(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatDuLieuChungTuTBi ({ commit, state }, data) {
        let response = await API.CapNhatDuLieuChungTuTBi(this._vm.axios, data)
        return response.data.data
    },

    async XoaTatCaChiTietChungTu ({ commit, state }, data) {
        let response = await API.XoaTatCaChiTietChungTu(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatDonHangChungTu ({ commit, state }, data) {
        let response = await API.CapNhatDonHangChungTu(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatVatTuChungTu ({ commit, state }, data) {
        let response = await API.CapNhatVatTuChungTu(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatChiTietChungTu ({ commit, state }, data) {
        let response = await API.CapNhatChiTietChungTu(this._vm.axios, data)
        return response.data.data
    },

    async CapNhatVatTuMuaLe ({ commit, state }, data) {
        let response = await API.CapNhatVatTuMuaLe(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraCapNhatChungTu ({ commit, state }, data) {
        let response = await API.KiemTraCapNhatChungTu(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraXoaPhieuDaHoanThanh ({ commit, state }, data) {
        let response = await API.KiemTraXoaPhieuDaHoanThanh(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraHanChungTu ({ commit, state }, data) {
        let response = await API.KiemTraHanChungTu(this._vm.axios, data)
        return response.data.data
    },

    async KiemTraSuaThuHoi ({ commit, state }, data) {
        let response = await API.KiemTraSuaThuHoi(this._vm.axios, data)
        return response.data.data
    },
    async SinhSoPhieuVatTuV3 ({ commit, state }, data) {
        let response = await API.SinhSoPhieuVatTuV3(this._vm.axios, data)
        try {
            return response.data.data && response.data.data[0] || ''
        } catch (error) {
            throw error
        }
        return response
    },
    async getIDLoaiKho ({ commit, state }, data) {
        let response = await API.getIDLoaiKho(this._vm.axios, data)
        return response.data.data
    },
    async getMaPrefixLoHang ({ commit, state }, data) {
        let response = await API.getMaPrefixLoHang(this._vm.axios, data)
        return response.data.data
    },

    async XoaChiTietChungTu ({ commit, state }, data) {
        let response = await API.XoaChiTietChungTu(this._vm.axios, data)
        return response.data.data
    },
    async getThongTinNgayNhanCT ({ commit, state }, data) {
        let response = await API.getThongTinNgayNhanCT(this._vm.axios, data)
        return response.data.data
    },
    async getThongTinQuyenTrenKho ({ commit, state }, data) {
        let response = await API.getThongTinQuyenTrenKho(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatSerialV2 ({ commit, state }, data) {
        let response = await API.CapNhatSerialV2(this._vm.axios, data)
        return response.data.data
    },

    async CapNhatSerialV2_1 ({ commit, state }, data) {
        let response = await API.CapNhatSerialV2_1(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraMaVatTuTonTai ({ commit, state }, data) {
        let response = await API.KiemTraMaVatTuTonTai(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraDonViTinh ({ commit, state }, data) {
        let response = await API.KiemTraDonViTinh(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraTonVatTuCT ({ commit, state }, data) {
        let response = await API.KiemTraTonVatTuCT(this._vm.axios, data)
        return response.data.data
    },

    async CapNhatChungTuThietBi ({ commit, state }, data) {
        let response = await API.CapNhatChungTuThietBi(this._vm.axios, data)
        return response.data.data
    },
    async getThongTinTrangThaiPhieu ({ commit, state }, data) {
        let response = await API.getThongTinTrangThaiPhieu(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatChungTu ({ commit, state }, data) {
        let response = await API.CapNhatChungTu(this._vm.axios, data)
        return response.data.data
    },
    async XoaChungTuBaoHanh ({ commit, state }, data) {
        let response = await API.XoaChungTuBaoHanh(this._vm.axios, data)
        try {
            return response.data.data || ""
        } catch (error) {
            throw error
        }
        return response
    },

    async GuiSMSChungTu ({ commit, state }, data) {
        let response = await API.GuiSMSChungTu(this._vm.axios, data)
        return response.data.data
    },
    async XoaPhieuDaHoanThanh ({ commit, state }, data) {
        let response = await API.XoaPhieuDaHoanThanh(this._vm.axios, data)
        try {
            return response.data.data || ""
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhatDangKy ({ commit, state }, data) {
        let response = await API.CapNhatDangKy(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatVatTuDangKy ({ commit, state }, data) {
        let response = await API.CapNhatVatTuDangKy(this._vm.axios, data)
        return response.data.data
    },

    async getDSChiTietChungTu ({ commit, state }, data) {
        let response = await API.getDSChiTietChungTu(this._vm.axios, data)
        return response.data.data
    },
    async getMaMucDichTheoChungTu ({ commit, state }, data) {
        let response = await API.getMaMucDichTheoChungTu(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatTrangThaiChiTietCT ({ commit, state }, data) {
        let response = await API.CapNhatTrangThaiChiTietCT(this._vm.axios, data)
        return response.data.data
    },
    async getSoPhieu ({ commit, state }, data) {
        let response = await API.getSoPhieu(this._vm.axios, data)
        return response.data.data
    },

    async KiemTraXoaDongBo ({ commit, state }, data) {
        let response = await API.KiemTraXoaDongBo(this._vm.axios, data)
        return response.data.data
    },
    async XoaChungTu ({ commit, state }, data) {
        let response = await API.XoaChungTu(this._vm.axios, data)
        return response.data.data
    },
    async getIDTrangThaiPhieu ({ commit, state }, data) {
        let response = await API.getIDTrangThaiPhieu(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraCanhBaoHoanThienCT ({ commit, state }, data) {
        let response = await API.KiemTraCanhBaoHoanThienCT(this._vm.axios, data)
        return response.data.data
    },

    async KiemTraHoanThanhCT ({ commit, state }, data) {
        let response = await API.KiemTraHoanThanhCT(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraHoanThienCT ({ commit, state }, data) {
        let response = await API.KiemTraHoanThienCT(this._vm.axios, data)
        return response.data.data
    },
    async HoanThienChungTu ({ commit, state }, data) {
        let response = await API.HoanThienChungTu(this._vm.axios, data)
        return response.data.data
    },
    async XoaTatCaChungTuThietBi ({ commit, state }, data) {
        let response = await API.XoaTatCaChungTuThietBi(this._vm.axios, data)
        return response.data.data
    },

    async KiemTraSerialTonTai ({ commit, state }, data) {
        let response = await API.KiemTraSerialTonTai(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraSerialDangCap ({ commit, state }, data) {
        let response = await API.KiemTraSerialDangCap(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraLoHangCuaSerial ({ commit, state }, data) {
        let response = await API.KiemTraLoHangCuaSerial(this._vm.axios, data)
        return response.data.data
    },
    async XoaCTThietBiTheoTBi ({ commit, state }, data) {
        let response = await API.XoaCTThietBiTheoTBi(this._vm.axios, data)
        return response.data.data
    },

    async getKieuNhapCap ({ commit, state }, data) {
        let response = await API.getKieuNhapCap(this._vm.axios, data)
        return response.data.data
    },
    async getDanhSachCap ({ commit, state }, data) {
        let response = await API.getDanhSachCap(this._vm.axios, data)
        try {
            return response.data.data
        } catch (error) {
            throw error
        }
        return response
    },
    async TachCuonCap ({ commit, state }, data) {
        let response = await API.TachCuonCap(this._vm.axios, data)
        return response.data.data
    },
    async getTongSoLuongTonTheoCTCT ({ commit, state }, data) {
        let response = await API.getTongSoLuongTonTheoCTCT(this._vm.axios, data)
        return response.data.data
    },

    async CopyChungTuV2 ({ commit, state }, data) {
        let response = await API.CopyChungTuV2(this._vm.axios, data)
        return response.data.data
    },
    async getDSKhoVatTuLe ({ commit, state }, data) {
        let response = await API.getDSKhoVatTuLe(this._vm.axios, data)
        return response.data.data
    },
    async getDSKhoTheoNV ({ commit, state }, data) {
        let response = await API.getDSKhoTheoNV(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraTonKho ({ commit, state }, data) {
        let response = await API.KiemTraTonKho(this._vm.axios, data)
        return response.data.data
    },

    async getDSTonKhoTheoCT ({ commit, state }, data) {
        let response = await API.getDSTonKhoTheoCT(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraUpdateSerial ({ commit, state }, data) {
        let response = await API.KiemTraUpdateSerial(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatGhiChuCTTB ({ commit, state }, data) {
        let response = await API.CapNhatGhiChuCTTB(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatMaThungCTTB ({ commit, state }, data) {
        let response = await API.CapNhatMaThungCTTB(this._vm.axios, data)
        return response.data.data
    },

    async getChungTuTapDoan ({ commit, state }, data) {
        let response = await API.getChungTuTapDoan(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraDongBo ({ commit, state }, data) {
        let response = await API.KiemTraDongBo(this._vm.axios, data)
        return response.data.data
    },
    async XoaDuLieuChungTu ({ commit, state }, data) {
        let response = await API.XoaDuLieuChungTu(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatNgayXacNhanCT ({ commit, state }, data) {
        let response = await API.CapNhatNgayXacNhanCT(this._vm.axios, data)
        return response.data.data
    },

    async getThongTinNgayGui ({ commit, state }, data) {
        let response = await API.getThongTinNgayGui(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatNVThucHienCT ({ commit, state }, data) {
        let response = await API.CapNhatNVThucHienCT(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatXoaNgayNhanCT ({ commit, state }, data) {
        let response = await API.CapNhatXoaNgayNhanCT(this._vm.axios, data)
        return response.data.data
    },
    async InPhieuInKim ({ commit, state }, data) {
        let response = await API.InPhieuInKim(this._vm.axios, data)
        return response.data.data
    },

    async getThamSoTheoNguoiDung ({ commit, state }, data) {
        let response = await API.getThamSoTheoNguoiDung(this._vm.axios, data)
        return response.data.data
    },
    async getDanhMucDuAn ({ commit, state }, data) {
        let response = await API.getDanhMucDuAn(this._vm.axios, data)
        return response.data.data
    },
    async GanHDDuAnChiTietCT ({ commit, state }, data) {
        let response = await API.GanHDDuAnChiTietCT(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraChiTietVatTuCoDauVao ({ commit, state }, data) {
        let response = await API.KiemTraChiTietVatTuCoDauVao(this._vm.axios, data)
        return response.data.data
    },

    async GoGanHDDuAnCTCT ({ commit, state }, data) {
        let response = await API.GoGanHDDuAnCTCT(this._vm.axios, data)
        return response.data.data
    },
    async RefreshDanhMucDuAn ({ commit, state }, data) {
        let response = await API.RefreshDanhMucDuAn(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatTinhTrangCTTBi ({ commit, state }, data) {
        let response = await API.CapNhatTinhTrangCTTBi(this._vm.axios, data)
        return response.data.data
    },
    async CapNhatLoHang ({ commit, state }, data) {
        let response = await API.CapNhatLoHang(this._vm.axios, data)
        return response.data.data
    },

    async getDSChungTuNhapLe ({ commit, state }, data) {
        let response = await API.getDSChungTuNhapLe(this._vm.axios, data)
        return response.data.data
    },
    async getDSChungTuTheoVatTu ({ commit, state }, data) {
        let response = await API.getDSChungTuTheoVatTu(this._vm.axios, data)
        return response.data.data
    },
    async getQuyTrinhHuongGiaoTheoCT ({ commit, state }, data) {
        let response = await API.getQuyTrinhHuongGiaoTheoCT(this._vm.axios, data)
        return response.data.data
    },
    async getHuongGiaoTiepTheo ({ commit, state }, data) {
        let response = await API.getHuongGiaoTiepTheo(this._vm.axios, data)
        return response.data.data
    },

    async ThemMoiChungTu ({ commit, state }, data) {
        let response = await API.ThemMoiChungTu(this._vm.axios, data)
        return response.data.data
    },

    async getDSSerialNullIMS ({ commit, state }, data) {
        let response = await API.getDSSerialNullIMS(this._vm.axios, data)
        return response.data.data
    },

    async CapNhatSerialNullIMS ({ commit, state }, data) {
        let response = await API.CapNhatSerialNullIMS(this._vm.axios, data)
        return response.data.data
    },

    async getDanhMucNghiepVu ({ commit, state }, data) {
        let response = await API.getDanhMucNghiepVu(this._vm.axios, data)
        return response.data.data
    },

    async getDSTinh ({ commit, state }, data) {
        let response = await API.getDSTinh(this._vm.axios, data)
        return response.data.data
    },

    async getDSDonViSuDung ({ commit, state }, data) {
        let response = await API.getDSDonViSuDung(this._vm.axios, data)
        return response.data.data
    },

    async getDSThamSoMacDinhTheoMaTS ({ commit, state }, data) {
        let response = await API.getDSThamSoMacDinhTheoMaTS(this._vm.axios, data)
        return response.data.data
    },

    async getMaCSHT ({ commit, state }, data) {
        let response = await API.getMaCSHT(this._vm.axios, data)
        return response.data.data
    },
    
    async getDSChungTuDeNghi ({ commit, state }, data) {
        let response = await API.getDSChungTuDeNghi(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },

    async getQuyTrinhHuongGiaoTheoDeNghiId ({ commit, state },denghi_id) {
        let response = await API.getQuyTrinhHuongGiaoTheoDeNghiId(this._vm.axios,denghi_id)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async capNhatDeNghiChungTu ({ commit, state },data) {
        let response = await API.capNhatDeNghiChungTu(this._vm.axios,data)
        try {
            return response.data.data || ""
        } catch (error) {
            throw error
        }
    },
    async getDSDonViFMIS ({ commit, state }) {
        let response = await API.getDSDonViFMIS(this._vm.axios)
        try {
            return response.data.data || []
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
    async Insert_HDMS ({ commit, state }, data) {
        let response = await API.Insert_HDMS(this._vm.axios, data)
        return response.data.data              
    },
    async KT_PHIEU_QT ({ commit, state }, data) {
        let response = await API.KT_PHIEU_QT(this._vm.axios, data)
        return response.data.data              
    },
    async KIEMTRA_XOA_CHUNGTU ({ commit, state }, data) {
        let response = await API.KIEMTRA_XOA_CHUNGTU(this._vm.axios, data)
        return response.data.data              
    },
    async KIEMTRA_TRANGTHAI_CT ({ commit, state }, data) {
        let response = await API.KIEMTRA_TRANGTHAI_CT(this._vm.axios, data)
        return response.data.data              
    },
    async XOA_GIAOPHIEU_CHUNGTU ({ commit, state }, data) {
        let response = await API.XOA_GIAOPHIEU_CHUNGTU(this._vm.axios, data)
        return response.data.data              
    },
    async GAN_DENGHI_CHUNGTU ({ commit, state }, data) {
        let response = await API.GAN_DENGHI_CHUNGTU(this._vm.axios, data)
        return response.data.data              
    },
    async capNhatBBBGIMS ({ commit, state }, data) {
        let response = await API.capNhatBBBGIMS(this._vm.axios, data)
        return response.data.data              
    },
    async kiemTraChungTuDongBo ({ commit, state }, chungTuId) {
        let response = await API.kiemTraChungTuDongBo(this._vm.axios, chungTuId)        
        return response.data.data || []        
    },

    async kiemTraDongBoIMS ({ commit, state }, chungTuId) {
        let response = await API.kiemTraDongBoIMS(this._vm.axios, chungTuId)
        return response.data.data || []            
    },
    async xuLyDongBoIMS ({ commit, state }, data) {
        let response = await API.xuLyDongBoIMS(this._vm.axios, data)
        return response.data.data             
    },
    async getDSCSHT ({ commit, state }, data) {
        let response = await API.getDSCSHT(this._vm.axios, data)
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
    async getMaCoSoHaTang ({ commit, state }, data) {
        let response = await API.getMaCoSoHaTang(this._vm.axios, data)       
        return response.data.data         
    },
    async SinhChungTuIMSBienBan ({ commit, state }, data) {
        let response = await API.SinhChungTuIMSBienBan(this._vm.axios, data)       
        return response.data.data         
    },
    async capNhatNgayChungTu ({ commit, state }, data) {
        let response = await API.capNhatNgayChungTu(this._vm.axios, data)       
        return response.data.data         
    },
    async xoaChungTu3In1 ({ commit, state }, data) {
        let response = await API.xoaChungTu3In1(this._vm.axios, data)       
        return response.data.data         
    },
    async KiemTraVatTuDocfile ({ commit, state }, data) {
        let response = await API.KiemTraVatTuDocfile(this._vm.axios, data)       
        return response.data.data         
    },
    async ghiLaiAllInOne ({ commit, state }, data) {
        let response = await API.ghiLaiAllInOne(this._vm.axios, data)       
        return response.data.data         
    },
    async getDSChungTu_V3 ({ commit, state }, data) {
        let response = await API.getDSChungTu_V3(this._vm.axios, data)       
        return response.data.data         
    },

    async uploadFileVatTu ({ commit, state }, data) {
        let response = await API.uploadFileVatTu(this._vm.axios, data)       
        return response.data.data         
    },
    async getDSPhieuInDeAn07 ({ commit, state }, data) {
        let response = await API.getDSPhieuInDeAn07(this._vm.axios, data)       
        return response.data.data         
    },
    async getParamsBaoCao ({ commit, state }, data) {
        let response = await API.getParamsBaoCao(this._vm.axios, data)       
        return response.data.data         
    },
    async getDSFileChungTu ({ commit, state }, chungTuId) {
        let response = await API.getDSFileChungTu(this._vm.axios, chungTuId)       
        return response.data.data         
    },

    async getLinkFile({ commit, state },data) {
        let response = await API.getLinkFile(this._vm.axios,data)
        return response.data 
    },
    async checkFileExist({ commit, state },data) {
        let response = await API.checkFileExist(this._vm.axios,data)
        return response.data 
    },
    async deleteFile({ commit, state },data) {
        let response = await API.deleteFile(this._vm.axios,data)
        return response.data 
    },
    async uploadFile({ commit, state },data) {
        let response = await API.uploadFile(this._vm.axios,data)
        return response.data.data
    },
    async uploadFileCVChungTu({ commit, state },data) {
        let response = await API.uploadFileCVChungTu(this._vm.axios,data)
        return response.data.data
    },
    async kiemTraXoaFileChungTu({ commit, state },data) {
        let response = await API.kiemTraXoaFileChungTu(this._vm.axios,data)
        return response.data.data
    },
}