// khai báo 1 store
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachKhuyenMai: {},
  dataItem: { },
  selectNe: { },
  donViID: null,
  isLoading: false,
  totalRow: 0
}

const namespaced = true

export const complaintTopic = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
