/* eslint-disable */

import TraCuuThueBaoChuaNhapCapApi from "../../api/TraCuuThueBaoChuaNhapCap";

var apiManager;

export const actions = {
  createApiManager() {
    apiManager = TraCuuThueBaoChuaNhapCapApi(this._vm.$bssApiCaller);
  },
  async layDsDonVi({ commit }) {
    let response = await apiManager.layDsDonVi();
    try {
      commit("setDsDonVi", response.data.data);
    } catch (err) {
      throw err;
    }
    return response;
  },
  async layDsThueBaoChuaNhapCap({ commit }, donvi_id) {
    let response = await apiManager.layDsThueBaoChuaNhapCap(donvi_id);
    try {
      commit("setDsThueBaoChuaNhapCap", response.data.data);
    } catch (err) {
      throw err;
    }
    return response;
  },
  async layLsTacDongCapThueBao({ commit }, data) {
    let response = await apiManager.layLsTacDongCapThueBao(data);
    try {
      commit("setLsTacDongCapThueBao", response.data.data);
    } catch (err) {
      throw err;
    }
    return response;
  },
  clearState({ commit }) {
    commit("clearState");
  }
};
