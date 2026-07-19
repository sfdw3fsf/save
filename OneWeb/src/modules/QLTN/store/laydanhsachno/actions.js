import API from "../../api/LayDanhSachNo";
export const actions = {
  async getDanhSachThueBao({ commit, state }, data) {
    try {
      let response = await API.getDanhSachThueBao(this._vm.axios, data);
      commit("setDanhSachThueBao", response.data.data || []);
      return response;
    } catch (error) {
      commit("setDanhSachThueBao", []);
      // throw error;
      return error.data
    }
  },
  clearData({ commit, sate }) {
    commit("clearData");
  }
};
