import API from '../../api/frmChiaCuonCapAPI'

export const actions = {
    async getDSVatTuDangKy({ commit, state },data) {
        let response = await API.getDSVatTuDangKy(this._vm.axios,data)
        return response.data.data || []
    },  
    async kiemTraSoLuongLoHang({ commit, state },data) {
        let response = await API.kiemTraSoLuongLoHang(this._vm.axios,data)
        return response.data.data || ""
    },  
}