import API from '../../api/TraCuuDeNghiAPI'

export const actions = {
    async getDSLoaiPhieu({ commit, state }, data) {
        let response = await API.getDSLoaiPhieu(this._vm.axios, data)
        return response.data.data
    },
    async getDSPhieu({ commit, state }, data) {
        let response = await API.getDSPhieu(this._vm.axios, data)
        return response.data.data
    },
    async getDSChiTietPhieu({ commit, state }, data) {
        let response = await API.getDSChiTietPhieu(this._vm.axios, data)
        return response.data.data
    },
    async getDSNhanVienTheoPhieu({ commit, state }, phieuId) {
        let response = await API.getDSNhanVienTheoPhieu(this._vm.axios, phieuId)
        return response.data.data
    },
    async downloadFile({ commit, state },data) {
        let response = await API.downloadFile(this._vm.axios,data)
        return response.data 
    },
    async getLinkFile({ commit, state },data) {
        let response = await API.getLinkFile(this._vm.axios,data)
        return response.data 
    },
    async checkFileExist({ commit, state },data) {
        let response = await API.checkFileExist(this._vm.axios,data)
        return response.data 
    },
    async deleteFile({ commit, state },data) {
        let response = await API.deleteFile(this._vm.axios,data)
        return response.data 
    },
}