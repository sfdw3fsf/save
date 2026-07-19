import API from '../../api/TraCuuSerialThietBiAPI'

export const actions = {
    async getDSTimKiem({ commit, state }, data) {
        let response = await API.getDSTimKiem(this._vm.axios, data)
        return response.data.data
    },
    async getDSChungTu({ commit, state }, data) {
        let response = await API.getDSChungTu(this._vm.axios, data)
        return response.data.data
    },
    async getDSTamUng({ commit, state }, data) {
        let response = await API.getDSTamUng(this._vm.axios, data)
        return response.data.data
    },
    async getDSThueBao({ commit, state }, data) {
        let response = await API.getDSThueBao(this._vm.axios, data)
        return response.data.data
    },
    async getDSDangKyHopDong({ commit, state }, data) {
        let response = await API.getDSDangKyHopDong(this._vm.axios, data)
        return response.data.data
    },
    async getDSMaThietBi({ commit, state }, data) {
        let response = await API.getDSMaThietBi(this._vm.axios, data)
        return response.data.data
    },
    async getDSTongHop({ commit, state }, data) {
        let response = await API.getDSTongHop(this._vm.axios, data)
        return response.data.data
    },
    async getDSSerialGP({ commit, state }, data) {
        let response = await API.getDSSerialGP(this._vm.axios, data)
        return response.data.data
    },
    async getDSSerialTheoSerialGP({ commit, state }, data) {
        let response = await API.getDSSerialTheoSerialGP(this._vm.axios, data)
        return response.data.data
    },
    async getDSThamSoMD({ commit, state }, kieuId) {
        let response = await API.getDSThamSoMD(this._vm.axios, kieuId)
        return response.data.data
    },
    async getThongTinQuyetToanSerial({ commit, state }, serial) {
        let response = await API.getThongTinQuyetToanSerial(this._vm.axios, serial)
        return response.data.data
    },

    async capNhatTrangThaiSerial({ commit, state }, data) {
        let response = await API.capNhatTrangThaiSerial(this._vm.axios, data)
        return response.data.data
    },

    async testTree({ commit, state }, ) {
        let response = await API.testTree(this._vm.axios, )
        return response.data.data
    },

    async getHinhAnh({ commit, state }, data) {
        let response = await API.getHinhAnh(this._vm.axios, data)
        return response.data.data
    },
    async getLinkFile({ commit, state },data) {
        let response = await API.getLinkFile(this._vm.axios,data)
        return response.data 
    },
    async checkFileExist({ commit, state },data) {
        let response = await API.checkFileExist(this._vm.axios,data)
        return response.data 
    },
}