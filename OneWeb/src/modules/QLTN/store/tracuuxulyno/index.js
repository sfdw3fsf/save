import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhsachXuLyNo: [],
}

const namespaced = true

export const tracuuxulyno = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
