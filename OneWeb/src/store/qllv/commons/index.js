import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachLinhVuc: []
}

const namespaced = true

export const qllvCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
