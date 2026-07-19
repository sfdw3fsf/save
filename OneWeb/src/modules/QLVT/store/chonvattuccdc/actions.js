import API from '../../api/ChonVatTuCCDCAPI'

export const actions = {    
    
    async getDSNhanVien({ commit, state }, ) {
        let response = await API.getDSNhanVien(this._vm.axios, )
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSCCDCNVDV({ commit, state }, data) {
        let response = await API.getDSCCDCNVDV(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
}