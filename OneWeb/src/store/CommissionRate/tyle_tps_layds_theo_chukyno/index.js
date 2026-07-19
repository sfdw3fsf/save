import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  DanhSachTyleTPSTheoChuKyNoDaGan: [],
  DanhSachTyleTPSTheoChuKyNoChuaGan: []
}

const namespaced = true

export const TyleTPSLayDSTheoChuKyNoCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
