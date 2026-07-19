import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  portParameters: {
    subcriberTypes: [],
    brasList: [],
    services: [],
    dsLam: [],
    speedList: []
  }
}

const namespaced = true

export const portParametersCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
