import API from "../../api/XacNhanHoaDonChuyenKhoan";

export const actions = {
    async getNVTC({ commit, state }, data) {
        try {
            let response = await API.getNVTC(this._vm.axios, data);
            if (Array.isArray(response.data.data)) {
                commit("setNVTC", response.data.data);
            } else {
                commit("setNVTC", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setNVTC", []);
            return error.response.data;
        }
    },
    async getLuotGiao({ commit, state }, data) {
        try {
            let response = await API.getLuotGiao(this._vm.axios, data);
            if (Array.isArray(response.data.data)) {
                commit("setLuotGiao", response.data.data);
            } else {
                commit("setLuotGiao", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setLuotGiao", []);
            return error.response.data;
        }
    },

    async getListHoaDon({ commit, state }, data) {
        try {
            let response = await API.getListHoaDon(this._vm.axios, data);
            if (Array.isArray(response.data.data)) {
                commit("setListHoaDon", response.data.data);
            } else {
                commit("setListHoaDon", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setListHoaDon", []);
            return error.response.data;
        }
    },

    async getLyDoTra({ commit, state }, data) {
        try {
            let response = await API.getLyDoTra(this._vm.axios, data);
            response.data.data.unshift({
                LYDOTRA_ID: 0,
                NOIDUNG: "Chưa có lý do"
            });
            if (Array.isArray(response.data.data)) {
                commit("setLyDoTra", response.data.data);
            } else {
                commit("setLyDoTra", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setLyDoTra", []);
            return error.response.data;
        }
    },

    async capNhatTraPhieuChuyenKhoan({ commit, state }, data) {
        try {
            let response = await API.capNhatTraPhieuChuyenKhoan(
                this._vm.axios,
                data
            );
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async capNhatHuyTraPhieuChuyenKhoan({ commit, state }, data) {
        try {
            let response = await API.capNhatHuyTraPhieuChuyenKhoan(
                this._vm.axios,
                data
            );
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async huyHoaDonPhatDuoc({ commit, state }, data) {
        try {
            let response = await API.huyHoaDonPhatDuoc(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },
    async capNhatNgayTraPhieu({ commit, state }, data) {
        try {
            let response = await API.capNhatNgayTraPhieu(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async getTuFile({ commit, state }, data) {
        try {
            let response = await API.getTuFile(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },
    async getKyCuocHienHanh({ commit, state }, data) {
        try {
            let response = await API.getKyCuocHienHanh(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async clearData({ commit, state }, data) {
        commit("clearData", data);
    }
};
