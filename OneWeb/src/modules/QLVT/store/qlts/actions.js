import API from '../../api/QLTSAPI'

export const actions = {
    async getDSThietBiChungTu ({ commit, state }, chungTuId) {
        let response = await API.getDSThietBiChungTu(this._vm.axios, chungTuId)
        try {
            return response.data.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSSerialDHSX ({ commit, state }, data) {
        let response = await API.getDSSerialDHSX(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSThietBiChuaGan ({ commit, state }, chungTuId) {
        let response = await API.getDSThietBiChuaGan(this._vm.axios, chungTuId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async capNhatSerialQLTS ({ commit, state }, data) {
        let response = await API.capNhatSerialQLTS(this._vm.axios, data)
        try {
            return response.data.data || ""
        } catch (error) {
            throw error
        }
    },
    async kiemTraTonSerial ({ commit, state }, serial) {
        let response = await API.kiemTraTonSerial(this._vm.axios, serial)
        try {
            return response.data.data || 0
        } catch (error) {
            throw error
        }
    },
    async kiemTraTonTaiMaVT ({ commit, state }, maVT) {
        let response = await API.kiemTraTonTaiMaVT(this._vm.axios, maVT)
        try {
            return response.data.data || 0
        } catch (error) {
            throw error
        }
    },
    async kiemTraChungTuKhoNhan ({ commit, state }, chungTuId) {
        let response = await API.kiemTraChungTuKhoNhan(this._vm.axios, chungTuId)
        try {
            return response.data.data || 0
        } catch (error) {
            throw error
        }
    },
    async getTTQLTS ({ commit, state }, data) {
        let response = await API.getTTQLTS(this._vm.axios, data)
        try {
            return response.data.data || "ERROR: đã có lỗi xảy ra khi lấy dữ liệu!"
        } catch (error) {
            throw error
        }
    },
    // async kiemTraSeriTonTaiQLTS ({ commit, state }, data) {
    //     let response = await API.kiemTraSeriTonTaiQLTS(this._vm.axios, data)
    //     try {
    //         return response.data.data || []
    //     } catch (error) {
    //         throw error
    //     }
    // },
}
