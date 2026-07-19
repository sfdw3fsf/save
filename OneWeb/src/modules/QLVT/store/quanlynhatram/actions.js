import API from "../../api/QuanLyNhaTram";

export const actions = {
    async getDSLoaiVatTu({ commit, state }) {
        let response = await API.getDSLoaiVatTu(this._vm.axios);
        try {
            commit("setDSLoaiVatTu", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSMaVatTu({ commit, state }, data) {
        let response = await API.getDSMaVatTu(this._vm.axios, data.loaiVt);
        try {
            commit("setDSMaVatTu", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response;
    },
    async getDSTaiSanTB({ commit, state }, data) {
        let response = await API.getDSTaiSanTB(this._vm.axios, data);
        try {
            commit("setDSTaiSanTB", response.data.data || []);
        } catch (error) {
            throw error;
        }
        return response;
    }
};
