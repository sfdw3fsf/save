import Vue from 'vue'
export const mutations = {
  set_ds_ttvt(state, data) {
    state.ds_ttvt = data
  },
  set_ds_tram(state, data) {
    state.ds_tram = data
  },
  set_ds_loairanh(state, data) {
    state.ds_loairanh = data
  },
  set_ds_tokt(state, data) {
    state.ds_tokt = data
  },
  set_ds_ranh_id(state, data) {
    state.ds_ranh_id = data
  },
  clearState(state) {
    state.ds_ttvt = []
    state.ds_tram = []
    state.ds_loairanh = []
    state.ds_tokt = []
    state.ttvt_id = null
    state.tramvt_id = null
    state.ds_ranh_id = []
    ;(state.tovt_id = null), (state.loairanh_id = null)
  },
  set_TabPageIndex(state, data) {
    state.tabPageIndex = data
  },
  setState(state, { key, value }) {
    Vue.set(state, key, value)
  }
}
