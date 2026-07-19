import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  listTelecomService: [],
  listSubscriptionType: [],
  listRequestType: []
}

const namespaced = true

export const restoreLiquidateCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
