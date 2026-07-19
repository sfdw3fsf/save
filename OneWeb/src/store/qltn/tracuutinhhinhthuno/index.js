import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachTinhHinhThuNo: []
}

const namespaced = true

export const tracuutinhhinhthuno = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
