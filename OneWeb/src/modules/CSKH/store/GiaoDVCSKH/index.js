import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsLoaiDonVi: [],
  dsChuongTrinh: [],
  dsDaGan: [],
  dsChuaGan: []
}

const namespaced = true

export const giaodvcskh = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
