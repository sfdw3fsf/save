
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsCsht: null,
  dvxl: null,
  kqxl: null,
  dvvp: null
  // ,dsLoaiTbChuaGan: null
}

const namespaced = true

export const viPhamCsht = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}

export const getterName = Object.getOwnPropertyNames(getters)
export const actionName = Object.getOwnPropertyNames(actions)
export const statePropertyName = Object.getOwnPropertyNames(state)
