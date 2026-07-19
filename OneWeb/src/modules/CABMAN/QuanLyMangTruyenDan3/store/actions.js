import api from "../api";

var apiManager;

export const actions = {
  createApiManager() {
    apiManager = api(this._vm.$requestContext);
  },
  async api_execute({ commit }, data) {
    try {
      let postData = {
        ...data.data,
        PROCEDURE_NAME: data.function
      };
      const response = await apiManager.api_execute(postData);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }
  },
  async api_get_ds_ttvt({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_ttvt(data);
      commit("set_ds_ttvt", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }
  },
  async setThongTinMap({ commit }, data) {
    commit("setThongTinMap", data);
  },
  async updateThongTinMap({ commit }, data) {
    commit("updateThongTinMap", data);
  },
  async setState({ commit }, { key, value }) {
    commit("setState", { key, value });
  },
  async clearState({ commit }) {
    commit("clearState");
  }
};
