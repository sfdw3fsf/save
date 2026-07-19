import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  listSubcribersNotReceiveDamage: {
    dsChuKyNo: [],
    dsThamSoMd: [],
    subcribers: [],
    gridSubcribers: [],
    subcriberSearched: {}
  }
}

const namespaced = true

export const listSubcribersNotReceiveDamageCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
