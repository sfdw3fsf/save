import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsNhanVienCSKH: [],
  dsCongViecCSKHTheoNguoiTao: [],
}

const namespaced = true

export const duyetcongvieccskh = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
