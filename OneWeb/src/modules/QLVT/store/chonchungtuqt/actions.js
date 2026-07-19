import API from '../../api/ChonChungTuQTAPI'

export const actions = {
    async getDSDonVi ({ commit, state }, data) {
        let response = await API.getDSDonVi(this._vm.axios, data)
        return response.data.data        
    },
    async getDSChungTuHoanUng ({ commit, state }, data) {
        let response = await API.getDSChungTuHoanUng(this._vm.axios, data)
        return response.data.data        
    },
    async getDSVatTu ({ commit, state }, chungTuId) {
        let response = await API.getDSVatTu(this._vm.axios, chungTuId)
        return response.data.data        
    },
    async themDeNghiChungTu ({ commit, state }, chungTuId) {
        let response = await API.themDeNghiChungTu(this._vm.axios, chungTuId)
        return response.data.data        
    },
}
