/* eslint-disable */

import api from "../../DauNoiThueBao/api";

var apiManager;

export const actions = {
  createApiManager() {
    apiManager = api(this._vm.$requestContext);
  },
  async api_getThongTinCap({ commit }, data) {
    try {
      const response = await apiManager.api_getThongTinCap(data);
      commit("setThongTinCap", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }
  },
  async api_getDanDoCap({ commit }, data) {
    try {
      const response = await apiManager.api_getDanDoCap(data);
      commit("setDanDoCap", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }
  },
  async api_getDanDoCapNew({ commit }, data) {
    try {
      const response = await apiManager.api_getDanDoCap(data);
      commit("setDanDoCapNew", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }
  },
  async api_saveGhiChuCap({ commit }, data) {
    try {
      const response = await apiManager.api_saveGhiChuCap(data);
      commit("setGhiChuCap", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }
  },
  async api_getDsThueBaoTheoMaTB({ commit }, data) {
    try {
      const response = await apiManager.api_getDsThueBaoTheoMaTB(data);
      commit("setDsThueBaoTheoMaTB", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }
  },

  async api_getKetCuoiTheoKyHieu({ commit }, data) {
    try {
      const response = await apiManager.api_getKetCuoiTheoKyHieu(data);
      commit("setKetCuoiNew", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }
  },

  async api_getPortKetCuoiRoi({ commit }, data) {
    try {
      const response = await apiManager.api_getPortKetCuoiRoi(data);
      commit("setPortKetCuoiRoi", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }
  },

  async api_getCapGocTheoKetCuoiID({ commit }, data) {
    try {
      const response = await apiManager.api_getCapGocTheoKetCuoiID(data);
      commit("setCapGoc", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }
  },

  async api_saveNhapCapThueBao({ commit }, data) {
    try {
      const response = await apiManager.api_saveNhapCapThueBao(data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        let e = new Error();
        e = { ...e, BSS: error.response.data };
        throw e;
      }
      throw error;
    }
  },

  async api_saveGiaiPhongCapThueBao({ commit }, data) {
    try {
      const response = await apiManager.api_saveGiaiPhongCapThueBao(data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        let e = new Error();
        e = { ...e, BSS: error.response.data };
        throw e;
      }
      throw error;
    }
  },

  setKetCuoiNew({ commit }, data) {
    commit("setKetCuoiNew", data);
  },

  setPortKetCuoiRoi({ commit }, data) {
    commit("setPortKetCuoiRoi", data);
  },

  clearState({ commit }) {
    commit("clearState");
  },
  clearStateDanDoCapNew({ commit }) {
    commit("clearStateDanDoCapNew");
  }
};
