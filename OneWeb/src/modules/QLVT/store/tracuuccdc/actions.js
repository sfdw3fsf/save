import API from '../../api/TraCuuCCDCAPI'

export const actions = {
    async getDSDonVi ({ commit, state }, data) {
        let response = await API.getDSDonVi(this._vm.axios, data)
        try {
            // commit('setDSDonVi', response.data.data || [])
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSNhanVien ({ commit, state }, data) {
        let response = await API.getDSNhanVien(this._vm.axios, data)
        try {
            // commit('setDSNhanVien', response.data.data || [])
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSNhanVienTheoDV ({ commit, state }, data) {
        let response = await API.getDSNhanVienTheoDV(this._vm.axios, data)
        try {
            // commit('setDSNhanVien', response.data.data || [])
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDanhSachCCDC ({ commit, state }, data) {
        let response = await API.getDanhSachCCDC(this._vm.axios, data)
        try {
            // let res = response.data.data || []
            // res.forEach(a=>a.ISCHECK = parseInt(a.ISCHECK) == 1)
            // commit('setDanhSachCCDC', res || [])
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSTheoMaND ({ commit, state }, data) {
        let response = await API.getDSTheoMaND(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSDonViTheoMaNV ({ commit, state }, data) {
        let response = await API.getDSDonViTheoMaNV(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDonViTheoTenNV ({ commit, state }, data) {
        let response = await API.getDonViTheoTenNV(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
}