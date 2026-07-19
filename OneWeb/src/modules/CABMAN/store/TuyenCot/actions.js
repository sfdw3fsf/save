import TuyenCot from '../../api/TuyenCot';

var apiManager;
export const actions = {
    createApiManager: function() {
        apiManager = TuyenCot(this._vm.$bssApiCaller);
    },
    clearState: function ({ commit }) {
        commit('clearState');
    },
    layDsTrungTamVienThong: async function({ commit }) {
        try {
            var response = await apiManager.layDsTrungTamVienThong();
            commit('setDsTrungTamVienThong', response.data.data);
        } catch (error) {
            throw(error)
        }
        return response.data.data;
    },
    layDsToKyThuat: async function({ commit }, donvi_id) {
        try {
            var response = await apiManager.layDsToKyThuat(donvi_id);
            
            commit('setDsToKyThuat', response.data.data);
        } catch (error) {
            throw(error)
        }
        return response.data.data;
    },
    layDsTuyenCotTheoDonVi: async function({ commit }, donvi_id) {
        try {
            var response = await apiManager.layDsTuyenCotTheoDonVi(donvi_id);
            commit('setDsTuyenCot', response.data.data)
        } catch (error) {
            throw error;
        }
        return response.data.data;
    },
    layDsCotVaToQuanLyTheoTuyen: async function({ commit }, data) {
        try {
            var response = await apiManager.layDsCotVaToQuanLyTheoTuyen(data);
            commit('setDsCot', response.data.data.dsCot);
            commit('setDsTQLChuaGan', response.data.data.toQuanLyChuaGan);
            commit('setDsTQLDaGan', response.data.data.toQuanLyDaGan);
        } catch (error) {
            throw error;
        }
    },
    themTuyenCot: async function({ commit }, data) {
        try {
            var response = await apiManager.themTuyenCot(data);
        } catch (error) {
            throw error;
        }
    },
    capNhatTuyenCot: async function({ commit }, data) {
        try {
            var response = await apiManager.capNhatTuyenCot(data);
            console.log(response.data.data);
        } catch (error) {
            throw error;
        }
        return response.data.data;
    },
    xoaTuyenCot: async function({ commit }, data) {
        try {
            var response = await apiManager.xoaTuyenCot(data);
            console.log(response.data.data);
        } catch (error) {
            throw error;
        }
    }
}