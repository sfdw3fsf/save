import API from "../../api/CapNhatSerial";

export const actions = {
  async getDSSerialCN({ commit, state }, data) {
    let response = await API.getDSSerialCN(this._vm.axios, data);
    try {
      commit("setDSSerialCN", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async capNhatSerialTraCuu({ commit, state }, data) {
    let response = await API.capNhatSerialTraCuu(this._vm.axios, data);
    try {
      commit("setCapNhatSerialTC", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getLogCapNhatSerial({ commit, state }, data) {
    let response = await API.getLogCapNhatSerial(this._vm.axios, data);
    try {
      commit("setLogCapNhatSerial", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getKeySessionIDVT({ commit, state }, data) {
    let response = await API.getKeySessionIDVT(this._vm.axios, data);
    try {
      commit("setKeySessionIDVT", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
  async getKetQuaCN({ commit, state }, data) {
    let response = await API.getKetQuaCN(this._vm.axios, data.sessionId);
    try {
      commit("setKetQuaCN", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response.data;
  },
};
