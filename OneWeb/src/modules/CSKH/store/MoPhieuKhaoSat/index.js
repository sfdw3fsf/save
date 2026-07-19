import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsThueBaoMoPhieu: [],
  totalPage: 0,
  pageSize: 10,
  totaItems: 0,
  objSearch: {}
}

const namespaced = true

export const mophieukhaosat = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
