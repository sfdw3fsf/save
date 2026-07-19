import API from "../../api/DangKyNhuCauVT";

export const actions = {
    async getDSThoiGianDK({ commit, state }, data) {
        let response = await API.getDSThoiGianDK(this._vm.axios, data.ckdkId);
        try {
            commit("setDSThoiGianDK", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDotDK({ commit, state }) {
        let response = await API.getDotDK(this._vm.axios);
        try {
            commit("setDotDK", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDuAnNV({ commit, state }, data) {
        let response = await API.getDuAnNV(this._vm.axios, data.nhanVienId);
        try {
            commit("setDuAnNV", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSCongTrinh({ commit, state }, data) {
        let response = await API.getDSCongTrinh(this._vm.axios, data);
        try {
            commit("setDSCongTrinh", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getNhanVienLienHe({ commit, state }, data) {
        let response = await API.getNhanVienLienHe(
            this._vm.axios,
            data.nhanVienId
        );
        try {
            commit("setNhanVienLienHe", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getKhoDVCungUng({ commit, state }) {
        let response = await API.getKhoDVCungUng(this._vm.axios);
        try {
            commit("setKhoDVCungUng", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSDangKyNhuCauTinhTrang({ commit, state }, data) {
        let response = await API.getDSDangKyNhuCauTinhTrang(
            this._vm.axios,
            data
        );
        try {
            commit("setDSDangKyNhuCauTinhTrang", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSVatTuDaDK({ commit, state }, data) {
        let response = await API.getDSVatTuDaDK(this._vm.axios, data.dangKyId);
        try {
            commit("setDSVatTuDaDK", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSCuonCapTheoKho({ commit, state }, data) {
        let response = await API.getDSCuonCapTheoKho(
            this._vm.axios,
            data.khoId
        );
        try {
            commit("setDSCuonCapTheoKho", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSMucDichDK({ commit, state }, data) {
        let response = await API.getDSMucDichDK(this._vm.axios, data.kieu);
        try {
            commit("setDSMucDichDK", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSVatTuMD({ commit, state }, data) {
        let response = await API.getDSVatTuMD(
            this._vm.axios,
            data.donViId,
            data.nhomMd
        );
        try {
            commit("setDSVatTuMD", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSVatTuTimKiem({ commit, state }) {
        let response = await API.getDSVatTuTimKiem(this._vm.axios);
        return response.data.data
    },
    async deleteVatTuDangKy({ commit, state }, data) {
        let response = await API.deleteVatTuDangKy(
            this._vm.axios,
            data.dangKyId,
            data.vtuDkId
        );
        try {
            commit("deleteDSVatTuTK", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getVatTuTon({ commit, state }) {
        let response = await API.getVatTuTon(this._vm.axios);
        try {
            commit("setVatTuTon", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getVattu_MDDK({ commit, state }, data) {
        let response = await API.getVattu_MDDK(
            this._vm.axios,
            data.nhomMucDichId
        );
        try {
            commit("setVattu_MDDK", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getVattu_MDDK_v2({ commit, state }) {
        let response = await API.getVattu_MDDK_v2(this._vm.axios);
        try {
            commit("setVattu_MDDK_v2", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async deleteDuLieuVT({ commit, state }, data) {
        let response = await API.deleteDuLieuVT(this._vm.axios, data.dangKyId);
        try {
            commit("deleteDuLieuVT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async kiemTraDangKyCT({ commit, state }, data) {
        let response = await API.kiemTraDangKyCT(this._vm.axios, data);
        try {
            commit("setKiemTraDangKyCT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async kiemTraDSDangKyTheoTime({ commit, state }, data) {
        let response = await API.kiemTraDSDangKyTheoTime(this._vm.axios, data);
        try {
            commit("setKiemTraDSDangKyTheoTime", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async themMoiVatTuDK({ commit, state }, data) {
        let response = await API.themMoiVatTuDK(this._vm.axios, data);
        try {
            commit("setThemMoiVatTuDK", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getKeyDKVTMuaSam({ commit, state }, data) {
        let response = await API.getKeyDKVTMuaSam(this._vm.axios, data);
        try {
            commit("setKeyDKVTMS", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async checkInfoVatTu({ commit, state }, data) {
        let response = await API.checkInfoVatTu(this._vm.axios, data);
        try {
            commit("setCheckInfoVatTu", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async addDuLieuVatTuDKv2({ commit, state }, data) {
        let response = await API.addDuLieuVatTuDKv2(this._vm.axios, data);
        try {
            commit("setAddDuLieuVatTuDKv2", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSThamSoMacDinh({ commit, state }) {
        let response = await API.getDSThamSoMacDinh(this._vm.axios);
        try {
            commit("setDSThamSoMacDinh", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSDangKy_frmCopy({ commit, state }, data) {
        let response = await API.getDSDangKy_frmCopy(this._vm.axios, data);
        try {
            commit("setDSDangKy_frmCopy", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async IU_DANGKY_TT({ commit, state }, data) {
        let response = await API.IU_DANGKY_TT(this._vm.axios, data);
        try {
            commit("set_IU_DANGKY_TT", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSKhoTheoNV({ commit, state }, data) {
        let response = await API.getDSKhoTheoNV(this._vm.axios, data);
        try {
            commit("setDSKhoTheoNV", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async listVTThuocNhomMDDK({ commit, state }) {
        let response = await API.listVTThuocNhomMDDK(this._vm.axios);
        try {
            commit("set_listVTThuocNhomMDDK", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response.data;
    },

    async getVattuMucDich({ commit, state }, data) {
        let response = await API.getVattuMucDich(this._vm.axios, data);
        return response.data.data;
    },

    async themMoiVatTuKHDN({ commit, state }, data) {
        let response = await API.themMoiVatTuKHDN(this._vm.axios, data);
        return response.data.data;
    },
    async getVattuMucDichKHDN({ commit, state }, data) {
        let response = await API.getVattuMucDichKHDN(this._vm.axios, data);
        return response.data.data;
    },
    async addDuLieuVatTuDKKHDN({ commit, state }, data) {
        let response = await API.addDuLieuVatTuDKKHDN(this._vm.axios, data);
        return response.data.data;
    },
    async getDSVatTuDaDKKHDN({ commit, state }, data) {
        let response = await API.getDSVatTuDaDKKHDN(this._vm.axios, data);
        return response.data.data;
    },

    //02-11-2023 Vienntl add form ChonDK
    async getDSDangKY_CDK({ commit, state }, data) {
        let response = await API.getDSDangKY_CDK(this._vm.axios, data);
        return response.data.data;
    },
    async getDSChiTietDangKY_CDK({ commit, state }, data) {
        let response = await API.getDSChiTietDangKY_CDK(this._vm.axios, data);
        return response.data.data;
    },
    async chapNhanDangKy({ commit, state }, data) {
        let response = await API.chapNhanDangKy(this._vm.axios, data);
        return response.data.data;
    },
    async getDSXemDangKy({ commit, state }, data) {
        let response = await API.getDSXemDangKy(this._vm.axios, data);
        return response.data.data;
    },
    async xoaVatTuDangKyKHDN({ commit, state }, data) {
        let response = await API.xoaVatTuDangKyKHDN(this._vm.axios, data);
        return response.data.data;
    },
    async getVatTuDangKyKHDN({ commit, state }, data) {
        let response = await API.getVatTuDangKyKHDN(this._vm.axios, data);
        return response.data.data;
    },
    async XoaDangKyKHDN({ commit, state }, dangKyId) {
        let response = await API.XoaDangKyKHDN(this._vm.axios, dangKyId);
        return response.data.data;
    },
};
