
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    danhSachHkn: null,
    danhMucHkn: null,
    hknNew: null,
    hknChitiet: null
}

const namespaced = true

export const hknNoiTinh = {
    namespaced,
    state,
    getters,
    actions,
    mutations
}

export const getterName = Object.getOwnPropertyNames(getters)
export const actionName = Object.getOwnPropertyNames(actions)
export const statePropertyName = Object.getOwnPropertyNames(state)
