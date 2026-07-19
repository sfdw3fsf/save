import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsThuocTinh: []
}

export const updateDeviceType = {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
