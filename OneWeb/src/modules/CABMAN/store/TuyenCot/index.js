import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    dsTTVT: [],
    dsToKT: [],
    dsTuyenCot: [],
    dsCot: [],
    dsTQLChuaGan: [],
    dsTQLDaGan: []
}

const namespaced = true;

export const tuyenCot = {
    namespaced,
    actions,
    getters,
    mutations,
    state
}

export const getterName = Object.getOwnPropertyNames(getters);
export const actionName = Object.getOwnPropertyNames(actions);
export const statePropertyName = Object.getOwnPropertyNames(state);
export const mutationName = Object.getOwnPropertyNames(mutations);
