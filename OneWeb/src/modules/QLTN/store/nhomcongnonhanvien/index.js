import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    dsCongNo: [],
    dsNhanVienDaGan: [],
    dsNhanVienChuaGan: [],
    dsAllNhanVienDaGan: [],
}

const namespaced = true

export const nhomcongnonhanvien = {
    actions,
    getters,
    mutations,
    state,
    namespaced
}