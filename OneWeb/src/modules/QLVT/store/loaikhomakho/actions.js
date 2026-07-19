import API from "../../api/LoaiKhoMaKhoAPI";

export const actions = {
    async getDSKieuPhieu({ commit, state }, loaictId) {
        let response = await API.getDSKieuPhieu(this._vm.axios, loaictId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSChatLuong({ commit, state }, ) {
        let response = await API.getDSChatLuong(this._vm.axios, )
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSLoaiKhoMaKho({ commit, state }, data ) {
        let response = await API.getDSLoaiKhoMaKho(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSLoaiCT({ commit, state }, ) {
        let response = await API.getDSLoaiCT(this._vm.axios, )
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async capNhatMaKhoLoaiKho({ commit, state },data) {
        let response = await API.capNhatMaKhoLoaiKho(this._vm.axios,data)
        try {
            return response.data.data || ""
        } catch (error) {
            throw error
        }
    },
    async getDSLoaiKhoTheoMucDich({ commit, state },data) {
        let response = await API.getDSLoaiKhoTheoMucDich(this._vm.axios,data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSKhoTD({ commit, state },loaiKhoId) {
        let response = await API.getDSKhoTD(this._vm.axios,loaiKhoId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSDongThietBi({ commit, state },loaiKhoId) {
        let response = await API.getDSDongThietBi(this._vm.axios,loaiKhoId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
    async getDSHeThong({ commit, state },dongTBId) {
        let response = await API.getDSHeThong(this._vm.axios,dongTBId)
        try {
            return response.data.data || []
        } catch (error) {
            throw error
        }
    },
};
