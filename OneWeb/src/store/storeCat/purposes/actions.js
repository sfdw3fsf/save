import api from '../../../modules/admin/category/Store/api'

export const actions = {
  async loadPurposeNotAdded ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getPurposesNotAdded(this._vm.axios, data)
    commit('setPurposeNotAdded', res.data.data)
  },
  async loadPurposeAdded ({ state, commit, rootState, rootGetters }, data) {
    const res = await api.getPurposesAdded(this._vm.axios, data)
    commit('setPurposeAdded', res.data.data)
  },
  removePurposesFromPurposesAdded ({ state, commit, rootState, rootGetters }, data) {
    commit('removePurposesFromPurposesAdded', data)
  },
  addPurposesToPurposesAdded ({ state, commit, rootState, rootGetters }, data) {
    commit('addPurposesToPurposesAdded', data)
  },
  resetPurposes ({ state, commit, rootState, rootGetters }, data) {
    commit('resetPurposes', data)
  }
}
