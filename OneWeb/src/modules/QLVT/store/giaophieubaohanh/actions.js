import API from "../../api/GiaoPhieuBaoHanh";

export const actions = {
    // getObjSearch ({commit, state}, data) {
    //   commit('setObjSearch', data)
    // },
    async getDanhSachKho({ commit, state }, data) {
        let response = await API.getDanhSachKho(
            this._vm.axios,
            data.nhanVienId,
            data.donViId
        );
        try {
            commit("setDanhSachKho", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSHeThongQuyTrinh({ commit, state }, data) {
        let response = await API.getDSHeThongQuyTrinh(
            this._vm.axios,
            data.nhomQtId
        );
        try {
            commit("setDSHeThongQuyTrinh", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSHuongGiao({ commit, state }, data) {
        let response = await API.getDSHuongGiao(this._vm.axios, data);
        try {
            commit("setDSHuongGiao", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSDonViTheoLoaiDichVu({ commit, state }, data) {
        let response = await API.getDSDonViTheoLoaiDichVu(
            this._vm.axios,
            data.loaiDvuId
        );
        try {
            commit("setDSDonViTheoLoaiDichVu", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDsGiaoPhieuBaoHanh({ commit, state }, data) {
        let response = await API.getDsGiaoPhieuBaoHanh(this._vm.axios, data);
        try {
            commit("setDSGiaoPhieuBaoHanh", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async taoChungTu({ commit, state }, data) {
        let response = await API.taoChungTu(this._vm.axios, data);
        try {
            commit("setTaoChungTu", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSLuongThaoTac({ commit, state }, data) {
        let response = await API.getDSLuongThaoTac(
            this._vm.axios,
            data.nhomQtId
        );
        try {
            commit("setDSLuongThaoTac", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSThietBiBaoHanhTra({ commit, state }, data) {
        let response = await API.getDSThietBiBaoHanhTra(
            this._vm.axios,
            data.baoHanhId
        );
        try {
            commit("setDSThietBiBaoHanhTra", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSMucDichXuatVatTuKho({ commit, state }, data) {
        let response = await API.getDSMucDichXuatVatTuKho(
            this._vm.axios,
            data.maMd
        );
        try {
            commit("setDSMucDichXuatVatTuKho", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSKhoGiaoTheoToTruong({ commit, state }, data) {
        let response = await API.getDSKhoGiaoTheoToTruong(
            this._vm.axios,
            data.loaiKhoId,
            data.donViId
        );
        try {
            commit("setDSKhoGiaoTheoToTruong", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getCapNhatGPBHHuongGiao({ commit, state }, data) {
        let response = await API.getCapNhatGPBHHuongGiao(
            this._vm.axios,
            data.huongGiaoId,
            data.baoHanhId
        );
        try {
            commit("setCapNhatGPBHHuongGiao", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getCapNhatBaoHanhKho({ commit, state }, data) {
        let response = await API.getCapNhatBaoHanhKho(
            this._vm.axios,

            data.khoId,
            data.baoHanhId
        );
        try {
            commit("setCapNhatBaoHanhKho", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSThietBiKhoGiao({ commit, state }, data) {
        let response = await API.getDSThietBiKhoGiao(
            this._vm.axios,

            data.serial,
            data.khoGiaoId
        );
        try {
            commit("setDSThietBiKhoGiao", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getCapNhatBaoHanhThietBi({ commit, state }, data) {
        let response = await API.getCapNhatBaoHanhThietBi(
            this._vm.axios,

            data.khoId,
            data.baoHanhId
        );
        try {
            commit("setCapNhatBaoHanhThietBi", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSThietBiBaoHanh({ commit, state }, data) {
        let response = await API.getDSThietBiBaoHanh(
            this._vm.axios,

            data.baoHanhId
        );
        try {
            commit("setDSThietBiBaoHanh", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSKiemTraVatTuTrongBH({ commit, state }, data) {
        let response = await API.getDSKiemTraVatTuTrongBH(
            this._vm.axios,

            data.baoHanhId,
            data.khoGiaoId
        );
        try {
            commit("setDSKiemTraVatTuTrongBH", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSKiemTraVatTuTrongKho({ commit, state }, data) {
        let response = await API.getDSKiemTraVatTuTrongKho(
            this._vm.axios,
            data
        );
        try {
            commit("setDSKiemTraVatTuTrongKho", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSKiemTraTaoChungTu({ commit, state }, data) {
        let response = await API.getDSKiemTraTaoChungTu(this._vm.axios, data);
        try {
            commit("setDSKiemTraTaoChungTu", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async CapNhatGiaoPhieuBH({ commit, state }, data) {
        let response = await API.CapNhatGiaoPhieuBH(this._vm.axios, data);
        try {
            commit("setCapNhatGiaoPhieuBH", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSKieuBaoHanh({ commit, state }, data) {
        let response = await API.getDSKieuBaoHanh(this._vm.axios, data);        
        return response.data.data;
    },
    async HoanThienBaoHanh({ commit, state }, data) {
        let response = await API.HoanThienBaoHanh(this._vm.axios, data);
        try {
            commit("setHoanThienBaoHanh", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getDSLoaiDichVuHuongGiao({ commit, state }, data) {
        let response = await API.getDSLoaiDichVuHuongGiao(
            this._vm.axios,
            data.huongGiaoId
        );
        try {
            commit("setDSLoaiDichVuHuongGiao", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async TaoGiaoPhieuBaoHanh({ commit, state }, data) {
        let response = await API.TaoGiaoPhieuBaoHanh(this._vm.axios, data);
        try {
            commit("setTaoGiaoPhieuBaoHanh", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async CapNhatDonViBH({ commit, state }, data) {
        let response = await API.CapNhatDonViBH(this._vm.axios, data);
        try {
            commit("setCapNhatDonViBH", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async clearData({ commit, state }, data) {
        commit("clearData", data);
    },
    async mapTKQuyTrinh({ commit, state }, data) {
        let response = await API.mapTKQuyTrinh(this._vm.axios, data);
        try {
            commit("setMapTKQuyTrinh", response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data;
    },
    async getLayTenMenu({ commit, state }, data) {
        let response = await API.getLayTenMenu(this._vm.axios, data);
        try {
            return response.data.data || [];
        } catch (error) {
            throw error;
        }
        return response;
    },

    async tongHopGiaoPhieuBaohanh({ commit, state }, data) {
        let response = await API.tongHopGiaoPhieuBaohanh(this._vm.axios, data);
        return response.data.data
    },
    async huyGiaoPhieu({ commit, state }, data) {
        let response = await API.huyGiaoPhieu(this._vm.axios, data);
        return response.data.data
    },
    async getDSCayDonVi({ commit, state }, donViId) {
        let response = await API.getDSCayDonVi(this._vm.axios, donViId);
        return response.data.data
    },
    async getParamsBaoCao({ commit, state }, maBc) {
        let response = await API.getParamsBaoCao(this._vm.axios, maBc);
        return response.data.data
    }
};
