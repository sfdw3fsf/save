import api from "../../BanDoRanh/api";
var apiManager;
export const actions = {
  createApiManager() {
    apiManager = api(this._vm.$requestContext);
  },
  async api_get_ds_ttvt({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_ttvt(data);
      commit("set_ds_ttvt", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },
  async api_get_ds_ranhtram({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_ranhtram(data);
      commit("set_ds_tram", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },
  async set_TabPageIndex({ commit }, TabPageIndex) {
    commit("set_TabPageIndex", TabPageIndex);
  },
  clearState({ commit }) {
    commit("clearState");
  },
  async setState({ commit }, { key, value }) {
    commit("setState", { key, value });
  }
};
