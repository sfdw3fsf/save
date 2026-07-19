import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsDonVi: [],
  dsChuongTrinh: [],
  dsDaGan: [],
  dsChuaGan: []
}

const namespaced = true

export const giaonvcskh = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
