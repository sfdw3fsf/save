// khai báo 1 store
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachVaiTro: [],
  isLoading: false,
  dataItem: {}
}

const namespaced = true

export const contractorRole = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
