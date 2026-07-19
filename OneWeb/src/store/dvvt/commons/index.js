import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachDichVuVienThong: []
}

const namespaced = true

export const dvvtCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
