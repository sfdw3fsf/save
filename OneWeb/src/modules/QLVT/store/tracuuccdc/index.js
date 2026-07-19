import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    // DSDonVi: [],
    // DSNhanVien: [],
    // DanhSachCCDC: [],
    // objSearch: {},
}

const namespaced = true

export const tracuuccdc = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}

export const getterName = Object.getOwnPropertyNames(getters);
export const actionName = Object.getOwnPropertyNames(actions);
export const statePropertyName = Object.getOwnPropertyNames(state);
export const mutationName = Object.getOwnPropertyNames(mutations);