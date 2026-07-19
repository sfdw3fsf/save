import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    // ds_VT: [],
    // ds_nv: [],
    // ds_ccdc: [],
    // ds_capphat: [],
    // ds_DV: [],
    DSTinhTrangTBi: [],
    // ds_serial: [],
    // ds_luuCP: [],
    // capnv: "",
    // dtpNgayDK: ""
}

const namespaced = true

export const chonnhanvienccdc = {
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