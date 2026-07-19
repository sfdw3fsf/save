import QuyHoachVLAN from '../../api/QuyHoachVLAN';

export const actions = {
    async layDsVungQuyHoach({ commit }) {
        var response = await QuyHoachVLAN.layDsVungQuyHoach(this._vm.axios);
        try {
            commit('setDsVungQuyHoach', response.data.data);
        } catch(err) {
            throw err;
        }
        return response.data.data;
    },
    async layDsDichVu({ commit }) {
        var response = await QuyHoachVLAN.layDsDichVu(this._vm.axios);
        try {
            commit('setDsDichVu', response.data.data);
        } catch(err) {
            throw err;
        }
        return response.data.data;
    },
    async layDsLoaiDichVu({ commit }, data) {
        var response = await QuyHoachVLAN.layDsLoaiDichVu(this._vm.axios, data);
        try {
            commit('setDsLoaiDichVu', response.data.data);
        } catch(err) {
            throw err;
        }
        return response.data.data;
    },
    async layDsQuyHoachVLAN({ commit }, data) {
        var response = await QuyHoachVLAN.layDsQuyHoachVLAN(this._vm.axios, data);
        try {
            commit('setDsQuyHoachVLAN', response.data.data);
        } catch(err) {
            throw err;
        }
        return response.data.data;
    },
    async capNhatQuyHoachVLAN({ commit }, data) {
        try {
            var response = await QuyHoachVLAN.capNhatQuyHoachVLAN(this._vm.axios, data);
            return response.data.data;
        } catch(err) {
            console.log(err)
            throw err;
        }
    },
    async themMoiQuyHoachVLAN({ commit }, data) {
        try {
            var response = await QuyHoachVLAN.themMoiQuyHoachVLAN(this._vm.axios, data);
            return response.data.data;
        } catch(err) {
            console.log(err)
            throw err;
        }
    },
    async xoaQuyHoachVLAN({ commit }, data) {
        try {
            var response = await QuyHoachVLAN.xoaQuyHoachVLAN(this._vm.axios, data);
            return response.data.data;
        } catch(err) {
            console.log(err)
            throw err;
        }
    },
    clearState ({ commit }) {
        commit('clearState')
    }
}