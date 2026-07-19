import API from '../../api/ThanhToanDonHangAPI'

export const actions = {
    async getDSDonHangTheoDK ({ commit, state }, data) {
        let response = await API.getDSDonHangTheoDK(this._vm.axios, data)
        return response.data.data || []
    },
    async getDSDonHangTheoDV ({ commit, state }, data) {
        let response = await API.getDSDonHangTheoDV(this._vm.axios, data)
        return response.data.data || []
    },
    async getDSHinhThucTra ({ commit, state }, data) {
        let response = await API.getDSHinhThucTra(this._vm.axios, data)
        return response.data.data || []
    },
    async getDSNhanVienThuNgan ({ commit, state }, data) {
        let response = await API.getDSNhanVienThuNgan(this._vm.axios, data)
        return response.data.data || []
    },
    async getDSDonViTinh ({ commit, state }, data) {
        let response = await API.getDSDonViTinh(this._vm.axios, data)
        try {
            // commit('setDSDonViTinh', response.data.data || "")
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSVatTuLuaChon ({ commit, state }, data) {
        let response = await API.getDSVatTuLuaChon(this._vm.axios, data)
        try {
            // commit('setCboVatTu_Filter', response.data.data || "")
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response.data.data
    },
    async KiemTraNgayTT ({ commit, state }, data) {
        let response = await API.KiemTraNgayTT(this._vm.axios, data)
        return response.data.data || []
    },
    async getKeys ({ commit, state }, data) {
        let response = await API.getKeys(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSThanhToanDK ({ commit, state }, data) {
        let response = await API.getDSThanhToanDK(this._vm.axios, data)
        try {
            // commit('setDSThanhToanDK', response.data.data || "")
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSVatTuTheoDH ({ commit, state }, data) {
        let response = await API.getDSVatTuTheoDH(this._vm.axios, data)
        try {
            // commit('setDSVatTuTheoDH', response.data.data || "")
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getDSVatTuThanhToan ({ commit, state }, data) {
        let response = await API.getDSVatTuThanhToan(this._vm.axios, data)
        return response.data.data || []
    },
    async XoaPhieuThanhToan ({ commit, state }, data) {
        let response = await API.XoaPhieuThanhToan(this._vm.axios, data)
        return response.data.data || []
    },
    async CapNhatTrangThaiDH ({ commit, state }, data) {
        let response = await API.CapNhatTrangThaiDH(this._vm.axios, data)
        return response.data.data || ""
    },
    async TaoBackUpDSVatTu ({ commit, state }, data) {
        let response = await API.TaoBackUpDSVatTu(this._vm.axios, data)
        return response.data.data || []
    },
    async XoaChungTuPhieuTT ({ commit, state }, data) {
        let response = await API.XoaChungTuPhieuTT(this._vm.axios, data)
        return response.data.data || []
    },
    async TaoDonHang ({ commit, state }, data) {
        let response = await API.TaoDonHang(this._vm.axios, data)
        return response.data.data || []
    },
    async CapNhatDonHang ({ commit, state }, data) {
        let response = await API.CapNhatDonHang(this._vm.axios, data)
        return response.data.data || []
    },
    async CapNhatBackUpDonHangTT ({ commit, state }, data) {
        let response = await API.CapNhatBackUpDonHangTT(this._vm.axios, data)
        return response.data.data || []
    },
}