import API from '../../api/frmGiaHanHDAPI'

export const actions = {
    async TRACUU_HD_THEO_DK({ commit, state },data) {
        let response = await API.TRACUU_HD_THEO_DK(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async LAY_LICHSU_GIAHAN_HD({ commit, state },data) {
        let response = await API.LAY_LICHSU_GIAHAN_HD(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async giahan_hdms({ commit, state },data) {
        let response = await API.giahan_hdms(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

}