import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  listDeviceGroup: [],
  listDeviceType: [],
  listCondition: [],
  listDeviceRecallCommission: []
}

const namespaced = true

export const deviceRecallCommissionCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
