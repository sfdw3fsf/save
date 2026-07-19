import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachNhanVienTheoDonVi: []
}

const namespaced = true

export const nhanVienTheoDonViCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
