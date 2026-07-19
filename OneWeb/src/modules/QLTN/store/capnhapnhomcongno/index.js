import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    dsNhomCongNo: [],
}

const namespaced = true

export const capnhapnhomcongno = {
    actions,
    getters,
    mutations,
    state,
    namespaced
}