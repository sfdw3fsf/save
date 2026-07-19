import API from '../../api/QuanLyThietBiBTSAPI'

export const actions = {
    async getDSBTSNodeb({ commit, state }, data) {
        try {
            let response = await API.getDSBTSNodeb(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getThongTinThietBiBTS({ commit, state }, data) {
        try {
            let response = await API.getThongTinThietBiBTS(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async getDanhMucBTS({ commit, state }, data) {
        try {
            let response = await API.getDanhMucBTS(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },
    async CapNhatThongTinBTS({ commit, state }, data) {
        try {
            let response = await API.CapNhatThongTinBTS(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    async getChiTietBTS({ commit, state }, data) {
        try {
            let response = await API.getChiTietBTS(this._vm.axios, data)
            return response.data.data || []
        } catch (err) {
            throw err
        }
    },

    
}