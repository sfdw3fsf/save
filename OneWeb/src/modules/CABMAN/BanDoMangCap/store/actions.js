import api from "../../BanDoMangCap/api";

var apiManager;

export const actions = {
  createApiManager() {
    apiManager = api(this._vm.$requestContext);
  },
  async api_get_ds_ttvt({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_ttvt(data);
      console.log("api_get_ds_ttvt", response);
      commit("set_ds_ttvt", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },
  async api_get_ds_tokt({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_tokt(data);
      commit("set_ds_tokt", response.data);
      return response;
    } catch (error) {
      console.log("api_get_ds_tokt", data, error);
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },
  async api_get_ds_tram({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_tram(data);
      commit("set_ds_tram", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },
  async api_get_ds_cap({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_cap(data);
      commit("set_ds_cap", response.data);
      commit("set_nhomlc", data.nhomlc_id);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },
  async api_get_ds_cap_cache({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_cap_cache(data);
      commit("set_ds_cap", response.data);
      commit("set_nhomlc", data.nhomlc_id);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },
  async api_get_ds_ketcuoi_chua_nhapcap({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_ketcuoi_chua_nhapcap(data);
      commit("set_ds_ketcuoi_cnc", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },

  async api_get_gian_do_cap({ commit }, data) {
    try {
      const response = await apiManager.api_get_gian_do_cap(data);
      commit("set_gian_do_cap", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },
  async api_get_gian_do_cap_cache({ commit }, data) {
    try {
      const response = await apiManager.api_get_gian_do_cap_cache(data);
      commit("set_gian_do_cap", response.data);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },
  async api_set_toado_doituong({ commit }, data) {
    try {
      const response = await apiManager.api_set_toado_doituong(data);
      console.log("api_set_toado_doituong", response);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },

  async api_set_toado_cap({ commit }, data) {
    try {
      const response = await apiManager.api_set_toado_cap(data);
      console.log("api_set_toado_cap", response);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },

  async api_set_xoa_cap({ commit }, data) {
    try {
      const response = await apiManager.api_set_xoa_cap(data);
      console.log("api_set_xoa_cap", response);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },
  async api_set_xoa_cap_cache({ commit }, data) {
    try {
      const response = await apiManager.api_set_xoa_cap_cache(data);
      console.log("api_set_xoa_cap_cache", response);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },
  async api_set_xoa_cap_midspan({ commit }, data) {
    try {
      const response = await apiManager.api_set_xoa_cap_midspan(data);
      console.log("api_set_xoa_cap_midspan", response);
      return response;
    } catch (error) {
      console.log("api_set_xoa_cap_midspan", error);
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },

  async api_set_xoa_ketcuoi({ commit }, data) {
    try {
      const response = await apiManager.api_set_xoa_ketcuoi(data);
      console.log("api_set_xoa_ketcuoi", response);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },

  async api_set_daochieu_cap({ commit }, data) {
    try {
      const response = await apiManager.api_set_daochieu_cap(data);
      console.log("api_set_daochieu_cap", response);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message);
      }
      throw error;
    }
  },
  async api_set_chen_ketcuoi({ commit }, data) {
    try {
      const response = await apiManager.api_set_chen_ketcuoi(data);
      console.log("api_set_chen_ketcuoi", response);
      return response;
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail);
      }
      throw error;
    }
  },

  async set_ds_ketcuoi_ctd({ commit }, data) {
    commit("set_ds_ketcuoi_ctd", data);
  },
  async remove_ds_ketcuoi_ctd_id({ commit }, data) {
    commit("remove_ds_ketcuoi_ctd_id", data);
  },
  async set_cap_goc({ commit }, cap_goc) {
    commit("set_cap_goc", cap_goc);
  },

  async set_tokt({ commit }, data) {
    console.log("set_tokt", data);
    commit("set_tokt", data);
  },

  async set_donvi({ commit }, data) {
    commit("set_donvi", data);
  },

  async set_nhomlc({ commit }, data) {
    commit("set_nhomlc", data);
  },

  async set_TabPageIndex({ commit }, TabPageIndex) {
    commit("set_TabPageIndex", TabPageIndex);
  },
  clearState({ commit }) {
    commit("clearState");
  },
  clearState_ds_cap({ commit }) {
    commit("clearState_ds_cap");
  },
  async setState({ commit }, { key, value }) {
    console.log("setState", key, value);
    commit("setState", { key, value });
  },
  // map default
  async setMap({ commit }, data) {
    commit("setMap", data);
  },
  async updateMap({ commit }, data) {
    commit("updateMap", data);
  }
  // map default
};
