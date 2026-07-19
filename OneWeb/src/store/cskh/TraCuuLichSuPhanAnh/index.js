import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsLichSuPhanAnh: [],
  totalPage: 0,
  pageSize: 10,
  totaItems: 0,
  objSearch: {}
}

const namespaced = true

export const tracuulichsuphananh = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
