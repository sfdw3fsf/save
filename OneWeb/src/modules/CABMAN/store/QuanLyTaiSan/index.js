
import { actions } from './actions'
import { mutations } from './mutations'

const state = {
    dsTaiSan: [],
    dsTaiSanTimKiem: [],
    dsDuAn: [],
    dsTtvt: []
}

const namespaced = true

export const quanLyTaiSan = {
    namespaced,
    state,
    actions,
    mutations
}

export const actionName = Object.getOwnPropertyNames(actions)
export const statePropertyName = Object.getOwnPropertyNames(state)
