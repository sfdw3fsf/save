import API from "../../api/ChuyenPhieuLacHuong";
export const actions = {
  async getDanhSachNVThuCuoc({ commit, state }, data) {
    try {
      let response = await API.getDanhSachNVThuCuoc(this._vm.axios, data);
      commit("setDanhSachNV", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachNV", []);
      return error.data
    }
  },
  async getDanhSachNVThuCuocDungHuong({ commit, state }, data) {
    try {
      let response = await API.getDanhSachNVThuCuocDungHuong(this._vm.axios, data);
      commit("setDanhSachNVDungHuong", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachNVDungHuong", []);
      return error.data
    }
  },
  async getDanhSachHoaDon({ commit, state }, data) {
    try {
      let response = await API.getDanhSachHoaDon(this._vm.axios, data);
      commit("setDanhSachHoaDon", response.data.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachHoaDon", []);
      return error.data
    }
  },
  async chuyenPhieu({ commit, state }, data) {
    try {
      let response = await API.chuyenPhieu(this._vm.axios, data);
      return response;
    } catch (error) {
      return error.data
    }
  },
  clearData({ commit, sate }) {
    commit("clearData");
  }
};
