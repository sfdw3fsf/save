import API from '../../api/SuaSerialGPAPI'

export const actions = {
    async getDSLichSuSuaSerialGP({ commit, state }, ) {
        let response = await API.getDSLichSuSuaSerialGP(this._vm.axios, )       
        return response.data.data
    },

    async capNhapSerialGP({ commit, state }, data) {
        let response = await API.capNhapSerialGP(this._vm.axios, data)
        return response.data.data
    },

    async uploadFile({ commit, state },data) {
        let response = await API.uploadFile(this._vm.axios,data)
        return response.data.data
    },
    async downloadFile({ commit, state },linkFile) {
        let response = await API.downloadFile(this._vm.axios,linkFile)
        return response.data.data
    },
    async getLinkFile({ commit, state },data) {
        let response = await API.getLinkFile(this._vm.axios,data)
        return response.data.data
    },
    async checkFileExist({ commit, state },data) {
        let response = await API.checkFileExist(this._vm.axios,data)
        return response.data.data
    },
    
    async deleteFile({ commit, state },data) {
        let response = await API.deleteFile(this._vm.axios,data)
        return response.data.data
    },
    
}