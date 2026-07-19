import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhsachLoi: [],
  danhsachNhanVienTC:[],
}

const namespaced = true

export const quanlythutuin = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
