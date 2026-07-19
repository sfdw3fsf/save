import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    dsVersion: [],
    dsInv: [],
    dsGroup: [],
    dsFormat: [],
    dsDataLayout: [],
}

const namespaced = true

export const gandinhdangchobanke = {
    actions,
    getters,
    mutations,
    state,
    namespaced
}