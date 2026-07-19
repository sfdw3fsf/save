import api from "@/modules/admin/category/State/api";
import {
  STATUS_EXIST_MA_QUAN_HUYEN,
  STATUS_EXIST_TEN_QUAN_HUYEN
} from "@/modules/admin/category/State/constants.js";

export const actions = {
  async initializeCityList({ commit, state }) {
    try {
      const res = await api.getCityList(this._vm.axios);
      //console.log('initializeCityList', this._vm.$auth.getPhanVungID())
      commit("setCityList", res.data.data.filter(x=>x.TINH_ID==this._vm.$auth.getPhanVungID()));
    } catch (error) {
      throw error;
    }
  },

  async fetchDistrictList({ commit, state }) {
    try {
      commit("setIsLoading", true);
      const res = await api.getDistrictList(this._vm.axios);
      commit("setDistrictList", res.data.data.filter(x=>x.TINH_ID==this._vm.$auth.getPhanVungID()));
      commit("setIsLoading", false);
    } catch (error) {
      throw error;
    }
  },

  async checkState({ commit, state }, variables) {
    try {
      const res = await api.checkStateExist(this._vm.axios, variables);
      commit("setStatusCheckExistState", res.data.data);
    } catch (error) {
      throw error;
    }
  },

  async createState({ commit, state }, variables) {
    const params = {
      danhsach: [variables]
    };
    try {
      await api.createState(this._vm.axios, params);
    } catch (error) {
      console.log("loix:", error.data.message_detail);
      commit("setErrorExist", error.data.message_detail);
      throw error;
    }
  },

  async updateState({ commit, state }, variables) {
    console.log("api");
    const params = {
      danhsach: [variables]
    };
    try {
      await api.updateState(this._vm.axios, params);
    } catch (error) {
      console.log("loix:", error.data.message_detail);
      commit("setErrorExist", error.data.message_detail);
      throw error;
    }
  },

  async deleteState({ commit, state }, variables) {
    try {
      await api.deleteState(this._vm.axios, variables);
    } catch (error) {
      throw error;
    }
  }
};
