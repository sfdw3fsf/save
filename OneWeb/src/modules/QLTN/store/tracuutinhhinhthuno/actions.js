import API from "../../api/TraCuuTinhHinhThuNo";

export const actions = {
  // async getDanhSachNVThuCuoc({ commit, state }, data) {
  //   let response = await API.getDanhSachNVThuCuoc(this._vm.axios);
  //   try {
  //     commit("setDanhSachNVThuCuoc", response.data.data.data);
  //   } catch (error) {
  //     throw error;
  //   }
  //   return response;
  // },
  async getTyLeThuNVThuCuoc({ commit, state }, data) {
    let response = await API.getTyLeThuNVThuCuoc(this._vm.axios, data);
    try {
      commit("setTyLeThuNVThuCuoc", response.data.data);
    } catch (error) {
      throw error;
    }
    return response;
  },
  async getDanhSachTinhCuocTN({ commit, state }, data) {
    let response = await API.getDanhSachTinhCuocTN(this._vm.axios, data);
    try {
      commit("setDanhSachTinhCuocTN", response.data.data);
    } catch (error) {
      throw error;
    }
    return response;
  },
  getObjSearch ({commit, state}, data) {
    commit('setObjSearch', data)
  },
  clearData ({commit, sate}, data) {
    commit('clearData', data)
  }
};
