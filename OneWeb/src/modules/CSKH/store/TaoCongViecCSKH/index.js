import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsChuongTrinhCSKH: [],
  dsCongViecCSKH: [],
}

const namespaced = true

export const taocongvieccskh = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
