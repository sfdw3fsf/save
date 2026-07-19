
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    dsBras : null,
    chiTietBras:null,
    brasNew:null
}

const namespaced = true

export const quanLyBras = {
    namespaced,
    state,
    getters,
    actions,
    mutations
}

export const getterName = Object.getOwnPropertyNames(getters)
export const actionName = Object.getOwnPropertyNames(actions)
export const statePropertyName = Object.getOwnPropertyNames(state)
