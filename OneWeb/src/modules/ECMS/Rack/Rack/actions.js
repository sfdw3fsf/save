import RackApi from "../RackApi";

var apiManager;

export const actions = {
  createApiManager() {
    apiManager = RackApi(this._vm.$bssApiCaller);
  },
  clearState({ commit }) {
    commit("clearState");
  },
  async layDsRackAction({ commit }) {
    try {
      let response = await apiManager.layDsRack();
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data != null
      ) {
        commit("setDsRack", response.data.data);
      } else {
        commit("setDsRack", []);
      }
    } catch (err) {
      commit("setDsRack", []);
    }
  },
  commitDsRack({ commit }, data) {
    commit("setDsRack", data);
  },
  addDsRack({ commit }, data) {
    commit("addDsRack", data);
  },
  async capNhatAction({ commit }, data) {
    let response = await apiManager.capNhatApi(data);
    console.log(response);
    return response;
  },
  async themMoiAction({ commit }, data) {
    try {
      let response = await apiManager.themMoiApi(data);

      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data != null
      ) {
        commit("setDsTaiSan", response.data.data);
        return response.data.data;
      } else {
        commit("setDsTaiSan", []);
      }
    } catch (err) {
      commit("setDsTaiSan", []);
    }
  },

  async xoaAction({ commit }, data) {
    await apiManager
      .xoaApi(data)
      .then(response => {
        console.log(response);
      })
      .catch(error => {
        throw error.response.data.message_detail;
      });
  },
  async layDsTaiSanTheoMaTaiSan({ commit }, maTaiSan) {
    try {
      let response = await apiManager.layDsTaiSanTheoMaTaiSan(maTaiSan);

      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data != null
      ) {
        commit("setDsTaiSanTimKiem", response.data.data);
      } else {
        commit("setDsTaiSanTimKiem", []);
      }
    } catch (err) {
      commit("setDsTaiSanTimKiem", []);
    }
  }
};
