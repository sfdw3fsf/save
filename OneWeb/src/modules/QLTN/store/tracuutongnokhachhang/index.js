import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhsachTongNoKH: [],
  danhsachTongTraKH: [],
  objSearch: {},
  thongTinTT: null
}

const namespaced = true

export const tracuutongnokhachhang = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
