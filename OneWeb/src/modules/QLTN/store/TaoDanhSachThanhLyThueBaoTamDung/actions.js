import API from "../../api/TaoDanhSachThanhLyThueBaoTamDung";
import CommonAPI from "../../api/Commons";

export const actions = {
    async getDonVi({ commit, state }, data) {
        try {
            let response = await API.getDanhSachDonVi(this._vm.axios, data);
            console.log(response.data.data);
            commit("setListDonVi", response.data.data);
            return response;
        } catch (error) {
            commit("setListDonVi", []);
            return error.response.data;
        }
    },

    async getDichVuVT({ commit, state }, data) {
        try {
            let response = await CommonAPI.getDanhSachDichVuVienThong(
                this._vm.axios,
                data
            );
            if (Array.isArray(response.data.data)) {
                commit("setListDichVuVT", response.data.data);
            } else {
                commit("setListDichVuVT", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setListDichVuVT", []);
            return error.response.data;
        }
    },

    async getLoaiHinhTB({ commit, state }, data) {
        try {
            let response = await CommonAPI.getDanhSachLoaiHinhThueBao(
                this._vm.axios,
                data
            );
            if (Array.isArray(response.data.data)) {
                commit("setListLoaiHinhTB", response.data.data);
            } else {
                commit("setListLoaiHinhTB", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setListLoaiHinhTB", []);
            return error.response.data;
        }
    },

    async getTrungTamVT({ commit, state }, data) {
        try {
            let response = await API.getTrungTamVT(this._vm.axios, data);
            if (Array.isArray(response.data.data)) {
                commit("setTrungTamVT", response.data.data);
            } else {
                commit("setTrungTamVT", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setTrungTamVT", []);
            return error.response.data;
        }
    },

    async getDMKieuTamDung({ commit, state }, data) {
        try {
            let response = await API.getDMKieuTamDung(this._vm.axios, data);
            if (Array.isArray(response.data.data)) {
                commit("setDMKieuTamDung", response.data.data);
            } else {
                commit("setDMKieuTamDung", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setDMKieuTamDung", []);
            return error.response.data;
        }
    },

    async getDanhSachLoaiKhachHang({ commit, state }, data) {
        try {
            let response = await CommonAPI.getDanhSachLoaiKhachHang(
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

    async getDanhSachKHLon({ commit, state }, data) {
        try {
            let response = await CommonAPI.getDanhSachKHLon(
                this._vm.axios,
                data
            );
            if (Array.isArray(response.data.data)) {
                commit("setDanhSachKHLon", response.data.data);
            } else {
                commit("setDanhSachKHLon", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setDanhSachKHLon", []);
            return error.response.data;
        }
    },

    async getDanhSachThanhLyThueBao({ commit, state }, data) {
        try {
            let response = await API.getDanhSachThanhLyThueBao(
                this._vm.axios,
                data
            );
            if (Array.isArray(response.data.data)) {
                commit("setDanhSachThanhLyThueBao", response.data.data);
            } else {
                commit("setDanhSachThanhLyThueBao", response.data.data.data);
            }
            return response;
        } catch (error) {
            commit("setDanhSachThanhLyThueBao", []);
            return error;
        }
    },

    async taoPhieuThanhLy({ commit, state }, data) {
        try {
            let response = await API.taoPhieuThanhLy(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },

    async taoDanhSachThanhLyThueBao({ commit, state }, data) {
        try {
            let response = await API.taoDanhSachThanhLyThueBao(
                this._vm.axios,
                data
            );
            return response;
        } catch (error) {
            return error;
        }
    },

    async xoaThueBao({ commit, state }, data) {
        try {
            let response = await API.xoaThueBao(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },

    async xoaHopDong({ commit, state }, data) {
        try {
            let response = await API.xoaHopDong(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },

    async getListTDTuFile({ commit, state }, data) {
        try {
            let response = await API.getListTDTuFile(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async updateListTDTuFile({ commit, state }, data) {
        try {
            let response = await API.updateListTDTuFile(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    setDanhSachThanhLyThueBao({ commit, state }, data) {
        commit("setDanhSachThanhLyThueBao", data);
    },

    async clearData({ commit, state }, data) {
        commit("clearData", data);
    }
};
