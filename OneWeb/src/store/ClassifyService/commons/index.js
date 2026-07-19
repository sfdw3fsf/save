import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachPhanLoaiDichVu: []
}

const namespaced = true

export const classifyServiceCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
