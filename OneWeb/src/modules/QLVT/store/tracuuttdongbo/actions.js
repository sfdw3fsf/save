import API from '../../api/TraCuuTTDongBoAPI'

export const actions = {    
    async getDSTTDongBo({ commit, state }, data) {
        let response = await API.getDSTTDongBo(this._vm.axios, data)
        return response.data.data || []      
    },
    async kiemTraTrangThaiIMS({ commit, state }, data) {
        let response = await API.kiemTraTrangThaiIMS(this._vm.axios, data)
        return response.data.data 
    },
    async getSoIDFMIS({ commit, state }, data) {
        let response = await API.getSoIDFMIS(this._vm.axios, data)
        return response.data.data 
    },
}