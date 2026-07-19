import API from '../../api/ChonVatTuCTCTAPI'

export const actions = {    
    async getDSVatTuChungTu({ commit, state },chungTuId) {
        let response = await API.getDSVatTuChungTu(this._vm.axios, chungTuId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSChiTietVTCT({ commit, state },data) {
        let response = await API.getDSChiTietVTCT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async ganGoDSChiTietVT({ commit, state },data) {
        let response = await API.ganGoDSChiTietVT(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
}