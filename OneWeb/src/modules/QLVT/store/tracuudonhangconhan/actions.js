import API from '../../api/TraCuuDonHangConHanAPI'

export const actions = {
    async getDSDonVi({ commit, state }, data) {
        let response = await API.getDSDonVi(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSDonHang({ commit, state }, data) {
        let response = await API.getDSDonHang(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSVatTuDonHang({ commit, state }, data) {
        let response = await API.getDSVatTuDonHang(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
}