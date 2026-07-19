import API from "../../api/InHoaDonDienTuAPI";
import CommonAPI from "../../api/Commons";

export const actions = {
    async traCuuHoaDon({ commit, state }, data) {
        try {
            let response = await API.traCuuHoaDon(this._vm.axios, data);
            commit("setListDonVi", response.data.data);
            return response;
        } catch (error) {
            commit("setListDonVi", []);
            return error.response.data;
        }
    },

    async getHTTT({ commit, state }, data) {
        try {
            let response = await API.getDanhSachHinhThucThanhToan(
                this._vm.axios,
                data
            );
            commit("setListHTTT", response.data.data);
            return response;
        } catch (error) {
            commit("setListHTTT", []);

            return error.response.data;
        }
    },

    async docFileMaTT({ commit, state }, data) {
        try {
            let response = await API.docFileMaTT(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },

    async kiemTraFile({ commit, state }, data) {
        try {
            let response = await API.kiemTraFile(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async getHDDTTheoFile({ commit, state }, data) {
        try {
            let response = await API.getHDDTTheoFile(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },

    async getPhanLoaiKH({ commit, state }, data) {
        try {
            let response = await API.getNhomKh(this._vm.axios, data);
            commit("setListNhomKH", response.data.data);

            return response;
        } catch (error) {
            commit("setListNhomKH", []);
            return error.response.data;
        }
    },

    async getNVQuanLy({ commit, state }, data) {
        try {
            let response = await API.getNVQuanLy(this._vm.axios, data);
            commit("setListNVQuanLy", response.data.data);

            return response;
        } catch (error) {
            commit("setListNVQuanLy", []);
            return error.response.data;
        }
    },

    async getNVThuCuoc({ commit, state }, data) {
        try {
            let response = await API.getNVThuCuoc(this._vm.axios, data);
            commit("setListNVThuCuoc", response.data.data);
            return response;
        } catch (error) {
            commit("setListNVThuCuoc", []);
            return error.response.data;
        }
    },

    async getDanhSachLoaiKH({ commit, state }, data) {
        try {
            let response = await API.getDanhSachLoaiKH(this._vm.axios, data);
            if (Array.isArray(response.data.data)) {
                commit("setDanhSachLoaiKH", response.data.data);
            } else {
                commit("setDanhSachLoaiKH", []);
            }
            return response;
        } catch (error) {
            commit("setDanhSachLoaiKH", []);
            return error.response.data;
        }
    },

    async getNhomCongNo({ commit, state }, data) {
        try {
            let response = await API.getNhomCongNo(this._vm.axios, data);
            if (Array.isArray(response.data.data)) {
                commit("setNhomCongNo", response.data.data);
            } else {
                commit("setNhomCongNo", []);
            }
            return response;
        } catch (error) {
            commit("setNhomCongNo", []);
            return error.response.data;
        }
    },

    async getKyHoaDon({ commit, state }, data) {
        try {
            let response = await API.getKyHoaDon(this._vm.axios, data);
            if (Array.isArray(response.data.data)) {
                commit("setKyHoaDon", response.data.data);
            } else {
                commit("setKyHoaDon", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setKyHoaDon", []);
            return error.response.data;
        }
    },

    async getListCongNo({ commit, state }, data) {
        try {
            let response = await API.getListCongNo(this._vm.axios, data);
            if (Array.isArray(response.data.data)) {
                // commit("setListCongNo", response.data.data);
            } else {
                // commit("setListCongNo", response.data.data.data);
            }
            return response;
        } catch (error) {
            //   commit("setListCongNo", []);
            return error.response.data;
        }
    },

    async capNhatCongNo({ commit, state }, data) {
        try {
            let response = await API.capNhatCongNo(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async huyCongNo({ commit, state }, data) {
        try {
            let response = await API.huyCongNo(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async clearData({ commit, state }, data) {
        commit("clearData", data);
    }
};
