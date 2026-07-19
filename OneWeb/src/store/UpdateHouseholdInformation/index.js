// khai báo 1 store
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachHoDan: []
}

const namespaced = true

export const UpdateHouseholdInformation = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
