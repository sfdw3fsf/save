import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachLoaiHinhThueBao: []
}

const namespaced = true

export const lhtbCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
