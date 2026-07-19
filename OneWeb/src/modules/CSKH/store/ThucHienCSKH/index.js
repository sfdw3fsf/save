import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsChuongTrinhCSKH: [],
  dsLoaiDoiTuong: [],
  dsChamSocTheoNV: [],
  dsCongViecTheoCT: []
}

const namespaced = true

export const thuchiencskh = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
