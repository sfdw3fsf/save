/* eslint-disable */

import api from "../../DanhSachCotCap/api";

var apiManager;

export const actions = {
  createApiManager() {
    apiManager = api(this._vm.$requestContext);
  },
  async layDsTTVT({ commit }) {
    
    try {
      let response = await apiManager.layDsTTVT();
      commit("setDsTTVT", response.data);
      return response;
    } catch (err) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }    
  },
  async layDsTOQL({ commit }, donvi_id) {
    let response = await apiManager.layDsTOQL(donvi_id);
    try {
      commit("setDsTOQL", response.data);
    } catch (err) {
      throw err;
    }
    return response;
  },

  async layDsTuyenCot({ commit }, donvi_id) {
    let response = await apiManager.layDsTuyenCot(donvi_id);
    try {
      commit("setDsTuyenCot", response.data);
    } catch (err) {
      throw err;
    }
    return response;
  },

  async layDsCot({ commit }, data) {
    console.log("layDsCot", data);
    let response = await apiManager.layDsCot(data);
    try {
      commit("setDsCot", response.data);
    } catch (err) {
      throw err;
    }
    return response;
  },

  async tracuuCot({ commit }, query) {
    console.log("tracuuCot", query);
    let response = await apiManager.tracuuCot(query);
    try {
      commit("setDsCot", response.data);
    } catch (err) {
      throw err;
    }
    return response;
  },

  clearStateDsTOQL({ commit }) {
    commit("clearStateDsTOQL");
  },

  clearStateDsTC({ commit }) {
    commit("clearStateDsTC");
  },

  clearStateDsC({ commit }) {
    commit("clearStateDsC");
  },

  clearState({ commit }) {
    commit("clearState");
  }
};
