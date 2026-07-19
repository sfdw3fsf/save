import API from "../../api/NhacNoThueBao";
import CommonAPI from "../../api/Commons";

export const actions = {
  async getDonVi({ commit, state }, data) {
    try {
      let response = await CommonAPI.getDanhSachDonVi(this._vm.axios, data);
      commit("setListDonVi", response.data.data);
      return response;
    } catch (error) {
      commit("setListDonVi", []);
      return error.response.data;
    }
  },

  async getDichVuVT({ commit, state }, data) {
    try {
      let response = await CommonAPI.getDanhSachDichVuVienThong(
        this._vm.axios,
        data
      );
      if (Array.isArray(response.data.data)) {
        commit("setListDichVuVT", response.data.data);
      } else {
        commit("setListDichVuVT", response.data.data.data);
      }
      return response;
    } catch (error) {
      commit("setListDichVuVT", []);
      return error.response.data;
    }
  },

  async getLanTaoDuLieu({ commit, state }, data) {
    try {
      let response = await API.getLanTaoDuLieu(this._vm.axios, data);
      if (Array.isArray(response.data.data)) {
        commit("setLanTaoDuLieu", response.data.data);
      } else {
        commit("setLanTaoDuLieu", response.data.data.data);
      }
      return response;
    } catch (error) {
      commit("setLanTaoDuLieu", []);
      return error.response.data;
    }
  },

  async getLanNhacNo({ commit, state }, data) {
    try {
      let response = await API.getLanNhacNo(this._vm.axios, data);
      console.log("resssss", response);
      if (Array.isArray(response.data.data)) {
        commit("setLanNhacNo", response.data.data);
      } else {
        commit("setLanNhacNo", response.data.data.data);
      }
      return response;
    } catch (error) {
      commit("setLanNhacNo", []);
      return error.response.data;
    }
  },
  async getListChuaNhacNo({ commit, state }, data) {
    try {
      let response = await API.getListChuaNhacNo(this._vm.axios, data);
      return response;
    } catch (error) {
      return error.response.data;
    }
  },
  async getListNhacNo({ commit, state }, data) {
    try {
      let response = await API.getListNhacNo(this._vm.axios, data);
      return response;
    } catch (error) {
      return error.response.data;
    }
  },

  async getListDaNhacNo({ commit, state }, data) {
    try {
      let response = await API.getListDaNhacNo(this._vm.axios, data);
      return response;
    } catch (error) {
      return error.response.data;
    }
  },

  async chuyenNhacNoAction({ commit, state }, data) {
    try {
      let response = await API.chuyenNhacNoAction(this._vm.axios, data);
      return response;
    } catch (error) {
      return error.response.data;
    }
  },

  async huyNhacNoAction({ commit, state }, data) {
    try {
      let response = await API.huyNhacNoAction(this._vm.axios, data);
      return response;
    } catch (error) {
      return error.response.data;
    }
  },

  async huyLanNhacAction({ commit, state }, data) {
    try {
      let response = await API.huyLanNhacAction(this._vm.axios, data);
      return response;
    } catch (error) {
      return error.response.data;
    }
  },
  async capNhatNhacNoAction({ commit, state }, data) {
    try {
      let response = await API.capNhatNhacNoAction(this._vm.axios, data);
      return response;
    } catch (error) {
      return error.response.data;
    }
  },

  async clearData({ commit, state }, data) {
    commit("clearData", data);
  }
};
