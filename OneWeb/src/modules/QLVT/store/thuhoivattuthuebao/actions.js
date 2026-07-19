import API from "../../api/ThuHoiVatTuThueBaoAPI";

export const actions = {
    async getDSPhanHoiKH({ commit, state }, data) {
        let response = await API.getDSPhanHoiKH(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSLoaiHopDong({ commit, state }, data) {
        let response = await API.getDSLoaiHopDong(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSDVVienThong({ commit, state }, data) {
        let response = await API.getDSDVVienThong(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSMucdichLyDoThuHoi({ commit, state }, data) {
        let response = await API.getDSMucdichLyDoThuHoi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async getDSTinhTrangTbi({ commit, state }, data) {
        let response = await API.getDSTinhTrangTbi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSThietBiVatTuThuHoi({ commit, state }, data) {
        let response = await API.getDSThietBiVatTuThuHoi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSKieuTrangBi({ commit, state }, data) {
        let response = await API.getDSKieuTrangBi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSLyDoThuHoi({ commit, state }, data) {
        let response = await API.getDSLyDoThuHoi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async getDSLyDoHuy({ commit, state }, data) {
        let response = await API.getDSLyDoHuy(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSDonViQuanLy({ commit, state }, data) {
        let response = await API.getDSDonViQuanLy(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSMucDichLoaiHopDong({ commit, state }, data) {
        let response = await API.getDSMucDichLoaiHopDong(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSThamSoMacDinh({ commit, state }, data) {
        let response = await API.getDSThamSoMacDinh(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async getDSHuongGiaoTheoTBao({ commit, state }, data) {
        let response = await API.getDSHuongGiaoTheoTBao(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSThueBaoThuHoiVT_V1({ commit, state }, data) {
        let response = await API.getDSThueBaoThuHoiVT_V1(this._vm.axios, data);
        try {
            return response.data.data || {};
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSThueBaoThuHoiVT_V2({ commit, state }, data) {
        let response = await API.getDSThueBaoThuHoiVT_V2(this._vm.axios, data);
        try {
            return response.data.data || {};
        } catch (error) {
            throw error;
        }
        return response;
    },
    async KiemTraThietBiThuHoi({ commit, state }, data) {
        let response = await API.KiemTraThietBiThuHoi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async getDSKieuYeuCau({ commit, state }, data) {
        let response = await API.getDSKieuYeuCau(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSSuDungVatTuTBao({ commit, state }, data) {
        let response = await API.getDSSuDungVatTuTBao(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSPhieuThuHoiVTTB({ commit, state }, data) {
        let response = await API.getDSPhieuThuHoiVTTB(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSVatTuGiaoDichThuHoi({ commit, state }, data) {
        let response = await API.getDSVatTuGiaoDichThuHoi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async getDSThietBiDaCapTheoSerialMoi({ commit, state }, data) {
        let response = await API.getDSThietBiDaCapTheoSerialMoi(
            this._vm.axios,
            data
        );
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async KiemTraSerialMoiTBaoSuDung({ commit, state }, data) {
        let response = await API.KiemTraSerialMoiTBaoSuDung(
            this._vm.axios,
            data
        );
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSThietBiTheoSerial({ commit, state }, data) {
        let response = await API.getDSThietBiTheoSerial(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSVatTu({ commit, state }, data) {
        let response = await API.getDSVatTu(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async getKeys({ commit, state }, data) {
        let response = await API.getKeys(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async CapNhatThuHoiVatTu_V2({ commit, state }, data) {
        let response = await API.CapNhatThuHoiVatTu_V2(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSThaoTacTheoCode({ commit, state }, data) {
        let response = await API.getDSThaoTacTheoCode(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async CapNhatPhieuVatTuThuHoi({ commit, state }, data) {
        let response = await API.CapNhatPhieuVatTuThuHoi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async KiemTraSeTopBox({ commit, state }, data) {
        let response = await API.KiemTraSeTopBox(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async ThuHoiSeTopBox({ commit, state }, data) {
        let response = await API.ThuHoiSeTopBox(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async CapNhatLogVASC({ commit, state }, data) {
        let response = await API.CapNhatLogVASC(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async CapNhatTrangThaiDongBoSeTopBox({ commit, state }, data) {
        let response = await API.CapNhatTrangThaiDongBoSeTopBox(
            this._vm.axios,
            data
        );
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async GiaoPhieuTapDoanDuyetTH({ commit, state }, data) {
        let response = await API.GiaoPhieuTapDoanDuyetTH(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSQuyTrinhTheoMa({ commit, state }, data) {
        let response = await API.getDSQuyTrinhTheoMa(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async getIDPhieuTheoHDTB({ commit, state }, data) {
        let response = await API.getIDPhieuTheoHDTB(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async HoanCongThuHoiVT_V1({ commit, state }, data) {
        let response = await API.HoanCongThuHoiVT_V1(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async TaoGiaoPhieuTon({ commit, state }, data) {
        let response = await API.TaoGiaoPhieuTon(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async CapNhatLyDoHuy({ commit, state }, data) {
        let response = await API.CapNhatLyDoHuy(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async CapNhatKQXuLyKhongTH({ commit, state }, data) {
        let response = await API.CapNhatKQXuLyKhongTH(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async CapNhatKQXyLyTH({ commit, state }, data) {
        let response = await API.CapNhatKQXyLyTH(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSNhanVienChamSocThueBao({ commit, state }, data) {
        let response = await API.getDSNhanVienChamSocThueBao(
            this._vm.axios,
            data
        );
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async SendSMS({ commit, state }, data) {
        let response = await API.SendSMS(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async getThongTinVatTuTheoSerial({ commit, state }, data) {
        let response = await API.getThongTinVatTuTheoSerial(
            this._vm.axios,
            data
        );
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async CapNhatGhiChuTon({ commit, state }, data) {
        let response = await API.CapNhatGhiChuTon(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSNhanVienTheoPhieu({ commit, state }, data) {
        let response = await API.getDSNhanVienTheoPhieu(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async CapNhatSuDungVatTuThuHoi({ commit, state }, data) {
        let response = await API.CapNhatSuDungVatTuThuHoi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async KiemTraSerialThuHoi({ commit, state }, data) {
        let response = await API.KiemTraSerialThuHoi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async KiemTraVatTuThuHoi({ commit, state }, data) {
        let response = await API.KiemTraVatTuThuHoi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getSoLuongVatTuThueBao({ commit, state }, data) {
        let response = await API.getSoLuongVatTuThueBao(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getTongSoLuong({ commit, state }, data) {
        let response = await API.getTongSoLuong(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async getDSLyDoTon({ commit, state }, data) {
        let response = await API.getDSLyDoTon(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDanhMucDonViQuanLy({ commit, state }, data) {
        let response = await API.getDanhMucDonViQuanLy(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async CapNhatChuyenPhieuTo({ commit, state }, data) {
        let response = await API.CapNhatChuyenPhieuTo(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSMaTBMaHGThuHoi({ commit, state }, data) {
        let response = await API.getDSMaTBMaHGThuHoi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getKiemTraCanhBaoSLThuHoi({ commit, state }, data) {
        let response = await API.getCanhBaoSLThuHoi(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },
    async CapNhatChuyenToThuHoiVT({ commit, state }, data) {
        let response = await API.CapNhatChuyenToThuHoiVT(this._vm.axios, data);
        return response.data.data       
    },    
    async HoanCongThuHoiVTTB({ commit, state }, data) {
        let response = await API.HoanCongThuHoiVTTB(this._vm.axios, data);
        return response.data.data       
    },
    
    async check_LAY_DS_THAMSO_MD_MATS({ commit, state }, data) {
        let response = await API.check_LAY_DS_THAMSO_MD_MATS(this._vm.axios, data);
        return response.data.data       
    }, 
};
