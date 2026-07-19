import API from '../../api/DanhSachQuyetToanVatTuAPI'

export const actions = {    
    async getDSDichVu({ commit, state },) {
        let response = await API.getDSDichVu(this._vm.axios,)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSNhanVien({ commit, state },data) {
        let response = await API.getDSNhanVien(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSKieuLapDat({ commit, state },) {
        let response = await API.getDSKieuLapDat(this._vm.axios,)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getDSKhoTheoNV({ commit, state },data) {
        let response = await API.getDSKhoTheoNV(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSLyDoCap({ commit, state },data) {
        let response = await API.getDSLyDoCap(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSLoaiHinhThueBao({ commit, state },dichVuId) {
        let response = await API.getDSLoaiHinhThueBao(this._vm.axios,dichVuId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getDSBaoCaoQuyetToan({ commit, state },data) {
        let response = await API.getDSBaoCaoQuyetToan(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSTongHopThuHoi({ commit, state },data) {
        let response = await API.getDSTongHopThuHoi(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
}