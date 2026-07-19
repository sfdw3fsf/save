
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  listTelecomService: [],
  listSubscriptionType: [],
  listRequestType: [],
  listDonViQL: []
}

const namespaced = true

export const restoreLiquidate = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
