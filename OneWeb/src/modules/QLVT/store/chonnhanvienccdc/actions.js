import API from "../../api/ChonNhanVienCCDCAPI";

export const actions = {
    async getDanhMucDonVi ({ commit, state }, data) {
        let response = await API.getDanhMucDonVi(this._vm.axios, data)
        try {
            // commit("setDanhMucDonVi", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response.data.data || []
    },
    async getDSTinhTrangTBi ({ commit, state }, data) {
        let response = await API.getDSTinhTrangTBi(this._vm.axios, data)
        try {
            // commit("setDSTinhTrangTBi", response.data.data || []);
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getThongTinDonVi ({ commit, state }, data) {
        let response = await API.getThongTinDonVi(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getThongTinNhanVien ({ commit, state }, data) {
        let response = await API.getThongTinNhanVien(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getThongTinVatTu ({ commit, state }, data) {
        let response = await API.getThongTinVatTu(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSNhanVien ({ commit, state }, data) {
        let response = await API.getDSNhanVien(this._vm.axios, data)
        return response.data.data || []
    },
    async getDSDonViV2 ({ commit, state }, data) {
        let response = await API.getDSDonViV2(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSDonViV3 ({ commit, state }, data) {
        let response = await API.getDSDonViV3(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async KiemTraToTruong ({ commit, state }, data) {
        let response = await API.KiemTraToTruong(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },

};

