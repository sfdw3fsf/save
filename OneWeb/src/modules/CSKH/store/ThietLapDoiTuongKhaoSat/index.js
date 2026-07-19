import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    dsDichVuKS: [],
    dsDichVuKhongKS: [],
    dsKieuLDKS: [],
    dsKieuLDKhongKS: [],
    dsTinhTrangHongKS: [],
    dsTinhTrangHongKhongKS: []
}

const namespaced = true

export const thietlapdoituongkhaosat = {
    actions,
    getters,
    mutations,
    state,
    namespaced
}