import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    loaiDSThueBaoKhongKhaoSat: []
}

const namespaced = true

export const cskhCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
