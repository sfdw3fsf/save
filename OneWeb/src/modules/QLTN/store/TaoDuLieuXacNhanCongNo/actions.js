import API from "../../api/TaoDuLieuXacNhanCongNoAPI";
import CommonAPI from "../../api/Commons";

export const actions = {
    async getLanTao({ commit, state }, data) {
        try {
            let response = await API.getLanTao(this._vm.axios, data);
            // commit("setListDonVi", response.data.data);
            return response;
        } catch (error) {
            // commit("setListDonVi", []);
            return error.response.data;
        }
    },

    async capNhatTuFile({ commit, state }, data) {
        try {
            let response = await API.capNhatTuFile(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async getChuKy({ commit, state }, data) {
        try {
            let response = await CommonAPI.getChuKyNoTheoKyCuoc(
                this._vm.axios,
                data
            );
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async taoCongNo({ commit, state }, data) {
        try {
            let response = await API.taoCongNo(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },

    async getTuFile({ commit, state }, data) {
        try {
            let response = await API.getTuFile(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async getListCongNo({ commit, state }, data) {
        try {
            let response = await API.getListCongNo(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async getXuatFileExcel({ commit, state }, data) {
        try {
            let response = await API.getXuatFileExcel(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },

    async clearData({ commit, state }, data) {
        commit("clearData", data);
    }
};
