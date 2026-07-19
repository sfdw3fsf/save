import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsQuanHuyen: [],
  dsDonVi: [],
  dsNhanVien: [],
  dsChuongTrinhCSKH: [],
  dsKHL: [],
  dsDVKHL: [],
  totalElements:0,
  dsKHLTotalElements:0
}

const namespaced = true

export const gannvchamsockhl = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
