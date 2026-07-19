import API from '../../api/DongBoCCDCAPI'

export const actions = {
    async getDSThietBiCCDC({ commit, state },chungTuId) {
        let response = await API.getDSThietBiCCDC(this._vm.axios,chungTuId)        
        return response.data.data         
    },
    async kiemTraDongBoCCDC({ commit, state },data) {
        let response = await API.kiemTraDongBoCCDC(this._vm.axios,data)        
        return response.data.data         
    },
    async kiemTraCapNhapQLTS({ commit, state },chungTuId) {
        let response = await API.kiemTraCapNhapQLTS(this._vm.axios,chungTuId)        
        return response.data.data         
    },
    async getEmailNguoiDung({ commit, state }) {
        let response = await API.getEmailNguoiDung(this._vm.axios)        
        return response.data.data         
    },
    async capNhatThietBiQLTS({ commit, state },data) {
        let response = await API.capNhatThietBiQLTS(this._vm.axios,data)        
        return response.data.data         
    },
    async dongBoCCDC({ commit, state },data) {
        let response = await API.dongBoCCDC(this._vm.axios,data)        
        return response.data.data         
    },
}