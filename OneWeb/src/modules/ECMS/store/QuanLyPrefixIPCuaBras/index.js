
import { actions } from './actions'
import { mutations } from './mutations'

const state = {
    dsPrefixIp: [],
    dsLoaiTbDaGan: [],
    dsLoaiTbChuaGan: [],
    dsIp: [],
    dsBras: [],
    dsLoaiIp: [],
    dsDichVuVt: []
}

const namespaced = true

export const quanLyPrefixIPCuaBras = {
    namespaced,
    state,
    actions,
    mutations
}

export const actionName = Object.getOwnPropertyNames(actions)
export const statePropertyName = Object.getOwnPropertyNames(state)