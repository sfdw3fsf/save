export const actions = {
  setChuKyNo: function ({ state, commit, rootState, rootGetters }, data) {
    commit('setChuKyNo', data)
  },
  setDsThamSoMd: function ({ state, commit, rootState, rootGetters }, data) {
    commit('setDsThamSoMd', data)
  },
  setInforSubcribers: function ({ state, commit, rootState, rootGetters }, data) {
    commit('setInforSubcribers', data)
  },
  setGridSubcribers: function ({ state, commit, rootState, rootGetters }, data) {
    commit('setGridSubcribers', data)
  },
  setSubcriberSearched: function ({ state, commit, rootState, rootGetters }, data) {
    commit('setSubcriberSearched', data)
  }
}
