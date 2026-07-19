import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachDonVi: []
}

const namespaced = true

export const donViCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
