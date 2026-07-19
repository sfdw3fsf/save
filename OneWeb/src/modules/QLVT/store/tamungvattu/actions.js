import API from '../../api/TamUngVatTuAPI'

export const actions = {
    async getDSNhanVienGiaoNhan({ commit, state }, data) {
        let response = await API.getDSNhanVienGiaoNhan(this._vm.axios, data)
        return response.data.data
    },

    async getDSKho({ commit, state }, data) {
        let response = await API.getDSKho(this._vm.axios, data)
        return response.data.data
    },

    async getDSVatTu({ commit, state }, data) {
        let response = await API.getDSVatTu(this._vm.axios, data)
        return response.data.data
    },
    async getDSNhanVien({ commit, state }, data) {
        let response = await API.getDSNhanVien(this._vm.axios, data)
        return response.data.data
    },
    async getDSVatTuKhoGiaoNhan({ commit, state }, data) {
        let response = await API.getDSVatTuKhoGiaoNhan(this._vm.axios, data)
        return response.data.data
    },
    async getIDKhoCha({ commit, state }, data) {
        let response = await API.getIDKhoCha(this._vm.axios, data)       
        return response.data.data 
    },
    async getKey({ commit, state }, data) {
        let response = await API.getKey(this._vm.axios, data)
        return response.data.data
    },
    async addTamUngVatTu({ commit, state }, data) {
        let response = await API.addTamUngVatTu(this._vm.axios, data)
        return response.data.data
    },
    async tamUngVatTuNhanVien({ commit, state }, data) {
        let response = await API.tamUngVatTuNhanVien(this._vm.axios, data)
        return response.data.data
    },
}