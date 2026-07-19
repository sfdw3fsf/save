import API from "../../api/LoaiThietBi";

export const actions = {
  async getDSNhomTB({ commit, state }) {
    let response = await API.getDSNhomTB(this._vm.axios);
    try {
      commit("setDSNhomTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response;
  },
  async addDuLieuDSTB({ commit, state }, data) {
    let response = await API.addDuLieuDSTB(this._vm.axios, data);
    try {
      commit("setAddDuLieuDSTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response;
  },
  async getKeyNhomThietBi({ commit, state }, data) {
    let response = await API.getKeyNhomThietBi(this._vm.axios, data);
    try {
      commit("setGetKeyNhomThietBi", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response;
  },
  async updateDuLieuDSTB({ commit, state }, data) {
    let response = await API.updateDuLieuDSTB(this._vm.axios, data);
    try {
      commit("setUpdateDuLieuDSTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response;
  },
  async deleteDLNTB({ commit, state }, data) {
    let response = await API.deleteDLNTB(this._vm.axios, data.nhomTbiId);
    try {
      commit("setDeleteDLNTB", response.data.data || []);
    } catch (error) {
      throw error;
    }
    return response;
  }
};
