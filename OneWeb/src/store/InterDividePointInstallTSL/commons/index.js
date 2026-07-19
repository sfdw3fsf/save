import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachThueBao: []
}

const namespaced = true

export const interDividePointInstallTSLCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
