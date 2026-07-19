import API from '../../api/GanThuocTinhAPI'

export const actions = {    
    async getDSThuocTinh({ commit, state },data) {
        let response = await API.getDSThuocTinh(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async capNhatThuocTinh({ commit, state },data) {
        let response = await API.capNhatThuocTinh(this._vm.axios,data)
        try {
            return response.data.data || ""
        } catch (error) {
            throw error
        }
        return response
    },
}