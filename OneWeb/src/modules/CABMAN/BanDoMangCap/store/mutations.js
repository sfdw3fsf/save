import Vue from "vue";
export const mutations = {
  set_ds_ttvt(state, data) {
    state.ds_ttvt = data;
  },
  set_ds_tokt(state, data) {
    state.ds_tokt = data;
  },
  set_ds_tram(state, data) {
    state.ds_tram = data;
  },
  set_ds_cap(state, data) {
    state.ds_cap = data;
  },
  set_tokt(state, data) {
    state.toql = data;
  },
  set_donvi(state, data) {
    state.donvi = data;
  },
  set_nhomlc(state, data) {
    state.phanloaikc_id = data;
  },
  set_ds_ketcuoi_cnc(state, data) {
    state.ds_ketcuoi_cnc = data;
  },
  remove_ds_ketcuoi_ctd_id(state, data) {
    const i = state.ds_ketcuoi_ctd.findIndex(x => x.FULL_ID === data);
    state.ds_ketcuoi_ctd.splice(i, 1);
  },
  set_ds_ketcuoi_ctd(state, data) {
    state.ds_ketcuoi_ctd = data;
  },

  set_gian_do_cap(state, data) {
    state.gian_do_cap = data;
  },

  clearState(state) {
    state.ds_ttvt = [];
    state.ds_tokt = [];
    state.ds_tram = [];
    state.ds_cap = [];
    state.ds_ketcuoi_cnc = [];
    state.ds_ketcuoi_ctd = [];
    state.gian_do_cap = [];
    state.capgoc = Object;
    state.toql = Object;
    state.donvi = Object;
    state.phanloaikc_id = null;
    state.vaction = "None";
    state.vnguon_id = null;
    state.vdich_id = null;
  },
  clearState_ds_cap(state) {
    state.ds_cap = [];
  },
  set_cap_goc(state, data) {
    state.capgoc = data;
  },
  set_TabPageIndex(state, data) {
    state.tabPageIndex = data;
  },
  setState(state, { key, value }) {
    Vue.set(state, key, value);
  },
  // map default
  setMap(state, data) {
    state.map.center.lat = parseFloat(data.lat);
    state.map.center.lng = parseFloat(data.lng);
    state.map.zoom = data.zoom;
    state.map.mapTypeId = data.mapTypeId;
    state.map.cluster_map_google = data.cluster_map_google;
  },
  updateMap(state, { loai, data }) {
    if (loai === "center") {
      state.map.center.lat = data.lat();
      state.map.center.lng = data.lng();
    } else if (loai === "zoom") {
      state.map.zoom = data;
    } else if (loai === "mapTypeId") {
      state.map.mapTypeId = data;
    }
  }
  //
};
