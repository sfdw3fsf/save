import API from '../../api/TraCuuVatTuTrongKhoAPI'

export const actions = {
    async getDSKhoTheoNV ({ commit, state }, data) {
        let response = await API.getDSKhoTheoNV(this._vm.axios, data)
        try {
            commit('setDSKhoTheoNV', response.data.data || [])
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSTongHopTB ({ commit, state }, data) {
        let response = await API.getDSTongHopTB(this._vm.axios, data)
        try {
            commit('setDSTongHopTB', response.data.data || [])
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSChiTietTB ({ commit, state }, data) {
        let response = await API.getDSChiTietTB(this._vm.axios, data)
        try {
            commit('setDSChiTietTB', response.data.data || [])
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSThueBaoDangCap ({ commit, state }, data) {
        let response = await API.getDSThueBaoDangCap(this._vm.axios, data)
        try {
            commit('setDSThueBaoDangCap', response.data.data || [])
        } catch (error) {
            throw error
        }
        return response
    },
    getObjSearch ({commit, state}, data) {
        commit('setObjSearch', data)
      },

}