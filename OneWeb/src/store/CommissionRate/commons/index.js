import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  DanhSachTyLeThuCuoc: []
}

const namespaced = true

export const commissionRateCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
