import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  handoverIncident: {
    dsDichVuVt: [],
    statuses: []
  }
}

const namespaced = true

export const handoverIncidentCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
