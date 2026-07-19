import API from "../../api/ChonChungTuCCDCAPI";

export const actions = {
    async getDSQuyenNguoiDung ({ commit, state }, data) {
        let response = await API.getDSQuyenNguoiDung(this._vm.axios, data)
        try {
            return  response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSLoaiChungTu ({ commit, state }, data) {
        let response = await API.getDSLoaiChungTu(this._vm.axios, data)
        try {
            // commit("setDSLoaiChungTu", response.data.data || []);
            return  response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSChungTuCCDC ({ commit, state }, data) {
        let response = await API.getDSChungTuCCDC(this._vm.axios, data)
        try {
            // commit("setDSChungTuCCDC", response.data.data || []);
            return  response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSChungTuXBH ({ commit, state }, data) {
        let response = await API.getDSChungTuXBH(this._vm.axios, data)
        try {
            return  response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSKho ({ commit, state }, data) {
        let response = await API.getDSKho(this._vm.axios, data)
        try {
            return response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSThietBi ({ commit, state }, data) {
        let response = await API.getDSThietBi(this._vm.axios, data)
        try {
            // commit("setDSThietBi", response.data.data || []);
            return  response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDSVatTu ({ commit, state }, data) {
        let response = await API.getDSVatTu(this._vm.axios, data)
        try {
            return  response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },
    async getDanhMucLoaiChungTu ({ commit, state }, data) {
        let response = await API.getDanhMucLoaiChungTu(this._vm.axios, data)
        try {
            return  response.data.data || []
        } catch (error) {
            throw error;
        }
        return response
    },


};

