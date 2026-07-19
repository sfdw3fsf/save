import API from "../../api/TraCuuLichSuDieuChinhHoaDonAPI";
import CommonAPI from "../../api/Commons";

export const actions = {
    async traCuuLichSu({ commit, state }, data) {
        try {
            let response = await API.traCuuLichSu(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },
    async traCuuChiTiet({ commit, state }, data) {
        try {
            let response = await API.traCuuChiTiet(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async clearData({ commit, state }, data) {
        commit("clearData", data);
    }
};
