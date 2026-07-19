import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachTocDoKenh: []
}

const namespaced = true

export const tocDoKenhCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
