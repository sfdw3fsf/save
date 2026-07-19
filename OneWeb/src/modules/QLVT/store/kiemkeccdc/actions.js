import API from '../../api/KiemKeCCDCAPI'

export const actions = {
    async getDSDonViTheoNhanVien({ commit, state }, nhanVienId) {
        let response = await API.getDSDonViTheoNhanVien(this._vm.axios,nhanVienId )
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSAllNhanVien({ commit, state }, ) {
        let response = await API.getDSAllNhanVien(this._vm.axios, )
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSTrangThai({ commit, state }, ) {
        let response = await API.getDSTrangThai(this._vm.axios, )
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getToTruongId({ commit, state }, donViId) {
        let response = await API.getToTruongId(this._vm.axios, donViId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSThietBi({ commit, state }, donViId) {
        let response = await API.getDSThietBi(this._vm.axios, donViId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async capNhatTinhTrangTbi({ commit, state }, data) {
        let response = await API.capNhatTinhTrangTbi(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async themDSThietBi({ commit, state }, data) {
        let response = await API.themDSThietBi(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
}