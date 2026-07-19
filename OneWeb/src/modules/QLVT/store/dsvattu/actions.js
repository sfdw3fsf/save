import API from "../../api/DSVatTuAPI";

export const actions = {
    async getDSVatTuTheoThamSo ({ commit, state }, data) {
        let response = await API.getDSVatTuTheoThamSo(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response.data.data || []
    },
    async getDSLoHang ({ commit, state }, data) {
        let response = await API.getDSLoHang(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response.data.data || []
    },
    async getDanhMucChatLuong ({ commit, state }, data) {
        let response = await API.getDanhMucChatLuong(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response.data.data || []
    },
    async getDSLoHangTheoMa ({ commit, state }, data) {
        let response = await API.getDSLoHangTheoMa(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response.data.data || []
    },
    async getThongTinThietBi ({ commit, state }, data) {
        let response = await API.getThongTinThietBi(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response.data.data || []
    },
    async getThongTinHeThongTheoTB ({ commit, state }, data) {
        let response = await API.getThongTinHeThongTheoTB(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response.data.data || []
    },
};

