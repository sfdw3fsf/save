import API from "../../api/KhoaMoMayTheoSoMayACCMoiXacMinh";
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
    async getDichVuVienThong({ commit, state }, data) {
        try {
            let response = await API.getDichVuVienThong(this._vm.axios, data);
            console.log({ dichvuvt: response });
            commit("setListDVVT", response.data.data);
            return response;
        } catch (error) {
            commit("setListDVVT", []);
            return error.response.data;
        }
    },

    async getDanhSachLoaiHinhThueBao({ commit, state }, data) {
        try {
            let response = await CommonAPI.getDanhSachLoaiHinhThueBao(
                this._vm.axios,
                data
            );
            commit("setListLoaiHinhTB", response.data.data);
            return response;
        } catch (error) {
            commit("setListLoaiHinhTB", []);

            return error.response.data;
        }
    },

    async getDanhSachLyDoKhoa({ commit, state }, data) {
        try {
            let response = await CommonAPI.getDanhSachLyDoKhoa(
                this._vm.axios,
                data
            );
            commit("setListLyDoKhoa", response.data.data);
            return response;
        } catch (error) {
            commit("setListLyDoKhoa", []);

            return error.response.data;
        }
    },

    async getQuyTrinh({ commit, state }, data) {
        try {
            let response = await API.getQuyTrinh(this._vm.axios, data);
            commit("setListQuyTrinh", response.data.data);
            return response;
        } catch (error) {
            commit("setListQuyTrinh", []);
            return error.response.data;
        }
    },

    async getThongTinTheoMaTB({ commit, state }, data) {
        try {
            let response = await API.getThongTinTheoMaTB(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },
    async getDiemTinNhiem({ commit, state }, data) {
        try {
            let response = await API.getDiemTinNhiem(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },
    async getDanhBaTheoMaThueBao({ commit, state }, data) {
        try {
            let response = await CommonAPI.getDanhBaTheoMaThueBao(
                this._vm.axios,
                data
            );
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async checkTongNoTB({ commit, state }, data) {
        try {
            let response = await API.checkTongNoTB(this._vm.axios, data);
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async kiemTraTB({ commit, state }, data) {
        try {
            let response = await API.kiemTraTB(this._vm.axios, data);
            return response;
        } catch (error) {
            console.log(error);
            return error;
        }
    },

    async capNhatDanhSach({ commit, state }, data) {
        try {
            let response = await API.capNhatDanhSach(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },

    async getKyCuocHienHanh({ commit, state }, data) {
        try {
            let response = await API.getKyCuocHienHanh(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },

    async checkThueBaoTrongDSDacBiet({ commit, state }, data) {
        try {
            let response = await API.checkThueBaoTrongDSDacBiet(
                this._vm.axios,
                data
            );
            return response;
        } catch (error) {
            return error.response.data;
        }
    },

    async checkTBMyTVChinhPhu({ commit, state }, data) {
        try {
            let response = await API.checkTBMyTVChinhPhu(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },
    async checkKhieuNai({ commit, state }, data) {
        try {
            let response = await API.checkKhieuNai(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },
    async checkTBLapHD({ commit, state }, data) {
        try {
            let response = await API.checkTBLapHD(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },
    async getDSMoMayTheoMaTB({ commit, state }, data) {
        try {
            let response = await API.getDSMoMayTheoMaTB(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },
    async getDiemTinNhiemTheoNhanVien({ commit, state }, data) {
        try {
            let response = await API.getDiemTinNhiemTheoNhanVien(this._vm.axios, data);
            return response;
        } catch (error) {
            return error;
        }
    },

    async clearData({ commit, state }, data) {
        commit("clearData", data);
    }
};
