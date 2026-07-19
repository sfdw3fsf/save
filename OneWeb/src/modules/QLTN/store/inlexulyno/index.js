import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhsachKyHoaDon: [],
  danhsachXuLyNo:[],
}

const namespaced = true

export const inlexulyno = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
