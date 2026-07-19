import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhsachBienNhan: [],
  danhsachHoaDonChuyenKhoan: [],
  objSearch: {}
}

const namespaced = true

export const tracuuhoadonchuyenkhoan = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
