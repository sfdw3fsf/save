import API from '../../api/DieuChuyenSuaChuaThuHoiCCDCAPI'

export const actions = {    
    async getDSMucDich({ commit, state }) {
        let response = await API.getDSMucDich(this._vm.axios)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSDonVi({ commit, state },nhanVienId) {
        let response = await API.getDSDonVi(this._vm.axios,nhanVienId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSNhanVien({ commit, state }) {
        let response = await API.getDSNhanVien(this._vm.axios)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSChungTuCCDC({ commit, state },data) {
        let response = await API.getDSChungTuCCDC(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSChiTietVatTu({ commit, state },nxccId) {
        let response = await API.getDSChiTietVatTu(this._vm.axios,nxccId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    
    async getDSTongHop({ commit, state },nxccId) {
        let response = await API.getDSTongHop(this._vm.axios,nxccId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async xoaChungTuCCDC({ commit, state },nxccId) {
        let response = await API.xoaChungTuCCDC(this._vm.axios,nxccId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async hoanThienCCDC({ commit, state },data) {
        let response = await API.hoanThienCCDC(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async ThemMoiChungTuCCDC({ commit, state },data) {
        let response = await API.ThemMoiChungTuCCDC(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async CapNhapChungTuCCDC({ commit, state },data) {
        let response = await API.CapNhapChungTuCCDC(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSNVSuDungCCDC({ commit, state },Thang) {
        let response = await API.getDSNVSuDungCCDC(this._vm.axios,Thang)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSCayDonVi({ commit, state },donViId) {
        let response = await API.getDSCayDonVi(this._vm.axios,donViId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async kiemTraToTruong({ commit, state },donViId) {
        let response = await API.kiemTraToTruong(this._vm.axios,donViId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
    async getDSDonViThiCong({ commit, state },donViId) {
        let response = await API.getDSDonViThiCong(this._vm.axios,donViId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },

    async getDSNVTheoMucDichDonVi({ commit, state },data) {
        let response = await API.getDSNVTheoMucDichDonVi(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
        return response
    },
}