import Vue from 'vue'
export const mutations = {
  set_ds_ttvt (state, data) {
    state.ds_ttvt = data
  },
  clearState (state) {
    state.map = {
      center: {
        lat: 21.037613239281285,
        lng: 105.83846254936175
      },
      zoom: 14,
      mapTypeId: 'roadmap'
    }
    state.data = {
      dsTuyenCap: [],
      dsTTVT: [],
      dsTOVT_2: [],
      dsTOVT: [],
      dsTRAMTB: [],
      dsTRAMTB_2: [],
      dsGIANDO: [],
      dsPHANCAP: [],
      dsCAPGOC: []
    }
  },
  setState (state, { key, value }) {
    Vue.set(state.data, key, value)
  },
  setThongTinMap (state, data) {
    state.map.center.lat = parseFloat(data.lat)
    state.map.center.lng = parseFloat(data.lng)
    state.map.zoom = data.zoom
    state.map.mapTypeId = data.mapTypeId
  },
  updateThongTinMap (state, { loai, data }) {
    if (loai === 'center') {
      state.map.center.lat = data.lat()
      state.map.center.lng = data.lng()
    } else if (loai === 'zoom') {
      state.map.zoom = data
    } else if (loai === 'mapTypeId') {
      state.map.mapTypeId = data
    }
  }
}
