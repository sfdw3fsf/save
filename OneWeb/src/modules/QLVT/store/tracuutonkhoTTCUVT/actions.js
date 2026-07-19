import API from "../../api/TraCuuTonKhoTTCUVT";

export const actions = {
  async getTraCuuVatTu({ commit, state }, data) {
    let response = await API.getTraCuuVatTu(this._vm.axios, data);
    try {
      commit("setTraCuuVatTu", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getDanhSachKho({ commit, state }) {
    let response = await API.getDanhSachKho(this._vm.axios);
    try {
      commit("setDanhSachKho", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  }
};
