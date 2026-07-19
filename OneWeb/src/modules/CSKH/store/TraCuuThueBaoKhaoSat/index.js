import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  DsCauHoi: [],
  DsThueBaoKS: [],
  DsLyDoKTL: []
}

const namespaced = true

export const tracuuthuebaokhaosat = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
