// khai báo 1 store
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachSoNe: [],
  itemNe: { },
  selectNe: { },
  donViID: null,
  isLoading: false
}

const namespaced = true

export const changeNEParameter = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
