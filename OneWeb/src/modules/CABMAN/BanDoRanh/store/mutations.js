import Vue from "vue";
export const mutations = {
  set_ds_ttvt(state, data) {
    state.ds_ttvt = data;
  },
  set_ds_tram(state, data) {
    state.ds_tram = data;
  },
  clearState(state) {
    state.ds_ttvt = [];
    state.ds_tram = [];
    state.ttvt_id = null;
    state.tramvt_id = null;
  },
  set_TabPageIndex(state, data) {
    state.tabPageIndex = data;
  },
  setState(state, { key, value }) {
    Vue.set(state, key, value);
  }
};
