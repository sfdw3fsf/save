import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachLyDoTra: []
}

const namespaced = true

export const lyDoTraCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
