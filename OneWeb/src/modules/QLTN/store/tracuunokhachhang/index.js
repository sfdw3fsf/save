import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhsachThongTinNo: [],
  objSearch: {}
}

const namespaced = true

export const tracuunokhachhang = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
