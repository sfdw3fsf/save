
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    dsLoaiTu: null,
    tenLoaiTu: ''
}

const namespaced = true

export const loaiTu = {
    namespaced,
    state,
    getters,
    actions,
    mutations
}

export const getterName = Object.getOwnPropertyNames(getters)
export const actionName = Object.getOwnPropertyNames(actions)