import API from "../../api/KhoaMayThueBaoNoCuoc";
export const actions = {
  async getDanhSachDonViQL({ commit, state }, data) {
    try {
      let response = await API.getDanhSachDonViQL(this._vm.axios, data);
      commit("setDanhSachDonVi", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachDonVi", []);
      // throw error;
      return error.data
    }
  },
  async getDanhSachQuyTrinhKhoaMay({ commit, state }, data) {
    try {
      let response = await API.getDanhSachQuyTrinhKhoaMay(this._vm.axios, data);
      commit("setDanhSachQuyTrinh", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachQuyTrinh", []);
      // throw error;
      return error.data
    }
  },
  async getDanhSachLoaiPhieu({ commit, state }, data) {
    try {
      let response = await API.getDanhSachLoaiPhieu(this._vm.axios, data);
      commit("setDanhSachLoaiPhieu", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachLoaiPhieu", []);
      // throw error;
      return error.data
    }
  },
  async khoaMayTheoFile({ commit, state }, data) {
    try {
      let response = await API.khoaMayTheoFile(this._vm.axios, data);
      commit("setDanhSachKhoaThueBao", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachKhoaThueBao", []);
      // throw error;
      return error.data
    }
  },
  async getDanhSachKhoaMay({ commit, state }, data) {
    try {
      let response = await API.getDanhSachKhoaMayKiemTraLapHD(this._vm.axios, data);
      commit("setDanhSachKhoaThueBao", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachKhoaThueBao", []);
      // throw error;
      return error.data
    }
  },
  async capNhatDanhSach({ commit, state }, data) {
    try {
      let response = await API.capNhatDanhSach(this._vm.axios, data);
      // commit("setDanhSachKhoaThueBao", response.data.data || []);
      return response;
    } catch (error) {
      // commit("setDanhSachKhoaThueBao", []);
      // throw error;
      return error.data
    }
  },
  async capNhatDanhSachVer3({ commit, state }, data) {
    try {
      let response = await API.capNhatDanhSachV3(this._vm.axios, data);
      // commit("setDanhSachKhoaThueBao", response.data.data || []);
      return response;
    } catch (error) {
      // commit("setDanhSachKhoaThueBao", []);
      // throw error;
      return error.data
    }
  },
  clearData({ commit, sate }) {
    commit("clearData");
  }
};
