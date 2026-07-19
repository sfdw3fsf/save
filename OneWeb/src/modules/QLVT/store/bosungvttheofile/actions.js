import API from "../../api/BoSungVT_TheoFileAPI";

export const actions = {
    async uploadDSBoSungTheoFile({ commit, state }, data) {
        let response = await API.uploadDSBoSungTheoFile(this._vm.axios, data);
        return response.data;
    },
    async dayDanhSachBoSung({ commit, state }, data) {
        let response = await API.dayDanhSachBoSung(this._vm.axios, data);
        return response.data;
    },
};
