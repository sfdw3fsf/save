import API from '../../api/DotTongHopAPI'

export const actions = {    
    async getDSTongHopThang({ commit, state }, data) {
        let response = await API.getDSTongHopThang(this._vm.axios, data)
        return response.data.data
    },
    
    async getDSDotDk({ commit, state }) {
        let response = await API.getDSDotDk(this._vm.axios)
        return response.data.data
    },
    async getDotTongHop({ commit, state }, data) {
        let response = await API.getDotTongHop(this._vm.axios, data)
        return response.data.data
    },
    async getKey({ commit, state }, data) {
        let response = await API.getKey(this._vm.axios, data)
        return response.data.data
    },
    async capNhapDotTongHop({ commit, state }, data) {
        let response = await API.capNhapDotTongHop(this._vm.axios, data)
        return response.data.data
    },
    async kiemTraVatTuTongHop({ commit, state }, tongHopId) {
        let response = await API.kiemTraVatTuTongHop(this._vm.axios, tongHopId)
        return response.data.data
    },
    async getDSFileCVTongHop({ commit, state }, data) {
        let response = await API.getDSFileCVTongHop(this._vm.axios, data)
        return response.data.data
    },
    async kiemTraDotTongHop({ commit, state }, data) {
        let response = await API.kiemTraDotTongHop(this._vm.axios, data)
        return response.data.data
    },
    async duyetDotTongHop({ commit, state }, data) {
        let response = await API.duyetDotTongHop(this._vm.axios, data)
        return response.data.data
    },

    async kiemTraHopDongDaLap({ commit, state }, data) {
        let response = await API.kiemTraHopDongDaLap(this._vm.axios, data)
        return response.data.data
    },
    async kiemTraChungTuDaLap({ commit, state }, data) {
        let response = await API.kiemTraChungTuDaLap(this._vm.axios, data)
        return response.data.data
    },

    async getDSDangKyTongHop({ commit, state }, data) {
        let response = await API.getDSDangKyTongHop(this._vm.axios, data)
        return response.data.data
    },
    async getDSFileCV({ commit, state }, data) {
        let response = await API.getDSFileCV(this._vm.axios, data)
        return response.data.data
    },
    async getMaxDotTongHop({ commit, state }, data) {
        let response = await API.getMaxDotTongHop(this._vm.axios, data)
        return response.data.data
    },

    async kiemTraDangKyDotTH({ commit, state }, data) {
        let response = await API.kiemTraDangKyDotTH(this._vm.axios, data)
        return response.data.data
    },
    async taoDanhSachTongHopDangKy({ commit, state }, data) {
        let response = await API.taoDanhSachTongHopDangKy(this._vm.axios, data)
        return response.data.data
    },

    async uploadFile({ commit, state }, data) {
        let response = await API.uploadFile(this._vm.axios, data)
        return response.data.data
    },

    async downloadFile({ commit, state }, data) {
        let response = await API.downloadFile(this._vm.axios, data)
        return response.data.data
    },
    async getLinkFile({ commit, state }, data) {
        let response = await API.getLinkFile(this._vm.axios, data)
        return response.data.data
    },
    async checkFileExist({ commit, state }, data) {
        let response = await API.checkFileExist(this._vm.axios, data)
        return response.data.data
    },
    async deleteFile({ commit, state }, data) {
        let response = await API.deleteFile(this._vm.axios, data)
        return response.data.data
    },

    async capNhapFileCV({ commit, state }, data) {
        let response = await API.capNhapFileCV(this._vm.axios, data)
        return response.data.data
    },

    async xoaDanhSachTongHop({ commit, state }, data) {
        let response = await API.xoaDanhSachTongHop(this._vm.axios, data)
        return response.data.data
    },

}