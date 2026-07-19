import API from "../../api/TraCuuHopDongVaDonHangAPI";

export const actions = {
    async getDSDonViTinh ({ commit, state }, data) {
        let response = await API.getDSDonViTinh(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSTrangThaiHD ({ commit, state }, data) {
        let response = await API.getDSTrangThaiHD(this._vm.axios, data)
        try {
            // commit("setDSTrangThaiHD", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSMucDichDK ({ commit, state }, data) {
        let response = await API.getDSMucDichDK(this._vm.axios, data)
        try {
            // commit("setDSMucDichDK", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSKhoNhaCC ({ commit, state }, data) {
        let response = await API.getDSKhoNhaCC(this._vm.axios, data)
        try {
            // commit("setDSKhoNhaCC", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSDonVi ({ commit, state }, data) {
        let response = await API.getDSDonVi(this._vm.axios, data)
        try {
            // commit("setDSDonVi", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSTrangThaiDonHang ({ commit, state }, data) {
        let response = await API.getDSTrangThaiDonHang(this._vm.axios, data)
        try {
            // commit("setDSTrangThaiDonHang", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSHopDongDK ({ commit, state }, data) {
        let response = await API.getDSHopDongDK(this._vm.axios, data)
        try {
            // commit("setDSHopDongDK", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },

    async getDSMucDichMuaSam ({ commit, state }, data) {
        let response = await API.getDSMucDichMuaSam(this._vm.axios, data)
        try {
            // commit("setDSMucDichMuaSam", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },

    async getDSDonHangDK ({ commit, state }, data) {
        let response = await API.getDSDonHangDK(this._vm.axios, data)
        try {
            // commit("setDSDonHangDK", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSTrangThaiDK ({ commit, state }, data) {
        let response = await API.getDSTrangThaiDK(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSVatTu ({ commit, state }, data) {
        let response = await API.getDSVatTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    
    
    async getDSNhanVienNghiemThu ({ commit, state }, data) {
        let response = await API.getDSNhanVienNghiemThu(this._vm.axios, data)
        try {
            return response.data.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    
    async getTraCuuHopDong ({ commit, state }, data) {
        let response = await API.getTraCuuHopDong(this._vm.axios, data)
        try {
            // commit("setTraCuuHopDong", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getTraCuuDonHang ({ commit, state }, data) {
        let response = await API.getTraCuuDonHang(this._vm.axios, data)
        try {
            // commit("setTraCuuDonHang", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSDonHangTheoHD ({ commit, state }, data) {
        let response = await API.getDSDonHangTheoHD(this._vm.axios, data)
        try {
            // commit("setDSDonHangTheoHD", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSVatTuTheoHD ({ commit, state }, data) {
        let response = await API.getDSVatTuTheoHD(this._vm.axios, data)
        try {
            // commit("setDSVatTuTheoHD", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async CapNhatHDHetHan ({ commit, state }, data) {
        let response = await API.CapNhatHDHetHan(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getLichSuGiaHan ({ commit, state }, data) {
        let response = await API.getLichSuGiaHan(this._vm.axios, data)
        return response
    },
    async getVatTuChiTietTheoDH ({ commit, state }, data) {
        let response = await API.getVatTuChiTietTheoDH(this._vm.axios, data)
        try {
            // commit("setDSVatTuTheoDH", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async TraCuuHDTheoDonHang ({ commit, state }, data) {
        let response = await API.TraCuuHDTheoDonHang(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async XuatDuLieu ({ commit, state }, data) {
        let response = await API.XuatDuLieu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },

    async getDS_NV_TG_V2 ({ commit, state }, data) {
        let response = await API.getDS_NV_TG_V2(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },

    
};
