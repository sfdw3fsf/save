import LoaiTuApi from '../../api/LoaiTuApi'

export const actions = {
    async lietKeLoaiTu ({ commit }) {
        let response = await LoaiTuApi.lietKeLoaiTu(this._vm.axios)
        try {
            commit('setDsLoaiTu', response.data.data)
        } catch (err) {
            throw err
        }
        return response
    },
    setDsLoaiTu({ commit }, data) {
        commit('setDsLoaiTu', data)
    }
}