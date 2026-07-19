import API from '../../api/frmGanSeriAPI'

export const actions = {
    // async getDanhMucTinhTrangTBi({ commit, state },data) {
    //     let response = await API.getDanhMucTinhTrangTBi(this._vm.axios, data)
    //     try {
    //       return response.data.data || []
    //     } catch (error) {
    //         throw error
    //     }
    //     return response
    //   },

    // async getSeriDieuChinh({ commit, state },data) {
    //     let response = await API.getSeriDieuChinh(this._vm.axios, data)
    //     try {
    //         return response.data.data || []
    //     } catch (error) {
    //         throw error
    //     }
    //     return response
    // },

    // async CapNhatSeriDieuChinh({ commit, state },data) {
    // let response = await API.CapNhatSeriDieuChinh(this._vm.axios, data)
    //     try {
    //         return response.data.data || []
    //     } catch (error) {
    //         throw error
    //     }
    //     return response
    // },
    async getDSThietBiSeri({ commit, state },data) {
        let response = await API.getDSThietBiSeri(this._vm.axios, data)
        return response.data.data
    },
    async getDSKhoTheoNV({ commit, state },data) {
        let response = await API.getDSKhoTheoNV(this._vm.axios, data)
        return response.data.data
    },
    async KiemTraSLGanSeri({ commit, state },data) {
        let response = await API.KiemTraSLGanSeri(this._vm.axios, data)
        return response.data.data
    },

    async KiemTraChiTietVatTu({ commit, state },data) {
        let response = await API.KiemTraChiTietVatTu(this._vm.axios, data)
        return response.data.data
    },

    async CapNhatSLChiTietVatTu({ commit, state },data) {
        let response = await API.CapNhatSLChiTietVatTu(this._vm.axios, data)
        return response.data.data
    },

    async CapNhatChungTu({ commit, state },data) {
        let response = await API.CapNhatChungTu(this._vm.axios, data)
        return response.data.data
    },

    async getDSCuonCapV2({ commit, state },data) {
        let response = await API.getDSCuonCapV2(this._vm.axios, data)
        return response.data.data
    },

    async ThemChiTietThietBi({ commit, state },data) {
        let response = await API.ThemChiTietThietBi(this._vm.axios, data)
        return response.data.data
    },
    async CatChuyenCap({ commit, state },data) {
        let response = await API.CatChuyenCap(this._vm.axios, data)
        return response.data.data
    },

    async getKeys({ commit, state },data) {
        let response = await API.getKeys(this._vm.axios, data)
        return response.data.data
    },

}