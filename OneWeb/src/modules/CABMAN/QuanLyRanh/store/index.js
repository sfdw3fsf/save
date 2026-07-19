/* eslint-disable */

import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'
const state = {
  ds_ttvt: [],
  ds_tram: [],
  ds_loairanh: [],
  ds_tokt: [],
  tabPageIndex: 0,
  ttvt_id: null,
  tramvt_id: null,
  tovt_id: null,
  loairanh_id: null,
  ds_ranh_id: []
}
const namespaced = true
export const QuanLyRanh = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}

export const getterName = Object.getOwnPropertyNames(getters)
export const actionName = Object.getOwnPropertyNames(actions)
export const statePropertyName = Object.getOwnPropertyNames(state)
