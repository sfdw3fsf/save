import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsThietLapThoiGian: [],
}

const namespaced = true

export const thietlapthamso = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
