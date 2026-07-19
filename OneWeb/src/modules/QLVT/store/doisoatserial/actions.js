import API from '../../api/DoiSoatSerialAPI'

export const actions = {    
    
    async getDSSerialChuaDoiSoat({ commit, state },data) {
        let response = await API.getDSSerialChuaDoiSoat(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async capNhatSerialDoiSoat({ commit, state },data) {
        let response = await API.capNhatSerialDoiSoat(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
}