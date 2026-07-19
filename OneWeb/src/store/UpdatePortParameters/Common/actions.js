export const actions = {
  setSubcriberTypes ({ state, commit, rootState, rootGetters }, data) {
    commit('setSubcriberTypes', data)
  },
  setBrasList ({ state, commit, rootState, rootGetters }, data) {
    commit('setBrasList', data)
  },
  setServices ({ state, commit, rootState, rootGetters }, data) {
    commit('setServices', data)
  },
  setDsLam ({ state, commit, rootState, rootGetters }, data) {
    commit('setDsLam', data)
  },
  setSpeedList ({ state, commit, rootState, rootGetters }, data) {
    commit('setSpeedList', data)
  }
}
