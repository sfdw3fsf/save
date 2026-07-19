import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachLoaiHD: []
}

const namespaced = true

export const loaiHDCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
