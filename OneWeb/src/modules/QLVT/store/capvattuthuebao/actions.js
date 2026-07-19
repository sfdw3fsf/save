import API from "../../api/CapVatTuThueBao";

export const actions = {
    async getLoaiHDvaPL({ commit, state }, data) {
        let response = await API.getLoaiHDvaPL(this._vm.axios, data.nhomMdId);
        try {
            commit("setLoaiHDvaPL", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMDichVu({ commit, state }) {
        let response = await API.getDMDichVu(this._vm.axios);
        try {
            commit("setDMDichVu", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMNhanVien({ commit, state }, data) {
        let response = await API.getDMNhanVien(
            this._vm.axios,
            data.donViId,
            data.nhanVienId
        );
        try {
            commit("setDMNhanVien", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMKho({ commit, state }, data) {
        let response = await API.getDMKho(
            this._vm.axios,
            data.nhanVienId,
            data.donViId
        );
        try {
            commit("setDMKho", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMKieuTB({ commit, state }) {
        let response = await API.getDMKieuTB(this._vm.axios);
        try {
            commit("setDMKieuTB", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMTinhTrangTBCap({ commit, state }) {
        let response = await API.getDMTinhTrangTBCap(this._vm.axios);
        try {
            commit("setDMTinhTrangTBCap", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMLyDoTon({ commit, state }, data) {
        let response = await API.getDMLyDoTon(this._vm.axios, data.nhomTonId);
        try {
            commit("setDMLyDoTon", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMLyDoCap({ commit, state }, data) {
        let response = await API.getDMLyDoCap(this._vm.axios, data);
        try {
            commit("setDMLyDoCap", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMTrangBi({ commit, state }) {
        let response = await API.getDMTrangBi(this._vm.axios);
        try {
            commit("setDMTrangBi", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMMucDichLyDo({ commit, state }, data) {
        let response = await API.getDMMucDichLyDo(
            this._vm.axios,
            data.nhomMdId
        );
        try {
            commit("setDMMucDichLyDo", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMTenThietBi({ commit, state }) {
        let response = await API.getDMTenThietBi(this._vm.axios);
        try {
            commit("setDMTenThietBi", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getQuyenNguoiDungCVTTB({ commit, state }) {
        let response = await API.getQuyenNguoiDungCVTTB(this._vm.axios);
        try {
            commit("setQuyenNguoiDungCVTTB", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSThueBaoCapVT({ commit, state }, data) {
        let response = await API.getDSThueBaoCapVT(this._vm.axios, data);
        try {
            commit("setDSThueBaoCapVT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSThueBaoCapVTTruyenSoLieu({ commit, state }, data) {
        let response = await API.getDSThueBaoCapVTTruyenSoLieu(
            this._vm.axios,
            data
        );
        try {
            commit("setDSThueBaoCapVTTruyenSoLieu", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMThaoTac({ commit, state }, data) {
        let response = await API.getDMThaoTac(this._vm.axios, data.code);
        try {
            commit("setDMThaoTac", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDMTinhTrangVTThuHoi({ commit, state }) {
        let response = await API.getDMTinhTrangVTThuHoi(this._vm.axios);
        try {
            commit("setDMTinhTrangVTThuHoi", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSVatTuDaCap({ commit, state }, data) {
        let response = await API.getDSVatTuDaCap(this._vm.axios, data);
        try {
            commit("setDSVatTuDaCap", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSVatTuThueBaoSuDung({ commit, state }, data) {
        let response = await API.getDSVatTuThueBaoSuDung(
            this._vm.axios,
            data.thueBaoId
        );
        try {
            commit("setDSVatTuThueBaoSuDung", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSVatTuTrongKho({ commit, state }, data) {
        let response = await API.getDSVatTuTrongKho(this._vm.axios, data);
        // try {
        //     commit("setDSVatTuTrongKho", response.data.data || []);
        // } catch (error) {
        //     throw error;
        // }
        return response.data;
    },
    async kiemTraHuyPhieuGiao({ commit, state }, data) {
        let response = await API.kiemTraHuyPhieuGiao(this._vm.axios, data);
        try {
            commit("setKiemTraHuyPhieuGiao", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getNgayBatDauDungThu({ commit, state }, data) {
        let response = await API.getNgayBatDauDungThu(
            this._vm.axios,
            data.hdtbId
        );
        try {
            commit("setNgayBatDauDungThu", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async themMoiPhieuCapVT({ commit, state }, data) {
        let response = await API.themMoiPhieuCapVT(this._vm.axios, data);
        try {
            commit("setThemMoiPhieuCapVT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getKeyDKVTMuaSam({ commit, state }, data) {
        let response = await API.getKeyDKVTMuaSam(this._vm.axios, data);
        try {
            commit("setKeyDKVTMuaSam", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSNhanVienTheoID({ commit, state }, data) {
        let response = await API.getDSNhanVienTheoID(
            this._vm.axios,
            data.phieuId,
            data.nhanVienId,
            data.dkieuId
        );
        try {
            commit("setDSNhanVienTheoID", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async deleteLichSuVT({ commit, state }, data) {
        let response = await API.deleteLichSuVT(this._vm.axios, data);
        try {
            commit("setDeleteLichSuVT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getKTTTHongVT({ commit, state }, data) {
        let response = await API.getKTTTHongVT(this._vm.axios, data);
        try {
            commit("setKTTTHongVT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async KiemTraDinhMuc({ commit, state }, data) {
        let response = await API.KiemTraDinhMuc(this._vm.axios, data);
        try {
            commit("setKiemTraDinhMuc", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async KIEMTRA_VTCAPRA_V2({ commit, state }, data) {
        let response = await API.KIEMTRA_VTCAPRA_V2(this._vm.axios, data);
        try {
            commit("setKIEMTRA_VTCAPRA_V2", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async KIEMTRA_VTDACAPTB({ commit, state }, data) {
        let response = await API.KIEMTRA_VTDACAPTB(this._vm.axios, data);
        try {
            commit("setKIEMTRA_VTDACAPTB", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSThamSoMacDinh({ commit, state }, data) {
        let response = await API.getDSThamSoMacDinh(
            this._vm.axios,
            data.kieuId
        );
        try {
            commit("setDSThamSoMacDinh", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSTBSuDungVT({ commit, state }, data) {
        let response = await API.getDSTBSuDungVT(this._vm.axios, data);
        try {
            commit("setDSTBSuDungVT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getThongTinTBDaCapTheoSerial({ commit, state }, data) {
        let response = await API.getThongTinTBDaCapTheoSerial(
            this._vm.axios,
            data
        );
        try {
            commit("setThongTinTBDaCapTheoSerial", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async KTSoLuongVTTBDangSD({ commit, state }, data) {
        let response = await API.KTSoLuongVTTBDangSD(
            this._vm.axios,
            data.vatTuId,
            data.thueBaoId
        );
        try {
            commit("setKTSoLuongVTTBDangSD", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async KTSoLuongVTTBDangSD_v2({ commit, state }, data) {
        let response = await API.KTSoLuongVTTBDangSD_v2(
            this._vm.axios,
            data.vatTuId,
            data.thueBaoId,
            data.hdtb_id
        );
        try {
            commit("setKTSoLuongVTTBDangSD", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async CapNhatSuDungVT_TH({ commit, state }, data) {
        let response = await API.CapNhatSuDungVT_TH(this._vm.axios, data);
        try {
            commit("setCapNhatSuDungVT_TH", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getSL_DD({ commit, state }, data) {
        let response = await API.getSL_DD(this._vm.axios, data.sdVtId);
        try {
            commit("setSL_DD", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getSL_HT({ commit, state }, data) {
        let response = await API.getSL_HT(this._vm.axios, data.sdVtId);
        try {
            commit("setSL_HT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async CAPNHAT_PHIEUVT_THUHOI({ commit, state }, data) {
        let response = await API.CAPNHAT_PHIEUVT_THUHOI(this._vm.axios, data);
        try {
            commit("setCAPNHAT_PHIEUVT_THUHOI", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getThamSoMacDinh({ commit, state }, data) {
        let response = await API.getThamSoMacDinh(
            this._vm.axios,
            data.maTs,
            data.nhanvienId
        );
        try {
            commit("setThamSoMacDinh", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getMAP_ID({ commit, state }) {
        let response = await API.getMAP_ID(this._vm.axios);
        try {
            commit("setMAP_ID", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async KTMaTBCoTonTai({ commit, state }, data) {
        let response = await API.KTMaTBCoTonTai(this._vm.axios, data.maThueBao);
        try {
            commit("setKTMaTBCoTonTai", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async LAY_PORT_THEO_TBID({ commit, state }, data) {
        let response = await API.LAY_PORT_THEO_TBID(this._vm.axios, data);
        try {
            commit("setLAY_PORT_THEO_TBID", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async LAY_DS_PORT_THEOHD_TB({ commit, state }, data) {
        let response = await API.LAY_DS_PORT_THEOHD_TB(
            this._vm.axios,
            data.hdTbId,
            data.thueBaoId
        );
        try {
            commit("setLAY_DS_PORT_THEOHD_TB", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async testPort({ commit, state }, data) {
        let response = await API.testPort(this._vm.axios, data);
        try {
            commit("setTestPort", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getInfoTamUngVT({ commit, state }, data) {
        let response = await API.getInfoTamUngVT(
            this._vm.axios,
            data.nhanVienId,
            data.serial
        );
        try {
            commit("setInfoTamUngVT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async ht_phieu_kkhongvt({ commit, state }, data) {
        let response = await API.ht_phieu_kkhongvt(this._vm.axios, data);
        try {
            commit("set_ht_phieu_kkhongvt", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async KT_TB_DA_ThuHoiVT({ commit, state }, data) {
        let response = await API.KT_TB_DA_ThuHoiVT(
            this._vm.axios,
            data.hdTbId,
            data.phieuId
        );
        try {
            commit("set_KT_TB_DA_ThuHoiVT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async KT_TB_BaoHong_DA_ThuHoiVT({ commit, state }, data) {
        let response = await API.KT_TB_BaoHong_DA_ThuHoiVT(
            this._vm.axios,
            data.baoHongId,
            data.phieuId
        );
        try {
            commit("set_KT_TB_BaoHong_DA_ThuHoiVT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getGiaTriDoKiem({ commit, state }, data) {
        let response = await API.getGiaTriDoKiem(this._vm.axios, data);
        try {
            commit("setGiaTriDoKiem", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getThongTinPhieuCapVTTB({ commit, state }, data) {
        let response = await API.getThongTinPhieuCapVTTB(
            this._vm.axios,
            data.hdTbId
        );
        try {
            commit("setThongTinPhieuCapVTTB", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getThongTinPhieuCapVTTB_BaoHong({ commit, state }, data) {
        let response = await API.getThongTinPhieuCapVTTB_BaoHong(
            this._vm.axios,
            data.baoHongId
        );
        try {
            commit("setThongTinPhieuCapVTTB_BaoHong", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async KT_SR_GPON({ commit, state }, data) {
        let response = await API.KT_SR_GPON(this._vm.axios, data);
        try {
            commit("set_KT_SR_GPON", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async get_TT_ONT_Theo_Account({ commit, state }, data) {
        let response = await API.get_TT_ONT_Theo_Account(this._vm.axios, data);
        try {
            commit("set_TT_ONT_Theo_Account", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async deleteLyDo({ commit, state }, data) {
        let response = await API.deleteLyDo(this._vm.axios, data);
        try {
            commit("setDeleteLyDo", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getThongTinModemCTS({ commit, state }, data) {
        let response = await API.getThongTinModemCTS(
            this._vm.axios,
            data.id,
            data.kieu
        );
        try {
            commit("setThongTinModemCTS", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async GIAOPHIEU_TD_TAOPHIEU_QLVT({ commit, state }, data) {
        let response = await API.GIAOPHIEU_TD_TAOPHIEU_QLVT(
            this._vm.axios,
            data
        );
        try {
            commit("setGIAOPHIEU_TD_TAOPHIEU_QLVT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async updateCTS({ commit, state }, data) {
        let response = await API.updateCTS(this._vm.axios, data);
        try {
            commit("setUpdateCTS", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getHopDongKhachHangId({ commit, state }, hdtb_id) {
        let response = await API.getHopDongKhachHangId(this._vm.axios, hdtb_id);
        try {
            return response.data.data;
        } catch (error) {
            throw error;
        }
    },
    async updateKQGiaoPhieu({ commit, state }, data) {
        let response = await API.updateKQGiaoPhieu(
            this._vm.axios,
            data.phieuId
        );
        try {
            commit("setUpdateKQGiaoPhieu", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async themMoiDSPhieuTon({ commit, state }, data) {
        let response = await API.themMoiDSPhieuTon(this._vm.axios, data);
        try {
            commit("setThemMoiDSPhieuTon", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async kt_thuhoi_khongvt({ commit, state }, data) {
        let response = await API.kt_thuhoi_khongvt(this._vm.axios, data);
        try {
            commit("set_kt_thuhoi_khongvt", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSTheoSerialOrSoAcc({ commit, state }, data) {
        let response = await API.getDSTheoSerialOrSoAcc(
            this._vm.axios,
            data.serial
        );
        try {
            commit("setDSTheoSerialOrSoAcc", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async taoDL_Ton_Khong_ThuHoi_Duoc({ commit, state }, data) {
        let response = await API.taoDL_Ton_Khong_ThuHoi_Duoc(
            this._vm.axios,
            data
        );
        try {
            commit("set_taoDL_Ton_Khong_ThuHoi_Duoc", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSLoaiHopDongMucDich({ commit, state }) {
        let response = await API.getDSLoaiHopDongMucDich(this._vm.axios);
        try {
            commit("setDSLoaiHopDongMucDich", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getParamsBaoCao({ commit, state }, data) {
        let response = await API.getParamsBaoCao(
            this._vm.axios,
            data.maBc
        );
        try {
            commit("setParamsBaoCao", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async btnCapVatTu({ commit, state }, data) {
        let response = await API.btnCapVatTu(
            this._vm.axios,
            data
        );
        try {
            commit("setBtnCapVatTu", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async capVatTuV2({ commit, state }, data) {
        let response = await API.capVatTuV2(
            this._vm.axios,
            data
        );
        try {
            commit("setBtnCapVatTu", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async hoanCongCapVatTuTB({ commit, state }, data) {
        let response = await API.hoanCongCapVatTuTB(this._vm.axios,data);       
        return response.data.data;
    },
    async hoanCongDanhSach({ commit, state }, data) {
        let response = await API.hoanCongDanhSach(this._vm.axios,data);       
        return response.data.data;
    },
    async getHinhAnh({ commit, state }, phieuVtId) {
        let response = await API.getHinhAnh(this._vm.axios,phieuVtId);       
        return response.data.data;
    },
    async downloadFile({ commit, state },data) {
        let response = await API.downloadFile(this._vm.axios,data)
        return response.data 
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
    async updateHinhAnh({ commit, state },data) {
        let response = await API.updateHinhAnh(this._vm.axios,data)
        return response.data.data
    },
    async uploadFile({ commit, state },data) {
        let response = await API.uploadFile(this._vm.axios,data)
        return response.data.data
    },
};
