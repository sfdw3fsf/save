import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  purposes: {
    purposesNotAdded: [],
    purposesAdded: []
  }
}

const namespaced = true
export const purposesCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
