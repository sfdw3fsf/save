import API from "../../api/InLeXuLyNo";
export const actions = {
  async getDanhSachKyHoaDon({ commit, state }, data) {
    try {
      let response = await API.getDanhSachKyHoaDon(this._vm.axios, data);
      commit("setDanhSachKyHoaDon", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachKyHoaDon", []);
      // throw error;
      return error.data
    }
  },
  async getDanhSachXuLyNo({ commit, state }, data) {
    try {
      let response = await API.getDanhSachXuLyNo(this._vm.axios, data);
      commit("setDanhSachXuLyNo", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachXuLyNo", []);
      // throw error;
      return error.data
    }
  },
  async getDanhSachXuLyNoTheoFile({ commit, state }, data) {
    try {
      let response = await API.getDanhSachXuLyNoTheoFile(this._vm.axios, data);
      commit("setDanhSachXuLyNo", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachXuLyNo", []);
      // throw error;
      return error.data
    }
  },
  clearData({ commit, sate }) {
    commit("clearData");
  },
  async kiemtraMaXLN({ commit, state }, data) {
    try {
      let response = await API.kiemtraMaXLN(this._vm.axios, data);
      return response;
    } catch (error) {
      // throw error;
      return error.data
    }
  },
};
