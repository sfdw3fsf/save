import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    dsDichVu: [],
    dsQuyTrinh: [],
    dsLoaiHD: [],
    dsThueBao: [],
    dsLoaiHinh: [],
    dsLyDoHuy: [],
}

const namespaced = true

export const dongbothanhlyinternet = {
    actions,
    getters,
    mutations,
    state,
    namespaced
}