import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhsachDonVi: []
}

const namespaced = true

export const qltnCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
