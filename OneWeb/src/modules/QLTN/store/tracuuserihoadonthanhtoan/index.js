import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhsachHoaDonThanhToan: [],
  objSearch: {},
  thongTinTT: null
}

const namespaced = true

export const tracuuserihoadonthanhtoan = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
