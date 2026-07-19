import API from "../../api/XacNhanCongNoTheoDanhSach";
import CommonAPI from "../../api/Commons";

export const actions = {
    async getDonVi({ commit, state }, data) {
        try {
            let response = await API.getDanhSachDonVi(this._vm.axios, data);
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

    async getTuyenThu({ commit, state }, data) {
        try {
            let response = await API.getTuyenThu(this._vm.axios, data);
            commit("setTuyenThu", response.data.data);
            return response;
        } catch (error) {
            commit("setTuyenThu", []);
            return error.response.data;
        }
    },

    async getLyDo({ commit, state }, data) {
        try {
            let response = await API.getLyDo(this._vm.axios, data);
            commit("setLyDo", response.data.data);
            return response;
        } catch (error) {
            commit("setLyDo", []);
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

    async getNguyenNhan({ commit, state }, data) {
        try {
            let response = await API.getNguyenNhan(this._vm.axios, data);
            commit("setNguyenNhan", response.data.data);
            return response;
        } catch (error) {
            commit("setNguyenNhan", []);
            return error.response.data;
        }
    },

    async getDanhSachLoaiKH({ commit, state }, data) {
        try {
            let response = await API.getDanhSachLoaiKhachHang(
                this._vm.axios,
                data
            );
            if (Array.isArray(response.data.data)) {
                commit("setDanhSachLoaiKH", response.data.data);
            } else {
                commit("setDanhSachLoaiKH", response.data.data.data);
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
                commit("setListCongNo", response.data.data);
            } else {
                commit("setListCongNo", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setListCongNo", []);
            return error;
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

    async capNhatCongNoTheoFile({ commit, state }, data) {
        try {
            let response = await API.capNhatCongNoTheoFile(
                this._vm.axios,
                data
            );
            return response;
        } catch (error) {
            return error;
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

    async kiemTraFile({ commit, state }, data) {
        try {
            let response = await API.kiemTraFile(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },
    async taiBieuMau({ commit, state }, data) {
        try {
            let response = await API.taiBieuMau(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },

    async clearData({ commit, state }, data) {
        commit("clearData", data);
    }
};
