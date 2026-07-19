import {actions} from './actions'
import {mutations} from './mutations'
import {getters} from './getters'

const state = {
  dsRack: [],
  dsTaiSanTimKiem: [],
  dsDuAn: [],
  dsTtvt: []
}

const namespaced = true

export const rack = {
  namespaced,
  state,
  actions,
  getters,
  mutations
}

export const actionName = Object.getOwnPropertyNames(actions)
export const statePropertyName = Object.getOwnPropertyNames(state)
export const getterName = Object.getOwnPropertyNames(getters)
